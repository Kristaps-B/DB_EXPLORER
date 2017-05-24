package Models;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.TableResult;
import Results.UsersResult;

public class AllTablesMod {
	
	
	public AllTablesMod() {
		
		
	}
	
	
	public void updateTables () {
		
		
		String activeUsers = this.loadActiveUsers();
		
	
			
		System.out.println("ACTIVE_USERS: " + activeUsers);
		
		
		String sql = "SELECT rownum ID, rownum TABLE_ID, OWNER, TABLE_NAME FROM all_tables WHERE 1=1 " + activeUsers;
		
		SQLOracle oQuerie = new SQLOracle();
		
		TableResult  rs = new TableResult();
		 
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.table_name);
				
				 
				this.saveTable(row);
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	
	
	private String loadActiveUsers () {
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		UsersResult rs = new UsersResult();
		
		String sql = "SELECT id, user_id, username, active FROM all_users WHERE active='Y'";
		
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			if (rs.getColumns().size() > 0 ) {
				
				result = "AND owner IN (";
				
				for (UsersResult.Row row: rs.getColumns()) {
					
					result += "'" + row.username + "',";
				}
				
				result += "'')";
				
			} else {
				result = " AND 1=2";
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return result;
	}
	
	
	
	private void saveTable(TableResult.Row row) throws Exception {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_tables"
		+ "(table_id, owner, table_name) "
		+ "VALUES (" 
		+ "'" + row.table_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.table_name  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("TABLE " + row.table_name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	public String loadTables () {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		String sql = "SELECT id, table_id, owner, table_name FROM all_tables";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_TABLES--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("table_id", "" + row.table_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("table_name", row.table_name);
				
				aJson.newRow();
			}
			
			aJson.endJson();
			
			
			result = aJson.getJson();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		System.out.println(result);
		
		return result;
		
		
	}
	
}
