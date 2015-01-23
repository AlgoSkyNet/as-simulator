package com.tibco.as.simulator.provider;

import nl.flotsam.xeger.Xeger;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.Regex;

public class RegexProvider implements IValueProvider {

	private Xeger xeger;

	public RegexProvider(Regex regex) {
		this.xeger = new Xeger(regex.getRegex());
	}

	@Override
	public String getValue() {
		return xeger.generate();
	}

	public static void main(String[] args) {
		String regex = "(+1)? ?(\\([0-9]{3}\\)|[0-9]{3})[- ]?([0-9]{3})[- ]?([0-9]{4})";
		for (int i = 0; i < 10; i++) {
			System.out.println(new Xeger(regex).generate());
		}
	}

}
