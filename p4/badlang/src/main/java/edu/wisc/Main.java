package edu.wisc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class to run the Badlang interpreter.
 * It can run in two modes:
 * 1. Test mode: Run all .bl files in the test_programs directory and compare the output to the expected output.
 * 2. File mode: Run a single .bl file and print the output.
 */
public class Main {

	private static final PrintStream originalOut = System.out;
	private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private static final PrintStream capturedOut = new PrintStream(baos);

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			// If arguments are provided, run in file mode.
			for (String arg : args) {
				runFile(Paths.get(arg));
			}
		} else {
			// Otherwise, run in test mode.
			runAllTests();
		}
	}

	/**
	 * Run all .bl files in the test_programs directory.
	 * @throws IOException if there is an error reading the files.
	 */
	private static void runAllTests() throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get("test_programs"))) {
			List<Path> testFiles = paths.filter(Files::isRegularFile)
					.filter(p -> p.toString().endsWith(".bl"))
					.collect(Collectors.toList());

			int passed = 0;
			int failed = 0;

			for (Path testFile : testFiles) {
				if (runTest(testFile)) {
					passed++;
				} else {
					failed++;
				}
			}

			originalOut.println("\nTest summary: " + passed + " passed, " + failed + " failed.");
		}
	}

	/**
	 * Run a single test file.
	 * @param filePath the path to the test file.
	 * @return true if the test passed, false otherwise.
	 * @throws IOException if there is an error reading the file.
	 */
	private static boolean runTest(Path filePath) throws IOException {
		String source = Files.readString(filePath);
		String expectedOutput = getExpectedOutput(source);
		String expectedErrors = getExpectedErrors(source);

		System.setOut(capturedOut); // Capture output
		baos.reset();

		try {
			List<Stmt> program = parseSource(source);
			Checker check = new Checker();
			var errors = check.check(program);
			if (!expectedErrors.isEmpty()) {
				System.setOut(originalOut);
				String actualErrors = errors.stream().map(BadlangError::getLocalizedMessage).collect(Collectors.joining("\n"));
				if (actualErrors.equals(expectedErrors)) {
					originalOut.println("[PASS] " + filePath.getFileName());
					return true;
				} else {
					originalOut.println("[FAIL] " + filePath.getFileName());
					originalOut.println("  Expected errors: " + expectedErrors);
					originalOut.println("  Actual errors  : " + actualErrors);
					return false;
				}
			}
			if (errors.size() == 0) {
				Interpreter interpreter = new Interpreter();
				interpreter.interpret(program);
			} else {
				System.setOut(originalOut);
				originalOut.println("[FAIL] " + filePath.getFileName());
				originalOut.println("  Unexpected errors:");
				for (var e : errors) {
					originalOut.println("    " + e.getLocalizedMessage());
				}
				return false;
			}
		} catch (Exception e) {
			baos.reset();
			System.setOut(originalOut);
			originalOut.println("[FAIL] " + filePath.getFileName());
			System.err.println("Error during test " + filePath.getFileName().toString() + ": " + e.getMessage());
			return false;
		} finally {
			System.setOut(originalOut); // Restore original output
		}

		String actualOutput = baos.toString().trim().replaceAll("\n", "\n");

		if (actualOutput.equals(expectedOutput)) {
			originalOut.println("[PASS] " + filePath.getFileName());
			return true;
		} else {
			originalOut.println("[FAIL] " + filePath.getFileName());
			originalOut.println("  Expected: " + expectedOutput);
			originalOut.println("  Actual  : " + actualOutput);
			return false;
		}
	}

	/**
	 * Run a single file and print the output.
	 * @param filePath the path to the file.
	 * @throws IOException if there is an error reading the file.
	 */
	private static void runFile(Path filePath) throws IOException {
		String source = Files.readString(filePath);
		try {
			List<Stmt> program = parseSource(source);
			Checker checker = new Checker();
			var errors = checker.check(program);
			if (errors.size() == 0) {
				Interpreter interpreter = new Interpreter();
				interpreter.interpret(program);
			} else {
				originalOut.println("[FAIL] " + filePath.getFileName());
				for (var e : errors) {
					System.err.println(e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Parse the source code and return the list of statements.
	 * @param source the source code to parse.
	 * @return the list of statements.
	 */
	private static List<Stmt> parseSource(String source) {
		Lexer l = new Lexer(source);
		var tokens = l.scanTokens();
		Parser p = new Parser(tokens);
		return p.parse();
	}

	/**
	 * Get the expected output from the source code.
	 * The expected output is specified in comments like "// expect: <output>"
	 * @param source the source code.
	 * @return the expected output.
	 */
	private static String getExpectedOutput(String source) {
		StringBuilder expected = new StringBuilder();
		Pattern pattern = Pattern.compile("//\s*expect:\s*(.*)");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			expected.append(matcher.group(1).trim()).append("\n");
		}
		return expected.toString().trim();
	}

	private static String getExpectedErrors(String source) {
		StringBuilder expected = new StringBuilder();
		Pattern pattern = Pattern.compile("//\s*expect-error:\s*(.*)");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			expected.append(matcher.group(1).trim()).append("\n");
		}
		return expected.toString().trim();
	}
}