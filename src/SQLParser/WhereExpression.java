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
		
		parserUtils = new ParserUtils();
		
		
		/*
		this.sql = this.sql.replaceAll(" ", "");
		
		
		String [] arr = this.sql.split("=");
		
		
		if (arr.length > 1) {
			
			this.expression = "=";
			
			
			
			String [] arrLeft = arr[0].split("\\.");
			
			if (arrLeft.length > 1) {
				
				leftAlias = arrLeft[0];
				leftColumn = arrLeft[1];
			}
			
			String [] arrRight = arr[1].split("\\.");
			
			if (arrRight.length > 1) {
				
				rightAlias = arrRight[0];
				rightColumn = arrRight[1];
			}
			
			
		}
		*/
		
		System.out.println("Where Expression START!!!");
		System.out.println("SQL: " + this.sql);
		
		
		if (parserUtils.isTextInside(this.sql, "=") == true) {
			
			
			leftColumn   = parserUtils.getFirstPart(this.sql, "=");
			rightColumn  = parserUtils.getSecondPart(this.sql, "=");
			
			
			if (parserUtils.isTextInside(leftColumn, ".")) {
				leftAlias   = parserUtils.getFirstPart(leftColumn, ".");
				leftColumn  = parserUtils.getSecondPart(leftColumn, ".");
			}
			
			
			if (parserUtils.isTextInside(rightColumn, ".")) {					
				rightAlias  = parserUtils.getFirstPart(rightColumn, ".");
				rightColumn = parserUtils.getSecondPart(rightColumn, ".");				
			

			}
			
	
				System.out.println("LEFT_ALIAS: " + leftAlias);
				System.out.println("LEFT_COLUMN: " + leftColumn);
				System.out.println("Expression: " + expression);
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
