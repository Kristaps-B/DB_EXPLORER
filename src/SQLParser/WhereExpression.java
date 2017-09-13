package SQLParser;

public class WhereExpression {
	
	private String sql;
	
	private String expression = "";
	private String leftAlias = "";
	private String rightAlias = "";
	private String leftColumn = "";
	private String rightColumn = "";
	
	
	private ParserUtils parserUtils;
	
	public WhereExpression (String sql) {
		this.sql = sql;
		
		this.sql = this.sql.trim();
		
		parserUtils = new ParserUtils();
		
		
	
		
		System.out.println("Where Expression START!!!");
		System.out.println("SQL: " + this.sql);
		
		
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
			
	
				System.out.println("LEFT_ALIAS: " + leftAlias);
				System.out.println("LEFT_COLUMN: " + leftColumn);
				// System.out.println("Expression: " + expression);
				System.out.println("RIGHT_ALIAS: " + rightAlias);
				System.out.println("RIGHT_COLUMN: " + rightColumn);	

			
		}
		

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
