package edu.wisc;

/**
 * Represents a single token produced by the lexer.
 * 
 * Implement this class according to your lexical analysis needs.
 */
public class Token {

    public enum TokenType {
        //keywords
        INT, FUN, IF, ELSE, WHILE, RETURN, PRINT, TRUE, FALSE, ASSIGN, 
        //operators
        MINUS, PLUS, TIMES, DIVIDE, AND_AND, OR_OR, EQUAL_EQUAL, BANG_EQUAL, BANG, LESS_THAN, GREATER_THAN, LESS_EQUAL, GREATER_EQUAL,
        //literals
        NUMBER, BOOLEAN,
        //identifiers
        IDENTIFIER, 
        //punctuation
        LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, COMMA, SEMICOLON, 
        EOF
    }
    // Add fields for token type, lexeme, literal value, line number, etc.
    public final TokenType type;
    public final String lexeme;
    public final Object literal;
    public final int line;
    public final int column;

    public Token(TokenType type, String lexeme, Object literal, int line, int column) {
        // Add constructor(s)
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
        this.column = column;
    }

    // Add getter methods

    public TokenType getType() { 
        return type; 
    }
    public String getLexeme() { 
        return lexeme; 
    }
    public Object getLiteral() { 
        return literal; 
    }
    public int getLine() { 
        return line; 
    }
    public int getColumn() { 
        return column; 
    }
    
    // Override toString() for debugging
    @Override
    public String toString() {
        return String.format("%s %s %s @%d:%d",
                type, lexeme, literal == null ? "" : literal, line, column);
    }
}


