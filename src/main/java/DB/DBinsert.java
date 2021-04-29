package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
public class DBinsert {
    
    
    
    

    public void insertDB(Integer doc_id, String title, String year, String abs,String intro, Integer pid) {
        String query = "INSERT INTO Document ("
                + " doc_id,"
                + " title,"
                + " year,"
                
                + " abstract,"
                + " introduction, "
                + " publisher_id"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "root", "root");

            // create the java statement
            //Statement st = con.createStatement();
            //st.executeUpdate("INSERT INTO document " + 
            //   "VALUES (1, 'testing', '2017-2019', 'Springfield', 'working')");
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, doc_id);
            st.setString(2, title);
            st.setString(3,  year);
            
            st.setString(4, abs);
            st.setString(5, intro);
             st.setInt(6, pid);

            // execute the preparedstatement insert
            st.executeUpdate();

            st.close();
        } catch (Exception e) {
            System.out.println("lol " + e);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    
    public void insertAuthor(Integer a_id, String a_name, String dept, String university) {
        String query = "INSERT INTO author ("
                + " author_id,"
                + " author_name,"
                + " department,"
                
               
                + " university) VALUES ("
                + " ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "root", "root");

            // create the java statement
            //Statement st = con.createStatement();
            //st.executeUpdate("INSERT INTO document " + 
            //   "VALUES (1, 'testing', '2017-2019', 'Springfield', 'working')");
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, a_id);
            st.setString(2, a_name);
            st.setString(3,  dept);
            
            st.setString(4, university);
            

            // execute the preparedstatement insert
            st.executeUpdate();

            st.close();
        } catch (Exception e) {
            System.out.println("lol " + e);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    
    
     
    public void insertKeyword(Integer key_id, String keyword) {
        String query = "INSERT INTO keyword ("
                + " key_id,"
              
                
               
                + " keyword ) VALUES ("
                + " ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "root", "root");

            
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, key_id);
            st.setString(2, keyword);
         

            // execute the preparedstatement insert
            st.executeUpdate();

            st.close();
        } catch (Exception e) {
            System.out.println("lol " + e);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    
    
    public void insert_dockeyw (Integer doc_keyw_id, Integer doc_id,Integer key_id) {
        String query = "INSERT INTO document_has_keyword ("
                + " doc_keyw_id,"
                + " document_doc_id,"
              
                + " keyword_key_id"
                + ") VALUES ("
                + " ?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "root", "root");

            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, doc_keyw_id);
            st.setInt(2, doc_id);
            st.setInt(3,  key_id);
            
            

            st.executeUpdate();

            st.close();
        } catch (Exception e) {
            System.out.println("lol " + e);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

}
