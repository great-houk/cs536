package edu.wisc;

import java.util.List;
import java.util.Vector;

public class Checker implements Expr.Visitor<VarType>, Stmt.Visitor<Void> {
	private Environment env;
	private List<BadlangError> errors;
	private VarType retType;
	private int functionDepth = 0;  // 0 = global, >0 = inside a function, added this so we can check for nested functions


	public Checker() {
		env = new Environment();
		errors = new Vector<>();
		retType = null;
	}

	@Override
	public Void visitBlockStmt(Stmt.Block stmt) {
		enterEnv();
		for (var s : stmt.statements)
			s.accept(this);
		exitEnv();
		return null;
	}

	@Override
	public Void visitExpressionStmt(Stmt.Expression stmt) {
		stmt.expression.accept(this);
		return null;
	}

	@Override
	public Void visitFunctionStmt(Stmt.Function stmt) {
		// Functions get defined in a first pass in 
		// the check function, so we don't add them to env here
		if (functionDepth > 0) {
        	errors.add(new BadlangError("Functions cannot be nested.", stmt.line));
        	// We still descend so other errors inside can be reported
    	}
		enterEnv();
		for (var p : stmt.params){
			try {
				env.defineVar(p.name(), p.type(), stmt.line);
			} catch (BadlangError e) {
				errors.add(e);
			}
		}
		var oldRetType = retType;
		retType = stmt.returnType;

		functionDepth++;
		for (var s : stmt.body){
			s.accept(this);
		}
		functionDepth--;

		retType = oldRetType;
		exitEnv();
		return null;
	}

	@Override
	public Void visitIfStmt(Stmt.If stmt) {
		if (!sameType(stmt.condition.accept(this), VarType.BOOL)) {
			errors.add(new BadlangError("Invalid condition, doesn't evaluate to type 'bool'", stmt.line));
		}

		enterEnv();
		stmt.thenBranch.accept(this);
		exitEnv();

		if (stmt.elseBranch != null) {
			enterEnv();
			stmt.elseBranch.accept(this);
			exitEnv();
		}

		return null;
	}

	@Override
	public Void visitPrintStmt(Stmt.Print stmt) {
		stmt.expression.accept(this);
		return null;
	}

	@Override
	public Void visitReturnStmt(Stmt.Return stmt) {
		if (retType == null) {
			errors.add(new BadlangError("Invalid return statement, not in function", stmt.line));
			return null;
		} 

		if (stmt.value == null) {
			errors.add(new BadlangError("Invalid return: missing value of type '" + retType.getName() + "'", stmt.line));
			return null;
		}

		var type = stmt.value.accept(this);
		if (!sameType(type, retType)) {
			errors.add(new BadlangError(
					"Invalid return type, expected type '" + retType.getName() + "', found type '" + type.getName()
							+ "'",
					stmt.line));
		}
		
		return null;
	}

	@Override
	public Void visitVarStmt(Stmt.Var stmt) {
		if (stmt.initializer != null) {
			var type = stmt.initializer.accept(this);
			if (!sameType(type, stmt.type)) {
				errors.add(new BadlangError(
						"Wrong variable type, expected '" + stmt.type.getName() + "', found type '"
								+ type.getName()
								+ "'",
						stmt.line));
			}
		}
		try {
			env.defineVar(stmt.name, stmt.type, stmt.line);
		} catch (BadlangError e) {
			errors.add(e);
		}
		return null;
	}

	@Override
	public Void visitAssignStmt(Stmt.Assign stmt) {
		VarType varType;
		try {
			varType = (VarType) env.getVar(stmt.name, stmt.line);
		} catch (BadlangError e) {
			errors.add(e);
			varType = VarType.ERROR;
		}
		var exprType = stmt.value.accept(this);

		if (!sameType(varType, exprType))
			errors.add(new BadlangError(
					"Invalid assignment, expected type '" + varType.getName() + "', found '" + exprType.getName() + "'",
					stmt.line));

		return null;
	}

	@Override
	public Void visitWhileStmt(Stmt.While stmt) {
		if (!sameType(stmt.condition.accept(this), VarType.BOOL)) {
			errors.add(new BadlangError("Invalid condition, doesn't evaluate to type 'bool'", stmt.line));
		}
		enterEnv();
		stmt.body.accept(this);
		exitEnv();
		return null;
	}

	@Override
	public VarType visitBinaryExpr(Expr.Binary expr) {
		var left = expr.left.accept(this);
		var right = expr.right.accept(this);

		VarType argType = VarType.ERROR;
		VarType retType = VarType.ERROR;

		switch (expr.operator) {
			case PLUS:
			case MINUS:
			case MULTIPLY:
			case DIVIDE:
				argType = VarType.INT;
				retType = VarType.INT;
				break;
			case OR:
			case AND:
			case NOT:
				argType = VarType.BOOL;
				retType = VarType.BOOL;
				break;
			case GREATER:
			case GREATER_EQUAL:
			case LESS:
			case LESS_EQUAL:
				argType = VarType.INT;
				retType = VarType.BOOL;
				break;
			case EQUAL:
			case NOT_EQUAL:
				argType = left;
				retType = VarType.BOOL;
				break;
		}

		if (!sameType(left, argType)) {
			errors.add(new BadlangError(
					"Invalid operand type, expected '" + argType.getName() + "', found '" + left.getName() + "'",
					expr.line));
		}
		if (!sameType(right, argType)) {
			errors.add(new BadlangError(
					"Invalid operand type, expected '" + argType.getName() + "', found '" + right.getName()
							+ "'",
					expr.line));
		}

		return retType;
	}

	@Override
	public VarType visitLiteralExpr(Expr.Literal expr) {
		if (expr.value instanceof Integer)
			return VarType.INT;
		else if (expr.value instanceof Boolean)
			return VarType.BOOL;
		else
			return VarType.ERROR;
	}

	@Override
	public VarType visitUnaryExpr(Expr.Unary expr) {
		if (expr.operator == Operator.MINUS) {
			var type = expr.right.accept(this);
			if (!sameType(type, VarType.INT)) {
				errors.add(new BadlangError("Invalid operand type, expected 'int', found '" + type.getName() + "'",
						expr.line));
			}
			return VarType.INT;
		} else if (expr.operator == Operator.NOT) {
			var type = expr.right.accept(this);
			if (!sameType(type, VarType.BOOL)) {
				errors.add(new BadlangError("Invalid operand type, expected 'bool', found '" + type.getName() + "'",
						expr.line));
			}
			return VarType.BOOL;
		} else {
			errors.add(new BadlangError("Invalid unary op '" + expr.operator + "'", expr.line));
			return VarType.ERROR;
		}
	}

	@Override
	public VarType visitVariableExpr(Expr.Variable expr) {
		try {
			return (VarType) env.getVar(expr.name, expr.line);
		} catch (BadlangError e) {
			errors.add(e);
			return VarType.ERROR;
		}
	}

	@Override
	public VarType visitCallExpr(Expr.Call expr) {
		try {
			var fun = env.getFun(expr.name, expr.line);

			if (expr.arguments.size() != fun.params.size()) {
				errors.add(new BadlangError("Invalid function call, expected " + fun.params.size()
						+ " parameters, found " + expr.arguments.size(), expr.line));
			} else {
				for (int i = 0; i < expr.arguments.size(); i++) {
					var expType = fun.params.get(i).type();
					var type = expr.arguments.get(i).accept(this);
					if (!sameType(type, expType)) {
						errors.add(new BadlangError("Invalid function parameter, expected type '" + expType.getName()
								+ "', found type '" + type.getName() + "'", expr.line));
					}
				}
			}

			return fun.returnType;
		} catch (BadlangError e) {
			errors.add(e);
			return VarType.ERROR;
		}
	}

	private void enterEnv() {
		var oldEnv = env;
		env = new Environment(oldEnv);
	}

	private void exitEnv() {
		env = env.getParent();
	}

	private boolean sameType(VarType a, VarType b) {
		if (a == VarType.ERROR || b == VarType.ERROR || a == b)
			return true;
		return false;
	}

	public List<BadlangError> check(List<Stmt> program) {
		// Get all function definitions
		for (var s : program) {
			if (s instanceof Stmt.Function) {
				var fun = (Stmt.Function) s;
				try{
					env.defineFun(fun.name, fun);
				} catch (BadlangError e){
					errors.add(e);
				}
			}
		}
		// Visit everything
		for (var s : program) {
			s.accept(this);
		}
		return errors;
	}
}
