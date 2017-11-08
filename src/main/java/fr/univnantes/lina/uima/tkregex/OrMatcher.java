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
package fr.univnantes.lina.uima.tkregex;

import java.util.Collection;
import java.util.List;

import org.apache.uima.cas.text.AnnotationFS;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class OrMatcher implements AnnotationMatcher {
	
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
	
	private List<AnnotationMatcher> expresions = Lists.newArrayList();
	
	
	public OrMatcher(Iterable<AnnotationMatcher> expresions) {
		super();
		this.expresions = Lists.newArrayList(expresions);
	}
	public List<AnnotationMatcher> getDisjonctionParts() {
		return expresions;
	}


	public boolean addConjonctionPart(AnnotationMatcher e) {
		return expresions.add(e);
	}


	public boolean addAllDijonctionParts(Collection<? extends AnnotationMatcher> c) {
		return expresions.addAll(c);
	}
	
	@Override
	public boolean matches(AnnotationFS annotation) {
		for(AnnotationMatcher matcher:expresions) {
			if(matcher.matches(annotation))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "OrMatcher: (" + Joiner.on(" & ").join(expresions) + ")";
	}
}
