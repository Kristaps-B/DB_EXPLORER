package SQLParser;

import java.util.ArrayList;

public class SelectQuery {
	
	
	private ArrayList <SelectQuery> selectUnionList = new ArrayList <>();
	
	
	private SelectClause selectClause = new SelectClause();
	private FromClause fromClause     = new FromClause();
	private WhereClause whereClause   = new WhereClause();
	
	
	private String sql;
	
	public SelectQuery () {
		
	}
	
	
	public void createSelect (String sql) {
		this.sql = sql;
		
		splitClauses ();
	}
	
	
	public void addSelectQuery (SelectQuery selectQuery) {
		
		selectUnionList.add(selectQuery);
		
	}
	
	
	public SelectClause getSelect () {
		return this.selectClause;
	}
	
	public FromClause getFrom () {
		return this.fromClause;
	}
	
	public WhereClause getWhere () {
		return this.whereClause;
	}
	
	
	
	
	
	private void splitClauses() {
		
		
		int select_start_index = -1;
		int select_end_index   = -1;
		int from_start_index   = -1;
		int from_end_index     = -1;
		int where_start_index  = -1;
		int where_end_index    = -1;
		
		
		this.sql = this.sql.toUpperCase();
		
		this.sql = this.sql.replace("\n", " ").replace("\r", " ");
		
		
		this.sql = " " + this.sql.trim().replaceAll(" +", " ") + " ";
		
		
		System.out.println(this.sql);
		
		for (int  i = 0; i < this.sql.length(); i ++) {
			
			char c = this.sql.charAt(i);
			
			// System.out.println(i + ") " + c);
			
			
			// SELECT START
			if (select_start_index == -1) {
				if (atLocation(i, " SELECT ", this.sql)) {
					select_start_index = i + " SELECT".length();
					
					System.out.println("SELECT_START_INDEX: " + select_start_index);
				}
			}
			// SELECT END;
			else if (select_end_index == -1) {
				if (atLocation(i, " FROM ", this.sql)) {
					select_end_index = i;
					
					System.out.println("SELECT_END_INDEX: " + select_end_index);
					
					// FROM START
					
					from_start_index = i + " FROM".length();
					
					System.out.println("FROM_START_INDEX: " + from_start_index);
				}
				
			}
			// FROM END
			else if (from_end_index == -1) {
				if (atLocation(i, " WHERE ", this.sql)) {
					from_end_index = i;
					
					System.out.println("FROM_END_INDEX: " + from_end_index);
					
					// WHERE START
					
					where_start_index = i + " WHERE".length();
					
					System.out.println("WHERE_START_INDEX: " + where_start_index);
				}
				
			}
			
			
			// WHERE END
			else if (where_end_index == -1) {
				if (atLocation(i, " GROUP BY ", this.sql)
					|| atLocation(i, " ORDER BY ", this.sql)	
					) {
					where_end_index = i;
					
					System.out.println("WHERE_END_INDEX: " + where_end_index);
				
				}
				
			}
			
			
			
			
		}
		
		
		getClauses (
				select_start_index, 
				select_end_index, 
				from_start_index, 
				from_end_index, 
				where_start_index, 
				where_end_index	
		);
		
		
	}
	
	
	private boolean atLocation (int index, String search, String fullString) {
		
		boolean result = false;
	
		if ((index) == fullString.length() - 1) {
			return true;
		}	
		else if ((index + search.length() +1) >= fullString.length() - 1) {
			
			return false;
		} 
		 
		 
		String sbstr = fullString.substring(index, index + search.length());
		
		
		if (sbstr.equals(search)) {
			return true;
		}
		
		
		return result;
	}
	
	
	
	private void getClauses (
		int select_start_index,
		int select_end_index,
		int from_start_index,
		int from_end_index,
		int where_start_index,
		int where_end_index
			
			) {
		
		
		
		
		System.out.println("GET_CLAUSES");
		
		
		// SELECT
		String selectString = this.sql.substring(select_start_index, select_end_index);
		System.out.println("SELECT_STRING: " + selectString);
		
		
		selectClause.addSelectString(selectString);
		
		// FROM
		String fromString = this.sql.substring(from_start_index, from_end_index ); 
		System.out.println("FROM_STRING: "   + fromString);
		
		
		fromClause.addFromString(fromString);
		
		// WHERE
		String whereString = this.sql.substring(where_start_index, where_end_index ); 
		System.out.println("WHERE_STRING: "  + whereString);
		
		whereClause.addWhereString(whereString);
		
		
		
		
		 
		
		
	}
	
	
	public ArrayList <FromTable> getTables () {
		return this.fromClause.getTables();
	}
	
	
	public FromTable getTableByAlias (String alias) {
		return this.fromClause.getTableByAlias(alias);
	}
	
	
	public FromTable getTableByTable (String table) {
		return this.fromClause.getTableByTable(table);
	}
	
	public ArrayList <ColumnSelect> getColumnList () {
		
		return this.selectClause.getColumnList();
	}
	
	public ArrayList <WhereExpression> getWhereList () {
		return this.whereClause.getExpressionList();
	}
	

}
