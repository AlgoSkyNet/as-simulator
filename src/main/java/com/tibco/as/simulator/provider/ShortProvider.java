package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.RandomShort;

public class ShortProvider implements IValueProvider {

	private Random random = new Random();
	private RandomShort field;

	public ShortProvider(RandomShort field) {
		this.field = field;
	}

	@Override
	public Short getValue() {
		if (field.getMin() == null) {
			if (field.getMax() == null) {
				return (short) random.nextInt();
			}
			return (short) random.nextInt(field.getMax());
		}
		return (short) random.nextInt(field.getMax() - field.getMin());
	}

}
