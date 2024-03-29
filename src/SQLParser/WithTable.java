package SQLParser;

public class WithTable {
	
	private String sql = "";
	
	
	private String tableName  = "";
	private String tableQuery = "";
	
	
	private ParserUtils parserUtils;
	
	private MainSelectQuery selectParser;
	
	public WithTable (String sql) {
		this.sql = sql;
		
		
		this.parserUtils = new ParserUtils();
		
		
		this.tableName = parserUtils.getFirstPart(this.sql, " AS ");
		this.tableQuery = parserUtils.getSecondPart(this.sql, " AS ");
		
		
		this.tableQuery = parserUtils.getBracketsContent(this.tableQuery);
		
		selectParser = new MainSelectQuery(this.tableQuery);
		 
		
		System.out.println("TableName: " + this.tableName);
		System.out.println("Table Query: " + this.tableQuery);
	}
	
	
	
	public String getTableName () {
		return this.tableName;
	}
	
	public MainSelectQuery getSelectParser () {
		return this.selectParser;
	}
	
	
	public String getQuery () {
		return this.sql;
	}

}
