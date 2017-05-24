package Models;

import Database.SQLLite;
import Global.Session;
import Results.TableResult;
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
	
	
}
