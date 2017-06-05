package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.ColumnResult;
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
			sqlLite.query(sql, rs);
			
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
			sqlLite.query(sql, rs);
			
			
			System.out.println("---------LOAD_COLUMNS--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ColumnResult.Row row = rs.getColumns().get(i);
				
				System.out.println(" " + row.table_id);
				 
				
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
	
	
}
