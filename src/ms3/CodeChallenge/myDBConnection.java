package ms3.CodeChallenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class myDBConnection {
	  
    private static String driverName = "com.mysql.jdbc.Driver";   
    private static Connection con;
    
  
	
	public static Connection getConnection() {
//	
		
         
            try {
            	Class.forName(driverName);
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ms3","root","");
//                System.out.println("Connected");
                return con;
            } catch (SQLException ex) {
            	System.out.println("Driver not found."); 
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          return null;
}
	
	
	//Populate Table method
	@SuppressWarnings("null")
	public static Connection populateTable(String getTable_query) throws SQLException {
		PreparedStatement pst = null;
	    ResultSet rs = null;
		Connection con = null;
		
//            getTable_query = "SELECT * FROM tbl_client";
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ms3","root","");
    			pst = con.prepareStatement(getTable_query);
    			rs = pst.executeQuery();
//    		
			return con;
        
	}
	}
