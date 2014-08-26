package com.tibco.as.simulator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.accessors.AccessorFactory;
import com.tibco.as.accessors.ITupleAccessor;
import com.tibco.as.convert.Attributes;
import com.tibco.as.convert.ConverterFactory;
import com.tibco.as.convert.IConverter;
import com.tibco.as.convert.UnsupportedConversionException;
import com.tibco.as.convert.array.ArrayToTupleConverter;
import com.tibco.as.io.Field;
import com.tibco.as.io.FieldUtils;
import com.tibco.as.io.Import;
import com.tibco.as.io.Importer;
import com.tibco.as.io.Transfer;
import com.tibco.as.simulator.Address;
import com.tibco.as.simulator.AddressLine2;
import com.tibco.as.simulator.BirthDate;
import com.tibco.as.simulator.BusinessName;
import com.tibco.as.simulator.City;
import com.tibco.as.simulator.Constant;
import com.tibco.as.simulator.EmailAddress;
import com.tibco.as.simulator.FirstName;
import com.tibco.as.simulator.Item;
import com.tibco.as.simulator.LastName;
import com.tibco.as.simulator.Name;
import com.tibco.as.simulator.Now;
import com.tibco.as.simulator.NumberText;
import com.tibco.as.simulator.Prefix;
import com.tibco.as.simulator.RandomBlob;
import com.tibco.as.simulator.RandomBoolean;
import com.tibco.as.simulator.RandomChar;
import com.tibco.as.simulator.RandomChars;
import com.tibco.as.simulator.RandomDateTime;
import com.tibco.as.simulator.RandomDouble;
import com.tibco.as.simulator.RandomFloat;
import com.tibco.as.simulator.RandomInteger;
import com.tibco.as.simulator.RandomLong;
import com.tibco.as.simulator.RandomShort;
import com.tibco.as.simulator.RandomText;
import com.tibco.as.simulator.RandomWord;
import com.tibco.as.simulator.RandomWords;
import com.tibco.as.simulator.Regex;
import com.tibco.as.simulator.Sequence;
import com.tibco.as.simulator.SimField;
import com.tibco.as.simulator.Simulation;
import com.tibco.as.simulator.Space;
import com.tibco.as.simulator.StreetName;
import com.tibco.as.simulator.StreetSuffix;
import com.tibco.as.simulator.Suffix;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.util.Utils;

public class SimulationImporter extends Importer<Object[]> {

	private ConverterFactory converterFactory = new ConverterFactory();

	private Random random = new Random();

	private ValueProviderFactory valueProviderFactory;

	private Simulation simulation;

	public SimulationImporter(Metaspace metaspace, Simulation simulation) {
		super(metaspace);
		this.simulation = simulation;
		valueProviderFactory = new ValueProviderFactory(
				getDataFactory(simulation), random);
	}

	private DataFactory getDataFactory(Simulation simulation) {
		DataFactory dataFactory = new DataFactory();
		if (simulation.getDataValues() != null) {
			if (simulation.getDataValues().getAddresses() != null) {
				dataFactory.setAddressDataValues(new CustomAddressDataValues(
						simulation.getDataValues().getAddresses()));
			}

			if (simulation.getDataValues().getContents() != null) {
				dataFactory.setContentDataValues(new CustomContentDataValues(
						simulation.getDataValues().getContents()));
			}
			if (simulation.getDataValues().getNames() != null) {
				dataFactory.setNameDataValues(new CustomNameDataValues(
						simulation.getDataValues().getNames()));
			}
		}
		return dataFactory;
	}

	@Override
	protected boolean isParallelTransfers() {
		return true;
	}

	@Override
	protected SimulationImport createTransfer() {
		return new SimulationImport();
	}

	@Override
	protected Collection<Transfer> getTransfers(Metaspace metaspace) {
		Collection<Transfer> imports = new ArrayList<Transfer>();
		for (Space space : simulation.getSpace()) {
			SimulationImport simulationImport = ((SimulationImport) getDefaultTransfer())
					.clone();
			simulationImport.setSpace(space);
			if (simulationImport.getSpaceName() == null) {
				String spaceName = space.getName();
				if (spaceName == null) {
					spaceName = MessageFormat.format("space{0}", simulation
							.getSpace().indexOf(space) + 1);
				}
				simulationImport.setSpaceName(spaceName);
			}
			if (space.getBatchSize() != null) {
				simulationImport.setBatchSize(space.getBatchSize());
			}
			if (space.getDistributionRole() != null) {
				simulationImport.setDistributionRole(space
						.getDistributionRole());
			}
			if (space.getOperation() != null) {
				simulationImport.setOperation(space.getOperation());
			}
			imports.add(simulationImport);
		}
		return imports;
	}

	public SpaceDef getSpaceDef(String spaceName) throws Exception {
		Space space = getSpace(spaceName);
		SimulationImport config = createTransfer();
		config.setSpace(space);
		config.setSpaceName(spaceName);
		return getSpaceDef(config);
	}

	private Space getSpace(String name) {
		for (Space space : simulation.getSpace()) {
			if (space.getName().equals(name)) {
				return space;
			}
		}
		return null;
	}

	@Override
	protected SpaceDef createSpaceDef(String spaceName, Import config) {
		SimulationImport simulation = (SimulationImport) config;
		Collection<Field> fields = new ArrayList<Field>();
		if (simulation.getSpace() != null) {
			for (SimField simField : simulation.getSpace()
					.getBlobOrBooleanOrConstant()) {
				Field f = new Field();
				if (simField.getName() == null) {
					simField.setName(simField.getClass().getSimpleName());
				}
				f.setName(simField.getName());
				f.setType(converterFactory.getFieldType(getType(simField)));
				f.setKey(simField.isKey());
				f.setNullable(simField.isNullable());
				fields.add(f);
			}
		}
		Field[] fieldArray = fields.toArray(new Field[fields.size()]);
		return FieldUtils.createSpaceDef(spaceName, fieldArray);
	}

	@Override
	protected String getInputSpaceName(Import transfer) {
		return ((SimulationImport) transfer).getSpaceName();
	}

	private List<SimField> getFields(Space space) {
		return space.getBlobOrBooleanOrConstant();
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

	@SuppressWarnings("rawtypes")
	@Override
	protected IConverter<Object[], Tuple> getConverter(Transfer transfer,
			SpaceDef spaceDef) throws UnsupportedConversionException {
		SimulationImport simulation = (SimulationImport) transfer;
		Space space = simulation.getSpace();
		FieldDef[] fieldDefs = Utils.getFieldDefs(spaceDef);
		ITupleAccessor[] accessors = AccessorFactory.create(fieldDefs);
		Class[] types = getTypes(getFields(space));
		IConverter[] converters = converterFactory.getConverters(
				new Attributes(), types, fieldDefs);
		return new ArrayToTupleConverter<Object>(accessors, converters);
	}

	@SuppressWarnings("rawtypes")
	private Class[] getTypes(List<SimField> simFields) {
		Class[] types = new Class[simFields.size()];
		for (int index = 0; index < simFields.size(); index++) {
			types[index] = getType(simFields.get(index));
		}
		return types;
	}

	private Class<?> getType(SimField field) {
		if (field instanceof Address)
			return java.lang.String.class;
		else if (field instanceof AddressLine2)
			return java.lang.String.class;
		else if (field instanceof BirthDate)
			return java.util.Calendar.class;
		else if (field instanceof BusinessName)
			return java.lang.String.class;
		else if (field instanceof City)
			return java.lang.String.class;
		else if (field instanceof Constant)
			return ((Constant) field).getValue().getClass();
		else if (field instanceof EmailAddress)
			return java.lang.String.class;
		else if (field instanceof FirstName)
			return java.lang.String.class;
		else if (field instanceof LastName)
			return java.lang.String.class;
		else if (field instanceof Name)
			return java.lang.String.class;
		else if (field instanceof RandomBlob)
			return byte[].class;
		else if (field instanceof RandomBoolean)
			return java.lang.Boolean.class;
		else if (field instanceof RandomChar)
			return java.lang.Character.class;
		else if (field instanceof RandomDateTime)
			return java.util.Calendar.class;
		else if (field instanceof RandomDouble)
			return java.lang.Double.class;
		else if (field instanceof RandomFloat)
			return java.lang.Float.class;
		else if (field instanceof RandomInteger)
			return java.lang.Integer.class;
		else if (field instanceof Item)
			return ((Item) field).getValue().get(0).getClass();
		else if (field instanceof RandomLong)
			return java.lang.Long.class;
		else if (field instanceof RandomShort)
			return java.lang.Short.class;
		else if (field instanceof NumberText)
			return java.lang.String.class;
		else if (field instanceof Prefix)
			return java.lang.String.class;
		else if (field instanceof RandomChars)
			return java.lang.String.class;
		else if (field instanceof RandomText)
			return java.lang.String.class;
		else if (field instanceof RandomWord)
			return java.lang.String.class;
		else if (field instanceof RandomWords)
			return java.lang.String.class;
		else if (field instanceof Sequence)
			return java.lang.Long.class;
		else if (field instanceof StreetName)
			return java.lang.String.class;
		else if (field instanceof StreetSuffix)
			return java.lang.String.class;
		else if (field instanceof Suffix)
			return java.lang.String.class;
		else if (field instanceof Now)
			return java.util.Calendar.class;
		else if (field instanceof Regex)
			return String.class;
		return null;
	}

	@Override
	protected SimulationInputStream getInputStream(Metaspace metaspace,
			Transfer transfer, SpaceDef spaceDef) {
		SimulationImport config = (SimulationImport) transfer;
		Space space = config.getSpace();
		Collection<IValueProvider> providers = new ArrayList<IValueProvider>();
		for (SimField field : space.getBlobOrBooleanOrConstant()) {
			providers.add(valueProviderFactory.create(field));
		}
		return new SimulationInputStream(config,
				providers.toArray(new IValueProvider[providers.size()]));
	}
}
