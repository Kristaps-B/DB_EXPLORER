package SQLParser;

public class FromTable {
	
	private String sql;
	
	
	private String alias;
	private String table;

	public FromTable (String sql) {
		
		this.sql = sql;
		
		
	 String [] tableArray =	this.sql.split(" ");
	 
	 
	 this.table = tableArray[0];
	 
	 if (tableArray.length > 1) {
		 this.alias = tableArray[1];
	 }
	 
	 System.out.println("table: " + this.table);
	 System.out.println("alias: " + this.alias);
		
		
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	public String getTable () {
		return this.table;
	}
	
}
