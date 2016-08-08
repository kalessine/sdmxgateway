package org.opensdmx.adapter.olap4j.conventions;

import org.olap4j.Position;
import org.olap4j.metadata.Member;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.repository.generatormdx.MemberPropertyUtil;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;

/**
 * Add the concept of the measure as a dimension to the dataset on the 3th
 * position.
 * 
 * Note: the measure position is now 2, this may be calculated more
 * intelligently in the future
 * 
 * @author Erik van Ingen
 * 
 */
public class IndicatorDimensionConvention {

	public static final String INDICATOR = "INDICATOR";
	public static final int MEASURE_POSITION = 2;
	private MemberPropertyUtil u = new MemberPropertyUtil();

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param m
	 * @param positionY
	 * @param series
	 */
	public void applyConvention2Key(MdxMetadata m, Position positionY, SeriesType series) {
		if (m.isMeasureAsIndicatorDimension()) {
			Member member0 = positionY.getMembers().get(MEASURE_POSITION);
			ValueType d = new ValueType();
			d.setConcept(INDICATOR);

			// Workaround? INDICATOR has now value m511. This should be 511,
			// therefore the first character is omitted. TODO Discuss this with
			// Giorgio.

			d.setValue(u.getValueFrom(member0).substring(1));
			int position = 2;
			if (m.isFreqAsDimension()) {
				// when the FREQ will be added as a dimension, the positions
				// will be different.
				position = 1;
			}
			series.getSeriesKey().getValues().add(position, d);
		}
	}

}
