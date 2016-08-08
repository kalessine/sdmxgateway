package org.opensdmx.adapter.olap4j.repository.generatormdx;

import java.util.ArrayList;
import java.util.List;

import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.configuration.repository.GenericData;
import org.opensdmx.configuration.repository.RepositoryArtefact;
import org.opensdmx.configuration.repository.dynamic.DynamicGenericDatalist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class generates the list of dataset, having each dataset represented by
 * a MDX1.
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */

@Component
public class MdxPerDataset implements DynamicGenericDatalist {

	@Autowired
	MetadataService metadataService;

	@Override
	public List<RepositoryArtefact> getGenericDataList() {
		List<RepositoryArtefact> list = new ArrayList<RepositoryArtefact>();
		String[] flows = metadataService.getFlows();
		for (String flow : flows) {
			GenericData c = new GenericData();
			c.setFlowId(flow);
			c.setProviderId(metadataService.getProviderId());
			c.setTitle(flow);
			list.add(c);
		}
		return list;
	}

	public void setExternalService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

}
