package Models;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.PlsqlResult;
import Results.Result;
import Results.TableResult;
import Utils.UserUtils;

public class AllPlsqlMod {
	public AllPlsqlMod () {
		
	}
	
	
	
	
	public String loadPlsql(int limit, int offset) {

		
		
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		PlsqlResult rs = new PlsqlResult();
		
		String sql = "SELECT id, plsql_id, owner, name, type, examine_time FROM all_plsql  LIMIT " + limit + "  OFFSET " + offset;
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_PLSQL--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				PlsqlResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("plsql_id", "" + row.plsql_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("name", row.name);
				aJson.addValue("type", row.type);
				aJson.addValue("examine_time", row.examine_time);
				
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
	
	
	
	
	public void updatePlsql () {
		String activeUsers =  new UserUtils().loadActiveUsers();
		
		
		
		System.out.println("ACTIVE_USERS: " + activeUsers);
		
		
		
		String sql = "select object_id id, object_id plsql_id, owner, object_name name, object_type type, '' examine_time" +
		" FROM all_objects " +
		" WHERE 1=1 " +
		activeUsers +
		" and object_type IN " +
		" ('PROCEDURE', 'FUNCTION') ";
		
		SQLOracle oQuerie = new SQLOracle();
		
		PlsqlResult  rs = new PlsqlResult();
		 
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				PlsqlResult.Row row = rs.getColumns().get(i);
				
				//System.out.println("Plsql ID: " + row.id + " " + row.name);
				
				 
				this.savePlsql(row);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("AllPlsqlMod.updatePlsql: " + e.getMessage());
		}
		
		
		
	}
	
	
	
	private void savePlsql (PlsqlResult.Row row) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_plsql "
		+ "(plsql_id, owner, name, type, examine_time) "
		+ "VALUES (" 
		+ "'" + row.plsql_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.name  + "',"
		+ "'" + row.type  + "',"
		+ "NULL"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("PLSQL " + row.name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		
	}
	
	
	
	private void update_examine_time (String owner, String name) throws Exception {
		SQLLite sqlLite = new SQLLite();
		
		String sql  = "UPDATE all_plsql " +
		"SET examine_time = DATETIME('now', 'localtime')" +
	    "WHERE owner = '" + owner +"' AND name = '" + name + "' ";
		
		
		try {
			
			System.out.println("Updating examine_time " + owner + "  name: " + name);
			
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
		 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	public void analysePlsql(String owner, String name) {
		
		try {
			
			update_examine_time(owner, name);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
