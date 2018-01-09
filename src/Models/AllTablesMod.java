package Models;

import Database.DbUtils;
import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.ColumnResult;
import Results.CountResult;
import Results.FkResult;
import Results.IdResult;
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
			
			
			queryFk (  );
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	private void saveTable(TableResult.Row row) throws Exception {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into tables"
		+ "(table_id, owner, table_name, examine_time) "
		+ "VALUES (" 
		+ "'" + row.table_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.table_name  + "',"
		+ "NULL"
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
		
		String sql  = "UPDATE tables " +
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
		System.out.println("Offset: " + offset);
		
		String sql = "SELECT id, table_id, owner, table_name, examine_time FROM tables ";
		
		if (limit != -1 && offset != -1) {
			
			sql +=  " LIMIT " + limit + "  OFFSET " + offset;
			
		}
		
		System.out.println("SQL: " + sql);
				
		
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
			
			// queryFk(owner, table);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
	}
	
	
	private void saveColumns (ColumnResult columResult) {
		
		String sql = "";
		

		
		SQLLite sqlLite = new SQLLite();
		
		for (ColumnResult.Row row: columResult.getColumns()) {
			
			sql = "insert into columns"
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
	
	private void queryFk (  ) {
		
		String activeUsers =  new UserUtils().loadActiveUsers();
		DbUtils dbUtils = new DbUtils ();
		
		String sql = "SELECT a.owner left_owner, a.table_name left_table, a.column_name left_column, a.constraint_name,  "
      + " b.owner right_owner, b.table_name right_table, b.column_name right_column "
      + " FROM all_cons_columns a "
      + " JOIN all_constraints c ON a.owner = c.owner AND a.constraint_name = c.constraint_name "
      + " join all_cons_columns b on c.owner = b.owner and c.r_constraint_name = b.constraint_name "
      +  " WHERE c.constraint_type = 'R' "
      /*
      + " AND ((a.table_name = '" + table + "' "
      + " AND a.owner = '" + owner +"' ) OR ("
      + " b.table_name = '" + table +"' "
      + " AND b.owner = '" + owner + "' )) "
      */
     + " AND a.owner IN " + activeUsers.substring(12)  + "  " 
     + " AND  b.owner IN " + activeUsers.substring(12)  +" "
      
      ;
		
		
		System.out.println("Query FK! " + sql);
		SQLOracle oQuerie = new SQLOracle();
		
		FkResult rs = new FkResult();	
		
		
		
		
		try {
			
			oQuerie.queryDatabase(sql, rs);
			
			
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				FkResult.Row row = rs.getColumns().get(i);
				
				System.out.println("FK name: " + row.constraintName);
				
				
				// int left_table_id = dbUtils.getTableId(row.leftOwner, row.leftTableName);
				// int left_column_id = dbUtils.getColumnId(left_table_id, row.leftColumnName);
				
				
				// int right_table_id = dbUtils.getTableId(row.rightOwner, row.rightTableName);
				// int right_column_id = dbUtils.getColumnId(right_table_id, row.rightColumnName);
				
				
				System.out.println(row.leftTableName + "." + row.leftColumnName + "   -   "  +  row.rightTableName + "." + row.rightColumnName  );
				
				if (dbUtils.joinExists(row.leftOwner, row.rightOwner, row.leftTableName, row.rightTableName, row.leftColumnName, row.rightColumnName) == false) {
					System.out.println("Saving join");
					
					
					
					dbUtils.saveTableJoin(row.leftOwner, row.rightOwner, row.leftTableName, row.rightTableName, row.leftColumnName, row.rightColumnName);
					
				    int joinId = dbUtils.findJoinId(row.leftOwner, row.rightOwner, row.leftTableName, row.rightTableName, row.leftColumnName, row.rightColumnName);
				    int tableId = dbUtils.getTableId(row.leftOwner, row.leftTableName);
				    
				    dbUtils.saveJoinLink(tableId, joinId, "TABLE");
					
					
					
				} else {
					System.out.println("Join already exists!");
				}
				
			}				
			
			 
			
			
	 
			
			// saveColumns(rs);
			
			
			
			// update_examine_time ( owner, table );
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	

	
}



