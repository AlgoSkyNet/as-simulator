package com.tibco.as.simulator.provider;

import java.math.BigDecimal;
import java.util.Random;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.RandomDouble;

public class DoubleProvider implements IValueProvider {

	private Random random = new Random();
	private RandomDouble field;

	public DoubleProvider(RandomDouble field) {
		this.field = field;
	}

	@Override
	public Double getValue() {
		double d = getDouble();
		if (field.getDecimals() == null) {
			return d;
		}
		return new BigDecimal(d).setScale(field.getDecimals(),
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private double getDouble() {
		if (field.getMin() == null) {
			if (field.getMax() == null) {
				return random.nextInt() + random.nextDouble();
			}
			return random.nextInt((int) (field.getMax() - 1))
					+ random.nextDouble();
		}
		int base = (int) (field.getMax() - field.getMin() - 1);
		return random.nextInt(base) + random.nextDouble();
	}
}
