package edu.wisc;

import java.util.List;

import edu.wisc.Expr.Binary;
import edu.wisc.Expr.Call;
import edu.wisc.Expr.Literal;
import edu.wisc.Expr.Unary;
import edu.wisc.Expr.Variable;
import edu.wisc.Stmt.Assign;
import edu.wisc.Stmt.Block;
import edu.wisc.Stmt.Expression;
import edu.wisc.Stmt.Function;
import edu.wisc.Stmt.If;
import edu.wisc.Stmt.Print;
import edu.wisc.Stmt.Return;
import edu.wisc.Stmt.Var;
import edu.wisc.Stmt.While;

public final class CodegenVisitor implements Expr.Visitor<Void>, Stmt.Visitor<Void> {
    private final Asm asm;
    private Frame frame;              // set in visitFunctionStmt

    public CodegenVisitor(Asm asm) { this.asm = asm; }

    /** Entry point: generate MIPS for the whole program (root = List<Stmt>). */
    public void generate(List<Stmt> program) {
        // No strings in this language, so no .data prepass needed.
        asm.t(".text");
        asm.t(".globl main");
        for (Stmt s : program) if (s != null) s.accept(this);
    }

    // ---- statements ----
    @Override public Void visitFunctionStmt(Function stmt) {
        frame = Frame.build(stmt);
        asm.label(stmt.name);
        emitPrologue(stmt.name, frame.size());

        for (Stmt s : stmt.body) s.accept(this);

        // (For now) fall-through: main exit; others return via explicit Return
        return null;
    }

    @Override
    public Void visitVarStmt(Var stmt) {
        // If there’s an initializer, evaluate it
        if (stmt.initializer != null) {
            stmt.initializer.accept(this);  // generates code → result in $t0
            storeT0ToVar(stmt.name);        // save $t0 into x’s stack slot
        }
        // If no initializer, default to 0
        else {
            asm.t("li $t0, 0");
            storeT0ToVar(stmt.name);
        }
        return null;
    }

    @Override
    public Void visitAssignStmt(Assign stmt) {
        // Evaluate RHS into $t0
        stmt.value.accept(this);
        // Store into the variable’s stack slot
        storeT0ToVar(stmt.name);
        return null;
    }


    @Override public Void visitBlockStmt(Block stmt) { 
        for (var s: stmt.statements) s.accept(this); 
        return null; 
    }


    @Override
    public Void visitIfStmt(If stmt) {
        String elseLbl = L("else");
        String endLbl  = L("endif");

        // 1. Evaluate condition → result in $t0
        stmt.condition.accept(this);

        // 2. Branch to elseLbl if false ($t0 == 0)
        asm.t("beqz $t0, " + elseLbl);

        // 3. Then branch
        stmt.thenBranch.accept(this);
        asm.t("j " + endLbl);          // skip else after executing then

        // 4. Else branch (if present)
        asm.label(elseLbl);
        if (stmt.elseBranch != null) {
            stmt.elseBranch.accept(this);
        }

        // 5. End label
        asm.label(endLbl);
        return null;
    }

    @Override
    public Void visitWhileStmt(While stmt) {
        String startLbl = L("while_start");
        String endLbl   = L("while_end");

        // Start label (loop entry)
        asm.label(startLbl);

        // 1. Evaluate condition → result in $t0
        stmt.condition.accept(this);

        // 2. If false, jump to end
        asm.t("beqz $t0, " + endLbl);

        // 3. Loop body
        stmt.body.accept(this);

        // 4. Jump back to start
        asm.t("j " + startLbl);

        // 5. Exit label
        asm.label(endLbl);
        return null;
    }



    @Override
    public Void visitReturnStmt(Return stmt) {
        // If there’s a return value, evaluate it → result in $t0
        if (stmt.value != null)
            stmt.value.accept(this);

        // Move result into $v0 (convention: return value)
        asm.t("move $v0, $t0");

        // Function epilogue (restore stack and return)
        emitEpilogue(frame.size());
        return null;
    }



    @Override
    public Void visitPrintStmt(Print stmt) {
        // Evaluate the expression → result in $t0
        stmt.expression.accept(this);

        // For now, assume only integers or chars are printable
        printIntFromT0();  

        return null;
    }


    @Override public Void visitExpressionStmt(Expression stmt) { 
        stmt.expression.accept(this); 
        return null; 
    }


    //exprs

    @Override
    public Void visitLiteralExpr(Literal expr) {
        Object v = expr.value;

        if (v instanceof Integer) {
            // Load integer constant directly into $t0
            asm.t("li $t0, " + v);
        } else if (v instanceof Boolean) {
            // true = 1, false = 0
            int b = ((Boolean) v) ? 1 : 0;
            asm.t("li $t0, " + b);
        } else {
            // Safety: unknown literal type
            asm.t("li $t0, 0  # unsupported literal type");
        }

        return null;
    }


    @Override
    public Void visitVariableExpr(Variable expr) {
        loadVarToT0(expr.name);
        return null;
    }

    @Override
    public Void visitUnaryExpr(Unary expr) {
        // Evaluate the operand → result in $t0
        expr.right.accept(this);

        switch (expr.operator) {
            case MINUS:
                // Arithmetic negation: $t0 = -$t0
                asm.t("sub $t0, $zero, $t0");
                break;
            case NOT:
                // Logical NOT: $t0 = !$t0 → (if $t0 == 0, becomes 1; else 0)
                asm.t("seq $t0, $t0, $zero");
                break;
            default:
                asm.t("# Unsupported unary operator");
        }

        return null;
    }
    
    @Override
    public Void visitBinaryExpr(Binary expr) {
        // Short-circuit boolean ops first (no need to eval RHS if not needed)
        if (expr.operator == Operator.AND) {
            String Lfalse = L("and_false"), Lend = L("and_end");
            // left
            expr.left.accept(this);                 // -> $t0
            asm.t("beqz $t0, " + Lfalse);          // if left == 0 -> false
            // right
            expr.right.accept(this);                // -> $t0
            asm.t("beqz $t0, " + Lfalse);          // if right == 0 -> false
            asm.t("li $t0, 1");                     // both nonzero -> true
            asm.t("j " + Lend);
            asm.label(Lfalse);
            asm.t("li $t0, 0");
            asm.label(Lend);
            return null;
        }
        if (expr.operator == Operator.OR) {
            String Ltrue = L("or_true"), Lend = L("or_end");
            // left
            expr.left.accept(this);                 // -> $t0
            asm.t("bnez $t0, " + Ltrue);           // if left != 0 -> true
            // right
            expr.right.accept(this);                // -> $t0
            asm.t("bnez $t0, " + Ltrue);           // if right != 0 -> true
            asm.t("li $t0, 0");                     // both zero -> false
            asm.t("j " + Lend);
            asm.label(Ltrue);
            asm.t("li $t0, 1");
            asm.label(Lend);
            return null;
        }

        // General case: evaluate both sides, keep left in $t1, right in $t0
        expr.left.accept(this);   // -> $t0 (left)
        pushT0();                 // save left
        expr.right.accept(this);  // -> $t0 (right)
        popToT1();                // $t1 = left, $t0 = right

        switch (expr.operator) {
            case PLUS:
                asm.t("add $t0, $t1, $t0");
                break;
            case MINUS:
                asm.t("sub $t0, $t1, $t0");
                break;
            case MULTIPLY:
                asm.t("mul $t0, $t1, $t0");              // SPIM/MARS allow 3-reg mul
                break;
            case DIVIDE:
                asm.t("div $t1, $t0");                   // left / right
                asm.t("mflo $t0");
                break;

            // Relational: leave 0/1 in $t0
            case LESS:
                asm.t("slt $t0, $t1, $t0");              // left < right
                break;
            case GREATER:
                asm.t("slt $t0, $t0, $t1");              // left > right  <=> right < left
                break;
            case LESS_EQUAL:
                asm.t("slt $t0, $t0, $t1");              // t0 = (right < left)
                asm.t("xori $t0, $t0, 1");               // !(right<left) => left <= right
                break;
            case GREATER_EQUAL:
                asm.t("slt $t0, $t1, $t0");              // t0 = (left < right)
                asm.t("xori $t0, $t0, 1");               // !(left<right) => left >= right
                break;
            case EQUAL:
                asm.t("seq $t0, $t1, $t0");              // pseudo: t0 = (left == right)
                break;
            case NOT_EQUAL:
                asm.t("sne $t0, $t1, $t0");              // pseudo: t0 = (left != right)
                break;

            default:
                asm.t("# unsupported binary op");
        }
        return null;
    }


    @Override
    public Void visitCallExpr(Call expr) {
        // 1) Push args right-to-left so arg0 ends up closest to $fp (positive offsets)
        for (int i = expr.arguments.size() - 1; i >= 0; --i) {
            expr.arguments.get(i).accept(this);  // result -> $t0
            pushT0();                             // addi $sp,-4 ; sw $t0,0($sp)
        }

        // 2) Call the function
        asm.t("jal " + expr.name);

        // 3) Pop the args (caller cleans)
        int bytes = 4 * expr.arguments.size();
        if (bytes != 0) asm.t("addi $sp, $sp, " + bytes);

        // 4) Function result in $v0 -> expression result in $t0
        asm.t("move $t0, $v0");
        return null;
    }

    // ---- helper emissions ----
    private void emitPrologue(String name, int frameSize) {
        asm.t("# prologue " + name);
        asm.t("addi $sp, $sp, -" + frameSize);
        asm.t("sw $ra, " + (frameSize - 4) + "($sp)");
        asm.t("sw $fp, " + (frameSize - 8) + "($sp)");
        asm.t("addi $fp, $sp, " + frameSize);
    }

    private void emitEpilogue(int frameSize) {
        asm.t("# epilogue");
        asm.t("lw $ra, " + (frameSize - 4) + "($sp)");
        asm.t("lw $fp, " + (frameSize - 8) + "($sp)");
        asm.t("addi $sp, $sp, " + frameSize);
        asm.t("jr $ra");
    }

    // name-based stack access
    private void loadVarToT0(String name) {
        asm.t("lw $t0, " + frame.offsetOf(name) + "($fp)");
    }
    private void storeT0ToVar(String name) {
        asm.t("sw $t0, " + frame.offsetOf(name) + "($fp)");
    }

//helpers

    private String L(String base) {
        return asm.newLabel(base);
    }

    // Stack operations for temporary values
    private void pushT0() {
        asm.t("addi $sp, $sp, -4");   // make space
        asm.t("sw $t0, 0($sp)");      // save t0 on stack
    }
    private void popToT1() {
        asm.t("lw $t1, 0($sp)");      // restore into t1
        asm.t("addi $sp, $sp, 4");    // reclaim space
    }

    // Printing helpers
    private void printIntFromT0() {
        asm.t("move $a0, $t0");
        asm.t("li $v0, 1");           // syscall: print_int
        asm.t("syscall");
    }
    private void printCharFromT0() {
        asm.t("move $a0, $t0");
        asm.t("li $v0, 11");          // syscall: print_char
        asm.t("syscall");
    }
    private void printNewline() {
        asm.t("li $a0, 10");          // ASCII newline
        asm.t("li $v0, 11");
        asm.t("syscall");
    }

    // Program exit helper
    private void exitProgram() {
        asm.t("li $v0, 10");          // syscall: exit
        asm.t("syscall");
    }

}
