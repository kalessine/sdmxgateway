package org.opensdmx.adapter.olap4j.configuration.repository;

public class BlockAttribute {

	/**
	 * TODO Check whether this one can be deleted.
	 */
	private String measureName;

	private String concept;
	/**
	 * default is true
	 */
	private boolean use = true;

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

}
