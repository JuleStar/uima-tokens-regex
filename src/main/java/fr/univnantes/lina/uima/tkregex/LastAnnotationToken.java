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
package fr.univnantes.lina.uima.tkregex;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

public class LastAnnotationToken implements AnnotationFS {
	
	private LastAnnotationToken() {
		super();
	}

	@Override
	public Object clone() {
		return INSTANCE;
	}
	
	public static final LastAnnotationToken INSTANCE = new LastAnnotationToken();
	
	@Override
	public int getBegin() {
		return 0;
	}

	@Override
	public int getEnd() {
		return 0;
	}

	@Override
	public String getCoveredText() {
		return "$";
	}

	@Override
	public CAS getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFeatureValue(Feature feat, FeatureStructure fs)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FeatureStructure getFeatureValue(Feature feat)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStringValue(Feature feat, String s)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStringValue(Feature f) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFloatValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFloatValue(Feature feat, float f) throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIntValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setIntValue(Feature feat, int i) throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte getByteValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setByteValue(Feature feat, byte i) throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBooleanValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBooleanValue(Feature feat, boolean i)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public short getShortValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShortValue(Feature feat, short i) throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getLongValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLongValue(Feature feat, long i) throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDoubleValue(Feature feat) throws CASRuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDoubleValue(Feature feat, double i)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFeatureValueAsString(Feature feat)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFeatureValueFromString(Feature feat, String s)
			throws CASRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CAS getCAS() {
		// TODO Auto-generated method stub
		return null;
	}

}
