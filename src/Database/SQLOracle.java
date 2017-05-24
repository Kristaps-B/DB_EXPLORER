package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Global.Session;
import Results.Result;
import Results.UsersResult;

public class SQLOracle {
	
	
	
	private String URL = "jdbc:oracle:thin:@192.168.0.102:1521:ORCL"; // "jdbc:oracle:thin:@127.0.0.1:6060:ORCL";
	private String username = "SYSTEM";
	private String password = "kristaps";
	
	
	

	
	
	public SQLOracle () {
		this.URL = "jdbc:oracle:thin:@" + Session.ip + ":" + Session.port + ":" + Session.sid;
		
		this.username = Session.username;
		this.password = Session.password;
		
	}
	
	
	public void queryDatabase (String query, Result rs) throws Exception {
		
		try (Connection conn = DriverManager.getConnection(URL, username, password);) {
			
			
	
			
			Statement command = conn.createStatement();
			
			
			
			String sql = query;
			
			
			
			ResultSet rSet = command.executeQuery(sql);
			
		
			
			//sqlLite.saveColumns(rez);
			copyToResult(rSet, rs);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			
			System.out.println(e.getMessage());
			
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	private void copyToResult (ResultSet rSet, Result rs) throws Exception {
		
		//if (rs instanceof UsersResult) {
			
			( rs).copyRows(rSet);
			
		//} 
		
	}
	
}
