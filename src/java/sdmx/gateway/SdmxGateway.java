/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.jersey.servlet.ServletContainer;
/**
 *
 * @author James
 *
 */
public class SdmxGateway extends ResourceConfig {
    public SdmxGateway() {
        packages("sdmx.gateway");
    }
}
