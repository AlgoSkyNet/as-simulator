package com.tibco.as.simulator;

import com.tibco.as.io.IInputStream;
import com.tibco.as.space.SpaceDef;

public class SimulatorInputStream implements IInputStream<Object[]> {

	private static final long DEFAULT_SIZE = 100;

	private SimulatorDestination destination;
	private IValueProvider[] providers;
	private long position;
	private boolean open;
	private Long size;

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
		size = getSize();
		position = 0L;
		open = true;
	}

	private Long getSize() {
		if (destination.getSpace().getSize() == null) {
			return DEFAULT_SIZE;
		}
		long size = destination.getSpace().getSize();
		if (size == -1) {
			return null;
		}
		return size;
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
		if (!underLimit()) {
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

	private boolean underLimit() {
		if (size == null) {
			return true;
		}
		return position < size;
	}

	@Override
	public Long size() {
		return null;
	}

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return destination.getSpace().getName();
	}

}
