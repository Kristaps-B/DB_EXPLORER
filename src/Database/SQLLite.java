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
	
	
	
	public void insertUpdate (String sql) throws Exception {
		
		try ( Connection c = DriverManager.getConnection(Session.dBUserString);
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
	
	
	
	
	public void query (String sql , Result rs) throws Exception {
		
		try ( Connection c = DriverManager.getConnection(Session.dBUserString);
				Statement stmt = c.createStatement();
				) {
			 
		 
		    
		    Class.forName("org.sqlite.JDBC");
			
			
		    c.setAutoCommit(false);
		 
			
				
			
			 
				
			ResultSet rSet = stmt.executeQuery(sql);
			
			
			copyToResult(rSet, rs); 
			
			/*
			while (rs.next()) {

				result += "{";
				
				result += "\"id\":\"" + rs.getString("id") + "\",";
				result += "\"ip\":\"" + rs.getString("ip") + "\",";
				result += "\"port\":\"" + rs.getString("port") + "\","; 
				result += "\"sid\":\"" + rs.getString("sid") + "\",";
				result += "\"username\":\"" + rs.getString("username") + "\",";
				result += "\"password\":\"" + rs.getString("password") + "\"";
			 
				
				
				result += "},";
			}
			
			if (result.length() > 1) {
				result = result.substring(0, result.length() - 1);
			}
				
			
			result += "]";
			*/
			
			//result = "[{a:a},{a:b}]";
			
			//c.commit();
			
			
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
