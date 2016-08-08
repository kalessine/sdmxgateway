package org.opensdmx.adapter.olap4j.external.client;

/**
 * All parameter for this configuration class has been config in /opensdmx-mondrian-adapter.xml
 */
public class MetadataClientConfiguration {
	
	private String flowUrl;
	
	private String flowsUrl;
	
	public MetadataClientConfiguration(){}

	/**
	 * Returns the default URL that use for request the flow information
	 * @return
	 */
	public String getFlowUrl() {
		return flowUrl;
	}
	
	/**
	 * Returns the URL that use for request the flow information. It will be replaced {uuid} with the given uuid. 
	 * @param uuid
	 * @return
	 */
	public String getFlowUrl(String uuid){
		return flowUrl.replaceAll("\\{uuid\\}", uuid);
	}
	
	public void setFlowUrl(String flowUrl) {
		this.flowUrl = flowUrl;
	}
	
	/**
	 * Returns the default URL for request the flows information
	 * @return
	 */
	public String getFlowsUrl() {
		return flowsUrl;
	}

	public void setFlowsUrl(String flowsUrl) {
		this.flowsUrl = flowsUrl;
	}
	
}
