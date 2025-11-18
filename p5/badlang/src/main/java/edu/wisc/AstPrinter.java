package edu.wisc;

import java.util.List;

public class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {

	String print(List<Stmt> statements) {
		StringBuilder builder = new StringBuilder();
		for (Stmt statement : statements) {
			if (statement != null) {
				builder.append(statement.accept(this));
			}
		}
		return builder.toString();
	}

	@Override
	public String visitAssignStmt(Stmt.Assign stmt) {
		return parenthesize(stmt.name + " =", stmt.value);
	}

	@Override
	public String visitBinaryExpr(Expr.Binary expr) {
		return parenthesize(expr.operator.toString(), expr.left, expr.right);
	}

	@Override
	public String visitCallExpr(Expr.Call expr) {
		return parenthesize(expr.name, (Object[]) expr.arguments.toArray(new Expr[0]));
	}

	@Override
	public String visitLiteralExpr(Expr.Literal expr) {
		if (expr.value == null)
			return "nil";
		return expr.value.toString();
	}

	@Override
	public String visitUnaryExpr(Expr.Unary expr) {
		return parenthesize(expr.operator.toString(), expr.right);
	}

	@Override
	public String visitVariableExpr(Expr.Variable expr) {
		return expr.name;
	}

	@Override
	public String visitBlockStmt(Stmt.Block stmt) {
		StringBuilder builder = new StringBuilder();
		builder.append("(block ");

		for (Stmt statement : stmt.statements) {
			builder.append(statement.accept(this));
		}

		builder.append(")");
		return builder.toString();
	}

	@Override
	public String visitExpressionStmt(Stmt.Expression stmt) {
		return parenthesize(";", stmt.expression);
	}

	@Override
	public String visitFunctionStmt(Stmt.Function stmt) {
		StringBuilder builder = new StringBuilder();
		builder.append("(fun " + stmt.name + "(");

		for (Stmt.Parameter param : stmt.params) {
			builder.append(" " + param.type() + " " + param.name());
		}

		builder.append(") ");

		for (Stmt body : stmt.body) {
			builder.append(body.accept(this));
		}

		builder.append(")");
		return builder.toString();
	}

	@Override
	public String visitIfStmt(Stmt.If stmt) {
		if (stmt.elseBranch == null) {
			return parenthesize("if", stmt.condition, stmt.thenBranch);
		}

		return parenthesize("if-else", stmt.condition, stmt.thenBranch, stmt.elseBranch);
	}

	@Override
	public String visitPrintStmt(Stmt.Print stmt) {
		return parenthesize("print", stmt.expression);
	}

	@Override
	public String visitReturnStmt(Stmt.Return stmt) {
		if (stmt.value == null)
			return "(return)";
		return parenthesize("return", stmt.value);
	}

	@Override
	public String visitVarStmt(Stmt.Var stmt) {
		if (stmt.initializer == null) {
			return parenthesize("var " + stmt.name);
		}

		return parenthesize("var " + stmt.name + " =", stmt.initializer);
	}

	@Override
	public String visitWhileStmt(Stmt.While stmt) {
		return parenthesize("while", stmt.condition, stmt.body);
	}

	private String parenthesize(String name, Object... parts) {
		StringBuilder builder = new StringBuilder();

		builder.append("(").append(name);
		for (Object part : parts) {
			builder.append(" ");
			if (part instanceof Expr) {
				builder.append(((Expr) part).accept(this));
			} else if (part instanceof Stmt) {
				builder.append(((Stmt) part).accept(this));
			} else {
				builder.append(part);
			}
		}
		builder.append(")");

		return builder.toString();
	}
}
