package com.tibco.as.simulator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.beust.jcommander.Parameter;
import com.tibco.as.io.IMetaspaceTransfer;
import com.tibco.as.io.cli.AbstractCommandImport;
import com.tibco.as.simulator.ObjectFactory;
import com.tibco.as.simulator.Simulation;
import com.tibco.as.space.Metaspace;

public class CommandSimulate extends AbstractCommandImport {

	@Parameter(description = "The list of simulation files to execute")
	private List<String> files = new ArrayList<String>();

	private Collection<Simulation> simulations = new ArrayList<Simulation>();

	@Override
	protected Collection<IMetaspaceTransfer> getMetaspaceTransfers(
			Metaspace metaspace) {
		Collection<IMetaspaceTransfer> transfers = new ArrayList<IMetaspaceTransfer>();
		for (Simulation simulation : simulations) {
			SimulationImporter importer = new SimulationImporter(metaspace,
					simulation);
			SimulationImport transfer = new SimulationImport();
			configure(transfer);
			importer.setDefaultTransfer(transfer);
			transfers.add(importer);
		}
		return transfers;
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
		for (String file : files) {
			// Prepare JAXB objects
			JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream in = new FileInputStream(file);
			@SuppressWarnings("rawtypes")
			JAXBElement element = (JAXBElement) unmarshaller.unmarshal(in);
			Simulation simulation = (Simulation) element.getValue();
			simulations.add(simulation);
		}
		if (simulations.isEmpty()) {
			throw new Exception("No simulation file specified");
		}
	}

}
