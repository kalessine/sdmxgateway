/**
 * 
 */
package org.opensdmx.adapter.olap4j.external.client.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dario Fabbri
 * 
 */
@XmlRootElement(name="Flows")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flows {

	@XmlElement(name="uuid")
	private List<String> flows;

	public List<String> getFlows() {
		return flows;
	}

	public void setFlows(List<String> flows) {
		this.flows = flows;
	}

}
