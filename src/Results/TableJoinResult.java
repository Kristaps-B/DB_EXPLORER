package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableResult.Row;

public class TableJoinResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getString  ("LEFT_TABLE_NAME"), 
					rs.getString ("RIGHT_TABLE_NAME"),
					rs.getString("LEFT_COLUMN_NAME"),
					rs.getString("RIGHT_COLUMN_NAME")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public String left_table_name;
			public String right_table_name;	
			public String left_column_name;
			public String right_column_name;
			
			
			public Row (int id, String left_table_name, String right_table_name, String left_column_name, String right_column_name) {
				this.id = id;
				this.left_table_name    = left_table_name;
				this.right_table_name   = right_table_name;
				this.left_column_name   = left_column_name;
				this.right_column_name  = right_column_name;
			}
			

			
			
		}
}
