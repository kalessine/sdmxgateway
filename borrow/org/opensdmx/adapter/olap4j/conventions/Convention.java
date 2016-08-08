package org.opensdmx.adapter.olap4j.conventions;

import org.opensdmx.adapter.olap4j.external.NameValue;
import org.opensdmx.util.OpenSdmxException;
import org.sdmx.resources.sdmxml.schemas.v2_0.generic.ValueType;

public abstract class Convention {

	public ValueType createValueType(NameValue nameValue) {
		
		ValueType d = new ValueType();
		if (nameValue.getName() == null) {
			throw new OpenSdmxException("FixedDimensionValueConvention: No name found for the concept");
		}
		d.setConcept(nameValue.getName());
		d.setValue(nameValue.getValue());
		
		return d;
	}

}
