package SQLParser;

public class WhereExpression {
	
	private String sql;
	
	private String expression = "";
	private String leftAlias = "";
	private String rightAlias = "";
	private String leftColumn = "";
	private String rightColumn = "";
	
	public WhereExpression (String sql) {
		this.sql = sql;
		
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
		
		
		System.out.println("LEFT_ALIAS: " + leftAlias);
		System.out.println("LEFT_COLUMN: " + leftColumn);
		System.out.println("Expression: " + expression);
		System.out.println("RIGHT_ALIAS: " + rightAlias);
		System.out.println("RIGHT_COLUMN: " + rightColumn);
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
