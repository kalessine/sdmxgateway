package org.opensdmx.adapter.olap4j.external.client.dto;

import java.util.Map;

public class MeasureStructure{
	private Map<String, BlockStructure> blockStructureMap;

	public Map<String, BlockStructure> getBlockStructureMap() {
		return blockStructureMap;
	}

	public void setBlockStructureMap(Map<String, BlockStructure> blockStructureMap) {
		this.blockStructureMap = blockStructureMap;
	}
	
}
