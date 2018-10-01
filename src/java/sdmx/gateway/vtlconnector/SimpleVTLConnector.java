/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.vtlconnector;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ssb.vtl.connectors.ConnectorException;
import no.ssb.vtl.model.Dataset;
import sdmx.SdmxIO;

/**
 *
 * @author James
 */
public class SimpleVTLConnector {

    public boolean canHandle(String identifier) {
        return identifier.startsWith("sdmx://");
    }

    public Dataset getDataset(String identifier) throws ConnectorException {
        /*
        return new Dataset() {

            public getData() {
                try {
                    URL url = new URL(identifier.replace("sdmx://", "http://"));
                    //SdmxIO.parseData(url.openStream());
                    return null;
                } catch (MalformedURLException ex) {
                    Logger.getLogger(SimpleVTLConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/
        return null;
    }

    public Dataset putDataset(String identifier, Dataset set) {
        return null;
    }
}
