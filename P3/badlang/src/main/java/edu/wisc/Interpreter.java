package edu.wisc;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

class ReturnValue {
	public Object value;

	ReturnValue(Object value) {
		this.value = value;
	}
}

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<ReturnValue> {
	// These aren't in env, because we only need one global function scope.
	private final Map<String, Stmt.Function> functions = new HashMap<>();
	Environment env;

	Interpreter() {
		this.env = new Environment();
	}

	private <T> void assertType(Class<T> type, Object... values) {
		for (Object t : values) {
			if (!type.isInstance(t)) {
				throw new RuntimeException(
						"Expected type '" + type.getName() + "' found type '" + t.getClass().getName() + "'.");
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
		}
		throw new RuntimeException("Expected type '" + t.name() + "' but found '" + val + "'.");
	}

	@Override
	public ReturnValue visitFunctionStmt(Stmt.Function stmt) {
		functions.put(stmt.name, stmt);
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

		env.define(stmt.name, value);
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
		env.assign(stmt.name, val);
		return null;
	}

	@Override
	public Object visitBinaryExpr(Expr.Binary expr) {
		Object left = evalExpression(expr.left);
		Object right = evalExpression(expr.right);

		switch (expr.operator) {
			case PLUS:
				assertType(Integer.class, left, right);
				return (Integer) left + (Integer) right;
			case MINUS:
				assertType(Integer.class, left, right);
				return (Integer) left - (Integer) right;
			case MULTIPLY:
				assertType(Integer.class, left, right);
				return (Integer) left * (Integer) right;
			case DIVIDE:
				assertType(Integer.class, left, right);
				if ((Integer) right == 0) {
					throw new RuntimeException("Division by zero.");
				}
				return (Integer) left / (Integer) right;
			case EQUAL:
				return left.equals(right);
			case NOT_EQUAL:
				return !left.equals(right);
			case LESS:
				assertType(Integer.class, left, right);
				return (Integer) left < (Integer) right;
			case LESS_EQUAL:
				assertType(Integer.class, left, right);
				return (Integer) left <= (Integer) right;
			case GREATER:
				assertType(Integer.class, left, right);
				return (Integer) left > (Integer) right;
			case GREATER_EQUAL:
				assertType(Integer.class, left, right);
				return (Integer) left >= (Integer) right;
			case AND:
				assertType(Boolean.class, left, right);
				if ((Boolean) left)
					return (Boolean) right;
				return false;
			case OR:
				assertType(Boolean.class, left, right);
				if ((Boolean) left)
					return true;
				return (Boolean) right;
			default:
				throw new RuntimeException("Unknown binary operator: " + expr.operator);
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
				assertType(Integer.class, right);
				return -(Integer) right;
			case NOT:
				assertType(Boolean.class, right);
				return !(Boolean) right;
			default:
				throw new RuntimeException("Unknown unary operator: " + expr.operator);
		}
	}

	@Override
	public Object visitVariableExpr(Expr.Variable expr) {
		return env.get(expr.name);
	}

	@Override
	public Object visitCallExpr(Expr.Call expr) {
		if (!functions.containsKey(expr.name)) {
			throw new RuntimeException("Undefined function '" + expr.name + "'.");
		}

		var function = functions.get(expr.name);
		var env = new Environment(this.env);

		// Check argument types
		if (expr.arguments.size() != function.params.size())
			throw new RuntimeException("Invalid arg count for function '" + function.name + "'.");

		// Eval args
		var ps = function.params;
		var as = expr.arguments;
		for (int i = 0; i < function.params.size(); i++) {
			var val = evalExpression(as.get(i), ps.get(i).type());
			env.define(ps.get(i).name(), val);
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
		if (ret == null) {
			throw new RuntimeException("Function '" + function.name + "' must return a value.");
		}
		if (ret.value == null) {
			throw new RuntimeException("Function '" + function.name + "' must return a value, but returned null.");
		}
		switch (function.returnType) {
			case INT:
				assertType(Integer.class, ret.value);
				return (Integer) ret.value;
			case BOOL:
				assertType(Boolean.class, ret.value);
				return (Boolean) ret.value;
			default:
				throw new RuntimeException("Unknown Return Type '" + function.returnType + "'.");
		}
	}

	public Object interpret(List<Stmt> stmts) {
		for (var s : stmts) {
			var ret = s.accept(this);
			if (ret != null)
				throw new RuntimeException("Cannot return from top-level code.");
		}
		return null;
	}
}