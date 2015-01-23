package com.tibco.as.simulator.provider;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.xml.Item;

public class ItemProvider implements IValueProvider {

	private DataFactory df;
	private Item field;
	private List<Object> values;

	public ItemProvider(DataFactory df, Item field) {
		this.df = df;
		this.field = field;
		this.values = getValues(field);
	}

	@Override
	public Object getValue() {
		if (field.getDefault() == null) {
			if (field.getProbability() == null) {
				return df.getItem(values);
			}
			return df.getItem(values, field.getProbability());
		}
		return df.getItem(values, field.getProbability(), field.getDefault());
	}

	private List<Object> getValues(Item field) {
		List<Object> values = new ArrayList<Object>();
		for (Object value : field.getValue()) {
			if (value instanceof Element) {
				Element element = (Element) value;
				NodeList childNodes = element.getChildNodes();
				for (int index = 0; index < childNodes.getLength(); index++) {
					Node item = childNodes.item(index);
					values.add(item.getNodeValue());
				}
			} else {
				values.add(value);
			}
		}
		return values;
	}

}
