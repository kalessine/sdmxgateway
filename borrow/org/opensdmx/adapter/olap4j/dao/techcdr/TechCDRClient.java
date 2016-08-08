package org.opensdmx.adapter.olap4j.dao.techcdr;

public interface TechCDRClient {

	SchemaDTO getSchema(String wks, String cat, String sch);
	
	ConnectionDTO getConnection(String wks, String con);
	
	String getSchemaXml(String wks, String cat, String sch, String lng);
}
