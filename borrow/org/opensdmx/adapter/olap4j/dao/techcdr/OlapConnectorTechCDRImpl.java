package org.opensdmx.adapter.olap4j.dao.techcdr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.olap4j.OlapConnection;
import org.opensdmx.adapter.olap4j.dao.OlapConnector;
import org.opensdmx.util.OpenSdmxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dario Fabbri
 * 
 */
public class OlapConnectorTechCDRImpl implements OlapConnector {

	private static final Logger logger = LoggerFactory.getLogger(OlapConnectorTechCDRImpl.class);
	
	private TechCDRClient restClient;

	public void setRestClient(TechCDRClient restClient) {
		this.restClient = restClient;
	}

	public OlapConnectorTechCDRImpl() {

		// Register Mondrian JDBC driver.
		//
		try {
			Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
		} catch (ClassNotFoundException e) {
			throw new OpenSdmxException("Exception caught while registering Mondrian driver.", e);
		}
	}

	@Override
	public OlapConnection connectTo(String catalog) {

		// Split catalog into its parts.
		//
		String[] parts = catalog.split("\\.");
		if(parts.length != 4) {
			logger.debug("Invalid catalog specified: " + catalog);
			return null;
		}
		
		// Extract schema coordinates.
		//
		String wks = parts[0];
		String cat = parts[1];
		String sch = parts[2];
		String lng = parts[3];
		
		// Retrieve schema.
		//
		SchemaDTO schema = restClient.getSchema(wks, cat, sch);
		if(schema == null)
			return null;
		
		// Retrieve connection data.
		//
		ConnectionDTO connection = restClient.getConnection(wks, schema.getConnectionName());

		// Retrieve schema.
		//
		String xml = restClient.getSchemaXml(wks, cat, sch, lng);
		xml = xml.replaceAll("\"", "\"\"");
				
		// Create connection string.
		//
		MessageFormat mf = new MessageFormat(
				"jdbc:mondrian:" +
				"JdbcDrivers={0};" +
				"Jdbc={1};" +
				"JdbcUser={2};" +
				"JdbcPassword={3};" +
				"CatalogContent=\"{4}\";");
		String[] args = new String[5];
		args[0] = connection.getDriver();
		args[1] = connection.getUrl();
		args[2] = connection.getUsername();
		args[3] = connection.getPassword();
		args[4] = xml;
		String jdbcUrl = mf.format(args);

		// Establish connection.
		//
		Connection cn;
		try {
			cn = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			throw new OpenSdmxException("Unable to establish a connection.", e);
		}

		// Unwrap OLAP connection object.
		//
		OlapConnection olapConnection = null;
		try {
			olapConnection = cn.unwrap(OlapConnection.class);
		} catch (SQLException e) {
			try {
				cn.close();
			}
			catch(Exception e1) {
				logger.error("Unable to release connection while bailing out. Close issued the following exception.", e1);
			}

			throw new OpenSdmxException("Exception caught while unwrapping OLAP connection.", e);
		}

		return olapConnection;
	}
}
