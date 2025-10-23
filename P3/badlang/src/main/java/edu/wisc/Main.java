package edu.wisc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

//to run the tests, can either enter --tests or you can runa a specific file

//to run a specific file, just enter the path to the file from P3 folder.





public class Main { 


    public static void main(String[] args) {//use the main method as a CLI
        Scanner scanner = new Scanner(System.in);

        if (args.length == 0) {
            System.out.println("Welcome to Badlang CLI!");
            System.out.println("Enter '--tests' to run all the tests I put together when working on the parser, or enter a file path to parse:");
            System.out.print("> ");

            String input = scanner.nextLine().trim();  // Waits for user input

            if ("--tests".equals(input)) {
                runAllTests();
            } else {
                try {
                    String source = Files.readString(Path.of(input));
                    parseSourceAndPrint(source);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }

            scanner.close();
            return;
        }

   
    if (args.length == 1) {
        if ("--tests".equals(args[0])) {
            runAllTests();
        } else {
            try {
                String source = Files.readString(Path.of(args[0]));
                parseSourceAndPrint(source);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    } else {
        System.err.println("Usage: java edu.wisc.Main [--tests | <filename>]");
    }
}


    //helper methods, have the one that summarizes a parsed text, and also the method to run all the tests

    //parse a source string and print a summary of the AST
    private static void parseSourceAndPrint(String source) {
        CharStream input = CharStreams.fromString(source);
        BadlangLexer lexer = new BadlangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BadlangParser parser = new BadlangParser(tokens);

        // checking for syntax errors
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) {
                throw new RuntimeException("Syntax error at " + line + ":" + charPositionInLine + " " + msg);
            }
        });

        ParseTree tree = parser.program();
        ASTBuilder builder = new ASTBuilder();

        @SuppressWarnings("unchecked")
        List<Stmt> program = (List<Stmt>) builder.visit(tree);

        System.out.println("Number of top-level statements: " + program.size());
        System.out.println("Details of each statement:");
        for (Stmt s : program) {
            System.out.println(s.getClass().getSimpleName());
            if (s instanceof Stmt.Var v) {
                System.out.println("  Var name: " + v.name);
                if (v.initializer != null)
                    System.out.println("  Init expr: " + v.initializer.getClass().getSimpleName());
                else
                    System.out.println("  Init expr: null");
            } else if (s instanceof Stmt.Print p) {
                System.out.println("  Print expr: " + p.expression.getClass().getSimpleName());
            }
        }
    }

    // this is all my original tests I made when I was making the parser
    private static void runAllTests() {
        // ---------- TEST 1 ----------
        try {
            System.out.println("----------TEST 1----------");
            System.out.println("This test parses one line, 'int x = 3 + 5; print(x);'");
            System.out.println("The expected output is two top-level statements: a variable declaration and a print statement.");

            String source = "int x = 3 + 5; print(x);";
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 2 ----------
        try {
            System.out.println("----------TEST 2----------");
            System.out.println("This test parses one line, 'print(10 * (2 + 3));'");
            System.out.println("The expected output is one top-level statement: a print statement.");

            String source = "print(10 * (2 + 3));";
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 3: arithmetic.bl ----------
        try {
            System.out.println("----------TEST 3----------");
            System.out.println("This test parses the contents of 'arithmetic.bl'.");
            System.out.println("Expected output: should have 8 top level statements, 5 vars and 3 prints.");

            String source = Files.readString(Path.of("test_programs/arithmetic.bl"));
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error reading or parsing 'arithmetic.bl': " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 4: factorial.bl ----------
        try {
            System.out.println("----------TEST 4----------");
            System.out.println("This test parses the contents of 'factorial.bl'.");
            System.out.println("Expected output: should have 3 top level statements, a function, then a var declaration, and a print.");

            String source = Files.readString(Path.of("test_programs/factorial.bl"));
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error reading or parsing 'factorial.bl': " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 5: loops.bl ----------
        try {
            System.out.println("----------TEST 5----------");
            System.out.println("This test parses the contents of 'loops.bl'.");
            System.out.println("Expected output: should have 3 top level statements, a variable, a while, and a print.");

            String source = Files.readString(Path.of("test_programs/loops.bl"));
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error reading or parsing 'loops.bl': " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 6: precedence.bl ----------
        try {
            System.out.println("----------TEST 6----------");
            System.out.println("This test parses the contents of 'precedence.bl'.");
            System.out.println("Expected output: should have 6 top level statements, 4 variables, and 2 prints.");

            String source = Files.readString(Path.of("test_programs/precedence.bl"));
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error reading or parsing 'precedence.bl': " + e.getMessage());
            e.printStackTrace();
        }

        // ---------- TEST 7: simple.bl ----------
        try {
            System.out.println("----------TEST 7----------");
            System.out.println("This test parses the contents of 'simple.bl'.");
            System.out.println("Expected output: should have 2 top level statements, 1 variable and 1 print.");

            String source = Files.readString(Path.of("test_programs/simple.bl"));
            parseSourceAndPrint(source);

        } catch (Exception e) {
            System.err.println("Error reading or parsing 'simple.bl': " + e.getMessage());
            e.printStackTrace();
        }
    }
}
