package Models;

import Database.DbUtils;
import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.ArgumentsResult;
import Results.DmlResult;
import Results.PlsqlResult;

public class PlsqlInformationMod {
	
	
	private DbUtils dbUtils;
	
	
	
	public PlsqlInformationMod () {
		
		this.dbUtils = new DbUtils();
		
		
		
		
	}
	
	
	
	public String loadProcFunc (String owner, String name) {
		String result = "";
		
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		PlsqlResult rs = new PlsqlResult();
		
		String sql = "SELECT id, plsql_id, owner, name, type, parent, examine_time "
				+ " FROM plsql "
				+ " WHERE 1=1 "
				+ " AND owner = '" + owner + "' "
				+ " AND parent = '" + name + "' ";
				
		
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
	
	
	public String getArguments ( String owner, String name ) {
		String result = "";
		
		System.out.println("---------LOAD_ARGUMENTS--------------");
		
		
		int plsql_id = dbUtils.getPlsqlId(owner, name);
		
		System.out.println("PLSQL_ID: " + plsql_id);
		
		
		SQLLite sqlLite = new SQLLite();
		
		// PlsqlResult rs = new PlsqlResult();
		
		ArgumentsResult rs = new ArgumentsResult();
		
		String sql = "SELECT id, plsql_id, '" + owner + "' owner, argument_name, data_type, position, in_out "
				+ " FROM arguments "
				+ " WHERE 1=1 "
				+ " AND plsql_id = " + plsql_id + " "
				+ " ORDER BY position ASC "
				;
		
		
		System.out.println("SQL: " + sql);
				
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ArgumentsResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("plsql_id", "" + row.plsql_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("argument_name", row.argument_name);
				aJson.addValue("data_type", row.data_type);
				aJson.addValue("position", row.position + "");
				aJson.addValue("in_out", row.in_out);
				
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
	
	
	
	public String getDml (String owner, String name) {
		String result = "";
		
		
		System.out.println("---------LOAD_DML--------------");
		
		
		int plsqlId = dbUtils.getPlsqlId(owner, name);
		
		System.out.println("PLSQL_ID: " + plsqlId);
		
		
		SQLLite sqlLite = new SQLLite();
		
		// PlsqlResult rs = new PlsqlResult();
		
		DmlResult rs = new DmlResult();
		
		String sql = "SELECT d.id, t.owner, t.table_name name, d.type "
				+ " FROM tables t, dml_statements d "
				+ " WHERE 1=1 "
				+ " AND t.table_id = d.table_id "
				+ " AND d.plsql_id = " + plsqlId + "  "
				
				;
		
		
		System.out.println("SQL: " + sql);
				
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				DmlResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("owner", "" + row.owner);
				aJson.addValue("name", row.name);
				aJson.addValue("type", row.type);
		 
				
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
