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

	private final static String DEFAULT_FILEPATH = "simulation.xml";

	@Parameter(names = { "-config" }, description = "XML configuration file")
	private String configFile;
	@Parameter(names = { "-save" }, description = "Save configuration as XML")
	private boolean save;
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
		SimulatorConfig simulatorConfig = (SimulatorConfig) config;
		return new SimulatorChannel(simulatorConfig);
	}

	@Override
	protected SimulatorConfig getChannelConfig() throws Exception {
		SimulatorConfig config = new SimulatorConfig();
		File file = new File(getConfigFilepath());
		if (file.exists()) {
			Simulation simulation = JAXB.unmarshal(file, Simulation.class);
			config.setDataValues(simulation.getDataValues());
			for (Space space : simulation.getSpace()) {
				SpaceConfig spaceConfig = new SpaceConfig();
				spaceConfig.setDirection(Direction.IMPORT);
				spaceConfig.setSpace(space.getName());
				spaceConfig.setLimit(space.getSize());
				spaceConfig.setSleep(space.getSleep());
				for (Field field : space.getFields()) {
					SimulatorField fieldConfig = (SimulatorField) spaceConfig
							.addField();
					fieldConfig.setField(field);
					if (field.isKey()) {
						spaceConfig.getKeys().add(fieldConfig.getFieldName());
					}
				}
				config.getDestinations().add(spaceConfig);
			}
		}
		return config;
	}

	private String getConfigFilepath() {
		if (configFile == null) {
			return DEFAULT_FILEPATH;
		}
		return configFile;
	}

	@Override
	protected void execute(IChannel channel) {
		super.execute(channel);
		if (save) {
			SimulatorChannel simulatorChannel = (SimulatorChannel) channel;
			SimulatorConfig config = simulatorChannel.getConfig();
			Simulation simulation = new Simulation();
			simulation.setDataValues(config.getDataValues());
			for (DestinationConfig destination : config.getDestinations()) {
				SpaceConfig spaceConfig = (SpaceConfig) destination;
				Space space = new Space();
				space.setName(spaceConfig.getSpace());
				space.setSize(spaceConfig.getLimit());
				space.setSleep(spaceConfig.getSleep());
				simulation.getSpace().add(space);
				for (com.tibco.as.convert.Field fieldConfig : destination
						.getFields()) {
					SimulatorField simulatorField = (SimulatorField) fieldConfig;
					Field field = simulatorField.getField();
					field.setFieldName(simulatorField.getFieldName());
					if (spaceConfig.getKeys().contains(
							simulatorField.getFieldName())) {
						field.setKey(true);
					}
					space.getFields().add(field);
				}
			}
			JAXB.marshal(simulation, new File(getConfigFilepath()));
		}
	}

}
