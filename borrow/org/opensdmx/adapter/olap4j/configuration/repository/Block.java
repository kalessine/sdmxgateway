package org.opensdmx.adapter.olap4j.configuration.repository;

import java.util.List;

/**
 * A block is a list of values. The first value is the measure, the others are
 * attributes of the observation value.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class Block {

	private String measureName;
	private List<BlockAttribute> attributes;

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public List<BlockAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<BlockAttribute> attributes) {
		this.attributes = attributes;
	}

	public int getSize() {
		return attributes != null ? attributes.size() + 1 : 1;
	}

}
