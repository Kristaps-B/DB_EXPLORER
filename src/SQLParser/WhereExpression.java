package SQLParser;

public class WhereExpression {
	
	private String sql;
	
	private String expression = "";
	private String leftTable = "";
	private String rightTable = "";
	private String leftColumn = "";
	private String rightColumn = "";
	
	public WhereExpression (String sql) {
		this.sql = sql;
		
		this.sql = this.sql.replaceAll(" ", "");
		
		
		String [] arr = this.sql.split("=");
		
		
		if (arr.length > 1) {
			
			String expression = "=";
			
			
			
			String [] arrLeft = arr[0].split("\\.");
			
			if (arrLeft.length > 1) {
				
				leftTable = arrLeft[0];
				leftColumn = arrLeft[1];
			}
			
			String [] arrRight = arr[1].split("\\.");
			
			if (arrRight.length > 1) {
				
				rightTable = arrRight[0];
				rightColumn = arrRight[1];
			}
			
			
		}
		
		
		System.out.println("LEFT_TABLE: " + leftTable);
		System.out.println("LEFT_COLUMN: " + leftColumn);
		System.out.println("Expression: " + expression);
		System.out.println("RIGHT_TABLE: " + rightTable);
		System.out.println("RIGHT_COLUMN: " + rightColumn);
	}
	
	
	
	public String getExpression () {
		return this.expression;
	}
	
	public String getLeftTable () {
		return this.expression;
	}
	
	public String getRightTable () {
		return this.expression;
	}
	
	public String getLeftAlias () {
		return this.expression;
	}
	
	public String getRightAlias () {
		return this.expression;
	}
	

	
}
