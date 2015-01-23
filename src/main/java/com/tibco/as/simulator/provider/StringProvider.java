package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.RandomString;

public class StringProvider implements IValueProvider {

	private DataFactory df;
	private RandomString field;

	public StringProvider(DataFactory df, RandomString field) {
		this.df = df;
		this.field = field;
	}

	@Override
	public String getValue() {
		return toCase(getChars());
	}

	private String getChars() {
		if (field.getLength() == null) {
			return df
					.getRandomChars(field.getMinLength(), field.getMaxLength());
		}
		return df.getRandomChars(field.getLength());
	}

	private String toCase(String string) {
		switch (field.getCase()) {
		case LOWER:
			return string.toLowerCase();
		case UPPER:
			return string.toUpperCase();
		default:
			return string;
		}
	}

}
