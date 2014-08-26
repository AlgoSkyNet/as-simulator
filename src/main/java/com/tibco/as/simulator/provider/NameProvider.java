package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;

public class NameProvider implements IValueProvider {

	private DataFactory df;

	public NameProvider(DataFactory df) {
		this.df = df;
	}

	@Override
	public String getValue() {
		return df.getName();
	}

}
