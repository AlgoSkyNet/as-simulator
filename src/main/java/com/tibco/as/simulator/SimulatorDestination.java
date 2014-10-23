package com.tibco.as.simulator;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.AbstractDestination;
import com.tibco.as.io.IInputStream;
import com.tibco.as.io.IOutputStream;
import com.tibco.as.simulator.xml.DataValues;
import com.tibco.as.space.Metaspace;

public class SimulatorDestination extends AbstractDestination {

	private SimulatorChannel channel;
	private SpaceConfig config;

	protected SimulatorDestination(SimulatorChannel channel, SpaceConfig config) {
		super(channel, config);
		this.channel = channel;
		this.config = config;
	}

	@Override
	protected IInputStream getImportInputStream() throws Exception {
		if (config.getFields().isEmpty()) {
			Metaspace metaspace = channel.getMetaspace();
			config.setSpaceDef(metaspace.getSpaceDef(config.getSpace()));
		}
		DataFactory dataFactory = new DataFactory();
		DataValues dataValues = channel.getConfig().getDataValues();
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
		return new SimulatorInputStream(config, dataFactory);
	}

	@Override
	protected IOutputStream getExportOutputStream() throws Exception {
		return null;
	}

	@Override
	protected Class<?> getComponentType() {
		return Object.class;
	}

	public SpaceConfig getSpaceConfig() {
		return config;
	}

}
