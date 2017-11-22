package Models;

import java.sql.ResultSet;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.Result;
import Results.UsersResult;

public class AllUsersMod {
	
	
	public AllUsersMod () {
		
	}
	
	
	public void queryUsers() {
		
		
		SQLOracle oQuerie = new SQLOracle();
		
		UsersResult  rs = new UsersResult();
		
		
		String sql = "SELECT rownum id, user_id, username, '' active FROM all_users"
				+ " WHERE username NOT IN ('ANONYMOUS','CTXSYS','DBSNMP','EXFSYS', 'APEX_040200', 'AUDSYS', 'SYSBACKUP', 'SYSDG', 'SYSKM', "
				+ " 'LBACSYS', 'MDSYS','MGMT_VIEW','OLAPSYS','OWBSYS','ORDPLUGINS', 'GSMADMIN_INTERNAL', 'GSMUSER', 'APPQOSSYS',  "
				+ "  'ORDSYS','OUTLN', 'SI_INFORMTN_SCHEMA','SYS','SYSMAN','SYSTEM', 'GSMCATUSER', 'OJVMSYS', 'ORDDATA', "
				+ "  'TSMSYS','WK_TEST','WKSYS', 'WKPROXY','WMSYS','XDB','APEX_040000', 'DVSYS', 'APEX_040200', 'DVF',    "
				+ "  'APEX_PUBLIC_USER','DIP', 'FLOWS_30000','FLOWS_FILES','MDDATA', 'PDBADMIN',    "
				+ "  'ORACLE_OCM','SPATIAL_CSW_ADMIN_USR', 'SPATIAL_WFS_ADMIN_USR', "
				+ "  'XS$NULL','PUBLIC')  ";
		
		
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
		//	while (rs.next()) {
		//		System.out.println("USERNAME: " );
		//	}
			
		System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				UsersResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.username);
				
				saveUser(row);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
		}
		
	}
	
	
	private void saveUser(UsersResult.Row row) throws Exception {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into users"
		+ "(user_id, username, active) "
		+ "VALUES (" 
		+ "'" + row.user_id  + "',"
		+ "'" + row.username  + "',"
		+ "'" + 'N'  + "')"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("USER " + row.username + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	public String loadUsers() {
		String res = "";
		
		SQLLite sqlLite = new SQLLite();
		
		UsersResult rs = new UsersResult();
		
		String sql = "SELECT id, user_id, username, active FROM users";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			System.out.println("---------LOAD_USERS--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				UsersResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(row.id + " " + row.username);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("user_id", "" + row.user_id);
				aJson.addValue("username", row.username);
				aJson.addValue("active", row.active);
				
				aJson.newRow();
			}
			
			aJson.endJson();
			
			
			res = aJson.getJson();
			
			
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e);
		}
		
		
		return res;
	}
	
	
	
	public void updateActive(int id, String active) throws Exception {
		
		try {
			
			String sql = "update users set active = '" + active + "' WHERE id = " + id;
					
					;
			
			
			SQLLite  sqlLite = new SQLLite();
			
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
}
