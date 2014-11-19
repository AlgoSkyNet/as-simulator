package com.tibco.as.simulator;

import com.tibco.as.io.ImportConfig;
import com.tibco.as.simulator.xml.Space;

public class SimulatorImportConfig extends ImportConfig {

	private static final long DEFAULT_LIMIT = 100;
	private SimulatorDestination destination;

	public SimulatorImportConfig(SimulatorDestination destination) {
		this.destination = destination;
	}

	@Override
	public Long getLimit() {
		Space space = destination.getSpace();
		if (space.getSize() == null) {
			if (super.getLimit() == null) {
				return DEFAULT_LIMIT;
			}
			return super.getLimit();
		}
		if (space.getSize().equals(-1L)) {
			return null;
		}
		return space.getSize();
	}

}
