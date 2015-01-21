package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.tibco.as.io.IChannel;
import com.tibco.as.io.IExecutor;
import com.tibco.as.io.cli.ImportCommand;
import com.tibco.as.util.Utils;

public class SimulatorCommand extends ImportCommand {

	@Parameter(description = "The list of spaces to simulate")
	private List<String> spaceNames = new ArrayList<String>();

	@Override
	public IExecutor getExecutor(IChannel channel) throws Exception {
		if (spaceNames.isEmpty()) {
			spaceNames.add("*");
		}
		for (String pattern : spaceNames) {
			for (String spaceName : channel.getMetaspace().getUserSpaceNames()) {
				if (Utils.matches(spaceName, pattern, false)) {
					SimulatorDestination destination = new SimulatorDestination(
							(SimulatorChannel) channel);
					destination.getSpaceDef().setName(spaceName);
					channel.getDestinations().add(destination);
				}
			}
		}
		return super.getExecutor(channel);
	}
}
