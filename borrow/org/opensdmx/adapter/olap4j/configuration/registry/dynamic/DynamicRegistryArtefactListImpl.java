package org.opensdmx.adapter.olap4j.configuration.registry.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.configuration.registry.RegistryArtefact;
import org.opensdmx.configuration.registry.dynamic.DynamicRegistryArtefactList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DynamicRegistryArtefactListImpl implements DynamicRegistryArtefactList {

	@Autowired
	private MetadataService metadataService;

	public void setMetadataService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	@Override
	public List<RegistryArtefact> getRegistryArtefactList() {
		List<RegistryArtefact> list = new ArrayList<RegistryArtefact>();
		list.addAll(metadataService.getCodeLists());
		return list;
	}
}
