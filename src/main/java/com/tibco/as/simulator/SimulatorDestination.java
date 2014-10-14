package com.tibco.as.simulator;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.AbstractDestination;
import com.tibco.as.io.IInputStream;
import com.tibco.as.io.IOutputStream;

public class SimulatorDestination extends AbstractDestination {

	private SpaceConfig config;
	private DataFactory dataFactory;

	protected SimulatorDestination(SimulatorChannel channel,
			SpaceConfig config, DataFactory dataFactory) {
		super(channel, config);
		this.config = config;
		this.dataFactory = dataFactory;
	}

	@Override
	protected IInputStream createInputStream() throws Exception {
		return new SimulatorInputStream(config, dataFactory);
	}

	@Override
	protected IOutputStream createOutputStream() throws Exception {
		return null;
	}

}
