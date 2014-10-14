package com.tibco.as.simulator;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXB;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.tibco.as.convert.Direction;
import com.tibco.as.io.ChannelConfig;
import com.tibco.as.io.IChannel;
import com.tibco.as.io.cli.AbstractApplication;
import com.tibco.as.io.cli.ICommand;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.simulator.xml.Space;

public class SimulatorApplication extends AbstractApplication {

	@Parameter(names = { "-config" }, description = "XML configuration file")
	private String config;
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
		if (config != null) {
			FileInputStream in = new FileInputStream(config);
			Simulation simulation = JAXB.unmarshal(in, Simulation.class);
			return getSimulatorConfig(simulation);
		}
		return new SimulatorConfig();
	}

	public static SimulatorConfig getSimulatorConfig(Simulation simulation) {
		SimulatorConfig simulatorConfig = new SimulatorConfig();
		simulatorConfig.setDataValues(simulation.getDataValues());
		for (Space space : simulation.getSpace()) {
			SpaceConfig spaceConfig = new SpaceConfig();
			spaceConfig.setDirection(Direction.IMPORT);
			spaceConfig.setSpace(space.getName());
			spaceConfig.setLimit(space.getSize());
			spaceConfig.setSleep(space.getSleep());
			Collection<String> keys = new ArrayList<String>();
			for (Field field : space.getBlobOrBooleanOrConstant()) {
				SimulatorFieldConfig fieldConfig = new SimulatorFieldConfig(
						spaceConfig);
				fieldConfig.setField(field);
				spaceConfig.getFields().add(fieldConfig);
				if (field.isKey()) {
					keys.add(fieldConfig.getFieldName());
				}
			}
			spaceConfig.setKeys(keys);
			simulatorConfig.getDestinations().add(spaceConfig);
		}
		return simulatorConfig;
	}

}
