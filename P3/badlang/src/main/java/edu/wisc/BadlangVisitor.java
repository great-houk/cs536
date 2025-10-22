// Generated from Badlang.g4 by ANTLR 4.13.1

package edu.wisc;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BadlangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BadlangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BadlangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BadlangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(BadlangParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(BadlangParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(BadlangParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#funDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecl(BadlangParser.FunDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(BadlangParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(BadlangParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(BadlangParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(BadlangParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(BadlangParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(BadlangParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(BadlangParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(BadlangParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(BadlangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(BadlangParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(BadlangParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#logic_or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_or(BadlangParser.Logic_orContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#logic_and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_and(BadlangParser.Logic_andContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#equality}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality(BadlangParser.EqualityContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(BadlangParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(BadlangParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(BadlangParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(BadlangParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(BadlangParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link BadlangParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(BadlangParser.ArgsContext ctx);
}