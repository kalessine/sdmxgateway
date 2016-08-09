
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import sdmx.gateway.data.DatabaseData;
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

    public static DatabaseData dd = new DatabaseData();
     
    @BeforeClass
    public static void before() {
    }

    @AfterClass
    public static void after() {
    }

    @Test
    public void loadALC() throws MalformedURLException {
        try {
            DataProvider dp = ServiceList.getDataProvider(0, "ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx", "http://stats.oecd.org/OECDStatWS/SDMX/", "Based on Australian Bureau of Statistics data", "Based on Australian Bureau of Statistics data");
            Queryable queryable = dp.getQueryable();
            Registry reg = queryable.getRegistry();
            Repository rep = queryable.getRepository();
            DataflowType flow = new DataflowType();
            flow.setAgencyID(new NestedNCNameID("ABS"));
            flow.setId(new IDType("ALC"));
            flow.setVersion(Version.ONE);
            DataStructureReference ref = DataStructureReference.create(new NestedNCNameID("ABS"),new IDType("ALC"), Version.ONE);
            flow.setStructure(ref);
            DataStructureType struct = reg.find(ref);
            dd.createDataflow(struct, "ALC");
            DataQueryMessage query = new DataQueryMessage();
            DataQuery q = new DataQuery();
            DataParametersAndType dw = new DataParametersAndType();
            // This sets which cube we want to query...
            // some queryables fudge the dataflow name (like SDW)
            // as SDW does not have dataflows, only a list of datastructures
            dw.setDataflow(Collections.singletonList(flow.asReference()));
            // hard coded times
            // year-month-day
            dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("1940-01-01"), new TimeValue("2017-01-01"))));
            DataParametersType dpt = new DataParametersType();
            // Some Providers require another "AND" query element to be under the main DataParametersType(which is an And)
            // so we put everything into dw and set it here
            dpt.setAnd(Collections.singletonList(dw));
            q.setDataWhere(dpt);
            query.setQuery(q);
            ParseParams params = new ParseParams();
            params.setDataflow(flow);
            params.setRegistry(reg);
            params.setCallbackHandler(new FlatParseDataCallbackHandler());
            DataMessage dm = null;
            try {
                dm = rep.query(params, query);
            } catch (ParseException ex) {
                Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
            }
            dm.dump();
            dd.insertDataflow((FlatDataSet)dm.getDataSets().get(0), "ALC");
        } catch (SQLException ex) {
            Logger.getLogger(LoadABSDotStatData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
