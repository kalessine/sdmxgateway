package org.opensdmx.adapter.olap4j.repository.generatormdx;

import org.olap4j.OlapException;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.NamedList;
import org.opensdmx.util.OpenSdmxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberPropertyUtil {

	private static final Logger log = LoggerFactory.getLogger(MemberPropertyUtil.class);

	public final static String PROPERTY = "MEMBER_KEY";

	public String getValueFrom(Member member) {
		String foundValue = null;
		NamedList<org.olap4j.metadata.Property> ps = member.getProperties();
		for (org.olap4j.metadata.Property property : ps) {

			if (property.getName().equals(PROPERTY)) {
				try {
					foundValue = member.getPropertyValue(property).toString();
				} catch (OlapException e) {
					log.error("Exception caught while accessing property value.", e);
					throw new OpenSdmxException("Exception caught while accessing property value.", e);
				}
			}
		}
		return foundValue;
	}
}
