package com.tibco.as.simulator;

import com.tibco.as.io.Import;
import com.tibco.as.simulator.Space;

public class SimulationImport extends Import {

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
