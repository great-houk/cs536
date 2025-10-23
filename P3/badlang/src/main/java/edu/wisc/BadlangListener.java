// Generated from C:/Users/tyler/Programming/cs536/p3/badlang/src/main/java/edu/wisc/Badlang.g4 by ANTLR 4.13.1
package edu.wisc;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BadlangParser}.
 */
public interface BadlangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BadlangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(BadlangParser.ProgramContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(BadlangParser.ProgramContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(BadlangParser.DeclarationContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(BadlangParser.DeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(BadlangParser.VarDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(BadlangParser.VarDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(BadlangParser.TypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(BadlangParser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunDecl(BadlangParser.FunDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunDecl(BadlangParser.FunDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(BadlangParser.ParamListContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(BadlangParser.ParamListContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(BadlangParser.ParamContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(BadlangParser.ParamContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(BadlangParser.StatementContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(BadlangParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(BadlangParser.ExprStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(BadlangParser.ExprStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(BadlangParser.PrintStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(BadlangParser.PrintStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(BadlangParser.IfStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(BadlangParser.IfStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(BadlangParser.WhileStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(BadlangParser.WhileStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(BadlangParser.ReturnStmtContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(BadlangParser.ReturnStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(BadlangParser.BlockContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(BadlangParser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(BadlangParser.ExpressionContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(BadlangParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(BadlangParser.AssignmentContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(BadlangParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#logic_or}.
	 * @param ctx the parse tree
	 */
	void enterLogic_or(BadlangParser.Logic_orContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#logic_or}.
	 * @param ctx the parse tree
	 */
	void exitLogic_or(BadlangParser.Logic_orContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#logic_and}.
	 * @param ctx the parse tree
	 */
	void enterLogic_and(BadlangParser.Logic_andContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#logic_and}.
	 * @param ctx the parse tree
	 */
	void exitLogic_and(BadlangParser.Logic_andContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#equality}.
	 * @param ctx the parse tree
	 */
	void enterEquality(BadlangParser.EqualityContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#equality}.
	 * @param ctx the parse tree
	 */
	void exitEquality(BadlangParser.EqualityContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(BadlangParser.ComparisonContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(BadlangParser.ComparisonContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(BadlangParser.TermContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(BadlangParser.TermContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(BadlangParser.FactorContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(BadlangParser.FactorContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(BadlangParser.UnaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(BadlangParser.UnaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(BadlangParser.PrimaryContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(BadlangParser.PrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link BadlangParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(BadlangParser.ArgsContext ctx);

	/**
	 * Exit a parse tree produced by {@link BadlangParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(BadlangParser.ArgsContext ctx);
}