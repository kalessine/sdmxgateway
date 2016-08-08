package org.opensdmx.adapter.olap4j.configuration.repository;

import java.util.Map;

/**
 * The Olap4j returns a cellset. The conventions is that the measure can be
 * returned in a 'block'. A block can be found by the concept name of the
 * meausure.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class CellSetMeasureStructure {

	private Map<String, Block> blockMap;

	public Map<String, Block> getBlockMap() {
		return blockMap;
	}

	public void setBlockMap(Map<String, Block> blockMap) {
		this.blockMap = blockMap;
	}

}
