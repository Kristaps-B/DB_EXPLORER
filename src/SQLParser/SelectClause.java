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
		
		
	
		
		ArrayList <String> list = parserUtils.parseString(sql, ",");
		
		for (String s: list) {
			
			columnSelectList.add(new ColumnSelect(s));
		}
		
		
	}
	
	
	public ArrayList <ColumnSelect> getColumnList () {
		return this.columnSelectList;
	}
	
	
	
	public ArrayList <FromTable> getTables () {
		ArrayList <FromTable> tableList = new ArrayList <> ();
		
		
		
		return tableList;
	}
	
	
	public ColumnSelect  getColumnSelect (String alias) {
		ColumnSelect columnSelect = null;
		
		
		for (ColumnSelect cs: columnSelectList) {
			
			if (cs.getAlias().equals(alias)) {
				System.out.println("SelectClause.getColumnSelect FOUND MATCH to ALIAS: " + alias);
				columnSelect = cs;
			}
			
		}
		
		
		return columnSelect;
	}


	
	
}
