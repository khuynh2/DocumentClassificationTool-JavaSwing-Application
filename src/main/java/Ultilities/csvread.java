/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ultilities;

import Ontology.OntoBuild;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

public class csvread {

    private static final String CSV_FILE_PATH = "output_LDA\\Topic_Keyword.csv";
    private static final String CSV_FILE_PATH1 = "output_LDA\\Topic_Keyword.csv";

    // read values from csv file, then return these values in form of a matrix[row][col]
    public String[][] CSVread(int row, int col, String path) throws IOException {

        if (row <= 0) { //unable to determine the number of row 

            row = CSVrow(path) + 1;
        }
        col = col + 1;

        String[][] multD = new String[row][col];
       // File filepath = new File(path);
        
       

        try (
                Reader reader = Files.newBufferedReader(Paths.get(path));
                
                //InputStream in = getClass().getResourceAsStream(path); 
               // BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
                
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withIgnoreEmptyLines(true));) {

            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index

                int i = (int) csvParser.getRecordNumber() % row;

                System.out.println("read line i: " + i + "of" + path);
                for (int j = 0; j < col; j++) {

                    String temp = csvRecord.get(j);
                    if (temp.isEmpty()) {
                        break;
                    } else {
                        multD[i][j] = csvRecord.get(j);
                    }

                    System.out.println("[" + i + "]" + "[" + j + "]" + "=" + multD[i][j]);

                }

            }
        }
        return multD;
    }

    public static String TopicConvert(String num) {

        String topic = "Topic " + num + " ";

        return topic;
    }

    public static int CSVrow(String path) throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withIgnoreEmptyLines(true));
        int row = csvParser.getRecords().size();
        System.out.println(row);

        return row;
    }

    
    
    public static void PrintList(List<String> l) throws IOException {

        for (String str : l) {
            System.out.println(str);

        }

    }
    
     public static void PrintMap(Map<String,Integer> map) throws IOException {

        for (Map.Entry m : map.entrySet()) {
            
            System.out.println(m.getKey() + " " + m.getValue());

        }

    }

    public static void main(String[] args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        csvread csv=new csvread();
        //System.out.println(csvParser.getRecords().size());
        String Table[][] = csv.CSVread(0, 12, "output_LDA\\Document2.csv");

        //String Table1[][] = CSVread(0, 5, "output_LDA\\Document_Topic.csv");

        /* for (int i = 1; i < Table.length; i++) {
            for (int j = 0; j < Table[j].length; j++) {

                if (Table[i][j] == null) {
                } else {
                    System.out.println(Table[i][j]);
                }
            }

        }
        System.out.println();*/
        // CSVrow("output_LDA\\Document.csv");
        /* for (int i = 1; i < Table1.length; i++) {
            for (int j = 0; j < Table1[j].length; j++) {

                if (Table1[i][j] == null) {
                } else {
                    if(j==1){
                        
                    String filename = Table1[i][j].replace(".txt", "");
                    Table1[i][j] = filename.substring(filename.lastIndexOf("\\"));    
                        
                    String filename1 = Table1[i][j].replace(".txt", "");
                    Table1[i][j] = filename1.substring(filename1.lastIndexOf("/"));
                    
                 
                        
                    System.out.println("i: "+i);
                    System.out.println(Table1[i][j]);
                    System.out.println(Table1[i][j]);
                    
                    
                    
                    
                    }
                }
            }

        }
        System.out.println();*/
    }
}
