package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLLite {
	
	
	
	public SQLLite () {
		
	}
	
	
	
	public void insert (String sql) throws Exception {
		
		try ( Connection c = DriverManager.getConnection(DBGlobal.dBString);
				Statement stmt = c.createStatement();
				) {
			 
		 
		    
		    Class.forName("org.sqlite.JDBC");
			
			c.setAutoCommit(false);
			
		 
			
			
				
				
				stmt.executeUpdate(sql);
				
				
			
			c.commit();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			
			throw new Exception(e.getMessage());
		}
		
	}
}
