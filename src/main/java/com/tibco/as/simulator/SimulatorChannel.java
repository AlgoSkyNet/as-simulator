package com.tibco.as.simulator;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.AbstractChannel;
import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.IDestination;
import com.tibco.as.simulator.xml.DataValues;

public class SimulatorChannel extends AbstractChannel {

	private SimulatorConfig config;

	public SimulatorChannel(SimulatorConfig config) {
		super(config);
		this.config = config;
	}

	@Override
	protected IDestination createDestination(DestinationConfig destination) {
		DataFactory dataFactory = new DataFactory();
		DataValues dataValues = config.getDataValues();
		if (dataValues != null) {
			if (dataValues.getAddresses() != null) {
				dataFactory.setAddressDataValues(new CustomAddressDataValues(
						dataValues.getAddresses()));
			}

			if (dataValues.getContents() != null) {
				dataFactory.setContentDataValues(new CustomContentDataValues(
						dataValues.getContents()));
			}
			if (dataValues.getNames() != null) {
				dataFactory.setNameDataValues(new CustomNameDataValues(
						dataValues.getNames()));
			}
		}
		return new SimulatorDestination(this, (SpaceConfig) destination, dataFactory);
	}

}
