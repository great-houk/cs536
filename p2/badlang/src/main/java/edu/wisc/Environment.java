package edu.wisc;

import java.util.HashMap;
import java.util.Map;

class Environment {
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

	// Define a variable in this environment
	void define(String name, Object value) {
		// We don't check if a parent defines a variable, because I
		// think shadowing is cool.
		if (values.containsKey(name))
			throw new RuntimeException("Variable '" + name + "' is already defined in this scope.");
		values.put(name, value);
	}

	// Get a variable value, checking parent environments if not found locally
	Object get(String name) {
		if (values.containsKey(name)) {
			return values.get(name);
		}

		if (parent != null) {
			return parent.get(name);
		}

		throw new RuntimeException("Undefined variable '" + name + "'.");
	}

	// Assign to an existing variable, searching parent environments if needed
	void assign(String name, Object value) {
		if (values.containsKey(name)) {
			values.put(name, value);
			return;
		}

		if (parent != null) {
			parent.assign(name, value);
			return;
		}

		throw new RuntimeException("Undefined variable '" + name + "'.");
	}

	// Check if a variable is defined in this environment or parent environments
	boolean isDefined(String name) {
		return values.containsKey(name) || (parent != null && parent.isDefined(name));
	}
}
