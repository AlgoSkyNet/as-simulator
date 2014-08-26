package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.NumberText;

public class NumberTextProvider implements IValueProvider {

	private DataFactory df;
	private NumberText field;

	public NumberTextProvider(DataFactory df, NumberText field) {
		this.df = df;
		this.field = field;
	}

	@Override
	public String getValue() {
		return df.getNumberText(field.getDigits());
	}

}
