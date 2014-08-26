package com.tibco.as.simulator;

import java.util.List;

import org.fluttercode.datafactory.AddressDataValues;

import com.tibco.as.simulator.Addresses;

public class CustomAddressDataValues implements AddressDataValues {

	private Addresses addresses;

	public CustomAddressDataValues(Addresses addresses) {
		this.addresses = addresses;
	}

	@Override
	public String[] getAddressSuffixes() {
		List<String> suffixes = addresses.getSuffix();
		return suffixes.toArray(new String[suffixes.size()]);
	}

	@Override
	public String[] getCities() {
		List<String> cities = addresses.getCity();
		return cities.toArray(new String[cities.size()]);
	}

	@Override
	public String[] getStreetNames() {
		List<String> streetNames = addresses.getStreetName();
		return streetNames.toArray(new String[streetNames.size()]);
	}

}
