package com.tibco.as.simulator;

import com.tibco.as.convert.ConverterFactory;
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
import com.tibco.as.simulator.xml.RandomChars;
import com.tibco.as.simulator.xml.RandomDateTime;
import com.tibco.as.simulator.xml.RandomDouble;
import com.tibco.as.simulator.xml.RandomFloat;
import com.tibco.as.simulator.xml.RandomInteger;
import com.tibco.as.simulator.xml.RandomLong;
import com.tibco.as.simulator.xml.RandomShort;
import com.tibco.as.simulator.xml.RandomText;
import com.tibco.as.simulator.xml.RandomWord;
import com.tibco.as.simulator.xml.RandomWords;
import com.tibco.as.simulator.xml.Regex;
import com.tibco.as.simulator.xml.Sequence;
import com.tibco.as.simulator.xml.StreetName;
import com.tibco.as.simulator.xml.StreetSuffix;
import com.tibco.as.simulator.xml.Suffix;
import com.tibco.as.space.FieldDef.FieldType;

public class SimulatorFieldConfig extends com.tibco.as.io.FieldConfig {

	private Field field;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String getFieldName() {
		if (super.getFieldName() == null) {
			if (field.getName() == null) {
				return field.getClass().getSimpleName();
			}
			return field.getName();
		}
		return super.getFieldName();
	}

	@Override
	public FieldType getFieldType() {
		if (super.getFieldType() == null) {
			return ConverterFactory.getFieldType(getJavaType());
		}
		return super.getFieldType();
	}

	@Override
	public Class<?> getJavaType() {
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
		return super.getJavaType();
	}

	@Override
	public Boolean getFieldNullable() {
		if (super.getFieldNullable() == null) {
			return field.isNullable();
		}
		return super.getFieldNullable();
	}

}
