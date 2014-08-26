package com.tibco.as.simulator;

import java.util.List;

import org.fluttercode.datafactory.NameDataValues;

import com.tibco.as.simulator.Names;

public class CustomNameDataValues implements NameDataValues {

	private Names names;

	public CustomNameDataValues(Names names) {
		this.names = names;
	}

	@Override
	public String[] getFirstNames() {
		List<String> firstNames = names.getFirstName();
		return firstNames.toArray(new String[firstNames.size()]);
	}

	@Override
	public String[] getLastNames() {
		List<String> lastNames = names.getLastName();
		return lastNames.toArray(new String[lastNames.size()]);
	}

	@Override
	public String[] getPrefixes() {
		List<String> prefixes = names.getPrefix();
		return prefixes.toArray(new String[prefixes.size()]);
	}

	@Override
	public String[] getSuffixes() {
		List<String> suffixes = names.getSuffix();
		return suffixes.toArray(new String[suffixes.size()]);
	}

}
