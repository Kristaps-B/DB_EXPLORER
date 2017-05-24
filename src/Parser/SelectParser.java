package Parser;

public class SelectParser {
	private String sql = "";
	
	
	private int select_start_index = -1;
	private int select_end_index   = -1;
	private int from_start_index   = -1;
	private int from_end_index     = -1;
	private int where_start_index  = -1;
	private int where_end_index    = -1;
	
	
	public SelectParser (String sql) {
		
		this.sql = sql;
		splitClauses();
	}
	
	
	private void splitClauses() {
		this.sql = this.sql.toUpperCase();
		
		for (int  i = 0; i < this.sql.length(); i ++) {
			
			char c = this.sql.charAt(i);
			
			System.out.println(i + ") " + c);
			
			
			if (select_start_index == -1) {
				
			}
		}
		
	}
	
	
	private boolean AtLocation (int index, String search, String fullString) {
		
		boolean result = false;
		
		String sbstr = fullString.substring(index, index + search.length());
		
		
		if (sbstr.equals(search)) {
			return true;
		}
		
		
		return result;
	}
	
	
	
	
}
