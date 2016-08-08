package org.opensdmx.adapter.olap4j.repository.generatormdx;

import org.olap4j.Axis;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.opensdmx.util.OpenSdmxException;

public class CellSetAnalyzer {

	public static final String MEASURES = "Measures";
	public static final int NUMBER_OF_MEASURES = 1;

	public Datastructure analyze(CellSet cellSet) {
		// precondition
		assert cellSet.getAxes().size() == 2;

		CellSetAxis rowDimensionAxis = cellSet.getAxes().get(Axis.ROWS.axisOrdinal());

		// derive the number of dimensions
		int numberOfDimensions = rowDimensionAxis.getAxisMetaData().getHierarchies().size() - 1;
		int indexOfLastDimension = numberOfDimensions - 1;

		checkMeasuresConvention(rowDimensionAxis);
		checkDimensionsConvention(rowDimensionAxis);

		// derive the measure
		String measureConcept = rowDimensionAxis.getPositions().get(0).getMembers().get(numberOfDimensions).getName();

		// get the dimensions
		String[] dimensionNames = new String[numberOfDimensions];
		for (int i = 0; i <= indexOfLastDimension; i++) {
			String dimensionConcept = rowDimensionAxis.getAxisMetaData().getHierarchies().get(i).getName();
			dimensionNames[i] = dimensionConcept;
		}

		// fill in the metadata object in SDMX terms
		Datastructure ds = new Datastructure();
		ds.setMeasureConcept(measureConcept);
		ds.setNumberOfDimensions(numberOfDimensions);
		ds.setDimensionNames(dimensionNames);
		return ds;
	}

	/**
	 * The convention is that there are at least 2 dimensions (from which one is
	 * the measure dimension)
	 * 
	 * @param rowDimensionAxis
	 */
	private void checkDimensionsConvention(CellSetAxis rowDimensionAxis) {
		int nrOfDimensions = rowDimensionAxis.getAxisMetaData().getHierarchies().size();
		if (nrOfDimensions < 2) {
			throw new OpenSdmxException("There are less then 2 dimensions, number of found dimensions is: "
					+ nrOfDimensions);
		}

	}

	/**
	 * The convention is that the last hierarchy has the measures. The name is
	 * Measures.
	 * 
	 * @param rowDimensionAxis
	 */
	private void checkMeasuresConvention(CellSetAxis rowDimensionAxis) {
		int indexMeasuresHierarchy = rowDimensionAxis.getAxisMetaData().getHierarchies().size() - 1;
		String name = rowDimensionAxis.getAxisMetaData().getHierarchies().get(indexMeasuresHierarchy).getName();
		if (!name.equals(MEASURES)) {
			throw new OpenSdmxException("Name was not Measures but: " + name);
		}
	}

	/**
	 * Convention1: The row axis has on the first position only 1 member. This 1
	 * member represents the concept of the fact (observation).
	 * 
	 * 
	 * 
	 * @param cellSet
	 */
	public void validateConventions(CellSet cellSet) {
		// precondition
		if (cellSet.getAxes().size() != 2) {
			throw new OpenSdmxException("Unexpected number of axis detected: " + cellSet.getAxes().size());
		}

		CellSetAxis rowDimensionAxis = cellSet.getAxes().get(Axis.ROWS.axisOrdinal());
		if (rowDimensionAxis.getAxisMetaData().getHierarchies().size() < 1) {
			throw new OpenSdmxException("Unexpected number of hierarchies detected: "
					+ rowDimensionAxis.getAxisMetaData().getHierarchies().size());
		}
	}

}
