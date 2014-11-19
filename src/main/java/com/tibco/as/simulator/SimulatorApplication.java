package com.tibco.as.simulator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.tibco.as.io.cli.Application;

public class SimulatorApplication extends Application {

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
	protected SimulatorCommand getDefaultCommand() {
		return command;
	}

	@Override
	protected SimulatorChannel getChannel(String metaspaceName) {
		SimulatorChannel channel = new SimulatorChannel(metaspaceName);
		channel.setSaveConfig(saveConfig);
		channel.setConfigPath(configPath);
		return channel;
	}

}
