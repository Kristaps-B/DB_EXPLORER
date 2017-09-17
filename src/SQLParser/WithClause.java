package SQLParser;

import java.util.ArrayList;

public class WithClause {
	
	String sql = "";
	ParserUtils parserUtils = new ParserUtils();
	
	ArrayList <WithTable> tableList = new ArrayList <>();
	
	
	public WithClause() {
		 
	}
	
	
	
	public void addWithString(String sql) {
		
		this.sql = sql;
		
		
		
		// REPLACE IS to AS
		
		this.sql = this.sql.replaceAll(" IS ", " AS ");
		this.sql = parserUtils.getSecondPart(this.sql, " WITH ");
		
		
		System.out.println("With SQL: " + this.sql);
		
		
		
		// Split WITH
		for (String str: parserUtils.parseString(this.sql, ",")) {
			System.out.println("WITH TABLE: " + str);
			
			tableList.add(new WithTable(str));
		}
		
		
		
		
		
	}
	
	
	
	public SelectParser getSelectParser(String tableName) {
		SelectParser selectParser = null;
		
		for (WithTable sq: tableList) {
			
			if (sq.getTableName().equals(tableName)) {
				selectParser = sq.getSelectParser();
			}
		}
		
		return selectParser;
	}
	

}
