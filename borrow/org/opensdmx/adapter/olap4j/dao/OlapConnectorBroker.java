package org.opensdmx.adapter.olap4j.dao;

import java.util.List;

import org.olap4j.OlapConnection;
import org.opensdmx.util.OpenSdmxException;

/**
 * 
 * @author Dario Fabbri
 * 
 */
public class OlapConnectorBroker implements OlapConnector {

	private List<OlapConnector> connectors;

	public List<OlapConnector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<OlapConnector> connectors) {
		this.connectors = connectors;
	}


	@Override
	public OlapConnection connectTo(String catalog) {

		OlapConnection foundOlapConnection = null;
		
		for(OlapConnector connector : connectors) {
			
			OlapConnection olapConnection = connector.connectTo(catalog);
			if(olapConnection != null) {
				if(foundOlapConnection != null) {
					throw new OpenSdmxException("More than one connection found for this catalog: ");
				}
				
				foundOlapConnection = olapConnection;
			}
		}
		
		if(foundOlapConnection == null) {
			throw new OpenSdmxException("No connection found for this catalog: " + catalog);
		}
		
		return foundOlapConnection;
	}
}
