package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class LongProvider implements IValueProvider {

	private Random random = new Random();

	@Override
	public Long getValue() {
		return random.nextLong();
	}

}
