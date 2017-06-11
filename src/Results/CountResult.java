package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableJoinResult.Row;

public class CountResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("CNT")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int cnt;
			
			
			public Row (int cnt) {
				this.cnt = cnt;
			}
			

			
			
		}
}
