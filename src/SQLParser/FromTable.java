package SQLParser;

public class FromTable {
	
	private String sql;
	
	private String owner = "";
	private String alias = "";
	private String table = "";
	
	private boolean isSubquery = false;
	
	
	private MainSelectQuery selectParser;
	private ParserUtils parserUtils;
	
	private SelectQuery mainQuery;

	public FromTable (String sql, SelectQuery mainQuery) {
		
		this.sql = sql;
		
		this.sql = this.sql.trim();
		this.sql = this.sql.replaceAll(" AS ", " ");
		
		this.mainQuery = mainQuery;
		parserUtils = new ParserUtils();
	
		
		// splitAlias(this.sql);
		
		this.table = parserUtils.getFirstPart(this.sql, " ");
		this.alias = parserUtils.getSecondPart(this.sql, " ");
		
		
		
		// Get Owner if exists
		this.owner = parserUtils.getFirstPart(this.table, ".");
		this.table = parserUtils.getSecondPart(this.table, ".");
		
		if (this.table.equals("")) {
			this.table= this.owner;
			this.owner = "";
		}
		
		
 
		 
		 System.out.println("owner: " + this.owner);
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
	
	public String getOwner () {
		return this.owner;
	}
	
	

	private void checkWith(String table) {
		
		WithClause withClause = mainQuery.getWith();
		
		MainSelectQuery withTableParser = withClause.getSelectParser(table);
		
		if (withTableParser != null) {
			
			System.out.println("With SUBSTITUTION!!! table: " + table);
			// System.out.println("Table Query: " + withTableParser.getSelectQuery().getQuery());
			
			isSubquery = true;
			this.selectParser = withTableParser;
			
		}
		
		
	}
	
	private void  checkSubquery(String table) {
		
		if (parserUtils.isQuery(table) == true) {
			
			System.out.println("Its subquery");
			isSubquery = true;
			
			String subquery = parserUtils.getBracketsContent(table);  //  table.substring(1, table.length() - 1);
			
			System.out.println("Subquery: " + subquery);
			
			
			this.selectParser = new MainSelectQuery(subquery);
		}
		
	}
	
	
	public boolean getIsSubstring () {
		return this.isSubquery;
	}
	
	
	public MainSelectQuery getSubquery () {
		
		return selectParser;
		
	}
	
	
 
	
}
