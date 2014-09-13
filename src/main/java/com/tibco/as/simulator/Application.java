package com.tibco.as.simulator;

import com.beust.jcommander.ParametersDelegate;
import com.tibco.as.io.cli.AbstractApplication;
import com.tibco.as.io.cli.AbstractCommand;

public class Application extends AbstractApplication {

	@ParametersDelegate
	private SimulateCommand command = new SimulateCommand();

	public static void main(String[] args) throws Exception {
		new Application().execute(args);
	}

	@Override
	protected String getProgramName() {
		return "as-simulator";
	}

	@Override
	protected AbstractCommand getDefaultCommand() {
		return command;
	}

}
