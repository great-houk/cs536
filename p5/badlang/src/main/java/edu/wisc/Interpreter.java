package edu.wisc;

import java.util.List;

class ReturnValue {
	public Object value;

	ReturnValue(Object value) {
		this.value = value;
	}
}

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<ReturnValue> {
	Environment env;

	Interpreter() {
		this.env = new Environment();
	}

	private void assertType(VarType type, int line, Object... values) {
		for (Object t : values) {
			if (!type.toType().isInstance(t)) {
				throw new BadlangError(
						"Expected variable of type '" + type.getName() + "' found type '" + t.getClass().getName()
								+ "'.",
						line);
			}
		}
	}

	private Object evalExpression(Expr e) {
		return this.evalExpression(e, null);
	}

	private Object evalExpression(Expr e, VarType t) {
		Object val = e.accept(this);
		if (t == null) {
			return val;
		}
		switch (t) {
			case INT:
				if (val instanceof Integer)
					return val;
				break;
			case BOOL:
				if (val instanceof Boolean)
					return val;
				break;
			default:
				break;
		}
		throw new BadlangError(
				"Expected variable of type '" + t.getName() + "' but found type '" + val.getClass().getName() + "'",
				e.line);
	}

	@Override
	public ReturnValue visitFunctionStmt(Stmt.Function stmt) {
		// We do a first pass to get all function definitions, so nothing needs to happen here
		// env.defineFun(stmt.name, stmt);
		return null;
	}

	@Override
	public ReturnValue visitIfStmt(Stmt.If stmt) {
		var condition = evalExpression(stmt.condition, VarType.BOOL);

		if ((boolean) condition) {
			return stmt.thenBranch.accept(this);
		} else if (stmt.elseBranch != null) {
			return stmt.elseBranch.accept(this);
		} else {
			return null;
		}
	}

	@Override
	public ReturnValue visitPrintStmt(Stmt.Print stmt) {
		var val = evalExpression(stmt.expression);
		System.out.println(val);
		return null;
	}

	@Override
	public ReturnValue visitReturnStmt(Stmt.Return stmt) {
		if (stmt.value == null) {
			return new ReturnValue(null);
		} else {
			return new ReturnValue(evalExpression(stmt.value));
		}
	}

	@Override
	public ReturnValue visitBlockStmt(Stmt.Block stmt) {
		var old = this.env;
		this.env = new Environment(old);
		ReturnValue ret = null;
		for (var s : stmt.statements) {
			ret = s.accept(this);
			if (ret != null)
				break;
		}
		this.env = old;
		return ret;
	}

	@Override
	public ReturnValue visitVarStmt(Stmt.Var stmt) {
		Object value = null;
		if (stmt.initializer != null) {
			value = evalExpression(stmt.initializer, stmt.type);
		}

		env.defineVar(stmt.name, value, stmt.line);
		return null;
	}

	@Override
	public ReturnValue visitWhileStmt(Stmt.While stmt) {
		while ((boolean) evalExpression(stmt.condition, VarType.BOOL)) {
			var ret = stmt.body.accept(this);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	@Override
	public ReturnValue visitExpressionStmt(Stmt.Expression stmt) {
		evalExpression(stmt.expression);
		return null;
	}

	@Override
	public ReturnValue visitAssignStmt(Stmt.Assign stmt) {
		var val = evalExpression(stmt.value);
		env.assignVar(stmt.name, val, stmt.line);
		return null;
	}

	@Override
	public Object visitBinaryExpr(Expr.Binary expr) {
		Object left = evalExpression(expr.left);
		Object right = evalExpression(expr.right);

		switch (expr.operator) {
			case PLUS:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left + (Integer) right;
			case MINUS:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left - (Integer) right;
			case MULTIPLY:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left * (Integer) right;
			case DIVIDE:
				assertType(VarType.INT, expr.line, left, right);
				if ((Integer) right == 0) {
					throw new BadlangError("Division by zero", expr.line);
				}
				return (Integer) left / (Integer) right;
			case EQUAL:
				return left.equals(right);
			case NOT_EQUAL:
				return !left.equals(right);
			case LESS:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left < (Integer) right;
			case LESS_EQUAL:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left <= (Integer) right;
			case GREATER:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left > (Integer) right;
			case GREATER_EQUAL:
				assertType(VarType.INT, expr.line, left, right);
				return (Integer) left >= (Integer) right;
			case AND:
				assertType(VarType.BOOL, expr.line, left, right);
				if ((Boolean) left)
					return (Boolean) right;
				return false;
			case OR:
				assertType(VarType.BOOL, expr.line, left, right);
				if ((Boolean) left)
					return true;
				return (Boolean) right;
			default:
				throw new BadlangError("Unknown binary operator '" + expr.operator + "'", expr.line);
		}
	}

	@Override
	public Object visitLiteralExpr(Expr.Literal expr) {
		return expr.value;
	}

	@Override
	public Object visitUnaryExpr(Expr.Unary expr) {
		Object right = expr.right.accept(this);

		switch (expr.operator) {
			case MINUS:
				assertType(VarType.INT, expr.line, right);
				return -(Integer) right;
			case NOT:
				assertType(VarType.BOOL, expr.line, right);
				return !(Boolean) right;
			default:
				throw new BadlangError("Unknown unary operator '" + expr.operator + "'", expr.line);
		}
	}

	@Override
	public Object visitVariableExpr(Expr.Variable expr) {
		return env.getVar(expr.name, expr.line);
	}

	@Override
	public Object visitCallExpr(Expr.Call expr) {
		var function = env.getFun(expr.name, expr.line);
		var env = new Environment(this.env);

		// Check argument types
		if (expr.arguments.size() != function.params.size())
			throw new BadlangError("Invalid arg count for function '" + function.name + "'", expr.line);

		// Eval args
		var ps = function.params;
		var as = expr.arguments;
		for (int i = 0; i < function.params.size(); i++) {
			var val = evalExpression(as.get(i), ps.get(i).type());
			env.defineVar(ps.get(i).name(), val, expr.line);
		}

		// Call body
		var old = this.env;
		this.env = env;

		ReturnValue ret = null;
		for (Stmt s : function.body) {
			ret = s.accept(this);
			if (ret != null)
				break;
		}

		this.env = old;

		// Return
		switch (function.returnType) {
			case INT:
				assertType(VarType.INT, expr.line, ret.value);
				return (Integer) ret.value;
			case BOOL:
				assertType(VarType.BOOL, expr.line, ret.value);
				return (Boolean) ret.value;
			default:
				throw new BadlangError("Unknown Return Type '" + function.returnType + "'", expr.line);
		}
	}

	public Object interpret(List<Stmt> stmts) {
		// Get all function definitions
		for (var s : stmts) {
			if (s instanceof Stmt.Function) {
				var fun = (Stmt.Function) s;
				env.defineFun(fun.name, fun);
			}
		}
		// Visit everything
		for (var s : stmts) {
			var ret = s.accept(this);
			if (ret != null)
				return ret;
		}
		return null;
	}

	public void clear_env() {
		this.env = new Environment();
	}
}