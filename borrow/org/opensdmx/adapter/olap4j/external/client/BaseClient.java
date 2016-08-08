package org.opensdmx.adapter.olap4j.external.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.opensdmx.client.OpenSdmxClientException;
import org.opensdmx.client.sdmx20.Sdmx20ClientAbstract;

public abstract class BaseClient extends Sdmx20ClientAbstract implements Client{
	/**
	 * Check the protocol of the target URL, and read the input stream.
	 * @param url
	 * @return
	 */
	public InputStream deriveInputStream(URL url){
		InputStream is = null;
		String protocol = url.getProtocol();
		if("file".equalsIgnoreCase(protocol)){ //File protocol
			is = deriveInputStreamFromFile(url);
		}else{//HTTP protocol
			is = deriveInputStreamFrom(url);
		}
		return is;
	}
	
	/**
	 * Get input stream from File URL.
	 * @param url
	 * @return
	 */
	public InputStream deriveInputStreamFromFile(URL url){
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	/**
	 * Parse an Flow structure into its java representation.
	 * @param is
	 * @param class1
	 * @return
	 */
	public Object parseStructure(InputStream is,Class<?> class1){
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(class1);
			Unmarshaller u = jc.createUnmarshaller();
			return u.unmarshal(is);
		} catch (JAXBException e) {
			throw new OpenSdmxClientException(e);
		}
	}
	
}
