package org.opensdmx.adapter.olap4j.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.olap4j.OlapConnection;
import org.opensdmx.util.OpenSdmxException;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * The OlapConnector foresees a number of catalogs on different databases.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class OlapConnectorConfigurationBasedImpl implements OlapConnector {

	private OlapGate olapGate;

	/**
	 * Connect to a certain catalog.
	 * 
	 * 
	 * @param catalog
	 * @return
	 */
	@Override
	public OlapConnection connectTo(String catalog) {

		List<Catalog> catalogList = olapGate.getCatalogList();
		Catalog foundCatalog = null;
		for (Catalog cat : catalogList) {
			if (cat.getCatalog().equals(catalog)) {
				foundCatalog = cat;
			}
		}
		if (foundCatalog == null) {
			return null;
		}
		
		return composeConnection(foundCatalog);
	}

	
	private OlapConnection composeConnection(Catalog foundCatalog) {
		String olapUrl = "jdbc:mondrian:JdbcDrivers=org.postgresql.Driver;Jdbc=jdbc:postgresql://"
				+ foundCatalog.getHost() + ":" + foundCatalog.getPort() + "/" + foundCatalog.getSchema() + ";JdbcUser="
				+ foundCatalog.getUsername() + ";JdbcPassword=" + foundCatalog.getPassword();

		OlapConnection olapConnection = null;
		try {
			// Olap4j driver for generic XML for Analysis (XMLA) providers.
			// Since olap4j is a superset of JDBC, you register this driver as
			// you would any JDBC driver:
			Class.forName("org.olap4j.driver.xmla.XmlaOlap4jDriver");
			Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
			ClassPathResource cpr = new ClassPathResource(foundCatalog.getFile());
			olapUrl = olapUrl + "; Catalog=file:" + cpr.getFile().getAbsolutePath();

			// Then create a connection using a URL with the prefix
			// "jdbc:xmla:". For example,
			Connection connection = DriverManager.getConnection(olapUrl);
			olapConnection = connection.unwrap(OlapConnection.class);
		} catch (ClassNotFoundException e) {
			throw new OpenSdmxException(e);
		} catch (SQLException e) {
			throw new OpenSdmxException(e);
		} catch (IOException e) {
			throw new OpenSdmxException(e);
		}
		return olapConnection;
	}

	
	public void setOlapGate(OlapGate olapGate) {
		this.olapGate = olapGate;
	}
}
