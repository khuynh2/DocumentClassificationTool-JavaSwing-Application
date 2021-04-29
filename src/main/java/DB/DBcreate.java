package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Notice, do not import com.mysql.jdbc.*
// or you will have problems!
public class DBcreate {
    
    
    
    

    public static void DB() {
        String query = "CREATE TABLE Document ("
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
                    "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull", "root", "root");

            String sql = "CREATE DATABASE " + "DocClass";
            
            
            
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        statement.close();
     
            
            
            
        } catch (Exception e) {
            System.out.println("lol " + e);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    
    
    
     public static void main(String args[]){
        
         DB();
         
         
    }
   
     
    
}
