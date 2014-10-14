package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.tibco.as.io.ChannelConfig;
import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.cli.AbstractImportCommand;

public class SimulatorCommand extends AbstractImportCommand {

	@Parameter(description = "The list of spaces to simulate")
	private List<String> spaceNames = new ArrayList<String>();

	@Override
	public void configure(ChannelConfig config) throws Exception {
		List<DestinationConfig> destinations = config.getDestinations();
		for (String spaceName : spaceNames) {
			SpaceConfig destination = new SpaceConfig();
			destination.setSpace(spaceName);
			destinations.add(destination);
		}
		if (destinations.isEmpty()) {
			destinations.add(new SpaceConfig());
		}
		super.configure(config);
	}
}
