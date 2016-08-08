package org.opensdmx.adapter.olap4j.repository.generatormdx;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.opensdmx.adapter.olap4j.conventions.DataSetAttributeConvention;
import org.opensdmx.adapter.olap4j.conventions.FixedDimensionValueConvention;
import org.opensdmx.adapter.olap4j.conventions.FreqDimensionConvention;
import org.opensdmx.adapter.olap4j.dao.Olap4jInteraction;
import org.opensdmx.adapter.olap4j.dao.OlapConnector;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.configuration.repository.GenericData;
import org.opensdmx.configuration.repository.generator.SeriesGeneratorDTO;
import org.opensdmx.configuration.repository.generator.SeriesGeneratorFull;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValuesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeriesGeneratorMdxImpl implements SeriesGeneratorFull {

	private Logger logger = Logger.getLogger(SeriesGeneratorMdxImpl.class);

	@Autowired
	private OlapConnector olapConnector;

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private CellSet2GenericData cellSet2GenericData;

	private CellSetAnalyzer cellSetAnalyzer = new CellSetAnalyzer();
	private ConceptsAnalyzer conceptsAnalyzer = new ConceptsAnalyzer();

	private FreqDimensionConvention freqDimensionConvention = new FreqDimensionConvention();
	private FixedDimensionValueConvention fixedDimensionValueConvention = new FixedDimensionValueConvention();
	private DataSetAttributeConvention dataSetAttributeConvention = new DataSetAttributeConvention();

	@Override
	public SeriesGeneratorDTO generate(GenericData genericDataConfigElement) {
		logger.debug("Hitting new SeriesGeneratorMdxImpl.generate !!!");

		String flow = genericDataConfigElement.getFlowId();

		MdxMetadata m = metadataService.getCatalogPlusMdx4(flow);
		OlapConnection connection = olapConnector.connectTo(m.getCatalog());
		Olap4jInteraction i = new Olap4jInteraction(connection);
		CellSet cellSet = i.generateCellSet(m);

		// validate conventions
		cellSetAnalyzer.validateConventions(cellSet);

		// analyse
		Datastructure datastructure = cellSetAnalyzer.analyze(cellSet);

		// Enrich datastructure with concept / dimensions map.
		//
		conceptsAnalyzer.analyzeConcepts(metadataService, datastructure);
		
		// generate the SDMX dataset from the cellset
		List<SeriesType> seriesList = new ArrayList<SeriesType>();
		cellSet2GenericData.convert(cellSet, seriesList, datastructure, m, flow);

		// apply the freq as dimension convention, if appropriate
		if (m.isFreqAsDimension()) {
			freqDimensionConvention.apply(m, datastructure, seriesList);
		}

		// apply the fixed dimensions convention (done for example for the
		// faostat domain)
		if (m.getFixedDimensionValueList() != null && m.getFixedDimensionValueList().size() > 0) {
			fixedDimensionValueConvention.apply(m, datastructure, seriesList);
		}

		// apply the dataset attribute convention
		ValuesType attributes = null;
		if (m.getFixedDatasetAttributeValueList() != null && m.getFixedDatasetAttributeValueList().size() > 0) {
			attributes = dataSetAttributeConvention.apply(m);
		}

		// Prepare result and return it.
		//
		SeriesGeneratorDTO dto = new SeriesGeneratorDTO();
		dto.setAttributes(attributes);
		dto.setSeries(seriesList);
		return dto;
	}

	public void setExternalService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	public void setCellSet2GenericData(CellSet2GenericData cellSet2GenericData) {
		this.cellSet2GenericData = cellSet2GenericData;
	}

	public void setOlapConnector(OlapConnector olapConnector) {
		this.olapConnector = olapConnector;
	}

}
