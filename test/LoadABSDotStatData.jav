
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.Queryable;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.SdmxIO;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.data.DefaultParseDataCallbackHandler;
import sdmx.data.FlatParseDataCallbackHandler;
import sdmx.data.flat.FlatDataSet;
import sdmx.exception.ParseException;
import sdmx.gateway.services.DatabaseRepository;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.net.ServiceList;
import sdmx.net.list.DataProvider;
import sdmx.query.base.TimeValue;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQuery;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.querykey.Query;
import sdmx.querykey.impl.RegistryQuery;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.version.common.ParseParams;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author James
 */
public class LoadABSDotStatData {

    public static DatabaseRepository dd = new DatabaseRepository();

    @BeforeClass
    public static void before() {
    }

    @AfterClass
    public static void after() {
    }

    @Test
    public void loadALC() throws MalformedURLException {
        SdmxIO.setDumpQuery(true);
        DataProvider dp = ServiceList.listDataProviders().get(6);
        Queryable queryable = dp.getQueryable();
        Registry reg = queryable.getRegistry();
        Repository rep = queryable.getRepository();
        List<DataflowType> dfs = reg.listDataflows();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(Calendar.YEAR, 1000);
        start.set(Calendar.MONTH, 1);
        start.set(Calendar.DATE, 1);
        end.set(Calendar.YEAR, 2016);
        end.set(Calendar.MONTH, 1);
        end.set(Calendar.DATE, 1);
        for (int i = 0; i < dfs.size(); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
            }
            DataflowType flow = dfs.get(i);
            DataStructureType struct = reg.find(flow.getStructure());
            if (!dd.hasDataflow(flow.getId().toString())) {
                try {
                    System.out.println("Create Dataflow " + flow.getId().toString());
                    dd.createDataflow(struct, flow.getId().toString());
                } catch (SQLException ex) {
                    Logger.getLogger(LoadABSDotStatData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ItemSchemeType cl = reg.find(struct.getDataStructureComponents().getDimensionList().getDimension(0).getLocalRepresentation().getEnumeration());
            ItemSchemeType cl2 = reg.find(struct.getDataStructureComponents().getDimensionList().getDimension(1).getLocalRepresentation().getEnumeration());
            // Split queries by first dimension code id
            for (int cli = 0; cli < cl.size(); cli++) {
                for (int cli2 = 0; cli2 < cl2.size(); cli2++) {
                    Query q = new RegistryQuery(struct, reg, flow.getId().toString());
                    for (int j = 0; j < q.size(); j++) {
                        if (j == 0) {
                            q.getQueryDimension(j).addValue(q.getQueryDimension(j).getPossibleValues().get(cli).getId().toString());
                        }
                        if (j == 1) {
                            q.getQueryDimension(j).addValue(q.getQueryDimension(j).getPossibleValues().get(cli2).getId().toString());
                        } else {
                            // Everything
                            for (int k = 0; k < q.getQueryDimension(j).getPossibleValues().size(); k++) {
                                q.getQueryDimension(j).addValue(q.getQueryDimension(j).getPossibleValues().get(k).getId().toString());
                            }
                        }
                    }
                    q.setProviderRef(flow.getAgencyID().toString());
                    q.getQueryTime().setStartTime(start.getTime());
                    q.getQueryTime().setEndTime(end.getTime());
                    ParseParams params = new ParseParams();
                    params.setRegistry(reg);
                    DataMessage dm = null;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                    }
                    try {
                        dm = rep.query(q);
                    } catch (ParseException ex) {
                        Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    } catch (IllegalArgumentException ilae) {
                        ilae.printStackTrace();
                    }
                    if (dm != null) {
                        try {
                            System.out.println("Inserting " + dm.getDataSets().get(0).size() + " obs");
                            dd.insertDataflow(dm.getDataSets().get(0), flow.getId().toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(LoadABSDotStatData.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
