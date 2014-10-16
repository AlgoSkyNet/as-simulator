package com.tibco.as.simulator;

import com.tibco.as.io.DestinationConfig;

public class SpaceConfig extends DestinationConfig {

	private static final long DEFAULT_LIMIT = 100;

	private SimulatorConfig channel;
	private Long sleep;

	public SpaceConfig(SimulatorConfig channel) {
		this.channel = channel;
	}

	public Long getSleep() {
		return sleep;
	}

	public void setSleep(Long sleep) {
		this.sleep = sleep;
	}

	@Override
	public SpaceConfig clone() {
		SpaceConfig export = new SpaceConfig(channel);
		copyTo(export);
		return export;
	}

	public void copyTo(SpaceConfig target) {
		target.sleep = sleep;
		super.copyTo(target);
	}

	@Override
	protected SimulatorFieldConfig newField() {
		return new SimulatorFieldConfig();
	}

	@Override
	public Long getLimit() {
		Long limit = super.getLimit();
		if (limit == null) {
			return DEFAULT_LIMIT;
		}
		return limit;
	}

	@Override
	public boolean isWildcard() {
		return getSpace() == null;
	}

}
