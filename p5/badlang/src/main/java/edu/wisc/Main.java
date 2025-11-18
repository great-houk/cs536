package edu.wisc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Test harness: compile Badlang -> MIPS, then run in SPIM and assert outputs. */
public class Main {

    // ---------- Pipeline: parse -> check -> codegen ----------
    private static String compileToAsm(String source) {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.scanTokens());
        List<Stmt> program = parser.parse();

        // Keep checker to avoid codegen on malformed ASTs
        Checker checker = new Checker();
        var errors = checker.check(program);
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (var e : errors) sb.append(e.getMessage()).append('\n');
            throw new RuntimeException("Checker errors:\n" + sb);
        }

        Asm asm = new Asm();
        CodegenVisitor cg = new CodegenVisitor(asm);
        cg.generate(program);
        return asm.emit();
    }

    // ---------- Find SPIM executable (CLI) ----------
    private static String findSpimCommand() {
        // 1) Allow override with env var
        String env = System.getenv("SPIM_CMD");
        if (env != null && !env.isBlank()) return env;

        // 2) Try common names; Windows users may need spim.exe in PATH
        String os = System.getProperty("os.name", "").toLowerCase();
        String[] candidates = os.contains("win")
                ? new String[] {"spim.exe", "spim"}      // prefer .exe on Windows
                : new String[] {"spim"};

        for (String cmd : candidates) {
            try {
                Process p = new ProcessBuilder(cmd, "-version")
                        .redirectErrorStream(true).start();
                p.waitFor();
                // If it started at all, assume OK
                return cmd;
            } catch (Exception ignore) { /* try next */ }
        }
        throw new RuntimeException(
            "Could not find SPIM. Install it and/or set SPIM_CMD to the full path (e.g. C:\\\\Path\\\\to\\\\spim.exe).");
    }

    // ---------- Run SPIM on a given .s file and capture stdout ----------
    private static String runSpimOn(Path asmPath) throws IOException, InterruptedException {
        // Build command tokens correctly (supports "wsl spim")
        java.util.List<String> cmd = new java.util.ArrayList<>(java.util.List.of(spimCmdTokens()));

        // WSL needs /mnt/... paths
        String asmArg = asmPath.toString();
        boolean viaWSL = !cmd.isEmpty() && cmd.get(0).equalsIgnoreCase("wsl");
        if (viaWSL) asmArg = toWslPath(asmArg);

        // SPIM 8.x prefers -f; include exceptions runtime for safety
        cmd.add("-exception"); cmd.add("/usr/lib/spim/exceptions.s");  // try /usr/share/... if needed
        cmd.add("-f"); cmd.add(asmArg);

        // (Temporarily) do NOT use -quiet so we can see banners/errors
        // cmd.add(0, "-quiet");

        System.out.println("CMD: " + String.join(" ", cmd));

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        // Close stdin so SPIM can't block waiting for input
        p.getOutputStream().close();

        // Read output concurrently to avoid buffer deadlock
        java.io.InputStream is = p.getInputStream();
        byte[] buf = is.readAllBytes();

        // Add a timeout so we never hang forever
        boolean finished = p.waitFor(20, java.util.concurrent.TimeUnit.SECONDS);
        if (!finished) {
            p.destroyForcibly();
            throw new RuntimeException("SPIM timed out (20s). Output so far:\n" + new String(buf));
        }

        int code = p.exitValue();
        String out = new String(buf);

        if (code != 0) {
            throw new RuntimeException("SPIM exited " + code + "\n" + out);
        }
        return out;
    }


    // ---------- Helper: write a .s file to disk for debugging ----------
    private static Path writeAsmFile(String asm, String name) throws IOException {
        Path out = Path.of(name);
        Files.writeString(out, asm);
        return out;
    }

    // ---------- One test: compile, run in SPIM, check output contains tokens ----------
    private static boolean runtimeTest(String blPath, String[] expectContains) {
        try {
            String src = Files.readString(Path.of(blPath));
            String asm = compileToAsm(src);
            Path sPath = writeAsmFile(asm, "out.s");
            String spimOut = runSpimOn(sPath);

            for (String needle : expectContains) {
                if (!spimOut.contains(needle)) {
                    System.out.println("[FAIL] " + blPath + " — missing in SPIM output: \"" + needle + "\"");
                    System.out.println("----- SPIM OUTPUT -----");
                    System.out.println(spimOut);
                    System.out.println("----- END -----");
                    return false;
                }
            }
            System.out.println("[PASS] " + blPath);
            return true;
        } catch (Throwable t) {
            System.out.println("[FAIL] " + blPath + " — " + t.getMessage());
            return false;
        }
    }
    static String toWslPath(String p) {
        String s = p.replace('\\', '/');
        if (s.length() >= 2 && Character.isLetter(s.charAt(0)) && s.charAt(1) == ':') {
            char drive = Character.toLowerCase(s.charAt(0));
            s = "/mnt/" + drive + s.substring(2);
        }
        return s;
    }

    static String[] spimCmdTokens() {
        String env = System.getenv("SPIM_CMD");          // e.g., "wsl spim"
        if (env != null && !env.isBlank()) {
            return env.trim().split("\\s+");             // ["wsl","spim"]
        }
        // Fallbacks: try plain "spim", else "wsl spim"
        try {
            Process t = new ProcessBuilder("spim", "-v").redirectErrorStream(true).start();
            t.waitFor();
            return new String[] { "spim" };
        } catch (Exception ignore) {
            return new String[] { "wsl", "spim" };
        }
    }


    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                // ---- No-args mode: run the runtime tests ----
                int passed = 0, total = 0;

                total++; if (runtimeTest(
                    "test_programs/test1.bl",
                    new String[] { "7", "10", "1" }
                )) passed++;

                total++; if (runtimeTest(
                    "test_programs/test_if_else.bl",
                    new String[] { "111" }
                )) passed++;

                total++; if (runtimeTest(
                    "test_programs/test_call.bl",
                    new String[] { "9" }
                )) passed++;

                System.out.printf("Runtime tests: %d/%d passed%n", passed, total);
                if (passed != total) System.exit(1);
                return;
            }

            // ---- Single-file mode: compile one program, emit out.s, run SPIM ----
            Path inputPath = Path.of(args[0]);
            String source = Files.readString(inputPath);
            String asm = compileToAsm(source);

            Path outS = Path.of("out.s");
            Files.writeString(outS, asm);
            System.out.println("===== MIPS Assembly (saved to out.s) =====");
            System.out.println(asm);

            // Prefer SPIM_CMD if set (e.g., "wsl spim"); else try "spim"; else fallback to WSL
            String env = System.getenv("SPIM_CMD");
            java.util.List<String> cmd = new java.util.ArrayList<>();
            if (env != null && !env.isBlank()) {
                for (String part : env.trim().split("\\s+")) cmd.add(part);
            } else {
                try {
                    var test = new ProcessBuilder("spim", "-v").redirectErrorStream(true).start();
                    test.waitFor();
                    cmd.add("spim");
                } catch (Exception ignore) {
                    cmd.add("wsl"); cmd.add("spim");
                }
            }

            String asmArg = outS.toString();
            boolean viaWSL = !cmd.isEmpty() && cmd.get(0).equalsIgnoreCase("wsl");
            if (viaWSL) asmArg = toWslPath(asmArg); // uses your helper

            java.util.List<String> full = new java.util.ArrayList<>(cmd);
            full.add("-quiet");
            full.add("-exception"); full.add("/usr/lib/spim/exceptions.s"); // try /usr/share/... if needed
            full.add("-f"); full.add(asmArg);

            Process p = new ProcessBuilder(full).redirectErrorStream(true).start();
            String out = new String(p.getInputStream().readAllBytes());
            int code = p.waitFor();

            System.out.println("===== SPIM OUTPUT =====");
            System.out.print(out);
            if (code != 0) {
                System.err.println("SPIM exited with code " + code);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Could not run: " + e.getMessage());
            System.exit(1);
        }
    }

}
