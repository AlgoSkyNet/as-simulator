package com.tibco.as.simulator;

import com.tibco.as.io.DestinationConfig;
import com.tibco.as.io.IInputStream;

public class SpaceConfig extends DestinationConfig {

	private Long sleep;
	private Long size;

	public Long getSleep() {
		return sleep;
	}

	public void setSleep(Long sleep) {
		this.sleep = sleep;
	}

	public Long getSize() {
		if (size == null) {
			return IInputStream.UNKNOWN_SIZE;
		}
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public SpaceConfig clone() {
		SpaceConfig export = new SpaceConfig();
		copyTo(export);
		return export;
	}

	public void copyTo(SpaceConfig target) {
		target.size = size;
		target.sleep = sleep;
		super.copyTo(target);
	}

}
