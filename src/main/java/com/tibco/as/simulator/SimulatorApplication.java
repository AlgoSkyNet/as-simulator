package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.Collection;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.tibco.as.io.cli.AbstractApplication;
import com.tibco.as.io.cli.ICommand;

public class SimulatorApplication extends AbstractApplication {

	@Parameter(names = { "-config" }, description = "XML configuration file")
	private String configPath;
	@Parameter(names = { "-save_config" }, description = "Save configuration as XML")
	private Boolean saveConfig;
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
	protected Collection<ICommand> getCommands() {
		return new ArrayList<ICommand>();
	}

	@Override
	protected SimulatorCommand getDefaultCommand() {
		return command;
	}

	@Override
	protected SimulatorChannel getChannel() {
		SimulatorChannel channel = new SimulatorChannel();
		channel.setSaveConfig(saveConfig);
		channel.setConfigPath(configPath);
		return channel;
	}

}
