package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class FloatProvider implements IValueProvider {

	private Random random;

	public FloatProvider(Random random) {
		this.random = random;
	}

	@Override
	public Float getValue() {
		return random.nextFloat();
	}

}
