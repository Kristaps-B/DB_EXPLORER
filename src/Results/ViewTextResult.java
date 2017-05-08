package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ViewsResult.Row;

public class ViewTextResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getString("OWNER"),
					rs.getString("VIEW_NAME"),
					rs.getString("TEXT")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
		
			public String owner;	
			public String view_name;
			public String text;
			
			
			public Row (String owner, String view_name, String text) {

				this.owner = owner;
				this.view_name = view_name;
				this.text = text;
			}
			

			
			
		}
}
