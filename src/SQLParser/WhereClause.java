package SQLParser;

public class WhereClause {
	
	
	private String sql;
	
	public WhereClause () {
		
		
	}
	
	
	
	public void addWhereString (String sql) {
		this.sql = sql;
	}
	
	
	/*
	
	private void splitWhereClauses () {

		String [] whereArray = this.whereString.split("AND");
		
		System.out.println("Split WHERE");
		
		for (String str: whereArray) {
			
			str = str.trim();
			
			selectColumnList.add(str);
			
			System.out.println(str);
		}		
		
	}
	
	*/
	
}
