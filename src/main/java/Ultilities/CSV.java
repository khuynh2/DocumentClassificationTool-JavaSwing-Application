/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ultilities;

import cc.mallet.util.PropertyList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author USER
 */
public class CSV {

    public void CSVconvert(String in, String out) throws FileNotFoundException, IOException {

        FileWriter writer = null;
        File input = new File("output_LDA\\" + in);
        Scanner scan = new Scanner(input, "UTF-8");
        File output = new File("output_LDA\\" + out + ".csv");
        input.createNewFile();
        writer = new FileWriter(output);

        while (scan.hasNext()) {
            String csv = scan.nextLine().replace("\t", ",");
            System.out.println(csv);
            writer.append(csv);
            writer.append("\n");
            writer.flush();

        }

        writer.close();
    }

    
    //write set to text file, for testing
    public static void usingBufferedWritter(Set<String> searchRresult) throws IOException {
        
      

        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\NetBeansProjects\\com.mycompany_DocClass_jar_1.0-SNAPSHOT\\SearhResult.txt"));
        for(String s: searchRresult){
        
        writer.write(s);
        writer.write("\n");
        }
        writer.close();
    }
    
    
    
    

    public static void main(String[] args) throws URISyntaxException,
            IOException {

        /* FileWriter writer = null;
        File file = new File("D:\\Document\\thesis\\paper\\format\\springer\\mallet\\topic.mallet.txt");
        Scanner scan = new Scanner(file);
        File file2 = new File("D:\\Document\\thesis\\paper\\format\\springer\\mallet\\topic.mallet.csv");
        file.createNewFile();
        writer = new FileWriter(file2);

        while (scan.hasNext()) {
            String csv = scan.nextLine().replace("\t", ",");
            System.out.println(csv);
            writer.append(csv);
            writer.append("\n");
            writer.flush();
        }*/
        CSV csv1 = new CSV();
        //csv1.CSVconvert ("Document_Topic.mallet.txt","Document_Topic");
        //csv1.CSVconvert ("Topic_Keyword.mallet.txt","Topic_Keyword");
        csv1.CSVconvert("Document.txt", "Document");
    }

}
