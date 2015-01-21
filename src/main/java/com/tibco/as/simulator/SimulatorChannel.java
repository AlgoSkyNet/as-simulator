package com.tibco.as.simulator;

import java.io.File;

import javax.xml.bind.JAXB;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.AbstractChannel;
import com.tibco.as.io.IDestination;
import com.tibco.as.simulator.xml.DataValues;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.simulator.xml.Space;

public class SimulatorChannel extends AbstractChannel {

	private final static File DEFAULT_CONFIG_FILE = new File("simulation.xml");

	private DataFactory dataFactory = new DataFactory();
	private String configPath;
	private Boolean saveConfig;
	private Simulation simulation;

	@Override
	public void open() throws Exception {
		loadConfig();
		for (Space space : simulation.getSpace()) {
			SimulatorDestination destination = (SimulatorDestination) addDestination();
			destination.setSpace(space);
		}
		super.open();
	}

	private void loadConfig() {
		simulation = loadSimulation();
		DataValues dataValues = simulation.getDataValues();
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
	}

	@Override
	public void close() throws Exception {
		super.close();
		saveConfig();
	}

	private void saveConfig() {
		if (saveConfig == null) {
			return;
		}
		if (saveConfig) {
			Simulation simulation = new Simulation();
			simulation.setDataValues(this.simulation.getDataValues());
			for (IDestination destination : getDestinations()) {
				SimulatorDestination simulatorDestination = (SimulatorDestination) destination;
				simulation.getSpace().add(simulatorDestination.getSpace());
			}
			JAXB.marshal(simulation, getConfigFile());
		}
	}

	private Simulation loadSimulation() {
		if (configPath != null) {
			File configFile = getConfigFile();
			if (configFile.exists()) {
				return JAXB.unmarshal(configFile, Simulation.class);
			}
		}
		return new Simulation();
	}

	public DataFactory getDataFactory() {
		return dataFactory;
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
	protected SimulatorDestination newDestination() {
		return new SimulatorDestination(this);
	}

}
