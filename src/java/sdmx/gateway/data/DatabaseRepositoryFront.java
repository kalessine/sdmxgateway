/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdmx.Queryable;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.SdmxIO;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.net.ServiceList;
import sdmx.net.list.DataProvider;
import sdmx.querykey.Query;
import sdmx.structure.base.Component;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.util.QueryStringUtils;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.xml.DateTime;

/**
 *
 * @author James
 */
public class DatabaseRepositoryFront implements Repository {

    public static final SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");

    Registry reg = null;
    DatabaseRepository rep = new DatabaseRepository();

    public DatabaseRepositoryFront(Registry reg) {
        this.reg = reg;
    }

    @Override
    public DataMessage query(Query query) throws ParseException, IOException {
        try {
            return rep.query(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseRepositoryFront.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void query(Query query, ParseDataCallbackHandler handler) throws ParseException, IOException {
        try {
            rep.query(query,handler);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseRepositoryFront.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
