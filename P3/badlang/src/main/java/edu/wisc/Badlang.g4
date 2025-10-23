grammar Badlang;


// =========================
// PARSER RULES
// =========================

options { visitor = true; }


program
    : (declaration | statement)* EOF
    ;

declaration
    : varDecl
    | funDecl
    ;

varDecl
    : type IDENTIFIER (ASSIGN expression)? SEMICOLON
    ;

type
    : INT               // 'int'
    | BOOL              // 'bool'
    ;

funDecl
    : FUN type IDENTIFIER LPAREN paramList? RPAREN block
    ;

paramList
    : param (COMMA param)*
    ;
param
    : type IDENTIFIER
    ;
    
statement
    : exprStmt
    | printStmt
    | ifStmt
    | whileStmt
    | returnStmt
    | block
    ;

exprStmt
    : expression SEMICOLON
    ;
    
printStmt
    : PRINT expression SEMICOLON
    | PRINT LPAREN expression RPAREN SEMICOLON
    ;


ifStmt
    : IF LPAREN expression RPAREN statement (ELSE statement)?
    ;

whileStmt
    : WHILE LPAREN expression RPAREN statement
    ;

returnStmt
    : RETURN expression? SEMICOLON
    ;

block
    : LBRACE (declaration | statement)* RBRACE
    ;

// ------- Expressions (precedence via stratification) -------

expression
    : assignment
    ;

// assignment is right-associative: a = b = c
assignment
    : logic_or
    | IDENTIFIER ASSIGN assignment
    ;

logic_or
    : logic_and (OR_OR logic_and)*
    ;

logic_and
    : equality (AND_AND equality)*
    ;

equality
    : comparison ((EQUAL_EQUAL | BANG_EQUAL) comparison)*
    ;

comparison
    : term ((LESS_THAN | GREATER_THAN | LESS_EQUAL | GREATER_EQUAL) term)*
    ;

term
    : factor ((PLUS | MINUS) factor)*
    ;

factor
    : unary ((TIMES | DIVIDE) unary)*
    ;

unary
    : (BANG | MINUS) unary
    | primary
    ;

primary
    : NUMBER
    | TRUE
    | FALSE
    | IDENTIFIER
    | LPAREN expression RPAREN
    | IDENTIFIER LPAREN args? RPAREN   // function call
    ;

args
    : expression (COMMA expression)*
    ;

// =========================
// LEXER RULES (TOKENS)
// ========================


// keywords
INT : 'int';
FUN : 'fun';
BOOL : 'bool';
IF : 'if';
ELSE : 'else';
WHILE : 'while';
RETURN : 'return';
PRINT : 'print';
TRUE : 'true';
FALSE : 'false';
ASSIGN : '=';

// operators
PLUS : '+';
MINUS : '-';
TIMES : '*';
DIVIDE : '/';
AND_AND : '&&';
OR_OR : '||';
EQUAL_EQUAL : '==';
BANG_EQUAL : '!=';
BANG : '!';
LESS_THAN : '<';
GREATER_THAN : '>';
LESS_EQUAL : '<=';
GREATER_EQUAL : '>=';

// punctuation
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
COMMA : ',';
SEMICOLON : ';';

// literals and identifiers
NUMBER : [0-9]+;
IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]*;

// whitespace
WS : [ \t\r\n]+ -> skip;

