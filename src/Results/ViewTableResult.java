package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ViewsResult.Row;

public class ViewTableResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("VIEW_ID"), 
					rs.getInt("TABLE_ID"),
					rs.getString("OWNER"),
					rs.getString("TABLE_NAME"),
					rs.getString("ALIAS")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int view_id;
			public int table_id;
			public String owner;
			public String table_name;
			public String alias;
			
			
			
			public Row (int id, int view_id, int table_id, String owner, String table_name, String alias) {
				this.id = id;
				this.view_id = view_id;
				this.table_id = table_id;
				this.owner = owner;
				this.table_name = table_name;
				this.alias = alias;
			}
			

			
			
		}
}
