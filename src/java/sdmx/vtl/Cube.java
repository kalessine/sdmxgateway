/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.vtl;

import java.util.List;
import sdmx.data.DataSet;
import sdmx.data.flat.FlatObs;
import sdmx.data.key.FullKey;
import sdmx.data.key.Key;
import sdmx.querykey.QueryKey;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public interface Cube {
    public DataStructureType getStructure();
    public Cube query(QueryKey query);
    public FlatObs find(FullKey key);
    public List<String> getStructureValues(String col);
    public List<CodeType> getStructureCodes(String col);
    public List<String> getExistingValues(String col);
    public List<CodeType> getExistingCodes(String col);
    // # observations
    public int size(); 
}
