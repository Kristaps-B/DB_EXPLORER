package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ArgumentsResult.Row;

public class DmlResult extends Result {

	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getString("OWNER"), 
					rs.getString("NAME"),
					rs.getString("TYPE")
					));			
			
		}
		
		
		
	
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public String owner;
			public String name;	
			public String type;
 
			
			
			public Row (int id, String owner, String name, String type) {
				this.id             = id;
				this.owner          = owner;
				this.name           = name;
				this.type           = type;
			}
			

			
			
		}	
	
}
