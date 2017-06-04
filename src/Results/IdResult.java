package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ColumnResult.Row;

public class IdResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID")
					));			
			
		}
		
		
		
	
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			
			
			public Row (int id) {
				
				this.id           = id;
				

			}
			

			
			
		}	
}
