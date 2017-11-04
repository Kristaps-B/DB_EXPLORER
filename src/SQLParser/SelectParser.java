package SQLParser;

import java.util.ArrayList;

public class SelectParser {
	private String sql = "";
	
	

	
	

	
	
	private ArrayList <String> fromTableList     = new ArrayList <> ();
	private ArrayList <String> selectColumnList  = new ArrayList <> ();
	private ArrayList <String> whereCheckList         = new ArrayList <> ();
	
	
	// private SelectQuery selectQuery;
	
	
	private ParserUtils parserUtils;
	
	
	private ArrayList <SelectQuery> selectQueryList = new ArrayList <> ();
	
	
	public SelectParser (String sql) {
		this(sql, null);
		
		
	 
		// splitClauses();
		
		
		
		// selectQuery = new SelectQuery(null);
		// selectQuery.createSelect(this.sql);
		
		
		
		
		//getClauses();
		
		//splitSelectClauses();
		
		//splitFromClauses();
		
		//splitWhereClauses();
	}
	
	public SelectParser (String sql, SelectQuery outerQuery) {
		this.sql = sql;
		// splitClauses();
		
		parserUtils = new ParserUtils();
		
		this.sql = this.sql.toUpperCase();
		this.sql = this.sql.replace("\n", " ").replace("\r", " ");
		this.sql = " " + this.sql.trim().replaceAll(" +", " ") + " ";
		this.sql = this.removeComments(this.sql);
		
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
	
	
	public SelectQuery getSelectQuery () {
		return selectQueryList.get(0);
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
	
	

	
/*
	
	

	
	
	
	

	
	*/
	
}
