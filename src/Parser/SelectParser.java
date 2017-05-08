package Parser;

public class SelectParser {
	private String sql = "";
	
	
	private int select_start_index;
	private int select_end_index;
	private int from_start_index;
	private int from_end_index;
	private int where_start_index;
	private int where_end_index;
	
	
	public SelectParser (String sql) {
		
		this.sql = sql;
		splitClauses();
	}
	
	
	private void splitClauses() {
		this.sql = this.sql.toUpperCase();
		
		for (int  i = 0; i < this.sql.length(); i ++) {
			
			char c = this.sql.charAt(i);
			
			System.out.println(i + ") " + c); 
		}
		
	}
	
	
	
	
}
