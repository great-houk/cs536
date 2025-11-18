# P4: Badlang Name Analysis and Type Checking

## Overview

In this assignment, you will implement **name analysis** and **type checking** for the badlang programming language. These are two critical semantic analysis phases that catch errors that the parser cannot detect.

## Learning Goals

This assignment has several primary learning objectives:

### 1. **Name Analysis**
You will implement a complete name analyzer that:
- Tracks variable and function declarations
- Detects undefined variable/function references
- Detects duplicate declarations in the same scope
- Reports name-related errors with helpful messages

### 2. **Type Checking**
You will implement a type checker that:
- Assigns types to all expressions
- Verifies type compatibility for operations
- Checks function call argument types and counts
- Validates return statement types
- Detects type mismatches and reports them clearly

### 3. **Error Reporting**
One of the key design challenges is deciding how to report errors:
- Should you stop at the first error or continue checking?
- How do you report multiple errors clearly?
- What information should error messages include?
- How do you prevent cascading errors?


## Type System Rules

Your type checker should enforce the following rules:

### **Literals**
- Integer literals (e.g., `42`, `0`, `-5`) have type `int`
- Boolean literals (`true`, `false`) have type `bool`

### **Variables**
- Variables have the type specified in their declaration
- Example: `int x = 5;` means `x` has type `int`

### **Arithmetic Operators** (`+`, `-`, `*`, `/`)
- Both operands must be `int`
- Result type is `int`
- Error if either operand is not `int`

### **Comparison Operators** (`<`, `<=`, `>`, `>=`)
- Both operands must be `int`
- Result type is `bool`
- Error if either operand is not `int`

### **Equality Operators** (`==`, `!=`)
- Both operands must have the same type (`int` or `bool`)
- Result type is `bool`
- Error if operand types do not match or are not `int`/`bool`

### **Logical Operators** (`&&`, `||`)
- Both operands must be `bool`
- Result type is `bool`
- Error if either operand is not `bool`

### **Logical NOT** (`!`)
- Operand must be `bool`
- Result type is `bool`
- Error if operand is not `bool`

### **Unary Minus** (`-`)
- Operand must be `int`
- Result type is `int`
- Error if operand is not `int`

### **Variable Declarations**
- If an initializer is present, its type must match the declared type
- Example: `int x = true;` is a type error

### **Assignments**
- The right-hand side type must match the variable's declared type
- Example: If `x` is declared as `int`, then `x = true;` is a type error

### **If Statements**
- The condition must have type `bool`
- Error if condition is not `bool`

### **While Statements**
- The condition must have type `bool`
- Error if condition is not `bool`

### **Return Statements**
- The returned value's type must match the function's return type
- Example: In a function declared as `fun int f() {...}`, all return statements must return `int`

### **Function Calls**
- The number of arguments must match the number of parameters
- Each argument's type must match the corresponding parameter's type

### **Print Statements**
- The expression can be either `int` or `bool` (print accepts both)

## Name Analysis Rules

Your name analyzer should enforce the following rules:

### **Variable Declarations**
- A variable cannot be declared twice in the same scope
- A variable must be declared before it's used
- Variables in inner scopes can shadow variables in outer scopes

### **Function Declarations**
- A function cannot be declared twice (even in different scopes, since functions are global)
- Functions cannot be nested
- Function names cannot conflict with each other
- Functions can be used before they are declared -- e.g., mutual recursion.

### **Variable References**
- A variable must be declared before it's referenced
- Variable lookup follows lexical scoping rules

### **Function Calls**
- The function name must refer to a function, not a variable

### **Scoping**
- Each block (`{ ... }`) creates a new scope
- Function parameters are in the function's scope
- Variables declared in a scope are only visible in that scope and nested scopes

## Your Task

Implement name analysis and type checking for the badlang programming language.
- I'm leaving things open-ended for this assignment, since you are now experts in writing visitors for badlang.
- Feel free to combine name analysis and type checking as one visitor
- Remember, from class, that you can use something like the `Environment` class to help with this.
- Design a clear and helpful error reporting mechanism:
  - Each error should include the location (ideally line number)
  - Each error should include a descriptive message
  - Consider collecting all errors rather than stopping at the first one


### **Test Your Implementation**

Create comprehensive test programs covering:
- **Valid programs** that pass both name analysis and type checking
- **Name analysis errors**: undefined variables, duplicate declarations, scope violations
- **Type errors**: type mismatches, incorrect operation types, bad function calls

You should create at least 15-20 test programs demonstrating different scenarios.

You can write those tests as ASTs or as badlang source files and parse them with your P3 parser.

## Error Reporting: Design Decisions

One of the key aspects of this assignment is designing good error reporting. You're free to make your own design decisions here. Here are some questions to consider.

**Also, take inspiration from existing compilers, e.g., run javac, gcc, rustc, etc. and see how they report errors.**

### **Stop at First Error vs. Continue Checking?**

**Option 1: Stop at first error**
- Simpler to implement
- Less risk of cascading errors
- User sees one error at a time

**Option 2: Continue checking and collect all errors**
- More helpful to developers (see all problems at once)
- More complex to implement
- Need to handle cascading errors carefully
- Recommended approach for this assignment

### **How to Prevent Cascading Errors?**

If you find a type error in a subexpression, you might:
- Assign it an `ERROR` type
- Continue checking but don't report additional errors that result from the `ERROR` type
- Example: If `x` is undefined, don't also report a type error for `x + 5`

### **What Information to Include?**

Each error should include:
- **Error type**: Name error or type error
- **Location**: Line number (and column if available)
- **Message**: Clear description of what went wrong
- **Context**: What was expected vs. what was found

Example error messages:
```
Line 5: Name error: Undefined variable 'count'
Line 10: Type error: Cannot apply operator '+' to types 'int' and 'bool'
Line 15: Type error: Function 'factorial' expects 1 argument but got 2
Line 20: Name error: Variable 'x' is already declared in this scope
```

## Example Error Cases

### **Name Analysis Errors**

#### **Undefined Variable**
```badlang
int x = 5;
print y;  // Error: 'y' is not declared
```

#### **Duplicate Declaration**
```badlang
int x = 5;
int x = 10;  // Error: 'x' is already declared in this scope
```

#### **Undefined Function**
```badlang
int result = factorial(5);  // Error: 'factorial' is not declared
```

#### **Variable Used Before Declaration**
```badlang
print x;  // Error: 'x' is not declared
int x = 5;
```

#### **Scoping Example (Valid)**
```badlang
int x = 5;
{
  int x = 10;  // OK: shadows outer 'x'
  print x;     // Prints 10
}
print x;       // Prints 5
```

#### **Out of Scope**
```badlang
{
  int x = 5;
}
print x;  // Error: 'x' is not declared in this scope
```

### **Type Errors**

#### **Type Mismatch in Binary Operation**
```badlang
int x = 5;
bool b = true;
int result = x + b;  // Error: Cannot apply '+' to 'int' and 'bool'
```

#### **Wrong Condition Type**
```badlang
int x = 5;
if (x) {  // Error: Condition must be 'bool', found 'int'
  print x;
}
```

#### **Type Mismatch in Assignment**
```badlang
int x = 5;
x = true;  // Error: Cannot assign 'bool' to variable of type 'int'
```

#### **Wrong Return Type**
```badlang
fun int getValue() {
  return true;  // Error: Expected return type 'int', found 'bool'
}
```

#### **Function Call Type Errors**
```badlang
fun int add(int a, int b) {
  return a + b;
}

int result = add(5, true);  // Error: Argument 2 type mismatch: expected 'int', found 'bool'
```

#### **Wrong Number of Arguments**
```badlang
fun int add(int a, int b) {
  return a + b;
}

int result = add(5);  // Error: Function 'add' expects 2 arguments but got 1
```

#### **Logical Operator Type Error**
```badlang
int x = 5;
int y = 10;
bool result = x && y;  // Error: Operator '&&' requires 'bool' operands, found 'int'
```

#### **Comparison is Valid**
```badlang
int x = 5;
int y = 10;
bool result = x < y;  // OK: Comparison returns bool
```

## Deliverables

Submit a zip file of the `P4` folder containing your changes.
**If you worked with a partner, only one of you needs to submit; include both names at the top of the README.md**

## Grading Rubric

Your assignment will be graded on:

1. **Name Analysis (35%)**
   - Correctness: Detects all name errors correctly
   - Completeness: Handles all language constructs
   - Scoping: Implements proper lexical scoping
   - Code quality: Clean, well-organized code

2. **Type Checking (35%)**
   - Correctness: Detects all type errors correctly
   - Completeness: Handles all type rules
   - Function checking: Validates function calls properly
   - Type propagation: Correctly assigns types to expressions
   - Code quality: Clean, well-organized code

3. **Error Reporting (20%)**
   - Clarity: Error messages are clear and informative
   - Location: Errors include line numbers or other location info
   - Quality: Messages help users understand what went wrong

4. **Testing (10%)**
   - Coverage: Test programs cover all error cases
   - Quantity: At least 15-20 well-designed test programs
   - Organization: Tests are organized into clear categories
   - Edge cases: Tests cover boundary conditions

Good luck! 🚀

