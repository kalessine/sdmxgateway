package org.opensdmx.adapter.olap4j.external.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.configuration.registry.DataStructure;
import org.opensdmx.util.OpenSdmxException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class should be replaced finally by a client which connects to the
 * TechCDR Catalog. This class now is a broker to 2 implementations:
 * 
 * 1) MetadataServiceFaostatImpl
 * 
 * 2) MetadataServiceFisheriesImpl
 * 
 * @author Erik van Ingen
 * 
 */
public class MetadataServiceImplBroker implements MetadataService {
	
	private static Logger logger = Logger.getLogger(MetadataServiceImplBroker.class);
	
	@Autowired
	private List<MetadataService> metadataServiceList;

	@Override
	public MdxMetadata getCatalogPlusMdx4(String flow) {
		MdxMetadata mdxMetadata = null;
		for (MetadataService metadataService : metadataServiceList) {
			MdxMetadata found = metadataService.getCatalogPlusMdx4(flow);
			if (mdxMetadata != null && found != null) {
				throw new OpenSdmxException("Finding a MdxMetadata for different flows");
			}
			if (mdxMetadata == null) {
				mdxMetadata = found;
			}
		}
		return mdxMetadata;
	}

	@Override
	public String[] getFlows() {
		String[] flows = {};
		for (MetadataService metadataService : metadataServiceList) {
			String[] found = metadataService.getFlows();
			flows = concatenateArrays(flows, found);
		}
		return flows;
	}

	@Override
	public String getConcept(String flow, String dimension) {
		String concept = null;
		for (MetadataService metadataService : metadataServiceList) {
			String found = metadataService.getConcept(flow, dimension);
			if (concept != null && found != null) {
				throw new OpenSdmxException("Different catalogs are using the same dimension");
			}
			if (concept == null) {
				concept = found;
			}
		}
		if (concept == null) {
			logger.debug("No SDMX concept found for given OLAP4J dimension: " + dimension);
		}
		return concept;
	}

	@Override
	public List<Code> getCodelist(String concept) {
		List<Code> codeList = null;
		for (MetadataService metadataService : metadataServiceList) {
			List<Code> found = metadataService.getCodelist(concept);
			if (codeList != null && found != null) {
				throw new OpenSdmxException("Finding the same codelist in more catalogs");
			}
			if (codeList == null) {
				codeList = found;
			}
		}
		return codeList;
	}

	@Override
	public List<CodeList> getCodeLists() {
		List<CodeList> codeList = new ArrayList<CodeList>();
		for (MetadataService metadataService : metadataServiceList) {
			List<CodeList> found = metadataService.getCodeLists();
			codeList.addAll(found);
		}
		return codeList;
	}

	@Override
	public List<DataStructure> getDataStructureList() {
		List<DataStructure> dataStructureList = new ArrayList<DataStructure>();
		for (MetadataService metadataService : metadataServiceList) {
			List<DataStructure> found = metadataService.getDataStructureList();
			dataStructureList.addAll(found);
		}
		return dataStructureList;
	}

	@Override
	public String getProviderId() {
		String providedId = null;
		for (MetadataService metadataService : metadataServiceList) {
			String found = metadataService.getProviderId();
			if (providedId != null && !providedId.equals(found)) {
				throw new OpenSdmxException("ProviderIds should be the same in all catalogs");
			}
			if (providedId == null) {
				providedId = found;
			}
		}
		return providedId;
	}

	private String[] concatenateArrays(String[] first, String[] second) {
		List<String> both = new ArrayList<String>(first.length + second.length);
		Collections.addAll(both, first);
		Collections.addAll(both, second);
		return both.toArray(new String[] {});
	}

	public void setMetadataServiceList(List<MetadataService> metadataServiceList) {
		this.metadataServiceList = metadataServiceList;
	}

}
