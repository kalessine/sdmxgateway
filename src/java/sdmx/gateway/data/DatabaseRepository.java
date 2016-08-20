/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.data;

import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.SdmxIO;
import sdmx.common.PayloadStructureType;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.data.ColumnMapper;
import sdmx.data.DataSet;
import sdmx.data.DataSetWriter;
import sdmx.data.DefaultParseDataCallbackHandler;
import sdmx.data.flat.FlatDataSet;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.flat.FlatObs;
import sdmx.exception.ParseException;
import sdmx.gateway.SdmxGatewayApplication;
import sdmx.message.BaseHeaderType;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.DataStructure;
import sdmx.querykey.Query;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.util.PostParseUtilities;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;

/**
 *
 * @author James
 */
public class DatabaseRepository implements Repository {

    PoolingDataSource<Connection> pool;

    public DatabaseRepository() {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("jdbc:mysql://localhost:3306/sdmxgateway", "root", "pooky213");
        PoolableConnectionFactory poolableConnectionFactory;
        poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        GenericObjectPool connectionPool = new GenericObjectPool(poolableConnectionFactory);
        pool = new PoolingDataSource(connectionPool);
    }

    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    public void returnConnection(Connection c) throws SQLException {
        if (!c.isClosed()) {
            c.close();
        }
    }

    public void createDataflow(DataStructureType struct, String id) throws SQLException {
        String create = "create table if not exists `flow_" + id + "` (";
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            create += "`" + dim.getId().toString() + "` VARCHAR(50)";
            if (struct.getDataStructureComponents().getDimensionList().size() - 1 > i) {
                create += ",";
            }
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType dim = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            create += ",";
            create += "`" + dim.getId().toString() + "` VARCHAR(50)";
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType dim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            create += ",";
            create += "`" + dim.getId().toString() + "` VARCHAR(50)";
        }
        for (int i = 0; i < struct.getDataStructureComponents().getAttributeList().size(); i++) {
            create += ",";
            AttributeType att = struct.getDataStructureComponents().getAttributeList().getAttribute(i);
            create += "`" + att.getId().toString() + "` VARCHAR(50)";
        }
        create += ",";
        PrimaryMeasure pm = struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure();
        create += "`" + pm.getId().toString() + "` VARCHAR(50)";
        create += ");";
        Connection con = pool.getConnection();
        System.out.println(create);
        // TODO Add Column Indexes to improve query performance
        PreparedStatement pst = con.prepareStatement(create);
        pst.executeUpdate();
        returnConnection(con);
    }

    public void deleteDataflow(DataflowType df) {

    }

    /**
     *
     * @param query
     * @return
     * @throws SQLException
     */
    @Override
    public DataMessage query(Query query) {
        try{
        List<FlatObs> result = new ArrayList<FlatObs>();
        Connection con = pool.getConnection();
        String select = "select * from `flow_" + query.getFlowRef() + "`";
        int count = 0;
        if (query.getQuerySize() > 0) {
            select += " where ";
            for (int i = 0; i < query.size() && query.getQueryDimension(i).size() > 0; i++) {
                if (count != 0 && count < query.size() - 1) {
                    select += " and ";
                }
                count += query.getQueryDimension(i).size();
                select += query.getQueryDimension(i).getConcept() + " in ";
                select += "(";
                for (int j = 0; j < query.getQueryDimension(i).size(); j++) {
                    select += "'" + query.getQueryDimension(i).getValues().get(j) + "'";
                    if (j < query.getQueryDimension(i).size() - 1) {
                        select += ",";
                    }
                }
                select += ")";

            }
        }
        select += ";";
        System.out.println("Query:" + select);
        PreparedStatement pst = con.prepareStatement(select);
        ResultSet rst = pst.executeQuery();
        ParseDataCallbackHandler handler = new DefaultParseDataCallbackHandler();
        handler.headerParsed(SdmxIO.getBaseHeader());
        DataSetWriter w = handler.getDataSetWriter();
        w.newDataSet();
        while (rst.next()) {
            w.newObservation();
            for (int i = 1; i <= rst.getMetaData().getColumnCount(); i++) {
                String n = rst.getMetaData().getColumnName(i);
                String s = rst.getString(i);
                //System.out.println("n="+n+":s="+s);
                if (s != null && !"".equals(s) && !"".equals(n) && n != null) {
                    w.writeObservationComponent(n, s);
                }
            }
            w.finishObservation();
        }
        returnConnection(con);
        // streaming output writers return null at w.finishDataSet();
        DataSet ds = w.finishDataSet();
        DataMessage dm = new DataMessage();
        ArrayList list = new ArrayList<DataSet>();
        list.add(ds);
        dm.setDataSets(list);
        dm.setHeader(SdmxIO.getBaseHeader());
        Registry reg = SdmxGatewayApplication.getApplication().getRegistry();
        DataflowReference ref = DataflowReference.create(new NestedNCNameID(query.getProviderRef()), new IDType(query.getFlowRef()), Version.ONE);
        DataflowType flow = reg.find(ref);
        PostParseUtilities.setStructureReference(dm, flow.getStructure());
        handler.footerParsed(null);
        handler.documentFinished();
        return dm;
        }catch(SQLException sql) {
            throw new Error(sql);
        }
    }

    @Override
    public void query(Query query, ParseDataCallbackHandler handler) {
        try{
        List<FlatObs> result = new ArrayList<FlatObs>();
        Connection con = pool.getConnection();
        String select = "select * from `flow_" + query.getFlowRef() + "`";
        int count = 0;
        if (query.getQuerySize() > 0) {
            select += " where ";
            for (int i = 0; i < query.size() && query.getQueryDimension(i).size() > 0; i++) {
                if (count != 0 && count < query.size() - 1) {
                    select += " and ";
                }
                count += query.getQueryDimension(i).size();
                select += query.getQueryDimension(i).getConcept() + " in ";
                select += "(";
                for (int j = 0; j < query.getQueryDimension(i).size(); j++) {
                    select += "'" + query.getQueryDimension(i).getValues().get(j) + "'";
                    if (j < query.getQueryDimension(i).size() - 1) {
                        select += ",";
                    }
                }
                select += ")";

            }
        }
        select += ";";
        System.out.println("Query:" + select);
        PreparedStatement pst = con.prepareStatement(select);
        ResultSet rst = pst.executeQuery();
        BaseHeaderType header = SdmxIO.getBaseHeader();
        Registry reg = SdmxGatewayApplication.getApplication().getRegistry();
        DataflowReference ref = DataflowReference.create(new NestedNCNameID(query.getProviderRef()), new IDType(query.getFlowRef()), Version.ONE);
        DataflowType flow = reg.find(ref);
        PayloadStructureType payload = new PayloadStructureType();
        payload.setStructure(flow.getStructure());
        header.setStructures(new ArrayList<PayloadStructureType>());
        header.getStructures().add(payload);
        handler.headerParsed(header);
        DataSetWriter w = handler.getDataSetWriter();
        w.newDataSet();
        while (rst.next()) {
            w.newObservation();
            for (int i = 1; i <= rst.getMetaData().getColumnCount(); i++) {
                String n = rst.getMetaData().getColumnName(i);
                String s = rst.getString(i);
                //System.out.println("n="+n+":s="+s);
                if (s != null && !"".equals(s) && !"".equals(n) && n != null) {
                    w.writeObservationComponent(rst.getMetaData().getColumnName(i), s);
                }
            }
            w.finishObservation();
        }
        w.finishDataSet();
        returnConnection(con);
        // streaming output writers return null at w.finishDataSet();
        handler.footerParsed(null);
        handler.documentFinished();
        }catch(SQLException sql) {
            throw new Error(sql);
        }
    }

    public void insertDataflow(DataSet ds, String flow) throws SQLException {
        Connection con = pool.getConnection();
        String insert = "insert into `flow_" + flow + "`";
        ColumnMapper mapper = ds.getColumnMapper();
        String values = "(";
        String params = "(";
        for (int i = 0; i < mapper.size(); i++) {
            values += "`" + mapper.getColumnName(i) + "`";
            params += "?";
            if (mapper.size() - 1 > i) {
                values += ",";
                params += ",";
            }
        }
        values += ")";
        params += ")";
        insert += " " + values + " values " + params + ";";
        PreparedStatement pst = con.prepareStatement(insert);
        for (int i = 0; i < ds.size(); i++) {
            for (int j = 0; j < mapper.size(); j++) {
                pst.setString(j + 1, ds.getFlatObs(i).getValue(j));
            }
            pst.addBatch();
        }
        pst.executeBatch();
        returnConnection(con);
    }
}
