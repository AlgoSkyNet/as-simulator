package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.cli.AbstractImportCommand;

public class SimulatorCommand extends AbstractImportCommand {

	@Parameter(description = "The list of spaces to simulate")
	private List<String> spaceNames = new ArrayList<String>();

	@Override
	protected void populate(Collection<DestinationConfig> destinations) {
		for (String spaceName : spaceNames) {
			SpaceConfig spaceConfig = newDestination();
			spaceConfig.setSpace(spaceName);
			destinations.add(spaceConfig);
		}
	}

	@Override
	protected SpaceConfig newDestination() {
		return new SpaceConfig();
	}

}
