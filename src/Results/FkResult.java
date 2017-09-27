package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ViewJoinResult.Row;

public class FkResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getString("CONSTRAINT_NAME"),
					rs.getString("LEFT_OWNER"), 
					rs.getString("LEFT_TABLE"), 
					rs.getString("LEFT_COLUMN"),
					rs.getString("RIGHT_OWNER"), 
					rs.getString("RIGHT_TABLE"),
					rs.getString("RIGHT_COLUMN")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public String constraintName;
			public String leftOwner;
			public String leftTableName;
			public String leftColumnName;	
			public String rightOwner;
			public String rightTableName;
			public String rightColumnName;
			
			
			public Row (String constraintName, String leftOwner, String leftTableName, String leftColumnName, String rightOwner, String rightTableName, String rightColumnName) {
				this.constraintName = constraintName;
				this.leftOwner = leftOwner;
				this.leftTableName = leftTableName;
				this.leftColumnName = leftColumnName;
				this.rightOwner = rightOwner;
				this.rightTableName = rightTableName;
				this.rightColumnName = rightColumnName;
			}
			

			
			
		}
}
