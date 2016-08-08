
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.gateway.services.DatabaseRegistry;
import sdmx.message.DataMessage;
import sdmx.message.StructureType;
import sdmx.net.LocalRegistry;
import sdmx.net.service.sdw.Sdmx20SOAPQueryable;
import sdmx.structure.base.NameableType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.version.twopointone.writer.Sdmx21StructureWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author James
 */
public class LoadABSDotStat {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Start Test Database Registry");
        File f = new File(".\\test\\resources\\");
        if (!f.exists()) {
            f.mkdirs();
            Sdmx20SOAPQueryable q = new Sdmx20SOAPQueryable("ABS", "http://stat.abs.gov.au/sdmxws/sdmx.asmx");
            q.setSoapNamespace("http://stats.oecd.org/OECDStatWS/SDMX/");
            List<DataflowType> dfs = q.listDataflows();
            for (int i = 0; i < dfs.size(); i++) {
                System.out.println("Loading: " + NameableType.toString(dfs.get(i)));
                q.find(dfs.get(i).getStructure());
                List<StructureType> cache = q.getCache();
                Iterator<StructureType> it = cache.iterator();
                int j = 0;
                while (it.hasNext()) {
                    StructureType st = it.next();
                    it.remove();
                    FileOutputStream fos = null;
                    try {
                        String s;
                        s = convert(NameableType.toString(dfs.get(i)));
                        fos = new FileOutputStream(new File(f, s + ".xml"));
                        Sdmx21StructureWriter.write(st, fos);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LoadABSDotStat.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoadABSDotStat.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(LoadABSDotStat.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finish Test Database Registry");
    }

    @Test
    public void testLoadStructure() throws IOException, ParseException {
        File f = new File(".\\test\\resources\\");
        DatabaseRegistry dr = new DatabaseRegistry();
        loadFile(dr, f);
    }

    public void loadFile(DatabaseRegistry dr, File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                loadFile(dr, files[i]);
            }
        } else if (f.isFile() && f.getName().toLowerCase().endsWith(".xml")) {
            load(dr, f);
        }

    }

    public void load(DatabaseRegistry dr, File f) {
        System.out.println("Load:" + f.toString());
        try {
            FileInputStream fis = new FileInputStream(f);
            StructureType st = SdmxIO.parseStructure(fis);
            dr.load(st);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String convert(String s) {
        if (s.contains("/")) {
            s = s.replace("/", "-");
        }
        if (s.contains("?")) {
            s = s.replace("?", "-");
        }
        if (s.contains("\\")) {
            s = s.replace("\\", "-");
        }
        if (s.contains("#")) {
            s = s.replace("#", "-");
        }
        if (s.contains("*")) {
            s = s.replace("*", "-");
        }
        if (s.contains("=")) {
            s = s.replace("=", "-");
        }
        if (s.contains("<")) {
            s = s.replace("<", "-");
        }
        if (s.contains(">")) {
            s = s.replace(">", "-");
        }
        if (s.contains(":")) {
            s = s.replace(":", "-");
        }
        if (s.contains("|")) {
            s = s.replace("|", "-");
        }
        return s;
    }
}
