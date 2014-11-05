package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.tibco.as.io.Channel;
import com.tibco.as.io.ChannelImport;
import com.tibco.as.io.cli.ImportCommand;

public class SimulatorCommand extends ImportCommand {

	@Parameter(description = "The list of spaces to simulate")
	private List<String> spaceNames = new ArrayList<String>();

	@Override
	public ChannelImport getTransfer(Channel channel) throws Exception {
		SimulatorChannel simChannel = (SimulatorChannel) channel;
		for (String spaceName : spaceNames) {
			SimulatorDestination destination = simChannel.addDestination();
			destination.setSpace(spaceName);
		}
		return super.getTransfer(channel);
	}

}
