package edu.wisc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
	private final String source;
	private final List<Token> tokens = new ArrayList<>();
	private int start = 0;
	private int current = 0;
	private int line = 1;
	private int column = 1;

	private static final Map<String, Token.TokenType> keywords;

	static {
		keywords = new HashMap<>();
		keywords.put("int", Token.TokenType.INT);
		keywords.put("bool", Token.TokenType.BOOL);
		keywords.put("fun", Token.TokenType.FUN);
		keywords.put("if", Token.TokenType.IF);
		keywords.put("else", Token.TokenType.ELSE);
		keywords.put("while", Token.TokenType.WHILE);
		keywords.put("return", Token.TokenType.RETURN);
		keywords.put("print", Token.TokenType.PRINT);
		keywords.put("true", Token.TokenType.BOOLEAN);
		keywords.put("false", Token.TokenType.BOOLEAN);
	}

	public Lexer(String source) {
		this.source = source;
	}

	public List<Token> scanTokens() {
		while (!isAtEnd()) {
			start = current;
			scanToken();
		}

		tokens.add(new Token(Token.TokenType.EOF, "", line, column));
		return tokens;
	}

	private void scanToken() {
		char c = advance();
		switch (c) {
			case '(':
				addToken(Token.TokenType.LEFT_PAREN);
				break;
			case ')':
				addToken(Token.TokenType.RIGHT_PAREN);
				break;
			case '{':
				addToken(Token.TokenType.LEFT_BRACE);
				break;
			case '}':
				addToken(Token.TokenType.RIGHT_BRACE);
				break;
			case ',':
				addToken(Token.TokenType.COMMA);
				break;
			case ';':
				addToken(Token.TokenType.SEMICOLON);
				break;
			case '+':
				addToken(Token.TokenType.PLUS);
				break;
			case '-':
				addToken(Token.TokenType.MINUS);
				break;
			case '*':
				addToken(Token.TokenType.TIMES);
				break;
			case '/':
				// Skip over comments
				if (match('/')) {
					while (peek() != '\n' && !isAtEnd())
						advance();
				} else {
					addToken(Token.TokenType.DIVIDE);
				}
				break;
			case '=':
				addToken(match('=') ? Token.TokenType.EQUAL_EQUAL : Token.TokenType.ASSIGN);
				break;
			case '!':
				addToken(match('=') ? Token.TokenType.BANG_EQUAL : Token.TokenType.BANG);
				break;
			case '<':
				addToken(match('=') ? Token.TokenType.LESS_EQUAL : Token.TokenType.LESS_THAN);
				break;
			case '>':
				addToken(match('=') ? Token.TokenType.GREATER_EQUAL : Token.TokenType.GREATER_THAN);
				break;
			case '&':
				if (match('&')) {
					addToken(Token.TokenType.AND_AND);
				} else {
					throw new BadlangError("Unknown token '&'", line, column);
				}
				break;
			case '|':
				if (match('|')) {
					addToken(Token.TokenType.OR_OR);
				} else {
					throw new BadlangError("Unknown token '|'", line, column);
				}
				break;
			case ' ':
			case '\r':
			case '\t':
				// Ignore whitespace
				break;
			case '\n':
				line++;
				column = 1;
				break;
			default:
				if (isDigit(c)) {
					number();
				} else if (isAlpha(c)) {
					identifier();
				} else {
					String text = source.substring(start, current);
					throw new BadlangError("Unknown token '" + text + "'", line, column);
				}
				break;
		}
	}

	private void identifier() {
		while (isAlphaNumeric(peek()))
			advance();

		String text = source.substring(start, current);
		Token.TokenType type = keywords.get(text);
		if (type == null)
			type = Token.TokenType.IDENTIFIER;
		addToken(type);
	}

	private void number() {
		while (isDigit(peek()))
			advance();

		addToken(Token.TokenType.NUMBER);
	}

	private boolean match(char expected) {
		if (isAtEnd())
			return false;
		if (source.charAt(current) != expected)
			return false;

		current++;
		return true;
	}

	private char peek() {
		if (isAtEnd())
			return '\0';
		return source.charAt(current);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z') ||
				c == '_';
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private char advance() {
		current++;
		column++;
		return source.charAt(current - 1);
	}

	private void addToken(Token.TokenType type) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, line, column));
	}
}