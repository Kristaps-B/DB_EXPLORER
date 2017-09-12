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
		
		
		int nmbOfBrackets = 0;
		
		
		String strTable = "";
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			
			strTable += c;
			
			if (c == '(') {
				nmbOfBrackets += 1;
			}
			if (c == ')') {
				nmbOfBrackets -= 1;
			}
			
			if ((c == ',' && nmbOfBrackets == 0) || i == sql.length() - 1) {
				
				// Remove last coma
				if (c == ',') {
					strTable = strTable.substring(0, strTable.length() - 1);
				}
				
				
				System.out.println("Table -> " + strTable);
				fromTableList.add(new FromTable(strTable));
				
				strTable = "";
				
			}
			
			
			
		}
		
		
	}
	
	
	
	public ArrayList <FromTable> getTables () {
		ArrayList <FromTable> tables = new ArrayList <> ();
		
		
		for ( FromTable t: this.fromTableList ) {
			if ( t.getIsSubstring() == true ) {
				tables.addAll(t.getSubquery().getTables());
			} else {
				tables.add(t);
				
			}
			
			
		}
		
		
		
		return tables;
	}
	
	
	public FromTable getTableByAlias(String alias) {
		FromTable fromTable = null;
		
		System.out.println("getTableByAlias");
		
		
		
			for (FromTable ft: fromTableList) {
				
				
				if (ft.getIsSubstring() == false) {
					if (ft.getAlias().equals(alias.toUpperCase())) {
						
						fromTable = ft;
							
						
					}
				} else {
					
					fromTable = ft.getSubquery().getTableByAlias(alias);
					
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
