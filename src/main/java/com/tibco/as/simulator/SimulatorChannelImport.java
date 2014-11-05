package com.tibco.as.simulator;

import java.util.Collection;

import javax.xml.bind.JAXB;

import com.tibco.as.io.Channel;
import com.tibco.as.io.ChannelImport;
import com.tibco.as.io.Destination;
import com.tibco.as.io.IDestinationTransfer;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.simulator.xml.Space;

public class SimulatorChannelImport extends ChannelImport {

	private SimulatorChannel channel;

	public SimulatorChannelImport(SimulatorChannel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public void execute() throws Exception {
		super.execute();
		if (Boolean.TRUE.equals(channel.getSaveConfig())) {
			Simulation simulation = new Simulation();
			simulation.setDataValues(channel.getDataValues());
			for (IDestinationTransfer transfer : getTransfers()) {
				SimulatorDestination destination = (SimulatorDestination) transfer
						.getDestination();
				Space space = new Space();
				space.setName(destination.getSpace());
				space.setSize(destination.getImportLimit());
				space.setSleep(destination.getSleep());
				simulation.getSpace().add(space);
				for (com.tibco.as.io.Field fieldConfig : destination
						.getFields()) {
					SimulatorField simulatorField = (SimulatorField) fieldConfig;
					Field field = simulatorField.getField();
					field.setFieldName(simulatorField.getFieldName());
					if (destination.getKeys().contains(
							simulatorField.getFieldName())) {
						field.setKey(true);
					}
					space.getFields().add(field);
				}
			}
			JAXB.marshal(simulation, channel.getConfigFile());
		}
	}

	@Override
	protected Collection<Destination> getDestinations(Channel channel)
			throws Exception {
		Collection<Destination> destinations = super.getDestinations(channel);
		if (destinations.isEmpty()) {
			destinations.addAll(channel.getExportDestinations());
		}
		return destinations;
	}

}
