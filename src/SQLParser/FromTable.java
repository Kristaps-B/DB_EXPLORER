package SQLParser;

public class FromTable {
	
	private String sql;
	
	
	private String alias = "";
	private String table = "";
	
	private boolean isSubquery = false;
	
	
	private SelectParser selectParser;
	private ParserUtils parserUtils;
	
	private SelectQuery mainQuery;

	public FromTable (String sql, SelectQuery mainQuery) {
		
		this.sql = sql;
		
		this.sql = this.sql.trim();
		this.mainQuery = mainQuery;
		parserUtils = new ParserUtils();
	
		
		// splitAlias(this.sql);
		
		this.table = parserUtils.getFirstPart(this.sql, " ");
		this.alias = parserUtils.getSecondPart(this.sql, " ");
		 
		 System.out.println("table: " + this.table);
		 System.out.println("alias: " + this.alias);
		 
		 
		 checkWith(this.table);
		 
		 
		 checkSubquery(this.table);
		
		
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	public String getTable () {
		return this.table;
	}
	
	

	private void checkWith(String table) {
		
		WithClause withClause = mainQuery.getWith();
		
		SelectParser withTableParser = withClause.getSelectParser(table);
		
		if (withTableParser != null) {
			
			System.out.println("With SUBSTITUTION!!! table: " + table);
			System.out.println("Table Query: " + withTableParser.getSelectQuery().getQuery());
			
			isSubquery = true;
			this.selectParser = withTableParser;
			
		}
		
		
	}
	
	private void  checkSubquery(String table) {
		
		if (table.charAt(0) == '(') {
			
			System.out.println("Its subquery");
			isSubquery = true;
			
			String subquery =  table.substring(1, table.length() - 1);
			
			System.out.println("Subquery: " + subquery);
			
			
			this.selectParser = new SelectParser(subquery);
		}
		
	}
	
	
	public boolean getIsSubstring () {
		return this.isSubquery;
	}
	
	
	public SelectQuery getSubquery () {
		
		return selectParser.getSelectQuery();
		
	}
	
	
 
	
}
