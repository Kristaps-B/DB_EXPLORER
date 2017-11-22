package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.JoinResult;
import Results.TableJoinResult;
import Results.TableResult;
import Results.ViewJoinResult;
import Utils.UserUtils;

public class GraphPageMod {
	public GraphPageMod () {
		
	}
	
	
	
	
	public String generateGraph() {
		String result = "";
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		String activeUsers =  new UserUtils().loadActiveUsers();
		
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM tables"
				+ " WHERE "
				+ " 1=1 "
				+ " " + activeUsers
				
				+ " ORDER BY owner" ;
		
		System.out.println("SQL: " + sql);
		
		ArrayJson aJson = new ArrayJson ();
		
		
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_TABLES--------------");
			
			aJson.startJson();
			
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				//  "color": "red", "size":40, "shape": "box"
				aJson.addValue("id", "" + row.owner + "." +row.table_name);
				aJson.addValue("label", row.owner + "." +row.table_name);
				aJson.addValue("color", "green");
				aJson.addValue("font", "20px arial white");
				aJson.addValue("shape", "box");
		
				
				aJson.newRow();
				
	
			}
			
			
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		ArrayJson bJson = new ArrayJson ();
		JoinResult rs1 = new JoinResult();
		
		
		int id = 0;
		
		
	 sql = "SELECT  "
	 		+ "j.left_table left_table, "
	 		+ "j.right_table right_table, "
	 		+ "j.left_column left_column, "
	 		+ "j.right_column right_column, "
	 		+ "j.left_owner left_owner, "
	 		+ "j.right_owner right_owner " + 
		" FROM joins j" + 
		" WHERE 1=1 " 

		;
		
		try {
			
			sqlLite.query(sql, rs1, Session.dBUserString);
			
			
			System.out.println("--------- LOAD_JOINS --------------");
			
			
			
			bJson.startJson();
			
			for (int i = 0; i < rs1.getColumns().size(); i++) {
				
				 JoinResult.Row row = rs1.getColumns().get(i);
				
			
				 
				aJson.addValue("id", row.left_owner + "." + row.left_table + "." + row.left_column + "." + id);
				aJson.addValue("label",  row.left_column);
				
				 
				
				aJson.newRow();
				
				
				aJson.addValue("id", row.right_owner + "." + row.right_table + "." + row.right_column+ "." + id);
				aJson.addValue("label",  row.right_column);
				
				
				
				aJson.newRow();
				
				
				
				// -- {"from": "1", "to": "3"} 
				
				bJson.addValue("from", row.left_owner + "." + row.left_table + "." + row.left_column+ "." + id);
				bJson.addValue("to", row.right_owner + "." + row.right_table + "." + row.right_column+ "." + id);
				
				
				bJson.newRow();
				
				
				bJson.addValue("from", row.right_owner + "." +row.right_table);
				bJson.addValue("to", row.right_owner + "." + row.right_table + "." + row.right_column + "." + id);
				
				bJson.newRow();
				
				bJson.addValue("from", row.left_owner + "." +row.left_table);
				bJson.addValue("to", row.left_owner + "." + row.left_table + "." + row.left_column + "." + id);				
				
				
				bJson.newRow();
				
				id ++;
			}
			
		 
			
			
			
			
		} catch (Exception e) {
			System.out.println("GenerateGraph Nodes: " + e.getMessage());
			
			
		}
		
		
		aJson.endJson();
		bJson.endJson();
		
		
		result = "{ \"nodes\" : " + aJson.getJson() + " , \"edges\": "  + bJson.getJson() + " }" ;
		
		
		
		System.out.println(result);		
		
		
		
		return result;
		
	}
}
