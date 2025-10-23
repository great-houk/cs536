package edu.wisc;

import java.util.ArrayList;
import java.util.List;

public class Parser {

	private final List<Token> tokens;
	private int current = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Stmt> parse() {
		List<Stmt> statements = new ArrayList<>();
		while (!isAtEnd()) {
			statements.add(statement());
		}
		return statements;
	}

	private Stmt statement() {
		if (match(Token.TokenType.LEFT_BRACE))
			return new Stmt.Block(block());
		// We check for expression at the end
		if (match(Token.TokenType.FUN))
			return funStmt();
		if (match(Token.TokenType.PRINT))
			return printStatement();
		if (match(Token.TokenType.IF))
			return ifStatement();
		if (match(Token.TokenType.RETURN))
			return returnStatement();
		if (match(Token.TokenType.INT, Token.TokenType.BOOL))
			return varStmt();
		if (check(Token.TokenType.IDENTIFIER) && checkNext(Token.TokenType.ASSIGN))
			return assignmentStatement();
		if (match(Token.TokenType.WHILE))
			return whileStatement();

		return expressionStatement();
	}

	private List<Stmt> block() {
		List<Stmt> statements = new ArrayList<>();

		while (!check(Token.TokenType.RIGHT_BRACE) && !isAtEnd()) {
			statements.add(statement());
		}

		consume(Token.TokenType.RIGHT_BRACE, "Expect '}' after block.");
		return statements;
	}

	private Stmt printStatement() {
		Expr value = expression();
		consume(Token.TokenType.SEMICOLON, "Expect ';' after value.");
		return new Stmt.Print(value);
	}

	private Stmt funStmt() {
		Token typeToken = advance();
		VarType type = typeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
		Token name = consume(Token.TokenType.IDENTIFIER, "Expect function name.");
		consume(Token.TokenType.LEFT_PAREN, "Expect '(' after function name.");
		List<Stmt.Parameter> parameters = new ArrayList<>();
		if (!check(Token.TokenType.RIGHT_PAREN)) {
			do {
				if (parameters.size() >= 255) {
					error(peek(), "Cannot have more than 255 parameters.");
				}
				Token paramTypeToken = consume(Token.TokenType.INT, "Expect parameter type.");
				VarType paramType = paramTypeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
				Token paramName = consume(Token.TokenType.IDENTIFIER, "Expect parameter name.");
				parameters.add(new Stmt.Parameter(paramName.lexeme, paramType));
			} while (match(Token.TokenType.COMMA));
		}
		consume(Token.TokenType.RIGHT_PAREN, "Expect ')' after parameters.");
		consume(Token.TokenType.LEFT_BRACE, "Expect '{' before function body.");
		List<Stmt> body = block();
		return new Stmt.Function(name.lexeme, type, parameters, body);
	}

	private Stmt varStmt() {
		Token typeToken = previous();
		VarType type = typeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
		Token name = consume(Token.TokenType.IDENTIFIER, "Expect variable name.");
		Expr initializer = null;
		if (match(Token.TokenType.ASSIGN)) {
			initializer = expression();
		}
		consume(Token.TokenType.SEMICOLON, "Expect ';' after variable declaration.");
		return new Stmt.Var(name.lexeme, type, initializer);
	}

	private Stmt ifStatement() {
		consume(Token.TokenType.LEFT_PAREN, "Expect '(' after 'if'.");
		Expr condition = expression();
		consume(Token.TokenType.RIGHT_PAREN, "Expect ')' after if condition.");

		Stmt thenBranch = statement();
		Stmt elseBranch = null;
		if (match(Token.TokenType.ELSE)) {
			elseBranch = statement();
		}

		return new Stmt.If(condition, thenBranch, elseBranch);
	}

	private Stmt whileStatement() {
		consume(Token.TokenType.LEFT_PAREN, "Expect '(' after 'while'.");
		Expr condition = expression();
		consume(Token.TokenType.RIGHT_PAREN, "Expect ')' after condition.");
		Stmt body = statement();

		return new Stmt.While(condition, body);
	}

	private Stmt returnStatement() {
		Expr value = null;
		if (!check(Token.TokenType.SEMICOLON)) {
			value = expression();
		}

		consume(Token.TokenType.SEMICOLON, "Expect ';' after return value.");
		return new Stmt.Return(value);
	}

	private Stmt assignmentStatement() {
		Token name = consume(Token.TokenType.IDENTIFIER, "Expect identifier.");
		consume(Token.TokenType.ASSIGN, "Expect '='.");
		Expr value = expression();
		consume(Token.TokenType.SEMICOLON, "Expect ';' after expression.");
		return new Stmt.Assign(name.lexeme, value);
	}

	private Stmt expressionStatement() {
		Expr expr = expression();
		consume(Token.TokenType.SEMICOLON, "Expect ';' after expression.");
		return new Stmt.Expression(expr);
	}

	private Expr expression() {
		return equality();
	}

	private Expr equality() {
		Expr expr = comparison();

		while (match(Token.TokenType.BANG_EQUAL, Token.TokenType.EQUAL_EQUAL)) {
			Operator operator = operator(previous().type);
			Expr right = comparison();
			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr comparison() {
		Expr expr = term();

		while (match(Token.TokenType.GREATER_THAN, Token.TokenType.GREATER_EQUAL, Token.TokenType.LESS_THAN,
				Token.TokenType.LESS_EQUAL)) {
			Operator operator = operator(previous().type);
			Expr right = term();
			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr term() {
		Expr expr = factor();

		while (match(Token.TokenType.MINUS, Token.TokenType.PLUS)) {
			Operator operator = operator(previous().type);
			Expr right = factor();
			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr factor() {
		Expr expr = unary();

		while (match(Token.TokenType.DIVIDE, Token.TokenType.TIMES)) {
			Operator operator = operator(previous().type);
			Expr right = unary();
			expr = new Expr.Binary(expr, operator, right);
		}

		return expr;
	}

	private Expr unary() {
		if (match(Token.TokenType.BANG, Token.TokenType.MINUS)) {
			Operator operator = operator(previous().type);
			Expr right = unary();
			return new Expr.Unary(operator, right);
		}

		return call();
	}

	private Expr call() {
		Expr expr = primary();

		while (true) {
			if (match(Token.TokenType.LEFT_PAREN)) {
				expr = finishCall(expr);
			} else {
				break;
			}
		}

		return expr;
	}

	private Expr finishCall(Expr callee) {
		List<Expr> arguments = new ArrayList<>();
		if (!check(Token.TokenType.RIGHT_PAREN)) {
			do {
				if (arguments.size() >= 255) {
					error(peek(), "Cannot have more than 255 arguments.");
				}
				arguments.add(expression());
			} while (match(Token.TokenType.COMMA));
		}

		consume(Token.TokenType.RIGHT_PAREN, "Expect ')' after arguments.");

		return new Expr.Call(((Expr.Variable) callee).name, arguments);
	}

	private Expr primary() {
		if (match(Token.TokenType.FALSE))
			return new Expr.Literal(false);
		if (match(Token.TokenType.TRUE))
			return new Expr.Literal(true);
		if (match(Token.TokenType.NUMBER)) {
			return new Expr.Literal(previous().literal);
		}

		if (match(Token.TokenType.IDENTIFIER)) {
			return new Expr.Variable(previous().lexeme);
		}

		if (match(Token.TokenType.LEFT_PAREN)) {
			Expr expr = expression();
			consume(Token.TokenType.RIGHT_PAREN, "Expect ')' after expression.");
			return new Expr.Binary(expr, null, null);
		}

		throw error(peek(), "Expect expression.");
	}

	private boolean match(Token.TokenType... types) {
		for (Token.TokenType type : types) {
			if (check(type)) {
				advance();
				return true;
			}
		}

		return false;
	}

	private Token consume(Token.TokenType type, String message) {
		if (check(type))
			return advance();

		throw error(peek(), message);
	}

	private boolean check(Token.TokenType type) {
		if (isAtEnd())
			return false;
		return peek().type == type;
	}

	private boolean checkNext(Token.TokenType type) {
		if (isAtEnd())
			return false;
		if (tokens.get(current + 1).type == Token.TokenType.EOF)
			return false;
		return tokens.get(current + 1).type == type;
	}

	private Token advance() {
		if (!isAtEnd())
			current++;
		return previous();
	}

	private boolean isAtEnd() {
		return peek().type == Token.TokenType.EOF;
	}

	private Token peek() {
		return tokens.get(current);
	}

	private Token previous() {
		return tokens.get(current - 1);
	}

	private ParseError error(Token token, String message) {
		// TODO: Report error
		return new ParseError();
	}

	private void synchronize() {
		advance();

		while (!isAtEnd()) {
			if (previous().type == Token.TokenType.SEMICOLON)
				return;

			switch (peek().type) {
				case FUN:
				case INT:
				case BOOL:
				case IF:
				case WHILE:
				case PRINT:
				case RETURN:
					return;
			}

			advance();
		}
	}

	private Operator operator(Token.TokenType type) {
		switch (type) {
			case PLUS:
				return Operator.PLUS;
			case MINUS:
				return Operator.MINUS;
			case TIMES:
				return Operator.MULTIPLY;
			case DIVIDE:
				return Operator.DIVIDE;
			case EQUAL_EQUAL:
				return Operator.EQUAL;
			case BANG_EQUAL:
				return Operator.NOT_EQUAL;
			case LESS_THAN:
				return Operator.LESS;
			case LESS_EQUAL:
				return Operator.LESS_EQUAL;
			case GREATER_THAN:
				return Operator.GREATER;
			case GREATER_EQUAL:
				return Operator.GREATER_EQUAL;
			case AND_AND:
				return Operator.AND;
			case OR_OR:
				return Operator.OR;
			case BANG:
				return Operator.NOT;
			default:
				return null;
		}
	}

	private static class ParseError extends RuntimeException {
	}
}