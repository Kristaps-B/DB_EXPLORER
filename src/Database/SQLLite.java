package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Global.Session;
import Results.Result;
import Results.UsersResult;

public class SQLLite {
	
	
	
	public SQLLite () {
		
	}
	
	
	
	public void insertUpdate (String sql, String connectionString) throws Exception {
		
		try ( Connection c = DriverManager.getConnection(connectionString);
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
	
	
	
	
	public void query (String sql , Result rs, String connectionString) throws Exception {
		
		try ( Connection c = DriverManager.getConnection(connectionString);
				Statement stmt = c.createStatement();
				) {
			 
		 
		    
		    Class.forName("org.sqlite.JDBC");
			
			
		    c.setAutoCommit(false);
		 
			
				
			
			 
				
			ResultSet rSet = stmt.executeQuery(sql);
			
			
			copyToResult(rSet, rs); 
			

			
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
	}
	
	
	
	private void copyToResult (ResultSet rSet, Result rs) throws Exception {
		
		// if (rs instanceof UsersResult) {
			
			(rs).copyRows(rSet);
			
		// }
		
	}	
	
}
