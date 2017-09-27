package Database;

import Global.Session;
import Results.CountResult;
import Results.IdResult;

public class DbUtils {
	public DbUtils () {
		
	}
	
	public int getTableId (String owner, String table) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM all_tables WHERE table_name = '" + table + "' AND owner = '" + owner + "' ";
		
		
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
	
	
	public int getColumnId (int table_id, String column) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM all_columns " + 
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
			int leftTableId,
			int rightTableId,
			int leftColumnId,
			int rightColumnId	
			) {
		boolean result = false;
		
		
		String sql = "SELECT COUNT(1) CNT FROM all_table_joins " + 
		" WHERE " +
		" ( left_table_id = " + leftTableId + " " +
		" AND right_table_id = " + rightTableId + " " +
		" AND left_column_id = " + leftColumnId + " " +
		" AND right_column_id = " + rightColumnId + " " +
		" ) OR " +
		" ( left_table_id = " + rightTableId + " " +
		" AND right_table_id = " + leftTableId + " " +
		" AND left_column_id = " + rightColumnId + " " +
		" AND right_column_id = " + leftColumnId + " " +
		" ) "
		;
				
		
		
		SQLLite sqlLite = new SQLLite();
		
		CountResult rs = new CountResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			int cnt = rs.getColumns().get(0).cnt;
			
			if (cnt > 0) {
				result = true;
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		return result;
		
	}
	
	
	
	public int findJoinId (
			int leftTableId,
			int rightTableId,
			int leftColumnId,
			int rightColumnId		
				
		 ) {
			int result = -1;
			
			String sql = "SELECT ID  FROM all_table_joins " + 
			" WHERE " +
			" ( left_table_id = " + leftTableId + " " +
			" AND right_table_id = " + rightTableId + " " +
			" AND left_column_id = " + leftColumnId + " " +
			" AND right_column_id = " + rightColumnId + " " +
			" ) OR " +
			" ( left_table_id = " + rightTableId + " " +
			" AND right_table_id = " + leftTableId + " " +
			" AND left_column_id = " + rightColumnId + " " +
			" AND right_column_id = " + leftColumnId + " " +
			" ) "
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
		
	
	
	public void saveTableJoin (
			int leftTableId,
			int rightTableId,
			int leftColumnId,
			int rightColumnId
				) {
			
			SQLLite  sqlLite = new SQLLite();
			
			String sql = "insert into all_table_joins "
			+ "(left_table_id, right_table_id, left_column_id, right_column_id) "
			+ "VALUES (" 
			+ "" + leftTableId  + ","
			+ "" + rightTableId  + ","
			+ "" + leftColumnId  + ","
			+ "" + rightColumnId  + ""
			+ ")"
			;
			
			
			
			try {
				sqlLite.insertUpdate(sql, Session.dBUserString);
				
				System.out.println("TABLE_JOIN created! ");
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
			
		}
	
	
}
