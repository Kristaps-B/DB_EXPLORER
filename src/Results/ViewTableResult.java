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
					rs.getString("SOURCE_OWNER"),
					rs.getString("SOURCE_NAME"),
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
			public String source_owner;
			public String source_name;
			public String alias;
			
			
			
			public Row (int id, int view_id, String source_owner, String source_name, String alias) {
				this.id = id;
				this.view_id = view_id;
				this.source_owner = source_owner;
				this.source_name = source_name;
				this.alias = alias;
			}
			

			
			
		}
}
