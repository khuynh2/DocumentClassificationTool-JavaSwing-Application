/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.script.ScriptEngine.FILENAME;
import static scala.xml.XML.encoding;

/**
 *
 * @author USER
 */
public class Extractor {

    public String Readtxt(File file) throws IOException {

        String content = null;

        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return content;
    }
    //dealing with utf-8 encoding

    public String Readtxt2(File file) throws IOException {

        String content = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {

            String strCurrentLine;

            while ((strCurrentLine = br.readLine()) != null) {
                //System.out.println(strCurrentLine);
                content += strCurrentLine;
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String Abstract(File file) throws IOException {

        String inputStr = this.Readtxt2(file);
        String patternStr = "(?<=abstract	=	\").*?(?=\")";

        String contens = "";

        Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
        if (inputStr != null) {
            Matcher matcher = pattern.matcher(inputStr);

            while (matcher.find()) {
                String paragraph = matcher.group();
                contens += paragraph;

            }
        }
        return contens;

    }

    public String Authors(File file) throws IOException {

        String inputStr = this.Readtxt2(file);
        String patternStr = "(?<=author	=	\").*?(?=\")";

        String contens = "";

        Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
        if (inputStr != null) {
            Matcher matcher = pattern.matcher(inputStr);

            while (matcher.find()) {

                String paragraph = matcher.group();
                contens += paragraph;

            }
        }
        //contens.replaceAll("Ho Minh","").replaceAll("Minh City","").replaceAll("Ho Chi","").replaceAll("Vietnam","").replaceAll("Viet Nam","");
        // contens=contens.replaceAll("Ho Minh|Minh City|Ho Chi|Vietnam|Viet Nam", "");
        // System.out.println(contens);
        return contens;

    }

    public String[] Author(String names) throws IOException {

        String[] values = names.split(" and ");

        return values;

    }

    public String Title(File file) throws IOException {

        String inputStr = this.Readtxt2(file);
        String patternStr = "(?<=title	=	\").*?(?=\")";

        String contens = "";

        Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
        if (inputStr != null) {
            Matcher matcher = pattern.matcher(inputStr);

            while (matcher.find()) {
                String paragraph = matcher.group();
                contens += paragraph;

            }
        }
        return contens.replaceAll(",", "");

    }

    // create txt for abstract
    public void AbstractFile(File inputDir) throws IOException {

        String path = inputDir + "\\bib";
        File dir = new File(path);

        new File(path + "\\output_abs").mkdirs();

        Extractor extract = new Extractor();
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // System.out.println(files[i].getName());
                File dir2 = new File(path + "\\" + files[i].getName());
                //System.out.println(extract.Title(dir2));
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path + "\\output_abs\\" + files[i].getName()), "utf-8"))) {
                    writer.write(extract.Abstract(dir2));
                }

            }
        }

    }

    // create a file storing file path, author, title.
    public void Document(File inputDir) throws IOException {

        String path = inputDir + "\\bib";
        File dir = new File(path);

        new File(path + "\\document").mkdirs();
        int j = 0;
        Extractor extract = new Extractor();
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                // System.out.println(files[i].getName());
                File dir2 = new File(path + "\\" + files[i].getName());
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("output_LDA\\" + "Document.txt", true), "utf-8"))) {

                    writer.write("" + j++);

                    writer.write("\t");
                    writer.write("" + files[i].getAbsolutePath());
                    System.out.println(files[i].getAbsolutePath());
                    writer.write("\t");
                    writer.write("" + extract.Title(dir2));
                    writer.write("\t");
                    for (String a : extract.Author(extract.Authors(dir2))) {
                        writer.write("" + a);
                        writer.write("\t");
                    }
                    writer.write("\n");
                    writer.flush();
                    writer.close();

                }

            }

        }

    }

    public void Rename(File inputDir) throws IOException {

        String path = inputDir + "\\bib";
        File dir = new File(path);

        // File folder = new File(a + "\\bib");
        File[] listOfFiles = dir.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {

                // System.out.println(file.getName().replaceAll(",", ""));
                String fname = file.getName().replaceAll(",", "");
                File file2 = new File(inputDir + "\\bib\\" + fname);

                file.renameTo(file2);
                // System.out.println(inputDir + "\\bib\\"+fname);

            }
        }

        File[] listOfFiles2 = inputDir.listFiles();
        for (File file : listOfFiles2) {
            if (file.isFile()) {

                String fname = file.getName().replace("pdf_file", "");
                File file3 = new File(inputDir + "\\" + fname);

                file.renameTo(file3);

            }

        }
    }

    public static void main(String[] args) throws IOException {
        Extractor extract = new Extractor();

        String a = "D:\\Document\\thesis\\paper\\format\\pdf_file\\bib\\2003 - The Description Logic Handbook.txt";
        //File a1 = new File(a);
        // extract.Document(a1);
        //extract.Rename(a1);
        //System.out.println(extract.Title(a1));
        //System.out.println(extract.Readtxt2(a1));

        // String [] str=extract.Author("Ranjit Ghoshal and Anandarup Roy and Swapan Parui");    
        // System.out.println(str[1]);
        
        //  System.out.println(contens);
        File a1 = new File("D:\\Document\\thesis\\paper\\format\\pdf_file");
        extract.Rename(a1);

    }
}
