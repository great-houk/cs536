package edu.wisc;

public class BadlangError extends RuntimeException {
	public BadlangError(String message, int line, int column) {
		super("Line: " + line + " Column: " + column + ": " + message);
	}

	public BadlangError(String message, int line) {
		super("Line: " + line + ": " + message);
	}
}
