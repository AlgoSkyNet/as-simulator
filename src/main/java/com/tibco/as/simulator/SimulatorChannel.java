package com.tibco.as.simulator;

import com.tibco.as.io.AbstractChannel;
import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.IDestination;

public class SimulatorChannel extends AbstractChannel {

	private SimulatorConfig config;

	public SimulatorChannel(SimulatorConfig config) {
		super(config);
		this.config = config;
	}

	public SimulatorConfig getConfig() {
		return config;
	}

	@Override
	protected IDestination createDestination(DestinationConfig destination) {
		SpaceConfig spaceConfig = (SpaceConfig) destination;
		return new SimulatorDestination(this, spaceConfig);
	}

}
