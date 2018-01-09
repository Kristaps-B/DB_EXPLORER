package SQLParser;

import java.util.ArrayList;

public class FromClause {
	
	
	private String sql;
	
	
	private ArrayList <FromTable> fromTableList = new ArrayList <> ();	
	private ArrayList <WhereClause> ansiWhereList = new ArrayList <> ();
	
	private ParserUtils parserUtils = new ParserUtils();
	
	private SelectQuery mainQuery;

	public FromClause (SelectQuery mainQuery) {
		
		this.mainQuery = mainQuery;
		
		
	}
	
	
	public void addFromString (String sql) {
		this.sql = sql;
		
		
		splitFromClauses (this.sql);
	}
	
	

	
	private void splitFromClauses (String sql) {
		
		
		int nmbOfBrackets = 0;
		
	
		
		for (String str: parserUtils.parseString(this.sql, ",")) {
			
				processTable(str);
			
		}
				
				
		
		
	}
	
	
	
	private void processTable (String str) {
		
		
		// Check if it is ANSI JOIN
		
		if (parserUtils.isTextInside(str, "JOIN")) {
			// Looks like its is ANSI JOIN
			System.out.println("ANSI Join  -> ");
			
			// Remove LEFT, INNER, RIGHT keywoad, because its not needed
			str = str.replace(" LEFT ", " ");
			str = str.replaceAll(" INNER ", " ");
			str = str.replaceAll(" RIGHT", " ");
			str = str.replaceAll(" OUTER", " ");
			
			processAnsi(str);
			
			
		} else {
			
			System.out.println("-----------  FROM subquery  ----------------");
			
			fromTableList.add(new FromTable(str, this.mainQuery));		
			
			System.out.println("---------------------------------------------------------------------");
			
		}
		
		

		
		
		
	}
	
	
	private void processAnsi (String str) {
		// Split by JOIN
		
		for (String s: parserUtils.parseString(str, " JOIN ")) {
			
			System.out.println("-------> ANSI part: " + s);
			
			// Split by ON, to get JOIN
			
			String tablePart = "";
			String wherePart = "";
			
			
			tablePart = parserUtils.getFirstPart(s, " ON ");
			wherePart = parserUtils.getSecondPart(s, " ON ");
			
			if (wherePart != "") {
				wherePart = parserUtils.getBracketsContent(wherePart);	
			}
			
			
			
			System.out.println("ANSI Table: " + tablePart + " Where part: " + wherePart);
			
			fromTableList.add(new FromTable(tablePart, this.mainQuery));
			
			
			ansiWhereList.add(new WhereClause(wherePart, this.mainQuery));
			
			
			
		}
		
	}
	
	
	public ArrayList <FromTable> getTables () {
		ArrayList <FromTable> tables = new ArrayList <> ();
		
		
		for ( FromTable t: this.fromTableList ) {
			if ( t.getIsSubstring() == true ) {
				tables.addAll(t.getSubquery().getTableList());
			} else {
				tables.add(t);
				
			}
			
			
		}
		
		
		
		return tables;
	}
	
	
	public FromTable getTableByAlias(String alias) {
		FromTable fromTable = null;
		
		System.out.println("---------------getTableByAlias----------------- alias: " + alias);
		
		
		
			for (FromTable ft: fromTableList) {
				
				
				if (ft.getIsSubstring() == false) {
					if (ft.getAlias().equals(alias.toUpperCase())) {
						
						System.out.println("Get by alias (not substring)");
						fromTable = ft;
						return fromTable;
						
					}
				} else {
					System.out.println("Get by alias: " + alias + " from substring");
					// System.out.println("Substring query: " + ft.getSubquery().getQuery());
					
					
					fromTable = ft.getSubquery().getFirstQuery().getTableByAlias(alias);
					
					
					if (fromTable != null) {
						return fromTable;
					}
					
					
				}
				
				
					
			}
			
			System.out.println("Dont found Table by ALIAS!");
			
			
			if ( fromTable == null ) {
				System.out.println("1");
				SelectQuery outerQuery = this.mainQuery.getOuterQuery();
				
				
				if (outerQuery != null) {
					System.out.println("Get table by ALIAS from outer table");
					
					fromTable = outerQuery.getFrom().getTableByAlias(alias);
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
	
	
	public FromTable getColumnTable( String tableAlias, String column) {
		FromTable fromTable = null;
		
		
		
		for (FromTable ft: fromTableList) {
			
			System.out.println("FromClause.getColumnTable: Is Substring: " + ft.getIsSubstring() + " Table " + ft.getTable() + " Alias: " + ft.getAlias());
			
			
			if (ft.getIsSubstring() == false) {
				if (ft.getAlias().equals(tableAlias.toUpperCase()) || (ft.getAlias().length() == 0 &&  ft.getTable().equals(tableAlias.toUpperCase()))) {
					
					System.out.println("Get by alias (not substring)");
					fromTable = ft;
					return fromTable;
					
				}
			} else {
				
				// System.out.println("Substring query: " + ft.getSubquery().getQuery());
				
				
				//fromTable = ft.getSubquery().getTableByAlias(alias);
				
				
				ColumnSelect columnSelect = ft.getSubquery().getFirstQuery().getColumnSelect(column);
				
				if (columnSelect == null) {
					continue;
				}
				
				fromTable = ft.getSubquery().getFirstQuery().getColumnTable(columnSelect.getTableAlias(), columnSelect.getColumn());
				
				
				
				// System.out.println("FromClause.getColumnTable: Subquery FROMTable? :: "   + ft.getTable());
				
				if (fromTable != null) {
					return fromTable;
				}
				
				
			}
			
			
				
		}
		
		
		
		
		
		return fromTable;
	}
	
	
	public ArrayList <WhereClause> getAnsiWhereList () {
		return this.ansiWhereList;
	}
	
	
	
 
	
}
