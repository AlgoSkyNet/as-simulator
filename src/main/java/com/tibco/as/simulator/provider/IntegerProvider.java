package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.RandomInteger;

public class IntegerProvider implements IValueProvider {

	private RandomInteger field;
	private Random random = new Random();

	public IntegerProvider(RandomInteger field) {
		this.field = field;
	}

	@Override
	public Integer getValue() {
		if (field.getMin() == null) {
			if (field.getMax() == null) {
				return random.nextInt();
			}
			return random.nextInt(field.getMax());

		}
		return field.getMin() + random.nextInt(field.getMax() - field.getMin());
	}

}
