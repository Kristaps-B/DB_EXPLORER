package SQLParser;

import java.util.ArrayList;

public class SelectParser {
	private String sql = "";
	
	

	
	

	
	
	private ArrayList <String> fromTableList     = new ArrayList <> ();
	private ArrayList <String> selectColumnList  = new ArrayList <> ();
	private ArrayList <String> whereCheckList         = new ArrayList <> ();
	
	
	private SelectQuery selectQuery;
	
	
	public SelectParser (String sql) {
		
		this.sql = sql;
		// splitClauses();
		
		
		
		selectQuery = new SelectQuery(null);
		selectQuery.createSelect(this.sql);
		
		
		//getClauses();
		
		//splitSelectClauses();
		
		//splitFromClauses();
		
		//splitWhereClauses();
	}
	
	public SelectParser (String sql, SelectQuery outerQuery) {
		this.sql = sql;
		// splitClauses();
		
		
		
		selectQuery = new SelectQuery(outerQuery);
		selectQuery.createSelect(this.sql);
	}
	
	
	public SelectQuery getSelectQuery () {
		return selectQuery;
	}
	
	

	
/*
	
	

	
	
	
	

	
	*/
	
}
