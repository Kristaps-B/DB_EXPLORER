package SQLParser;

import java.util.ArrayList;

public class WhereClause {
	
	
	private String sql;
	
	
	private ArrayList <WhereExpression> whereExpressionList = new ArrayList <> ();	
	private FromClause fromClause;
	private SelectQuery mainQuery;
	
	public WhereClause (String sql, SelectQuery mainQuery) {
		this.sql = sql;
		this.mainQuery = mainQuery;
		this.fromClause = mainQuery.getFrom();
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
		ArrayList <WhereExpression> joinList = new ArrayList <> ();
		
		for (WhereExpression we: whereExpressionList) {
			
			if (we.getIsJoin() == true) {
				joinList.add(we);
			}
			
		}
		
		return joinList;
	}
	
	

	
}
