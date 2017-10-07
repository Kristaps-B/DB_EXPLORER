package SQLParser;

public class ColumnSelect {
	
	private String sql = "";
	
	private String table = "";
	private String column = "";
	private String alias = "";
	
	ParserUtils parserUtils;
	
	
	private boolean isSubquery = false;
	
	
	public ColumnSelect (String sql) {
		
		this.sql = sql;
		
		parserUtils = new ParserUtils();
		
		this.sql = this.sql.trim();
		
		
		String tableColumn = "";
		
		tableColumn = parserUtils.getFirstPart(this.sql, " ");
		
		this.table = parserUtils.getFirstPart(tableColumn, ".");
		this.column = parserUtils.getSecondPart(tableColumn, ".");
		
		this.alias = parserUtils.getSecondPart(this.sql, " ");
		
		
		if (this.column.equals("")) {
			this.column = this.table;
			this.table = "";
		}
		
		
		// Set alias equal to table, if no alias is present
		if (this.alias.equals("")) {
			this.alias = this.column;
		}
		
		
		
		System.out.println("table:  "  + this.table);
		System.out.println("column: " + this.column);
		System.out.println("alias:  "  + this.alias);		
		
		
		// Check if column is subquery
		checkIsSubquery( this.column );
		
		
		

		
	}
	
	
	public String getTableAlias () {
		return this.table;
	}
	
	public String getColumn () {
		return this.column;
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	
	private void checkIsSubquery (String column) {
		
		// parserUtils.
		
	}
	
	
	
	
}
