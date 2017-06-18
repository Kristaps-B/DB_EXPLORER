package Results;

import java.sql.ResultSet;
import java.util.ArrayList;



public class ViewColumnResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getString("TABLE_NAME"),
					rs.getString("COLUMN_NAME"), 
					rs.getString("DATA_TYPE")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public String table_name;
			public String column_name;
			public String data_type;	
 
			
			
			public Row (int id, String tableName, String columnName, String dataType) {
				this.id = id;
				this.table_name = tableName;
				this.column_name  = columnName;
				this.data_type = dataType;
			}
			

			
			
		}
}
