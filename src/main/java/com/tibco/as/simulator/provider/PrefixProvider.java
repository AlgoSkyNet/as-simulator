package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.Prefix;

public class PrefixProvider implements IValueProvider {

	private DataFactory df;
	private Prefix field;

	public PrefixProvider(DataFactory df, Prefix field) {
		this.df = df;
		this.field = field;
	}

	@Override
	public String getValue() {
		return df.getPrefix(field.getChance());
	}

}
