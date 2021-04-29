/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Ultilities.csvread;
import extract.Extractor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class dbSQLite {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:doc.db");
            // Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);  // set timeout to 30 sec.

        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

    public static void createTableDoc() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Document (\n"
                + "	doc_id integer PRIMARY KEY,\n"
                + "	path text NOT NULL UNIQUE,\n"
                + "	title text NOT NULL ,\n"
                + "	authors text ,\n"
                + "	abstract text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableSearchResult() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS SearchResult (\n"
                + "	doc_id integer PRIMARY KEY,\n"
                + "	title text ,\n"
                + "	score  int \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableBestAuthor() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS BestAuthor (\n"
                + "	ba_id integer PRIMARY KEY,\n"
                + "	Topic text ,\n"
                + "	authors  int \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableTopic() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Topics (\n"
                + "	Topic text PRIMARY KEY,\n"
             
                + "	keywords text \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
       public static void createTableDocTopic() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:doc.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS DocTopic (\n"
                + "	path text PRIMARY KEY,\n"
                + "	filename text ,\n"
                + "	topic1 text ,\n"
                + "	distribution1 int ,\n"
                + "	topic2 text ,\n"
           
                + "	distribution2 int \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertDoc(String path, String title, String authors, String abs) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        String sql = "INSERT INTO Document(path,title,authors,abstract) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, path);
            pstmt.setString(2, title);
            pstmt.setString(3, authors);
            pstmt.setString(4, abs);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertSearchResult(String title, String occurrence) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        String sql = "INSERT INTO SearchResult(title,score) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, occurrence);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertBestAuthor(String topic, String authors) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        String sql = "INSERT INTO BestAuthor(topic,authors) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, topic);
            pstmt.setString(2, authors);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insertTopics(String topic, String kws) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        String sql = "INSERT INTO Topics(topic,keywords) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, topic);
            pstmt.setString(2, kws);
          

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
        public void insertDocTopics(String path, String t1, String d1, String t2, String d2) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        String sql = "INSERT INTO DocTopic(path,filename,topic1,distribution1,topic2,distribution2) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, path);
            pstmt.setString(2, path.substring(path.lastIndexOf("\\")+1).replace(".txt",".pdf"));
            pstmt.setString(3, t1);
            pstmt.setString(4, d1);
            pstmt.setString(5, t2);
            pstmt.setString(6, d2);
           

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void insertDocumentDir(String inpuDir) throws ClassNotFoundException, IOException {
        String path = inpuDir + "\\bib";
        File dir = new File(path);
        dbSQLite db = new dbSQLite();
        System.out.println("Create table Document from inputDir: " + path);
        Extractor extract = new Extractor();
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {

            if (files[i].isFile()) {
                String name=files[i].getName();
                System.out.println();
                File dir2 = new File(path + "\\" + name);
                
                String fpath=inpuDir+"\\"+name.replace(".txt", ".pdf");
                db.insertDoc(fpath, extract.Title(dir2), extract.Authors(dir2), extract.Abstract(dir2));

            }

        }

    }

    public void insertTopicCSV(int row, int col) throws ClassNotFoundException, IOException {
        csvread csv =new csvread();
        dbSQLite db = new dbSQLite();
        String csvTop_key[][] = csv.CSVread(row, col, "output_LDA\\" + "Topic_Keyword.csv");
        
        
        for (int i = 0; i < row; i++) {
           String keywords="";
           
           for(int j=1;j<=col;j++){
           keywords  =keywords+" "+csvTop_key[i][j];
          
           }
           
        
           
            db.insertTopics(csvTop_key[i][0],keywords);

        }

    }
    
        public void insertDocTopicCSV(int col) throws ClassNotFoundException, IOException {
        csvread csv =new csvread();
        dbSQLite db = new dbSQLite();
        String csvDoc_top[][] = csv.CSVread(0, col, "output_LDA\\" + "Document_Topic.csv");

        
        for (int i = 1; i <= csvread.CSVrow("output_LDA\\" + "Document_Topic.csv"); i++) {
            
            String path=csvDoc_top[i][1];
            if(path!=null){path=path.replaceAll("file:/","").replace("/","\\").replace("\\output_abs","");
            
            }
            
            db.insertDocTopics(path, csvDoc_top[i][2],String.format("%.8s",csvDoc_top[i][3]), csvDoc_top[i][4], String.format("%.8s",csvDoc_top[i][5]));

        }

    }

    public void DeleteSearchResult() throws ClassNotFoundException, IOException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DELETE FROM SearchResult");

    }
    
        public void DeleteDocument() throws ClassNotFoundException, IOException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS Document");

    }
        
       public void DeleteTopics() throws ClassNotFoundException, IOException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS Topics");

    }
        public void DeleteDocTopic() throws ClassNotFoundException, IOException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS DocTopic");

    }

    public void DeleteBestAuthor() throws ClassNotFoundException, IOException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE IF EXISTS BestAuthor");

    }

    public Set<String> searchAbstract(String input) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Set<String> matchesA = new LinkedHashSet<String>();
        String sql = "SELECT title FROM Document WHERE abstract LIKE '%" + input + "%'";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                /* System.out.println(//rs.getInt("id") +  "\t" + 
                                   //rs.getString("name") + "\t" +
                                   rs.getString("title"));*/
                matchesA.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return matchesA;
    }

    public Set<String> searchTitle(String input) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Set<String> matchesA = new LinkedHashSet<String>();
        String sql = "SELECT title FROM Document WHERE title LIKE '%" + input + "%'";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                /* System.out.println(//rs.getInt("id") +  "\t" + 
                                   //rs.getString("name") + "\t" +
                                   rs.getString("title"));*/
                matchesA.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return matchesA;
    }

    public ArrayList<String[]> selecTableDoc() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String[][] table;
        ArrayList<String[]> result = new ArrayList<String[]>();
        String sql = "SELECT title,authors FROM Document";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            int columnCount = rs.getMetaData().getColumnCount();
            // loop through the result set
            while (rs.next()) {

                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                result.add(row);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public String[] selectTopicBestAuthor(String input) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String[] author = {};
        String sql = "SELECT authors FROM BestAuthor WHERE Topic LIKE '%" + input + "%'";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {

                author = rs.getString("authors").split(",");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return author;
    }

    public String[][] ArrayList2D(ArrayList<String[]> list) throws ClassNotFoundException, IOException, SQLException {

        //ArrayList<String[]> list = db.selecTableDoc();
        String[][] array2D = new String[list.size()][];
        for (int i = 0; i < array2D.length; i++) {
            String[] row = list.get(i);
            array2D[i] = row;
        }

        return array2D;

    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        //connect();
        // createNewDatabase();
        createTableDoc();
        dbSQLite db = new dbSQLite();
        Extractor extract = new Extractor();

        //db.insertDocumentDir("D:\\Document\\thesis\\paper\\format\\pdf_file");
       // db.createTableTopic();
        //db.insertTopicCSV(10,5);
        
       
       
       db.createTableDocTopic();
       db.insertDocTopicCSV(5);
       
        //System.out.println( db.searchTitle("Biometrics"));

        /*       ArrayList<String[]> list = db.selecTableDoc();
        
        String[][] array2D= db.ArrayList2D(list);
        

       for (int i = 0; i < array2D.length; i++){
       
           //System.out.println(array2D[i][0]);
           for(String str: extract.Author(array2D[i][1])){
               
               System.out.println(str);
           
           }
           
          
           
       }*/

 /*for (String[] str : list) {
			//System.out.println(str[0]);
		}
                
                System.out.println(list.get(0)[0]);
                System.out.println(list.get(0)[1]);*/
 /*    String [] a=db.selectTopicBestAuthor("Topic 4 ");
       
       for (String str : a){
       System.out.println(str.trim());
       
       }*/
 
 
 
 //String dir="D:\\Document\\thesis\\paper\\format\\pdf_file\\bib\\20C75D~1.txt";
 //File dir2=new File(dir);
  //db.insertDoc(dir, extract.Title(dir2), extract.Authors(dir2), extract.Abstract(dir2));

 
 
    }

}
