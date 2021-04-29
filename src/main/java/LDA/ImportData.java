/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LDA;


import java.io.*;
import java.util.*;
import java.util.regex.*;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.types.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

public class ImportData {

    Pipe pipe;

    
    
    
    public ImportData() throws FileNotFoundException {
        pipe = buildPipe();
    }

    public Pipe buildPipe() throws FileNotFoundException {
        ArrayList pipeList = new ArrayList();

        // Read data from File objects
        pipeList.add(new Input2CharSequence("UTF-8"));

        // Regular expression for what constitutes a token.
        //  This pattern includes Unicode letters, Unicode numbers, 
        //   and the underscore character. Alternatives:
        //    "\\S+"   (anything not whitespace)
        //    "\\w+"    ( A-Z, a-z, 0-9, _ )
        //    "[\\p{L}\\p{N}_]+|[\\p{P}]+"   (a group of only letters and numbers OR
        //                                    a group of only punctuation marks)
        Pattern tokenPattern =
            Pattern.compile("[\\p{L}\\p{N}_]+");

        // Tokenize raw strings
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));

        // Normalize all tokens to all lowercase
        pipeList.add(new TokenSequenceLowercase());

        // Remove stopwords from a standard English stoplist.
        //  options: [case sensitive] [mark deletions]
      
        // pipeList.add(new TokenSequenceRemoveStopwords(false, false));
       // pipeList.add( new TokenSequenceRemoveStopwords(new File("en.txt"), "UTF-8", false, false, false) );
         ClassLoader classLoader = getClass().getClassLoader(); 
        // File f = new File(classLoader.getResource("en.txt").getFile());
         
        
         InputStream in = classLoader.getResourceAsStream("en.txt");
         File file =new File("en2.txt");
         OutputStream out = new FileOutputStream(file);
        try {
            IOUtils.copy(in, out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ImportData.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
         pipeList.add( new TokenSequenceRemoveStopwords(file, "UTF-8", false, false, false) );
    
        // Rather than storing tokens as strings, convert 
        //  them to integers by looking them up in an alphabet.
        pipeList.add(new TokenSequence2FeatureSequence());

        // Do the same thing for the "target" field: 
        //  convert a class label string to a Label object,
        //  which has an index in a Label alphabet.
        pipeList.add(new Target2Label());

        // Now convert the sequence of features to a sparse vector,
        //  mapping feature IDs to counts.
       // pipeList.add(new FeatureSequence2FeatureVector());

        // Print out the features and the label
        pipeList.add(new PrintInputAndTarget());
        
    
        

        return new SerialPipes(pipeList);
    }

    public InstanceList readDirectory(File directory) {
        return readDirectories(new File[] {directory});
    }

    public InstanceList readDirectories(File[] directories) {
        
        // Construct a file iterator, starting with the 
        //  specified directories, and recursing through subdirectories.
        // The second argument specifies a FileFilter to use to select
        //  files within a directory.
        // The third argument is a Pattern that is applied to the 
        //   filename to produce a class label. In this case, I've 
        //   asked it to use the last directory name in the path.
        FileIterator iterator =
            new FileIterator(directories,
                             new TxtFilter(),
                             FileIterator.LAST_DIRECTORY);

        // Construct a new instance list, passing it the pipe
        //  we want to use to process instances.
        InstanceList instances = new InstanceList(pipe);

        // Now process each instance provided by the iterator.
        instances.addThruPipe(iterator);

        return instances;
    }
    
        public static void ImportData(File directory) throws FileNotFoundException {
            
         File path = new File(directory + "\\bib\\output_abs\\");
         File path1 = new File("importData.mallet");
        
         System.out.println("ImportData from:" + path);
         System.out.println("**************************");
         
        ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(path);
        
        instances.save(path1);
            
        
    }

    public static void main (String[] args) throws IOException {

         //File path = new File("D:\\Document\\thesis\\paper\\format\\springer\\output_abstract");
         File path = new File("D:\\TESTING2");
         File path1 = new File("D:\\Document\\thesis\\paper\\format\\springer\\mallet\\TESTING2.mallet");
        
         
        ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(path);
        
        instances.save(path1);
        

      
      

    }

    /** This class illustrates how to build a simple file filter */
    class TxtFilter implements FileFilter {

        /** Test whether the string representation of the file 
         *   ends with the correct extension. Note that {@ref FileIterator}
         *   will only call this filter if the file is not a directory,
         *   so we do not need to test that it is a file.
         */
        public boolean accept(File file) {
            return file.toString().endsWith(".txt");
        }
    }

}

