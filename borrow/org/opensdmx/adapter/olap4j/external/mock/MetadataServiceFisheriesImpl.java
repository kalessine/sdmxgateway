package org.opensdmx.adapter.olap4j.external.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.client.sdmx20.rest.Sdmx20RestRegistryClient;
import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.configuration.registry.Concept;
import org.opensdmx.rest.domain.RestRegistryParameters;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeListType;

/**
 * http://km.fao.org/FIGISwiki/index.php/Fishstat_SDMX_DataStructure
 * 
 * dataset = AQUACULTURE_QUANTITY datastructure = aquaculture
 * 
 * dimensions: aquaculture-area country species environment
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class MetadataServiceFisheriesImpl extends MetadataServiceConfigBasedImpl {
	private static Logger logger = Logger.getLogger(MetadataServiceFisheriesImpl.class);
	public static final String FLOW3 = "AQUACULTURE_QUANTITY";
	public static final String FLOW4 = "AQUACULTURE_QUANTITY_TEST";

	// FISH SDMX Dimensions
	public static final String FISH_CONCEPT1 = "FAO_MAJOR_AREA";
	public static final String FISH_CONCEPT2 = "UN_COUNTRY";
	public static final String FISH_CONCEPT3 = "SPECIES";
	public static final String FISH_CONCEPT4 = "ENVIRONMENT";

	// FISH Measure Dimension
	public static final String FISH_CONCEPT5 = "OBS_VALUE";

	// FISH attributes
	public static final String FISH_CONCEPT6 = "UNIT";
	public static final String FISH_CONCEPT7 = "UNIT_MULTIPLIER";

	// FISH Mondrian dimensions
	public static final String FISH_MONDRIAN1 = "Fishing Area.Fishing Area";
	public static final String FISH_MONDRIAN2 = "Country.Country";
	public static final String FISH_MONDRIAN3 = "Species.Species";
	public static final String FISH_MONDRIAN4 = "Environment.Environment";

	// FISH Mondrian Measure Dimension
	public static final String FISH_MONDRIAN5 = "OBS_VALUE";

	// FISH Mondrian attributes
	public static final String FISH_MONDRIAN6 = "UNIT";
	public static final String FISH_MONDRIAN7 = "UNIT_MULTIPLIER";

	public static final String catalog3 = "Fisheries";

	public static String[] CODELIST_CONCEPTS = { FISH_CONCEPT1, FISH_CONCEPT2, FISH_CONCEPT3, FISH_CONCEPT5,
			FISH_CONCEPT6, FISH_CONCEPT7 };

	/**
	 * master MDX (is behind TODO update, use the MDX2 one)
	 */
	public static String MDX1 = "select NON EMPTY {([Year].[All Years].children)} ON COLUMNS,   " +
			"NON EMPTY {Crossjoin(CrossJoin(CrossJoin(CrossJoin({[Country].[All Countries].children}, " +
			"	[Species].[All Species].children), " +
			"	[Environment].[All Environments].Children), " +
			"	[Fishing Area].[All Fishing Areas].Children), " +
			"	[Measures].[mweight])} " +
			"DIMENSION PROPERTIES MEMBER_KEY ON ROWS from [Aquaculture]";

	/**
	 * just for testing
	 * 
	 * Alpha3 Code
	 */
	public static String MDX2 = "select NON EMPTY {([Year].[2008]:[Year].[2010])} ON COLUMNS,   " +
			"NON EMPTY {Crossjoin(CrossJoin(CrossJoin(CrossJoin({[Country].[Country].[Albania]:[Country].[Armenia]}, " +
			"	[Species].[Common carp]), " +
			"	[Environment].[All Environments].Children), " +
			"	[Fishing Area].[All Fishing Areas].Children), " +
			"	[Measures].[mweight])} " +
			"DIMENSION PROPERTIES MEMBER_KEY ON ROWS from [Aquaculture]";

	public MetadataServiceFisheriesImpl() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(FISH_MONDRIAN1, FISH_CONCEPT1);
		map.put(FISH_MONDRIAN2, FISH_CONCEPT2);
		map.put(FISH_MONDRIAN3, FISH_CONCEPT3);
		map.put(FISH_MONDRIAN4, FISH_CONCEPT4);
		CONCEPT_MAP.put(FLOW3, map);
		CONCEPT_MAP.put(FLOW4, map);

		FLOW_MDX_MAP.put(FLOW3, MDX1);
		FLOW_MDX_MAP.put(FLOW4, MDX2);

		FLOW_CATALOG_MAP.put(FLOW3, catalog3);
		FLOW_CATALOG_MAP.put(FLOW4, catalog3);

		FREQ_DIMENSION_MAP.put(FLOW3, false);
		FREQ_DIMENSION_MAP.put(FLOW4, false);

		INDICATOR_DIMENSION_MAP.put(FLOW3, false);
		INDICATOR_DIMENSION_MAP.put(FLOW4, false);

		List<NameValue> fixedDatasetAttributeValueList = new ArrayList<NameValue>();
		NameValue nv1 = new NameValue("UNIT_MULTIPLIER", "1");
		NameValue nv2 = new NameValue("UNIT", "t");
		fixedDatasetAttributeValueList.add(nv1);
		fixedDatasetAttributeValueList.add(nv2);

		DATASET_ATTRIBUTE_VALUE_MAP.put(FLOW3, fixedDatasetAttributeValueList);
		DATASET_ATTRIBUTE_VALUE_MAP.put(FLOW4, fixedDatasetAttributeValueList);

		DIMENSION_PROPERTY_CONVENTION.put(FISH_MONDRIAN3, "alpha3code");
		DIMENSION_PROPERTY_CONVENTION.put(FISH_MONDRIAN4, "envcode");// environment
		DIMENSION_PROPERTY_CONVENTION.put(FISH_MONDRIAN1, "fic_areacode"); //Fishing Area.Fishing Area
		DIMENSION_PROPERTY_CONVENTION.put(FISH_MONDRIAN2, "uncode"); //Country.Country

		// <generic:Value concept="FAO_MAJOR_AREA" value="04"/> Dimension: Fishing Area, Property: FI Catch Area Code
		// <generic:Value concept="UN_COUNTRY" value="156"/> Dimension: Country, Property: UN Code
		// <generic:Value concept="SPECIES" value="LEF"/> Dimension: Species, Property: Alpha3 Code
		// <generic:Value concept="ENVIRONMENT" value="MARINE"/> Dimension: Environment, Property: Full Code
		// </generic:SeriesKey>

	}

	/**
	 *
	 */
	@Override
	public List<Code> getCodelist(String concept) {
		logger.debug("Hitting getCodelist for " + concept);
		boolean found = false;
		for (String c : CODELIST_CONCEPTS) {
			if (c.equals(concept)) {
				found = true;
			}
		}
		List<Code> codelist;
		if (found) {
			// use the convention that the codelist starts with CL_ plus the concept
			// name
			String codelistId = "CL_" + concept;

			String wsRegistryEntryPoint = "http://www.fao.org/figis/sdmx/registry";
			Sdmx20RestRegistryClient client = new Sdmx20RestRegistryClient(wsRegistryEntryPoint);

			RestRegistryParameters p = new RestRegistryParameters();
			p.setAgencyID(getProviderId());
			p.setResourceID(codelistId);
			p.setVersion("0.1");
			CodeListType sdmxCodeList = client.retrieveCodelist(p);
			codelist = convert2SdmxAgnostic(sdmxCodeList);
		} else {
			codelist = null;
		}
		return codelist;
	}

	@Override
	public List<CodeList> getCodeLists() {
		List<CodeList> list = new ArrayList<CodeList>();
		for (String concept : CODELIST_CONCEPTS) {
			CodeList cl = new CodeList();
			cl.setResourceID("CL_" + concept);
			cl.setTitle(cl.getResourceID());
			cl.setVersion("0.1");
			cl.setAgency("FAO");
			Concept c = new Concept();
			c.setConceptId(concept);
			cl.setConcept(c);
			list.add(cl);
		}
		return list;
	}

	@Override
	public String[] getFlows() {
		String[] flows = { FLOW3, FLOW4 };
		return flows;
	}

}
