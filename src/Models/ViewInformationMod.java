package Models;

import Database.DbUtils;
import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.TableJoinResult;
import Results.TableResult;
import Results.ViewColumnResult;
import Results.ViewJoinResult;
import Results.ViewTableResult;
import Results.ViewsResult;

public class ViewInformationMod {
	
	
	private DbUtils dbUtils = new DbUtils();
	
	public String getViewName (String viewId ) {
		
		String result = "";
		
		
 
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewsResult rs = new ViewsResult();
		
		String sql = "SELECT id, view_id, owner, view_name, examine_time FROM all_views WHERE id = " + viewId;
		
		System.out.println(sql);
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).view_name;
			
			System.out.println(result);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		
		
		return result;
		
	}
	
	
	
	public String loadViewsTables() {
		
		String result = "";
		
		int viewId = dbUtils.getViewId(Session.owner, Session.viewName);
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewTableResult rs = new ViewTableResult();
		
		String sql = "SELECT vt.id, vt.view_id, vt.table_id, t.table_name, vt.alias "  + 
		" FROM all_view_tables vt, all_tables t" +
		" WHERE t.table_id = vt.table_id " +
		" AND vt.view_id = " + viewId +
		" ORDER BY vt.id ASC";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_VIEWS_TABLES--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewTableResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("view_id", "" + row.view_id);
				aJson.addValue("table_id", "" + row.table_id);
				aJson.addValue("table_name", "" + row.table_name);
				aJson.addValue("alias", "" + row.alias);
				
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
	
	
	
	public String loadJoins() {
		String result = "";
		
		
		int viewId = dbUtils.getViewId(Session.owner, Session.viewName);
		
		SQLLite sqlLite = new SQLLite();
		
		ViewJoinResult rs = new ViewJoinResult();
		
		String sql = "SELECT js.id, "
				+ "j.left_owner,"
				+ "j.right_owner,"
				+ "j.left_owner || '.' || j.left_table left_table_name,"
				+ "j.right_owner || '.' || j.right_table right_table_name,"
				+ "j.left_column left_column_name,"
				+ "j.right_column right_column_name " + 
		" FROM all_joins j, join_sources js  " + 
		" WHERE 1=1 " + 
		" AND j.id                = js.join_id " +
		" AND js.source_id        = " + viewId 
		;
		
		try {
			
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("--------- LOAD_VIEW_JOINS --------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewJoinResult.Row row = rs.getColumns().get(i);
				
			
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("left_table_name", "" + row.leftTableName);
				aJson.addValue("right_table_name", "" + row.rightTableName);
				aJson.addValue("left_column_name", "" + row.leftColumnName);
				aJson.addValue("right_column_name", "" + row.rightColumnName);
				 
				
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
	
	
	public String loadColumns() {
		String result = "";
		

		
		int viewId = dbUtils.getViewId(Session.owner, Session.viewName);
		
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewColumnResult rs = new ViewColumnResult();
		
		String sql = "SELECT ac.id, at.table_name, ac.column_name, ac.data_type"  + 
		" FROM column_sources cs, all_columns ac, all_tables at" +
		" WHERE cs.column_id = ac.id " +
		" AND at.id = ac.table_id " +
		" AND cs.source_id = " + viewId +
		" AND cs.source_type = 'VIEW'" +
		" ORDER BY cs.id ASC";
		
	
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_COLUMNS_TABLES--------------");
			System.out.println("SQL: " + sql);
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewColumnResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("table_name",  row.table_name);
				aJson.addValue("column_name", "" + row.column_name);
				aJson.addValue("data_type", "" + row.data_type);
				
	 
				
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
