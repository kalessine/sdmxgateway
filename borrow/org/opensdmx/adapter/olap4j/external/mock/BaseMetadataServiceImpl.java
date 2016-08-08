package org.opensdmx.adapter.olap4j.external.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.Description;
import org.opensdmx.adapter.olap4j.external.Description.Language;
import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.configuration.registry.DataStructure;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeListType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeType;

/**
 * This one is yet only configured in the
 * /opensdmx-mondrian-web/src/main/resources/olap-connection.xml because all the
 * unittests are using it in a hardcoded way.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public abstract class BaseMetadataServiceImpl implements MetadataService {

	protected Map<String, Map<String, String>> CONCEPT_MAP = new HashMap<String, Map<String, String>>();
	protected Map<String, String> FLOW_MDX_MAP = new HashMap<String, String>();
	protected Map<String, String> FLOW_CATALOG_MAP = new HashMap<String, String>();
	protected Map<String, Boolean> FREQ_DIMENSION_MAP = new HashMap<String, Boolean>();
	protected Map<String, Boolean> INDICATOR_DIMENSION_MAP = new HashMap<String, Boolean>();
	protected Map<String, NameValue> DIMENSION_VALUE_MAP = new HashMap<String, NameValue>();
	protected Map<String, List<NameValue>> SERIES_ATTRIBUTE_VALUE_MAP = new HashMap<String, List<NameValue>>();
	protected Map<String, List<NameValue>> DATASET_ATTRIBUTE_VALUE_MAP = new HashMap<String, List<NameValue>>();
	protected Map<String, String> DIMENSION_PROPERTY_CONVENTION = new HashMap<String, String>();
	protected String[] CODELIST_CONCEPTS = null;


	@Override
	public String getConcept(String flow, String dimension) {
		
		Map<String, String> flowConceptMap = CONCEPT_MAP.get(flow);
		if(flowConceptMap == null)
			return null;
		
		String concept = flowConceptMap.get(dimension);
		return concept;
	}


	@Override
	public List<DataStructure> getDataStructureList() {
		List<DataStructure> list = new ArrayList<DataStructure>();
		return list;
	}

	@Override
	public String getProviderId() {
		return "FAO";
	}

	protected List<Code> convert2SdmxAgnostic(CodeListType sdmxCodeList) {
		List<Code> codelist = new ArrayList<Code>();
		if (sdmxCodeList != null) {
			List<CodeType> codes = sdmxCodeList.getCodes();
			// convert the sdmx codes into the Metadata service codelist format
			for (CodeType sdmxCode : codes) {
				Code code = new Code();
				code.setCode(sdmxCode.getValue());
				if (sdmxCode.getDescriptions() != null) {
					List<TextType> descriptionsNative = sdmxCode.getDescriptions();
					List<Description> descriptions = new ArrayList<Description>();
					for (TextType description : descriptionsNative) {
						if (description.getValue().length() > 0) {
							// only when there is a description, a value will be
							// added.
							Description d = new Description();
							d.setDesciption(description.getValue());
							d.setLanguage(Language.parse(description.getLang()));
							descriptions.add(d);
						}
					}
					code.setDescriptions(descriptions);
				}
				codelist.add(code);
			}
		}
		if (codelist.size() == 0) {
			codelist = null;
		}
		return codelist;

	}

}
