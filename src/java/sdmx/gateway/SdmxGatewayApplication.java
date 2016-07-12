/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway;

import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.Queryable;
import sdmx.Registry;
import sdmx.SdmxIO;
import sdmx.gateway.services.DatabaseRegistry;
import sdmx.net.list.DataProvider;
import sdmx.structure.dataflow.DataflowType;

/**
 *
 * @author James
 */
public class SdmxGatewayApplication {
    private Queryable queryable = null;
    
    private Registry registry = new DatabaseRegistry();
    
    
    public SdmxGatewayApplication(){
        DataProvider dp = DataProvider.getList().get(0);
        try {
            connect(dp.getType(),dp.getServiceURL(),dp.getOptions(),dp.getAgencyId(),dp.getAttribution(),dp.getHtmlAttribution());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SdmxGatewayApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static final SdmxGatewayApplication APP = new SdmxGatewayApplication();
    public static SdmxGatewayApplication getApplication() {
        return APP;
    }

    public void connect(int type, String serviceURL, String options, String agencyId, String attribution, String htmlAttribution) throws MalformedURLException {
        queryable = SdmxIO.connect(type, agencyId, serviceURL, options, attribution, htmlAttribution);
        List<DataflowType> dataflows = getQueryable().getRegistry().listDataflows();
        int i = 0;
        for(DataflowType df:dataflows) {
            System.out.println("Loading Structure:"+df.getName());
            getQueryable().getRegistry().find(df.getStructure());
            if( i++==5 )break;
        }
    }

    /**
     * @return the queryable
     */
    public Queryable getQueryable() {
        return queryable;
    }
    public Registry getRegistry() { return this.registry; }
}

