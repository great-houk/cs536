package edu.wisc;

public enum Operator {
	// Arithmetic operators
	PLUS("+"),
	MINUS("-"),
	MULTIPLY("*"),
	DIVIDE("/"),

	// Logical operators
	AND("&&"),
	OR("||"),
	NOT("!"),
	EQUAL("=="),
	NOT_EQUAL("!="),
	LESS("<"),
	LESS_EQUAL("<="),
	GREATER(">"),
	GREATER_EQUAL(">=");

	private final String symbol;

	Operator(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public static Operator fromToken(Token t) {
		switch (t.type) {
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
				throw new BadlangError("Unexpected '" + t.lexeme + "', expected operator", t.line,
						t.column);
		}
	}

	@Override
	public String toString() {
		return symbol;
	}
}
