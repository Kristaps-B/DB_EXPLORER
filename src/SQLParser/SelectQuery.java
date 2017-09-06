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
		
		
		System.out.println("Select query START");
		
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
	
	
	
	
	private String removeComments (String inSql) {
		String outSql = "";
		
		System.out.println("Remove comments");
		
		boolean isComment = false;
		
		
		for (int  i = 0; i < this.sql.length(); i ++) {
			
			char c = this.sql.charAt(i);
			
			System.out.println("Character: " + c);
			
			if (atLocation(i, "--", this.sql) || atLocation(i, "/*", this.sql)) {
				
				isComment = true;
				// System.out.println("Comment starts");
			}
			
			
			if (isComment == false) {
				outSql += c;
			} else {
				if ( i > 0 && atLocation(i-1, "*/", this.sql)) {
					
					isComment = false;
					
				}
				
				if (c == '\n'  || c == '\r' ) {
					isComment = false;
				}
			}
			
			
		}
		
		
		
		
		return outSql;
		
	}
	
	
	
	
	
	private void splitClauses() {
		
		
		int select_start_index = -1;
		int select_end_index   = -1;
		int from_start_index   = -1;
		int from_end_index     = -1;
		int where_start_index  = -1;
		int where_end_index    = -1;
		
		
		
		
		
		this.sql = this.sql.toUpperCase();
		
		System.out.println("SQL before removing comments: " + this.sql);
		
		this.sql = this.removeComments(this.sql);
		
		System.out.println("SQL after removing comments: " + this.sql);
		
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
				if ( i == this.sql.length() - 1 ) {
					from_end_index = i;
					
					System.out.println("FROM_END_INDEX: " + from_end_index);
					
					
				}
				else if (atLocation(i, " WHERE ", this.sql) ) {
					// WHERE START
					
					from_end_index = i;
					
					System.out.println("FROM_END_INDEX: " + from_end_index);
					
					where_start_index = i + " WHERE".length();
					System.out.println("WHERE_START_INDEX: " + where_start_index);
				}
				
			}
			// WHERE END
			else if (where_end_index == -1) {
				if (atLocation(i, " GROUP BY ", this.sql)
					|| atLocation(i, " ORDER BY ", this.sql)	
					||  i == this.sql.length() - 1 
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
			return false;
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
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println("Select start index: " + select_start_index + " select end index: " + select_end_index );
		String selectString = this.sql.substring(select_start_index, select_end_index);
		System.out.println("SELECT_STRING: " + selectString);
		
		
		selectClause.addSelectString(selectString);
		
		// FROM
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println("From start index: " + from_start_index + " from end index: " + from_end_index);
		String fromString = this.sql.substring(from_start_index, from_end_index ); 
		System.out.println("FROM_STRING: "   + fromString);
		
		
		fromClause.addFromString(fromString);
		
		// WHERE
		System.out.println("-----------------------------------------------------------------------------------------------");
		if (where_start_index != -1) {
			
			System.out.println("Where start index: " + where_start_index + " where end index: " + where_end_index);
			String whereString = this.sql.substring(where_start_index, where_end_index ); 
			System.out.println("WHERE_STRING: "  + whereString);
			
			whereClause.addWhereString(whereString);			
		}

		
		
		
		
		 
		
		
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
