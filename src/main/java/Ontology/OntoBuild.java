/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ontology;

import DB.dbSQLite;
import Ultilities.csvread;

import static Ultilities.csvread.TopicConvert;
import extract.Extractor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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

/**
 *
 * @author USER
 */
public class OntoBuild {

    private static final String OWL_PATH = "document.owl";
    private static final String base = "http://www.co-ode.org/ontologies/document/document.owl/";
    private static final String output_LDA = "output_LDA\\";

    public static void Build() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException, ClassNotFoundException, SQLException {

        File path = new File(OWL_PATH);
        System.out.println("Start Building Ontology!");

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        OWLOntology o = manager.createOntology();
        manager.saveOntology(o, new FunctionalSyntaxDocumentFormat(), new FileOutputStream(path));

        Set<OWLAxiom> domainsAndRanges = new HashSet<OWLAxiom>();

        //String base = "http://www.co-ode.org/ontologies/document/document.owl/";
        IRI documentIRI = IRI.create(path);

        PrefixManager pm = new DefaultPrefixManager(null, null, base);
        //Class
        OWLClass documentClass = dataFactory.getOWLClass(IRI.create(base + "#Document"));
        OWLClass topicClass = dataFactory.getOWLClass(IRI.create(base + "#Topic"));
        OWLClass keywordClass = dataFactory.getOWLClass(IRI.create(base + "#Keyword"));
        OWLClass authorClass = dataFactory.getOWLClass(IRI.create(base + "#Author"));

        //Object properties
        OWLObjectProperty madeup = dataFactory.getOWLObjectProperty(IRI.create(base + "#madeup"));
        OWLObjectProperty isMadeupof = dataFactory.getOWLObjectProperty(IRI.create(base + "#isMadeupof"));

        OWLObjectProperty describe = dataFactory.getOWLObjectProperty(IRI.create(base + "#describe"));
        OWLObjectProperty isDescribedin = dataFactory.getOWLObjectProperty(IRI.create(base + "#isDescribedin"));

        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));
        OWLObjectProperty isWrittenby = dataFactory.getOWLObjectProperty(IRI.create(base + "#isWrittenby"));

        OWLObjectProperty mention = dataFactory.getOWLObjectProperty(IRI.create(base + "#mention"));
        OWLObjectProperty isMentionedin = dataFactory.getOWLObjectProperty(IRI.create(base + "#isMentionedin"));

        //domain range
        AddDomainandRange(path, documentClass, topicClass, describe);
        AddDomainandRange(path, topicClass, documentClass, isDescribedin);
        AddDomainandRange(path, keywordClass, topicClass, madeup);
        AddDomainandRange(path, topicClass, keywordClass, isMadeupof);
        AddDomainandRange(path, authorClass, documentClass, write);
        AddDomainandRange(path, documentClass, authorClass, isWrittenby);

        BuildTopic_Word(10, 5, topicClass, keywordClass, isMadeupof, madeup);
        BuildDocument_Topic(documentClass, isDescribedin, describe, authorClass, isWrittenby, write);

    }

    //read Topic_Keyword.csv and then add individuals to Ontology 
    public static void BuildTopic_Word(int row, int col, OWLClass class1, OWLClass class2, OWLObjectProperty prop01, OWLObjectProperty prop1) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
         csvread csv =new csvread();
        row = (row > 5) ? row : 5;  //minimum number of topic is 5
        col = (col > 5) ? col : 5;  //minimum number of keyword is 5

        File path = new File(OWL_PATH);
        String csvTop_key[][] = csv.CSVread(row, col, output_LDA + "Topic_Keyword.csv");

        for (int i = 0; i < row; i++) {

            for (int j = 0; j <= col; j++) {

                if (j == 0) {
                    //add topic
                    IndividualAssertion(path, base, csvTop_key[i][j].replace(":", ""), class1);
                    //System.out.println("[" + i + "]" + "[" + j + "]" + "=" + csvTop_key[i][j]);
                } else {
                    //System.out.println("[" + i + "]" + "[" + j + "]" + "=" + csvTop_key[i][j]); 
                    //add topic, keyword and relation
                    IndividualAssertion(path, base, csvTop_key[i][j], class2);
                    ObjectPropertyAssertion(path, base, csvTop_key[i][0].replace(":", ""), csvTop_key[i][j], prop01, prop1);

                }

            }

        }
        System.out.println("Add Topic_Keyword");

    }
    //read Document_Topic.csv and then add individuals to Ontology, add Authors 

    public static void BuildDocument_Topic(OWLClass class1, OWLObjectProperty prop01, OWLObjectProperty prop1, OWLClass class2, OWLObjectProperty prop02, OWLObjectProperty prop2) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException, ClassNotFoundException, SQLException {
        dbSQLite db = new dbSQLite();
        csvread csv =new csvread();
        Extractor extract = new Extractor();
        // row = (row > 5) ? row : 5;
        // col = (col > 5) ? col : 5;
        File path = new File(OWL_PATH);
        String csvDoc_top[][] = csv.CSVread(0, 3, output_LDA + "Document_Topic.csv");
        //String Doc[][] = CSVread(0, 12, "output_LDA\\Document.csv");

        ArrayList<String[]> list = db.selecTableDoc();
        String Doc[][] = db.ArrayList2D(list);

        for (int i = 1; i < csvread.CSVrow(output_LDA + "Document_Topic.csv"); i++) {
           
                for (int j = 1; j <= 3; j++) {

                    if (j == 1) {

                        String filename = csvDoc_top[i][j].replace(".txt", "");
                       // csvDoc_top[i][j] = filename.substring(filename.lastIndexOf("/"));
                        System.out.print("row:");
                        System.out.println(i - 1);
                        //IndividualAssertion(path, base, csvDoc_top[i][j].replace("/", ""), class1);

                        IndividualAssertion(path, base, Doc[i - 1][0], class1); // add individual document 
                        //System.out.println("[" + i + "]" + "[" + j + "]" + "=" + csvDoc_top[i][j].replace("/", ""));

                    } else if (j == 2) {
                        //System.out.println("[" + i + "]" + "[" + j + "]" + "=" + TopicConvert(csvDoc_top[i][j]));

                        ObjectPropertyAssertion(path, base, TopicConvert(csvDoc_top[i][2]), Doc[i - 1][0], prop01, prop1);   //topic, doc,isDescribedin,describe

                    } else {

                    }

                }

                for (String str : extract.Author(Doc[i - 1][1])) {

                    IndividualAssertion(path, base, str, class2);
                    ObjectPropertyAssertion(path, base, Doc[i - 1][0], str, prop02, prop2);
                    System.out.println(str);

                }
            

        }
        System.out.println("Add Document_Topic");
    }

    public static void IndividualAssertion(File path, String base, String word, OWLClass Class) throws OWLOntologyCreationException, OWLOntologyStorageException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        OWLIndividual keyword1 = dataFactory.getOWLNamedIndividual(IRI.create(base + word));
        OWLClassAssertionAxiom ax1 = dataFactory.getOWLClassAssertionAxiom(Class, keyword1);
        manager.addAxiom(ontology, ax1);
        ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), IRI.create(path));

    }

    public static void ObjectPropertyAssertion(File path, String base, String w1, String w2, OWLObjectProperty prop01, OWLObjectProperty prop1) throws OWLOntologyCreationException, OWLOntologyStorageException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        OWLNamedIndividual word = dataFactory.getOWLNamedIndividual(IRI.create(base + w1));
        OWLNamedIndividual t = dataFactory.getOWLNamedIndividual(IRI.create(base + w2));

        OWLObjectPropertyAssertionAxiom ax01 = dataFactory.getOWLObjectPropertyAssertionAxiom(prop01, word, t);
        OWLObjectPropertyAssertionAxiom ax1 = dataFactory.getOWLObjectPropertyAssertionAxiom(prop1, t, word);

        manager.addAxiom(ontology, ax01);
        manager.addAxiom(ontology, ax1);

        ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), IRI.create(path));

    }

    public static void AddDomainandRange(File path, OWLClass ClassDomain, OWLClass ClassRange, OWLObjectProperty prop01) throws OWLOntologyCreationException, OWLOntologyStorageException {

        Set<OWLAxiom> domainsAndRanges = new HashSet<OWLAxiom>();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        domainsAndRanges.add(dataFactory.getOWLObjectPropertyDomainAxiom(prop01, ClassDomain));
        domainsAndRanges.add(dataFactory.getOWLObjectPropertyRangeAxiom(prop01, ClassRange));
        manager.addAxioms(ontology, domainsAndRanges);
        ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), IRI.create(path));

    }

    public static void main(String[] args) throws URISyntaxException,
            IOException,
            OWLOntologyCreationException,
            OWLOntologyStorageException,
            ClassNotFoundException,
            SQLException {

        //  Build();

        /*   File path = new File(OWL_PATH);
        System.out.println("Start Building Ontology!");

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        Set<OWLAxiom> domainsAndRanges = new HashSet<OWLAxiom>();

        //String base = "http://www.co-ode.org/ontologies/document/document.owl/";
        IRI documentIRI = IRI.create(path);

        PrefixManager pm = new DefaultPrefixManager(null, null, base);
        
        OWLClass documentClass = dataFactory.getOWLClass(IRI.create(base + "#Document"));
        OWLClass topicClass = dataFactory.getOWLClass(IRI.create(base + "#Topic"));
        OWLClass keywordClass = dataFactory.getOWLClass(IRI.create(base + "#Keyword"));

        //Object properties
        OWLObjectProperty madeup = dataFactory.getOWLObjectProperty(IRI.create(base + "#madeup"));
        OWLObjectProperty isMadeupof = dataFactory.getOWLObjectProperty(IRI.create(base + "#isMadeupof"));

        OWLObjectProperty describe = dataFactory.getOWLObjectProperty(IRI.create(base + "#describe"));
        OWLObjectProperty isDescribedin = dataFactory.getOWLObjectProperty(IRI.create(base + "#isDescribedin"));
        
        
         BuildDocument_Topic(documentClass,isDescribedin,describe);
        
         */
    }

}
