package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;

public class CharProvider implements IValueProvider {

	private DataFactory df;

	public CharProvider(DataFactory df) {
		this.df = df;
	}

	@Override
	public Character getValue() {
		return df.getRandomChar();
	}

}
