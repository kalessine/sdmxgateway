package org.opensdmx.adapter.olap4j.conventions;

import java.util.List;

import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.repository.generatormdx.Datastructure;
import org.opensdmx.util.OpenSdmxException;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesKeyType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.SeriesType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;



/**
 * 
 * Generic class for all conventions that manipulate the key
 * 
 * 
 * @author Dario Fabbri
 *
 */
public abstract class KeyBasedConvention extends Convention {

	public void apply(MdxMetadata c, Datastructure datastructure, List<SeriesType> seriesList) {

		if(seriesList == null)
			throw new OpenSdmxException("Unexpected null series list detected.");
		
		for (SeriesType s : seriesList) {
			SeriesKeyType key = s.getSeriesKey();
			checkIfAlreadyApplied(key);
			applyConvention2Key(c, datastructure, key);
		}

	}

	abstract void applyConvention2Key(MdxMetadata c, Datastructure datastructure, SeriesKeyType key);

	abstract String getConcept();

	private void checkIfAlreadyApplied(SeriesKeyType key) {
		List<ValueType> values = key.getValues();
		for (ValueType valueType : values) {
			if (valueType.getConcept().equals(getConcept())) {
				throw new OpenSdmxException("There is already the " + getConcept()
						+ " dimension, has this convention already been applied?");
			}
		}
	}

}
