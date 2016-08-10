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
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.message.HeaderTimeType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.net.ServiceList;
import sdmx.net.list.DataProvider;
import sdmx.querykey.QueryKey;
import sdmx.structure.base.Component;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.util.QueryStringUtils;
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
    public DataMessage query(ParseParams pparams, DataQueryMessage message) throws ParseException, IOException {
        try {
            IDType flowid = message.getQuery().getDataWhere().getAnd().get(0).getDataflow().get(0).getMaintainableParentId();
            DataStructureType dst = null;
            DataflowReference dfref = DataflowReference.create(null, flowid, null);
            DataflowType df = reg.find(dfref);
            List<DataflowType> dataflowList = null;
            if (df == null) {
                dataflowList = reg.listDataflows();
                for (int i = 0; i < dataflowList.size(); i++) {
                    if (dataflowList.get(i).getId().equals(flowid)) {
                        DataStructureReference ref = DataStructureReference.create(dataflowList.get(i).getStructure().getAgencyId(), dataflowList.get(i).getStructure().getMaintainableParentId(), dataflowList.get(i).getStructure().getMaintainedParentVersion());
                        dst = reg.find(ref);
                    }
                }
            } else {
                dst = reg.find(df.getStructure());
            }
            DataStructureType structure = dst;
            QueryKey queryKey = new QueryKey(dst, reg, flowid.toString());
            for (int i = 0; i < queryKey.size(); i++) {
                Component dim = structure.getDataStructureComponents().findDimension(queryKey.getQueryDimension(i).getConcept());
                String concept = dim.getConceptIdentity().getId().toString();
                List<String> params = message.getQuery().getDataWhere().getAnd().get(0).getDimensionParameters(concept);
                if (params.size() > 0) {
                    for (int j = 0; j < params.size(); j++) {
                        queryKey.getQueryDimension(i).addValue(params.get(j));
                    }
                }
            }
            String startTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getStart().toString();
            String endTime = message.getQuery().getDataWhere().getAnd().get(0).getTimeDimensionValue().get(0).getEnd().toString();

            try {
                queryKey.getQueryTime().setStartTime(displayFormat.parse(startTime));
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseRepositoryFront.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                queryKey.getQueryTime().setEndTime(displayFormat.parse(endTime));
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseRepositoryFront.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataMessage msg = null;
            msg.setHeader(getBaseHeader());
            msg.setDataSets(Collections.singletonList(rep.queryDataflow(queryKey)));
            return msg;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseRepositoryFront.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public DataMessage query(ParseParams params, String query) throws ParseException, IOException {
        return query(params, QueryStringUtils.toDataQueryMessage(params, query));
    }

    public BaseHeaderType getBaseHeader() {
        BaseHeaderType header = new BaseHeaderType();
        header.setId("none");
        header.setTest(false);
        SenderType sender = new SenderType();
        sender.setId(new IDType("Sdmx-Sax"));
        header.setSender(sender);
        PartyType receiver = new PartyType();
        receiver.setId(new IDType("You"));
        header.setReceivers(Collections.singletonList(receiver));
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(DateTime.now());
        header.setPrepared(htt);
        return header;
    }
}
