// Generated from UimaTokenRegex.g4 by ANTLR 4.7

    package fr.univnantes.lina.uima.tkregex.antlr.generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UimaTokenRegexLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, IgnoreMatcher=14, IntegerLiteral=15, 
		NonZeroDigit=16, Digit=17, BooleanLiteral=18, StringLiteral=19, FloatingPointLiteral=20, 
		LCURL=21, RCURL=22, LPAREN=23, RPAREN=24, LBRACK=25, RBRACK=26, SEMI=27, 
		QUESTION=28, COLON=29, MUL=30, PLUS=31, AND=32, OR=33, GT=34, LT=35, EQUAL=36, 
		LE=37, GE=38, NOTEQUAL=39, IN=40, IMPORT=41, USE=42, SET=43, JAVA_MATCHER=44, 
		RESOURCE=45, LINE_COMMENT=46, MATCHER=47, RULE=48, Identifier=49, IdentifierPart=50, 
		WS=51, Regex=52;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "IgnoreMatcher", "IntegerLiteral", 
		"NonZeroDigit", "Digit", "BooleanLiteral", "StringLiteral", "StringCharacters", 
		"StringCharacter", "EscapeSequence", "FloatingPointLiteral", "LCURL", 
		"RCURL", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "SEMI", "QUESTION", "COLON", 
		"MUL", "PLUS", "AND", "OR", "GT", "LT", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"IN", "IMPORT", "USE", "SET", "JAVA_MATCHER", "RESOURCE", "LINE_COMMENT", 
		"MATCHER", "RULE", "Identifier", "IdentifierPart", "FirstLetter", "JavaLetterOrDigit", 
		"WS", "Regex"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'as'", "'='", "'text'", "'==='", "'list'", "'yaml'", "','", "'json'", 
		"'csv'", "'tsv'", "'nin'", "'inIgnoreCase'", "'ninIgnoreCase'", "'~'", 
		null, null, null, null, null, null, "'{'", "'}'", "'('", "')'", "'['", 
		"']'", "';'", "'?'", "':'", "'*'", "'+'", "'&'", "'|'", "'>'", "'<'", 
		"'=='", "'<='", "'>='", "'!='", "'in'", "'import'", "'use'", "'set'", 
		"'java-matcher'", "'resource'", null, "'matcher'", "'rule'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "IgnoreMatcher", "IntegerLiteral", "NonZeroDigit", "Digit", 
		"BooleanLiteral", "StringLiteral", "FloatingPointLiteral", "LCURL", "RCURL", 
		"LPAREN", "RPAREN", "LBRACK", "RBRACK", "SEMI", "QUESTION", "COLON", "MUL", 
		"PLUS", "AND", "OR", "GT", "LT", "EQUAL", "LE", "GE", "NOTEQUAL", "IN", 
		"IMPORT", "USE", "SET", "JAVA_MATCHER", "RESOURCE", "LINE_COMMENT", "MATCHER", 
		"RULE", "Identifier", "IdentifierPart", "WS", "Regex"
	};
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


	public UimaTokenRegexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "UimaTokenRegex.g4"; }

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

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 53:
			return FirstLetter_sempred((RuleContext)_localctx, predIndex);
		case 54:
			return JavaLetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean FirstLetter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean JavaLetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\66\u018b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\3\2\3\2\3\2\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\20\3\20\3\20\7\20\u00c1\n\20\f\20\16\20\u00c4\13\20"+
		"\5\20\u00c6\n\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\5\23\u00d5\n\23\3\24\3\24\5\24\u00d9\n\24\3\24\3\24\3\25\6"+
		"\25\u00de\n\25\r\25\16\25\u00df\3\26\3\26\5\26\u00e4\n\26\3\27\3\27\3"+
		"\27\3\30\6\30\u00ea\n\30\r\30\16\30\u00eb\3\30\3\30\6\30\u00f0\n\30\r"+
		"\30\16\30\u00f1\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3"+
		"(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3.\3"+
		".\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62"+
		"\7\62\u0148\n\62\f\62\16\62\u014b\13\62\3\62\3\62\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\7\65\u015f"+
		"\n\65\f\65\16\65\u0162\13\65\3\66\3\66\7\66\u0166\n\66\f\66\16\66\u0169"+
		"\13\66\3\67\3\67\3\67\3\67\3\67\3\67\5\67\u0171\n\67\38\38\38\38\38\3"+
		"8\58\u0179\n8\39\69\u017c\n9\r9\169\u017d\39\39\3:\3:\3:\7:\u0185\n:\f"+
		":\16:\u0188\13:\3:\3:\2\2;\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\2+\2-\2/\26\61\27\63"+
		"\30\65\31\67\329\33;\34=\35?\36A\37C E!G\"I#K$M%O&Q\'S(U)W*Y+[,]-_.a/"+
		"c\60e\61g\62i\63k\64m\2o\2q\65s\66\3\2\16\3\2\63;\3\2\62;\4\2$$^^\n\2"+
		"$$))^^ddhhppttvv\4\2\f\f\17\17\5\2C\\aac|\4\2\2\u0101\ud802\udc01\3\2"+
		"\ud802\udc01\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\17\17\"\"\3\2\61"+
		"\61\2\u0197\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2"+
		"\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y"+
		"\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2"+
		"\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\3u\3\2\2\2"+
		"\5x\3\2\2\2\7z\3\2\2\2\t\177\3\2\2\2\13\u0083\3\2\2\2\r\u0088\3\2\2\2"+
		"\17\u008d\3\2\2\2\21\u008f\3\2\2\2\23\u0094\3\2\2\2\25\u0098\3\2\2\2\27"+
		"\u009c\3\2\2\2\31\u00a0\3\2\2\2\33\u00ad\3\2\2\2\35\u00bb\3\2\2\2\37\u00c5"+
		"\3\2\2\2!\u00c7\3\2\2\2#\u00c9\3\2\2\2%\u00d4\3\2\2\2\'\u00d6\3\2\2\2"+
		")\u00dd\3\2\2\2+\u00e3\3\2\2\2-\u00e5\3\2\2\2/\u00e9\3\2\2\2\61\u00f3"+
		"\3\2\2\2\63\u00f5\3\2\2\2\65\u00f7\3\2\2\2\67\u00f9\3\2\2\29\u00fb\3\2"+
		"\2\2;\u00fd\3\2\2\2=\u00ff\3\2\2\2?\u0101\3\2\2\2A\u0103\3\2\2\2C\u0105"+
		"\3\2\2\2E\u0107\3\2\2\2G\u0109\3\2\2\2I\u010b\3\2\2\2K\u010d\3\2\2\2M"+
		"\u010f\3\2\2\2O\u0111\3\2\2\2Q\u0114\3\2\2\2S\u0117\3\2\2\2U\u011a\3\2"+
		"\2\2W\u011d\3\2\2\2Y\u0120\3\2\2\2[\u0127\3\2\2\2]\u012b\3\2\2\2_\u012f"+
		"\3\2\2\2a\u013c\3\2\2\2c\u0145\3\2\2\2e\u014e\3\2\2\2g\u0156\3\2\2\2i"+
		"\u015b\3\2\2\2k\u0163\3\2\2\2m\u0170\3\2\2\2o\u0178\3\2\2\2q\u017b\3\2"+
		"\2\2s\u0181\3\2\2\2uv\7c\2\2vw\7u\2\2w\4\3\2\2\2xy\7?\2\2y\6\3\2\2\2z"+
		"{\7v\2\2{|\7g\2\2|}\7z\2\2}~\7v\2\2~\b\3\2\2\2\177\u0080\7?\2\2\u0080"+
		"\u0081\7?\2\2\u0081\u0082\7?\2\2\u0082\n\3\2\2\2\u0083\u0084\7n\2\2\u0084"+
		"\u0085\7k\2\2\u0085\u0086\7u\2\2\u0086\u0087\7v\2\2\u0087\f\3\2\2\2\u0088"+
		"\u0089\7{\2\2\u0089\u008a\7c\2\2\u008a\u008b\7o\2\2\u008b\u008c\7n\2\2"+
		"\u008c\16\3\2\2\2\u008d\u008e\7.\2\2\u008e\20\3\2\2\2\u008f\u0090\7l\2"+
		"\2\u0090\u0091\7u\2\2\u0091\u0092\7q\2\2\u0092\u0093\7p\2\2\u0093\22\3"+
		"\2\2\2\u0094\u0095\7e\2\2\u0095\u0096\7u\2\2\u0096\u0097\7x\2\2\u0097"+
		"\24\3\2\2\2\u0098\u0099\7v\2\2\u0099\u009a\7u\2\2\u009a\u009b\7x\2\2\u009b"+
		"\26\3\2\2\2\u009c\u009d\7p\2\2\u009d\u009e\7k\2\2\u009e\u009f\7p\2\2\u009f"+
		"\30\3\2\2\2\u00a0\u00a1\7k\2\2\u00a1\u00a2\7p\2\2\u00a2\u00a3\7K\2\2\u00a3"+
		"\u00a4\7i\2\2\u00a4\u00a5\7p\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7\7t\2\2"+
		"\u00a7\u00a8\7g\2\2\u00a8\u00a9\7E\2\2\u00a9\u00aa\7c\2\2\u00aa\u00ab"+
		"\7u\2\2\u00ab\u00ac\7g\2\2\u00ac\32\3\2\2\2\u00ad\u00ae\7p\2\2\u00ae\u00af"+
		"\7k\2\2\u00af\u00b0\7p\2\2\u00b0\u00b1\7K\2\2\u00b1\u00b2\7i\2\2\u00b2"+
		"\u00b3\7p\2\2\u00b3\u00b4\7q\2\2\u00b4\u00b5\7t\2\2\u00b5\u00b6\7g\2\2"+
		"\u00b6\u00b7\7E\2\2\u00b7\u00b8\7c\2\2\u00b8\u00b9\7u\2\2\u00b9\u00ba"+
		"\7g\2\2\u00ba\34\3\2\2\2\u00bb\u00bc\7\u0080\2\2\u00bc\36\3\2\2\2\u00bd"+
		"\u00c6\7\62\2\2\u00be\u00c2\5!\21\2\u00bf\u00c1\5#\22\2\u00c0\u00bf\3"+
		"\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3"+
		"\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5\u00bd\3\2\2\2\u00c5\u00be\3\2"+
		"\2\2\u00c6 \3\2\2\2\u00c7\u00c8\t\2\2\2\u00c8\"\3\2\2\2\u00c9\u00ca\t"+
		"\3\2\2\u00ca$\3\2\2\2\u00cb\u00cc\7v\2\2\u00cc\u00cd\7t\2\2\u00cd\u00ce"+
		"\7w\2\2\u00ce\u00d5\7g\2\2\u00cf\u00d0\7h\2\2\u00d0\u00d1\7c\2\2\u00d1"+
		"\u00d2\7n\2\2\u00d2\u00d3\7u\2\2\u00d3\u00d5\7g\2\2\u00d4\u00cb\3\2\2"+
		"\2\u00d4\u00cf\3\2\2\2\u00d5&\3\2\2\2\u00d6\u00d8\7$\2\2\u00d7\u00d9\5"+
		")\25\2\u00d8\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00db\7$\2\2\u00db(\3\2\2\2\u00dc\u00de\5+\26\2\u00dd\u00dc\3\2\2\2\u00de"+
		"\u00df\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0*\3\2\2\2"+
		"\u00e1\u00e4\n\4\2\2\u00e2\u00e4\5-\27\2\u00e3\u00e1\3\2\2\2\u00e3\u00e2"+
		"\3\2\2\2\u00e4,\3\2\2\2\u00e5\u00e6\7^\2\2\u00e6\u00e7\t\5\2\2\u00e7."+
		"\3\2\2\2\u00e8\u00ea\5#\22\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ef\7\60"+
		"\2\2\u00ee\u00f0\5#\22\2\u00ef\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\60\3\2\2\2\u00f3\u00f4\7}\2\2"+
		"\u00f4\62\3\2\2\2\u00f5\u00f6\7\177\2\2\u00f6\64\3\2\2\2\u00f7\u00f8\7"+
		"*\2\2\u00f8\66\3\2\2\2\u00f9\u00fa\7+\2\2\u00fa8\3\2\2\2\u00fb\u00fc\7"+
		"]\2\2\u00fc:\3\2\2\2\u00fd\u00fe\7_\2\2\u00fe<\3\2\2\2\u00ff\u0100\7="+
		"\2\2\u0100>\3\2\2\2\u0101\u0102\7A\2\2\u0102@\3\2\2\2\u0103\u0104\7<\2"+
		"\2\u0104B\3\2\2\2\u0105\u0106\7,\2\2\u0106D\3\2\2\2\u0107\u0108\7-\2\2"+
		"\u0108F\3\2\2\2\u0109\u010a\7(\2\2\u010aH\3\2\2\2\u010b\u010c\7~\2\2\u010c"+
		"J\3\2\2\2\u010d\u010e\7@\2\2\u010eL\3\2\2\2\u010f\u0110\7>\2\2\u0110N"+
		"\3\2\2\2\u0111\u0112\7?\2\2\u0112\u0113\7?\2\2\u0113P\3\2\2\2\u0114\u0115"+
		"\7>\2\2\u0115\u0116\7?\2\2\u0116R\3\2\2\2\u0117\u0118\7@\2\2\u0118\u0119"+
		"\7?\2\2\u0119T\3\2\2\2\u011a\u011b\7#\2\2\u011b\u011c\7?\2\2\u011cV\3"+
		"\2\2\2\u011d\u011e\7k\2\2\u011e\u011f\7p\2\2\u011fX\3\2\2\2\u0120\u0121"+
		"\7k\2\2\u0121\u0122\7o\2\2\u0122\u0123\7r\2\2\u0123\u0124\7q\2\2\u0124"+
		"\u0125\7t\2\2\u0125\u0126\7v\2\2\u0126Z\3\2\2\2\u0127\u0128\7w\2\2\u0128"+
		"\u0129\7u\2\2\u0129\u012a\7g\2\2\u012a\\\3\2\2\2\u012b\u012c\7u\2\2\u012c"+
		"\u012d\7g\2\2\u012d\u012e\7v\2\2\u012e^\3\2\2\2\u012f\u0130\7l\2\2\u0130"+
		"\u0131\7c\2\2\u0131\u0132\7x\2\2\u0132\u0133\7c\2\2\u0133\u0134\7/\2\2"+
		"\u0134\u0135\7o\2\2\u0135\u0136\7c\2\2\u0136\u0137\7v\2\2\u0137\u0138"+
		"\7e\2\2\u0138\u0139\7j\2\2\u0139\u013a\7g\2\2\u013a\u013b\7t\2\2\u013b"+
		"`\3\2\2\2\u013c\u013d\7t\2\2\u013d\u013e\7g\2\2\u013e\u013f\7u\2\2\u013f"+
		"\u0140\7q\2\2\u0140\u0141\7w\2\2\u0141\u0142\7t\2\2\u0142\u0143\7e\2\2"+
		"\u0143\u0144\7g\2\2\u0144b\3\2\2\2\u0145\u0149\7%\2\2\u0146\u0148\n\6"+
		"\2\2\u0147\u0146\3\2\2\2\u0148\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149"+
		"\u014a\3\2\2\2\u014a\u014c\3\2\2\2\u014b\u0149\3\2\2\2\u014c\u014d\b\62"+
		"\2\2\u014dd\3\2\2\2\u014e\u014f\7o\2\2\u014f\u0150\7c\2\2\u0150\u0151"+
		"\7v\2\2\u0151\u0152\7e\2\2\u0152\u0153\7j\2\2\u0153\u0154\7g\2\2\u0154"+
		"\u0155\7t\2\2\u0155f\3\2\2\2\u0156\u0157\7t\2\2\u0157\u0158\7w\2\2\u0158"+
		"\u0159\7n\2\2\u0159\u015a\7g\2\2\u015ah\3\2\2\2\u015b\u0160\5k\66\2\u015c"+
		"\u015d\7\60\2\2\u015d\u015f\5k\66\2\u015e\u015c\3\2\2\2\u015f\u0162\3"+
		"\2\2\2\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161j\3\2\2\2\u0162\u0160"+
		"\3\2\2\2\u0163\u0167\5m\67\2\u0164\u0166\5o8\2\u0165\u0164\3\2\2\2\u0166"+
		"\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0167\u0168\3\2\2\2\u0168l\3\2\2\2"+
		"\u0169\u0167\3\2\2\2\u016a\u0171\t\7\2\2\u016b\u016c\n\b\2\2\u016c\u0171"+
		"\6\67\2\2\u016d\u016e\t\t\2\2\u016e\u016f\t\n\2\2\u016f\u0171\6\67\3\2"+
		"\u0170\u016a\3\2\2\2\u0170\u016b\3\2\2\2\u0170\u016d\3\2\2\2\u0171n\3"+
		"\2\2\2\u0172\u0179\t\13\2\2\u0173\u0174\n\b\2\2\u0174\u0179\68\4\2\u0175"+
		"\u0176\t\t\2\2\u0176\u0177\t\n\2\2\u0177\u0179\68\5\2\u0178\u0172\3\2"+
		"\2\2\u0178\u0173\3\2\2\2\u0178\u0175\3\2\2\2\u0179p\3\2\2\2\u017a\u017c"+
		"\t\f\2\2\u017b\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017b\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0180\b9\2\2\u0180r\3\2\2\2\u0181"+
		"\u0186\7\61\2\2\u0182\u0185\n\r\2\2\u0183\u0185\5-\27\2\u0184\u0182\3"+
		"\2\2\2\u0184\u0183\3\2\2\2\u0185\u0188\3\2\2\2\u0186\u0184\3\2\2\2\u0186"+
		"\u0187\3\2\2\2\u0187\u0189\3\2\2\2\u0188\u0186\3\2\2\2\u0189\u018a\7\61"+
		"\2\2\u018at\3\2\2\2\23\2\u00c2\u00c5\u00d4\u00d8\u00df\u00e3\u00eb\u00f1"+
		"\u0149\u0160\u0167\u0170\u0178\u017d\u0184\u0186\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}