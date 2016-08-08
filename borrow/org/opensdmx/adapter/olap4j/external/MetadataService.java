package org.opensdmx.adapter.olap4j.external;

import java.util.List;

import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.configuration.registry.DataStructure;

/**
 * 
 * 
 * The adapter needs an external service in order to know what mdx query
 * corresponds with what flow.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public interface MetadataService {

	/**
	 * 
	 * Return the MDX1 query, given a flow.
	 * 
	 * @param flow
	 * @return mdx corresponding this flow
	 */
	public MdxMetadata getCatalogPlusMdx4(String flow);

	/**
	 * 
	 * Return the list of flows, available in this adapter.
	 * 
	 * Assuming that flow is equivalent with flowId.
	 * 
	 * @return flows
	 */
	public String[] getFlows();

	/**
	 * Return the SDMX concept, given a dimension. This is needed in case the
	 * DSD is not known and it needs to be filled in in the SDMX dataset.
	 * 
	 * The DSD generator may eventually also use this operation.
	 * 
	 * 
	 * @param dimension
	 * @return
	 */
	public String getConcept(String flow, String dimension);

	/**
	 * Return the code list which is used for a given SDMX concept.
	 * 
	 * Instead of returning a List
	 * <Code> object, the native SDMX Codelists could have been used.
	 * There has been chosen for List<Code> in order not to confront the implementer too much with SDMX.
	 * 
	 * 
	 * @param concept
	 * @return
	 */
	public List<Code> getCodelist(String concept);

	/**
	 * Get list of codelists available
	 * 
	 * @returnlist of codelists
	 */
	public List<CodeList> getCodeLists();

	/**
	 * Yet not used because the FAOSTAT DSD is passed through the life link
	 * adapter.
	 * 
	 * @return
	 */
	public List<DataStructure> getDataStructureList();

	/**
	 * 
	 * The id of the provider of the data (SDMX dataset).
	 * 
	 * The agency id in the flowRef is the id of the agency maintaining the
	 * dataflow.
	 * 
	 * The agency id of the providerRef is the id of the agency maintaining the
	 * data provider scheme where the data provider is maintained.
	 * 
	 * These don't necessarily have to be the same. For instance, in an ECB web
	 * service, the maintenance agency for dataflows could be ECB, while the
	 * maintenance agency for the data provider scheme in which the ECB (as data
	 * provider) resides could be SDMX. However, in practice, omitting these
	 * will work in many cases and therefore a simplified version is also
	 * allowed: protocol://ws-entry-point/resource/flowId/key/providerId
	 * 
	 * @return
	 */
	public String getProviderId();

	/**
	 * @TODO: finish when eliminating the conventions.
	 *  
	 * @param flow
	 * @return
	 */
	/*
	public KeyFamilyType getPreexistingDataStructure(String flow);
	*/
}
