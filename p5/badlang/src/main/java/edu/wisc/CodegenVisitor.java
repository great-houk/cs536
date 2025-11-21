package edu.wisc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wisc.Expr.Binary;
import edu.wisc.Expr.Call;
import edu.wisc.Expr.Literal;
import edu.wisc.Expr.Unary;
import edu.wisc.Expr.Variable;
import edu.wisc.Stmt.Assign;
import edu.wisc.Stmt.Block;
import edu.wisc.Stmt.Expression;
import edu.wisc.Stmt.Function;
import edu.wisc.Stmt.If;
import edu.wisc.Stmt.Print;
import edu.wisc.Stmt.Return;
import edu.wisc.Stmt.Var;
import edu.wisc.Stmt.While;

public final class CodegenVisitor implements Expr.Visitor<VarType>, Stmt.Visitor<Void> {
	private List<AsmFrame> frames;
	private Map<String, Stmt.Function> funcs;
	private AsmFrame frame;

	CodegenVisitor() {
		this.frames = new ArrayList<>();
		this.funcs = new HashMap<>();
	}

	public String generate(List<Stmt> program) {
		// Set up frames, defining strings for boolean print values
		this.funcs.clear();
		this.frames.clear();
		this.frames.add(new AsmFrame());
		this.frame = this.frames.get(0);
		this.frame.allocString("bool_true", "true\\n");
		this.frame.allocString("bool_false", "false\\n");
		this.frame.allocString("string_newline", "\\n");

		// Parse functions for types
		for (Stmt s : program) {
			if (s instanceof Stmt.Function) {
				this.funcs.put(((Stmt.Function) s).name, (Stmt.Function) s);
			}
		}

		// Generate frames
		for (Stmt s : program) {
			s.accept(this);
		}

		// Output assembly
		String asm = "";
		for (AsmFrame f : this.frames) {
			asm += f.emit();
			asm += "\n";
		}

		return asm;
	}

	@Override
	public VarType visitBinaryExpr(Binary expr) {
		expr.left.accept(this);
		expr.right.accept(this);

		this.pop("$t1"); // Right
		this.pop("$t0"); // Left
		switch (expr.operator) {
			case AND:
				this.frame.asm("and $t2, $t0, $t1");
				break;
			case DIVIDE:
				this.frame.asm("div $t0, $t1");
				this.frame.asm("mflo $t2");
				break;
			case EQUAL:
				this.frame.asm("seq $t2, $t0, $t1");
				break;
			case GREATER:
				this.frame.asm("sgt $t2, $t0, $t1");
				break;
			case GREATER_EQUAL:
				this.frame.asm("sge $t2, $t0, $t1");
				break;
			case LESS:
				this.frame.asm("slt $t2, $t0, $t1");
				break;
			case LESS_EQUAL:
				this.frame.asm("sle $t2, $t0, $t1");
				break;
			case MINUS:
				this.frame.asm("sub $t2, $t0, $t1");
				break;
			case MULTIPLY:
				this.frame.asm("mult $t0, $t1");
				this.frame.asm("mflo $t2");
				break;
			case NOT_EQUAL:
				this.frame.asm("sne $t2, $t0, $t1");
				break;
			case OR:
				this.frame.asm("or $t2, $t0, $t1");
				break;
			case PLUS:
				this.frame.asm("add $t2, $t0, $t1");
				break;
			default:
				throw new Error("Invalid binary op");

		}
		this.push("$t2");

		return VarType.INT;
	}

	@Override
	public VarType visitCallExpr(Call expr) {
		// Load in args
		for (Expr e : expr.arguments) {
			e.accept(this);
		}
		// Call Function
		this.frame.asm("jal _" + expr.name);
		// Pop args
		for (int i = 0; i < expr.arguments.size(); i++) {
			this.pop("$t0");
		}
		// Push return value
		this.push("$v0");

		return this.funcs.get(expr.name).returnType;
	}

	@Override
	public VarType visitLiteralExpr(Literal expr) {
		if (expr.value instanceof Integer) {
			this.frame.asm("li $t0, " + (Integer) expr.value);
			this.push("$t0");
			return VarType.INT;
		} else if (expr.value instanceof Boolean) {
			Boolean v = (Boolean) expr.value;
			if (v) {
				this.frame.asm("li $t0, 1");
			} else {
				this.frame.asm("li $t0, 0");
			}
			this.push("$t0");
			return VarType.BOOL;
		} else {
			throw new Error("Unknown literal type");
		}
	}

	@Override
	public VarType visitUnaryExpr(Unary expr) {
		VarType t;
		expr.right.accept(this);
		this.pop("$t0");

		switch (expr.operator) {
			case MINUS:
				this.frame.asm("li $t1, 0");
				t = VarType.INT;
				break;
			case NOT:
				this.frame.asm("li $t1, 1");
				t = VarType.BOOL;
				break;
			default:
				throw new Error("Unknown unary op");
		}
		this.frame.asm("sub $t2, $t1, $t0");
		this.push("$t2");

		return t;
	}

	@Override
	public VarType visitVariableExpr(Variable expr) {
		this.frame.asm("lw $t0, " + this.frame.getVar(expr.name));
		this.push("$t0");
		return this.frame.getVarType(expr.name);
	}

	@Override
	public Void visitAssignStmt(Assign stmt) {
		stmt.value.accept(this);
		this.pop("$t0");
		this.frame.asm("sw $t0, " + this.frame.getVar(stmt.name));
		return null;
	}

	@Override
	public Void visitBlockStmt(Block stmt) {
		this.frame = new AsmFrame(this.frame);

		for (Stmt s : stmt.statements) {
			s.accept(this);
		}

		this.frame = this.frame.endBlock();

		return null;
	}

	@Override
	public Void visitExpressionStmt(Expression stmt) {
		stmt.expression.accept(this);
		this.pop("$t0");

		return null;
	}

	@Override
	public Void visitFunctionStmt(Function stmt) {
		this.frames.add(new AsmFrame(stmt, this.frame));
		this.frame = this.frames.getLast();

		for (Stmt s : stmt.body) {
			s.accept(this);
		}

		this.frame = this.frames.getFirst();

		return null;
	}

	@Override
	public Void visitIfStmt(If stmt) {
		stmt.condition.accept(this);
		this.pop("$t0");
		this.frame.asm("li $t1, 0");

		String elseLabel = this.frame.getLabel("else");
		String finalLabel = this.frame.getLabel("final");

		// Branch to else if we're false (== 0)
		this.frame.asm("beq $t0, $t1, " + elseLabel);
		// Continue through body if otherwise, and branch to final
		stmt.thenBranch.accept(this);
		this.frame.asm("j " + finalLabel);
		// Else branch
		this.frame.putLabel(elseLabel);
		if (stmt.elseBranch != null) {
			stmt.elseBranch.accept(this);
		}
		// Exit
		this.frame.putLabel(finalLabel);

		return null;
	}

	@Override
	public Void visitPrintStmt(Print stmt) {
		VarType t = stmt.expression.accept(this);

		if (t == VarType.INT) {
			this.pop("$a0");
			this.frame.asm("li $v0, 1");
			this.frame.asm("syscall");
			this.frame.asm("la $a0, _string_newline");
			this.frame.asm("li $v0, 4");
			this.frame.asm("syscall");
		} else if (t == VarType.BOOL) {
			this.pop("$t0");
			this.frame.asm("li $t1, 1");
			this.frame.asm("la $a0, _bool_true");
			String label = this.frame.getLabel("print_true");
			this.frame.asm("beq $t0, $t1, " + label);
			this.frame.asm("la $a0, _bool_false");
			this.frame.putLabel(label);
			this.frame.asm("li $v0, 4");
			this.frame.asm("syscall");
		} else {
			throw new Error("Unknown var type for print");
		}

		return null;
	}

	@Override
	public Void visitReturnStmt(Return stmt) {
		if (stmt.value != null) {
			stmt.value.accept(this);
			this.pop("$v0");
			this.frame.asm("j " + this.frame.getReturnLabel());
		}
		return null;
	}

	@Override
	public Void visitVarStmt(Var stmt) {
		this.frame.allocVar(stmt.name, stmt.type);
		if (stmt.initializer != null) {
			stmt.initializer.accept(this);
			this.pop("$t0");
			this.frame.asm("sw $t0, " + this.frame.getVar(stmt.name));
		}

		return null;
	}

	@Override
	public Void visitWhileStmt(While stmt) {
		String whileLabel = this.frame.getLabel("while");
		String finalLabel = this.frame.getLabel("final");

		this.frame.putLabel(whileLabel);
		stmt.condition.accept(this);
		this.pop("$t0");
		this.frame.asm("li $t1, 0");
		this.frame.asm("beq $t0, $t1, " + finalLabel);

		stmt.body.accept(this);
		this.frame.asm("j " + whileLabel);

		this.frame.putLabel(finalLabel);

		return null;
	}

	public void pop(String reg) {
		this.frame.asm("lw " + reg + ", 4($sp)");
		this.frame.asm("addu $sp, $sp, 4");
	}

	public void push(String reg) {
		this.frame.asm("sw " + reg + ", ($sp)");
		this.frame.asm("subu $sp, $sp, 4");
	}
}
