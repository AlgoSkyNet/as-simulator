package com.tibco.as.simulator;

import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;

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
import com.tibco.as.simulator.StreetName;
import com.tibco.as.simulator.StreetSuffix;
import com.tibco.as.simulator.Suffix;
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
import com.tibco.as.simulator.provider.RandomCharsProvider;
import com.tibco.as.simulator.provider.RandomTextProvider;
import com.tibco.as.simulator.provider.RandomWordProvider;
import com.tibco.as.simulator.provider.RandomWordsProvider;
import com.tibco.as.simulator.provider.RegexProvider;
import com.tibco.as.simulator.provider.SequenceProvider;
import com.tibco.as.simulator.provider.ShortProvider;
import com.tibco.as.simulator.provider.StreetNameProvider;
import com.tibco.as.simulator.provider.StreetSuffixProvider;
import com.tibco.as.simulator.provider.SuffixProvider;

public class ValueProviderFactory {

	private Random random;

	private DataFactory df;

	public ValueProviderFactory(DataFactory dataFactory, Random random) {
		this.df = dataFactory;
		this.random = random;
	}

	public IValueProvider create(SimField field) {
		if (field instanceof RandomBlob)
			return new BlobProvider(random, (RandomBlob) field);
		if (field instanceof RandomBoolean)
			return new BooleanProvider(random);
		if (field instanceof RandomChar)
			return new CharProvider(df);
		if (field instanceof Constant)
			return new ConstantProvider((Constant) field);
		if (field instanceof RandomDateTime)
			return new DateTimeProvider(random, (RandomDateTime) field);
		if (field instanceof RandomDouble)
			return new DoubleProvider(random, (RandomDouble) field);
		if (field instanceof RandomFloat)
			return new FloatProvider(random);
		if (field instanceof RandomInteger)
			return new IntegerProvider(random, (RandomInteger) field);
		if (field instanceof RandomLong)
			return new LongProvider(random);
		if (field instanceof RandomShort)
			return new ShortProvider(random, (RandomShort) field);
		if (field instanceof Address)
			return new AddressProvider(df);
		if (field instanceof AddressLine2)
			return new AddressLine2Provider(df, (AddressLine2) field);
		if (field instanceof BirthDate)
			return new BirthDateProvider(df);
		if (field instanceof BusinessName)
			return new BusinessNameProvider(df);
		if (field instanceof City)
			return new CityProvider(df);
		if (field instanceof EmailAddress)
			return new EmailAddressProvider(df);
		if (field instanceof FirstName)
			return new FirstNameProvider(df);
		if (field instanceof Item)
			return new ItemProvider(df, (Item) field);
		if (field instanceof LastName)
			return new LastNameProvider(df);
		if (field instanceof Name)
			return new NameProvider(df);
		if (field instanceof NumberText)
			return new NumberTextProvider(df, (NumberText) field);
		if (field instanceof Prefix)
			return new PrefixProvider(df, (Prefix) field);
		if (field instanceof RandomChars)
			return new RandomCharsProvider(df, (RandomChars) field);
		if (field instanceof RandomText)
			return new RandomTextProvider(df, (RandomText) field);
		if (field instanceof RandomWords)
			return new RandomWordsProvider(df, (RandomWords) field);
		if (field instanceof RandomWord)
			return new RandomWordProvider(df, (RandomWord) field);
		if (field instanceof StreetName)
			return new StreetNameProvider(df);
		if (field instanceof Sequence)
			return new SequenceProvider((Sequence) field);
		if (field instanceof StreetSuffix)
			return new StreetSuffixProvider(df);
		if (field instanceof Suffix)
			return new SuffixProvider(df, (Suffix) field);
		if (field instanceof Now)
			return new NowProvider();
		if (field instanceof Regex)
			return new RegexProvider((Regex) field);
		return null;
	}
}
