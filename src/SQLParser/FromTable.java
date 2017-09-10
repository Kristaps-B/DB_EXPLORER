package SQLParser;

public class FromTable {
	
	private String sql;
	
	
	private String alias = "";
	private String table = "";
	
	private boolean isSubquery = false;
	
	
	SelectParser selectParser;

	public FromTable (String sql) {
		
		this.sql = sql;
		
		this.sql = this.sql.trim();
		
		
		/*
		 String [] tableArray =	this.sql.split(" ");
		 
		 
		 this.table = tableArray[0];
		 
		 if (tableArray.length > 1) {
			 this.alias = tableArray[1];
		 }
		 */
		
		splitAlias(this.sql);
		 
		 System.out.println("table: " + this.table);
		 System.out.println("alias: " + this.alias);
		 
		 
		 checkSubquery(this.table);
		
		
	}
	
	public String getAlias () {
		return this.alias;
	}
	
	public String getTable () {
		return this.table;
	}
	
	
	private void splitAlias (String sql) {
		
		
		int numberOfBrackets = 0;
		
		int spacePosition = -1;
		
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			
			if (c == '(') {
				numberOfBrackets += 1;
			}
			if (c == ')') {
				numberOfBrackets -= 1;
			}
			
			if (c == ' ' && numberOfBrackets == 0) {
				spacePosition = i;
				break;
			}
			
			
			
		}
		
		
		
		if (spacePosition > 0) {
			
			
			
			this.table = this.sql.substring(0, spacePosition);
			
			
			this.alias = this.sql.substring(spacePosition + 1);
			
		} else {
			
			this.table = this.sql;
		}
		
		
		
	}
	
	
	
	private void  checkSubquery(String table) {
		
		if (table.charAt(0) == '(') {
			
			System.out.println("Its subquery");
			isSubquery = true;
			
			String subquery =  table.substring(1, table.length() - 1);
			
			System.out.println("Subquery: " + subquery);
			
			
			selectParser = new SelectParser(subquery);
		}
		
	}
	
	
 
	
}
