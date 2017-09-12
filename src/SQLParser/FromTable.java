package SQLParser;

public class FromTable {
	
	private String sql;
	
	
	private String alias = "";
	private String table = "";
	
	private boolean isSubquery = false;
	
	
	SelectParser selectParser;
	
	ParserUtils parserUtils;

	public FromTable (String sql) {
		
		this.sql = sql;
		
		this.sql = this.sql.trim();
		
		parserUtils = new ParserUtils();
	
		
		// splitAlias(this.sql);
		
		this.table = parserUtils.getFirstPart(this.sql, " ");
		this.alias = parserUtils.getSecondPart(this.sql, " ");
		 
		 System.out.println("table: " + this.table);
		 System.out.println("alias: " + this.alias);
		 
		 
		 checkSubquery(this.table);
		
		
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	public String getTable () {
		return this.table;
	}
	
	

	
	
	private void  checkSubquery(String table) {
		
		if (table.charAt(0) == '(') {
			
			System.out.println("Its subquery");
			isSubquery = true;
			
			String subquery =  table.substring(1, table.length() - 1);
			
			System.out.println("Subquery: " + subquery);
			
			
			selectParser = new SelectParser(subquery);
		}
		
	}
	
	
	public boolean getIsSubstring () {
		return this.isSubquery;
	}
	
	
	public SelectQuery getSubquery () {
		
		return selectParser.getSelectQuery();
		
	}
	
	
 
	
}
