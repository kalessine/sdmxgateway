package org.opensdmx.adapter.olap4j.conventions;

import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.repository.generatormdx.Datastructure;
import org.opensdmx.adapter.olap4j.repository.generatormdx.FreqFromMdx;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesKeyType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;

/**
 * Derive the frequency from the mdx and add this as a dimension in the dataset.
 * 
 * If FREQ is a SDMX dimension, it is always the first SDMX dimension and it
 * will be derived from the Time "ON COLUMNS"
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class FreqDimensionConvention extends KeyBasedConvention {

	public static final String FREQ = "FREQ";

	FreqFromMdx u = new FreqFromMdx();

	@Override
	void applyConvention2Key(MdxMetadata c, Datastructure datastructure, SeriesKeyType key) {
		ValueType d = new ValueType();
		d.setConcept(FREQ);
		d.setValue(u.understandFregFrom(c.getMdx()).toString());
		key.getValues().add(0, d);
	}

	@Override
	String getConcept() {
		return FREQ;
	}

}
