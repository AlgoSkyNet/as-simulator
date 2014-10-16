package com.tibco.as.simulator;

import java.io.File;

import javax.xml.bind.JAXB;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.tibco.as.io.ChannelConfig;
import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.Direction;
import com.tibco.as.io.IChannel;
import com.tibco.as.io.cli.AbstractApplication;
import com.tibco.as.io.cli.ICommand;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.simulator.xml.Space;

public class SimulatorApplication extends AbstractApplication {

	@Parameter(names = { "-config" }, description = "XML configuration file")
	private String config;
	@Parameter(names = { "-save_config" }, description = "Save XML configuration to file")
	private boolean saveConfig;
	@ParametersDelegate
	private SimulatorCommand command = new SimulatorCommand();

	public static void main(String[] args) throws Exception {
		new SimulatorApplication().execute(args);
	}

	@Override
	protected String getProgramName() {
		return "as-simulator";
	}

	@Override
	protected ICommand getDefaultCommand() {
		return command;
	}

	@Override
	protected IChannel getChannel(ChannelConfig config) {
		return new SimulatorChannel((SimulatorConfig) config);
	}

	@Override
	protected SimulatorConfig getChannelConfig() throws Exception {
		SimulatorConfig simulatorConfig = new SimulatorConfig();
		if (config != null) {
			File file = new File(config);
			if (file.exists()) {
				Simulation simulation = JAXB.unmarshal(file, Simulation.class);
				simulatorConfig.setDataValues(simulation.getDataValues());
				for (Space space : simulation.getSpace()) {
					SpaceConfig spaceConfig = (SpaceConfig) simulatorConfig
							.addDestinationConfig();
					spaceConfig.setDirection(Direction.IMPORT);
					spaceConfig.setSpace(space.getName());
					spaceConfig.setLimit(space.getSize());
					spaceConfig.setSleep(space.getSleep());
					for (Field field : space.getFields()) {
						SimulatorFieldConfig fieldConfig = (SimulatorFieldConfig) spaceConfig
								.addField();
						fieldConfig.setField(field);
						if (field.isKey()) {
							spaceConfig.getKeys().add(
									fieldConfig.getFieldName());
						}
					}
				}
			}
		}
		return simulatorConfig;
	}

	@Override
	protected void execute(IChannel channel) {
		super.execute(channel);
		if (saveConfig) {
			SimulatorChannel simulatorChannel = (SimulatorChannel) channel;
			SimulatorConfig simulatorConfig = simulatorChannel.getConfig();
			Simulation simulation = new Simulation();
			simulation.setDataValues(simulatorConfig.getDataValues());
			for (DestinationConfig destination : simulatorConfig
					.getDestinations()) {
				SpaceConfig spaceConfig = (SpaceConfig) destination;
				Space space = new Space();
				space.setName(spaceConfig.getSpace());
				space.setSize(spaceConfig.getLimit());
				space.setSleep(spaceConfig.getSleep());
				simulation.getSpace().add(space);
				for (com.tibco.as.convert.Field fieldConfig : destination
						.getFields()) {
					SimulatorFieldConfig simulatorField = (SimulatorFieldConfig) fieldConfig;
					Field field = simulatorField.getField();
					field.setFieldName(simulatorField.getFieldName());
					if (spaceConfig.getKeys().contains(
							simulatorField.getFieldName())) {
						field.setKey(true);
					}
					space.getFields().add(field);
				}
			}
			JAXB.marshal(simulation, new File(config));
		}
	}

}
