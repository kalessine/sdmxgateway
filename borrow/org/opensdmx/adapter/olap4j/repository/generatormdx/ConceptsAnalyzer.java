package org.opensdmx.adapter.olap4j.repository.generatormdx;

import java.util.HashMap;
import java.util.Map;

import org.opensdmx.adapter.olap4j.external.MetadataService;

public class ConceptsAnalyzer {

	public void analyzeConcepts(MetadataService metadataService, Datastructure datastructure) {

		datastructure.setConcepts(new HashMap<String, Map<String,String>>());
		
		String[] flows = metadataService.getFlows();
		String[] dimensions = datastructure.getDimensionNames();
		
		for(String flow : flows) {
			
			Map<String, String> map = new HashMap<String, String>();
			datastructure.getConcepts().put(flow, map);
			
			for(String dimension : dimensions) {
				
				String concept = metadataService.getConcept(flow, dimension);
				map.put(dimension, concept);
			}
		}
		
	}

}
