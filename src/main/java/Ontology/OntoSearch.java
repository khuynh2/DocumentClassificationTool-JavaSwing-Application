/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ontology;

import DB.dbSQLite;
import Ultilities.CSV;
import Ultilities.csvread;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.lang.reflect.*;

/**
 *
 * @author USER
 */
public class OntoSearch {

    private static final String OWL_PATH = "document.owl";
    private static final String base = "http://www.co-ode.org/ontologies/document/document.owl/";

    public  HashMap<String, Integer> Search(String input) throws FileNotFoundException, IOException, OWLOntologyCreationException, InterruptedException, OWLOntologyStorageException, ClassNotFoundException, SQLException {
        Set<String> kws = new HashSet<String>();
        Set<String> org = new HashSet<String>();
        File path = new File(OWL_PATH);
        OntoSearch a = new OntoSearch();
        dbSQLite db = new dbSQLite();

        List<String> key = new ArrayList<String>();
        List<String> author = new ArrayList<String>();
        List<String> title = new ArrayList<String>();
        List<String> abs = new ArrayList<String>();

        List<String> keySyn = new ArrayList<String>();
        List<String> titleSyn = new ArrayList<String>();

        List<String> Reccomedation = new ArrayList<String>();
        List<String> ReccomedationBA = new ArrayList<String>();

        for (String s : removeStopword(input)) {

            kws.addAll(a.getSynonyms(a.Stem(s)));
            org.addAll(a.Stem(s));
        }
        //
        System.out.println("Search base on synonym");
        for (String s : kws) { //Synonyms +original words

            keySyn.addAll(SearchKeyword(s));
      
            titleSyn.addAll(db.searchTitle(s));
            

        }
        //
        System.out.println("Search base on original input");
        for (String s : org) { //original words

            key.addAll(SearchKeyword(s));
            author.addAll(searchAuthor(path, s));

            title.addAll(db.searchTitle(s));
            abs.addAll(db.searchAbstract(s));
            
            ReccomedationBA.addAll(BestAuthorRecommendation(s));
        

        }

        Reccomedation.addAll(DocumentRecommendation(key));
        
        //Print Result :
        //onto
     /*   csvread u =new csvread();
        System.out.println("***key list:");
        u.PrintList(key);
        
        System.out.println("***keySyn list:");
        u.PrintList(keySyn);
        
        System.out.println("***author list:");
        u.PrintList(author);
        
        System.out.println("***Reccomedation list:");
        u.PrintList(Reccomedation);
        
        System.out.println("**8ReccomedationBA list:");
        u.PrintList(ReccomedationBA);
        
        //db search
    
        System.out.println("***title list:");
        u.PrintList(title);
        
        System.out.println("***titleSyn list:");
        u.PrintList(titleSyn);
        
        System.out.println("***abs list:");
        u.PrintList(abs);
        */
        
        
        

        HashMap<String, Integer> occur = new HashMap<String, Integer>();
        System.out.println("         Score calculation:");
        
        //Scoring based on occurence rate
        Occurrence(occur, key, 3,"Keyword");
        Occurrence(occur, author, 3,"Author");    //3

        Occurrence(occur, title, 2, "Title");
        Occurrence(occur, abs, 1, "Abstract");    //2

        Occurrence(occur, keySyn, 1, "keywordSyn");
        Occurrence(occur, titleSyn, 1, "titleSyn");

        Occurrence(occur, Reccomedation, 1, "Reccomedation");
        Occurrence(occur, ReccomedationBA, 1, "ReccomedationBA");

        float sum = 0.0f;
      //  float sum2= 0.0f;
        for (float f : occur.values()) {
            sum += f;
        }
        float mean = sum / occur.size();
       

        //remove all result with value < mean
        occur.entrySet().removeIf(e -> (int) ((Entry) e).getValue() < mean);
        
       /* for (float f : occur.values()) {
            sum2 += f;
        }
        float mean2 = sum2 / occur.size();*/
      
        //sort map by value 
        //occur=occur.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Mean:" + mean);
        
        
     //remove all result with value < mean2
     //   occur.entrySet().removeIf(e -> (int) ((Entry) e).getValue() < mean2);
     //   System.out.println("Mean2:" + mean2);

        dbSQLite.createTableSearchResult();
        db.DeleteSearchResult();

        for (Map.Entry m : occur.entrySet()) {

            System.out.println(m.getKey() + " " + m.getValue());
            db.insertSearchResult(m.getKey().toString(), m.getValue().toString());

            /*   try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("test.txt", true), "utf-8"))) {

                writer.write(m.getKey() + " " + m.getValue());
                writer.write("\n");
                writer.flush();
                writer.close();

            }*/
        }

        return occur;
    }

    //return Set of stopword form en.txt
    public  Set<String> stopwordList() throws FileNotFoundException, IOException {
        Set<String> stopwordList = new LinkedHashSet<String>();
        String line = null;
        
        //FileReader fileReader = new FileReader("en.txt");
    
        ClassLoader classLoader = getClass().getClassLoader(); 
        InputStream in = classLoader.getResourceAsStream("en.txt");
        
        BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
        while ((line = bfr.readLine()) != null) {
            String[] words = line.split("\n");
            for (String word : words) {
                //System.out.println(word);
                stopwordList.add(word);
            }
        }

        return stopwordList;
    }

    // remove  special character, convert to lowercase, remove stopwords
    public  Set<String> removeStopword(String input) throws FileNotFoundException, IOException {

        String[] words = input.replaceAll("[^a-zA-Z]+", " ").toLowerCase().split(" ");
        Set<String> wordList = new LinkedHashSet<String>(Arrays.asList(words));

        wordList.removeAll(stopwordList());
        //System.out.println(wordsList);
        System.out.println("Remove stopword finished :" + wordList);
        return wordList;
    }

    //search ontology for individuals whom associate with "word" through "properties"
    public static String[] getIndividual(File path, String word, OWLObjectProperty prop) throws OWLOntologyCreationException, OWLOntologyStorageException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);
        PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);

        OWLNamedIndividual indv = dataFactory.getOWLNamedIndividual(IRI.create(base + word));

        NodeSet<OWLNamedIndividual> topic = reasoner.getObjectPropertyValues(indv, prop);
        Set<OWLNamedIndividual> values = topic.getFlattened();
        String[] individual = new String[values.size()];
        int i = 0;
        System.out.println("The [" + prop.getIRI().getShortForm() + "] property values for [" + word + "] are: ");
        for (OWLNamedIndividual ind : values) {

            //System.out.println("    " + ind.getIRI().toString().replace(base, ""));
            individual[i] = ind.getIRI().toString().replace(base, "");

            System.out.println("    " + individual[i]);

            i++;

        }

        return individual;

    }

    // get all individuals associated with class
    private static Set<String> getIndividualsByclass(OWLOntology ontology, String owlClass) {
        String base = "http://www.co-ode.org/ontologies/document/document.owl/";
        OWLReasonerFactory reasonerFactory = new com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory();
        Set<String> ClassInd = new LinkedHashSet<String>();

        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
        for (OWLClass c : ontology.getClassesInSignature()) {
            if (c.getIRI().getShortForm().equals(owlClass)) {
                NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
                System.out.println("Class : " + c.getIRI().getShortForm());
                for (OWLNamedIndividual i : instances.getFlattened()) {
                    //System.out.println(i.getIRI().toString().replaceAll(base, ""));
                    ClassInd.add(i.getIRI().toString().replaceAll(base, ""));

                }
            }
        }

        return ClassInd;
    }

    //keyword madeup topic isdescribein doc
    public static Set<String> SearchKeyword(String word) throws OWLOntologyCreationException, OWLOntologyStorageException {
        System.out.print("Search Keyword******");
        File path = new File(OWL_PATH);

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        // load the ontology to the reasoner
        PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);
        reasoner.precomputeInferences();
        OWLObjectProperty madeup = dataFactory.getOWLObjectProperty(IRI.create(base + "#madeup"));
        OWLObjectProperty isMadeupof = dataFactory.getOWLObjectProperty(IRI.create(base + "#isMadeupof"));

        OWLObjectProperty describe = dataFactory.getOWLObjectProperty(IRI.create(base + "#describe"));
        OWLObjectProperty isDescribedin = dataFactory.getOWLObjectProperty(IRI.create(base + "#isDescribedin"));

        OWLNamedIndividual search = dataFactory.getOWLNamedIndividual(IRI.create(base + word));
        Set<String> matchesSK = new LinkedHashSet<String>();
        for (String i : getIndividual(path, word, madeup)) {

            //top isdescribein doc
            String doc[] = getIndividual(path, i, isDescribedin);
            for (String s : doc) {
                matchesSK.add(s);

            }

        }
        return matchesSK;
    }

    public static List<String> DocumentRecommendation(List<String> matchesSK) throws OWLOntologyCreationException, OWLOntologyStorageException {
        System.out.println("*****Document Recommendation******");
        File path = new File(OWL_PATH);
        //Set<String> temp = new LinkedHashSet<String>();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        // load the ontology to the reasoner
        PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);
        reasoner.precomputeInferences();

        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));
        OWLObjectProperty isWrittenby = dataFactory.getOWLObjectProperty(IRI.create(base + "#isWrittenby"));

     
        Set<String> Docset = new HashSet<String>(matchesSK);
        //Set<String> DocRec = new HashSet<String>();
        List<String> DocRec = new ArrayList<String>();
        
        for (String doc : Docset) {
            OWLNamedIndividual search = dataFactory.getOWLNamedIndividual(IRI.create(base + doc));
            for (String a : getIndividual(path, doc, isWrittenby)) {

                String Doc[] = getIndividual(path, a, write);
                for (String s : Doc) {
                    
                    if(s.equals(doc)){
                        
                          System.out.println("already exist !!!!!!   s:  "+s +"   doc:"+doc);                    
                    } 
                    else{
                    DocRec.add(s);
                    }
                    
                }

            }
        }

        System.out.println("*****Document Recommendation end*****");
        return DocRec;
    }

    public static Set<String> BestAuthorRecommendation(String word) throws OWLOntologyCreationException, OWLOntologyStorageException, ClassNotFoundException {
        System.out.print("BestAuthor Recommendation:");
        File path = new File(OWL_PATH);
        dbSQLite db = new dbSQLite();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        // load the ontology to the reasoner
        PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);
        reasoner.precomputeInferences();
        OWLObjectProperty madeup = dataFactory.getOWLObjectProperty(IRI.create(base + "#madeup"));
        OWLObjectProperty isMadeupof = dataFactory.getOWLObjectProperty(IRI.create(base + "#isMadeupof"));

        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));
        OWLObjectProperty isWrittenby = dataFactory.getOWLObjectProperty(IRI.create(base + "#isWrittenby"));

        OWLNamedIndividual search = dataFactory.getOWLNamedIndividual(IRI.create(base + word));
        Set<String> matchesSK = new LinkedHashSet<String>();
        for (String i : getIndividual(path, word, madeup)) {

            String[] authors = db.selectTopicBestAuthor(i);

            for (String a : authors) {
                
                String doc[] = getIndividual(path, a.trim(), write);
                for (String s : doc) {
                    matchesSK.add(s);

                }
            }

            /*  String doc[] = getIndividual(path, i, isDescribedin);
            for (String s : doc) {
                matchesSK.add(s);

            }*/
        }
        return matchesSK;
    }

    public static Set<String> searchAuthor(File path, String input) throws FileNotFoundException, IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        System.out.print("Search Author******");
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));

        Set<String> Author = getIndividualsByclass(ontology, "Author");
        Set<String> matchesA = new LinkedHashSet<String>();

        for (String s : Author) {

            if (StringUtils.containsIgnoreCase(s, input)) {
                //System.out.println(s);
                String doc[] = getIndividual(path, s, write);
                for (String s1 : doc) {
                    matchesA.add(s1);

                }

            }

        }
        return matchesA;
    }

    //search document  title using pellet reasoner (use normal db search instead)
    public static Set<String> searchTitle(File path, String input) throws FileNotFoundException, IOException, OWLOntologyCreationException, OWLOntologyStorageException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        Set<String> Title = getIndividualsByclass(ontology, "Document");
        Set<String> matchesT = new LinkedHashSet<String>();

        for (String s : Title) {
            if (StringUtils.containsIgnoreCase(s, input)) {
                //System.out.println(s);
                matchesT.add(s);

            }

        }
        return matchesT;
    }

    //Stem input keep both input and stem version
    public Set<String> Stem(String input) throws IOException {
        
        Path cdir = FileSystems.getDefault().getPath("dict").toAbsolutePath();
        String path = cdir.toString();

        //IRAMDictionary dict = new RAMDictionary(new File(path).toURI().toURL());
        IDictionary dict = new Dictionary(new File(path).toURI().toURL());

        dict.open();

        WordnetStemmer stemmer = new WordnetStemmer(dict);
        Set<String> SearchWord = new HashSet<String>();
        SearchWord.add(input);

        for (String s : stemmer.findStems(input, null)) {
            SearchWord.add(s);
        }
        System.out.println("Finish Stemming"+SearchWord);
        return SearchWord;
    }

    public Set<String> getSynonyms(Set<String> SearchWord) throws IOException, InterruptedException {
        
        Path cdir = FileSystems.getDefault().getPath("dict").toAbsolutePath();
        String path = cdir.toString();
        
        Set<String> Synonyms = new HashSet<String>();
        Set<String> temp = new HashSet<String>();

        //IRAMDictionary dict = new RAMDictionary(new File(path).toURI().toURL());
        IDictionary dict = new Dictionary(new File(path).toURI().toURL());

        dict.open();

        for (String s : SearchWord) {
            for (POS p : POS.values()) {
                IIndexWord idxWord = dict.getIndexWord(s, p);
                if (idxWord != null) {
                    for (IWordID wordID : idxWord.getWordIDs()) {
                        IWord word = dict.getWord(wordID);

                        ISynset synset = word.getSynset();
                        for (IWord w : synset.getWords()) {
                            temp.add(w.getLemma());
                        }
                    }
                }
            }
        }
        Synonyms.addAll(temp);
        Synonyms.removeAll( SearchWord);
        System.out.println("Synonyms: " + Synonyms);
        return Synonyms;
    }

    //Compare set, if similar gain point
    public static HashMap<String, Integer> Occurrence(HashMap<String, Integer> occur, List<String> org, int point, String Listname) throws IOException {
         csvread u =new csvread();
         System.out.println("********Adding ["  +  Listname  +  "] SCORE********");
        // HashMap<String, Integer> occur = new HashMap<String, Integer>();
        for (String a : org) {
            // if (resultFull.contains(a)) {

            if (occur.containsKey(a)) {
               
                occur.put(a, occur.get(a) + point);
              
            } else {
                //System.out.println(a);
                occur.put(a, point);
            }
            // }
        }
        
        u.PrintMap(occur);
         System.out.println();

        return occur;
    }

    //get topic->author with weight per occur, use for finding best author
    public static HashMap<String, Integer> TopicAuthor(String Topicname) throws OWLOntologyCreationException, OWLOntologyStorageException {

        File path = new File(OWL_PATH);
        HashMap<String, Integer> topic = new HashMap<String, Integer>();

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        // load the ontology to the reasoner
        PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(ontology);
        reasoner.precomputeInferences();

        OWLObjectProperty describe = dataFactory.getOWLObjectProperty(IRI.create(base + "#describe"));
        OWLObjectProperty isDescribedin = dataFactory.getOWLObjectProperty(IRI.create(base + "#isDescribedin"));
        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));
        OWLObjectProperty isWrittenby = dataFactory.getOWLObjectProperty(IRI.create(base + "#isWrittenby"));

        OWLNamedIndividual search = dataFactory.getOWLNamedIndividual(IRI.create(base + Topicname));

        for (String i : getIndividual(path, Topicname, isDescribedin)) {

            String doc[] = getIndividual(path, i, isWrittenby);
            for (String s : doc) {
                //matchesSK.add(s);

                if (topic.containsKey(s)) {

                    topic.put(s, topic.get(s) + 1);
                } else {
                    System.out.println(s);
                    topic.put(s, 1);
                }

            }

        }

        return topic;
    }

    public HashMap<String, Integer> getTopicAuthor(String topic) throws OWLOntologyCreationException, OWLOntologyStorageException {

        HashMap<String, Integer> map = TopicAuthor(topic);

        return map;
    }

    public List<String> findBestAuthor(HashMap<String, Integer> map) throws OWLOntologyCreationException, OWLOntologyStorageException {
        
        if(map.isEmpty()){
        System.out.println("No best authors");
        return null;
        }
        
        int max = Collections.max(map.values());
        List<String> keys = new ArrayList<>();
        if (max > 1) {

            for (Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == max) {
                    keys.add(entry.getKey());
                }
            }

            System.out.println("Best author: " + keys);
        } else {
            System.out.println("don't have enough information");
        }

        return keys;
    }

    //main class for finding dest author, insert best author to database
    public void BestAuthor() throws OWLOntologyCreationException, OWLOntologyStorageException, ClassNotFoundException, IOException, SQLException {
        File path = new File(OWL_PATH);

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        OntoSearch a = new OntoSearch();
        dbSQLite db = new dbSQLite();
        db.DeleteBestAuthor();
        db.createTableBestAuthor();

        for (String topic : getIndividualsByclass(ontology, "Topic")) {

            System.out.println(topic);
            String temp = a.findBestAuthor(a.getTopicAuthor(topic)).toString();
            if (temp == "[]") {
                db.insertBestAuthor(topic, "0");

            } else {

                db.insertBestAuthor(topic, temp.replaceAll("\\[", "").replaceAll("\\]", ""));
            }

        }

    }

    public static void main(String[] args) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException, InterruptedException, FileNotFoundException, ClassNotFoundException, SQLException {

        Set<String> list = new HashSet<String>();

        OntoSearch a = new OntoSearch();

        /*    // String w = "dogs";
        //list.addAll(a.Stem("dog"));
        // list.addAll(a.Stem("cat"));
        // a.getSynonyms(list);
        for (String s : removeStopword("math egg kitten yolo doom text picture")) {

            list.addAll(a.getSynonyms(a.Stem(s)));
        }

        for (String s : list) {

            SearchKeyword(s);
        }*/
        //Search("cat dog house food");
        File path = new File("D:\\Document\\thesis\\paper\\format\\springer\\mallet\\test5.owl");
        String base = "http://www.co-ode.org/ontologies/document/document.owl/";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(path);

        /*   String input = "software";

        OWLObjectProperty write = dataFactory.getOWLObjectProperty(IRI.create(base + "#write"));

        //System.out.println(getIndividualsByclass(ontology,"Author"));
        Set<String> Author = getIndividualsByclass(ontology, "Author");
        Set<String> Title = getIndividualsByclass(ontology,"Document");

        Set<String> matchesA = new LinkedHashSet<String>();
        Set<String> matchesT = new LinkedHashSet<String>();

       

        for (String s : Author) {

            if (StringUtils.containsIgnoreCase(s, input)) {
                System.out.println(s);
                String output[] = getIndividual(path, s, write);
                for (String s1 : output) {
                    matchesA.add(s1);

                }

            }

        }
        
        
  
          
          
          
           for (String s : Title) {
              if (StringUtils.containsIgnoreCase(s, input)) {
                System.out.println(s);
                matchesT.add(s);
              
              }
           
           
           }
           
           
           System.out.println(matchesT);*/
        //searchTitle(path,"Text");
        //SearchKeyword("food");
        /*  list=Search("cat Luca food math");
        
        for(String h:list){
        System.out.println(h);
        }
         */
        CSV c = new CSV();

        //  Search("procrastination");

        /*          dbSQLite db=new dbSQLite();
              db.createTableBestAuthor();
        String str = "";

        for (String topic : getIndividualsByclass(ontology, "Topic")) {
            //  HashMap<String, Integer> map = TopicAuthor("Topic 8 ");

            System.out.println(topic);
            String temp = a.findBestAuthor(a.getTopicAuthor(topic)).toString();
            if (temp=="[]") {
                db.insertBestAuthor(topic, "0");
              
            } else{
              
                db.insertBestAuthor(topic, temp.replaceAll("\\[", "").replaceAll("\\]",""));
            }

        }*/
        //System.out.println(str);

        /*  
        
        for (Map.Entry m : map.entrySet()) {

            System.out.println(m.getKey() + " " + m.getValue());
            // db.insertSearchResult(m.getKey().toString(), m.getValue().toString());
        }

        int max = Collections.max(map.values());
        if (max > 1) {
            List<String> keys = new ArrayList<>();
            for (Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == max) {
                    keys.add(entry.getKey());
                }
            }

            System.out.println(keys);
        } else {
            System.out.println("don't have enough information");
        }
        
         */
        //a.BestAuthor();
  
        
      
      
  //    System.out.println(BestAuthorRecommendation("ontology"));
        
     /*   HashMap<String, Integer> map = TopicAuthor("Topic 2 ");
        
        
        
              for (Map.Entry m : map.entrySet()) {

            System.out.println(m.getKey() + " score: " + m.getValue());
          
        }*/
        
    }

}
