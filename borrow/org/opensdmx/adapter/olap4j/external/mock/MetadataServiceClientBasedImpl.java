package org.opensdmx.adapter.olap4j.external.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opensdmx.adapter.olap4j.configuration.repository.Block;
import org.opensdmx.adapter.olap4j.configuration.repository.BlockAttribute;
import org.opensdmx.adapter.olap4j.configuration.repository.CellSetMeasureStructure;
import org.opensdmx.adapter.olap4j.external.Code;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.MetadataService;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.adapter.olap4j.external.client.Client;
import org.opensdmx.adapter.olap4j.external.client.dto.BlockAttributeStructure;
import org.opensdmx.adapter.olap4j.external.client.dto.BlockStructure;
import org.opensdmx.adapter.olap4j.external.client.dto.DatasetAttributeValue;
import org.opensdmx.adapter.olap4j.external.client.dto.DimensionValue;
import org.opensdmx.adapter.olap4j.external.client.dto.Flow;
import org.opensdmx.adapter.olap4j.external.client.dto.Flows;
import org.opensdmx.adapter.olap4j.external.client.dto.MeasureStructure;
import org.opensdmx.adapter.olap4j.external.client.dto.SeriesAttributeValue;
import org.opensdmx.configuration.registry.CodeList;
import org.opensdmx.util.OpenSdmxException;
import org.springframework.beans.factory.annotation.Autowired;

public class MetadataServiceClientBasedImpl extends BaseMetadataServiceImpl {
	private static Logger logger = Logger.getLogger(MetadataServiceImplBroker.class);
	/**
	 * The client class that used for connect to the techCDR catalog.
	 */
	@Autowired
	Client client;

	/**
	 * The provider id value. It is a static value and config in src/main/resource/opensdmx-mondrian-adapter.
	 */
	@Autowired
	private String providerId;

	/**
	 * Transform Flow object to MdxMetadata.
	 * 
	 * @param flowObj
	 * @return
	 */
	protected MdxMetadata convertFlow2MdxMetadata(Flow flowObj) {
		logger.debug("start convertFlow2MdxMetadata");
		MdxMetadata c = null;
		c = new MdxMetadata();
		c.setMdx(flowObj.getMdx());
		c.setCatalog(flowObj.getCatalog());
		c.setFreqAsDimension(flowObj.isFreqAsDimension());
		c.setMeasureAsIndicatorDimension(flowObj.isMeasureAsIndicatorDimension());

		// DimensionValue list
		List<DimensionValue> dimAttributeValues = flowObj.getDimensionValues();
		if (dimAttributeValues != null) {
			List<NameValue> fixedDimensionValueList = new ArrayList<NameValue>();
			c.setFixedDimensionValueList(fixedDimensionValueList);
			for (int i = 0; i < dimAttributeValues.size(); i++) {
				DimensionValue dimensionValue = dimAttributeValues.get(i);
				NameValue nameValue = new NameValue(dimensionValue.getName(), dimensionValue.getValue());
				fixedDimensionValueList.add(nameValue);
			}
		}

		// SeriesAttributeValue list
		List<SeriesAttributeValue> seriesAttributeValues = flowObj.getSeriesAttributeValues();
		if (seriesAttributeValues != null) {
			List<NameValue> fixedSeriesAttributeValueList = new ArrayList<NameValue>();
			c.setFixedSeriesAttributeValueList(fixedSeriesAttributeValueList);
			for (int i = 0; i < seriesAttributeValues.size(); i++) {
				SeriesAttributeValue seriesAttributeValue = seriesAttributeValues.get(i);
				NameValue nameValue = new NameValue(seriesAttributeValue.getName(), seriesAttributeValue.getValue());
				fixedSeriesAttributeValueList.add(nameValue);
			}
		}

		// DatasetAttributeValues list
		List<DatasetAttributeValue> datasetAttributeValues = flowObj.getDatasetAttributeValues();
		if (datasetAttributeValues != null) {
			List<NameValue> fixedDatasetAttributeValueList = new ArrayList<NameValue>();
			c.setFixedDatasetAttributeValueList(fixedDatasetAttributeValueList);
			for (int i = 0; i < datasetAttributeValues.size(); i++) {
				DatasetAttributeValue datasetAttributeValue = datasetAttributeValues.get(i);
				NameValue nameValue = new NameValue(datasetAttributeValue.getName(), datasetAttributeValue.getValue());
				fixedDatasetAttributeValueList.add(nameValue);
			}
		}

		// DimensionPropertyMap list
		Map<String, String> dimensionPropertyMap = flowObj.getDimensionProperty();
		if (dimensionPropertyMap != null) {
			c.setDimensionPropertyMap(dimensionPropertyMap);
		}

		// CellSetMeasureStructure
		MeasureStructure measureStructure = flowObj.getCellSetMeasureStructure();
		if (measureStructure != null) {
			CellSetMeasureStructure dest = new CellSetMeasureStructure();
			Map<String, BlockStructure> blockStructureMap = measureStructure.getBlockStructureMap();
			Map<String, Block> destBlockMap = new HashMap<String, Block>();
			for (Iterator<String> keyIterator = blockStructureMap.keySet().iterator(); keyIterator.hasNext();) {
				String blockKey = keyIterator.next();
				BlockStructure blockStructure = blockStructureMap.get(blockKey);
				Block destBlock = new Block();
				destBlock.setMeasureName(blockStructure.getMeasureName());
				List<BlockAttribute> destBlockAttributes = new ArrayList<BlockAttribute>();
				destBlock.setAttributes(destBlockAttributes);
				List<BlockAttributeStructure> blockAttributeStructures = blockStructure.getAttributes();
				if (blockAttributeStructures != null) {
					for (BlockAttributeStructure blockAttributeStructure : blockAttributeStructures) {
						BlockAttribute blockAttribute = new BlockAttribute();
						blockAttribute.setConcept(blockAttributeStructure.getConcept());
						blockAttribute.setMeasureName(blockAttributeStructure.getMeasureName());
						destBlockAttributes.add(blockAttribute);
					}
				}
				destBlockMap.put(blockKey, destBlock);
			}
			dest.setBlockMap(destBlockMap);
			c.setCellSetMeasureStructure(dest);
		}

		return c;
	}

	@Override
	public MdxMetadata getCatalogPlusMdx4(String flow) {
		logger.debug("start getCatalogPlusMdx4");
		MdxMetadata c = null;
		try {
			// Get all the available flows
			Flows flows2 = client.retrieveFlows();
			List<String> uuids = flows2.getFlows();
			for (String uuid : uuids) {
				// Use the return uuid for request the flow information
				Flow flowObj = client.retrieveFlow(uuid);
				String flowName = flowObj.getName();

				if (flow.equals(flowName)) {
					// if the flow does not have the mdx query and catalog, it will throw exception.
					if (flowObj.getMdx() == null || flowObj.getCatalog() == null) {
						throw new OpenSdmxException(
								"The external service was not able to find mdx and/or catalog for this flow: " + flow);
					}
					c = convertFlow2MdxMetadata(flowObj);

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenSdmxException(e);
		}

		return c;
	}

	@Override
	public String getConcept(String flow, String dimension) {
		logger.debug("start getConcept");
		String concept = null;
		Flows flows2 = client.retrieveFlows();
		List<String> uuids = flows2.getFlows();
		for (String uuid : uuids) {

			Flow flowObj = client.retrieveFlow(uuid);
			String flowName = flowObj.getName();

			if (flow.equals(flowName)) {
				Map<String, String> map = flowObj.getConceptDimensionMap();
				concept = map.get(dimension);

				break;
			}
		}
		if (concept == null) {
			logger.debug("No SDMX concept found for given OLAP4J dimension: " + dimension);
		}
		return concept;
	}

	@Override
	public String getProviderId() {
		logger.debug("start getProviderId");
		return providerId;
	}

	@Override
	public String[] getFlows() {
		logger.debug("start getFlows");
		String[] flows = null;
		try {

			// Get the list of the uuids of the flows.
			Flows flows2 = client.retrieveFlows();
			List<String> uuids = flows2.getFlows();

			List<String> flowNames = new ArrayList<String>();

			for (String uuid : uuids) {
				// for each uuid we make the call to retrieve all the data of the
				Flow flow = client.retrieveFlow(uuid);
				String flowName = flow.getName();

				flowNames.add(flowName);

			}

			flows = flowNames.toArray(new String[flowNames.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flows;
	}

	public List<Code> getCodelist(String concept) {
		MetadataService s = new MetadataServiceFaostatImpl();
		return s.getCodelist(concept);
	}

	public List<CodeList> getCodeLists() {
		MetadataService s = new MetadataServiceFaostatImpl();
		return s.getCodeLists();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
