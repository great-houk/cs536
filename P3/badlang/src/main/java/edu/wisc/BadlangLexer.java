// Generated from Badlang.g4 by ANTLR 4.13.1

package edu.wisc;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class BadlangLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT", "FUN", "BOOL", "IF", "ELSE", "WHILE", "RETURN", "PRINT", "TRUE", 
			"FALSE", "ASSIGN", "PLUS", "MINUS", "TIMES", "DIVIDE", "AND_AND", "OR_OR", 
			"EQUAL_EQUAL", "BANG_EQUAL", "BANG", "LESS_THAN", "GREATER_THAN", "LESS_EQUAL", 
			"GREATER_EQUAL", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "COMMA", "SEMICOLON", 
			"NUMBER", "IDENTIFIER", "WS"
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


	public BadlangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Badlang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000!\u00b7\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d"+
		"\u0001\u001e\u0004\u001e\u00a6\b\u001e\u000b\u001e\f\u001e\u00a7\u0001"+
		"\u001f\u0001\u001f\u0005\u001f\u00ac\b\u001f\n\u001f\f\u001f\u00af\t\u001f"+
		"\u0001 \u0004 \u00b2\b \u000b \f \u00b3\u0001 \u0001 \u0000\u0000!\u0001"+
		"\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007"+
		"\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d"+
		"\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/"+
		"\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? A!\u0001\u0000"+
		"\u0004\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u0003\u0000"+
		"\t\n\r\r  \u00b9\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000"+
		"%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000"+
		"3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001"+
		"\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000"+
		"\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000"+
		"A\u0001\u0000\u0000\u0000\u0001C\u0001\u0000\u0000\u0000\u0003G\u0001"+
		"\u0000\u0000\u0000\u0005K\u0001\u0000\u0000\u0000\u0007P\u0001\u0000\u0000"+
		"\u0000\tS\u0001\u0000\u0000\u0000\u000bX\u0001\u0000\u0000\u0000\r^\u0001"+
		"\u0000\u0000\u0000\u000fe\u0001\u0000\u0000\u0000\u0011k\u0001\u0000\u0000"+
		"\u0000\u0013p\u0001\u0000\u0000\u0000\u0015v\u0001\u0000\u0000\u0000\u0017"+
		"x\u0001\u0000\u0000\u0000\u0019z\u0001\u0000\u0000\u0000\u001b|\u0001"+
		"\u0000\u0000\u0000\u001d~\u0001\u0000\u0000\u0000\u001f\u0080\u0001\u0000"+
		"\u0000\u0000!\u0083\u0001\u0000\u0000\u0000#\u0086\u0001\u0000\u0000\u0000"+
		"%\u0089\u0001\u0000\u0000\u0000\'\u008c\u0001\u0000\u0000\u0000)\u008e"+
		"\u0001\u0000\u0000\u0000+\u0090\u0001\u0000\u0000\u0000-\u0092\u0001\u0000"+
		"\u0000\u0000/\u0095\u0001\u0000\u0000\u00001\u0098\u0001\u0000\u0000\u0000"+
		"3\u009a\u0001\u0000\u0000\u00005\u009c\u0001\u0000\u0000\u00007\u009e"+
		"\u0001\u0000\u0000\u00009\u00a0\u0001\u0000\u0000\u0000;\u00a2\u0001\u0000"+
		"\u0000\u0000=\u00a5\u0001\u0000\u0000\u0000?\u00a9\u0001\u0000\u0000\u0000"+
		"A\u00b1\u0001\u0000\u0000\u0000CD\u0005i\u0000\u0000DE\u0005n\u0000\u0000"+
		"EF\u0005t\u0000\u0000F\u0002\u0001\u0000\u0000\u0000GH\u0005f\u0000\u0000"+
		"HI\u0005u\u0000\u0000IJ\u0005n\u0000\u0000J\u0004\u0001\u0000\u0000\u0000"+
		"KL\u0005b\u0000\u0000LM\u0005o\u0000\u0000MN\u0005o\u0000\u0000NO\u0005"+
		"l\u0000\u0000O\u0006\u0001\u0000\u0000\u0000PQ\u0005i\u0000\u0000QR\u0005"+
		"f\u0000\u0000R\b\u0001\u0000\u0000\u0000ST\u0005e\u0000\u0000TU\u0005"+
		"l\u0000\u0000UV\u0005s\u0000\u0000VW\u0005e\u0000\u0000W\n\u0001\u0000"+
		"\u0000\u0000XY\u0005w\u0000\u0000YZ\u0005h\u0000\u0000Z[\u0005i\u0000"+
		"\u0000[\\\u0005l\u0000\u0000\\]\u0005e\u0000\u0000]\f\u0001\u0000\u0000"+
		"\u0000^_\u0005r\u0000\u0000_`\u0005e\u0000\u0000`a\u0005t\u0000\u0000"+
		"ab\u0005u\u0000\u0000bc\u0005r\u0000\u0000cd\u0005n\u0000\u0000d\u000e"+
		"\u0001\u0000\u0000\u0000ef\u0005p\u0000\u0000fg\u0005r\u0000\u0000gh\u0005"+
		"i\u0000\u0000hi\u0005n\u0000\u0000ij\u0005t\u0000\u0000j\u0010\u0001\u0000"+
		"\u0000\u0000kl\u0005t\u0000\u0000lm\u0005r\u0000\u0000mn\u0005u\u0000"+
		"\u0000no\u0005e\u0000\u0000o\u0012\u0001\u0000\u0000\u0000pq\u0005f\u0000"+
		"\u0000qr\u0005a\u0000\u0000rs\u0005l\u0000\u0000st\u0005s\u0000\u0000"+
		"tu\u0005e\u0000\u0000u\u0014\u0001\u0000\u0000\u0000vw\u0005=\u0000\u0000"+
		"w\u0016\u0001\u0000\u0000\u0000xy\u0005+\u0000\u0000y\u0018\u0001\u0000"+
		"\u0000\u0000z{\u0005-\u0000\u0000{\u001a\u0001\u0000\u0000\u0000|}\u0005"+
		"*\u0000\u0000}\u001c\u0001\u0000\u0000\u0000~\u007f\u0005/\u0000\u0000"+
		"\u007f\u001e\u0001\u0000\u0000\u0000\u0080\u0081\u0005&\u0000\u0000\u0081"+
		"\u0082\u0005&\u0000\u0000\u0082 \u0001\u0000\u0000\u0000\u0083\u0084\u0005"+
		"|\u0000\u0000\u0084\u0085\u0005|\u0000\u0000\u0085\"\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0005=\u0000\u0000\u0087\u0088\u0005=\u0000\u0000\u0088"+
		"$\u0001\u0000\u0000\u0000\u0089\u008a\u0005!\u0000\u0000\u008a\u008b\u0005"+
		"=\u0000\u0000\u008b&\u0001\u0000\u0000\u0000\u008c\u008d\u0005!\u0000"+
		"\u0000\u008d(\u0001\u0000\u0000\u0000\u008e\u008f\u0005<\u0000\u0000\u008f"+
		"*\u0001\u0000\u0000\u0000\u0090\u0091\u0005>\u0000\u0000\u0091,\u0001"+
		"\u0000\u0000\u0000\u0092\u0093\u0005<\u0000\u0000\u0093\u0094\u0005=\u0000"+
		"\u0000\u0094.\u0001\u0000\u0000\u0000\u0095\u0096\u0005>\u0000\u0000\u0096"+
		"\u0097\u0005=\u0000\u0000\u00970\u0001\u0000\u0000\u0000\u0098\u0099\u0005"+
		"(\u0000\u0000\u00992\u0001\u0000\u0000\u0000\u009a\u009b\u0005)\u0000"+
		"\u0000\u009b4\u0001\u0000\u0000\u0000\u009c\u009d\u0005{\u0000\u0000\u009d"+
		"6\u0001\u0000\u0000\u0000\u009e\u009f\u0005}\u0000\u0000\u009f8\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0005,\u0000\u0000\u00a1:\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a3\u0005;\u0000\u0000\u00a3<\u0001\u0000\u0000\u0000\u00a4"+
		"\u00a6\u0007\u0000\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a6"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a8>\u0001\u0000\u0000\u0000\u00a9\u00ad"+
		"\u0007\u0001\u0000\u0000\u00aa\u00ac\u0007\u0002\u0000\u0000\u00ab\u00aa"+
		"\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae@\u0001"+
		"\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00b2\u0007"+
		"\u0003\u0000\u0000\u00b1\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b3\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b6\u0006"+
		" \u0000\u0000\u00b6B\u0001\u0000\u0000\u0000\u0004\u0000\u00a7\u00ad\u00b3"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}