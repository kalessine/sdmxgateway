package org.opensdmx.adapter.olap4j.external;

/**
 * Name value holder.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class NameValue {

	public NameValue(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
