package com.tibco.as.simulator;

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
import com.tibco.as.simulator.xml.StreetName;
import com.tibco.as.simulator.xml.StreetSuffix;
import com.tibco.as.simulator.xml.Suffix;
import com.tibco.as.simulator.xml.Text;
import com.tibco.as.simulator.xml.Word;
import com.tibco.as.simulator.xml.Words;
import com.tibco.as.space.FieldDef.FieldType;

public class SimulatorField extends com.tibco.as.convert.Field {

	private Field field;

	@Override
	public SimulatorField clone() {
		SimulatorField clone = new SimulatorField();
		copyTo(clone);
		return clone;
	}

	@Override
	public void copyTo(com.tibco.as.convert.Field fieldConfig) {
		SimulatorField target = (SimulatorField) fieldConfig;
		target.field = field;
		super.copyTo(target);
	}

	public Field getField() {
		if (field == null) {
			switch (getFieldType()) {
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
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String getFieldName() {
		String fieldName = super.getFieldName();
		if (fieldName == null) {
			fieldName = field.getFieldName();
			if (fieldName == null) {
				return field.getClass().getSimpleName();
			}
		}
		return fieldName;
	}

	@Override
	public FieldType getFieldType() {
		FieldType fieldType = super.getFieldType();
		if (fieldType == null) {
			return getJavaFieldType();
		}
		return fieldType;
	}

	@Override
	public Class<?> getJavaType() {
		Field field = getField();
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

}
