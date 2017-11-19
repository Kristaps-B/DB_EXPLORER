package SQLParser;

import java.util.ArrayList;

public class MainSelectQuery {
	private String sql = "";
	
	

	
	

	
	
	private ArrayList <String> fromTableList     = new ArrayList <> ();
	private ArrayList <String> selectColumnList  = new ArrayList <> ();
	private ArrayList <String> whereCheckList         = new ArrayList <> ();
	
	
	// private SelectQuery selectQuery;
	
	
	private ParserUtils parserUtils;
	
	
	private ArrayList <SelectQuery> selectQueryList = new ArrayList <> ();
	
	
	public MainSelectQuery (String sql) {
		this(sql, null);
		
		
	 
		// splitClauses();
		
		
		
		// selectQuery = new SelectQuery(null);
		// selectQuery.createSelect(this.sql);
		
		
		
		
		//getClauses();
		
		//splitSelectClauses();
		
		//splitFromClauses();
		
		//splitWhereClauses();
	}
	
	public MainSelectQuery (String sql, SelectQuery outerQuery) {
		this.sql = sql;
		// splitClauses();
		
		parserUtils = new ParserUtils();
		
		this.sql = this.sql.toUpperCase();
		this.sql = this.sql.replace("\n", " ").replace("\r", " ");
		this.sql = " " + this.sql.trim().replaceAll(" +", " ") + " ";
		this.sql = parserUtils.removeComments(this.sql);
		
		this.sql = this.sql.replaceAll(" UNION ALL ", "UNION");
		
		ArrayList <String> sqlList = this.parserUtils.parseString(this.sql, " UNION ");
		
		
		for (String s: sqlList) {
			
			s = " " + s.trim() + " ";
			
			System.out.println("------------> Union select : " + s);
			
			SelectQuery selectQuery = new SelectQuery(outerQuery);
			selectQuery.createSelect(s);
			
			selectQueryList.add(selectQuery);
			
			
		}
		
		
		
		
	}
	
	
	public ArrayList <SelectQuery> getSelectQueryList () {
		return selectQueryList;
	}
	
	public SelectQuery getFirstQuery () {
		return selectQueryList.get(0);
	}
	
	
	
	
	
	
	

	
	
	public ArrayList <FromTable> getTableList () {
		ArrayList <FromTable> tableList = new ArrayList <> ();
		
		
		for (SelectQuery sq: selectQueryList) {
			
			
			tableList.addAll(sq.getTables());
			
		}
		
		
		return tableList;
	}
	
	
	public ArrayList <ColumnSelect> getColumnList () {
		
		ArrayList <ColumnSelect> columnList = new ArrayList <> ();
		
		for (SelectQuery sq: selectQueryList) {
			
			
			columnList.addAll(sq.getColumnList());
			
		}		
		
		
		return columnList;
		
		
	}
	
	
	
	public ArrayList <WhereExpression> getWhereList () {
		
		ArrayList <WhereExpression> whereList = new ArrayList <> ();
		
		
		for (SelectQuery sq: selectQueryList) {
			
			
			whereList.addAll(sq.getWhereList());
			
		}	
		
		return whereList;
		
		
	}
	
	
	public FromTable getColumnTable () {
		
		FromTable table = null;
		
		
		return table;
		
	}
	

	
/*
	
	

	
	
	
	

	
	*/
	
}
