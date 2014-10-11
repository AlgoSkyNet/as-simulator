package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class FloatProvider implements IValueProvider {

	private Random random = new Random();

	@Override
	public Float getValue() {
		return random.nextFloat();
	}

}
