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
		
		
		String sql = "SELECT t.id, t.table_id, t.owner, t.table_name, t.examine_time "
				+ " FROM tables t"
				+ " WHERE "
				+ " 1=1 "
				+ " " + activeUsers
				
				+ "AND  EXISTS ( SELECT 1  FROM joins j  WHERE ( t.owner = j.left_owner AND t.table_name = j.left_table ) OR  (t.owner = j.right_owner AND t.table_name = j.right_table ) ) "
				
				+ " ORDER BY t.owner" ;
		
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
				aJson.addValue("id", "" + (row.owner + "." +row.table_name).replace('$', '_')  );
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
		" FROM joins j " + 
		" WHERE 1=1 "
		+ " AND  j.right_owner IN  " + activeUsers.substring(12) + " OR j.left_owner IN " + activeUsers.substring(12) 
;
	 
	 
	 System.out.println("Edge SQL: " + sql);
		
		try {
			
			sqlLite.query(sql, rs1, Session.dBUserString);
			
			
			System.out.println("--------- LOAD_JOINS --------------");
			
			
			
			bJson.startJson();
			
			for (int i = 0; i < rs1.getColumns().size(); i++) {
				
				 JoinResult.Row row = rs1.getColumns().get(i);
				
			
				 
				aJson.addValue("id", (row.left_owner + "." + row.left_table + "." + row.left_column + "." + id ).replace('$', '_') );
				aJson.addValue("label",  row.left_column);
				
				 
				
				aJson.newRow();
				
				
				aJson.addValue("id", (row.right_owner + "." + row.right_table + "." + row.right_column+ "." + id + 1).replace('$', '_') );
				aJson.addValue("label",  row.right_column);
				
				
				
				aJson.newRow();
				
				
				
				// -- {"from": "1", "to": "3"} 
				
				bJson.addValue("from", row.left_owner + "." + row.left_table + "." + row.left_column+ "." + id);
				bJson.addValue("to", row.right_owner + "." + row.right_table + "." + row.right_column+ "." + id + 1);
				
				
				bJson.newRow();
				
				
				bJson.addValue("from", row.right_owner + "." +row.right_table);
				bJson.addValue("to", row.right_owner + "." + row.right_table + "." + row.right_column + "." + id + 1);
				
				bJson.newRow();
				
				bJson.addValue("from", row.left_owner + "." +row.left_table);
				bJson.addValue("to", row.left_owner + "." + row.left_table + "." + row.left_column + "." + id);				
				
				
				bJson.newRow();
				
				id += 2;
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
