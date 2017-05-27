package SQLParser;

public class FromClause {
	
	
	private String sql;

	public FromClause () {
		
	}
	
	
	public void addFromString (String sql) {
		this.sql = sql;
	}
	
	
	/*
	
	private void splitFromClauses () {
		
		String [] fromArray = this.fromString.split(",");
		
		System.out.println("Split FROM");
		
		for (String str: fromArray) {
			
			str = str.trim();
			
			selectColumnList.add(str);
			
			System.out.println(str);
		}
	}
	
	*/
	
}
