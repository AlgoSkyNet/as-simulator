package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.Collection;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.Destination;
import com.tibco.as.io.IInputStream;
import com.tibco.as.io.ImportConfig;
import com.tibco.as.simulator.provider.AddressLine2Provider;
import com.tibco.as.simulator.provider.AddressProvider;
import com.tibco.as.simulator.provider.BirthDateProvider;
import com.tibco.as.simulator.provider.BlobProvider;
import com.tibco.as.simulator.provider.BooleanProvider;
import com.tibco.as.simulator.provider.BusinessNameProvider;
import com.tibco.as.simulator.provider.CharProvider;
import com.tibco.as.simulator.provider.CityProvider;
import com.tibco.as.simulator.provider.ConstantProvider;
import com.tibco.as.simulator.provider.DateTimeProvider;
import com.tibco.as.simulator.provider.DoubleProvider;
import com.tibco.as.simulator.provider.EmailAddressProvider;
import com.tibco.as.simulator.provider.FirstNameProvider;
import com.tibco.as.simulator.provider.FloatProvider;
import com.tibco.as.simulator.provider.IntegerProvider;
import com.tibco.as.simulator.provider.ItemProvider;
import com.tibco.as.simulator.provider.LastNameProvider;
import com.tibco.as.simulator.provider.LongProvider;
import com.tibco.as.simulator.provider.NameProvider;
import com.tibco.as.simulator.provider.NowProvider;
import com.tibco.as.simulator.provider.NumberTextProvider;
import com.tibco.as.simulator.provider.PrefixProvider;
import com.tibco.as.simulator.provider.RegexProvider;
import com.tibco.as.simulator.provider.SequenceProvider;
import com.tibco.as.simulator.provider.ShortProvider;
import com.tibco.as.simulator.provider.StreetNameProvider;
import com.tibco.as.simulator.provider.StreetSuffixProvider;
import com.tibco.as.simulator.provider.StringProvider;
import com.tibco.as.simulator.provider.SuffixProvider;
import com.tibco.as.simulator.provider.TextProvider;
import com.tibco.as.simulator.provider.WordProvider;
import com.tibco.as.simulator.provider.WordsProvider;
import com.tibco.as.simulator.xml.Address;
import com.tibco.as.simulator.xml.AddressLine2;
import com.tibco.as.simulator.xml.BirthDate;
import com.tibco.as.simulator.xml.BusinessName;
import com.tibco.as.simulator.xml.City;
import com.tibco.as.simulator.xml.Constant;
import com.tibco.as.simulator.xml.EmailAddress;
import com.tibco.as.simulator.xml.Field;
import com.tibco.as.simulator.xml.FirstName;
import com.tibco.as.simulator.xml.Item;
import com.tibco.as.simulator.xml.LastName;
import com.tibco.as.simulator.xml.Name;
import com.tibco.as.simulator.xml.Now;
import com.tibco.as.simulator.xml.NumberText;
import com.tibco.as.simulator.xml.Prefix;
import com.tibco.as.simulator.xml.RandomBlob;
import com.tibco.as.simulator.xml.RandomBoolean;
import com.tibco.as.simulator.xml.RandomChar;
import com.tibco.as.simulator.xml.RandomDateTime;
import com.tibco.as.simulator.xml.RandomDouble;
import com.tibco.as.simulator.xml.RandomFloat;
import com.tibco.as.simulator.xml.RandomInteger;
import com.tibco.as.simulator.xml.RandomLong;
import com.tibco.as.simulator.xml.RandomShort;
import com.tibco.as.simulator.xml.RandomString;
import com.tibco.as.simulator.xml.Regex;
import com.tibco.as.simulator.xml.Sequence;
import com.tibco.as.simulator.xml.Space;
import com.tibco.as.simulator.xml.StreetName;
import com.tibco.as.simulator.xml.StreetSuffix;
import com.tibco.as.simulator.xml.Suffix;
import com.tibco.as.simulator.xml.Text;
import com.tibco.as.simulator.xml.Word;
import com.tibco.as.simulator.xml.Words;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.util.convert.ConverterFactory;

public class SimulatorDestination extends Destination {

	private SimulatorChannel channel;
	private Space space;
	private SimulatorImportConfig importConfig = new SimulatorImportConfig(this);

	public SimulatorDestination(SimulatorChannel channel, Space space) {
		super(channel);
		this.channel = channel;
		this.space = space;
	}

	public SimulatorDestination(SimulatorChannel channel) {
		this(channel, new Space());
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	@Override
	public ImportConfig getImportConfig() {
		return importConfig;
	}

	public static void copy(Space source, Space target) {
		if (target.getName() == null) {
			target.setName(source.getName());
		}
		if (target.getSize() == null) {
			target.setSize(source.getSize());
		}
		if (target.getSleep() == null) {
			target.setSleep(source.getSleep());
		}
		if (target.getFields().isEmpty()) {
			target.getFields().addAll(source.getFields());
		}
	}

	@Override
	public SimulatorChannel getChannel() {
		return channel;
	}

	@Override
	public IInputStream getInputStream() {
		return new SimulatorInputStream(this);
	}

	private Field getField(FieldType fieldType) {
		switch (fieldType) {
		case BLOB:
			return new RandomBlob();
		case BOOLEAN:
			return new RandomBoolean();
		case CHAR:
			return new RandomChar();
		case DATETIME:
			return new RandomDateTime();
		case DOUBLE:
			return new RandomDouble();
		case FLOAT:
			return new RandomFloat();
		case INTEGER:
			return new RandomInteger();
		case LONG:
			return new RandomLong();
		case SHORT:
			return new RandomShort();
		default:
			return new RandomString();
		}
	}

	@Override
	public SpaceDef getSpaceDef() {
		SpaceDef spaceDef = super.getSpaceDef();
		if (spaceDef.getName() == null || spaceDef.getName().isEmpty()) {
			spaceDef.setName(space.getName());
		}
		Collection<FieldDef> fieldDefs = spaceDef.getFieldDefs();
		if (fieldDefs.isEmpty()) {
			for (Field field : space.getFields()) {
				String fieldName = getFieldName(field);
				FieldType fieldType = getFieldType(field);
				fieldDefs.add(FieldDef.create(fieldName, fieldType));
			}
		}
		Collection<String> keys = spaceDef.getKeyDef().getFieldNames();
		if (keys.isEmpty()) {
			for (Field field : space.getFields()) {
				if (Boolean.TRUE.equals(field.isKey())) {
					keys.add(getFieldName(field));
				}
			}
		}
		return spaceDef;
	}

	private FieldType getFieldType(Field field) {
		return ConverterFactory.getFieldType(getJavaType(field));
	}

	@Override
	public void setSpaceDef(SpaceDef spaceDef) {
		super.setSpaceDef(spaceDef);
		if (space.getName() == null) {
			space.setName(spaceDef.getName());
		}
		if (space.getFields().isEmpty()) {
			for (FieldDef fieldDef : spaceDef.getFieldDefs()) {
				Field field = getField(fieldDef.getType());
				field.setField(fieldDef.getName());
				space.getFields().add(field);
			}
		}
		for (String fieldName : spaceDef.getKeyDef().getFieldNames()) {
			Field field = getFieldByFieldName(fieldName);
			if (field == null) {
				continue;
			}
			field.setKey(true);
		}
	}

	private Field getFieldByFieldName(String name) {
		for (Field field : space.getFields()) {
			if (name.equals(field.getField())) {
				return field;
			}
		}
		return null;
	}

	private String getFieldName(Field field) {
		String fieldName = field.getField();
		if (fieldName == null) {
			return field.getClass().getSimpleName();
		}
		return fieldName;
	}

	@Override
	protected Class<?> getJavaType(FieldDef fieldDef) {
		Field field = getFieldByFieldName(fieldDef.getName());
		if (field == null) {
			return getJavaType(getField(fieldDef.getType()));
		}
		return getJavaType(field);
	}

	private Class<?> getJavaType(Field field) {
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
		else if (field instanceof RandomString)
			return java.lang.String.class;
		else if (field instanceof Text)
			return java.lang.String.class;
		else if (field instanceof Word)
			return java.lang.String.class;
		else if (field instanceof Words)
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
		return String.class;
	}

	public IValueProvider[] getValueProviders() {
		Collection<IValueProvider> providers = new ArrayList<IValueProvider>();
		for (Field field : space.getFields()) {
			providers.add(getProvider(field));
		}
		return providers.toArray(new IValueProvider[providers.size()]);
	}

	private IValueProvider getProvider(Field field) {
		if (field instanceof RandomBlob)
			return new BlobProvider((RandomBlob) field);
		if (field instanceof RandomBoolean)
			return new BooleanProvider();
		if (field instanceof RandomChar)
			return new CharProvider(getDataFactory());
		if (field instanceof Constant)
			return new ConstantProvider((Constant) field);
		if (field instanceof RandomDateTime)
			return new DateTimeProvider((RandomDateTime) field);
		if (field instanceof RandomDouble)
			return new DoubleProvider((RandomDouble) field);
		if (field instanceof RandomFloat)
			return new FloatProvider();
		if (field instanceof RandomInteger)
			return new IntegerProvider((RandomInteger) field);
		if (field instanceof RandomLong)
			return new LongProvider();
		if (field instanceof RandomShort)
			return new ShortProvider((RandomShort) field);
		if (field instanceof Address)
			return new AddressProvider(getDataFactory());
		if (field instanceof AddressLine2)
			return new AddressLine2Provider(getDataFactory(),
					(AddressLine2) field);
		if (field instanceof BirthDate)
			return new BirthDateProvider(getDataFactory());
		if (field instanceof BusinessName)
			return new BusinessNameProvider(getDataFactory());
		if (field instanceof City)
			return new CityProvider(getDataFactory());
		if (field instanceof EmailAddress)
			return new EmailAddressProvider(getDataFactory());
		if (field instanceof FirstName)
			return new FirstNameProvider(getDataFactory());
		if (field instanceof Item)
			return new ItemProvider(getDataFactory(), (Item) field);
		if (field instanceof LastName)
			return new LastNameProvider(getDataFactory());
		if (field instanceof Name)
			return new NameProvider(getDataFactory());
		if (field instanceof NumberText)
			return new NumberTextProvider(getDataFactory(), (NumberText) field);
		if (field instanceof Prefix)
			return new PrefixProvider(getDataFactory(), (Prefix) field);
		if (field instanceof RandomString)
			return new StringProvider(getDataFactory(), (RandomString) field);
		if (field instanceof Text)
			return new TextProvider(getDataFactory(), (Text) field);
		if (field instanceof Words)
			return new WordsProvider(getDataFactory(), (Words) field);
		if (field instanceof Word)
			return new WordProvider(getDataFactory(), (Word) field);
		if (field instanceof StreetName)
			return new StreetNameProvider(getDataFactory());
		if (field instanceof Sequence)
			return new SequenceProvider((Sequence) field);
		if (field instanceof StreetSuffix)
			return new StreetSuffixProvider(getDataFactory());
		if (field instanceof Suffix)
			return new SuffixProvider(getDataFactory(), (Suffix) field);
		if (field instanceof Now)
			return new NowProvider();
		if (field instanceof Regex)
			return new RegexProvider((Regex) field);
		return null;
	}

	private DataFactory getDataFactory() {
		return channel.getDataFactory();
	}

}
