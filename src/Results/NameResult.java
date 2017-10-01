package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.IdResult.Row;

public class NameResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getString("NAME")
					));			
			
		}
		
		
		
	
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public String name;
			
			
			public Row (String name) {
				
				this.name           = name;
				

			}
			

			
			
		}	
}
