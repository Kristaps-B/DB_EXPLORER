package Results;

import java.sql.ResultSet;
import java.util.ArrayList;



public class ViewColumnResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("VIEW_ID"),
					rs.getString("OBJECT_OWNER"),
					rs.getString("OBJECT_NAME"), 
					rs.getString("COLUMN_NAME"),
					rs.getString("ALIAS")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int view_id;
			public String object_owner;
			public String object_name;
			public String column_name;	
			public String alias;	
 
			
			
			public Row (int id, int viewId, String objectOwner, String objectName, String columnName, String alias) {
				this.id = id;
				this.view_id = viewId;
				this.object_owner = objectOwner;
				this.object_name  = objectName;
				this.column_name = columnName;
				this.alias = alias;
			}
			

			
			
		}
}
