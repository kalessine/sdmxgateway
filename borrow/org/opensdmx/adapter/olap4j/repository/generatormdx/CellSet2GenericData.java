package org.opensdmx.adapter.olap4j.repository.generatormdx;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.olap4j.Axis;
import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.OlapException;
import org.olap4j.Position;
import org.olap4j.metadata.Member;
import org.opensdmx.adapter.olap4j.configuration.repository.Block;
import org.opensdmx.adapter.olap4j.configuration.repository.BlockAttribute;
import org.opensdmx.adapter.olap4j.conventions.DimensionPropertyConvention;
import org.opensdmx.adapter.olap4j.conventions.IndicatorDimensionConvention;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.adapter.olap4j.util.WhatTime;
import org.opensdmx.util.OpenSdmxException;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ObsType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ObsValueType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesKeyType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValuesType;
import org.springframework.stereotype.Component;

/**
 * Convert an Olap4j cellset to a SDMX generic dataset.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Component
public class CellSet2GenericData {

	protected Log log = LogFactory.getLog(this.getClass());
	private IndicatorDimensionConvention indicatorDimensionConvention = new IndicatorDimensionConvention();
	private DimensionPropertyConvention dimensionPropertyConvention = new DimensionPropertyConvention();

	private WhatTime whatTime = new WhatTime();

	/**
	 * Convert a Olap4j cellSet to an SDMX dataset.
	 * 
	 * Note that a SDMX series is a series of series. A single series is a list
	 * of observation. A multiple series is a series of series. A SDMX dataset
	 * in this case holds a series of series.
	 * 
	 * 
	 * @param cellSet
	 * @param dataSet2
	 * @param m
	 * @return
	 */
	public void convert(CellSet cellSet, List<SeriesType> seriesList, Datastructure datastructure, MdxMetadata m, String flow) {

		// convert

		CellSetAxis columnTimeAxis = cellSet.getAxes().get(Axis.COLUMNS.axisOrdinal());
		CellSetAxis rowDimensionAxis = cellSet.getAxes().get(Axis.ROWS.axisOrdinal());
		List<Position> positions = rowDimensionAxis.getPositions();

		// loop over horizontal dimensions
		int i = 0;
		while (i < positions.size()) {
			Position positionY = positions.get(i);

			// Read indicator value from cellset
			String concept = positionY.getMembers().get(datastructure.getNumberOfDimensions()).getName();

			// Map it to the proper block
			Block block = m.getCellSetMeasureStructure().getBlockMap().get(concept);
			if (block == null) {
				throw new OpenSdmxException("Unexpected null block detected for concept: " + concept);
			}

			// Create a new series
			SeriesType series = new SeriesType();

			// Add attributes to the series
			addAttributesOnSeriesLevel(series, m);

			// Iterate on X-axis to add observations and attributes to
			// observations using block attributes definition
			// loop over vertical dimensions (in case of faostat these are
			// measures)
			// this is looping over the block attributes
			addKey2Series(m, datastructure, positionY, series, flow);
			indicatorDimensionConvention.applyConvention2Key(m, positionY, series);
			for (Position positionX : columnTimeAxis.getPositions()) {
				Cell currentCell = cellSet.getCell(positionX, positionY);
				ObsType observation = addObservation2Series(series, positionX, currentCell);

				// Iterate on block attributes.
				if (block.getAttributes() != null) {
					for (int j = 0; j < block.getAttributes().size(); ++j) {

						BlockAttribute blockAttribute = block.getAttributes().get(j);
						if (blockAttribute.isUse()) {

							// Get cell in the same x position and in y position
							// = current y + attributeindex
							Cell attrCell = cellSet.getCell(positionX, positions.get(i + j + 1));

							// Add attribute to observation
							addAttribute2Observation(attrCell, observation, blockAttribute.getConcept());
						}
					}
				}
			}

			if (series.getObs().size() > 0) {
				// only when there are observations, the series will be added.
				seriesList.add(series);
			}

			// Move YPosition to the next block (i += current block size)
			i += block.getSize();
		}

		/*
		 * TODO: we need to verify if these are used.
		 * 
		 * if (dataSet.getGroupsAndSeries().size() == 0) {
		 * dataSet.setAnnotations(SdmxUtil.createAnnotations("Message",
		 * "No observations found")); } else {
		 * dataSet.setAnnotations(SdmxUtil.createAnnotations
		 * ("Concept of the measure", datastructure.getMeasureConcept())); }
		 */
	}

	/**
	 * Add the attributes to the series as defined in the MdxMetadata object for
	 * fixedAttributeValueList
	 * 
	 * 
	 * @param series
	 * @param m
	 */
	private void addAttributesOnSeriesLevel(SeriesType series, MdxMetadata m) {
		if (m.getFixedSeriesAttributeValueList() != null) {
			// in case attributes are available to be added, it will be done.
			if (series.getAttributes() == null) {
				// if null, create one
				ValuesType attributes = new ValuesType();
				series.setAttributes(attributes);
			}

			List<NameValue> attributes = m.getFixedSeriesAttributeValueList();
			for (NameValue nameValue : attributes) {
				ValueType attribute = new ValueType();
				attribute.setConcept(nameValue.getName());
				attribute.setValue(nameValue.getValue());
				series.getAttributes().getValues().add(attribute);
			}
		}
	}

	/**
	 * @TODO this could be the proper place where to enforce mandatory
	 *       attributes presence.
	 * 
	 *       In case of a value for the attribute, add it as an attribute.
	 * 
	 *       In case the value consists of 1 or 2 spaces, it will not be added
	 *       to the observation.
	 * 
	 * @param fact
	 * @param observation
	 * @param concept
	 */
	private void addAttribute2Observation(Cell fact, ObsType observation, String concept) {
		if (fact.getValue() != null) {
			ValueType value = new ValueType();
			value.setConcept(concept);
			String attributeValue = fact.getValue().toString().trim();
			if (attributeValue.length() > 0) {
				value.setValue(attributeValue);
				if (observation.getAttributes() == null) {
					observation.setAttributes(new ValuesType());
				}
				observation.getAttributes().getValues().add(value);
			}
		}
	}

	/**
	 * Add the SDMX key to this series.
	 * 
	 * @param m
	 * 
	 * @param datastructure
	 *            .getMeausureConcept()
	 * @param position
	 * @param series
	 */
	private void addKey2Series(MdxMetadata m, Datastructure datastructure, Position position, SeriesType series, String flow) {
		SeriesKeyType key = new SeriesKeyType();
		String[] ds = datastructure.getDimensionNames();
		for (int i = 0; i < ds.length; i++) {
			String dimension = ds[i];
			ValueType value = new ValueType();
			//String concept = metadataService.getConcept(flow, dimension);
			
			Map<String, String> map = datastructure.getConcepts().get(flow);
			if (map == null) {
				throw new OpenSdmxException("MetadataService did not provide a concept name for this flow: "
						+ flow);
			}
			String concept = map.get(dimension);
			if (concept == null || concept.equals("")) {
				throw new OpenSdmxException("MetadataService did not provide a concept name for this dimension: "
						+ dimension);
			}
			value.setConcept(concept);
			
			Member valueMember = position.getMembers().get(i);
			value.setValue(dimensionPropertyConvention.getValueFrom(dimension, m.getDimensionPropertyMap(), valueMember));
			key.getValues().add(value);
		}
		series.setSeriesKey(key);
	}

	/**
	 * Add on observation to this series.
	 * 
	 * @param series
	 * @param columnTime
	 * @param fact
	 */
	private ObsType addObservation2Series(SeriesType series, Position columnTime, Cell fact) {
		ObsType observation = new ObsType();
		Member timeValue = columnTime.getMembers().get(columnTime.getMembers().size() - 1);
		observation.setTime(whatTime.olapTime2Sdmx(timeValue.getUniqueName()));
		ObsValueType value = new ObsValueType();
		if (fact.getValue() != null) {
			try {
				value.setValue(fact.getDoubleValue());
				observation.setObsValue(value);
				series.getObs().add(observation);
			} catch (OlapException e) {
				// only when the cause is not a number is valid. This means
				// there is no value, and therefore no
				// observation.
				if (e.getCause() != null && !e.getCause().equals("not a number")) {
					throw new OpenSdmxException(e);
				}
			}
		}
		return observation;

	}
}
