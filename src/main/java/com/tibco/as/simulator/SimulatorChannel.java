package com.tibco.as.simulator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXB;

import com.tibco.as.io.Channel;
import com.tibco.as.simulator.xml.DataValues;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.simulator.xml.Space;

public class SimulatorChannel extends Channel {

	private final static File DEFAULT_CONFIG_FILE = new File("simulation.xml");

	private DataValues dataValues;
	private String configPath;
	private Boolean saveConfig;

	@Override
	public void open() throws Exception {
		File file = getConfigFile();
		if (file.exists()) {
			Simulation simulation = JAXB.unmarshal(file, Simulation.class);
			setDataValues(simulation.getDataValues());
			for (Space space : simulation.getSpace()) {
				SimulatorDestination destination = addDestination();
				destination.setSpace(space.getName());
				destination.setImportLimit(space.getSize());
				destination.setSleep(space.getSleep());
				Collection<String> keys = new ArrayList<String>();
				for (Field field : space.getFields()) {
					SimulatorField fieldConfig = (SimulatorField) destination
							.addField();
					fieldConfig.setField(field);
					if (field.isKey()) {
						keys.add(fieldConfig.getFieldName());
					}
				}
				destination.setKeys(keys);
			}
		}
		super.open();
	}

	public DataValues getDataValues() {
		return dataValues;
	}

	public void setDataValues(DataValues dataValues) {
		this.dataValues = dataValues;
	}

	@Override
	public SimulatorDestination addDestination() {
		return (SimulatorDestination) super.addDestination();
	}

	@Override
	protected SimulatorDestination newDestination() {
		return new SimulatorDestination(this);
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public void setSaveConfig(Boolean saveConfig) {
		this.saveConfig = saveConfig;
	}

	public Boolean getSaveConfig() {
		return saveConfig;
	}

	public File getConfigFile() {
		if (configPath == null) {
			return DEFAULT_CONFIG_FILE;
		}
		return new File(configPath);
	}

	@Override
	public SimulatorChannelImport getImport() {
		return new SimulatorChannelImport(this);
	}
}
