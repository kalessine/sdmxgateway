package org.opensdmx.adapter.olap4j.external;

import java.util.List;
import java.util.Map;

import org.opensdmx.adapter.olap4j.configuration.repository.CellSetMeasureStructure;

/**
 * 
 * This class is a holder of metadata information which can be given in addition
 * to the MDX1 for the adapter to understand how to compose the SDMX dataset
 * from the CellSet. The CellSet is generated based on the MDX1.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class MdxMetadata {

	/**
	 * Preexisting DSD means that the MDX1 is generating data which does respect
	 * a predefined or preexisting DSD.
	 * 
	 * In case of a preexisting DSD, the DSD will be retrieved from the
	 * MetadataService through the operation getPreexistingDataStructure.
	 * 
	 * In case of no preexisting DSD, the DSD will be generated in this adapter,
	 * based on the interpretation of the MDX1 query result.
	 * 
	 */
	private boolean preexistingDataStructure;

	/**
	 * The name of the catalogue where the MDX1 can be executed upon.
	 * 
	 * 
	 */
	private String catalog;

	/**
	 * The MDX1 query in order to retrieve the data
	 * 
	 */
	private String mdx;

	/**
	 * FREQ as Dimension. If FREQ is a dimension, it is always the first
	 * dimension and it will be derived from the Time "ON COLUMNS"
	 */
	private boolean freqAsDimension;

	/**
	 * In cases when the indicator is not given as a dimension "ON ROWS", it
	 * will be added as a dimension based on the concept of the measure.
	 * 
	 */
	private boolean measureAsIndicatorDimension;

	/**
	 * The list of dimensions with a fixed value which will be added to the SDMX
	 * dataset.
	 * 
	 * This is for instance used for the FAOSTAT DSD where Domain has always the
	 * same value in one dataset.
	 * 
	 */
	private List<NameValue> fixedDimensionValueList;

	/**
	 * The list of attributes with a fixed value which will be added to the SDMX
	 * dataset on Series level.
	 * 
	 * This is for instance used for the FAOSTAT DSD where the UNITS and
	 * UNIT_MULTIPLIER have always the same value.
	 * 
	 */
	private List<NameValue> fixedSeriesAttributeValueList;

	/**
	 * The list of attributes with a fixed value which will be added to the SDMX
	 * dataset on dataset level.
	 * 
	 */
	private List<NameValue> fixedDatasetAttributeValueList;

	/**
	 * This is of use for the DimensionPropertyConvention, see its javadocs
	 */
	private Map<String, String> dimensionPropertyMap;

	/**
	 * Defines the measure structure returning from MDX1 query.
	 * 
	 */
	private CellSetMeasureStructure cellSetMeasureStructure;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	public boolean isFreqAsDimension() {
		return freqAsDimension;
	}

	public void setFreqAsDimension(boolean freqAsDimension) {
		this.freqAsDimension = freqAsDimension;
	}

	public boolean isMeasureAsIndicatorDimension() {
		return measureAsIndicatorDimension;
	}

	public void setMeasureAsIndicatorDimension(boolean measureAsIndicatorDimension) {
		this.measureAsIndicatorDimension = measureAsIndicatorDimension;
	}

	public List<NameValue> getFixedDimensionValueList() {
		return fixedDimensionValueList;
	}

	public void setFixedDimensionValueList(List<NameValue> fixedDimensionValueList) {
		this.fixedDimensionValueList = fixedDimensionValueList;
	}

	public List<NameValue> getFixedSeriesAttributeValueList() {
		return fixedSeriesAttributeValueList;
	}

	public void setFixedSeriesAttributeValueList(List<NameValue> fixedAttributeValueList) {
		this.fixedSeriesAttributeValueList = fixedAttributeValueList;
	}

	public List<NameValue> getFixedDatasetAttributeValueList() {
		return fixedDatasetAttributeValueList;
	}

	public void setFixedDatasetAttributeValueList(List<NameValue> fixedDatasetAttributeValueList) {
		this.fixedDatasetAttributeValueList = fixedDatasetAttributeValueList;
	}

	public boolean isPreexistingDataStructure() {
		return preexistingDataStructure;
	}

	public void setPreexistingDataStructure(boolean preexistingDataStructure) {
		this.preexistingDataStructure = preexistingDataStructure;
	}

	public CellSetMeasureStructure getCellSetMeasureStructure() {
		return cellSetMeasureStructure;
	}

	public void setCellSetMeasureStructure(CellSetMeasureStructure cellSetMeasureStructure) {
		this.cellSetMeasureStructure = cellSetMeasureStructure;
	}

	public Map<String, String> getDimensionPropertyMap() {
		return dimensionPropertyMap;
	}

	public void setDimensionPropertyMap(Map<String, String> dimensionPropertyMap) {
		this.dimensionPropertyMap = dimensionPropertyMap;
	}

}
