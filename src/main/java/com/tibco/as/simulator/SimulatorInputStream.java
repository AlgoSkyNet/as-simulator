package com.tibco.as.simulator;

import com.tibco.as.io.IInputStream;
import com.tibco.as.space.SpaceDef;

public class SimulatorInputStream implements IInputStream {

	private SimulatorDestination destination;
	private IValueProvider[] providers;
	private Long position;
	private boolean open;

	public SimulatorInputStream(SimulatorDestination destination) {
		this.destination = destination;
	}

	@Override
	public synchronized void open() throws Exception {
		String spaceName = destination.getSpaceDef().getName();
		SpaceDef spaceDef = destination.getChannel().getMetaspace()
				.getSpaceDef(spaceName);
		if (spaceDef != null) {
			destination.setSpaceDef(spaceDef);
		}
		providers = destination.getValueProviders();
		position = 0L;
		open = true;
	}

	@Override
	public synchronized void close() throws Exception {
		open = false;
	}

	@Override
	public Object[] read() throws Exception {
		if (!open) {
			return null;
		}
		if (destination.getSpace().getSleep() != null) {
			Thread.sleep(destination.getSpace().getSleep());
		}
		Object[] data = new Object[providers.length];
		for (int i = 0; i < providers.length; i++) {
			data[i] = providers[i].getValue();
		}
		position++;
		return data;
	}

	@Override
	public Long size() {
		return null;
	}

	@Override
	public Long getPosition() {
		return position;
	}

	@Override
	public long getOpenTime() {
		return 0;
	}

}
