// Generated from C:/Users/tyler/Programming/cs536/p3/badlang/src/main/java/edu/wisc/Badlang.g4 by ANTLR 4.13.1
package edu.wisc;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class BadlangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FUN=2, BOOL=3, IF=4, ELSE=5, WHILE=6, RETURN=7, PRINT=8, TRUE=9, 
		FALSE=10, ASSIGN=11, PLUS=12, MINUS=13, TIMES=14, DIVIDE=15, AND_AND=16, 
		OR_OR=17, EQUAL_EQUAL=18, BANG_EQUAL=19, BANG=20, LESS_THAN=21, GREATER_THAN=22, 
		LESS_EQUAL=23, GREATER_EQUAL=24, LPAREN=25, RPAREN=26, LBRACE=27, RBRACE=28, 
		COMMA=29, SEMICOLON=30, NUMBER=31, IDENTIFIER=32, WS=33;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_varDecl = 2, RULE_type = 3, 
		RULE_funDecl = 4, RULE_paramList = 5, RULE_param = 6, RULE_statement = 7, 
		RULE_exprStmt = 8, RULE_printStmt = 9, RULE_ifStmt = 10, RULE_whileStmt = 11, 
		RULE_returnStmt = 12, RULE_block = 13, RULE_expression = 14, RULE_assignment = 15, 
		RULE_logic_or = 16, RULE_logic_and = 17, RULE_equality = 18, RULE_comparison = 19, 
		RULE_term = 20, RULE_factor = 21, RULE_unary = 22, RULE_primary = 23, 
		RULE_args = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "varDecl", "type", "funDecl", "paramList", 
			"param", "statement", "exprStmt", "printStmt", "ifStmt", "whileStmt", 
			"returnStmt", "block", "expression", "assignment", "logic_or", "logic_and", 
			"equality", "comparison", "term", "factor", "unary", "primary", "args"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'fun'", "'bool'", "'if'", "'else'", "'while'", "'return'", 
			"'print'", "'true'", "'false'", "'='", "'+'", "'-'", "'*'", "'/'", "'&&'", 
			"'||'", "'=='", "'!='", "'!'", "'<'", "'>'", "'<='", "'>='", "'('", "')'", 
			"'{'", "'}'", "','", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FUN", "BOOL", "IF", "ELSE", "WHILE", "RETURN", "PRINT", 
			"TRUE", "FALSE", "ASSIGN", "PLUS", "MINUS", "TIMES", "DIVIDE", "AND_AND", 
			"OR_OR", "EQUAL_EQUAL", "BANG_EQUAL", "BANG", "LESS_THAN", "GREATER_THAN", 
			"LESS_EQUAL", "GREATER_EQUAL", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"COMMA", "SEMICOLON", "NUMBER", "IDENTIFIER", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Badlang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BadlangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(BadlangParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6611281886L) != 0)) {
				{
				setState(52);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INT:
				case FUN:
				case BOOL:
					{
					setState(50);
					declaration();
					}
					break;
				case IF:
				case WHILE:
				case RETURN:
				case PRINT:
				case TRUE:
				case FALSE:
				case MINUS:
				case BANG:
				case LPAREN:
				case LBRACE:
				case NUMBER:
				case IDENTIFIER:
					{
					setState(51);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public VarDeclContext varDecl() {
			return getRuleContext(VarDeclContext.class,0);
		}
		public FunDeclContext funDecl() {
			return getRuleContext(FunDeclContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			setState(61);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				varDecl();
				}
				break;
			case FUN:
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				funDecl();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(BadlangParser.IDENTIFIER, 0); }
		public TerminalNode SEMICOLON() { return getToken(BadlangParser.SEMICOLON, 0); }
		public TerminalNode ASSIGN() { return getToken(BadlangParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			type();
			setState(64);
			match(IDENTIFIER);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(65);
				match(ASSIGN);
				setState(66);
				expression();
				}
			}

			setState(69);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(BadlangParser.INT, 0); }
		public TerminalNode BOOL() { return getToken(BadlangParser.BOOL, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==BOOL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunDeclContext extends ParserRuleContext {
		public TerminalNode FUN() { return getToken(BadlangParser.FUN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(BadlangParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(BadlangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(BadlangParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public FunDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterFunDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitFunDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitFunDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunDeclContext funDecl() throws RecognitionException {
		FunDeclContext _localctx = new FunDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_funDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(FUN);
			setState(74);
			type();
			setState(75);
			match(IDENTIFIER);
			setState(76);
			match(LPAREN);
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INT || _la==BOOL) {
				{
				setState(77);
				paramList();
				}
			}

			setState(80);
			match(RPAREN);
			setState(81);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BadlangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BadlangParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			param();
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(84);
				match(COMMA);
				setState(85);
				param();
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(BadlangParser.IDENTIFIER, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			type();
			setState(92);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public ExprStmtContext exprStmt() {
			return getRuleContext(ExprStmtContext.class,0);
		}
		public PrintStmtContext printStmt() {
			return getRuleContext(PrintStmtContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_statement);
		try {
			setState(100);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
			case MINUS:
			case BANG:
			case LPAREN:
			case NUMBER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
				exprStmt();
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				printStmt();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				ifStmt();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(97);
				whileStmt();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 5);
				{
				setState(98);
				returnStmt();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 6);
				{
				setState(99);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(BadlangParser.SEMICOLON, 0); }
		public ExprStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStmtContext exprStmt() throws RecognitionException {
		ExprStmtContext _localctx = new ExprStmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_exprStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			expression();
			setState(103);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrintStmtContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(BadlangParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(BadlangParser.SEMICOLON, 0); }
		public TerminalNode LPAREN() { return getToken(BadlangParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(BadlangParser.RPAREN, 0); }
		public PrintStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterPrintStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitPrintStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitPrintStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStmtContext printStmt() throws RecognitionException {
		PrintStmtContext _localctx = new PrintStmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_printStmt);
		try {
			setState(115);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				match(PRINT);
				setState(106);
				expression();
				setState(107);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				match(PRINT);
				setState(110);
				match(LPAREN);
				setState(111);
				expression();
				setState(112);
				match(RPAREN);
				setState(113);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(BadlangParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(BadlangParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(BadlangParser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(BadlangParser.ELSE, 0); }
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(IF);
			setState(118);
			match(LPAREN);
			setState(119);
			expression();
			setState(120);
			match(RPAREN);
			setState(121);
			statement();
			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(122);
				match(ELSE);
				setState(123);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(BadlangParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(BadlangParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(BadlangParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(WHILE);
			setState(127);
			match(LPAREN);
			setState(128);
			expression();
			setState(129);
			match(RPAREN);
			setState(130);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStmtContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(BadlangParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(BadlangParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitReturnStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(RETURN);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6477063680L) != 0)) {
				{
				setState(133);
				expression();
				}
			}

			setState(136);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(BadlangParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(BadlangParser.RBRACE, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(LBRACE);
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6611281886L) != 0)) {
				{
				setState(141);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INT:
				case FUN:
				case BOOL:
					{
					setState(139);
					declaration();
					}
					break;
				case IF:
				case WHILE:
				case RETURN:
				case PRINT:
				case TRUE:
				case FALSE:
				case MINUS:
				case BANG:
				case LPAREN:
				case LBRACE:
				case NUMBER:
				case IDENTIFIER:
					{
					setState(140);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(146);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			assignment();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public Logic_orContext logic_or() {
			return getRuleContext(Logic_orContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(BadlangParser.IDENTIFIER, 0); }
		public TerminalNode ASSIGN() { return getToken(BadlangParser.ASSIGN, 0); }
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_assignment);
		try {
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				logic_or();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(151);
				match(IDENTIFIER);
				setState(152);
				match(ASSIGN);
				setState(153);
				assignment();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_orContext extends ParserRuleContext {
		public List<Logic_andContext> logic_and() {
			return getRuleContexts(Logic_andContext.class);
		}
		public Logic_andContext logic_and(int i) {
			return getRuleContext(Logic_andContext.class,i);
		}
		public List<TerminalNode> OR_OR() { return getTokens(BadlangParser.OR_OR); }
		public TerminalNode OR_OR(int i) {
			return getToken(BadlangParser.OR_OR, i);
		}
		public Logic_orContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterLogic_or(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitLogic_or(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitLogic_or(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_orContext logic_or() throws RecognitionException {
		Logic_orContext _localctx = new Logic_orContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logic_or);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			logic_and();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OR) {
				{
				{
				setState(157);
				match(OR_OR);
				setState(158);
				logic_and();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_andContext extends ParserRuleContext {
		public List<EqualityContext> equality() {
			return getRuleContexts(EqualityContext.class);
		}
		public EqualityContext equality(int i) {
			return getRuleContext(EqualityContext.class,i);
		}
		public List<TerminalNode> AND_AND() { return getTokens(BadlangParser.AND_AND); }
		public TerminalNode AND_AND(int i) {
			return getToken(BadlangParser.AND_AND, i);
		}
		public Logic_andContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterLogic_and(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitLogic_and(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitLogic_and(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_andContext logic_and() throws RecognitionException {
		Logic_andContext _localctx = new Logic_andContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_logic_and);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			equality();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_AND) {
				{
				{
				setState(165);
				match(AND_AND);
				setState(166);
				equality();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityContext extends ParserRuleContext {
		public List<ComparisonContext> comparison() {
			return getRuleContexts(ComparisonContext.class);
		}
		public ComparisonContext comparison(int i) {
			return getRuleContext(ComparisonContext.class,i);
		}
		public List<TerminalNode> EQUAL_EQUAL() { return getTokens(BadlangParser.EQUAL_EQUAL); }
		public TerminalNode EQUAL_EQUAL(int i) {
			return getToken(BadlangParser.EQUAL_EQUAL, i);
		}
		public List<TerminalNode> BANG_EQUAL() { return getTokens(BadlangParser.BANG_EQUAL); }
		public TerminalNode BANG_EQUAL(int i) {
			return getToken(BadlangParser.BANG_EQUAL, i);
		}
		public EqualityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterEquality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitEquality(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitEquality(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityContext equality() throws RecognitionException {
		EqualityContext _localctx = new EqualityContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_equality);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			comparison();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQUAL_EQUAL || _la==BANG_EQUAL) {
				{
				{
				setState(173);
				_la = _input.LA(1);
				if ( !(_la==EQUAL_EQUAL || _la==BANG_EQUAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(174);
				comparison();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> LESS_THAN() { return getTokens(BadlangParser.LESS_THAN); }
		public TerminalNode LESS_THAN(int i) {
			return getToken(BadlangParser.LESS_THAN, i);
		}
		public List<TerminalNode> GREATER_THAN() { return getTokens(BadlangParser.GREATER_THAN); }
		public TerminalNode GREATER_THAN(int i) {
			return getToken(BadlangParser.GREATER_THAN, i);
		}
		public List<TerminalNode> LESS_EQUAL() { return getTokens(BadlangParser.LESS_EQUAL); }
		public TerminalNode LESS_EQUAL(int i) {
			return getToken(BadlangParser.LESS_EQUAL, i);
		}
		public List<TerminalNode> GREATER_EQUAL() { return getTokens(BadlangParser.GREATER_EQUAL); }
		public TerminalNode GREATER_EQUAL(int i) {
			return getToken(BadlangParser.GREATER_EQUAL, i);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			term();
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 31457280L) != 0)) {
				{
				{
				setState(181);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 31457280L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(182);
				term();
				}
				}
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(BadlangParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(BadlangParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(BadlangParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(BadlangParser.MINUS, i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			factor();
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(189);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(190);
				factor();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public List<UnaryContext> unary() {
			return getRuleContexts(UnaryContext.class);
		}
		public UnaryContext unary(int i) {
			return getRuleContext(UnaryContext.class,i);
		}
		public List<TerminalNode> TIMES() { return getTokens(BadlangParser.TIMES); }
		public TerminalNode TIMES(int i) {
			return getToken(BadlangParser.TIMES, i);
		}
		public List<TerminalNode> DIVIDE() { return getTokens(BadlangParser.DIVIDE); }
		public TerminalNode DIVIDE(int i) {
			return getToken(BadlangParser.DIVIDE, i);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			unary();
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TIMES || _la==DIVIDE) {
				{
				{
				setState(197);
				_la = _input.LA(1);
				if ( !(_la==TIMES || _la==DIVIDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(198);
				unary();
				}
				}
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryContext extends ParserRuleContext {
		public UnaryContext unary() {
			return getRuleContext(UnaryContext.class,0);
		}
		public TerminalNode BANG() { return getToken(BadlangParser.BANG, 0); }
		public TerminalNode MINUS() { return getToken(BadlangParser.MINUS, 0); }
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public UnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryContext unary() throws RecognitionException {
		UnaryContext _localctx = new UnaryContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_unary);
		int _la;
		try {
			setState(207);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MINUS:
			case BANG:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				_la = _input.LA(1);
				if ( !(_la==MINUS || _la==BANG) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(205);
				unary();
				}
				break;
			case TRUE:
			case FALSE:
			case LPAREN:
			case NUMBER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				primary();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(BadlangParser.NUMBER, 0); }
		public TerminalNode TRUE() { return getToken(BadlangParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(BadlangParser.FALSE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(BadlangParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(BadlangParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(BadlangParser.RPAREN, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_primary);
		int _la;
		try {
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(210);
				match(TRUE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(211);
				match(FALSE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(212);
				match(IDENTIFIER);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(213);
				match(LPAREN);
				setState(214);
				expression();
				setState(215);
				match(RPAREN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(217);
				match(IDENTIFIER);
				setState(218);
				match(LPAREN);
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6477063680L) != 0)) {
					{
					setState(219);
					args();
					}
				}

				setState(222);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BadlangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BadlangParser.COMMA, i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BadlangListener ) ((BadlangListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BadlangVisitor ) return ((BadlangVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			expression();
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(226);
				match(COMMA);
				setState(227);
				expression();
				}
				}
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001!\u00ea\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0001\u0000\u0001\u0000\u0005\u00005\b\u0000\n\u0000\f\u00008\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001>\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002D\b\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004O\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"W\b\u0005\n\u0005\f\u0005Z\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007e\b\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tt\b\t"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n}\b\n"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0003\f\u0087\b\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0005\r\u008e\b\r\n\r\f\r\u0091\t\r\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u009b"+
		"\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00a0\b\u0010"+
		"\n\u0010\f\u0010\u00a3\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0005"+
		"\u0011\u00a8\b\u0011\n\u0011\f\u0011\u00ab\t\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0005\u0012\u00b0\b\u0012\n\u0012\f\u0012\u00b3\t\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00b8\b\u0013\n\u0013\f\u0013"+
		"\u00bb\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u00c0\b"+
		"\u0014\n\u0014\f\u0014\u00c3\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0005\u0015\u00c8\b\u0015\n\u0015\f\u0015\u00cb\t\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0003\u0016\u00d0\b\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u00dd\b\u0017\u0001\u0017\u0003"+
		"\u0017\u00e0\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u00e5"+
		"\b\u0018\n\u0018\f\u0018\u00e8\t\u0018\u0001\u0018\u0000\u0000\u0019\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.0\u0000\u0006\u0002\u0000\u0001\u0001\u0003\u0003\u0001"+
		"\u0000\u0012\u0013\u0001\u0000\u0015\u0018\u0001\u0000\f\r\u0001\u0000"+
		"\u000e\u000f\u0002\u0000\r\r\u0014\u0014\u00ef\u00006\u0001\u0000\u0000"+
		"\u0000\u0002=\u0001\u0000\u0000\u0000\u0004?\u0001\u0000\u0000\u0000\u0006"+
		"G\u0001\u0000\u0000\u0000\bI\u0001\u0000\u0000\u0000\nS\u0001\u0000\u0000"+
		"\u0000\f[\u0001\u0000\u0000\u0000\u000ed\u0001\u0000\u0000\u0000\u0010"+
		"f\u0001\u0000\u0000\u0000\u0012s\u0001\u0000\u0000\u0000\u0014u\u0001"+
		"\u0000\u0000\u0000\u0016~\u0001\u0000\u0000\u0000\u0018\u0084\u0001\u0000"+
		"\u0000\u0000\u001a\u008a\u0001\u0000\u0000\u0000\u001c\u0094\u0001\u0000"+
		"\u0000\u0000\u001e\u009a\u0001\u0000\u0000\u0000 \u009c\u0001\u0000\u0000"+
		"\u0000\"\u00a4\u0001\u0000\u0000\u0000$\u00ac\u0001\u0000\u0000\u0000"+
		"&\u00b4\u0001\u0000\u0000\u0000(\u00bc\u0001\u0000\u0000\u0000*\u00c4"+
		"\u0001\u0000\u0000\u0000,\u00cf\u0001\u0000\u0000\u0000.\u00df\u0001\u0000"+
		"\u0000\u00000\u00e1\u0001\u0000\u0000\u000025\u0003\u0002\u0001\u0000"+
		"35\u0003\u000e\u0007\u000042\u0001\u0000\u0000\u000043\u0001\u0000\u0000"+
		"\u000058\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000067\u0001\u0000"+
		"\u0000\u000079\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u00009:\u0005"+
		"\u0000\u0000\u0001:\u0001\u0001\u0000\u0000\u0000;>\u0003\u0004\u0002"+
		"\u0000<>\u0003\b\u0004\u0000=;\u0001\u0000\u0000\u0000=<\u0001\u0000\u0000"+
		"\u0000>\u0003\u0001\u0000\u0000\u0000?@\u0003\u0006\u0003\u0000@C\u0005"+
		" \u0000\u0000AB\u0005\u000b\u0000\u0000BD\u0003\u001c\u000e\u0000CA\u0001"+
		"\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000"+
		"EF\u0005\u001e\u0000\u0000F\u0005\u0001\u0000\u0000\u0000GH\u0007\u0000"+
		"\u0000\u0000H\u0007\u0001\u0000\u0000\u0000IJ\u0005\u0002\u0000\u0000"+
		"JK\u0003\u0006\u0003\u0000KL\u0005 \u0000\u0000LN\u0005\u0019\u0000\u0000"+
		"MO\u0003\n\u0005\u0000NM\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000"+
		"OP\u0001\u0000\u0000\u0000PQ\u0005\u001a\u0000\u0000QR\u0003\u001a\r\u0000"+
		"R\t\u0001\u0000\u0000\u0000SX\u0003\f\u0006\u0000TU\u0005\u001d\u0000"+
		"\u0000UW\u0003\f\u0006\u0000VT\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000"+
		"\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Y\u000b\u0001"+
		"\u0000\u0000\u0000ZX\u0001\u0000\u0000\u0000[\\\u0003\u0006\u0003\u0000"+
		"\\]\u0005 \u0000\u0000]\r\u0001\u0000\u0000\u0000^e\u0003\u0010\b\u0000"+
		"_e\u0003\u0012\t\u0000`e\u0003\u0014\n\u0000ae\u0003\u0016\u000b\u0000"+
		"be\u0003\u0018\f\u0000ce\u0003\u001a\r\u0000d^\u0001\u0000\u0000\u0000"+
		"d_\u0001\u0000\u0000\u0000d`\u0001\u0000\u0000\u0000da\u0001\u0000\u0000"+
		"\u0000db\u0001\u0000\u0000\u0000dc\u0001\u0000\u0000\u0000e\u000f\u0001"+
		"\u0000\u0000\u0000fg\u0003\u001c\u000e\u0000gh\u0005\u001e\u0000\u0000"+
		"h\u0011\u0001\u0000\u0000\u0000ij\u0005\b\u0000\u0000jk\u0003\u001c\u000e"+
		"\u0000kl\u0005\u001e\u0000\u0000lt\u0001\u0000\u0000\u0000mn\u0005\b\u0000"+
		"\u0000no\u0005\u0019\u0000\u0000op\u0003\u001c\u000e\u0000pq\u0005\u001a"+
		"\u0000\u0000qr\u0005\u001e\u0000\u0000rt\u0001\u0000\u0000\u0000si\u0001"+
		"\u0000\u0000\u0000sm\u0001\u0000\u0000\u0000t\u0013\u0001\u0000\u0000"+
		"\u0000uv\u0005\u0004\u0000\u0000vw\u0005\u0019\u0000\u0000wx\u0003\u001c"+
		"\u000e\u0000xy\u0005\u001a\u0000\u0000y|\u0003\u000e\u0007\u0000z{\u0005"+
		"\u0005\u0000\u0000{}\u0003\u000e\u0007\u0000|z\u0001\u0000\u0000\u0000"+
		"|}\u0001\u0000\u0000\u0000}\u0015\u0001\u0000\u0000\u0000~\u007f\u0005"+
		"\u0006\u0000\u0000\u007f\u0080\u0005\u0019\u0000\u0000\u0080\u0081\u0003"+
		"\u001c\u000e\u0000\u0081\u0082\u0005\u001a\u0000\u0000\u0082\u0083\u0003"+
		"\u000e\u0007\u0000\u0083\u0017\u0001\u0000\u0000\u0000\u0084\u0086\u0005"+
		"\u0007\u0000\u0000\u0085\u0087\u0003\u001c\u000e\u0000\u0086\u0085\u0001"+
		"\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0088\u0001"+
		"\u0000\u0000\u0000\u0088\u0089\u0005\u001e\u0000\u0000\u0089\u0019\u0001"+
		"\u0000\u0000\u0000\u008a\u008f\u0005\u001b\u0000\u0000\u008b\u008e\u0003"+
		"\u0002\u0001\u0000\u008c\u008e\u0003\u000e\u0007\u0000\u008d\u008b\u0001"+
		"\u0000\u0000\u0000\u008d\u008c\u0001\u0000\u0000\u0000\u008e\u0091\u0001"+
		"\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001"+
		"\u0000\u0000\u0000\u0090\u0092\u0001\u0000\u0000\u0000\u0091\u008f\u0001"+
		"\u0000\u0000\u0000\u0092\u0093\u0005\u001c\u0000\u0000\u0093\u001b\u0001"+
		"\u0000\u0000\u0000\u0094\u0095\u0003\u001e\u000f\u0000\u0095\u001d\u0001"+
		"\u0000\u0000\u0000\u0096\u009b\u0003 \u0010\u0000\u0097\u0098\u0005 \u0000"+
		"\u0000\u0098\u0099\u0005\u000b\u0000\u0000\u0099\u009b\u0003\u001e\u000f"+
		"\u0000\u009a\u0096\u0001\u0000\u0000\u0000\u009a\u0097\u0001\u0000\u0000"+
		"\u0000\u009b\u001f\u0001\u0000\u0000\u0000\u009c\u00a1\u0003\"\u0011\u0000"+
		"\u009d\u009e\u0005\u0011\u0000\u0000\u009e\u00a0\u0003\"\u0011\u0000\u009f"+
		"\u009d\u0001\u0000\u0000\u0000\u00a0\u00a3\u0001\u0000\u0000\u0000\u00a1"+
		"\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2"+
		"!\u0001\u0000\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a9"+
		"\u0003$\u0012\u0000\u00a5\u00a6\u0005\u0010\u0000\u0000\u00a6\u00a8\u0003"+
		"$\u0012\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a8\u00ab\u0001\u0000"+
		"\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000"+
		"\u0000\u0000\u00aa#\u0001\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000"+
		"\u0000\u00ac\u00b1\u0003&\u0013\u0000\u00ad\u00ae\u0007\u0001\u0000\u0000"+
		"\u00ae\u00b0\u0003&\u0013\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b3\u0001\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b2%\u0001\u0000\u0000\u0000\u00b3\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b9\u0003(\u0014\u0000\u00b5\u00b6\u0007"+
		"\u0002\u0000\u0000\u00b6\u00b8\u0003(\u0014\u0000\u00b7\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b8\u00bb\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000"+
		"\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\'\u0001\u0000\u0000"+
		"\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bc\u00c1\u0003*\u0015\u0000"+
		"\u00bd\u00be\u0007\u0003\u0000\u0000\u00be\u00c0\u0003*\u0015\u0000\u00bf"+
		"\u00bd\u0001\u0000\u0000\u0000\u00c0\u00c3\u0001\u0000\u0000\u0000\u00c1"+
		"\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2"+
		")\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c4\u00c9"+
		"\u0003,\u0016\u0000\u00c5\u00c6\u0007\u0004\u0000\u0000\u00c6\u00c8\u0003"+
		",\u0016\u0000\u00c7\u00c5\u0001\u0000\u0000\u0000\u00c8\u00cb\u0001\u0000"+
		"\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000"+
		"\u0000\u0000\u00ca+\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000"+
		"\u0000\u00cc\u00cd\u0007\u0005\u0000\u0000\u00cd\u00d0\u0003,\u0016\u0000"+
		"\u00ce\u00d0\u0003.\u0017\u0000\u00cf\u00cc\u0001\u0000\u0000\u0000\u00cf"+
		"\u00ce\u0001\u0000\u0000\u0000\u00d0-\u0001\u0000\u0000\u0000\u00d1\u00e0"+
		"\u0005\u001f\u0000\u0000\u00d2\u00e0\u0005\t\u0000\u0000\u00d3\u00e0\u0005"+
		"\n\u0000\u0000\u00d4\u00e0\u0005 \u0000\u0000\u00d5\u00d6\u0005\u0019"+
		"\u0000\u0000\u00d6\u00d7\u0003\u001c\u000e\u0000\u00d7\u00d8\u0005\u001a"+
		"\u0000\u0000\u00d8\u00e0\u0001\u0000\u0000\u0000\u00d9\u00da\u0005 \u0000"+
		"\u0000\u00da\u00dc\u0005\u0019\u0000\u0000\u00db\u00dd\u00030\u0018\u0000"+
		"\u00dc\u00db\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000\u0000\u0000"+
		"\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u00e0\u0005\u001a\u0000\u0000"+
		"\u00df\u00d1\u0001\u0000\u0000\u0000\u00df\u00d2\u0001\u0000\u0000\u0000"+
		"\u00df\u00d3\u0001\u0000\u0000\u0000\u00df\u00d4\u0001\u0000\u0000\u0000"+
		"\u00df\u00d5\u0001\u0000\u0000\u0000\u00df\u00d9\u0001\u0000\u0000\u0000"+
		"\u00e0/\u0001\u0000\u0000\u0000\u00e1\u00e6\u0003\u001c\u000e\u0000\u00e2"+
		"\u00e3\u0005\u001d\u0000\u0000\u00e3\u00e5\u0003\u001c\u000e\u0000\u00e4"+
		"\u00e2\u0001\u0000\u0000\u0000\u00e5\u00e8\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e4\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000\u0000\u00e7"+
		"1\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001\u0000\u0000\u0000\u001746"+
		"=CNXds|\u0086\u008d\u008f\u009a\u00a1\u00a9\u00b1\u00b9\u00c1\u00c9\u00cf"+
		"\u00dc\u00df\u00e6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}