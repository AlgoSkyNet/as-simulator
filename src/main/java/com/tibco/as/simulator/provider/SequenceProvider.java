package com.tibco.as.simulator.provider;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.Sequence;

public class SequenceProvider implements IValueProvider {

	private long index;
	private Long end;

	public SequenceProvider(Sequence field) {
		this.index = field.getStart();
		this.end = field.getEnd();
	}

	@Override
	public Long getValue() {
		return end == null ? index++ : (index++ % end);
	}
}
