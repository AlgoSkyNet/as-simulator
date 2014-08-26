package com.tibco.as.simulator.provider;

import java.util.Calendar;

import com.tibco.as.simulator.IValueProvider;

public class NowProvider implements IValueProvider {

	@Override
	public Calendar getValue() {
		return Calendar.getInstance();
	}

}
