package org.opensdmx.adapter.olap4j.external.client.dto;

import java.util.List;


public class BlockStructure{
	private String measureName;
	private List<BlockAttributeStructure> attributes;
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	public List<BlockAttributeStructure> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<BlockAttributeStructure> attributes) {
		this.attributes = attributes;
	}
	
}
