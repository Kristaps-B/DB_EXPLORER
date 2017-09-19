package SQLParser;

import java.util.ArrayList;

public class WhereClause {
	
	
	private String sql;
	
	
	private ArrayList <WhereExpression> whereExpressionList = new ArrayList <> ();	
	private FromClause fromClause;
	
	public WhereClause (String sql, FromClause fromClause) {
		this.sql = sql;
		this.fromClause = fromClause;
		splitWhereClauses(sql);
	}
	

	
	private void splitWhereClauses (String sql) {

		String [] whereArray = sql.split("AND");
		
		System.out.println("Split WHERE");
		
		for (String str: whereArray) {
			
			str = str.trim();
			
			whereExpressionList.add(new WhereExpression(str, this.fromClause));
			
			System.out.println(str);
		}		
		
	}
	
	
	public ArrayList <WhereExpression> getExpressionList () {
		return whereExpressionList;
	}

	
}
