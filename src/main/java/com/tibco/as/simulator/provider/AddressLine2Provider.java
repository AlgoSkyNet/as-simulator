package com.tibco.as.simulator.provider;

import org.fluttercode.datafactory.impl.DataFactory;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.AddressLine2;

public class AddressLine2Provider implements IValueProvider {

	private DataFactory df;
	private AddressLine2 field;

	public AddressLine2Provider(DataFactory df, AddressLine2 field) {
		this.df = df;
		this.field = field;
	}

	@Override
	public String getValue() {
		if (field.getDefault() == null) {
			if (field.getProbability() == null) {
				return df.getAddressLine2();
			}
			return df.getAddressLine2(field.getProbability());
		}
		if (field.getProbability() == null) {
			df.getAddressLine2(0, field.getDefault());
		}
		return df.getAddressLine2(field.getProbability(), field.getDefault());
	}

}
