package Models;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.ColumnResult;
import Results.TableResult;
import Results.UsersResult;
import Utils.UserUtils;

public class AllTablesMod {
	
	
	public AllTablesMod() {
		
		
	}
	
	
	public void updateTables () {
		
		
		String activeUsers =  new UserUtils().loadActiveUsers();
		
	
			
		System.out.println("ACTIVE_USERS: " + activeUsers);
		
		
		String sql = "SELECT rownum ID, rownum TABLE_ID, OWNER, TABLE_NAME, '' examine_time FROM all_tables WHERE 1=1 " + activeUsers;
		
		SQLOracle oQuerie = new SQLOracle();
		
		TableResult  rs = new TableResult();
		 
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.table_name);
				
				 
				this.saveTable(row);
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	private void saveTable(TableResult.Row row) throws Exception {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_tables"
		+ "(table_id, owner, table_name, examine_time) "
		+ "VALUES (" 
		+ "'" + row.table_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.table_name  + "',"
		+ "DATETIME('now', 'localtime')"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("TABLE " + row.table_name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	private void update_examine_time (String owner, String tableName) throws Exception {
		SQLLite sqlLite = new SQLLite();
		
		String sql  = "UPDATE all_tables " +
		"SET examine_time = DATETIME('now', 'localtime')" +
	    "WHERE owner = '" + owner +"' AND table_name = '" + tableName + "' ";
		
		
		try {
			
			System.out.println("Updating examine_time " + owner + " table_name: " + tableName);
			
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
		 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	public String loadTables (int limit, int offset) {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		TableResult rs = new TableResult();
		
		
		System.out.println("LIMIT: " + limit);
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM all_tables LIMIT " + limit + "  OFFSET " + offset;
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_TABLES--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				TableResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("table_id", "" + row.table_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("table_name", row.table_name);
				aJson.addValue("examine_time", row.examine_time);
				
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
	
	
	
	public void analyseTable(int id, String owner, String table) {
		
		String result = "";
		
		
		SQLOracle oQuerie = new SQLOracle();
		
		ColumnResult rs = new ColumnResult();
		
		String sql = "SELECT rownum id, column_id, column_name,  " + id + " table_id, data_type FROM all_tab_cols WHERE owner = '" + owner + "' AND table_name = '" + table + "'";
		
		System.out.println(sql);
		
		try {
			
			oQuerie.queryDatabase(sql, rs);
			
			
			
			// ColumnResult.Row r =  rs.getColumns().get(0);
			
			saveColumns(rs);
			
			
			
			update_examine_time ( owner, table );
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void saveColumns (ColumnResult columResult) {
		
		String sql = "";
		
		SQLLite sqlLite = new SQLLite();
		
		for (ColumnResult.Row row: columResult.getColumns()) {
			
			sql = "insert into all_columns"
					+ "(column_id, column_name, data_type, table_id) "
					+ "VALUES (" 
					+ "'" + row.column_id  + "',"
					+ "'" + row.column_name  + "',"
					+ "'" + row.data_type  + "',"
					+ "'" + row.table_id  + "'"
					+ ")"
					;
			
			
			try {
				
				System.out.println("Inserting column_name: " + row.column_name + " data_type: " + row.data_type + " table_id: " + row.table_id);
				
				sqlLite.insertUpdate(sql, Session.dBUserString);
				
				System.out.println("Inserted column: " + row.column_name);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
	
}
