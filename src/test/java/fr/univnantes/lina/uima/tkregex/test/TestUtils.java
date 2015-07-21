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
package fr.univnantes.lina.uima.tkregex.test;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.tcas.Annotation;
import org.mockito.Mockito;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import fr.univnantes.lina.uima.tkregex.AnnotationMatcher;
import fr.univnantes.lina.uima.tkregex.Automaton;
import fr.univnantes.lina.uima.tkregex.AutomatonBuilder;
import fr.univnantes.lina.uima.tkregex.AutomatonFactory;
import fr.univnantes.lina.uima.tkregex.AutomatonQuantifier;
import fr.univnantes.lina.uima.tkregex.RegexOccurrence;
import fr.univnantes.lina.uima.tkregex.Ignorer;
import fr.univnantes.lina.uima.tkregex.Labeller;
import fr.univnantes.lina.uima.tkregex.RecognitionHandler;
import fr.univnantes.lina.uima.tkregex.State;

public class TestUtils {
	
	public static class LetterMatcher implements AnnotationMatcher {
		private String letter;
		public LetterMatcher(String letter) {
			super();
			this.letter = letter;
		}
		public boolean match(AnnotationFS annotation) {
			return annotation.getCoveredText().equals(letter);
		};
		/* Label aspect */
		private Labeller labeller = new Labeller();
		public String getLabel() {
			return labeller.getLabel();
		}
		public void setLabel(String label) {
			labeller.setLabel(label);
		}
		/* End of Label aspect */
		/* Ignorer aspect */
		private Ignorer ignorer = new Ignorer();
		public boolean isIgnoreMatcher() {
			return ignorer.isIgnoreMatcher();
		}
		public void setIgnoreMatcher(boolean ignoreMatcher) {
			ignorer.setIgnoreMatcher(ignoreMatcher);
		}
		/* End of Ignorer aspect */
	}

	private static AnnotationMatcher anyMatcher = new AnnotationMatcher() {
		public boolean match(AnnotationFS annotation) {
			return true;
		}
		@Override
		public String getLabel() {
			return null; // the label will be covered text
		}
		@Override
		public void setLabel(String label) {
		}
		/* Ignorer aspect */
		private Ignorer ignorer = new Ignorer();
		public boolean isIgnoreMatcher() {
			return ignorer.isIgnoreMatcher();
		}
		public void setIgnoreMatcher(boolean ignoreMatcher) {
			ignorer.setIgnoreMatcher(ignoreMatcher);
		}
		/* End of Ignorer aspect */
	};

	public static Automaton getSimpleLetterAutomaton(String letter) {
		AutomatonBuilder builder = new AutomatonBuilder();
		State s = builder.addInitState();
		State s2 = builder.addAcceptingState();
		LetterMatcher m = new LetterMatcher(letter);
		m.setLabel(letter);
		builder.addTransition(s, m, s2);
		Automaton automaton = builder.getAutomaton();
		return automaton;
	}

	public static Automaton automaton(String regexp) {
		return automaton(regexp, new HashMap<String, String>());
	}

	public static Automaton automaton(String regexp, Map<String, String> labels) {
		StringTokenizer st = new StringTokenizer(regexp);
		List<Automaton> list = new LinkedList<Automaton>();
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			String firstChar = token.substring(0, 1);
			
			String transitionLetter = firstChar;
			boolean ignore = false;
			if(firstChar.equals("~")) {
				ignore = true;
				transitionLetter = token.substring(1, 2);
			}
			LetterMatcher letterMatcher = new LetterMatcher(transitionLetter);
			AnnotationMatcher matcher = transitionLetter.equals(".") ? anyMatcher : letterMatcher;
			matcher.setIgnoreMatcher(ignore);
			if(labels.get(transitionLetter) != null)
				matcher.setLabel(labels.get(transitionLetter));
			else
				matcher.setLabel(transitionLetter);
			
			String quant = token.substring(firstChar.equals("~") ? 2 : 1, token.length());
			list.add(
					AutomatonFactory.createQuantifiedAutomaton(
						AutomatonFactory.createSimpleAutomaton(matcher), 
						getQuantifier(quant))
				);
		}
		return AutomatonFactory.createConcatenation(list);
	}
	
	private static AutomatonQuantifier getQuantifier(String quantifierStr) {
		if(quantifierStr == null || quantifierStr.isEmpty())
			return new AutomatonQuantifier(AutomatonQuantifier.ONE);
		switch(quantifierStr) {
		case "?":
			return new AutomatonQuantifier(AutomatonQuantifier.ZERO_ONE);
		case "+":
			return new AutomatonQuantifier(AutomatonQuantifier.ONE_N);
		case "*":
			return new AutomatonQuantifier(AutomatonQuantifier.ZERO_N);
		default:
			StringTokenizer st = new StringTokenizer(quantifierStr, ",");
			if(st.countTokens() == 2)
				return new AutomatonQuantifier(
						AutomatonQuantifier.N, 
						Integer.parseInt(st.nextToken()), 
						Integer.parseInt(st.nextToken()));
			else if(st.countTokens() == 1) {
				int n = Integer.parseInt(st.nextToken());
				return new AutomatonQuantifier(AutomatonQuantifier.N, n, n);
			} else
				throw new RuntimeException("Unknown quantifier: " + quantifierStr);
		}
	}
	
	public static String matchSequence(Automaton a, String str) {
		final List<RegexOccurrence> episodes = Lists.newLinkedList();
		RecognitionHandler rh = new  RecognitionHandler() {
			@Override
			public void recognizedEpisode(RegexOccurrence episode) {
				episodes.add(episode);
			}
		};
		a.addRecognitionHandler(rh);
		StringTokenizer st = new StringTokenizer(str, " ");
		int i = 0;
		while(st.hasMoreTokens()) {
			String s = st.nextToken();
			Annotation anno = Mockito.mock(Annotation.class);
			Mockito.when(anno.getCoveredText()).thenReturn(s);
			Mockito.when(anno.getBegin()).thenReturn(i);
			Mockito.when(anno.getEnd()).thenReturn(i+1);
			a.nextAnnotation(anno);
			i += 1;
		}
		a.finish();
		List<String> results = Lists.newLinkedList();
		for(RegexOccurrence e:episodes) {
			String result = Joiner.on(' ').join(e.getLabels());
			AnnotationFS first;
			AnnotationFS last;
			if(e.size() > 0) {
				first = e.getLabelledAnnotations().get(0).getAnnotation();
				last = e.getLabelledAnnotations().get(e.size()-1).getAnnotation();
				result += " (" + first.getBegin() + "," + last.getEnd() + ")";
			} else {
				result += " (none)";
			}
				
			results.add(result);
		}
		return Joiner.on(" | ").join(results);
	}

	public static void javaTest(String string, String patternStr, String expectedOcc) {
		Matcher matcher = Pattern.compile(patternStr).matcher(string);
		List<String> results = Lists.newLinkedList();
		while(matcher.find()) {
			String match = matcher.group();
			results.add(match + " ("+matcher.start()+","+matcher.end()+")");
		}
		String occ = Joiner.on(" | ").join(results);
		assertEquals(expectedOcc, occ);
	}
	
	public static void automatonTest(String sequence, String regexp, String result) {
		automatonTest(
				sequence, 
				automaton(regexp), 
				result);
	}

	public static void automatonTest(String sequence, Automaton a, String result) {
		assertEquals(
				result, 
				matchSequence(a, sequence));
	}

}
