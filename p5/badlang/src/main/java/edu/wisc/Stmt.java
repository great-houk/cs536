package edu.wisc;

import java.util.List;

abstract class Stmt {
	interface Visitor<R> {
		R visitBlockStmt(Block stmt);

		R visitExpressionStmt(Expression stmt);

		R visitFunctionStmt(Function stmt);

		R visitIfStmt(If stmt);

		R visitPrintStmt(Print stmt);

		R visitReturnStmt(Return stmt);

		R visitVarStmt(Var stmt);

		R visitAssignStmt(Assign stmt);

		R visitWhileStmt(While stmt);
	}

	static record Parameter(String name, VarType type) {
	}

	static class Block extends Stmt {
		Block(List<Stmt> statements, int line) {
			this.statements = statements;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBlockStmt(this);
		}

		final List<Stmt> statements;
	}

	static class Expression extends Stmt {
		Expression(Expr expression, int line) {
			this.expression = expression;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitExpressionStmt(this);
		}

		final Expr expression;
	}

	static class Function extends Stmt {
		Function(String name, VarType returnType, List<Parameter> params, List<Stmt> body, int line) {
			this.name = name;
			this.returnType = returnType;
			this.params = params;
			this.body = body;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitFunctionStmt(this);
		}

		final String name;
		final VarType returnType;
		final List<Parameter> params;
		final List<Stmt> body;
	}

	static class If extends Stmt {
		If(Expr condition, Stmt thenBranch, Stmt elseBranch, int line) {
			this.condition = condition;
			this.thenBranch = thenBranch;
			this.elseBranch = elseBranch;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitIfStmt(this);
		}

		final Expr condition;
		final Stmt thenBranch;
		final Stmt elseBranch;
	}

	static class Print extends Stmt {
		Print(Expr expression, int line) {
			this.expression = expression;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitPrintStmt(this);
		}

		final Expr expression;
	}

	static class Return extends Stmt {
		Return(Expr value, int line) {
			this.value = value;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitReturnStmt(this);
		}

		final Expr value;
	}

	static class Var extends Stmt {
		Var(String name, VarType type, Expr initializer, int line) {
			this.name = name;
			this.type = type;
			this.initializer = initializer;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVarStmt(this);
		}

		final String name;
		final VarType type;
		final Expr initializer;
	}

	static class Assign extends Stmt {
		Assign(String name, Expr value, int line) {
			this.name = name;
			this.value = value;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAssignStmt(this);
		}

		final String name;
		final Expr value;
	}

	static class While extends Stmt {
		While(Expr condition, Stmt body, int line) {
			this.condition = condition;
			this.body = body;
			this.line = line;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitWhileStmt(this);
		}

		final Expr condition;
		final Stmt body;
	}

	abstract <R> R accept(Visitor<R> visitor);

	protected int line;
}
