package Database;

import Global.Session;
import Results.CountResult;
import Results.IdResult;
import Results.NameResult;

public class DbUtils {
	public DbUtils () {
		
	}
	
	public int getTableId (String owner, String table) {
		int result = -1;
		
		
		
		
		String sql = "SELECT table_id id FROM tables WHERE table_name = '" + table + "' AND owner = '" + owner + "' ";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		return result;
		
		
	}
	
	
	public int getViewId (String owner, String view) {
		int result = -1;
		
		
		String sql = "SELECT id FROM views WHERE view_name = '" + view + "' AND owner = '" + owner + "' ";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}		
		
		return result;
		
	}
	
	
	public int getPlsqlId (String owner, String name) {
		int id = -1;
		
		String sql = "SELECT plsql_id id FROM plsql WHERE name = '" + name + "' AND owner = '" + owner + "' ";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			id = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		return id;
		
	}
	
	
	public String getTableName (String  owner, int tableId) {
		String result = null;
		

		String sql = "SELECT table_name name FROM tables WHERE id = " + tableId + " AND owner = '" + owner + "' ";
		
		
		SQLLite sqlLite = new SQLLite();
		
		NameResult rs = new NameResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).name;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}		
		
		
		return result;
		
	}
	
	
	public int getColumnId (int table_id, String column) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM columns " + 
		" WHERE table_id = " + table_id + " " +
		" AND column_name = '" + column + "'";
				;
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		return result;
		
		
	}	
	
	
	public  boolean joinExists (
			String leftOwner,
			String rightOwner,
			String leftTable,
			String rightTable,
			String leftColumn,
			String rightColumn
			) {
		boolean result = false;
		
		
		
		System.out.println("JoinExists");
		
		String sql = "SELECT COUNT(1) CNT FROM joins " + 
		" WHERE " +
		" ( left_owner = '" + leftOwner + "' " +
		" AND right_owner = '" + rightOwner + "' " +
		" AND left_table = '" + leftTable + "' " +
		" AND right_table = '" + rightTable + "' " +
		" AND left_column = '" + leftColumn + "' " +
		" AND right_column = '" + rightColumn + "' " +
		" ) OR " +
		" ( " +
		"  left_owner = '" + rightOwner + "' " +
		" AND right_owner = '" + leftOwner + "' " +
		" AND left_table = '" + rightTable + "' " +
		" AND right_table = '" + leftTable + "' " +
		" AND left_column = '" + rightColumn + "' " +
		" AND right_column = '" + leftColumn + "' " +
		" ) "
		;
		
		System.out.println(sql);
				
		
		
		SQLLite sqlLite = new SQLLite();
		
		CountResult rs = new CountResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			int cnt = rs.getColumns().get(0).cnt;
			
			if (cnt > 0) {
				result = true;
			}
			
			
		} catch (Exception e) {
			System.out.println("DbUtils.joinExists: " + e.getMessage());
			
			
		}
		
		
		
		return result;
		
	}
	
	
	
	public int findJoinId (
			String leftOwner,
			String rightOwner,
			String leftTable,
			String rightTable,
			String leftColumn,
			String rightColumn	
				
		 ) {
			int result = -1;
			
			
			System.out.println("FindJoinId");
			
			String sql = "SELECT ID  "
					+ "FROM joins " + 
			" WHERE " +
			" ( left_owner = '" + leftOwner + "' " +
			" AND right_owner = '" + rightOwner + "' " +
			" AND left_table = '" + leftTable + "' " +
			" AND right_table = '" + rightTable + "' " +
			" AND left_column = '" + leftColumn + "' " +
			" AND right_column = '" + rightColumn + "' " +
			" ) OR " +
			" ( " +
			" left_owner = '" + rightOwner + "' " +
			" AND right_owner = '" + leftOwner + "' " +
			" AND left_table = '" + rightTable + "' " +
			" AND right_table = '" + leftTable + "' " +
			" AND left_column = '" + rightColumn + "' " +
			" AND right_column = '" + leftColumn + "' " +
			" ) "
			;
					
			
			
			SQLLite sqlLite = new SQLLite();
			
			IdResult rs = new IdResult();
			
			try {
				sqlLite.query(sql, rs, Session.dBUserString);
				
				result = rs.getColumns().get(0).id;
				
			 
				
				
			} catch (Exception e) {
				System.out.println("DbUtils.findJoinId: " + e.getMessage());
				
				
			}		
			
			
			return result;
			
		}
		
	
	
	public void saveTableJoin (
			String leftOwner,
			String rightOwner,
			String leftTable,
			String rightTable,
			String leftColumn,
			String rightColumn	
				) {
			
			SQLLite  sqlLite = new SQLLite();
			
			System.out.println("SaveTableJoin");
			
			String sql = "insert into joins "
			+ "(left_owner, right_owner, left_table, right_table, left_column, right_column) "
			+ "VALUES (" 
			+ " '" + leftOwner  + "', "
		    + " '" + rightOwner  + "', "
		    + " '" + leftTable  + "', "
		    + " '" + rightTable  + "', "
		    + " '" + leftColumn  + "', "
		    + " '" + rightColumn  + "' "
			+ ")"
			;
			
			
			
			try {
				sqlLite.insertUpdate(sql, Session.dBUserString);
				
				System.out.println("TABLE_JOIN created! ");
				
			} catch (Exception e) {
				System.out.println("DbUtils.saveTableJoin: " +e.getMessage());
			}
			
			
			
		}
	
	
	
	
	
	
}
