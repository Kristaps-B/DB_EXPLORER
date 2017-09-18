package SQLParser;

public class WhereExpression {
	
	private String sql;
	
	private String expression = "";
	private String leftAlias = "";
	private String rightAlias = "";
	private String leftColumn = "";
	private String rightColumn = "";
	
	private boolean isJoin = false;
	
	
	private ParserUtils parserUtils;
	
	public WhereExpression (String sql) {
		this.sql = sql;
		
		this.sql = this.sql.trim();
		
		parserUtils = new ParserUtils();
		
		
	
		
		System.out.println("Where Expression START!!!");
		System.out.println("SQL: " + this.sql);
		
		// Remove left/right JOIN
		this.sql = this.sql.replace("(+)", "");
		
		
		if (parserUtils.isTextInside(this.sql, "=") == true) {
			
			
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
		

			System.out.println("LEFT_ALIAS: " + this.leftAlias);
			System.out.println("LEFT_COLUMN: " + this.leftColumn);
			// System.out.println("Expression: " + expression);
			System.out.println("RIGHT_ALIAS: " + this.rightAlias);
			System.out.println("RIGHT_COLUMN: " + this.rightColumn);	

			
		}
		
		
		checkIfIsJoin();

	}
	
	
	private void checkIfIsJoin () {
		
		if ( 
			this.leftAlias.equals("") ||
			this.leftAlias.equals("") ||
			this.leftAlias.equals("") ||
			this.leftAlias.equals("") 
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
	

	
}
