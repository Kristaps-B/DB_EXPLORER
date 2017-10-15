package Models;

import Database.DbUtils;
import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.ArgumentsResult;
import Results.PlsqlResult;
import Results.Result;
import Results.TableResult;
import Utils.UserUtils;

public class AllPlsqlMod {
	
	
	private DbUtils dbUtils = new DbUtils();
	
	public AllPlsqlMod () {
		
	}
	
	
	
	
	public String loadPlsql(int limit, int offset) {

		
		
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		PlsqlResult rs = new PlsqlResult();
		
		String sql = "SELECT id, plsql_id, owner, name, type, parent, examine_time "
				+ " FROM all_plsql "
				+ " WHERE  parent IS NULL "
				+ "  LIMIT " + limit + "  OFFSET " + offset;
		
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
		
		
		
		String sql = "select object_id id, object_id plsql_id, owner, object_name name, object_type type, null parent, '' examine_time" +
		" FROM all_objects " +
		" WHERE 1=1 " +
		activeUsers +
		" and object_type IN " +
		" ('PROCEDURE', 'FUNCTION', 'PACKAGE') ";
		
		SQLOracle oQuerie = new SQLOracle();
		
		PlsqlResult  rs = new PlsqlResult();
		 
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				PlsqlResult.Row row = rs.getColumns().get(i);
				
				//System.out.println("Plsql ID: " + row.id + " " + row.name);
				
				 
				this.savePlsql(row);
				
				
				if ( row.type.equals("PROCEDURE") || row.type.equals("FUNCTION") ) {
					this.getAllArguments(row.owner, row.name, row.plsql_id);
				}
				
				
			
				
			}
			
			
			
		} catch (Exception e) {
			System.out.println("AllPlsqlMod.updatePlsql: " + e.getMessage());
		}
		
		
		
	}
	
	

	
	
	
	private void savePlsql (PlsqlResult.Row row) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_plsql "
		+ "(plsql_id, owner, name, type, parent, examine_time) "
		+ "VALUES (" 
		+ "'" + row.plsql_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.name  + "',"
		+ "'" + row.type  + "',"
		+ "'" + row.parent  + "',"
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
	
	
	public void analysePlsql(String owner, String name, String type) {
		
		try {
			
			update_examine_time(owner, name);
			
			
			if (type.equals("PACKAGE")) {
				analysePackage(owner, name, type);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void analysePackage (String owner, String name, String type) {
		
		
		
		System.out.println("Analyse package: " + owner + "." + name);
		
		
        PlsqlResult rs = new PlsqlResult();


		SQLOracle oQuerie = new SQLOracle();
		
		String sql =  "select "
				+ "p.object_id id, "
				+ "p.object_id plsql_id, "
				+ "p.owner, "
				+ "p.procedure_name name, "
				+ "NVL2(a.object_id, 'FUNCTION', 'PROCEDURE') type, "
				+ "p.object_name parent, "
				+ "'' examine_time "
				+ " FROM " 
		        + " all_procedures p " 
				+ " LEFT JOIN all_arguments a "
				+ " ON ("
				+ "  a.object_id = p.object_id"
				+ "  AND a.subprogram_id = p.subprogram_id"
				+ "  AND a.position = 0"
				+ ") "
				+ " WHERE "
				+ " p.owner = '" + owner +"' "
				+ " AND p.object_name = '" + name + "' "
				+ " AND p.object_type = '" + type + "' "
		        + " AND p.procedure_name IS NOT NULL "
		        ;
		
		System.out.println("SQL: " + sql);
		
		try {
			 
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("---------LOAD_PLSQL--------------");
			
			
		
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				PlsqlResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				
				System.out.println("Object_name: " + row.name + " Object_type: " + row.type);
 
				
				
				
				savePlsql (row);
				
				System.out.println("AFTER SAVE PLSQL!");
				
				
				// Get arguments
				if ( row.type.equals("PROCEDURE") || row.type.equals("FUNCTION") ) {
					this.getAllArguments(row.owner, row.name, row.plsql_id);
				}
				 
			
			}
			
			
			
			
			// result = aJson.getJson();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
			
			
		}
		
		
		
		
	}	
	
	
	private void getAllArguments (String owner, String procFunctName, int plsqlId) {
		
		System.out.println("GetAllArguments: " + owner + " name: " + procFunctName);
		
		
        ArgumentsResult rs = new ArgumentsResult();


		SQLOracle oQuerie = new SQLOracle();
		
		String sql = 
				  "SELECT "
				+ " a.object_id id,"
				+ " a.object_id plsql_id,"
				+ " a.owner,"
				+ " a.argument_name,"
				+ " a.data_type,"
				+ " a.position,"
				+ " a.in_out "
				+ " FROM "
				+ " all_arguments a"
				+ " WHERE a.owner = '" + owner + "' "
				+ " AND a.object_name = '" + procFunctName + "' "
				+ " AND a.object_id = '" + plsqlId + "' "
				+ " ORDER BY a.position "
		        ;
		
		System.out.println("SQL: " + sql);
		
		try {
			 
			oQuerie.queryDatabase(sql, rs);
			
			
	
			
			
		
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ArgumentsResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				
				// System.out.println("Object_name: " + row.name);
				// System.out.println("Object_type: " + row.type);
				
				System.out.println("Argument_name: " + row.argument_name );
				
				
				saveArguments ( row );
				
				// savePlsql (row);
				
			
			}
			
			
			
			
			// result = aJson.getJson();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
			
			
		}		
		
		
	}
	
	
	private void saveArguments ( ArgumentsResult.Row row ) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_arguments "
		+ "( plsql_id, argument_name, data_type, position, in_out ) "
		+ "VALUES (" 
		+ "'" + row.plsql_id        + "', "
		+ "'" + row.argument_name   + "', "
		+ "'" + row.data_type       + "', "
		+ "'" + row.position        + "', "
        + "'" + row.in_out          + "' "
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("PLSQL " + row.argument_name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
	
}
