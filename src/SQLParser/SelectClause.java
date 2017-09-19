package SQLParser;

import java.util.ArrayList;

public class SelectClause {
	
	private String sql;
	
	
	private ArrayList <ColumnSelect> columnSelectList = new ArrayList <> ();
	
	private ParserUtils parserUtils;
	private FromClause fromClause;

	public SelectClause () {
		parserUtils = new ParserUtils();
		
	}
	
	
	public void addSelectString (String sql, FromClause fromClause) {
		this.sql = sql;
		this.fromClause = fromClause;
		
		this.sql = this.sql.replaceAll(" AS ", " ");
		
		splitSelectClause(this.sql);
	}
	
	
	
	
	private void splitSelectClause (String sql) {
		
		
		/*
		
		String [] selectArray = this.sql.split(",");
		
		System.out.println("Split SELECT");
		
		for (String str: selectArray) {
			
			str = str.trim();
			
			// selectColumnList.add(str);
			
			columnSelectList.add(new ColumnSelect(str));
			
			System.out.println(str);
		}
		
		
		*/
		
		ArrayList <String> list = parserUtils.parseString(sql, ",");
		
		for (String s: list) {
			
			columnSelectList.add(new ColumnSelect(s));
		}
		
		
	}
	
	
	public ArrayList <ColumnSelect> getColumnList () {
		return this.columnSelectList;
	}

	
	
}
