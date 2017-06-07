package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.ColumnResult;
import Results.TableJoinResult;
import Results.TableResult;
import Results.ViewsResult;

public class TableInformationMod {
	public TableInformationMod () {
		
		
	}
	
	
	public String getTableName (String tableId ) {
		
		String result = "";
		
		
 
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		String sql = "SELECT id, table_id, owner, table_name FROM all_tables WHERE id = " + tableId;
		
		System.out.println(sql);
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).table_name;
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		
		
		return result;
		
	}
	
	
	
	public String loadColumns() {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		ColumnResult rs = new ColumnResult();
		
		String sql = "SELECT id, column_id, table_id, column_name, data_type FROM all_columns  WHERE table_id = " + Session.currentTableId + " ORDER BY column_id ASC";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_COLUMNS--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ColumnResult.Row row = rs.getColumns().get(i);
				
			
				 
				
				aJson.addValue("column_id", "" + row.column_id);
				aJson.addValue("column_name", "" + row.column_name);
				aJson.addValue("data_type", row.data_type);
				 
				
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
	
	
	
	
	public String loadTableJoins() {
		
		String result = "";
		

		
		SQLLite sqlLite = new SQLLite();
		
		TableJoinResult rs = new TableJoinResult();
		
		String sql = "SELECT j.id, tl.table_name left_table_name, tr.table_name right_table_name, cl.column_name left_column_name, cr.column_name right_column_name " + 
		" FROM all_table_joins j, all_tables tl, all_tables tr, all_columns cl, all_columns cr  " + 
		" WHERE 1=1 " + 
		" AND j.left_table_id     = tl.id " +
		" AND j.right_table_id    = tr.id " +
		" AND j.left_column_id    = cl.id " +
		" AND j.right_column_id   = cr.id " +
		" AND ( " +
		" j.left_table_id      = " + Session.currentTableId +
		" OR j.right_table_id   = " + Session.currentTableId +
		" )"
		;
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("--------- LOAD_TABLE_JOINS --------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableJoinResult.Row row = rs.getColumns().get(i);
				
			
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("left_table_name", "" + row.left_table_name);
				aJson.addValue("right_table_name", "" + row.right_table_name);
				aJson.addValue("left_column_name", "" + row.left_column_name);
				aJson.addValue("right_column_name", "" + row.right_column_name);
				 
				
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
