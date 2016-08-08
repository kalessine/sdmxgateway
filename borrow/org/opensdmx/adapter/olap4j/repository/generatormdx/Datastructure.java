package org.opensdmx.adapter.olap4j.repository.generatormdx;

import java.util.Map;

/**
 * This class is used to express the metadata (datastructure) of a Olap4jCellset
 * in terms of SDMX.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class Datastructure {

	private int numberOfDimensions;
	private String[] dimensionNames;
	private String measureConcept;
	private Map<String, Map<String, String>> concepts;

	public int getNumberOfDimensions() {
		return numberOfDimensions;
	}

	public void setNumberOfDimensions(int numberOfDimensions) {
		this.numberOfDimensions = numberOfDimensions;
	}

	public String[] getDimensionNames() {
		return dimensionNames;
	}

	public void setDimensionNames(String[] dimensionNames) {
		this.dimensionNames = dimensionNames;
	}

	public String getMeasureConcept() {
		return measureConcept;
	}

	public void setMeasureConcept(String measureConcept) {
		this.measureConcept = measureConcept;
	}

	public Map<String, Map<String, String>> getConcepts() {
		return concepts;
	}

	public void setConcepts(Map<String, Map<String, String>> concepts) {
		this.concepts = concepts;
	}

}
