package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableResult.Row;

public class ViewJoinResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getString("LEFT_TABLE_NAME"), 
					rs.getString("LEFT_COLUMN_NAME"),
					rs.getString("RIGHT_TABLE_NAME"),
					rs.getString("RIGHT_COLUMN_NAME")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public String leftTableName;
			public String leftColumnName;	
			public String rightTableName;
			public String rightColumnName;
			
			
			public Row (int id, String leftTableName, String leftColumnName, String rightTableName, String rightColumnName) {
				this.id = id;
				this.leftTableName = leftTableName;
				this.leftColumnName = leftColumnName;
				this.rightTableName = rightTableName;
				this.rightColumnName = rightColumnName;
			}
			

			
			
		}
}
