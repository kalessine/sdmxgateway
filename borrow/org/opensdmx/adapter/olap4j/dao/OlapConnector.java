package org.opensdmx.adapter.olap4j.dao;

import org.olap4j.OlapConnection;

/**
 * 
 * @author Dario fabbri
 * 
 */
public interface OlapConnector {

	/**
	 * Connect to a certain catalog.
	 * 
	 * 
	 * @param catalog
	 * @return
	 */
	OlapConnection connectTo(String catalog);
}
