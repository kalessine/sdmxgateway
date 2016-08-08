package org.opensdmx.adapter.olap4j.registry.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.Description;
import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.generator.registry.AbstractCodeListGenerator;
import org.sdmx.resources.sdmxml.schemas.v2_0.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_0.structure.CodeType;
import org.springframework.beans.factory.annotation.Autowired;

public class CodeListGenerator extends AbstractCodeListGenerator {

	private Logger logger = Logger.getLogger(CodeListGenerator.class);

	@Autowired
	private MetadataService metadataService;

	@Override
	protected List<CodeType> createList(CodeList codeListConfig) {

		logger.debug("Hitting codelist generator (org.opensdmx.adapter.olap4j.registry.generator.CodeListGenerator)");

		List<CodeType> codes = new ArrayList<CodeType>();
		List<Code> externalCodes = metadataService.getCodelist(codeListConfig.getConcept().getConceptId());
		for (Code code : externalCodes) {
			CodeType sdmxCode = new CodeType();
			sdmxCode.setValue(code.getCode());
			if (code.getDescriptions() != null) {
				// process the descriptions if there are any
				List<Description> descriptions = code.getDescriptions();
				for (Description description : descriptions) {
					TextType d = new TextType();
					d.setValue(description.getDesciption());
					if (description.getLanguage() != null) {
						d.setLang(description.getLanguage().toString());
					}
					sdmxCode.getDescriptions().add(d);
				}
			}
			codes.add(sdmxCode);
		}
		return codes;
	}

	public void setMetadataService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

}
