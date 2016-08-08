package org.opensdmx.adapter.olap4j.dao;

import java.util.List;

/**
 * This class holds a list of olap connections. Every Olap4JConnection
 * represents a Olap4J catalog.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class OlapGate {

	private List<Catalog> catalogList;

	public List<Catalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<Catalog> catalogList) {
		this.catalogList = catalogList;
	}

}
