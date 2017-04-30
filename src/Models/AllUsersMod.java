package Models;

import java.sql.ResultSet;

import Database.SQLLite;
import Database.SQLLiteOld;
import Database.SQLOracle;
import Results.Result;
import Results.UsersResult;

public class AllUsersMod {
	
	
	public AllUsersMod () {
		
	}
	
	
	public void queryUsers(String ip, String port, String sid, String username, String password) {
		
		
		SQLOracle oQuerie = new SQLOracle(ip, port, sid, username, password);
		
		UsersResult  rs = new UsersResult();
		
		
		String sql = "SELECT rownum id, user_id, username FROM all_users";
		
		
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
		//	while (rs.next()) {
		//		System.out.println("USERNAME: " );
		//	}
			
		System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				UsersResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.username);
				
				saveUser(row);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
		}
		
	}
	
	
	private void saveUser(UsersResult.Row row) throws Exception {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_users"
		+ "(user_id, username) "
		+ "VALUES (" 
		+ "'" + row.user_id  + "',"
		+ "'" + row.username  + "')"
		;
		
		
		
		try {
			sqlLite.insert(sql);
			
			System.out.println("USER " + row.username + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
}
