package com.tibco.as.simulator.provider;

import com.tibco.as.simulator.Constant;
import com.tibco.as.simulator.IValueProvider;

public class ConstantProvider implements IValueProvider {

	private Constant field;

	public ConstantProvider(Constant field) {
		this.field = field;
	}

	@Override
	public Object getValue() {
		return field.getValue();
	}

}
