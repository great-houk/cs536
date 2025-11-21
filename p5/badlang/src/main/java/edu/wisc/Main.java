package edu.wisc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			for (var e : errors)
				sb.append(e.getMessage()).append('\n');
			throw new RuntimeException("Checker errors:\n" + sb);
		}

		CodegenVisitor cg = new CodegenVisitor();
		return cg.generate(program);
	}

	// ---------- Run SPIM on a given .s file and capture stdout ----------
	private static String runSpimOn(Path asmPath) throws IOException, InterruptedException {
		// Use spim directly (we're already running inside WSL)
		java.util.List<String> cmd = java.util.List.of(
				"spim",
				"-exception",
				"-f",
				asmPath.toString());

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
		String start = "Loaded: /usr/lib/spim/exceptions.s\n";
		int prefixInd = out.indexOf(start) + start.length();
		out = out.substring(prefixInd);
		// Remove trailing newline
		out = out.substring(0, out.length() - 1);

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

	// ---------- Helper: extract expected output lines from source comments ----------
	private static List<String> extractExpectLines(String source) {
		List<String> expects = new ArrayList<>();
		Pattern p = Pattern.compile("//\\s*expect:\\s*(.*)");
		Matcher m = p.matcher(source);
		while (m.find()) {
			expects.add(m.group(1).trim());
		}
		return expects;
	}

	// ---------- One test: compile, run in SPIM, check output contains tokens ----------
	private static boolean runtimeTest(String blPath, String[] expectContains) {
		try {
			String src = Files.readString(Path.of(blPath));
			String asm = compileToAsm(src);
			Path sPath = writeAsmFile(asm, "out.s");
			String spimOut = runSpimOn(sPath);
			List<String> lines = spimOut.lines().toList();

			// We expect an extra newline
			if (lines.size() != expectContains.length) {
				System.out.println("[FAIL] — Extra lines in SPIM output:");
				System.out.println("----- SPIM OUTPUT -----");
				System.out.println(spimOut);
				System.out.println("----- END -----");
				return false;
			}

			for (int i = 0; i < expectContains.length; i++) {
				if (!lines.get(i).equals(expectContains[i])) {
					System.out.println("[FAIL] " + blPath + " — missing in SPIM output: \"" + expectContains[i] + "\"");
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

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				// ---- No-args mode: run all .bl files in test_programs ----
				List<Path> testFiles;
				try (Stream<Path> s = Files.walk(Path.of("test_programs"))) {
					testFiles = s.filter(Files::isRegularFile)
							.filter(p -> p.toString().endsWith(".bl"))
							.collect(Collectors.toList());
				}

				int passed = 0, total = 0;
				for (Path p : testFiles) {
					System.out.println("Running " + p.toString());
					total++;
					String src = Files.readString(p);
					List<String> expects = extractExpectLines(src);
					String[] needles = expects.toArray(new String[0]);
					if (runtimeTest(p.toString(), needles))
						passed++;
					else
						break;
				}

				System.out.printf("Runtime tests: %d/%d passed%n", passed, total);
				if (passed != total)
					System.exit(1);
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

			// Call spim directly (we're already inside WSL)
			String asmArg = outS.toString();
			java.util.List<String> full = java.util.List.of(
					"spim",
					"-quiet",
					"-exception",
					"-f",
					asmArg);

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
