package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.RandomWord;

public class RandomWordProvider implements IValueProvider {

	private DataFactory df;
	private RandomWord field;

	public RandomWordProvider(DataFactory df, RandomWord field) {
		this.df = df;
		this.field = field;
	}

	@Override
	public String getValue() {
		if (field.getMinLength() == null) {
			if (field.isExactLength() == null) {
				if (field.getLength() == null) {
					return df.getRandomWord();
				} else {
					return df.getRandomWord(field.getLength());
				}
			} else {
				return df.getRandomWord(field.getLength(),
						field.isExactLength());
			}
		} else {
			return df.getRandomWord(field.getMinLength(), field.getMaxLength());
		}
	}

}
