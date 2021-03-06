/*******************************************************************************
 * Copyright 2015-2017 - CNRS (Centre National de Recherche Scientifique)
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


import com.google.common.base.Charsets;
import fr.univnantes.lina.uima.tkregex.antlr.AutomataParserListener;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexLexer;
import fr.univnantes.lina.uima.tkregex.antlr.generated.UimaTokenRegexParser;
import fr.univnantes.lina.uima.tkregex.model.automata.Rule;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TermSuiteFrenchMWTParserSpec {
	
	private static final Path FRENCH_MWT_RULES = AutomatonTests.RESOURCES.resolve("french-multi-word-rule-system.regex");
	private List<Rule> rules;

	@Before
	public void initAutomata() throws IOException {
		CharStream input = CharStreams.fromPath(FRENCH_MWT_RULES, Charsets.UTF_8);
		UimaTokenRegexLexer lexer = new UimaTokenRegexLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		UimaTokenRegexParser parser = new UimaTokenRegexParser(tokens);

		AutomataParserListener listener = new AutomataParserListener( parser, FRENCH_MWT_RULES.toUri().toURL() );
		ParseTreeWalker.DEFAULT.walk(listener, parser.ruleList());
		this.rules = listener.getRules();

	}
	

	@Test
	public void testParseExpressionMatcherRegex() {
		assertThat(rules)
			.hasSize(35);
	}
}
