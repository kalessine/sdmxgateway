/**
 * 
 */
package org.opensdmx.adapter.olap4j.external.client.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Dario Fabbri
 * 
 */
@XmlRootElement(name="Flow")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flow{

	private String uuid;
	private String name;
	private String provider;
	private String mdx;
	
	private boolean measureAsIndicatorDimension;
	private boolean freqAsDimension;
	
	private String catalog;
	
	@XmlElementWrapper(name="dimensionValues")
	@XmlElement(name="dimensionValue")
	private List<DimensionValue> dimensionValues;
	
	@XmlElementWrapper(name="datasetAttributeValues")
	@XmlElement(name="datasetAttributeValue")
	private List<DatasetAttributeValue> datasetAttributeValues;
	
	@XmlElementWrapper(name="seriesAttributeValues")
	@XmlElement(name="seriesAttributeValue")
	private List<SeriesAttributeValue> seriesAttributeValues;
	
	private Map<String, String> conceptDimensionMap;
	
	private Map<String, String> dimensionProperty;
	
	private MeasureStructure cellSetMeasureStructure;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	public boolean isMeasureAsIndicatorDimension() {
		return measureAsIndicatorDimension;
	}

	public void setMeasureAsIndicatorDimension(boolean measureAsIndicatorDimension) {
		this.measureAsIndicatorDimension = measureAsIndicatorDimension;
	}

	public boolean isFreqAsDimension() {
		return freqAsDimension;
	}

	public void setFreqAsDimension(boolean freqAsDimension) {
		this.freqAsDimension = freqAsDimension;
	}

	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public List<DimensionValue> getDimensionValues() {
		return dimensionValues;
	}

	public void setDimensionValues(List<DimensionValue> dimensionValues) {
		this.dimensionValues = dimensionValues;
	}

	public List<DatasetAttributeValue> getDatasetAttributeValues() {
		return datasetAttributeValues;
	}

	public void setDatasetAttributeValues(
			List<DatasetAttributeValue> datasetAttributeValues) {
		this.datasetAttributeValues = datasetAttributeValues;
	}

	public List<SeriesAttributeValue> getSeriesAttributeValues() {
		return seriesAttributeValues;
	}

	public void setSeriesAttributeValues(
			List<SeriesAttributeValue> seriesAttributeValues) {
		this.seriesAttributeValues = seriesAttributeValues;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Map<String, String> getDimensionProperty() {
		return dimensionProperty;
	}

	public void setDimensionProperty(Map<String, String> dimensionProperty) {
		this.dimensionProperty = dimensionProperty;
	}

	public Map<String, String> getConceptDimensionMap() {
		return conceptDimensionMap;
	}

	public void setConceptDimensionMap(Map<String, String> conceptDimensionMap) {
		this.conceptDimensionMap = conceptDimensionMap;
	}

	public MeasureStructure getCellSetMeasureStructure() {
		return cellSetMeasureStructure;
	}

	public void setCellSetMeasureStructure(MeasureStructure cellSetMeasureStructure) {
		this.cellSetMeasureStructure = cellSetMeasureStructure;
	}

	
}
