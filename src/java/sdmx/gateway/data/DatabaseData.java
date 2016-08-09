/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.data;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatDataSet;
import sdmx.data.flat.FlatDataSetWriter;
import sdmx.data.flat.FlatObs;
import sdmx.message.DataStructure;
import sdmx.querykey.QueryKey;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;

/**
 *
 * @author James
 */
public class DatabaseData {

    PoolingDataSource<Connection> pool;

    public DatabaseData() {
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
            if (struct.getDataStructureComponents().getDimensionList().size()-1 > i) {
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
        create+=",";
        PrimaryMeasure pm = struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure();
        create += "`" + pm.getId().toString() + "` VARCHAR(50)";
        create += ");";
        Connection con = pool.getConnection();
        System.out.println(create);
        PreparedStatement pst = con.prepareStatement(create);
        pst.executeUpdate();
        returnConnection(con);
    }

    public void deleteDataflow(DataflowType df) {

    }

    public FlatDataSet queryDataflow(QueryKey query) throws SQLException {
        List<FlatObs> result = new ArrayList<FlatObs>();
        Connection con = pool.getConnection();
        String select = "select * from `flow_" + query.getDataflowId() + "`";
        if (query.size() > 0) {
            select += " where ";
            for (int i = 0; i < query.size(); i++) {
                select += query.getQueryDimension(i) + " in ";
                select += "(";
                for (int j = 0; j < query.getQueryDimension(i).size(); j++) {
                    select += "'" + query.getQueryDimension(i).getValues().get(j) + "'";
                }
                if (i < query.size()) {
                    select += " and ";
                }
            }
        }
        select += ";";
        PreparedStatement pst = con.prepareStatement(select);
        ResultSet rst = pst.executeQuery();
        FlatDataSetWriter w = new FlatDataSetWriter();
        while (rst.next()) {
            for (int i = 0; i < rst.getMetaData().getColumnCount(); i++) {
                w.newObservation();
                w.writeObservationComponent(rst.getMetaData().getColumnName(i), rst.getString(i));
                w.finishObservation();
            }
        }
        returnConnection(con);
        return w.finishDataSet();
    }

    public void insertDataflow(FlatDataSet ds, String flow) throws SQLException {
        Connection con = pool.getConnection();
        String insert = "insert into `flow_" + flow + "`";
        ColumnMapper mapper = ds.getColumnMapper();
        String values = "(";
        String params = "(";
        for (int i = 0; i < mapper.size(); i++) {
            values += "`" + mapper.getColumnName(i) + "`";
            params += "?";
            if (mapper.size()-1 > i) {
                values += ",";
                params += ",";
            }
        }
        values += ")";
        params += ")";
        insert += " " + values + " values " + params+";";
        for (int i = 0; i < ds.size(); i++) {
            System.out.println(insert);
            PreparedStatement pst = con.prepareStatement(insert);
            for(int j=1;j<mapper.size()+1;j++) {
                pst.setString(j, ds.getFlatObs(i).getValue(j-1));
            }
            pst.executeUpdate();
            pst.close();
        }
        returnConnection(con);
    }
}
