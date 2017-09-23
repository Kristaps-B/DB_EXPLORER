package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableResult.Row;

public class ViewsResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("VIEW_ID"), 
					rs.getString("OWNER"),
					rs.getString("VIEW_NAME"),
					rs.getString("EXAMINE_TIME")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int view_id;
			public String owner;	
			public String view_name;
			public String examine_time;
			
			
			public Row (int id, int view_id, String owner, String view_name, String examine_time) {
				this.id = id;
				this.view_id = view_id;
				this.owner = owner;
				this.view_name = view_name;
				this.examine_time = examine_time;
			}
			

			
			
		}
}
