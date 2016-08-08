package org.opensdmx.adapter.olap4j.conventions;

import java.util.List;

import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.adapter.olap4j.repository.generatormdx.Datastructure;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesKeyType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;

/**
 * Dimension(s) with a fixed name and value will be added as the last
 * dimension(s).
 * 
 * Done for example for the FAOSTAT domain
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class FixedDimensionValueConvention extends KeyBasedConvention {

	private String dimension;

	/**
	 * Add all the dimensions in the list with the given fixed value.
	 * 
	 */
	@Override
	void applyConvention2Key(MdxMetadata m, Datastructure datastructure, SeriesKeyType key) {
		List<NameValue> list = m.getFixedDimensionValueList();
		for (NameValue nameValue : list) {
			ValueType d = createValueType(nameValue);
			key.getValues().add(d);
			// the last dimension is set for checking
			dimension = nameValue.getName();
		}
	}

	@Override
	String getConcept() {
		return dimension;
	}
}
