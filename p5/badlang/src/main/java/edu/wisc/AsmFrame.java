package edu.wisc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class AsmFrame {
	private final Map<String, Var> statics;
	private final Map<String, Var> locals;
	private final Map<String, Var> params;
	private final boolean isStatic;
	private final AsmFrame parent;
	private final String name;
	private Integer nestCount;
	private String asm = "";
	private int labelCount;

	private static class Var {
		public int ind;
		public String ref;
		public VarType t;

		Var(int ind, VarType t) {
			this.ind = ind;
			this.t = t;
		}

		Var(String ref, VarType t) {
			this.ref = ref;
			this.t = t;
		}
	}

	public AsmFrame() {
		this.statics = new HashMap<>();
		this.locals = new HashMap<>();
		this.params = new HashMap<>();
		this.nestCount = 0;
		this.parent = null;
		this.name = "main";
		this.labelCount = 0;
		this.isStatic = true;
	}

	public AsmFrame(Stmt.Function fun, AsmFrame parent) {
		this.statics = parent.statics;
		this.locals = new HashMap<>();
		this.params = new HashMap<>();
		this.name = "_" + fun.name;
		this.parent = null;
		this.nestCount = 0;
		this.labelCount = 0;
		this.isStatic = false;

		for (int i = 0; i < fun.params.size(); i++) {
			this.params
					.put(fun.params.get(i).name(), new Var(4 * (fun.params.size() - i), fun.params.get(i).type()));
		}
	}

	public AsmFrame(AsmFrame parent) {
		this.statics = parent.statics;
		this.locals = new HashMap<>();
		this.params = parent.params;
		this.parent = parent;
		this.labelCount = parent.labelCount;
		this.nestCount = parent.nestCount + 1;
		this.name = parent.name;
		this.asm = parent.asm + "\t# Block " + this.nestCount + "\n";
		this.isStatic = false;
	}

	public void allocString(String name, String value) {
		if (!this.isStatic) {
			throw new Error("Can't allocate string in function!");
		} else {
			this.statics.put(name, new Var(".asciiz \"" + value + "\"", null));
		}
	}

	public void allocVar(String name, VarType t) {
		if (this.isStatic) {
			this.statics.put(name, new Var(".space 4", t));
		} else if (this.parent != null) {
			this.locals.put(this.nestCount + name, new Var(-8 - 4 * this.locals.size(), t));
		} else {
			this.locals.put(name, new Var(-8 - 4 * this.locals.size(), t));
		}
	}

	public String getLabel(String name) {
		return name + "_" + this.name + this.labelCount++;
	}

	public void putLabel(String label) {
		this.asm += label + ":\n";
	}

	public String getReturnLabel() {
		return "return_" + this.name;
	}

	public String getVar(String name) {
		if (this.locals.containsKey(name)) {
			return this.locals.get(name).ind + "($fp)";
		} else if (this.locals.containsKey(this.nestCount + name)) {
			return this.locals.get(this.nestCount + name).ind + "($fp)";
		} else if (parent != null && parent.locals.containsKey(name)) {
			return parent.locals.get(name).ind + "($fp)";
		} else if (this.params.containsKey(name)) {
			return this.params.get(name).ind + "($fp)";
		} else if (this.statics.containsKey(name)) {
			return "_" + name;
		} else {
			throw new Error("Unknown variable '" + name + "'");
		}
	}

	public VarType getVarType(String name) {
		if (this.locals.containsKey(name)) {
			return this.locals.get(name).t;
		} else if (this.locals.containsKey(this.nestCount + name)) {
			return this.locals.get(this.nestCount + name).t;
		} else if (parent != null && parent.locals.containsKey(name)) {
			return parent.locals.get(name).t;
		} else if (this.params.containsKey(name)) {
			return this.params.get(name).t;
		} else if (this.statics.containsKey(name)) {
			return this.statics.get(name).t;
		} else {
			throw new Error("Unknown variable '" + name + "'");
		}
	}

	public void asm(String asm) {
		this.asm += "\t" + asm + "\n";
	}

	public String emit() {
		if (parent == null) {
			String ret = "";

			if (this.isStatic) {
				// We're main, so we should add statics and .data and .text
				ret += ".data\n";
				for (Entry<String, Var> e : this.statics.entrySet()) {
					ret += ".align 2\n";
					ret += "_" + e.getKey() + ":\t\t" + e.getValue().ref + "\n";
				}

				ret += """
						\n.text
						.globl main

						""";
			}

			ret += this.name + ":\n";

			// Generate preamble
			ret += """
					\t# Preamble:
					\tsw $ra, 0($sp)
					\tsubu $sp, $sp, 4
					\tsw $fp, 0($sp)
					\tsubu $sp, $sp, 4
					\taddu $fp, $sp, 8
					\tsubu $sp, $sp,""";
			ret += " " + this.locals.size() * 4 + "\n";

			// Put in body
			ret += "\t# Body:\n" + this.asm;

			// Function exit
			ret += this.getReturnLabel() + ":\n";
			ret += """
					\t# Exit:
					\tlw $ra, 0($fp)
					\tmove $t0, $fp
					\tlw $fp, -4($fp)
					\tmove $sp, $t0
					\tjr $ra
					""";

			return ret;
		} else {
			throw new Error("Cant emit raw block!");
		}
	}

	public AsmFrame endBlock() {
		if (parent == null) {
			throw new Error("Not in a block!");
		}

		this.asm("# End of Block " + this.nestCount);
		this.parent.asm = this.asm;
		return this.parent;
	}
}
