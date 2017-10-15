package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

import Results.ColumnResult.Row;

public class ArgumentsResult extends Result {
	
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("ID"),
					rs.getInt("PLSQL_ID"), 
					rs.getString("OWNER"),
					rs.getString("ARGUMENT_NAME"),
					rs.getString("DATA_TYPE"),
					rs.getInt("POSITION"),
					rs.getString("IN_OUT")
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
			public String argument_name;
			public String data_type;
			public int position;
			public String in_out;
			
			
			public Row (int id, int plsql_id, String owner, String argument_name, String data_type, int position, String in_out) {
				this.id             = id;
				this.plsql_id       = plsql_id;
				this.owner          = owner;
				this.argument_name  = argument_name;
				this.data_type      = data_type;
				this.position       = position;
				this.in_out         = in_out;
			}
			

			
			
		}
	
}
