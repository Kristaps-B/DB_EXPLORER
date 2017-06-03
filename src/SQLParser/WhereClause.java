package SQLParser;

import java.util.ArrayList;

public class WhereClause {
	
	
	private String sql;
	
	
	private ArrayList <WhereExpression> whereExpressionList = new ArrayList <> ();	
	
	public WhereClause () {
		
		
	}
	
	
	
	public void addWhereString (String sql) {
		this.sql = sql;
		
		splitWhereClauses(sql);
	}
	
	

	
	private void splitWhereClauses (String sql) {

		String [] whereArray = sql.split("AND");
		
		System.out.println("Split WHERE");
		
		for (String str: whereArray) {
			
			str = str.trim();
			
			whereExpressionList.add(new WhereExpression(str));
			
			System.out.println(str);
		}		
		
	}
	
	
	public ArrayList <WhereExpression> getExpressionList () {
		return whereExpressionList;
	}

	
}
