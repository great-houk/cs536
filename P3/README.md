# P3: Badlang Lexer and Parser

## Overview

In this assignment, you will implement a **lexer** (tokenizer) and **parser** for the badlang programming language.

## How to Build and Run

To build the project, run the following command:

```bash
javac -d target/classes src/main/java/edu/wisc/*.java
```

To run the project, run the following command:

```bash
java -cp target/classes edu.wisc.Main <filename>
```

For example:

```bash
java -cp target/classes edu.wisc.Main test_programs/factorial.bl
```

## Learning Goals

This assignment has several primary learning objectives:

### 1. **Lexing**
You will implement a complete lexer that:
- Recognizes and categorizes tokens (keywords, identifiers, literals, operators, punctuation)
- Handles whitespace and comments
- Reports lexical errors with helpful messages
- Tracks source locations for error reporting

### 2. **Parsing**
You'll implement a parser that:
- Consumes tokens from the lexer
- Builds an Abstract Syntax Tree (AST)
- Handles operator precedence and associativity correctly
- Reports syntax errors with meaningful messages

### 3. **Design a Context-Free Grammar**
You'll define a complete CFG for badlang that:
- Specifies the language syntax unambiguously
- Handles all language constructs (expressions, statements, functions)
- Documents operator precedence and associativity
- Serves as the blueprint for your parser implementation

## What is badlang?

badlang is a simple programming language with the following features.
A few notes first:
  - badlang does not allow nested function declarations, i.e., a function declared inside a function
  - badlang instructions end with a semicolon

### **Types**
- `int`: 32-bit signed integers
- `bool`: Boolean values (`true` and `false`)

### **Variables**
```badlang
int x = 42;
bool flag = true;
int y;  // Declaration without initialization
```

### **Functions**
```badlang
fun int factorial(int n) {
  if (n <= 1) {
    return 1;
  } else {
    return n * factorial(n - 1);
  }
}
```

### **Control Flow**
```badlang
// If statements
if (x > 0) {
  print x;
} else {
  print 0;
}

// While loops
while (x > 0) {
  print x;
  x = x - 1;
}
```

### **Expressions**
```badlang
// Arithmetic
int result = (a + b) * c - d / e;

// Logical
bool valid = (x > 0) && (y < 100) || (z == 0);

// Function calls
int answer = factorial(5);
```

## Your Task

### 1. **Define the Grammar** (`grammar.cfg`)
Write a complete Context-Free Grammar for badlang. Your CFG should:
- List all terminal symbols (tokens)
- Define production rules for all non-terminals
- Be unambiguous and complete

You can write this **informally** using standard notation from class/book:
- Use `|` for alternatives
- Use `*` for zero-or-more repetition

### 2. **Implement the Token Class** (`Token.java`)
Design and implement a Token class that represents a single token. Consider what information each token needs:
- Token type (keyword, identifier, literal, operator, etc.)
- Lexeme (the actual text)
- Literal value (for number and boolean literals)
- Source location (line number, column) for error reporting

### 3. **Implement the Lexer** (`Lexer.java`)
Implement a complete lexer that:
- Takes source code as input (String)
- Returns a list of tokens
- Recognizes all tokens in the language:
  - **Keywords**: `int`, `bool`, `fun`, `if`, `else`, `while`, `return`, `print`, `true`, `false`
  - **Identifiers**: variable and function names (start with letter or underscore, followed by letters, digits, or underscores)
  - **Literals**: integer numbers, boolean values
  - **Operators**: `+`, `-`, `*`, `/`, `==`, `!=`, `<`, `<=`, `>`, `>=`, `&&`, `||`, `!`
  - **Punctuation**: `(`, `)`, `{`, `}`, `,`, `;`, `=`
- Handles whitespace (spaces, tabs, newlines)
- Optionally handles comments (you can design your own comment syntax)
- Reports lexical errors clearly
- Tracks line numbers for error messages

**Tips:**
- Use a cursor/pointer to track your position in the source code
- Process characters one at a time
- Use helper methods for different token types
- Handle edge cases (EOF, invalid characters, etc.)
- Feel free to use a lexer generator like JFlex.

### 4. **Implement the Parser** (`Parser.java`)
Implement a recursive descent parser that:
- Takes a list of tokens from the lexer
- Builds an AST using the provided `Expr` and `Stmt` classes
- Follows your CFG from `grammar.cfg`
- Handles operator precedence correctly
- Reports syntax errors with meaningful messages (e.g., "Expected ';' after variable declaration")

Alternatively, you may use a parser generator like antlr. Note that antlr can additionally generate a lexer.

### 5. **Test Your Implementation** (`Main.java`)
Implement `Main.java` to read badlang source code from a file and process it through your lexer and parser.

**Requirements:**
- Your `main` method should accept a filename as a command-line argument: `java Main <filename>`
- Read the entire source file into a String
- Pass the source code to your Lexer to get tokens
- Pass the tokens to your Parser to build an AST
- Print out the tokens and/or AST structure (or use a pretty-printer)
- Handle file I/O errors gracefully (file not found, read errors, etc.)

**Example Usage:**
```bash
java edu.wisc.Main test_programs/factorial.bl
```

**Testing Strategy:**
Create multiple test files (`.bl` extension suggested) covering:
1. Basic expressions: `42`, `true`, `x + 5`
2. Each statement type individually
3. Operator precedence: does `2 + 3 * 4` parse correctly?
4. Complete programs (see examples below)
5. Error cases: syntax errors, unexpected tokens, etc.

You should create at least 5-10 test files demonstrating different language features.

## Implementation Requirements

1. Your implementation should handle **all** language features described above
2. Your code should produce **clear, informative error messages**
3. Feel free to add additional helper classes or methods as needed
4. **Feel free to modify any provided classes** (Expr, Stmt, etc.) if needed for your implementation

## Provided Code

You are provided with:
- **AST Classes** (`Expr.java`, `Stmt.java`): Complete AST node definitions using the visitor pattern
- **Supporting Classes** (`Operator.java`, `VarType.java`): Enums for operators and types
- **Skeleton Files** (`Token.java`, `Lexer.java`, `Parser.java`): Files you need to implement
- **Main Class** (`Main.java`): Entry point for testing with example code structure
- **Example Test Files** (`test_programs/*.bl`): Sample badlang programs to help you get started

## Deliverables

Submit a zip file of the `P3` folder containing:

1. **`grammar.cfg`**: Your complete CFG for badlang
2. **`Token.java`**: Your Token implementation
3. **`Lexer.java`**: Your Lexer implementation
4. **`Parser.java`**: Your Parser implementation
5. **`Main.java`**: Your Main class that reads source files and runs lexer/parser
6. **Test files**: At least 5-10 `.bl` test files demonstrating different language features
7. **Any additional files** you created (helper classes, utilities, etc.)
8. **Documentation**: Add any documentation to the top of this README.md file

**If you worked with a partner, only one of you needs to submit; include their name at the top of the README.md (this file)**

## Example Programs

### **Basic Arithmetic**
```badlang
int a = 10;
int b = 5;
int sum = a + b;
print sum;  // Should parse correctly
```

### **Control Flow**
```badlang
int x = 5;
while (x > 0) {
  print x;
  x = x - 1;
}
// Should parse correctly
```

### **Function with Recursion**
```badlang
fun int factorial(int n) {
  if (n <= 1) {
    return 1;
  } else {
    return n * factorial(n - 1);
  }
}

int result = factorial(5);
print result;  // Should parse correctly
```

### **Operator Precedence**
```badlang
int x = 2 + 3 * 4;        // Should parse as 2 + (3 * 4)
bool b = true || false && false;  // Should parse as true || (false && false)
```
## Grading Rubric

Your assignment will be graded on:

1. **Grammar (20%)**
   - Completeness: covers all language features
   - Correctness: unambiguous and accurate
   - Clarity: well-documented and understandable

2. **Lexer (30%)**
   - Correctness: recognizes all tokens correctly
   - Completeness: handles all token types
   - Error handling: reports lexical errors clearly
   - Code quality: clean, well-organized code

3. **Parser (40%)**
   - Correctness: builds correct ASTs for valid programs
   - Completeness: handles all language constructs
   - Precedence: handles operator precedence correctly
   - Error handling: reports syntax errors clearly
   - Code quality: clean, well-organized code

4. **Testing (10%)**
   - Coverage: test files cover all language features
   - Quantity: at least 5-10 well-designed test files
   - Edge cases: tests boundary conditions and errors
   - Main.java: correctly reads files and and processes them through lexer/parser


## Optional Enhancement: Hook Up Your Interpreter

If you completed **P2** (the interpreter assignment), you can optionally integrate your interpreter with this assignment to create a complete compiler pipeline:

**Lexer → Parser → Interpreter**

This allows you to not just parse badlang programs, but actually **execute** them!

### How to Integrate:

1. **Copy your interpreter code** from P2:
   - Bring in `Interpreter.java` and `Environment.java` from your P2 submission
   - Place them in the same `edu.wisc` package

2. **Update Main.java** to run the interpreter after parsing:

3. **Test end-to-end**:
   - Your test files should now execute and produce output
   - Compare the actual output to expected output
   - Example: `factorial.bl` should actually print `120`

### Benefits:

- See your parser work in action with real execution
- Catch parsing bugs by observing runtime behavior
- Build a complete, working programming language!

**Note:** This is completely optional and won't affect your P3 grade. It's just a fun way to see your work come together!

Good luck! 🚀
