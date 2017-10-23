package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.PlsqlResult;

public class PlsqlInformationMod {
	
	
	
	public PlsqlInformationMod () {
		
		
		
		
		
		
	}
	
	
	
	public String loadProcFunc (String owner, String name) {
		String result = "";
		
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		PlsqlResult rs = new PlsqlResult();
		
		String sql = "SELECT id, plsql_id, owner, name, type, parent, examine_time "
				+ " FROM all_plsql "
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
	
}
