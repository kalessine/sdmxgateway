package org.opensdmx.adapter.olap4j.external.mock;

import java.util.ArrayList;
import java.util.List;

import org.opensdmx.adapter.olap4j.configuration.repository.CellSetMeasureStructure;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.util.OpenSdmxException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Dario Fabbri
 * 
 */
public abstract class MetadataServiceConfigBasedImpl extends BaseMetadataServiceImpl {

	@Autowired
	CellSetMeasureStructure cellSetMeasureStructure;

	@Override
	public MdxMetadata getCatalogPlusMdx4(String flow) {
		MdxMetadata c = null;
		if (FLOW_MDX_MAP.get(flow) != null) {
			c = new MdxMetadata();

			c.setMdx(FLOW_MDX_MAP.get(flow));
			c.setCatalog(FLOW_CATALOG_MAP.get(flow));
			c.setFreqAsDimension(FREQ_DIMENSION_MAP.get(flow));
			c.setMeasureAsIndicatorDimension(INDICATOR_DIMENSION_MAP.get(flow));
			if (DIMENSION_VALUE_MAP.get(flow) != null) {
				List<NameValue> fixedDimensionValueList = new ArrayList<NameValue>();
				c.setFixedDimensionValueList(fixedDimensionValueList);
				fixedDimensionValueList.add(DIMENSION_VALUE_MAP.get(flow));
			}
			if (SERIES_ATTRIBUTE_VALUE_MAP.get(flow) != null) {
				c.setFixedSeriesAttributeValueList(SERIES_ATTRIBUTE_VALUE_MAP.get(flow));
			}
			if (DATASET_ATTRIBUTE_VALUE_MAP.get(flow) != null) {
				c.setFixedDatasetAttributeValueList(DATASET_ATTRIBUTE_VALUE_MAP.get(flow));
			}

			if (c.getMdx() == null || c.getCatalog() == null) {
				throw new OpenSdmxException(
						"The external service was not able to find mdx and/or catalog for this flow: " + flow);
			}
			if (DIMENSION_PROPERTY_CONVENTION.size() > 0) {
				c.setDimensionPropertyMap(DIMENSION_PROPERTY_CONVENTION);
			}
			c.setCellSetMeasureStructure(cellSetMeasureStructure);
		}
		return c;
	}

}
