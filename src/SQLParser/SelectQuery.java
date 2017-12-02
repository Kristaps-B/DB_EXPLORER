package SQLParser;

import java.util.ArrayList;

public class SelectQuery {
	
	
	private ArrayList <SelectQuery> selectUnionList = new ArrayList <>();
	
	private WithClause   withClause     = new WithClause(this);
	private SelectClause selectClause   = new SelectClause(this);
	private FromClause   fromClause     = new FromClause(this);
	private WhereClause  whereClause;
	
	private ParserUtils parserUtils;
	
	
	private String sql;
	
	private SelectQuery outerQuery;
	
	public SelectQuery (SelectQuery outerQuery) {
		parserUtils = new ParserUtils();
		
		this.outerQuery = outerQuery;
	}
	
	
	public void createSelect (String sql) {
		this.sql = sql;
		
		
		System.out.println("Select query START");
		
		splitClauses ();
	}
	
	
	public void addSelectQuery (SelectQuery selectQuery) {
		
		selectUnionList.add(selectQuery);
		
	}
	
	public WithClause getWith () {
		return this.withClause;
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
	
	
	
	

	
	
	
	
	
	private void splitClauses() {
		

		
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
		
		
		
		
		
		// fromClause = parserUtils.getFirstPart(remainingPart, " WHERE ");
		
		// remainingPart = parserUtils.getSecondPart(remainingPart, " WHERE ");
		
		
		
		if (this.parserUtils.checkIfTextExists(remainingPart, " WHERE ") == false) {
			
			System.out.println("There is no WHERE clause!");
			
			if (this.parserUtils.checkIfTextExists(remainingPart, " GROUP BY ") == true) {
				
				fromClause = parserUtils.getFirstPart(remainingPart, " GROUP BY ");
				
			} else if (this.parserUtils.checkIfTextExists(remainingPart, " ORDER BY ") == true) {
				
				fromClause = parserUtils.getFirstPart(remainingPart, " ORDER BY ");
				
			} else if (this.parserUtils.checkIfTextExists(remainingPart, " WITH ") == true) {
				
				fromClause = parserUtils.getFirstPart(remainingPart, " WITH ");
				
			} else {
				fromClause = remainingPart;
			}
			
			
			
		} else  {
			System.out.println("WHERE clause exists!");
			
			
			fromClause = parserUtils.getFirstPart(remainingPart, " WHERE ");
			remainingPart = parserUtils.getSecondPart(remainingPart, " WHERE ");
			
			 
			if (this.parserUtils.checkIfTextExists(remainingPart, " GROUP BY ") == true) {
				
				whereClause = parserUtils.getFirstPart(remainingPart, " GROUP BY ");
				
			} else if (this.parserUtils.checkIfTextExists(remainingPart, " ORDER BY ") == true) {
				
				whereClause = parserUtils.getFirstPart(remainingPart, " ORDER BY ");
				
			} else if (this.parserUtils.checkIfTextExists(remainingPart, " WITH ") == true) {
				
				whereClause = parserUtils.getFirstPart(remainingPart, " WITH ");
				
			} else {
				whereClause = remainingPart;
			}
			
		}
	 
		

		
		
		
		
		
		System.out.println("With clause: " + withClause);
		System.out.println("Select clause: " + selectClause);
		System.out.println("From clause: " + fromClause);
		System.out.println("Where clause: " + whereClause);
		
		
		
		if (withClause.equals("") != true) {
			this.withClause.addWithString(withClause);
		}
		
		
		this.fromClause.addFromString(fromClause);
		
		
		this.selectClause.addSelectString(selectClause, this.fromClause);
		
		
		
		
		
		if (whereClause.equals("") != true) {
			this.whereClause = new WhereClause(whereClause, this); 	
		}
		
		
		
		
		
	}
	
	


	
	
	public ArrayList <FromTable> getTables () {
		ArrayList <FromTable> tables = new ArrayList <> ();
		
		
		tables.addAll(this.fromClause.getTables());
		
		
		tables.addAll(this.selectClause.getTables());
	
		
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
		
		System.out.println("SelectQuery.getWhereList");
		
		ArrayList <WhereExpression> whereList = new ArrayList <> ();
		
		System.out.println("Get from where clause");
		
		
		if (this.whereClause != null) {
			whereList.addAll(this.whereClause.getExpressionList());
			
		}
	
		
		System.out.println("Get from select clause");
		whereList.addAll( this.selectClause.getExpressionList() );
		
		
		return whereList;
	}
	
	public String getQuery () {
		return this.sql;
	}
	
	
	public FromTable getColumnTable(String tableAlias, String column) {
		FromTable fromTable = null;
		
		
		System.out.println("SelectQuery.getColumnTable: tableAlias " + tableAlias + " Column: " + column);
		
		fromTable = this.fromClause.getColumnTable( tableAlias,  column);
		
		
		 
		
		
		return fromTable;
		
		
	}
	
	
	public ColumnSelect getColumnSelect(String alias) {
		
		
		System.out.println("Get columnSelect: alias: " + alias);
		ColumnSelect columnSelect = null;
		
		columnSelect = this.selectClause.getColumnSelect(alias);
		
		return columnSelect;
	}
	
	
	public SelectQuery getOuterQuery () {
		return this.outerQuery;
	}
	
	

}
