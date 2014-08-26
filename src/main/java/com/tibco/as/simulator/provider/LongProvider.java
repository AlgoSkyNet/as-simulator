package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class LongProvider implements IValueProvider {

	private Random random;

	public LongProvider(Random random) {
		this.random = random;
	}

	@Override
	public Long getValue() {
		return random.nextLong();
	}

}
