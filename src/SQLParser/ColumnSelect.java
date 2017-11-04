package SQLParser;

public class ColumnSelect {
	
	private String sql = "";
	
	private String table = "";
	private String column = "";
	private String alias = "";
	
	ParserUtils parserUtils;
	
	private SelectParser selectParser;
	
	private boolean isSubquery = false;
	private boolean isSimpleColumn = true;
	SelectQuery mainQuery;
	
	
	public ColumnSelect (String sql, SelectQuery mainQuery) {
		
		this.sql = sql;
		
		parserUtils = new ParserUtils();
		
		
		this.mainQuery = mainQuery;
		
		this.sql = this.sql.trim();
		
		
		String tableColumn = "";
		
		
		if (parserUtils.isSimpleColumn(this.sql)) {
			
			
			this.isSimpleColumn = true;
			
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
			
		} else {
			
			this.isSimpleColumn = false;
			
			this.sql = parserUtils.removeSpacesNextTo(this.sql, "\\|\\|");
			this.sql = parserUtils.removeSpacesNextTo(this.sql, "\\+");
			this.sql = parserUtils.removeSpacesNextTo(this.sql, "-");
			this.sql = parserUtils.removeSpacesNextTo(this.sql, "\\*");
			this.sql = parserUtils.removeSpacesNextTo(this.sql, "/");
			
			
			this.column = parserUtils.getFirstPart(this.sql, " ");
			this.alias = parserUtils.getSecondPart(this.sql, " ");
			
			
		}

		
		
		
		System.out.println("table:  "  + this.table);
		System.out.println("column: " + this.column);
		System.out.println("alias:  "  + this.alias);		
		
		
		// Check if column is subquery
		checkIsSubquery( this.column );
		
		
		System.out.println("  Is simple columns: " + this.isSimpleColumn);

		
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
		
		
		
		if (parserUtils.isQuery(column) == true) {
			
			System.out.println("Column: " + column + " is subquery!");
			
			isSubquery = true;
			
			String subquery = parserUtils.getBracketsContent(column); 
			
			
			System.out.println("Inside brackets content: " + subquery);
			
			
			this.selectParser = new SelectParser(subquery, this.mainQuery);
			
			
			
		}
		
		
		
	}
	
	
	public boolean getIsSimpleColumn () {
		return this.isSimpleColumn;
	}
	
	public boolean getIsSubquery () {
		return this.isSubquery;
	}
	
	
	public SelectQuery getSubquery () {
		return this.selectParser.getSelectQuery();
	}
	
	
	
	
}
