package com.tibco.as.io.simulation;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.Utils;
import com.tibco.as.simulator.SimulatorApplication;
import com.tibco.as.simulator.SimulatorChannel;
import com.tibco.as.simulator.SimulatorConfig;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.space.ASException;
import com.tibco.as.space.DateTime;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;

public class TestSimulation {

	private Metaspace metaspace;

	private void execute(String command) throws Exception {
		SimulatorApplication.main(command.split(" "));
	}

	@Before
	public void setup() throws ASException {
		MemberDef memberDef = MemberDef.create(null, "tcp", null);
		memberDef.setConnectTimeout(10000);
		metaspace = Metaspace.connect(null, memberDef);
	}

	@After
	public void tearDown() throws ASException {
		if (metaspace == null) {
			return;
		}
		metaspace.closeAll();
	}

	@Test
	public void testCustomers() throws Exception {
		executeChannel("customers.xml");
		assertCustomerSpace();
	}

	private void executeChannel(String file) throws Exception {
		Simulation simulation = getSimulation(file);
		SimulatorConfig config = SimulatorApplication
				.getSimulatorConfig(simulation);
		for (DestinationConfig destination : config.getDestinations()) {
			destination.setDistributionRole(DistributionRole.SEEDER);
		}
		SimulatorChannel channel = new SimulatorChannel(config);
		channel.open();
		channel.close();
	}

	@Test
	public void testCustomersConfigFile() throws Exception {
		File file = Utils.copy("customers.xml", Utils.createTempDirectory());
		execute("-config " + file.getAbsolutePath()
				+ " -discovery tcp -distribution_role seeder");
		assertCustomerSpace();
	}

	private void assertCustomerSpace() throws ASException {
		SpaceDef spaceDef = metaspace.getSpaceDef("customer");
		Collection<FieldDef> fieldDefs = spaceDef.getFieldDefs();
		Assert.assertEquals(10, fieldDefs.size());
		Assert.assertEquals("id", fieldDefs.iterator().next().getName());
		Browser browser = metaspace.browse("customer",
				com.tibco.as.space.browser.BrowserDef.BrowserType.GET);
		Pattern pattern = Pattern
				.compile("([2-9][0-9]{2})-([0-9]{3})-([0-9]{4})");
		Tuple tuple;
		while ((tuple = browser.next()) != null) {
			String phone = tuple.getString("phone");
			Matcher matcher = pattern.matcher(phone);
			Assert.assertTrue(matcher.find());
			Integer.parseInt(matcher.group(1));
			Integer.parseInt(matcher.group(2));
			Integer.parseInt(matcher.group(3));
		}
		com.tibco.as.space.Space customerSpace = metaspace.getSpace("customer");
		Assert.assertEquals(100, customerSpace.size());
	}

	@Test
	public void testSimulation() throws Exception {
		executeChannel("simulation.xml");
		assertPositionSpace();
	}

	private void assertPositionSpace() throws ASException {
		SpaceDef spaceDef = metaspace.getSpaceDef("position");
		FieldDef[] fieldDefs = spaceDef.getFieldDefs().toArray(new FieldDef[0]);
		Assert.assertEquals(7, fieldDefs.length);
		Assert.assertEquals("portfolioId", fieldDefs[0].getName());
		Browser browser = metaspace.browse("quote",
				com.tibco.as.space.browser.BrowserDef.BrowserType.GET);
		Tuple tuple;
		while ((tuple = browser.next()) != null) {
			DateTime dateTime = tuple.getDateTime("tradeTime");
			Calendar calendar = dateTime.getTime();
			Assert.assertEquals(2013, calendar.get(Calendar.YEAR));
			Assert.assertEquals(9, calendar.get(Calendar.MONTH));
			Assert.assertEquals(15, calendar.get(Calendar.DATE));
		}
		com.tibco.as.space.Space positionSpace = metaspace.getSpace("position");
		Assert.assertEquals(100000, positionSpace.size());
	}

	@Test
	public void testSimulationConfigFile() throws Exception {
		File file = Utils.copy("simulation.xml", Utils.createTempDirectory());
		execute("-config " + file.getAbsolutePath()
				+ " -discovery tcp -distribution_role seeder");
		assertPositionSpace();
	}

	private Simulation getSimulation(String resourceName) throws JAXBException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		JAXBContext jc = JAXBContext.newInstance(Simulation.class.getPackage()
				.getName(), classLoader);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<Simulation> element = (JAXBElement<Simulation>) unmarshaller
				.unmarshal(classLoader.getResourceAsStream(resourceName));
		return element.getValue();
	}

}
