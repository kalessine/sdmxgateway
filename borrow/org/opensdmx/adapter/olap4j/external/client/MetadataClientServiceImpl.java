package org.opensdmx.adapter.olap4j.external.client;

import java.io.InputStream;
import java.net.URL;

import org.opensdmx.adapter.olap4j.external.client.dto.Flow;
import org.opensdmx.adapter.olap4j.external.client.dto.Flows;
import org.opensdmx.util.OpenSdmxException;

public class MetadataClientServiceImpl extends BaseClient{
	
	private MetadataClientConfiguration clientConfiguration;
	
	public MetadataClientServiceImpl(MetadataClientConfiguration clientConfiguration){
		this.clientConfiguration = clientConfiguration;
	}
	
	public MetadataClientConfiguration getClientConfiguration() {
		return clientConfiguration;
	}

	public void setClientConfiguration(MetadataClientConfiguration clientConfiguration) {
		this.clientConfiguration = clientConfiguration;
	}

	@Override
	public Flow retrieveFlow(String uuid) {
		try {
			InputStream is = deriveInputStream(new URL(clientConfiguration.getFlowUrl(uuid)));
			return (Flow)parseStructure(is, Flow.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OpenSdmxException(e);
		}
	}
	
	
	@Override
	public Flows retrieveFlows() {
		try {
			InputStream is = deriveInputStream(new URL(clientConfiguration.getFlowsUrl()));
			return (Flows)parseStructure(is, Flows.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OpenSdmxException(e);
		}
		
	}
	
}
