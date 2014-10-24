package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.Words;

public class WordsProvider implements IValueProvider {

	private DataFactory df;
	private Words field;
	private WordProvider randomWordAccessor;

	public WordsProvider(DataFactory df, Words field) {
		this.df = df;
		this.field = field;
		this.randomWordAccessor = new WordProvider(df, field);
	}

	@Override
	public String getValue() {
		int count = df.getNumberBetween(field.getMinCount(),
				field.getMaxCount());
		String result = "";
		for (int i = 0; i < count; i++) {
			result += randomWordAccessor.getValue();
			if (i < count - 1) {
				result += " ";
			}
		}
		return result;
	}

}
