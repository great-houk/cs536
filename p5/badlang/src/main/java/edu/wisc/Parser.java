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
		if (checkAndAdvance(TokenType.LEFT_BRACE))
			return new Stmt.Block(block(), peek().line);
		// We check for expression at the end
		if (checkAndAdvance(TokenType.FUN))
			return funStmt();
		if (checkAndAdvance(TokenType.PRINT))
			return printStatement();
		if (checkAndAdvance(TokenType.IF))
			return ifStatement();
		if (checkAndAdvance(TokenType.RETURN))
			return returnStatement();
		if (check(TokenType.INT, TokenType.BOOL))
			return varStmt();
		if (check(TokenType.IDENTIFIER) && checkNext(TokenType.ASSIGN))
			return assignmentStatement();
		if (checkAndAdvance(TokenType.WHILE))
			return whileStatement();

		return expressionStatement();
	}

	private List<Stmt> block() {
		List<Stmt> statements = new ArrayList<>();

		while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
			statements.add(statement());
		}

		expect("Expect '}' after block.", TokenType.RIGHT_BRACE);
		return statements;
	}

	private Stmt printStatement() {
		Expr value = expression();
		expect("Expect ';' after value.", TokenType.SEMICOLON);
		return new Stmt.Print(value, value.line);
	}

	private Stmt funStmt() {
		Token typeToken = expect("Expect type name", TokenType.INT, TokenType.BOOL);
		VarType type = VarType.fromToken(typeToken);
		Token name = expect("Expect function name.", TokenType.IDENTIFIER);
		expect("Expect '(' after function name.", TokenType.LEFT_PAREN);
		List<Stmt.Parameter> parameters = new ArrayList<>();
		if (!check(TokenType.RIGHT_PAREN)) {
			do {
				if (parameters.size() >= 255) {
					error(peek(), "Cannot have more than 255 parameters.");
				}
				Token paramTypeToken = expect("Expect type name", TokenType.INT, TokenType.BOOL);
				VarType paramType = VarType.fromToken(paramTypeToken);
				Token paramName = expect("Expect parameter name.", TokenType.IDENTIFIER);
				parameters.add(new Stmt.Parameter(paramName.lexeme, paramType));
			} while (checkAndAdvance(TokenType.COMMA));
		}
		expect("Expect ')' after parameters.", TokenType.RIGHT_PAREN);
		expect("Expect '{' before function body.", TokenType.LEFT_BRACE);
		List<Stmt> body = block();
		return new Stmt.Function(name.lexeme, type, parameters, body, name.line);
	}

	private Stmt varStmt() {
		Token typeToken = expect("Expect type name", TokenType.INT, TokenType.BOOL);
		VarType type = VarType.fromToken(typeToken);
		Token name = expect("Expect variable name.", TokenType.IDENTIFIER);
		Expr initializer = null;
		if (checkAndAdvance(TokenType.ASSIGN)) {
			initializer = expression();
		}
		expect("Expect ';' after variable declaration.", TokenType.SEMICOLON);
		return new Stmt.Var(name.lexeme, type, initializer, name.line);
	}

	private Stmt ifStatement() {
		expect("Expect '(' after 'if'.", TokenType.LEFT_PAREN);
		Expr condition = expression();
		expect("Expect ')' after if condition.", TokenType.RIGHT_PAREN);

		Stmt thenBranch = statement();
		Stmt elseBranch = null;
		if (checkAndAdvance(TokenType.ELSE)) {
			elseBranch = statement();
		}

		return new Stmt.If(condition, thenBranch, elseBranch, condition.line);
	}

	private Stmt whileStatement() {
		expect("Expect '(' after 'while'.", TokenType.LEFT_PAREN);
		Expr condition = expression();
		expect("Expect ')' after condition.", TokenType.RIGHT_PAREN);
		Stmt body = statement();

		return new Stmt.While(condition, body, condition.line);
	}

	private Stmt returnStatement() {
		Expr value = null;
		if (!check(TokenType.SEMICOLON)) {
			value = expression();
		}

		expect("Expect ';' after return value.", TokenType.SEMICOLON);
		return new Stmt.Return(value, previous().line);
	}

	private Stmt assignmentStatement() {
		Token name = expect("Expect identifier.", TokenType.IDENTIFIER);
		expect("Expect '='.", TokenType.ASSIGN);
		Expr value = expression();
		expect("Expect ';' after expression.", TokenType.SEMICOLON);
		return new Stmt.Assign(name.lexeme, value, name.line);
	}

	private Stmt expressionStatement() {
		Expr expr = expression();
		expect("Expect ';' after expression.", TokenType.SEMICOLON);
		return new Stmt.Expression(expr, expr.line);
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
			// Skip over () since they always have the highest precedence
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
			Operator op = Operator.fromToken(advance());
			Expr right = expressionInRange(endInd);
			ret = new Expr.Binary(left, op, right, left.line);
		}
		// Paren
		else if (checkAndAdvance(TokenType.LEFT_PAREN)) {
			ret = expression();
			expect("Expect ')' after '('", TokenType.RIGHT_PAREN);
		}
		// Call
		else if (check(TokenType.IDENTIFIER) && checkNext(TokenType.LEFT_PAREN)) {
			Token id = expect("Expected function name", TokenType.IDENTIFIER);
			expect("Expect '('", TokenType.LEFT_PAREN);
			List<Expr> parameters = new ArrayList<>();
			if (!check(TokenType.RIGHT_PAREN)) {
				do {
					if (parameters.size() >= 255) {
						error(peek(), "Cannot have more than 255 parameters.");
					}
					Expr expr = expression();
					parameters.add(expr);
				} while (checkAndAdvance(TokenType.COMMA));
			}
			expect("Expected '(' at end of function call", TokenType.RIGHT_PAREN);
			return new Expr.Call(id.lexeme, parameters, id.line);
		}
		// Unary
		else if (check(TokenType.MINUS, TokenType.BANG)) {
			Operator op = Operator.fromToken(advance());
			Expr expr = expressionInRange(endInd);			//changed this to use the current subrange
			ret = new Expr.Unary(op, expr, expr.line);
		}
		// Literal
		else if (check(TokenType.NUMBER, TokenType.BOOLEAN)) {
			Token t = advance();
			Object value;
			if (t.type == TokenType.NUMBER) {
				try {
					value = Integer.parseInt(t.lexeme);
				} catch (NumberFormatException e) {
					throw new BadlangError("Invalid integer literal '" + t.lexeme + "'", t.line, t.column);
				}
			} else if (t.type == TokenType.BOOLEAN) {
				if (t.lexeme.equals("true"))
					value = true;
				else if (t.lexeme.equals("false"))
					value = false;
				else
					throw new BadlangError("Invalid boolean literal '" + t.lexeme + "'", t.line, t.column);
			} else {
				throw new BadlangError("Invalid literal type '" + t.type + "'", t.line, t.column);
			}
			ret = new Expr.Literal(value, t.line);
		}
		// Var Expr
		else if (check(TokenType.IDENTIFIER)) {
			ret = new Expr.Variable(peek().lexeme, advance().line);
		}
		// Error Case
		else {
			throw new BadlangError("Unexpected token '" + peek().lexeme + "'", peek().line, peek().column);
		}

		if (ret == null || current != endInd)
			throw new BadlangError("Unexpected token in expression '" + peek().toString() + "'", peek().line,
					peek().column);

		return ret;
	}

	// Helpers

	private boolean check(TokenType... types) {
		if (isAtEnd())
			return false;
		for (TokenType type : types)
			if (peek().type == type)
				return true;
		return false;
	}

	private boolean checkAndAdvance(TokenType... types) {
		if (check(types)) {
			advance();
			return true;
		}

		return false;
	}

	private Token expect(String message, TokenType... types) {
		if (check(types))
			return advance();

		throw error(peek(), message);
	}

	private boolean checkNext(TokenType type) {
		if (isAtEnd())
			return false;
		if (tokens.get(current + 1).type == TokenType.EOF)
			return false;
		return tokens.get(current + 1).type == type;
	}

	private Token advance() {
		if (!isAtEnd())
			current++;
		return previous();
	}

	private boolean isAtEnd() {
		return peek().type == TokenType.EOF;
	}

	private Token peek() {
		return tokens.get(current);
	}

	private Token previous() {
		return tokens.get(current - 1);
	}

	private BadlangError error(Token token, String message) {
		return new BadlangError(message, token.line, token.column);
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
		if (check(TokenType.MINUS)) { //need to handle special case this is a binary or a unary
			boolean isBinary = isBinaryMinus(index);
			current = old;
			return isBinary;
		} 

		current = old;
		return false;
	}

	private boolean isBinaryMinus(int index) {//helper method for when there is a minus
		if (index <= 0) return false;
		TokenType prev = tokens.get(index - 1).type; //prev is the symbol before the minus sign
		//tthis will return true (that is is a binary) if the previous symbol was a number, identifier, boolean, or right paren. 
		return prev == TokenType.IDENTIFIER		
			|| prev == TokenType.NUMBER
			|| prev == TokenType.BOOLEAN
			|| prev == TokenType.RIGHT_PAREN;
	}

	private int precedence(Token token) {
		switch (token.type) {
			case AND_AND:
				return 2;		//changing this so || is lower, so it will be the split and && will have a higher precedence
			case OR_OR:			//if && is higher it parses that first and it will be regarded as the split, so each side will be evaluated and then combined, even if on one side there is a || that should have been evaluated first
				return 1;		//be evaluated and then combined, even if on one side there is a || that should have been evaluated first
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
				throw new BadlangError("Unexpected '" + token.lexeme + "', expected operator", token.line,
						token.column);
		}
	}
}