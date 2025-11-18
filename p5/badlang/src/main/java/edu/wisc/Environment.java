package edu.wisc;

import java.util.HashMap;
import java.util.Map;

class Environment {
	private final Map<String, Stmt.Function> functions = new HashMap<>();
	private final Map<String, Object> values = new HashMap<>();
	private final Environment parent;

	// Constructor for global environment
	Environment() {
		this.parent = null;
	}

	// Constructor for local environment with parent
	Environment(Environment parent) {
		this.parent = parent;
	}

	public Environment getParent() {
		return this.parent;
	}

	// Define a variable in this environment
	void defineVar(String name, Object value, int line) {
		// We don't check if a parent defines a variable, because I
		// think shadowing is cool.
		if (values.containsKey(name))
			throw new BadlangError("Variable '" + name + "' is already defined in this scope.", line);
		values.put(name, value);
	}

	// Get a variable value, checking parent environments if not found locally
	Object getVar(String name, int line) {
		if (values.containsKey(name)) {
			return values.get(name);
		}

		if (parent != null) {
			return parent.getVar(name, line);
		}

		throw new BadlangError("Undefined variable '" + name + "'.", line);
	}

	// Assign to an existing variable, searching parent environments if needed
	void assignVar(String name, Object value, int line) {
		if (values.containsKey(name)) {
			values.put(name, value);
			return;
		}

		if (parent != null) {
			parent.assignVar(name, value, line);
			return;
		}

		throw new BadlangError("Undefined variable '" + name + "'.", line);
	}

	void defineFun(String name, Stmt.Function fun) {
		if (parent != null)
			parent.defineFun(name, fun);
		else {
			if (functions.containsKey(name))
				throw new BadlangError("Function '" + name + "' is already defined", fun.line);
			functions.put(name, fun);
		}
	}

	Stmt.Function getFun(String name, int line) {
		if (parent != null)
			return parent.getFun(name, line);
		else {
			if (functions.containsKey(name))
				return functions.get(name);

			throw new BadlangError("Unknown Function '" + name + "'", line);
		}
	}
}
