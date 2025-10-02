package edu.wisc;

import java.util.List;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		// Create an interpreter instance
		Interpreter interpreter = new Interpreter();

		System.out.println("=== Testing Basic Arithmetic ===");
		testBasicArithmetic(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing Variables ===");
		testVariables(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing Control Flow ===");
		testControlFlow(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing Functions ===");
		testFunctions(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing Nested Scopes ===");
		testNestedScopes(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing While Loops ===");
		testWhileLoops(interpreter);
		interpreter.clear_env();

		System.out.println("\n=== Testing Recursion ===");
		testRecursion(interpreter);
		interpreter.clear_env();
	}

	private static void testBasicArithmetic(Interpreter interpreter) {
		// Test: int a = 10; int b = 5; print a + b;
		List<Stmt> statements = Arrays.asList(
				new Stmt.Var("a", VarType.INT, new Expr.Literal(10)),
				new Stmt.Var("b", VarType.INT, new Expr.Literal(5)),
				new Stmt.Print(new Expr.Binary(
						new Expr.Variable("a"),
						Operator.PLUS,
						new Expr.Variable("b"))));
		interpreter.interpret(statements);
	}

	private static void testVariables(Interpreter interpreter) {
		// Test: int x = 42; bool flag = true; print x; print flag;
		List<Stmt> statements = Arrays.asList(
				new Stmt.Var("x", VarType.INT, new Expr.Literal(42)),
				new Stmt.Var("flag", VarType.BOOL, new Expr.Literal(true)),
				new Stmt.Print(new Expr.Variable("x")),
				new Stmt.Print(new Expr.Variable("flag")));
		interpreter.interpret(statements);
	}

	private static void testControlFlow(Interpreter interpreter) {
		// Test: int x = 5; if (x > 0) { print x; } else { print 0; }
		List<Stmt> statements = Arrays.asList(
				new Stmt.Var("x", VarType.INT, new Expr.Literal(5)),
				new Stmt.If(
						new Expr.Binary(
								new Expr.Variable("x"),
								Operator.GREATER,
								new Expr.Literal(0)),
						new Stmt.Print(new Expr.Variable("x")),
						new Stmt.Print(new Expr.Literal(0))));
		interpreter.interpret(statements);
	}

	private static void testFunctions(Interpreter interpreter) {
		// Test: fun int add(int a, int b) { return a + b; } print add(3, 4);
		List<Stmt> statements = Arrays.asList(
				new Stmt.Function(
						"add",
						VarType.INT,
						Arrays.asList(
								new Stmt.Parameter("a", VarType.INT),
								new Stmt.Parameter("b", VarType.INT)),
						Arrays.asList(
								new Stmt.Return(new Expr.Binary(
										new Expr.Variable("a"),
										Operator.PLUS,
										new Expr.Variable("b"))))),
				new Stmt.Print(new Expr.Call("add", Arrays.asList(
						new Expr.Literal(3),
						new Expr.Literal(4)))));
		interpreter.interpret(statements);
	}

	private static void testNestedScopes(Interpreter interpreter) {
		// Test: int x = 10; { int x = 20; print x; } print x;
		List<Stmt> statements = Arrays.asList(
				new Stmt.Var("x", VarType.INT, new Expr.Literal(10)),
				new Stmt.Print(new Expr.Variable("x")), // Should print 10
				new Stmt.Block(Arrays.asList(
						new Stmt.Var("x", VarType.INT, new Expr.Literal(20)),
						new Stmt.Print(new Expr.Variable("x")) // Should print 20
				)),
				new Stmt.Print(new Expr.Variable("x")) // Should print 10
		);
		interpreter.interpret(statements);
	}

	private static void testWhileLoops(Interpreter interpreter) {
		// Test: int i = 3; while (i > 0) { print i; i = i - 1; }
		List<Stmt> statements = Arrays.asList(
				new Stmt.Var("i", VarType.INT, new Expr.Literal(3)),
				new Stmt.While(
						new Expr.Binary(
								new Expr.Variable("i"),
								Operator.GREATER,
								new Expr.Literal(0)),
						new Stmt.Block(Arrays.asList(
								new Stmt.Print(new Expr.Variable("i")),
								new Stmt.Assign("i", new Expr.Binary(
										new Expr.Variable("i"),
										Operator.MINUS,
										new Expr.Literal(1)))))));
		interpreter.interpret(statements);
	}

	private static void testRecursion(Interpreter interpreter) {
		// Test: fun int factorial(int n) { if (n <= 1) return 1; else return n * factorial(n - 1); }
		List<Stmt> statements = Arrays.asList(
				new Stmt.Function(
						"factorial",
						VarType.INT,
						Arrays.asList(new Stmt.Parameter("n", VarType.INT)),
						Arrays.asList(
								new Stmt.If(
										new Expr.Binary(
												new Expr.Variable("n"),
												Operator.LESS_EQUAL,
												new Expr.Literal(1)),
										new Stmt.Return(new Expr.Literal(1)),
										new Stmt.Return(new Expr.Binary(
												new Expr.Variable("n"),
												Operator.MULTIPLY,
												new Expr.Call("factorial", Arrays.asList(
														new Expr.Binary(
																new Expr.Variable("n"),
																Operator.MINUS,
																new Expr.Literal(1))))))))),
				new Stmt.Print(new Expr.Call("factorial", Arrays.asList(new Expr.Literal(5)))));
		interpreter.interpret(statements);
	}
}