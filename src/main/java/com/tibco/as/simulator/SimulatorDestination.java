package com.tibco.as.simulator;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.Destination;
import com.tibco.as.io.IOutputStream;
import com.tibco.as.simulator.xml.DataValues;
import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;

public class SimulatorDestination extends Destination {

	private static final long DEFAULT_LIMIT = 100;

	private Long sleep;
	private SimulatorChannel channel;

	public SimulatorDestination(SimulatorChannel channel) {
		super(channel);
		this.channel = channel;
	}

	public Long getSleep() {
		return sleep;
	}

	public void setSleep(Long sleep) {
		this.sleep = sleep;
	}

	@Override
	protected SimulatorField newField() {
		return new SimulatorField();
	}

	@Override
	public void copyTo(Destination target) {
		SimulatorDestination destination = (SimulatorDestination) target;
		if (destination.sleep == null) {
			destination.sleep = sleep;
		}
		super.copyTo(destination);
	}

	@Override
	public SimulatorInputStream getInputStream() throws ASException {
		if (getFields().isEmpty()) {
			Metaspace metaspace = getChannel().getMetaspace();
			setSpaceDef(metaspace.getSpaceDef(getSpace()));
		}
		DataFactory dataFactory = new DataFactory();
		DataValues dataValues = channel.getDataValues();
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
		return new SimulatorInputStream(this, dataFactory);
	}

	@Override
	public IOutputStream getOutputStream() {
		return null;
	}

	@Override
	public Long getImportLimit() {
		Long limit = super.getImportLimit();
		if (limit == null) {
			return DEFAULT_LIMIT;
		}
		if (limit.equals(-1L)) {
			return null;
		}
		return limit;
	}

}
