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
import sdmx.Repository;
import sdmx.SdmxIO;
//import sdmx.gateway.services.DatabaseRepository;
import sdmx.gateway.services.DatabaseRegistry;
import sdmx.net.list.DataProvider;
import sdmx.structure.dataflow.DataflowType;

/**
 *
 * @author James
 */
public class SdmxGatewayApplication {

    private Queryable queryable = new Queryable() {

        @Override
        public Registry getRegistry() {
            return registry;
        }

        @Override
        public Repository getRepository() {
            //return rep;
            return null;
        }
    };
    private Registry registry = new DatabaseRegistry();
    //private Repository rep = (Repository) new DatabaseRepository();

    public SdmxGatewayApplication() {
    }

    public static final SdmxGatewayApplication APP = new SdmxGatewayApplication();

    public static SdmxGatewayApplication getApplication() {
        return APP;
    }

    /**
     * @return the queryable
     */
    public Queryable getQueryable() {
        return queryable;
    }

    public Registry getRegistry() {
        return this.registry;
    }
}
