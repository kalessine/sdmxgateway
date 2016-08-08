package org.opensdmx.adapter.olap4j.repository.generatormdx;

import org.sdmx.resources.sdmxml.schemas.v2_0.structure.TextTypeType;

/**
 * Given the mdx, this class is able to understand the used FREQ in the time
 * dimension.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class FreqFromMdx {

	/**
	 * 
	 * 
	 * 
	 * @param mdx
	 * @return frequency
	 */
	public TextTypeType understandFregFrom(String mdx) {
		TextTypeType frequency = null;
		if (mdx.toLowerCase().contains("year")) {
			frequency = TextTypeType.YEAR;
		}
		if (mdx.toLowerCase().contains("month")) {
			frequency = TextTypeType.MONTH;
		}
		if (mdx.toLowerCase().contains("day")) {
			frequency = TextTypeType.DAY;
		}
		return frequency;
	}

}
