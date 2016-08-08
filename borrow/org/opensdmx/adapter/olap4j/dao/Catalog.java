package org.opensdmx.adapter.olap4j.dao;

/**
 * Catalog has all the parameters to build up a OLAP4J Catalog.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class Catalog {

	/**
	 * The Olap4j Catalog, synonym to MdxMetadata.catalog
	 */
	private String catalog;

	/**
	 * Host of the db
	 */
	private String host;

	/**
	 * Port of the db
	 */
	private String port;

	/**
	 * Filename of the catalog
	 */
	private String file;

	/**
	 * DB schema
	 */
	private String schema;

	/**
	 * Username to enter the DB
	 */
	private String username;

	/**
	 * Password to enter the DB
	 * 
	 */
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
