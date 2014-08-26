package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.RandomShort;

public class ShortProvider implements IValueProvider {

	private Random random;
	private RandomShort field;

	public ShortProvider(Random random, RandomShort field) {
		this.random = random;
		this.field = field;
	}

	@Override
	public Short getValue() {
		if (field.getMin() == null)
			if (field.getMax() == null)
				return (short) random.nextInt();
			else
				return (short) random.nextInt(field.getMax());
		else
			return (short) random.nextInt(field.getMax() - field.getMin());
	}

}
