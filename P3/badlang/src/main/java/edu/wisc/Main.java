package edu.wisc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			runFile(args[0]);
		} else {
			System.out.println("Usage: jbadlang [script]");
			System.exit(64);
		}
	}

	private static void runFile(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		run(new String(bytes, Charset.defaultCharset()));
	}

	private static void run(String source) {
		Lexer scanner = new Lexer(source);
		List<Token> tokens = scanner.scanTokens();
		Parser parser = new Parser(tokens);
		List<Stmt> statements = parser.parse();

		Interpreter intp = new Interpreter();
		intp.interpret(statements);
	}

	static void error(int line, String message) {
		report(line, "", message);
	}

	private static void report(int line, String where, String message) {
		System.err.println(
				"[line " + line + "] Error" + where + ": " + message);
	}
}