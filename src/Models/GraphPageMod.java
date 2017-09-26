package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.JoinResult;
import Results.TableJoinResult;
import Results.TableResult;
import Results.ViewJoinResult;

public class GraphPageMod {
	public GraphPageMod () {
		
	}
	
	
	
	
	public String generateGraph() {
		String result = "";
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		
		
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM all_tables " ;
		
		
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
		
		
	 sql = "SELECT js.id, tl.table_name left_table_name, tr.table_name right_table_name, cl.column_name left_column_name, cr.column_name right_column_name, tl.owner left_owner, tr.owner right_owner " + 
		" FROM all_table_joins j, all_tables tl, all_tables tr, all_columns cl, all_columns cr, join_sources js  " + 
		" WHERE 1=1 " + 
		" AND j.id                = js.join_id " +
		" AND j.left_table_id     = tl.id " +
		" AND j.right_table_id    = tr.id " +
		" AND j.left_column_id    = cl.id " +
		" AND j.right_column_id   = cr.id "
		
	   
		;
		
		try {
			
			sqlLite.query(sql, rs1, Session.dBUserString);
			
			
			System.out.println("--------- LOAD_JOINS --------------");
			
			
			
			bJson.startJson();
			
			for (int i = 0; i < rs1.getColumns().size(); i++) {
				
				 JoinResult.Row row = rs1.getColumns().get(i);
				
			
				 
				aJson.addValue("id", row.left_owner + "." + row.left_table_name + "." + row.left_column_name + "." + id);
				aJson.addValue("label",  row.left_column_name);
				
				 
				
				aJson.newRow();
				
				
				aJson.addValue("id", row.right_owner + "." + row.right_table_name + "." + row.right_column_name+ "." + id);
				aJson.addValue("label",  row.right_column_name);
				
				
				
				aJson.newRow();
				
				
				
				// -- {"from": "1", "to": "3"} 
				
				bJson.addValue("from", row.left_owner + "." + row.left_table_name + "." + row.left_column_name+ "." + id);
				bJson.addValue("to", row.right_owner + "." + row.right_table_name + "." + row.right_column_name+ "." + id);
				
				
				bJson.newRow();
				
				
				bJson.addValue("from", row.right_owner + "." +row.right_table_name);
				bJson.addValue("to", row.right_owner + "." + row.right_table_name + "." + row.right_column_name+ "." + id);
				
				bJson.newRow();
				
				bJson.addValue("from", row.left_owner + "." +row.left_table_name);
				bJson.addValue("to", row.left_owner + "." + row.left_table_name + "." + row.left_column_name+ "." + id);				
				
				
				bJson.newRow();
				
				id ++;
			}
			
		 
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		aJson.endJson();
		bJson.endJson();
		
		
		result = "{ \"nodes\" : " + aJson.getJson() + " , \"edges\": "  + bJson.getJson() + " }" ;
		
		
		
		System.out.println(result);		
		
		
		
		return result;
		
	}
}
