package com.tibco.as.simulator;

import java.util.ArrayList;
import java.util.Collection;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.io.IInputStream;
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
import com.tibco.as.simulator.xml.RandomText;
import com.tibco.as.simulator.xml.RandomWord;
import com.tibco.as.simulator.xml.RandomWords;
import com.tibco.as.simulator.xml.Regex;
import com.tibco.as.simulator.xml.Sequence;
import com.tibco.as.simulator.xml.StreetName;
import com.tibco.as.simulator.xml.StreetSuffix;
import com.tibco.as.simulator.xml.Suffix;

public class SimulatorInputStream implements IInputStream {

	private SpaceConfig config;
	private DataFactory dataFactory;
	private IValueProvider[] providers;
	private Long position;
	private boolean closed;

	public SimulatorInputStream(SpaceConfig config, DataFactory dataFactory) {
		this.config = config;
		this.dataFactory = dataFactory;
	}

	@Override
	public void open() throws Exception {
		Collection<IValueProvider> provs = new ArrayList<IValueProvider>();
		for (com.tibco.as.convert.Field fieldConfig : config.getFields()) {
			SimulatorFieldConfig field = (SimulatorFieldConfig) fieldConfig;
			provs.add(getProvider(field.getField()));
		}
		providers = provs.toArray(new IValueProvider[provs.size()]);
		position = 0L;
		closed = false;
	}

	@Override
	public void close() throws Exception {
		closed = true;
	}

	@Override
	public Object[] read() throws Exception {
		if (closed) {
			return null;
		}
		if (config.getSleep() != null) {
			Thread.sleep(config.getSleep());
		}
		Object[] data = new Object[providers.length];
		for (int i = 0; i < providers.length; i++) {
			data[i] = providers[i].getValue();
		}
		position++;
		return data;
	}

	@Override
	public Long size() {
		return null;
	}

	@Override
	public Long getPosition() {
		return position;
	}

	@Override
	public long getOpenTime() {
		return 0;
	}

	private IValueProvider getProvider(Field field) {
		if (field instanceof RandomBlob)
			return new BlobProvider((RandomBlob) field);
		if (field instanceof RandomBoolean)
			return new BooleanProvider();
		if (field instanceof RandomChar)
			return new CharProvider(dataFactory);
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
			return new AddressProvider(dataFactory);
		if (field instanceof AddressLine2)
			return new AddressLine2Provider(dataFactory, (AddressLine2) field);
		if (field instanceof BirthDate)
			return new BirthDateProvider(dataFactory);
		if (field instanceof BusinessName)
			return new BusinessNameProvider(dataFactory);
		if (field instanceof City)
			return new CityProvider(dataFactory);
		if (field instanceof EmailAddress)
			return new EmailAddressProvider(dataFactory);
		if (field instanceof FirstName)
			return new FirstNameProvider(dataFactory);
		if (field instanceof Item)
			return new ItemProvider(dataFactory, (Item) field);
		if (field instanceof LastName)
			return new LastNameProvider(dataFactory);
		if (field instanceof Name)
			return new NameProvider(dataFactory);
		if (field instanceof NumberText)
			return new NumberTextProvider(dataFactory, (NumberText) field);
		if (field instanceof Prefix)
			return new PrefixProvider(dataFactory, (Prefix) field);
		if (field instanceof RandomString)
			return new StringProvider(dataFactory, (RandomString) field);
		if (field instanceof RandomText)
			return new TextProvider(dataFactory, (RandomText) field);
		if (field instanceof RandomWords)
			return new WordsProvider(dataFactory, (RandomWords) field);
		if (field instanceof RandomWord)
			return new WordProvider(dataFactory, (RandomWord) field);
		if (field instanceof StreetName)
			return new StreetNameProvider(dataFactory);
		if (field instanceof Sequence)
			return new SequenceProvider((Sequence) field);
		if (field instanceof StreetSuffix)
			return new StreetSuffixProvider(dataFactory);
		if (field instanceof Suffix)
			return new SuffixProvider(dataFactory, (Suffix) field);
		if (field instanceof Now)
			return new NowProvider();
		if (field instanceof Regex)
			return new RegexProvider((Regex) field);
		return null;
	}

}
