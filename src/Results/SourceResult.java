package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.PlsqlResult.Row;

public class SourceResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("LINE"),
					rs.getString("TEXT")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int line;
			public String text;
			
			
			public Row (int line, String text) {
				this.line = line;
				this.text = text;
			}
			

			
			
		}
}
