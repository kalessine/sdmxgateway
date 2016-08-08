package org.opensdmx.adapter.olap4j.external.client;

import org.opensdmx.adapter.olap4j.external.client.dto.Flow;
import org.opensdmx.adapter.olap4j.external.client.dto.Flows;

public interface Client {
	/**
	 * Returns the flow information by given uuid
	 * @param uuid
	 * @return
	 */
	public Flow retrieveFlow(String uuid); 
	
	/**
	 * Returns all available flows.
	 * @param
	 * @return
	 */
	public Flows retrieveFlows();
	
}
