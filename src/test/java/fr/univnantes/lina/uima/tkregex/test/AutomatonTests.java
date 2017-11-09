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


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(Suite.class)
@SuiteClasses({ 
	AutomatonParserTestCase.class,
	AutomatonTestCase.class,
	AutomatonFactoryTestCase.class,
	TermSuiteFrenchMWTParserTestCase.class,
	OnCasFunctionalTestCase.class,
	RegexCoveredTextMatcherTestCase.class,
	ArrayMatcherTestCase.class,
	}
)

public class AutomatonTests {

	public static final Path RESOURCES = Paths.get("src", "test", "resources");

}
