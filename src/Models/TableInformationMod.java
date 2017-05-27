package Models;

import Database.SQLLite;
import Global.Session;
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
	
	
}
