package com.tibco.as.simulator.provider;

import java.util.Random;

import com.tibco.as.simulator.IValueProvider;

public class BooleanProvider implements IValueProvider {

	private Random random = new Random();

	@Override
	public Boolean getValue() {
		return random.nextBoolean();
	}
}
