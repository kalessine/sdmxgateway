package org.opensdmx.adapter.olap4j.external;

import java.util.List;

/**
 * Code is a simplified holder for a code which will be used to create a SDMX
 * code from.
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class Code {

	private String code;

	private List<Description> descriptions;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}

}
