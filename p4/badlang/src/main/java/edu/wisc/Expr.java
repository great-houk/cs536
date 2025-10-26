package edu.wisc;

import java.util.List;

abstract class Expr {
	interface Visitor<R> {
		R visitBinaryExpr(Binary expr);

		R visitLiteralExpr(Literal expr);

		R visitUnaryExpr(Unary expr);

		R visitVariableExpr(Variable expr);

		R visitCallExpr(Call expr);
	}

	static class Binary extends Expr {
		Binary(Expr left, Operator operator, Expr right, int line) {
			this.left = left;
			this.operator = operator;
			this.right = right;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpr(this);
		}

		final Expr left;
		final Operator operator;
		final Expr right;
	}

	static class Literal extends Expr {
		Literal(Object value, int line) {
			this.value = value;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpr(this);
		}

		final Object value;
	}

	static class Unary extends Expr {
		Unary(Operator operator, Expr right, int line) {
			this.operator = operator;
			this.right = right;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpr(this);
		}

		final Operator operator;
		final Expr right;
	}

	static class Variable extends Expr {
		Variable(String name, int line) {
			this.name = name;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVariableExpr(this);
		}

		final String name;
	}

	static class Call extends Expr {
		Call(String name, List<Expr> arguments, int line) {
			this.name = name;
			this.arguments = arguments;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitCallExpr(this);
		}

		final String name;
		final List<Expr> arguments;
	}

	abstract <R> R accept(Visitor<R> visitor);

	protected int line;
}
