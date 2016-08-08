package org.opensdmx.adapter.olap4j.conventions;

import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.util.OpenSdmxException;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValuesType;



/**
 * 
 * Class to define a convention to add attributes to dataset.   
 * 
 * 
 * @author Dario Fabbri
 *
 */
public class DataSetAttributeConvention extends Convention {

	public ValuesType apply(MdxMetadata c) {
		
		if(c == null)
			throw new OpenSdmxException("Unexpected null metadata.");
		if(c.getFixedDatasetAttributeValueList() == null)
			throw new OpenSdmxException("Unexpected null dataset attribute list in metadata.");
		
		// Create return value.
		//
		ValuesType attributes = new ValuesType();
		
		// If no attributes are present in metadata, just return.
		//
		if(c.getFixedDatasetAttributeValueList() == null || 
				c.getFixedDatasetAttributeValueList().size() == 0)
			return attributes;
		
		for(NameValue nameValue : c.getFixedDatasetAttributeValueList()) {
			ValueType d = createValueType(nameValue);
			attributes.getValues().add(d);
		}
		
		return attributes;
	}

}
