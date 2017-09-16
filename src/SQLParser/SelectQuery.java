package SQLParser;

import java.util.ArrayList;

public class SelectQuery {
	
	
	private ArrayList <SelectQuery> selectUnionList = new ArrayList <>();
	
	private WithClause   withClause     = new WithClause();
	private SelectClause selectClause   = new SelectClause();
	private FromClause   fromClause     = new FromClause();
	private WhereClause  whereClause    = new WhereClause();
	
	private ParserUtils parserUtils;
	
	
	private String sql;
	
	public SelectQuery () {
		parserUtils = new ParserUtils();
	}
	
	
	public void createSelect (String sql) {
		this.sql = sql;
		
		
		System.out.println("Select query START");
		
		splitClauses ();
	}
	
	
	public void addSelectQuery (SelectQuery selectQuery) {
		
		selectUnionList.add(selectQuery);
		
	}
	
	
	public SelectClause getSelect () {
		return this.selectClause;
	}
	
	public FromClause getFrom () {
		return this.fromClause;
	}
	
	public WhereClause getWhere () {
		return this.whereClause;
	}
	
	
	
	
	private String removeComments (String inSql) {
		String outSql = "";
		
		System.out.println("Remove comments");
		
		boolean isComment = false;
		
		
		for (int  i = 0; i < this.sql.length(); i ++) {
			
			char c = this.sql.charAt(i);
			
			// System.out.println("Character: " + c);
			
			if (parserUtils.atLocation(i, "--", this.sql) || parserUtils.atLocation(i, "/*", this.sql)) {
				
				isComment = true;
				// System.out.println("Comment starts");
			}
			
			
			if (isComment == false) {
				outSql += c;
			} else {
				if ( i > 0 && parserUtils.atLocation(i-1, "*/", this.sql)) {
					
					isComment = false;
					
				}
				
				if (c == '\n'  || c == '\r' ) {
					isComment = false;
				}
			}
			
			
		}
		
		
		
		
		return outSql;
		
	}
	
	
	
	
	
	private void splitClauses() {
		
		this.sql = this.sql.toUpperCase();
		
		System.out.println("SQL before removing comments: " + this.sql);
		
		this.sql = this.removeComments(this.sql);
		
		System.out.println("SQL after removing comments: " + this.sql);
		
		this.sql = this.sql.replace("\n", " ").replace("\r", " ");
		
		
		this.sql = " " + this.sql.trim().replaceAll(" +", " ") + " ";
		
		String withClause = "";
		String selectClause = "";
		String fromClause = "";
		String whereClause = "";
		
		
		System.out.println(this.sql);
		
		String remainingPart = "";
		
		
		withClause = parserUtils.getFirstPart(this.sql, " SELECT ");
		
		remainingPart = parserUtils.getSecondPart(this.sql, " SELECT ");
		
		selectClause = parserUtils.getFirstPart(remainingPart, " FROM ");
		
		remainingPart = parserUtils.getSecondPart(remainingPart, " FROM ");
		
		fromClause = parserUtils.getFirstPart(remainingPart, " WHERE ");
		
		remainingPart = parserUtils.getSecondPart(remainingPart, " WHERE ");
		
		whereClause = parserUtils.getFirstPart(remainingPart, " GROUP BY ");
		
		
		
		System.out.println("With clause: " + withClause);
		System.out.println("Select clause: " + selectClause);
		System.out.println("From clause: " + fromClause);
		System.out.println("Where clause: " + whereClause);
		
		
		
		if (withClause.equals("") != true) {
			this.withClause.addWithString(withClause);
		}
		
		
		this.selectClause.addSelectString(selectClause);
		
		
		this.fromClause.addFromString(fromClause);
		
		
		if (whereClause.equals("") != true) {
			this.whereClause.addWhereString(whereClause);		
		}
		
		
		
		
		
	}
	
	


	
	
	public ArrayList <FromTable> getTables () {
		ArrayList <FromTable> tables = new ArrayList <> ();
		
		
		tables.addAll(this.fromClause.getTables());
	
		
		return tables;
		
	}
	
	
	public FromTable getTableByAlias (String alias) {
		return this.fromClause.getTableByAlias(alias);
	}
	
	
	public FromTable getTableByTable (String table) {
		return this.fromClause.getTableByTable(table);
	}
	
	public ArrayList <ColumnSelect> getColumnList () {
		
		return this.selectClause.getColumnList();
	}
	
	public ArrayList <WhereExpression> getWhereList () {
		return this.whereClause.getExpressionList();
	}
	

}
