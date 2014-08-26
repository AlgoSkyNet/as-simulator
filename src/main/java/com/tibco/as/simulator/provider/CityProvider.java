package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;

public class CityProvider implements IValueProvider {

	private DataFactory df;

	public CityProvider(DataFactory df) {
		this.df = df;
	}

	@Override
	public String getValue() {
		return df.getCity();
	}

}
