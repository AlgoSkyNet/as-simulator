package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class BooleanProvider implements IValueProvider {

	private Random random;

	public BooleanProvider(Random random) {
		this.random = random;
	}

	@Override
	public Boolean getValue() {
		return random.nextBoolean();
	}
}
