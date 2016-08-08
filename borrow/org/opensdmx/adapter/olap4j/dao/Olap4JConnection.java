package org.opensdmx.adapter.olap4j.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.olap4j.OlapConnection;
import org.opensdmx.util.OpenSdmxException;
import org.springframework.core.io.ClassPathResource;

/**
 * A wrapper in order to retrieve the Olap4J Connection through XMLA. The OLAP4J
 * guys did treat the Olap4J Connection literally in a JDBC way. Very confusing
 * and therefore the imports of this class do have java.sql.* stuff..
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class Olap4JConnection {

	// <olap.jdbc.driverClassName>org.postgresql.Driver</olap.jdbc.driverClassName>
	// <olap.jdbc.hostname>cio049-vm1.hq.un.fao.org</olap.jdbc.hostname>
	// <olap.jdbc.port>5432</olap.jdbc.port>
	// <olap.jdbc.schema>FoodMart</olap.jdbc.schema>
	// <olap.jdbc.username>foodmart</olap.jdbc.username>
	// <olap.jdbc.password>foodmart</olap.jdbc.password>

	private static Logger logger = Logger.getLogger(Olap4JConnection.class);

	private String olapUrl;
	private String catalog;

	public OlapConnection getConnection(String catalogXml) {
		OlapConnection olapConnection = null;
		try {
			// Olap4j driver for generic XML for Analysis (XMLA) providers.
			// Since olap4j is a superset of JDBC, you register this driver as
			// you would any JDBC driver:
			Class.forName("org.olap4j.driver.xmla.XmlaOlap4jDriver");
			Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
			ClassPathResource cpr = new ClassPathResource(catalogXml);
			olapUrl = olapUrl + "; Catalog=file:" + cpr.getFile().getAbsolutePath();
			logger.warn(olapUrl);

			// Then create a connection using a URL with the prefix
			// "jdbc:xmla:". For example,
			Connection connection = DriverManager.getConnection(olapUrl);
			olapConnection = connection.unwrap(OlapConnection.class);
			if (catalog != null) {
				olapConnection.setCatalog(catalog);
			}
		} catch (ClassNotFoundException e) {
			throw new OpenSdmxException(e);
		} catch (SQLException e) {
			throw new OpenSdmxException(e);
		} catch (IOException e) {
			throw new OpenSdmxException(e);
		}
		return olapConnection;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setOlapUrl(String olapUrl) {
		this.olapUrl = olapUrl;
	}

}
