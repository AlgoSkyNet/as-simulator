package com.tibco.as.simulator.provider;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.Constant;

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
