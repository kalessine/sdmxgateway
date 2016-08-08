package org.opensdmx.adapter.olap4j.dao.techcdr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TechCDRClientImpl implements TechCDRClient {

	private final static Logger logger = LoggerFactory.getLogger(TechCDRClientImpl.class);
	
	private RestTemplate restTemplate;
	private String baseUrl;

	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public SchemaDTO getSchema(String wks, String cat, String sch) {
		
		SchemaDTO schema = null;
		try {
			schema = restTemplate.getForObject(
					baseUrl + "/services/resources/schemas/{workspace}/{composite}", 
					SchemaDTO.class, 
					wks, 
					cat + "." + sch
					);
		} catch(HttpClientErrorException e) {
			logger.debug("Exception caught while accessing schema.", e);
			return null;
		}
		
		return schema;
	}
	
	@Override
	public ConnectionDTO getConnection(String wks, String con) {
		
		ConnectionDTO connection = null;
		try {
			connection = restTemplate.getForObject(
					baseUrl + "/services/resources/connections/{workspace}/{composite}", 
					ConnectionDTO.class, 
					wks, 
					con
					);
		} catch(HttpClientErrorException e) {
			logger.debug("Exception caught while accessing connection.", e);
			return null;
		}
		
		return connection;
	}
	
	@Override
	public String getSchemaXml(String wks, String cat, String sch, String lng) {
		
		String xml = null;
		try {
			xml = restTemplate.getForObject(
					baseUrl + "/services/resources/schemas/{workspace}/{composite}/xml/{language}", 
					String.class, 
					wks, 
					cat + "." + sch,
					lng
					);
		} catch(HttpClientErrorException e) {
			logger.debug("Exception caught while accessing xml schema.", e);
			return null;
		}
		
		return xml;
	}
}
