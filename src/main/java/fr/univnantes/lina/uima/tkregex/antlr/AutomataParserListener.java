/*******************************************************************************
 * Copyright 2015 - CNRS (Centre National de Recherche Scientifique)
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 *******************************************************************************/
package fr.univnantes.lina.uima.tkregex.antlr;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.FeatureDescription;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.univnantes.lina.uima.tkregex.AndMatcher;
import fr.univnantes.lina.uima.tkregex.AnnotationMatcher;
import fr.univnantes.lina.uima.tkregex.Automaton;
import fr.univnantes.lina.uima.tkregex.AutomatonFactory;
import fr.univnantes.lina.uima.tkregex.AutomatonQuantifier;
import fr.univnantes.lina.uima.tkregex.CustomMatcher;
import fr.univnantes.lina.uima.tkregex.ExpressionMatcher;
import fr.univnantes.lina.uima.tkregex.OrMatcher;
import fr.univnantes.lina.uima.tkregex.RegexCoveredTextMatcher;
import fr.univnantes.lina.uima.tkregex.Rule;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexListener;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.AndexpressionContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.AtomicExpressionContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.CoveredTextArrayContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.CoveredTextExactlyContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.CoveredTextIgnoreCaseContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.ExpressionContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.FeatureMatcherDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.FeatureNameContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.HeaderBlockContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.ImportDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.JavaMatcherDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.LabelIdentifierContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.LiteralContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.MatcherDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.OperatorContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.OptionDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.OrBranchingDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.OrexpressionContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.QuantifierDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.RuleDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.RuleListContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.RuleNameContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.ShortcutMatcherDeclarationContext;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser.UseDeclarationContext;
import fr.univnantes.lina.uima.tkregex.matchers.StringArrayMatcher;
import fr.univnantes.lina.uima.tkregex.matchers.StringExactlyMatcher;
import fr.univnantes.lina.uima.tkregex.matchers.StringIgnoreCaseMatcher;


public class AutomataParserListener implements UimaTokenRegexListener {
	private static final Pattern M_N_PATTERN = Pattern.compile("\\{(\\d+(,\\d+)?)\\}");

	public static final String OPTION_INLINE = "inline";
	
	private List<String> OPTION_NAMES = ImmutableList.of(
			OPTION_INLINE
			);
	private Map<String, String> options = Maps.newHashMap();;
	
	private List<Rule> rules;

	private Parser parser;
	
	private Map<String, AnnotationMatcher> shortcutMatchers;
	
	private boolean allowMatchingEmptySequences = false;

	private boolean allowInlineMatcherDeclaration = true;

	private boolean inRule = false;
	
	private TypeSystemDescription typeSystemDescription = null;
	
	private TypeDescription usedType = null;
	
	public Map<String, CustomMatcher> getCustomJavaMatchers() {
		return declaredJavaMatchers;
	}
	
	public List<Rule> getRules() {
		return ImmutableList.copyOf(this.rules);
	}
	
	public Map<String, AnnotationMatcher> getShortcutMatchers() {
		return shortcutMatchers;
	}
	
	public TypeDescription getIteraredType() {
		return usedType;
	}
	
	public AutomataParserListener(Parser parser) {
		super();
		this.parser = parser;
	}

	public void setAllowOnlineMatcherDeclaration(
			boolean allowOnlineMatcherDeclaration) {
		this.allowInlineMatcherDeclaration = allowOnlineMatcherDeclaration;
	}
	
	public void setAllowMatchingEmptySequences(
			boolean allowMatchingEmptySequences) {
		this.allowMatchingEmptySequences = allowMatchingEmptySequences;
	}

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {}

	@Override
	public void visitErrorNode(ErrorNode arg0) {}

	@Override
	public void visitTerminal(TerminalNode arg0) {}

	@Override
	public void enterExpression(ExpressionContext ctx) {}

	@Override
	public void exitExpression(ExpressionContext ctx) {
	}

	@Override
	public void enterRuleDeclaration(RuleDeclarationContext ctx) {
		this.inRule  = true;
	}
	
	private Automaton toOrBanchingAutomaton(OrBranchingDeclarationContext ctx) {
		AutomatonQuantifier quantifier = ctx.quantifierDeclaration() != null ?
				parseQuantifier(ctx.quantifierDeclaration()) :
					AutomatonQuantifier.getOneOne();
		
		Automaton automaton = null;
		
		if(ctx.matcherDeclaration() != null)
			automaton = toMatcherDeclarationAutomaton(ctx.matcherDeclaration());
		else {
			List<Automaton> orAutomata = ctx.orBranchingDeclaration().stream()
					.map(this::toOrBanchingAutomaton)
					.collect(Collectors.toList());
			automaton = AutomatonFactory.createOrAutomaton(orAutomata);
		}
		return AutomatonFactory.createQuantifiedAutomaton(automaton, quantifier);
	}

	private Automaton toMatcherDeclarationAutomaton(MatcherDeclarationContext ctx) {
		
		if(!this.allowInlineMatcherDeclaration && ctx.Identifier() == null && this.inRule) 
			throw new AutomataParsingException("undefined matcher "
					+ " at line " + ctx.getStart().getLine() + ":" + ctx.getText());
		
		AnnotationMatcher annotationMatcher;
		
		if(ctx.Regex() != null) {
			String text = ctx.Regex().getText();
			// remove trailing and heading slashes "/"
			String regex = text.substring(1, text.length() - 1);
			annotationMatcher = new RegexCoveredTextMatcher(regex);
		} else if(ctx.Identifier() != null) {
			String matcherName = ctx.Identifier().getText();
			annotationMatcher = this.shortcutMatchers.get(matcherName);
			if(annotationMatcher == null)
				throw new AutomataParsingException("There is no shortcut for matcher name " + matcherName);
		} else if(ctx.featureMatcherDeclaration() != null) {
			annotationMatcher = toAnnotationMatcher(ctx.featureMatcherDeclaration());
		} else 
			throw new AutomataParsingException("Not a valid matcher context");
		TerminalNode ignoreMatcher = ctx.IgnoreMatcher();
		annotationMatcher.setIgnoreMatcher(ignoreMatcher != null && ignoreMatcher.getText().equalsIgnoreCase("~"));
		return AutomatonFactory.createSimpleAutomaton(annotationMatcher);
	}

	
	private AnnotationMatcher toAnnotationMatcher(FeatureMatcherDeclarationContext ctx) {
		if(ctx.expression() != null)
			return toAnnotationMatcher(ctx.expression());
		else if(ctx.andexpression() != null)
			return toAnnotationMatcher(ctx.andexpression());
		else if(ctx.orexpression() != null)
			return toAnnotationMatcher(ctx.orexpression());
		else 
			return AnnotationMatcher.EMPTY_MATCHER;
	}

	private AnnotationMatcher toAnnotationMatcher(AndexpressionContext ctx) {
		return new AndMatcher(ctx.expression().stream().map(this::toAnnotationMatcher).collect(Collectors.toList()));
	}

	private AnnotationMatcher toAnnotationMatcher(OrexpressionContext ctx) {
		return new OrMatcher(ctx.expression().stream().map(this::toAnnotationMatcher).collect(Collectors.toList()));
	}

	private AnnotationMatcher toAnnotationMatcher(ExpressionContext ctx) {
		if(ctx.atomicExpression() != null) 
			return toAnnotationMatcher(ctx.atomicExpression());
		else if(ctx.andexpression() != null)
			return toAnnotationMatcher(ctx.andexpression());
		else if(ctx.orexpression() != null)
			return toAnnotationMatcher(ctx.orexpression());
		throw new IllegalStateException("Unexpected context for expression: " + ctx);
	}


	private AnnotationMatcher toAnnotationMatcher(AtomicExpressionContext ctx) {
		AnnotationMatcher matcher = null;
		if(ctx.Identifier() != null) {
			String matcherName = ctx.Identifier().getText();
			
			
			if(shortcutMatchers.containsKey(matcherName)) 
				matcher = shortcutMatchers.get(matcherName);
			else if(declaredJavaMatchers.containsKey(matcherName))
				matcher = declaredJavaMatchers.get(matcherName);
			else if(BuiltinMatcher.isRegistered(matcherName))
				matcher = BuiltinMatcher.get(matcherName);
			else
				throw new AutomataParsingException("No such custom nor builtin matcher: " + matcherName);
		} else if(ctx.coveredTextArray() != null) {
			matcher = new StringArrayMatcher(true, coveredTextArray.get());
		} else if(ctx.coveredTextExactly() != null) {
			matcher = new StringExactlyMatcher(coveredTextExactly.get());
		} else if(ctx.coveredTextIgnoreCase() != null) {
			matcher = new StringIgnoreCaseMatcher(coveredTextIgnoredCase.get());
		} else 
			matcher = new ExpressionMatcher(
					toFeature(ctx.featureName()), 
					ctx.operator().getText(), 
					toLiteralValue(ctx.literal()), 
					toLiteralType(ctx.literal())
				);

		return matcher;
	}

	private String toLiteralType(LiteralContext ctx) {
		if(ctx.IntegerLiteral() != null) 
			return ExpressionMatcher.TYPE_INT;
		else if(ctx.BooleanLiteral() != null) 
			return ExpressionMatcher.TYPE_BOOLEAN;
		else if(ctx.FloatingPointLiteral() != null)
			return ExpressionMatcher.TYPE_FLOAT;
		else if(ctx.StringLiteral() != null)
			return ExpressionMatcher.TYPE_STRING;
		else
			throw new AutomataParsingException("Unknown literal type: " + ctx.toStringTree(parser));
	}

	private Object toLiteralValue(LiteralContext ctx) {
		if(ctx.IntegerLiteral() != null)
			return Integer.parseInt(ctx.getText());
		else if(ctx.BooleanLiteral() != null)
			return Boolean.parseBoolean(ctx.getText());
		else if(ctx.FloatingPointLiteral() != null)
			return Float.parseFloat(ctx.getText());
		else if(ctx.StringLiteral() != null)
			return ctx.getText().substring(1, ctx.getText().length()-1); // remove heading and trailing "
		else
			throw new AutomataParsingException("Unknown literal type: " + ctx.toStringTree(parser));
		
	}

	private String toFeature(FeatureNameContext ctx) {
		String feature = resolveFeature(ctx.getText());
		if(feature == null) 
			throw new AutomataParsingException("No such feature: " + ctx.getText());
		return feature;
	}

	@Override
	public void exitRuleDeclaration(RuleDeclarationContext ctx) {
		List<Automaton> subAutomata = ctx.orBranchingDeclaration().stream().map(this::toOrBanchingAutomaton).collect(Collectors.toList());
		Automaton a = AutomatonFactory.createConcatenation(subAutomata);
		RuleNameContext ruleCtx = ctx.ruleName();
		String ruleName = ruleCtx.getText().substring(1, ruleCtx.getText().length()-1);
		Rule rule = new Rule(a, ruleName);
		if(a.matchesEmptySequence() && !allowMatchingEmptySequences )
			throw new AutomataParsingException("The automata " + ruleName + " matches the empty sequence");
		this.rules.add(rule);
		this.inRule = false;
	}

	@Override
	public void enterFeatureName(FeatureNameContext ctx) {
		// do nothing
	}

	@Override
	public void exitFeatureName(FeatureNameContext ctx) {
	}
	
	

	
	protected String resolveFeature(String shortName) {
		String feature = featureMap.get(shortName);
		if(feature == null) 
			throw new AutomataParsingException("No such feature found in type system: " + shortName);
		return feature;
	}

	@Override
	public void enterRuleName(RuleNameContext ctx) {
		// do nothing
	}

	@Override
	public void exitRuleName(RuleNameContext ctx) {
	}

	
	@Override
	public void enterMatcherDeclaration(MatcherDeclarationContext ctx) {
	}
	
	
	@Override
	public void exitMatcherDeclaration(MatcherDeclarationContext ctx) {
	}

	
	@Override
	public void enterOrBranchingDeclaration(OrBranchingDeclarationContext ctx) {
	}

	@Override
	public void exitOrBranchingDeclaration(OrBranchingDeclarationContext ctx) {
	}
	

	
	@Override
	public void enterQuantifierDeclaration(QuantifierDeclarationContext ctx) {
		// do nothing
	}

	@Override
	public void exitQuantifierDeclaration(QuantifierDeclarationContext ctx) {
		
	}

	private AutomatonQuantifier parseQuantifier(QuantifierDeclarationContext ctx) {
		AutomatonQuantifier quantifier = null;
		switch(ctx.getText()) {
		case "*":
			quantifier = AutomatonQuantifier.getZeroN();
			break;
		case "?":
			quantifier = AutomatonQuantifier.getZeroOne();
			break;
		case "+":
			quantifier = AutomatonQuantifier.getOneN();
			break;
		default:
			Matcher m = M_N_PATTERN.matcher(ctx.getText());
			if(m.matches() && m.groupCount() == 3) 
				quantifier = AutomatonQuantifier.getMN(
						Integer.parseInt(m.group(1)),
						Integer.parseInt(m.group(2))
						);
			else if(m.matches() && m.groupCount() == 2) 
				quantifier = AutomatonQuantifier.getN(
						Integer.parseInt(m.group(1))
						);
			else throw new AutomataParsingException("Unrecognized regex: " + ctx.getText());
		}
		return quantifier;
	}

	@Override
	public void enterRuleList(RuleListContext ctx) {
		this.rules = Lists.newLinkedList();
		this.shortcutMatchers = Maps.newHashMap();
	}

	@Override
	public void exitRuleList(RuleListContext ctx) {
	}

	@Override
	public void enterOperator(OperatorContext ctx) {
	}

	@Override
	public void exitOperator(OperatorContext ctx) {
	}

	@Override
	public void enterFeatureMatcherDeclaration(FeatureMatcherDeclarationContext ctx) {
	}

	
	@Override
	public void exitFeatureMatcherDeclaration(FeatureMatcherDeclarationContext ctx) {
	}

	@Override
	public void enterLiteral(LiteralContext ctx) {
		// do nothing
	}

	@Override
	public void exitLiteral(LiteralContext ctx) {
	}

	@Override
	public void enterOrexpression(OrexpressionContext ctx) {
	}

	@Override
	public void exitOrexpression(OrexpressionContext ctx) {
	}

	
	@Override
	public void enterAndexpression(AndexpressionContext ctx) {
	}

	@Override
	public void exitAndexpression(AndexpressionContext ctx) {
	}

	
	
	@Override
	public void enterShortcutMatcherDeclaration(
			ShortcutMatcherDeclarationContext ctx) {
	}

	
	@Override
	public void exitShortcutMatcherDeclaration(
			ShortcutMatcherDeclarationContext ctx) {
		AnnotationMatcher matcher;
		String matcherName = ctx.Identifier().getText();
		if(ctx.featureMatcherDeclaration() != null) {
			matcher = toAnnotationMatcher(ctx.featureMatcherDeclaration());
		} else if(ctx.Regex() !=null) {
			String text = ctx.Regex().getText();
			String regex = text.substring(1, text.length() - 1);
			matcher = new RegexCoveredTextMatcher(regex);
		} else 
			throw new AutomataParsingException("Not a valid shortcutMatcherDeclaration: " + ctx.getText());
		if(this.shortcutMatchers.get(matcherName) != null)
			throw new AutomataParsingException("There is already a matcher named " + matcherName);
		if(ctx.labelIdentifier() == null)
			matcher.setLabel(matcherName);
		else
			matcher.setLabel(ctx.labelIdentifier().getText());
		
		this.shortcutMatchers.put(matcherName, matcher);
	}

	@Override
	public void enterLabelIdentifier(LabelIdentifierContext ctx) {
	}

	@Override
	public void exitLabelIdentifier(LabelIdentifierContext ctx) {
	}


	@Override
	public void enterHeaderBlock(HeaderBlockContext ctx) {
		options = Maps.newHashMap();
	}

	@Override
	public void exitHeaderBlock(HeaderBlockContext ctx) {
		if(options.get(OPTION_INLINE) != null)
			try {
				this.allowInlineMatcherDeclaration = Boolean.parseBoolean(options.get(OPTION_INLINE));
			} catch(Exception e) {
				throw new AutomataParsingException("Bad value for parameter " + OPTION_INLINE + " (expeted true or false)");
			}
		else {
			this.allowInlineMatcherDeclaration = true;
		}
	}

	@Override
	public void enterUseDeclaration(UseDeclarationContext ctx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void exitUseDeclaration(UseDeclarationContext ctx) {
		String value = ctx.Identifier().getText();
		usedType = typeSystemDescription.getType(value);
		if(usedType == null)
			throw new AutomataParsingException("No such type in type system: " + value);
		inferFeatureMap(usedType);
	}
	
	protected Map<String, String> featureMap = Maps.newHashMap();
	
	private void inferFeatureMap(TypeDescription type) {
		// 1 - Recursively add features of the super type
		TypeDescription superType = typeSystemDescription.getType(type.getSupertypeName());
		if(superType != null)
			inferFeatureMap(superType);
		
		// 2 - Add features of this type
		for(FeatureDescription feature:type.getFeatures()) {
			String featureFQN = type.getName() + ":" + feature.getName();
			featureMap.put(feature.getName(), featureFQN);
			featureMap.put(featureFQN, featureFQN);
		}
	}

	@Override
	public void enterOptionDeclaration(OptionDeclarationContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOptionDeclaration(OptionDeclarationContext ctx) {
		String optName = ctx.Identifier().getText();
		if(!OPTION_NAMES.contains(optName))
			throw new AutomataParsingException("Unknown option " + optName);
		options.put(optName, ctx.literal().toString());
	}


			

	@Override
	public void enterImportDeclaration(ImportDeclarationContext ctx) {
		
	}

	@Override
	public void exitImportDeclaration(ImportDeclarationContext ctx) {
		String value = ctx.Identifier().getText();
		String filePath = value.replaceAll("\\.", "/") + ".xml";
		try {
			typeSystemDescription = TypeSystemDescriptionFactory.createTypeSystemDescription();
			typeSystemDescription.resolveImports();
		} catch (Exception e) {
			throw new AutomataParsingException("Failed to load type system " + filePath, e);
		}
	}

	private Map<String, CustomMatcher> declaredJavaMatchers = new ConcurrentHashMap<>();

	@Override
	public void enterJavaMatcherDeclaration(JavaMatcherDeclarationContext ctx) {
		
	}

	@Override
	public void exitJavaMatcherDeclaration(JavaMatcherDeclarationContext ctx) {
		String javaMatcherName = ctx.Identifier().getText();
		CustomMatcher matcher = new CustomMatcher(javaMatcherName);
		declaredJavaMatchers.put(javaMatcherName, matcher);
		
	}


	private Optional<String> coveredTextIgnoredCase = Optional.absent();
	
	@Override
	public void enterCoveredTextIgnoreCase(CoveredTextIgnoreCaseContext ctx) {
		coveredTextIgnoredCase = Optional.absent();
	}

	@Override
	public void exitCoveredTextIgnoreCase(CoveredTextIgnoreCaseContext ctx) {
		coveredTextIgnoredCase = Optional.of(prepareStringLiteral(ctx.StringLiteral().toString()));
	}

	private Optional<String> coveredTextExactly = Optional.absent();

	@Override
	public void enterCoveredTextExactly(CoveredTextExactlyContext ctx) {
		coveredTextExactly = Optional.absent();
	}

	@Override
	public void exitCoveredTextExactly(CoveredTextExactlyContext ctx) {
		coveredTextExactly = Optional.of(prepareStringLiteral(ctx.StringLiteral().toString()));
	}

	private Optional<String[]> coveredTextArray = Optional.absent();

	@Override
	public void enterCoveredTextArray(CoveredTextArrayContext ctx) {
		coveredTextArray = Optional.absent();		
	}

	@Override
	public void exitCoveredTextArray(CoveredTextArrayContext ctx) {
		List<String> values = ctx.StringLiteral().stream()
				.map(TerminalNode::toString)
				.map(this::prepareStringLiteral)
				.collect(toList());
		coveredTextArray = Optional.of(values.toArray(new String[values.size()]));
	}

	private static final String ERR_STRING_LITERAL = "Invalid string literal. Should start and end with double quotes. Got %s";
	private static final String QUOTE = "\"";
	private String prepareStringLiteral(String stringLiteral) {
		Preconditions.checkArgument(stringLiteral.startsWith(QUOTE) && stringLiteral.endsWith(QUOTE),
				ERR_STRING_LITERAL, stringLiteral);
		return stringLiteral.substring(1, stringLiteral.length()-1);
	}

	@Override
	public void enterAtomicExpression(AtomicExpressionContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAtomicExpression(AtomicExpressionContext ctx) {
		// TODO Auto-generated method stub
		
	}
}
