package edu.wisc;

import java.util.ArrayList;
import java.util.List;

import edu.wisc.Token.TokenType;

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
		if (checkAndAdvance(Token.TokenType.LEFT_BRACE))
			return new Stmt.Block(block());
		// We check for expression at the end
		if (checkAndAdvance(Token.TokenType.FUN))
			return funStmt();
		if (checkAndAdvance(Token.TokenType.PRINT))
			return printStatement();
		if (checkAndAdvance(Token.TokenType.IF))
			return ifStatement();
		if (checkAndAdvance(Token.TokenType.RETURN))
			return returnStatement();
		if (check(Token.TokenType.INT, Token.TokenType.BOOL))
			return varStmt();
		if (check(Token.TokenType.IDENTIFIER) && checkNext(Token.TokenType.ASSIGN))
			return assignmentStatement();
		if (checkAndAdvance(Token.TokenType.WHILE))
			return whileStatement();

		return expressionStatement();
	}

	private List<Stmt> block() {
		List<Stmt> statements = new ArrayList<>();

		while (!check(Token.TokenType.RIGHT_BRACE) && !isAtEnd()) {
			statements.add(statement());
		}

		expect(Token.TokenType.RIGHT_BRACE, "Expect '}' after block.");
		return statements;
	}

	private Stmt printStatement() {
		Expr value = expression();
		expect(Token.TokenType.SEMICOLON, "Expect ';' after value.");
		return new Stmt.Print(value);
	}

	private Stmt funStmt() {
		Token typeToken = advance();
		VarType type = typeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
		Token name = expect(Token.TokenType.IDENTIFIER, "Expect function name.");
		expect(Token.TokenType.LEFT_PAREN, "Expect '(' after function name.");
		List<Stmt.Parameter> parameters = new ArrayList<>();
		if (!check(Token.TokenType.RIGHT_PAREN)) {
			do {
				if (parameters.size() >= 255) {
					error(peek(), "Cannot have more than 255 parameters.");
				}
				Token paramTypeToken = advance();
				VarType paramType = paramTypeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
				Token paramName = expect(Token.TokenType.IDENTIFIER, "Expect parameter name.");
				parameters.add(new Stmt.Parameter(paramName.lexeme, paramType));
			} while (checkAndAdvance(Token.TokenType.COMMA));
		}
		expect(Token.TokenType.RIGHT_PAREN, "Expect ')' after parameters.");
		expect(Token.TokenType.LEFT_BRACE, "Expect '{' before function body.");
		List<Stmt> body = block();
		return new Stmt.Function(name.lexeme, type, parameters, body);
	}

	private Stmt varStmt() {
		Token typeToken = advance();
		VarType type = typeToken.type == Token.TokenType.INT ? VarType.INT : VarType.BOOL;
		Token name = expect(Token.TokenType.IDENTIFIER, "Expect variable name.");
		Expr initializer = null;
		if (checkAndAdvance(Token.TokenType.ASSIGN)) {
			initializer = expression();
		}
		expect(Token.TokenType.SEMICOLON, "Expect ';' after variable declaration.");
		return new Stmt.Var(name.lexeme, type, initializer);
	}

	private Stmt ifStatement() {
		expect(Token.TokenType.LEFT_PAREN, "Expect '(' after 'if'.");
		Expr condition = expression();
		expect(Token.TokenType.RIGHT_PAREN, "Expect ')' after if condition.");

		Stmt thenBranch = statement();
		Stmt elseBranch = null;
		if (checkAndAdvance(Token.TokenType.ELSE)) {
			elseBranch = statement();
		}

		return new Stmt.If(condition, thenBranch, elseBranch);
	}

	private Stmt whileStatement() {
		expect(Token.TokenType.LEFT_PAREN, "Expect '(' after 'while'.");
		Expr condition = expression();
		expect(Token.TokenType.RIGHT_PAREN, "Expect ')' after condition.");
		Stmt body = statement();

		return new Stmt.While(condition, body);
	}

	private Stmt returnStatement() {
		Expr value = null;
		if (!check(Token.TokenType.SEMICOLON)) {
			value = expression();
		}

		expect(Token.TokenType.SEMICOLON, "Expect ';' after return value.");
		return new Stmt.Return(value);
	}

	private Stmt assignmentStatement() {
		Token name = expect(Token.TokenType.IDENTIFIER, "Expect identifier.");
		expect(Token.TokenType.ASSIGN, "Expect '='.");
		Expr value = expression();
		expect(Token.TokenType.SEMICOLON, "Expect ';' after expression.");
		return new Stmt.Assign(name.lexeme, value);
	}

	private Stmt expressionStatement() {
		Expr expr = expression();
		expect(Token.TokenType.SEMICOLON, "Expect ';' after expression.");
		return new Stmt.Expression(expr);
	}

	// Expressions

	private Expr expression() {
		// Find end of expression, marked by unmatched RPAREN, semicolon, or comma
		int endInd = current;
		int parenCount = 0;
		for (; endInd < tokens.size(); endInd++) {
			Token t = tokens.get(endInd);
			if (t.type == TokenType.SEMICOLON || t.type == TokenType.COMMA)
				break;
			if (t.type == TokenType.RIGHT_PAREN) {
				parenCount -= 1;
				if (parenCount < 0)
					break;
			}
			if (t.type == TokenType.LEFT_PAREN)
				parenCount += 1;
		}

		return expressionInRange(endInd);
	}

	private Expr expressionInRange(int endInd) {
		Expr ret = null;

		// Binary
		// Find operator
		int lowestInd = -1;
		int lowestPrec = -1;
		for (int i = current; i < endInd; i++) {
			if (tokens.get(i).type == TokenType.LEFT_PAREN) {
				do {
					i++;
				} while (tokens.get(i).type != TokenType.RIGHT_PAREN);
			}
			if (checkBinOp(i)) {
				int p = precedence(tokens.get(i));
				if (lowestInd == -1 || p <= lowestPrec) {
					lowestPrec = p;
					lowestInd = i;
				}
			}
		}
		// Do binary stuff
		if (lowestInd != -1) {
			Expr left = expressionInRange(lowestInd);
			Operator op = tokenToOperator(advance());
			Expr right = expressionInRange(endInd);
			ret = new Expr.Binary(left, op, right);
		}
		// Paren
		else if (checkAndAdvance(Token.TokenType.LEFT_PAREN)) {
			ret = expression();
			expect(Token.TokenType.RIGHT_PAREN, "Expect '(' after ')'");
		}
		// Call
		else if (check(Token.TokenType.IDENTIFIER) && checkNext(Token.TokenType.LEFT_PAREN))
			ret = callExpr();
		// Unary
		else if (check(Token.TokenType.MINUS, Token.TokenType.BANG)) {
			Operator op = tokenToOperator(advance());
			ret = new Expr.Unary(op, expression());
		}
		// Literal
		else if (check(Token.TokenType.NUMBER, Token.TokenType.BOOLEAN)) {
			Token t = advance();
			Object value = t.type == Token.TokenType.NUMBER ? Integer.parseInt(t.lexeme)
					: Boolean.parseBoolean(t.lexeme);
			ret = new Expr.Literal(value);
		}
		// Var Expr
		else if (check(TokenType.IDENTIFIER)) {
			ret = new Expr.Variable(advance().lexeme);
		}
		// Error Case
		else {
			throw new ParseError("Unexpected token '" + peek().lexeme + "'", peek().line, peek().column);
		}

		if (ret == null || current != endInd)
			throw new ParseError("Unexpected end of expression", peek().line, peek().column);

		return ret;
	}

	private Expr callExpr() {
		Token id = expect(Token.TokenType.IDENTIFIER, "Expected function name");
		expect(Token.TokenType.LEFT_PAREN, "Expect '('");
		List<Expr> parameters = new ArrayList<>();
		if (!check(Token.TokenType.RIGHT_PAREN)) {
			do {
				if (parameters.size() >= 255) {
					error(peek(), "Cannot have more than 255 parameters.");
				}
				Expr expr = expression();
				parameters.add(expr);
			} while (checkAndAdvance(Token.TokenType.COMMA));
		}
		expect(TokenType.RIGHT_PAREN, "Expected '(' at end of function call");
		return new Expr.Call(id.lexeme, parameters);
	}

	// Helpers

	private boolean checkAndAdvance(Token.TokenType... types) {
		if (check(types)) {
			advance();
			return true;
		}

		return false;

	}

	private boolean check(Token.TokenType... types) {
		if (isAtEnd())
			return false;
		for (Token.TokenType type : types)
			if (peek().type == type)
				return true;
		return false;
	}

	private Token expect(Token.TokenType type, String message) {
		if (check(type))
			return advance();

		throw error(peek(), message);
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
		return new ParseError(message, token.line, token.column);
	}

	private boolean checkBinOp(int index) {
		int old = current;
		current = index;
		if (check(TokenType.PLUS, TokenType.TIMES, TokenType.DIVIDE, TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL,
				TokenType.LESS_THAN, TokenType.LESS_EQUAL, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL,
				TokenType.AND_AND, TokenType.OR_OR)) {
			current = old;
			return true;
		}
		if (check(TokenType.MINUS)) {
			current--;
			if (current < 0
					|| check(TokenType.COMMA, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN, TokenType.SEMICOLON)) {
				current = old;
				return false;
			} else {
				current = old;
				return true;
			}
		}

		current = old;
		return false;
	}

	private int precedence(Token token) {
		switch (token.type) {
			case AND_AND:
				return 1;
			case OR_OR:
				return 2;
			case EQUAL_EQUAL:
			case BANG_EQUAL:
			case LESS_THAN:
			case LESS_EQUAL:
			case GREATER_THAN:
			case GREATER_EQUAL:
				return 3;
			case PLUS:
			case MINUS:
				return 4;
			case TIMES:
			case DIVIDE:
				return 5;
			default:
				throw new ParseError("Unexpected '" + token.lexeme + "', expected operator", token.line, token.column);
		}
	}

	private Operator tokenToOperator(Token token) {
		switch (token.type) {
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
				throw new ParseError("Unexpected '" + token.lexeme + "', expected operator", token.line, token.column);
		}
	}

	private static class ParseError extends RuntimeException {
		public ParseError(String message, int line, int column) {
			super("Line: " + line + " Column: " + column + ": " + message);
		}
	}
}