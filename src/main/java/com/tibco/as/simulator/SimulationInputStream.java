package com.tibco.as.simulator;

import java.text.MessageFormat;

import com.tibco.as.io.IInputStream;
import com.tibco.as.simulator.Space;

public class SimulationInputStream implements IInputStream<Object[]> {

	private IValueProvider[] providers;

	private long position;

	private Space space;

	private SimulationImport config;

	private long size;

	public SimulationInputStream(SimulationImport config,
			IValueProvider[] providers) {
		this.config = config;
		this.space = config.getSpace();
		this.providers = providers;
	}

	@Override
	public void open() throws Exception {
		position = 0;
		size = getSize();
	}

	private long getSize() {
		if (space.getSize() == null) {
			return UNKNOWN_SIZE;
		}
		return space.getSize();
	}

	@Override
	public void close() throws Exception {
		providers = null;
	}

	@Override
	public boolean isClosed() {
		return providers == null;
	}

	@Override
	public Object[] read() throws Exception {
		Long sleep = space.getSleep();
		if (sleep != null) {
			Thread.sleep(sleep);
		}
		if (isClosed()) {
			return null;
		}
		if (space.getSize() == null || position < space.getSize()) {
			Object[] data = new Object[providers.length];
			for (int i = 0; i < providers.length; i++) {
				data[i] = providers[i].getValue();
			}
			position++;
			return data;
		}
		return null;
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return MessageFormat.format("space ''{0}''", config.getSpaceName());
	}
	
	@Override
	public long getOpenTime() {
		return 0;
	}

}
