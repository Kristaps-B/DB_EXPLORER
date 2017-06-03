package SQLParser;

import java.util.ArrayList;

public class SelectClause {
	
	private String sql;
	
	
	private ArrayList <ColumnSelect> columnSelectList = new ArrayList <> ();

	public SelectClause () {
		
	}
	
	
	public void addSelectString (String sql) {
		this.sql = sql;
		
		splitSelectClause(this.sql);
	}
	
	
	
	
	private void splitSelectClause (String sql) {
		
		String [] selectArray = this.sql.split(",");
		
		System.out.println("Split SELECT");
		
		for (String str: selectArray) {
			
			str = str.trim();
			
			// selectColumnList.add(str);
			
			columnSelectList.add(new ColumnSelect(str));
			
			System.out.println(str);
		}
		
	}
	
	
	public ArrayList <ColumnSelect> getColumnList () {
		return this.columnSelectList;
	}

	
	
}
