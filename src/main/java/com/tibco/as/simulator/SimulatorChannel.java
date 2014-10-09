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
	protected IDestination createDestination(DestinationConfig config) {
		return new SimulatorDestination(this, (SpaceConfig) config);
	}

	public DataFactory getDataFactory() {
		DataFactory dataFactory = new DataFactory();
		if (config.getDataValues() != null) {
			DataValues dataValues = config.getDataValues();
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
		return dataFactory;
	}

	// private SimField createField(FieldType fieldType) {
	// switch (fieldType) {
	// case BLOB:
	// return new RandomBlob();
	// case BOOLEAN:
	// return new RandomBoolean();
	// case CHAR:
	// return new RandomChar();
	// case DATETIME:
	// return new RandomDateTime();
	// case DOUBLE:
	// return new RandomDouble();
	// case FLOAT:
	// return new RandomFloat();
	// case INTEGER:
	// return new RandomInteger();
	// case LONG:
	// return new RandomLong();
	// case SHORT:
	// return new RandomShort();
	// default:
	// return new RandomChars();
	// }
	// }

}
