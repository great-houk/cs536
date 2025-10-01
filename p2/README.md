# P2: Badlang Interpreter

## Overview

In this assignment, you will implement an **interpreter** for the badlang programming language.

## Learning Goals

This assignment has several primary learning objectives:

### 1. **Master Expression Evaluation**
You will implement the core evaluation logic for all expression types:
- **Literals**: Numbers and boolean values
- **Variables**: Looking up values in the environment
- **Binary Operations**: Arithmetic, comparison, and logical operations
- **Unary Operations**: Negation and logical NOT
- **Function Calls**: Invoking functions with arguments

### 2. **Understand Statement Execution**
You'll implement execution logic for all statement types:
- **Variable Declarations**: Creating and initializing variables
- **Assignments**: Updating variable values
- **Control Flow**: `if` statements and `while` loops
- **Function Definitions**: Creating callable functions
- **Expression statement**: Evaluating a statement expression, e.g., `f(x)`, which may update global variables
- **Print Statements**: Outputting values
- **Return Statements**: Exiting functions with values

### 3. **Learn Runtime Concepts**
You'll implement fundamental interpreter concepts:
- **Environment Management**: Variable scoping and storage
- **Type Checking**: Runtime type validation
- **Error Handling**: Graceful runtime error reporting

## What is badlang?

badlang is a simple programming language with the following features.
A few notes first:
  - badlang does not allow nested function declarations, i.e., a function declared inside a function
  - badlang passes variables by value, not by reference.

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

1. You need to implement the **complete interpreter** by filling in all the TODO items in `Interpreter.java`. You will also need to write the `Environment` class in `Environment.java`. The interpreter will use the environment to track the values of variables.
2. You will add a number of tests -- you can do this in `Main.java` or separately in a new file -- that exercise different language features and corner cases.
3. Your interpreter will handle runtime errors. One of the goals of the assignment is for you to think of all the little things that can go wrong.  
4. *Feel free to modify provided classes as you see fit.*
  
**any documentation you would like to provide, simply add it to the top of this README.md file**

**submit a zip of this folder**

**if you worked with a partner, only one of you needs to submit; include their name at the top of the README.md (this file)**

## Example Programs

### **Basic Arithmetic**
```badlang
int a = 10;
int b = 5;
int sum = a + b;
print sum;  // Should print: 15
```

### **Control Flow**
```badlang
int x = 5;
while (x > 0) {
  print x;
  x = x - 1;
}
// Should print: 5, 4, 3, 2, 1
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
print result;  // Should print: 120
```

### **Nested Scopes**
```badlang
int x = 10;
print x;  // Prints: 10
{
  int x = 20;
  print x;  // Prints: 20
}
print x;  // Prints: 10
```

## Bonus (+20%)
You will receive bonus credit for this assignment if you add arrays to badlang.
This involves enriching the AST and extending the interpreter to handle arrays.
The expectation is that arrays will be declared as `int[] arr = [1,2,3];` (similarly, bool), and that one can access array elements using, e.g.,  `arr[1]`. You're only expected to handle single-dimensional arrays, but feel free to go crazy.