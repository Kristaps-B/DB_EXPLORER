package Models;

import Database.SQLLite;
import Global.Session;
import Json.ArrayJson;
import Results.TableResult;
import Results.ViewTableResult;
import Results.ViewsResult;

public class ViewInformationMod {
	
	
	
	
	public String getViewName (String viewId ) {
		
		String result = "";
		
		
 
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewsResult rs = new ViewsResult();
		
		String sql = "SELECT id, view_id, owner, view_name FROM all_views WHERE id = " + viewId;
		
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
		
		String viewId = Session.currentViewId;
		
		
		
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
	
	
}
