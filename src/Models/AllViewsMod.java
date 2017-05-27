package Models;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.TableResult;
import Results.UsersResult;
import Results.ViewTextResult;
import Results.ViewsResult;
import SQLParser.SelectParser;

public class AllViewsMod {
	
	private String loadActiveUsers () {
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		UsersResult rs = new UsersResult();
		
		String sql = "SELECT id, user_id, username, active FROM all_users WHERE active='Y'";
		
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			if (rs.getColumns().size() > 0 ) {
				
				result = "AND owner IN (";
				
				for (UsersResult.Row row: rs.getColumns()) {
					
					result += "'" + row.username + "',";
				}
				
				result += "'')";
				
			} else {
				result = " AND 1=2";
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return result;
	}	
	
	
	public void updateViews () {
		
		
		String activeUsers = this.loadActiveUsers();
		
		System.out.println("ACTIVE_USERS: " + activeUsers);
		
		
		String sql = "SELECT rownum ID, rownum VIEW_ID, OWNER, VIEW_NAME FROM all_views WHERE 1=1 " + activeUsers;
		
		SQLOracle oQuerie = new SQLOracle();
		
		ViewsResult  rs = new ViewsResult();
		
		
		try {
			
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewsResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.view_name);
				
				 
				 this.saveView(row);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	
	public void saveView (ViewsResult.Row row) {
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_views"
		+ "(view_id, owner, view_name) "
		+ "VALUES (" 
		+ "'" + row.view_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.view_name  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("VIEW " + row.view_name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	
	public String loadViews (String ip, String port, String sid, String username, String password) {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewsResult rs = new ViewsResult();
		
		String sql = "SELECT id, view_id, owner, view_name FROM all_views";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_VIEWS--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewsResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("view_id", "" + row.view_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("view_name", row.view_name);
				
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
	
	
	
	public void analyseView(String ip, String port, String sid, String username, String password,
			String owner, String view_name) {
		
		String sql = "SELECT OWNER, VIEW_NAME, TEXT FROM all_views WHERE owner = '" + owner + "' AND view_name = '" + view_name + "'";
		
		SQLOracle oQuerie = new SQLOracle();
		
		ViewTextResult  rs = new ViewTextResult();
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			String sqlView = rs.getColumns().get(0).text;
			
			System.out.println("VIEW TEXT: " + sqlView);
			SelectParser selectParser = new SelectParser(sqlView);
			
			
			
		} catch (Exception e) {
			
			
			System.out.println(e.getMessage());
		}
		
	}

}
