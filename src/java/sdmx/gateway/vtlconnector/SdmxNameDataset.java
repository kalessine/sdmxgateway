/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.vtlconnector;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.hamnaberg.jsonstat.util.IntCartesianProduct;
import no.ssb.vtl.model.Component;
import no.ssb.vtl.model.Component.Role;
import no.ssb.vtl.model.DataPoint;
import no.ssb.vtl.model.DataStructure;
import no.ssb.vtl.model.DataStructure.Builder;
import no.ssb.vtl.model.Order;
import no.ssb.vtl.model.VTLObject;
import sdmx.Queryable;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.cube.Cube;
import sdmx.cube.CubeObs;
import sdmx.cube.CubeObservation;
import sdmx.data.key.FullKey;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.querykey.impl.RegistryQuery;
import sdmx.structure.base.ItemSchemeType;
import sdmx.structure.base.NameableType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.structureddata.ValueTypeResolver;
import static sdmx.version.json.JSONStatWriter.toFullKey;

/**
 *
 * @author James
 */
public class SdmxNameDataset implements no.ssb.vtl.model.Dataset {

    private Queryable q = null;
    private Registry reg = null;
    private Repository rep = null;
    private DataflowType dataflow = null;

    private no.ssb.vtl.model.DataStructure dataStructure = null;

    Cube cube = null;
    int[] lengths = null;

    public SdmxNameDataset(Queryable q, DataflowType df, String query, String startPeriod, String endPeriod) {
        this.q = q;
        this.reg = q.getRegistry();
        this.rep = q.getRepository();
        this.dataflow = df;
        this.cube = new Cube(reg.find(df.getStructure()), reg);
        RegistryQuery rq = new RegistryQuery(reg.find(df.getStructure()), reg, this.dataflow.getId().toString());
        rq.fromString(query);
        System.out.println("RQ Query String=" + rq.getQueryString());
        rq.getQueryTime().parseStartTime(startPeriod);
        rq.getQueryTime().parseEndTime(endPeriod);
        DataMessage msg = null;
        try {
            msg = q.getRepository().query(rq);
        } catch (ParseException ex) {
            Logger.getLogger(SdmxNameDataset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SdmxNameDataset.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg.getDataSets().get(0).query(cube, null);
        DataStructureType struct = reg.find(dataflow.getStructure());
        lengths = new int[struct.getDataStructureComponents().getDimensionList().size() + 1];
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            lengths[i] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString()).size();
        }
        int timeDimensionIndex = struct.getDataStructureComponents().getDimensionList().size();// -1 +1 cancel each other out
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            lengths[timeDimensionIndex] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString()).size();
        }
        /*
        int measureDimensionIndex = lengths.length - 1;
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            lengths[lengths.length - 1] = cube.getValues(struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getId().toString()).size();
        }
         */
        System.out.println("Cube Size=" + cube.getSize());
        System.out.println("Flat Size=" + cube.getFlatSize());
    }

    /**
     * Creates a new independent, immutable stream of DataPoints.
     * <p>
     * Implementations can decide not to allow sorting by returning
     * {@link Optional#empty()}.
     * <p>
     * If supported, the {@link Spliterator} of the returned {@link Stream}
     * <b>must</b> be {@link Spliterator#SORTED} using the given {@link Order}.
     *
     * @param orders the order in which the {@link DataPoint}s should be
     * returned.
     * @param filtering the filtering on the {@link Component}s of the
     * {@link DataPoint}s
     * @return a <b>sorted</b> stream of {@link DataPoint}s if sorting is
     * supported.
     */
    public Stream<DataPoint> getData() {
        DataStructureType struct = this.reg.find(this.dataflow.getStructure());
        IntCartesianProduct cartesianProduct = new IntCartesianProduct(lengths);
        Stream<DataPoint> stream = Stream.generate(() -> {
            int[] result = null;
            CubeObs obs = null;
            while (obs == null) {
                result = cartesianProduct.next();
                obs = cube.find(toFullKey(result, struct, reg, cube));
            }
            return this.toDataPoint(obs);
        }).limit(cube.getSize());
        return stream;
    }

    @Override
    public Optional<Map<String, Integer>> getDistinctValuesCount() {
        return Optional.of(this.cube.getDistinctValuesCount());
    }

    @Override
    public Optional<Long> getSize() {
        return Optional.of(cube.getSize());
    }

    @Override
    public DataStructure getDataStructure() {
        if (this.dataStructure != null) {
            return this.dataStructure;
        }
        Builder b = DataStructure.builder();
        sdmx.structure.datastructure.DataStructureType data_struct = reg.find(dataflow.getStructure());
        for (int i = 0; i < data_struct.getDataStructureComponents().getDimensionList().size(); i++) {
            b.put(data_struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), Role.IDENTIFIER, String.class);
        }
        b.put(data_struct.getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString(), Role.IDENTIFIER, String.class);
        if (data_struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            ItemSchemeType cs = reg.find(data_struct.getDataStructureComponents().getDimensionList().getMeasureDimension().getLocalRepresentation().getEnumeration());
            for (int j = 0; j < cs.size(); j++) {
                b.put(NameableType.toString(cs.getItem(j)), Role.MEASURE, Number.class);
            }
        } else {
            b.put(data_struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().getId().toString(), Role.MEASURE, Number.class);
        }
        for (int k = 0; k < data_struct.getDataStructureComponents().getAttributeList().size(); k++) {
            b.put(data_struct.getDataStructureComponents().getAttributeList().getAttribute(k).getId().toString(), Role.ATTRIBUTE, String.class);
        }
        this.dataStructure = b.build();
        return this.dataStructure;
    }

    public static FullKey toFullKey(int[] result, DataStructureType struct, Registry reg, Cube cube) {
        LinkedHashMap<String, Object> key = new LinkedHashMap<String, Object>();
        int i = 0;
        for (; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            key.put(dim.getId().toString(), cube.getValues(dim.getId().toString()).get(result[i]));
            //System.out.print(dim.getId().toString()+":"+result[i]+":"+cube.getValues(dim.getId().toString()).get(result[i])+" ");
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType td = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            key.put(td.getId().toString(), cube.getValues(td.getId().toString()).get(result[i]));
            //System.out.println(td.getId().toString()+":"+result[i]+":"+cube.getValues(td.getId().toString()).get(result[i]));
        }
        /*
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType md = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
            key.put(md.getId().toString(), cube.getValues(md.getId().toString()).get(result[i++]));
        }*/
        return new FullKey(key);
    }

    public DataStructureType getDataStructureType() {
        return this.reg.find(this.dataflow.getStructure());
    }

    public DataPoint toDataPoint(CubeObs obs) {
        Object[] comps = getDataStructure().getRoles().keySet().toArray();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < obs.getColumnMapper().size(); i++) {
            if (getDataStructureType().getDataStructureComponents().getDimensionList().getTimeDimension().getId().toString().equals(obs.getColumnMapper().getColumnName(i))) {
                map.put(obs.getColumnMapper().getColumnName(i), obs.getValue(i));
                continue;
            }
            if (getDataStructureType().getDataStructureComponents().getDimensionList().getMeasureDimension() == null && obs.getColumnMapper().getColumnName(i).equals("OBS_VALUE")) {
                map.put("OBS_VALUE", obs.getValue(i));
                continue;
            }
            if (getDataStructureType().isAttribute(obs.getColumnMapper().getColumnName(i)) || getDataStructureType().isDimension(obs.getColumnMapper().getColumnName(i))) {
                map.put(obs.getColumnMapper().getColumnName(i), ValueTypeResolver.resolveCode(reg, reg.find(this.dataflow.getStructure()), obs.getColumnMapper().getColumnName(i), obs.getValue(i)));
                continue;
            }
            // Measure Dimension not found in SDMX File Structure
            ItemSchemeType cs = reg.find(getDataStructureType().getDataStructureComponents().getDimensionList().getMeasureDimension().getLocalRepresentation().getEnumeration());
            for (int j = 0; j < cs.size(); j++) {
                if (NameableType.toString(cs.getItem(j)).equals((String) comps[i])) {
                    map.put(NameableType.toString(cs.getItem(j)), (String) obs.getValue(i));
                }
            }
        }

        return this.dataStructure.fromStringMap(map);
    }
}
