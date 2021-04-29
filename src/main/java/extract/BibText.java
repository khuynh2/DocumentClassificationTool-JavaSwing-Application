/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extract;



import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * An example of usage of Grobid. Extract and normalize the header of a PDF file and export it in BibTex.
 *
 * @author Patrice Lopez
 */
import org.apache.commons.io.FileUtils;
import org.grobid.core.data.BibDataSet;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * An example of usage of Grobid. Extract and normalize the header of a PDF file and export it in BibTex.
 *
 * @author Patrice Lopez
 */
public class BibText {
    private static Engine engine = null;

    public BibText() {
        try {
            // context variable are read from the project property file grobid-example.properties
            Properties prop = new Properties();
            //prop.load(new FileInputStream("grobid-example.properties"));
           
            //ClassLoader classLoader = getClass().getClassLoader();
            //InputStream in = classLoader.getResourceAsStream("grobid.properties");
            //prop.load(in);
            
            prop.load(new FileInputStream("grobid-home\\config\\grobid.properties"));
            
            String pGrobidHome = "grobid-home";
            GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
            GrobidProperties.getInstance(grobidHomeFinder);

            System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());

            engine = GrobidFactory.getInstance().createEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String runGrobid(File pdfFile, boolean consolidate) {
        StringBuilder bibtex = new StringBuilder();
        try {
          
                // Biblio object for the result
                BiblioItem resHeader = new BiblioItem();
                engine.processHeader(pdfFile.getPath(), consolidate, resHeader);
                bibtex.append(resHeader.toBibTeX());
          
        } catch (Exception e) {
            // If an exception is generated, print a stack trace
            e.printStackTrace();
        }
        return bibtex.toString();
    }

    public void close() {
    }
    
    
      public void Grobidsetup(String pdfPath,String consolidation, boolean consolidate) {
      
        String bibPath= pdfPath+"\\bib";
        File pdfFile = new File(pdfPath);
        File bibFile = new File(bibPath);
        new File(bibPath).mkdirs();

        List<File> filesToProcess = new ArrayList<File>();
        if (pdfFile.isFile()) {
            filesToProcess.add(pdfFile);
        } else if (pdfFile.isDirectory()) {
            if (!bibFile.exists()) {
                System.err.println("Path does not exist: " + bibPath);
                System.exit(0);
            }

            if (!bibFile.isDirectory()) {
                System.err.println("BibTex path is not a directory: " + bibPath);
                System.exit(0);
            }

            File[] refFiles = pdfFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".pdf") || name.endsWith(".PDF");
                }
            });

            if (refFiles == null) {
                System.err.println("No PDF file to be processed under directory: " + pdfPath);
                System.exit(0);
            }

            for (int i = 0; i < refFiles.length; i++) {
                filesToProcess.add(refFiles[i]);
            }
        }

     
        try {
            for (File fileToProcess : filesToProcess) {
                String result = runGrobid(fileToProcess, consolidate);
                if (!bibFile.exists() || bibFile.isFile())
                    FileUtils.writeStringToFile(bibFile, result, "UTF-8");
                else {
                    File theBibFile = new File(bibFile.getPath() + "/" +
                            fileToProcess.getName().replace(".pdf", ".txt").replace(".PDF", ".txt"));
                    FileUtils.writeStringToFile(theBibFile, result, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     *
     */
    public static void main(String[] args) {
 
        String pdfPath = "D:\\Document\\thesis\\paper\\format\\springer";
       
        String consolidation = null;
        boolean consolidate = false;
        
        
      //  BibText bib =new BibText();
       // bib.Grobidsetup(pdfPath,null,false);
        
        
    
    }
}