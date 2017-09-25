package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.TableResult;

public class GraphPageMod {
	public GraphPageMod () {
		
	}
	
	
	
	
	public String generateGraph() {
		String result = "";
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		
		int id = 1;
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM all_tables " ;
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_TABLES--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				//  "color": "red", "size":40, "shape": "box"
				aJson.addValue("id", "" + row.id);
				aJson.addValue("label", row.owner + "." +row.table_name);
				aJson.addValue("color", "green");
				aJson.addValue("size", "60");
				aJson.addValue("shape", "box");
		
				
				aJson.newRow();
				
				id ++;
			}
			
			aJson.endJson();
			
			
			result = "{ \"nodes\" : " + aJson.getJson() + " , \"edges\": " + "[ ]" + " }" ;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		System.out.println(result);		
		
		
		
		return result;
		
	}
}
