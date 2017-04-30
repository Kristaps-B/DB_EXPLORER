package Results;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UsersResult extends Result {
	
	private ArrayList <Row> columns = new ArrayList <Row> ();
	
	
	public void copyRows(ResultSet rs) throws Exception {
		
		
		
		while (rs.next()) {
			

			
			columns.add(new Row(
					rs.getInt("id"),
					rs.getInt("user_id"), 
					rs.getString("username")
					));			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <Row> getColumns () {
		return columns;
	}
	
	
	
	 public class Row {
			
			
			public int id;
			public int user_id;
			public String username;	
			
			
			public Row (int id, int user_id, String username) {
				this.id = id;
				this.user_id = user_id;
				this.username = username;
				
			}
			

			
			
		}
	
}




