package com.tibco.as.simulator;

import com.tibco.as.io.ChannelConfig;
import com.tibco.as.simulator.xml.DataValues;

public class SimulatorConfig extends ChannelConfig {

	private DataValues dataValues;

	public DataValues getDataValues() {
		return dataValues;
	}

	public void setDataValues(DataValues dataValues) {
		this.dataValues = dataValues;
	}
}
