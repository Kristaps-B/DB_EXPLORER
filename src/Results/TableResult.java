package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.UsersResult.Row;

public class TableResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("TABLE_ID"), 
					rs.getString("OWNER"),
					rs.getString("TABLE_NAME")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int table_id;
			public String owner;	
			public String table_name;
			
			
			public Row (int id, int table_id, String owner, String table_name) {
				this.id = id;
				this.table_id = table_id;
				this.owner = owner;
				this.table_name = table_name;
			}
			

			
			
		}
}
