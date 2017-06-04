package SQLParser;

public class ColumnSelect {
	
	private String sql = "";
	
	private String table = "";
	private String column = "";
	private String alias = "";
	
	
	public ColumnSelect (String sql) {
		
		this.sql = sql;
		
		// System.out.println("ColumnSelect: " + this.sql);
		
		String [] clArray = this.sql.split("\\.");
		
		// System.out.println("Length: " + clArray.length);
		
		if (clArray.length == 1) {
			
			this.column = clArray[0];
			
			
		} else if (clArray.length > 1) {
			
			this.table  = clArray[0];
			this.column = clArray[1];
			
		}
		
		clArray = column.split(" ");
		
		
		
		if (clArray.length == 1) {
			
			this.column = clArray[0];
			
		} else if (clArray.length > 1) {
			
			this.column = clArray[0];
			this.alias = clArray[1];
			
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
