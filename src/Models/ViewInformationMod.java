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
		
		String sql = "SELECT id, view_id, owner, view_name, examine_time FROM views WHERE id = " + viewId;
		
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
		
		String sql = "SELECT vt.id, vt.view_id, vt.source_owner, vt.source_name, vt.alias "  + 
		" FROM view_tables vt " +
		" WHERE 1=1 " +
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
				aJson.addValue("source_owner", "" + row.source_owner);
				aJson.addValue("source_name", "" + row.source_name);
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
		" FROM joins j, join_sources js  " + 
		" WHERE 1=1 " + 
		" AND j.id                = js.join_id " +
		" AND  js.source_type = 'VIEW' " +
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
		
		String sql = "SELECT vc.id, vc.view_id, vc.object_owner, vc.object_name, vc.column_name, vc.alias "  + 
		" FROM view_columns vc " +
		" WHERE 1=1 " +
		" AND vc.view_id = " + viewId +
		" ORDER BY vc.id ASC";
		
	
		
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
				aJson.addValue("view_id",  row.view_id + "");
				aJson.addValue("object_name", "" + row.object_owner + "." + row.object_name);
				aJson.addValue("column_name", "" + row.column_name);
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
	
	
}
