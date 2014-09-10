package com.tibco.as.simulator;

import com.tibco.as.io.AbstractImport;

public class SimulationImport extends AbstractImport {

	Space space;

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	@Override
	public SimulationImport clone() {
		SimulationImport clone = new SimulationImport();
		copyTo(clone);
		return clone;
	}

	public void copyTo(SimulationImport target) {
		target.space = space;
		super.copyTo(target);
	}

}
