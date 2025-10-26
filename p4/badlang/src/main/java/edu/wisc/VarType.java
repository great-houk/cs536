package edu.wisc;

public enum VarType {
	INT,
	BOOL;

	public static VarType fromToken(Token t) {
		switch (t.type) {
			case INT:
				return VarType.INT;
			case BOOL:
				return VarType.BOOL;
			default:
				throw new BadlangError(null, t.line, t.column);
		}
	}

	public Class<?> toType() {
		switch (this) {
			case INT:
				return Integer.class;
			case BOOL:
				return Boolean.class;
			default:
				throw new IllegalStateException("Unknown VarType: " + this);
		}
	}

	public String getName() {
		switch (this) {
			case INT:
				return "int";
			case BOOL:
				return "bool";
			default:
				return "<<ERROR UNKNOWN>>";
		}
	}
}
