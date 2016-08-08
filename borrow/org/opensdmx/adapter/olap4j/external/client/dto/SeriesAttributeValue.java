package org.opensdmx.adapter.olap4j.external.client.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="seriesAttributeValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeriesAttributeValue extends AttributeValue{
	
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
