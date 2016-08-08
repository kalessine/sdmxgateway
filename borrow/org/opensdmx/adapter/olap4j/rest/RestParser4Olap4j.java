package org.opensdmx.adapter.olap4j.rest;

import org.opensdmx.rest.RestParser;
import org.opensdmx.util.OpenSdmxException;

/**
 * Adds to the RestParser the logic necessary for parsing the flowRef according
 * the conventions followed in the mondrian adapter.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class RestParser4Olap4j extends RestParser {

	/**
	 * Simple implementation of the parsing of the flowRef into a catalog and
	 * cube.
	 * 
	 * Uses the convention flowRef = {catalog}.{cube}
	 * 
	 * @param flowRef
	 * @return flowDef
	 */
	public String[] parseFlowRef(String flowRef) {

		// logic
		String[] flowDef = flowRef.split("\\.");

		// postcondition
		if (flowDef.length != 2) {
			throw new OpenSdmxException(
					"Committed deadly sin, the flowRef has to be composed of 2 elements(catalog and cube) seperated by a dot.");
		}
		return flowDef;
	}

}
