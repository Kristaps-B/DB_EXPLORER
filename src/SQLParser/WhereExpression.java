package SQLParser;

public class WhereExpression {
	
	private String sql;
	
	private String leftOwner = "";
	private String rightOwner = "";
	private String expression = "";
	private String leftAlias = "";
	private String rightAlias = "";
	private String leftColumn = "";
	private String rightColumn = "";
	private String leftTable = "";
	private String rightTable = "";
	
	private boolean isJoin = false;
	
	
	private ParserUtils parserUtils;
	
	private FromClause fromClause;
	
	public WhereExpression (String sql, FromClause fromClause) {
		this.sql = sql;
		
		this.sql = this.sql.trim();
		this.fromClause = fromClause;
		
		
		parserUtils = new ParserUtils();
		
		
	
		
		System.out.println("Where Expression START!!!");
		System.out.println("SQL: " + this.sql);
		
		// Remove left/right JOIN
		this.sql = this.sql.replace("(+)", "");
		
		
		this.checkIfIsJoinPre();
		
		
		
		
		if (this.isJoin == false) {
			System.out.println("WhereExpression.WhereExpression: IS JOIN!");
			
			
			leftColumn   = parserUtils.getFirstPart(this.sql, "=");
			rightColumn  = parserUtils.getSecondPart(this.sql, "=");
			
		
			
			
			if (parserUtils.isTextInside(leftColumn, ".") == true) {
				leftAlias   = parserUtils.getFirstPart(leftColumn, ".");
				leftColumn  = parserUtils.getSecondPart(leftColumn, ".");
			}
			
			
			if (parserUtils.isTextInside(rightColumn, ".") == true) {					
				rightAlias  = parserUtils.getFirstPart(rightColumn, ".");
				rightColumn = parserUtils.getSecondPart(rightColumn, ".");				
			

			}
			
			this.leftAlias = this.leftAlias.trim();
			this.rightAlias = this.rightAlias.trim();
			this.leftColumn = this.leftColumn.trim();
			this.rightColumn = this.rightColumn.trim();
			
			
			System.out.println("WhereExpression.WhereExpression: Before this.fromClause.getColumnTable this.leftAlias: " + this.leftAlias + " this.leftColumn:" + this.leftColumn);
			FromTable leftFromTable = this.fromClause.getColumnTable(this.leftAlias, this.leftColumn);
			System.out.println("WhereExpression.WhereExpression: Before this.fromClause.getColumnTable this.rightAlias: " + this.rightAlias + " this.righColumn:" + this.rightColumn);
			FromTable rightFromTable = this.fromClause.getColumnTable(this.rightAlias, this.rightColumn);
			System.out.println("WhereExpression.WhereExpression: After this.fromClause.getColumnTable");
			
			
			if (leftFromTable == null || rightFromTable == null) {
				this.isJoin = false;
				return;
			}
			
			if (leftFromTable != null ) {
			    
				this.leftTable = leftFromTable.getTable();
				this.leftOwner = leftFromTable.getOwner();
			} else {
				this.leftTable = "";
			}
			
			if (rightFromTable != null ) {
				this.rightTable = rightFromTable.getTable();
				this.rightOwner = rightFromTable.getOwner();
			} else {
				this.rightTable = "";
			}	
			
			if (this.leftTable.length() == 0 && this.leftAlias.length() != 0) {
				this.leftTable = this.leftAlias;
			}
					
 
			
		
			System.out.println("####################################################");
			System.out.println("   * LEFT_TABLE: " + this.leftTable);
			System.out.println("     LEFT_ALIAS: " + this.leftAlias);
			System.out.println("     LEFT_COLUMN: " + this.leftColumn);
			// System.out.println("Expression: " + expression);
			System.out.println("   * RIGHT_TABLE: " + this.rightTable);
			System.out.println("     RIGHT_ALIAS: " + this.rightAlias);
			System.out.println("     RIGHT_COLUMN: " + this.rightColumn);	
			System.out.println("####################################################");
			
		}
		
		
		checkIfIsJoin();

	}
	
	private void checkIfIsJoinPre () {
		
		if (parserUtils.isTextInside(this.sql, "=") == false ) {
			isJoin = false;
		}
		if (parserUtils.isTextInside(this.sql, "'") == false ) {
			isJoin = false;
		}
		if (parserUtils.isTextInside(this.sql, " IN ") == false ) {
			isJoin = false;
		}
		if (parserUtils.isTextInside(this.sql, " EXISTS ") == false ) {
			isJoin = false;
		}
		
	}
	
	
	private void checkIfIsJoin () {
		
		if ( 
			this.leftAlias.equals("") ||
			this.rightAlias.equals("") 
		  ) {
			return;
		}
		
		
		
		if (parserUtils.isNumeric(this.leftColumn) || parserUtils.isNumeric(this.rightColumn)) {
			return;
			
		}
		
		if (parserUtils.isTextInside(this.leftColumn, "(") || parserUtils.isTextInside(this.rightColumn, "(")) {
			return;
		}
			
		
		isJoin = true;
		System.out.println("*) Where Expression " + this.sql + " is JOIN!!!");
		
	}
	
	
	
	public String getExpression () {
		return this.expression;
	}
	
	public String getLeftColumn () { 
		
		return this.leftColumn;
	}
	
	public String getRightColumn () {
		return this.rightColumn;
	}
	
	public String getLeftAlias () {
		return this.leftAlias;
	}
	
	public String getRightAlias () {
		return this.rightAlias;
	}
	
	public String getRightTable() {
		return this.rightTable;
	}
	
	public String getLeftTable() {
		return this.leftTable;
	}
	
	
	public boolean getIsJoin () {
		return this.isJoin;
	}
	
	public String getLeftOwner() {
		return this.leftOwner;
	}
	
	public String getRightOwner () {
		return this.rightOwner;
	}

	
}
