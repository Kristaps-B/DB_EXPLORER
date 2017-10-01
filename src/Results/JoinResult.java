package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.UsersResult.Row;

public class JoinResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getString  ("LEFT_TABLE"), 
					rs.getString ("RIGHT_TABLE"),
					rs.getString("LEFT_COLUMN"),
					rs.getString("RIGHT_COLUMN"),
					rs.getString("LEFT_OWNER"),
					rs.getString("RIGHT_OWNER")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			

			public String left_table;
			public String right_table;	
			public String left_column;
			public String right_column;
			public String left_owner;
			public String right_owner;
			
			
			public Row (String left_table, String right_table, String left_column, String right_column, String left_owner, String right_owner) {
			
				this.left_table    = left_table;
				this.right_table   = right_table;
				this.left_column   = left_column;
				this.right_column  = right_column;
				this.left_owner         = left_owner;
				this.right_owner        = right_owner;
			}
			

			
			
		}
}
