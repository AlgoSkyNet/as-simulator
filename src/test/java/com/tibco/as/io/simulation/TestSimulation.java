package com.tibco.as.io.simulation;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tibco.as.io.Utils;
import com.tibco.as.simulator.SimulatorApplication;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.RandomBoolean;
import com.tibco.as.simulator.xml.RandomChar;
import com.tibco.as.simulator.xml.RandomDateTime;
import com.tibco.as.simulator.xml.RandomDouble;
import com.tibco.as.simulator.xml.RandomFloat;
import com.tibco.as.simulator.xml.RandomInteger;
import com.tibco.as.simulator.xml.RandomLong;
import com.tibco.as.simulator.xml.RandomShort;
import com.tibco.as.simulator.xml.RandomString;
import com.tibco.as.simulator.xml.Simulation;
import com.tibco.as.space.ASException;
import com.tibco.as.space.DateTime;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;

public class TestSimulation {

	public static final String SPACE_NAME = "MySpace";
	public static final String FIELD_NAME1 = "field1";
	public static final String FIELD_NAME2 = "field2";
	public static final String FIELD_NAME3 = "field3";
	public static final String FIELD_NAME4 = "field4";
	public static final String FIELD_NAME5 = "field5";
	public static final String FIELD_NAME6 = "field6";
	public static final String FIELD_NAME7 = "field7";
	public static final String FIELD_NAME8 = "field8";
	public static final String FIELD_NAME9 = "field9";
	public static final String FIELD_NAME10 = "field10";
	protected static final FieldDef FIELD1 = FieldDef.create(FIELD_NAME1,
			FieldType.LONG).setNullable(false);
	protected static final FieldDef FIELD2 = FieldDef.create(FIELD_NAME2,
			FieldType.STRING).setNullable(false);
	protected static final FieldDef FIELD3 = FieldDef.create(FIELD_NAME3,
			FieldType.DATETIME).setNullable(true);
	protected static final FieldDef FIELD4 = FieldDef.create(FIELD_NAME4,
			FieldType.BLOB).setNullable(true);
	protected static final FieldDef FIELD5 = FieldDef.create(FIELD_NAME5,
			FieldType.BOOLEAN).setNullable(true);
	protected static final FieldDef FIELD6 = FieldDef.create(FIELD_NAME6,
			FieldType.CHAR).setNullable(true);
	protected static final FieldDef FIELD7 = FieldDef.create(FIELD_NAME7,
			FieldType.DOUBLE).setNullable(true);
	protected static final FieldDef FIELD8 = FieldDef.create(FIELD_NAME8,
			FieldType.FLOAT).setNullable(true);
	protected static final FieldDef FIELD9 = FieldDef.create(FIELD_NAME9,
			FieldType.INTEGER).setNullable(true);
	protected static final FieldDef FIELD10 = FieldDef.create(FIELD_NAME10,
			FieldType.SHORT).setNullable(true);

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
		executeFile("customers.xml");
		assertCustomerSpace();
	}

	private void executeFile(String filename) throws Exception {
		File file = Utils.copy(filename, Utils.createTempDirectory());
		execute("-discovery tcp -distribution_role seeder -config "
				+ file.getAbsolutePath());
	}

	@Test
	public void testCustomersConfigFile() throws Exception {
		executeFile("customers.xml");
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
		executeFile("simulation.xml");
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
		executeFile("simulation.xml");
		assertPositionSpace();
	}

	@Test
	public void testNoConfig() throws Exception {
		File file = new File(Utils.createTempDirectory(), "saved-config.xml");
		metaspace.defineSpace(createSpaceDef());
		Space space = metaspace.getSpace(SPACE_NAME, DistributionRole.SEEDER);
		execute("-discovery tcp -config " + file.getAbsolutePath()
				+ " -save_config");
		Assert.assertTrue(space.size() > 0);
		Assert.assertTrue(file.exists());
		Simulation simulation = JAXB.unmarshal(file, Simulation.class);
		Assert.assertEquals(1, simulation.getSpace().size());
		com.tibco.as.simulator.xml.Space xmlSpace = simulation.getSpace()
				.get(0);
		List<Field> fields = xmlSpace.getFields();
		Assert.assertEquals(SPACE_NAME, xmlSpace.getName());
		Assert.assertEquals(10, fields.size());
		Field field1 = fields.get(0);
		Assert.assertEquals(FIELD_NAME1, field1.getFieldName());
		Assert.assertTrue(field1.isKey());
		Assert.assertEquals(RandomLong.class, field1.getClass());
		Field field2 = fields.get(1);
		Assert.assertEquals(FIELD_NAME2, field2.getFieldName());
		Assert.assertEquals(RandomString.class, field2.getClass());
		Assert.assertTrue(field2.isKey());
		Field field3 = fields.get(2);
		Assert.assertEquals(FIELD_NAME3, field3.getFieldName());
		Assert.assertEquals(RandomDateTime.class, field3.getClass());
		Assert.assertFalse(field3.isKey());
		Assert.assertEquals(RandomBoolean.class, fields.get(4).getClass());
		Assert.assertEquals(RandomChar.class, fields.get(5).getClass());
		Assert.assertEquals(RandomDouble.class, fields.get(6).getClass());
		Assert.assertEquals(RandomFloat.class, fields.get(7).getClass());
		Assert.assertEquals(RandomInteger.class, fields.get(8).getClass());
		Assert.assertEquals(RandomShort.class, fields.get(9).getClass());
	}

	protected SpaceDef createSpaceDef() {
		SpaceDef spaceDef = SpaceDef.create(SPACE_NAME, 0, Arrays.asList(
				FIELD1, FIELD2, FIELD3, FIELD4, FIELD5, FIELD6, FIELD7, FIELD8,
				FIELD9, FIELD10));
		spaceDef.setKey(FIELD1.getName(), FIELD2.getName());
		return spaceDef;
	}

}
