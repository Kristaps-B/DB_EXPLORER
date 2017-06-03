package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableResult.Row;

public class ColumnResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("COLUMN_ID"), 
					rs.getInt("TABLE_ID"),
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
			public int column_id;
			public String column_name;	
			public int table_id;
			public String data_type;
			
			
			public Row (int id, int column_id, int table_id, String column_name, String data_type) {
				this.id           = id;
				this.column_id    = column_id;
				this.table_id     = table_id;
				this.column_name  = column_name;
				this.data_type    = data_type;
			}
			

			
			
		}
}

