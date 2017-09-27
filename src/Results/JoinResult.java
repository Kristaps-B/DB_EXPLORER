package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.UsersResult.Row;

public class JoinResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getString  ("LEFT_TABLE_NAME"), 
					rs.getString ("RIGHT_TABLE_NAME"),
					rs.getString("LEFT_COLUMN_NAME"),
					rs.getString("RIGHT_COLUMN_NAME"),
					rs.getString("LEFT_OWNER"),
					rs.getString("RIGHT_OWNER")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			

			public String left_table_name;
			public String right_table_name;	
			public String left_column_name;
			public String right_column_name;
			public String left_owner;
			public String right_owner;
			
			
			public Row (String left_table_name, String right_table_name, String left_column_name, String right_column_name, String left_owner, String right_owner) {
			
				this.left_table_name    = left_table_name;
				this.right_table_name   = right_table_name;
				this.left_column_name   = left_column_name;
				this.right_column_name  = right_column_name;
				this.left_owner         = left_owner;
				this.right_owner        = right_owner;
			}
			

			
			
		}
}
