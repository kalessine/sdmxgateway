package org.opensdmx.adapter.olap4j.conventions;

import java.util.Map;

import org.olap4j.OlapException;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.NamedList;
import org.opensdmx.util.OpenSdmxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The definition of the DimensionPropertyConvention is this: The value of a OLAP dimension is by default taken on the
 * dimension level (from the MEMBER_KEY property). If however the propertyDimension is specified, it will take the value
 * of the specified property.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DimensionPropertyConvention {

	private static final Logger log = LoggerFactory.getLogger(DimensionPropertyConvention.class);

	public final static String PROPERTY = "MEMBER_KEY";

	public String getValueFrom(String dimension, Map<String, String> dimensionPropertyMap, Member member) {
		String foundValue = null;
		NamedList<org.olap4j.metadata.Property> ps = member.getProperties();
		String takeValueFrom = null;
		if (dimensionPropertyMap != null && dimensionPropertyMap.containsKey(dimension)) {
			// apply convention
			takeValueFrom = dimensionPropertyMap.get(dimension);
		} else {
			// do not apply convention
			takeValueFrom = PROPERTY;
		}
		
		if(takeValueFrom == null) {
			log.error("Unable to decide on property convention.");
			throw new OpenSdmxException("Unable to decide on property convention.");
		}
		
		for (org.olap4j.metadata.Property property : ps) {
			if (property.getName().equals(takeValueFrom)) {
				try {
					foundValue = member.getPropertyValue(property).toString();
				} catch (OlapException e) {
					log.error("Exception caught while accessing property value.", e);
					throw new OpenSdmxException("Exception caught while accessing property value.", e);
				}
			}
		}
		if(foundValue == null) {
			log.error("Unable to read property. Check dimensionPropertyMap configuration.");
			throw new OpenSdmxException("Unable to read property. Check dimensionPropertyMap configuration.");
		}
		
		log.debug("takeValueFrom = " + takeValueFrom + " foundValue = " + foundValue);
		return foundValue;
	}
}
