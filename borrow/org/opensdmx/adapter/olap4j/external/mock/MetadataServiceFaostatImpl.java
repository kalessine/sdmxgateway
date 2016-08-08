package org.opensdmx.adapter.olap4j.external.mock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opensdmx.adapter.olap4j.conventions.FreqDimensionConvention;
import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.client.sdmx20.custom.Sdmx20CustomRegistryClient;
import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.configuration.registry.Concept;
import org.opensdmx.util.OpenSdmxException;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeListType;

/**
 * This one is yet only configured in the /opensdmx-mondrian-web/src/main/resources/olap-connection.xml because all the
 * unittests are using it in a hardcoded way.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class MetadataServiceFaostatImpl extends MetadataServiceConfigBasedImpl {
	private static Logger logger = Logger.getLogger(MetadataServiceFaostatImpl.class);

	// public static final String FLOW5 = "FAOSTAT";
	public static final String FLOW5 = "CROP_PRODUCTION";

	// FAOSTAT
	public static final String FAOSTAT_CONCEPT1 = FreqDimensionConvention.FREQ;
	public static final String FAOSTAT_CONCEPT2 = "REF_AREA";
	public static final String FAOSTAT_CONCEPT3 = "INDICATOR";
	public static final String FAOSTAT_CONCEPT4 = "COMMODITY";
	public static final String FAOSTAT_CONCEPT5 = "DOMAIN";
	public static final String FAOSTAT_CONCEPT6 = "UNITS";
	public static final String FAOSTAT_CONCEPT7 = "UNIT_MULTIPLIER";
	public static final String FAOSTAT_CONCEPT8 = "LAST-UPDATED";
	public static final String FAOSTAT_CONCEPT9 = "OBS_STATUS";

	// FAOSTAT
	public static final String FAOSTAT_MONDRIAN1 = "Frequency";
	public static final String FAOSTAT_MONDRIAN2 = "Area.Area";
	public static final String FAOSTAT_MONDRIAN3 = "Indicator";
	public static final String FAOSTAT_MONDRIAN4 = "Item.Item";
	public static final String FAOSTAT_MONDRIAN5 = "Year";

	// FAOSTAT attribute values
	public static final String UNITS = "No";
	public static final String UNIT_MULTIPLIER = "1000";
	public static final String LAST_UPDATED = "21/09/2009";

	public static final String catalog2 = "Faostat Production - en";

	/**
	 * This is the official MDX1 for FAOSTAT.
	 * 
	 * With attributes.
	 * 
	 * Old one: select NON EMPTY [Date.Year].Children ON COLUMNS, NON EMPTY Crossjoin({[Area].Children},
	 * Crossjoin({[Item].Children}, {[Measures].[m511], [Measures].[f511]})) ON ROWS from [Population]
	 * 
	 * New one: select NON EMPTY [Date.Year].Children ON COLUMNS, NON EMPTY Crossjoin({[Area].Children},
	 * Crossjoin({[Item].Members}, {[Measures].[m511], [Measures].[f511]})) dimension properties MEMBER_KEY ON ROWS from
	 * [Population]
	 * 
	 * the unique value (code) which is different from the physical key can be addressed through the intrinsic
	 * properties. This I meant by exploiting the api.
	 * 
	 * anyway the codes you are getting from this queries are wrong (because I actually addressed the physical key
	 * instead of the business key… my bad) but it’s just a matter of redeploying the cube.
	 * 
	 */
	// public static final String FAOSTAT_MDX2 =
	// "select NON EMPTY [Date.Year].Children ON COLUMNS, NON EMPTY Crossjoin({[Area].Children}, Crossjoin({[Item].Members}, {[Measures].[m511], [Measures].[f511]})) dimension  properties MEMBER_KEY  ON ROWS from [Population]";

	// public static final String FAOSTAT_MDX2 =
	// "select NON EMPTY {[Year].[All Years].Children} ON COLUMNS, NON EMPTY Crossjoin(Crossjoin({[Area].[All Areas].Children}, [Item].[All Items].Children), {[Measures].[m5510], [Measures].[f5510]}) dimension properties MEMBER_KEY ON ROWS from [CROPS]";
	public static String FAOSTAT_MDX2 = "select NON EMPTY {[Year].[2008]:[Year].[2009]} ON COLUMNS, "
			+ "NON EMPTY Crossjoin(Crossjoin({[Area].[All Areas].Children}, " + "[Item].[All Items].Children), "
			+ "{[Measures].[m5312], " + "[Measures].[f5312], " + "[Measures].[m5419], " + "[Measures].[f5419], "
			+ "[Measures].[m5510], " + "[Measures].[f5510], " + "[Measures].[m5525], " + "[Measures].[f5525]}) "
			+ "DIMENSION PROPERTIES MEMBER_KEY " + "ON ROWS from [CROPS]";

	public static final String MDX_ERROR = "select NON EMPTY {[Time].[Month].Members} ON COLUMNS, NON EMPTY [Store Type].[Store Type].Members ON ROWS from [HR]";

	public MetadataServiceFaostatImpl() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(FAOSTAT_MONDRIAN1, FAOSTAT_CONCEPT1);
		map.put(FAOSTAT_MONDRIAN2, FAOSTAT_CONCEPT2);
		map.put(FAOSTAT_MONDRIAN3, FAOSTAT_CONCEPT3);
		map.put(FAOSTAT_MONDRIAN4, FAOSTAT_CONCEPT4);
		map.put(FAOSTAT_MONDRIAN5, FAOSTAT_CONCEPT5);
		CONCEPT_MAP.put(FLOW5, map);

		FLOW_MDX_MAP.put(FLOW5, FAOSTAT_MDX2);

		FLOW_CATALOG_MAP.put(FLOW5, catalog2);

		FREQ_DIMENSION_MAP.put(FLOW5, true);

		INDICATOR_DIMENSION_MAP.put(FLOW5, true);

		NameValue dimensionNameValue = new NameValue(FAOSTAT_CONCEPT5, "Q");
		DIMENSION_VALUE_MAP.put(FLOW5, dimensionNameValue);

		NameValue attributeNameValueUnits = new NameValue(FAOSTAT_CONCEPT6, UNITS);
		NameValue attributeNameValueUnitMultiplier = new NameValue(FAOSTAT_CONCEPT7, UNIT_MULTIPLIER);
		List<NameValue> fixedSeriesAttributeValueList = new ArrayList<NameValue>();
		fixedSeriesAttributeValueList.add(attributeNameValueUnits);
		fixedSeriesAttributeValueList.add(attributeNameValueUnitMultiplier);

		SERIES_ATTRIBUTE_VALUE_MAP.put(FLOW5, fixedSeriesAttributeValueList);

		NameValue attributeNameValueLastUpdated = new NameValue(FAOSTAT_CONCEPT8, LAST_UPDATED);
		List<NameValue> fixedDatasetAttributeValueList = new ArrayList<NameValue>();
		fixedDatasetAttributeValueList.add(attributeNameValueLastUpdated);

		DATASET_ATTRIBUTE_VALUE_MAP.put(FLOW5, fixedDatasetAttributeValueList);

		String[] CODELIST_CONCEPTS_TEMP = { FAOSTAT_CONCEPT1, FAOSTAT_CONCEPT2, FAOSTAT_CONCEPT3, FAOSTAT_CONCEPT4,
				FAOSTAT_CONCEPT5, FAOSTAT_CONCEPT6, FAOSTAT_CONCEPT9 };
		CODELIST_CONCEPTS = CODELIST_CONCEPTS_TEMP;

	}

	@Override
	public List<Code> getCodelist(String concept) {
		logger.debug("Hitting getCodelist for " + concept);

		// use the convention that the codelist starts with CL_ plus the concept
		// name
		String codelistId = "CL_" + concept;

		// this should go to the faostat one
		if (concept.equals(FAOSTAT_CONCEPT6)) {
			// process the exception to the convention
			codelistId = "CL_UNIT_MEASURE";
		}

		Sdmx20CustomRegistryClient client = new Sdmx20CustomRegistryClient();
		URL url;
		CodeListType sdmxCodeList;
		try {
			url = new URL("http://faostat.fao.org/DesktopModules/Faostat/sdmx/codelistSDMX.php?tableID=Warehouse");
			sdmxCodeList = client.retrieveCodelist(url, codelistId);
		} catch (MalformedURLException e) {
			throw new OpenSdmxException(e);
		}
		return convert2SdmxAgnostic(sdmxCodeList);
	}

	@Override
	public List<CodeList> getCodeLists() {
		List<CodeList> list = new ArrayList<CodeList>();
		for (String concept : CODELIST_CONCEPTS) {
			CodeList cl = new CodeList();
			cl.setResourceID("CL_" + concept);
			cl.setTitle(cl.getResourceID());
			if (concept.equals(FAOSTAT_CONCEPT6)) {
				// process the exception to the convention
				cl.setResourceID("CL_UNIT_MEASURE");
			}
			cl.setVersion("0.1");
			cl.setAgency("FAOSTAT");
			Concept c = new Concept();
			c.setConceptId(concept);
			cl.setConcept(c);
			list.add(cl);
		}
		return list;
	}

	@Override
	public String[] getFlows() {
		String[] flows = { FLOW5 };
		return flows;
	}

}
