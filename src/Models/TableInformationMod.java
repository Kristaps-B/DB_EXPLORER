package Models;

import Database.DbUtils;
import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.ColumnResult;
import Results.TableJoinResult;
import Results.TableResult;
import Results.ViewsResult;

public class TableInformationMod {
	
	
	
	private DbUtils dbUtils = new DbUtils();
	
	public TableInformationMod () {
		
		
	}
	
	
	public String getTableName (String tableId ) {
		
		String result = "";
		
		
 
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM tables WHERE id = " + tableId;
		
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
		
		int tableId = dbUtils.getTableId(Session.owner, Session.tableName);
		
		String sql = "SELECT id, column_id, table_id, column_name, data_type FROM columns  WHERE table_id = " + tableId + " ORDER BY column_id ASC";
		
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
		
		
		//String tableName = dbUtils.getTableName(Session.dBUserString , Integer.parseInt(Session.currentTableId) );
		
		
		
		
		
		String sql = "SELECT j.id,"
				+ " j.left_table left_table_name, "
				+ " j.right_table right_table_name,"
				+ " j.left_column left_column_name,"
				+ " j.right_column right_column_name " + 
		" FROM joins j " + 
		" WHERE 1=1 " + 
	    
		"AND '" + Session.owner + "' IN (j.left_owner, j.right_owner) " +
		"AND '" + Session.tableName + "' IN (j.left_table, j.right_table) " +
	
		""
		;
		
		System.out.println("SQL: " + sql);
		
		
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
