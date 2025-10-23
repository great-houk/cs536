package edu.wisc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ASTBuilder extends BadlangBaseVisitor<Object> {



    @Override
    public Object visitProgram(BadlangParser.ProgramContext ctx) {
        List<Stmt> results = new ArrayList<>();

        for (ParseTree child : ctx.children) {
            Object node = visit(child); //visit every child, if its a stmt then add it
            if (node instanceof Stmt) {
                results.add((Stmt) node);
            } else if (node instanceof List<?>) {   //if its a list then add them all
                for (Object stmt : (List<?>) node) {
                    if (stmt instanceof Stmt) {
                        results.add((Stmt) stmt);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public Object visitDeclaration(BadlangParser.DeclarationContext ctx) {
        if (ctx.varDecl() != null) {
            return visit(ctx.varDecl());    //declaration is simple, if its not a varDecl then just return funDecl
        } else {
            return visit(ctx.funDecl());
        }

    }

    @Override
    public Object visitVarDecl(BadlangParser.VarDeclContext ctx) {
        VarType type = (VarType) visit(ctx.type());   //get the type to return later
        String name = ctx.IDENTIFIER().getText();      //get the identifier

        Expr initializer = null;    //set this to be null, if there is no initializer then it will remain null 
        if (ctx.expression() != null) {             //if there is an expression, then visit it
            initializer = (Expr) visit(ctx.expression());
        }
        return new Stmt.Var(name, type, initializer);  //return new variable declaration

    }

    @Override
    public Object visitFunDecl(BadlangParser.FunDeclContext ctx) {
        VarType type = (VarType) visit(ctx.type()); //get the type
        String name = ctx.IDENTIFIER().getText();       //and get the name
        List<Stmt.Parameter> params = new ArrayList<>();
        if (ctx.paramList() != null) {      //to get the params, visit the params list
            params = (List<Stmt.Parameter>) visit(ctx.paramList());
        }

        Stmt.Block bodyBlock = (Stmt.Block) visit(ctx.block());     //the bod is just a block so visit it
        return new Stmt.Function(name, type, params, bodyBlock.statements);

    }

    @Override
    public Object visitType(BadlangParser.TypeContext ctx) {
        if (ctx.INT() != null) {    //if its not INT then its BOOL, v simple method
            return VarType.INT;
        } else {
            return VarType.BOOL;
        }
    }

    @Override
    public Object visitParam(BadlangParser.ParamContext ctx) {
        VarType type = (VarType) visit(ctx.type());
        String name = ctx.IDENTIFIER().getText();
        return new Stmt.Parameter(name, type);  //get the name and type, then return the param
    }

    @Override
    public Object visitParamList(BadlangParser.ParamListContext ctx) {
        List<Stmt.Parameter> params = new ArrayList<>();
        //this should just be a list of params so visit each one and add to the list
        for (BadlangParser.ParamContext paramCtx : ctx.param()) { 
            params.add((Stmt.Parameter) visit(paramCtx));
        }
        return params;
    }

    @Override
    public Object visitStatement(BadlangParser.StatementContext ctx) {
        //just check which statement it is and visit that one
        if (ctx.exprStmt() != null) {
            return visit(ctx.exprStmt());
        } else if (ctx.printStmt() != null) {
            return visit(ctx.printStmt());
        } else if (ctx.ifStmt() != null) {
            return visit(ctx.ifStmt());
        } else if (ctx.whileStmt() != null) {
            return visit(ctx.whileStmt());
        } else if (ctx.returnStmt() != null) {
            return visit(ctx.returnStmt());
        } else { 
            return visit(ctx.block());
        }
    }

    @Override
    public Object visitExprStmt(BadlangParser.ExprStmtContext ctx) {
        Object v = visit(ctx.expression());
        if (v instanceof Stmt) return (Stmt) v;        // assignment case becomes a statement, there was not an Expr.Assign
        return new Stmt.Expression((Expr) v);          // other expressions as statements
    }

    @Override
    public Object visitPrintStmt(BadlangParser.PrintStmtContext ctx) {
        //get the expression to print and return it
        Expr expression = (Expr) visit(ctx.expression());
        return new Stmt.Print(expression);
    }

    @Override
    public Object visitIfStmt(BadlangParser.IfStmtContext ctx) {
        Expr condition = (Expr) visit(ctx.expression()); //get the condition
        Stmt thenBranch = (Stmt) visit(ctx.statement(0));   //get the hen branch
        Stmt elseBranch = null;
        if (ctx.statement().size() > 1) {      //if there is more than one statement there is an else branch
            elseBranch = (Stmt.Block) visit(ctx.statement(1));
        } 
            
        return new Stmt.If(condition, thenBranch, elseBranch);
    }

    @Override
    public Object visitWhileStmt(BadlangParser.WhileStmtContext ctx) {
        Expr condition = (Expr) visit(ctx.expression());
        Stmt body = (Stmt) visit(ctx.statement()); //very similar to if, just get the condition and the body and return
        return new Stmt.While(condition, body);
    }

    @Override
    public Object visitReturnStmt(BadlangParser.ReturnStmtContext ctx) {
        Expr value = null;
        if (ctx.expression() != null) { //if there is a value to return then return it, otherwise it should just be "return;"
            value = (Expr) visit(ctx.expression());
        }
        return new Stmt.Return(value);
    }

    @Override
    public Object visitBlock(BadlangParser.BlockContext ctx) {
        List<Stmt> statements = new ArrayList<>();
        for (BadlangParser.StatementContext stmtCtx : ctx.statement()) {    
            //go through each statement and visit it, adding it to the list
            statements.add((Stmt) visit(stmtCtx));
        }
        return new Stmt.Block(statements);
        
    }

    @Override
    public Object visitExpression(BadlangParser.ExpressionContext ctx) {
        //this one only has assignment under it, so should just return assignment
        return visit(ctx.assignment()); 
    }

    @Override
    public Object visitAssignment(BadlangParser.AssignmentContext ctx) {
        if (ctx.logic_or() != null) {
            return visit(ctx.logic_or());
        } else {
            String name = ctx.IDENTIFIER().getText();
            Expr value = (Expr) visit(ctx.assignment());
            return new Stmt.Assign(name, value); 
            //there is no Expr.Assign, so use Stmt.Assign and figure it out in visitExprStmt?
        }    
    }

    @Override
    public Object visitLogic_or(BadlangParser.Logic_orContext ctx) {
        Expr expr = (Expr) visit(ctx.logic_and(0)); 

        //for the rest of the "|| logic_and" pairs, use a binary node
        for (int i = 1; i < ctx.logic_and().size(); i++) {
            Expr right = (Expr) visit(ctx.logic_and(i));
            expr = new Expr.Binary(expr, Operator.OR, right);
        }
        return expr;
    }

    @Override
    public Object visitLogic_and(BadlangParser.Logic_andContext ctx) {
        Expr expr = (Expr) visit(ctx.equality(0)); 
        //this one is pretty much the same as logic or, just with &&
        for (int i = 1; i < ctx.equality().size(); i++) {
            Expr right = (Expr) visit(ctx.equality(i));
            expr = new Expr.Binary(expr, Operator.AND, right);

        }
        return expr;
    }

    //the next few are all very similar, there are two operatiosn that they could be, and it could be a repeating pattern
    @Override
public Object visitEquality(BadlangParser.EqualityContext ctx) {
    Expr expr = (Expr) visit(ctx.comparison(0));

    // children layout: comparison0, (op1, comparison1), (op2, comparison2), ...
    for (int i = 1; i < ctx.comparison().size(); i++) {
        // operator sits at child index 2*i - 1
        TerminalNode opNode = (TerminalNode) ctx.getChild(2 * i - 1);
        int tokType = opNode.getSymbol().getType();

        Operator op;
        switch (tokType) { //use a switch to figure out which operation it is? 
            case BadlangParser.EQUAL_EQUAL:
                op = Operator.EQUAL;
                break;
            case BadlangParser.BANG_EQUAL:
                op = Operator.NOT_EQUAL;
                break;
            default:
                throw new IllegalStateException("Unexpected equality operator token type: " + tokType);
        }

        Expr right = (Expr) visit(ctx.comparison(i));
        expr = new Expr.Binary(expr, op, right);
    }

    return expr;
}

    @Override
    public Object visitComparison(BadlangParser.ComparisonContext ctx) {
        Expr expr = (Expr) visit(ctx.term(0));

        for (int i = 1; i < ctx.term().size(); i++) {
            TerminalNode opNode = (TerminalNode) ctx.getChild(2 * i - 1);
            int tokType = opNode.getSymbol().getType();

            Operator op; //same as above I suppose
            switch (tokType) {
                case BadlangParser.LESS_THAN:
                    op = Operator.LESS;
                    break;
                case BadlangParser.LESS_EQUAL:
                    op = Operator.LESS_EQUAL;
                    break;
                case BadlangParser.GREATER_THAN:
                    op = Operator.GREATER;
                    break;
                case BadlangParser.GREATER_EQUAL:
                    op = Operator.GREATER_EQUAL;
                    break;
                default:    //if there is an operation that we dont support throw an exception
                    throw new IllegalStateException("Unexpected comparison operator token type: " + tokType);
            }

            Expr right = (Expr) visit(ctx.term(i));
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    @Override
    public Object visitTerm(BadlangParser.TermContext ctx) {
        Expr expr = (Expr) visit(ctx.factor(0));
        //again this one is the same as the above ones for the most part, have the loop to get the repeating 
        //parts and then have the switch to get the right operation
        for (int i = 1; i < ctx.factor().size(); i++) {
            TerminalNode opNode = (TerminalNode) ctx.getChild(2 * i - 1);
            int tokType = opNode.getSymbol().getType();

            Operator op;
            switch (tokType) {
                case BadlangParser.PLUS:
                    op = Operator.PLUS;
                    break;
                case BadlangParser.MINUS:
                    op = Operator.MINUS;
                    break;
                default:
                    throw new IllegalStateException("Unexpected term operator token type: " + tokType);
            }

            Expr right = (Expr) visit(ctx.factor(i));
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    @Override
    public Object visitFactor(BadlangParser.FactorContext ctx) {
        Expr expr = (Expr) visit(ctx.unary(0));

        for (int i = 1; i < ctx.unary().size(); i++) {
            TerminalNode opNode = (TerminalNode) ctx.getChild(2 * i - 1);
            int tokType = opNode.getSymbol().getType();

            Operator op;
            switch (tokType) {
                case BadlangParser.TIMES:
                    op = Operator.MULTIPLY;
                    break;
                case BadlangParser.DIVIDE:
                    op = Operator.DIVIDE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected factor operator token type: " + tokType);
            }

            Expr right = (Expr) visit(ctx.unary(i));
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    @Override
    public Object visitUnary(BadlangParser.UnaryContext ctx) {
    if (ctx.BANG() != null) {   //if its a ! the return that 
        Expr right = (Expr) visit(ctx.unary());
        return new Expr.Unary(Operator.NOT, right);
    } else if (ctx.MINUS() != null) { 
        Expr right = (Expr) visit(ctx.unary());
        return new Expr.Unary(Operator.MINUS, right);
    } else {
        // if its not a ! or -, then that mwan its jsut a primary so go visit that
        return visit(ctx.primary());
    }
}

   @Override
    public Object visitPrimary(BadlangParser.PrimaryContext ctx) {
        // literals
        if (ctx.NUMBER() != null) {
            int value = Integer.parseInt(ctx.NUMBER().getText());
            return new Expr.Literal(value);
        }
        if (ctx.TRUE() != null)  return new Expr.Literal(true);
        if (ctx.FALSE() != null) return new Expr.Literal(false);

        // identifier cases (variable vs call)
        if (ctx.IDENTIFIER() != null && ctx.LPAREN() == null) {
            return new Expr.Variable(ctx.IDENTIFIER().getText());
        }
        if (ctx.IDENTIFIER() != null && ctx.LPAREN() != null) {
            String name = ctx.IDENTIFIER().getText();
            @SuppressWarnings("unchecked")
            List<Expr> args = ctx.args() != null ? (List<Expr>) visit(ctx.args()) : Collections.emptyList();
            return new Expr.Call(name, args);
        }

        // grouping: '(' expression ')'
        //this was failing my tests because somewhere in here, I made it possible to return a null value. 
        //now checks if the expression is null too
        if (ctx.LPAREN() != null) {
            if (ctx.expression() == null) {
                throw new RuntimeException("Grouping without expression in primary(): " + ctx.getText());
            }
            return (Expr) visit(ctx.expression());   // no Expr.Grouping
        }

        throw new IllegalStateException("Unknown primary: " + ctx.getText());
    }


    @Override
    public Object visitArgs(BadlangParser.ArgsContext ctx) {
        List<Expr> list = new ArrayList<>();
        for (BadlangParser.ExpressionContext ectx : ctx.expression()) {
            list.add((Expr) visit(ectx)); //just return the list of args
        }
        return list;
    }



    
}
