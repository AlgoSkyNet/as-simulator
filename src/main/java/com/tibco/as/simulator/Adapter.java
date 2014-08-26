package com.tibco.as.simulator;

import com.tibco.as.io.Operation;
import com.tibco.as.space.Member.DistributionRole;

public class Adapter {

	public static <T extends Enum<?>> String print(T value) {
		return value == null ? null : value.name().toLowerCase();
	}

	public static String parse(String name) {
		return name.toUpperCase();
	}

	public static String printDistributionRole(DistributionRole type) {
		return print(type);
	}

	public static DistributionRole parseDistributionRole(String name) {
		return DistributionRole.valueOf(parse(name));
	}

	public static String printOperation(Operation operation) {
		return print(operation);
	}

	public static Operation parseOperation(String name) {
		return Operation.valueOf(parse(name));
	}

}
