package com.tibco.as.simulator;

import com.tibco.as.io.IInputStream;

public class SimulationInputStream implements IInputStream<Object[]> {

	private IValueProvider[] providers;

	private long position;

	private SpaceConfig config;

	public SimulationInputStream(SpaceConfig config, IValueProvider[] providers) {
		this.config = config;
		this.providers = providers;
	}

	@Override
	public void open() throws Exception {
		position = 0;
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
		Long sleep = config.getSleep();
		if (sleep != null) {
			Thread.sleep(sleep);
		}
		if (isClosed()) {
			return null;
		}
		if (config.getSize() == null || position < config.getSize()) {
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
		return config.getSize();
	}

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public long getOpenTime() {
		return 0;
	}

}
