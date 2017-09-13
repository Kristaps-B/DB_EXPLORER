package SQLParser;

public class ColumnSelect {
	
	private String sql = "";
	
	private String table = "";
	private String column = "";
	private String alias = "";
	
	ParserUtils parserUtils;
	
	
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
		
		
		
		
		System.out.println("table:  "  + this.table);
		System.out.println("column: " + this.column);
		System.out.println("alias:  "  + this.alias);
		
	}
	
	
	public String getTable () {
		return this.table;
	}
	
	public String getColumn () {
		return this.column;
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	
	
	
}
