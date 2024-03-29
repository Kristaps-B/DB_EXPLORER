package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.TableResult.Row;

public class PlsqlResult extends Result {
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("PLSQL_ID"),
					rs.getString("OWNER"),
					rs.getString("NAME"),
					rs.getString("TYPE"),
					rs.getString("PARENT"),
					rs.getString("EXAMINE_TIME")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int plsql_id;
			public String owner;
			public String name;	
			public String type;
			public String parent;
			public String examine_time;
			
			
			public Row (int id, int plsql_id, String owner,  String name, String type, String parent, String examine_time) {
				this.id           = id;
				this.plsql_id     = plsql_id;
				this.owner        = owner;
				this.name         = name;
				this.type         = type;
				this.parent       = parent;
				this.examine_time = examine_time;
			}
			

			
			
		}
}
