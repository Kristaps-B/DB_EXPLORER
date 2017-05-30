package SQLParser;

import java.util.ArrayList;

public class FromClause {
	
	
	private String sql;
	
	
	private ArrayList <FromTable> fromTableList = new ArrayList <> ();	

	public FromClause () {
		
		
		
		
	}
	
	
	public void addFromString (String sql) {
		this.sql = sql;
		
		
		splitFromClauses (this.sql);
	}
	
	

	
	private void splitFromClauses (String sql) {
		
		String [] fromArray = sql.split(",");
		
		System.out.println("Split FROM");
		
		for (String str: fromArray) {
			
			str = str.trim();
			
			fromTableList.add(new FromTable(str));
			
			System.out.println(str);
		}
	}
	
	
	
	public ArrayList <FromTable> getTables () {
		return this.fromTableList;
	}
	
	
	public FromTable getTableByAlias(String alias) {
		FromTable fromTable = null;
		
		for (FromTable ft: fromTableList) {
			
			if (ft.getAlias().equals(alias)) {
				
				fromTable = ft;
			}
		}
		
		
		
		return fromTable;
	}
	
	
	public FromTable getTableByTable(String table) {
		FromTable fromTable = null;
		
		for (FromTable ft: fromTableList) {
			
			if (ft.getTable().equals(table)) {
				
				fromTable = ft;
			}
		}		
		
		return fromTable;		
	}
	
}
