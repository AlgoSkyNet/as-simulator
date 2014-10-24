package com.tibco.as.simulator;

import com.tibco.as.io.DestinationConfig;

public class SpaceConfig extends DestinationConfig {

	private static final long DEFAULT_LIMIT = 100;

	private Long sleep;

	public Long getSleep() {
		return sleep;
	}

	public void setSleep(Long sleep) {
		this.sleep = sleep;
	}

	@Override
	public SpaceConfig clone() {
		SpaceConfig export = new SpaceConfig();
		copyTo(export);
		return export;
	}

	public void copyTo(SpaceConfig target) {
		target.sleep = sleep;
		super.copyTo(target);
	}

	@Override
	protected SimulatorField newField() {
		return new SimulatorField();
	}

	@Override
	public Long getLimit() {
		Long limit = super.getLimit();
		if (limit == null) {
			return DEFAULT_LIMIT;
		}
		if (limit.equals(-1L)) {
			return null;
		}
		return limit;
	}

	@Override
	public boolean isWildcard() {
		return getSpace() == null;
	}

}
