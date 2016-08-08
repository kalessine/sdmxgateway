package org.opensdmx.adapter.olap4j.dao;

import org.apache.log4j.Logger;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.opensdmx.adapter.olap4j.external.MdxMetadata;
import org.opensdmx.util.OpenSdmxException;

public class Olap4jInteraction {

	private Logger logger = Logger.getLogger(Olap4jInteraction.class);

	private static int TRIES = 2;

	private OlapConnection olapConnection;

	public Olap4jInteraction(OlapConnection OlapConnection) {
		this.olapConnection = OlapConnection;
	}

	public CellSet generateCellSet(MdxMetadata mdxMetadata) {
		
		logger.debug("Hitting generateCellSet.");
		
		OlapStatement statement;
		CellSet cellSet = null;
		boolean again = true;
		int tries = 0;

		while (again && tries <= TRIES) {
			try {
				statement = olapConnection.createStatement();
				cellSet = statement.executeOlapQuery(mdxMetadata.getMdx());
				again = false;
			} catch (OlapException e) {
				e.printStackTrace();
				tries++;
				if (tries > TRIES) {
					throw new OpenSdmxException(e);
				}
			}
		}
		return cellSet;
	}
}
