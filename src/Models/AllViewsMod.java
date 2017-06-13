package Models;

import java.util.ArrayList;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Results.CountResult;
import Results.IdResult;
import Results.TableResult;
import Results.UsersResult;
import Results.ViewTextResult;
import Results.ViewsResult;
import SQLParser.ColumnSelect;
import SQLParser.FromTable;
import SQLParser.SelectParser;
import SQLParser.SelectQuery;
import SQLParser.WhereExpression;

public class AllViewsMod {
	
	private String loadActiveUsers () {
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		UsersResult rs = new UsersResult();
		
		String sql = "SELECT id, user_id, username, active FROM all_users WHERE active='Y'";
		
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			if (rs.getColumns().size() > 0 ) {
				
				result = "AND owner IN (";
				
				for (UsersResult.Row row: rs.getColumns()) {
					
					result += "'" + row.username + "',";
				}
				
				result += "'')";
				
			} else {
				result = " AND 1=2";
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return result;
	}	
	
	
	public void updateViews () {
		
		
		String activeUsers = this.loadActiveUsers();
		
		System.out.println("ACTIVE_USERS: " + activeUsers);
		
		
		String sql = "SELECT rownum ID, rownum VIEW_ID, OWNER, VIEW_NAME FROM all_views WHERE 1=1 " + activeUsers;
		
		SQLOracle oQuerie = new SQLOracle();
		
		ViewsResult  rs = new ViewsResult();
		
		
		try {
			
			oQuerie.queryDatabase(sql, rs);
			
			
			System.out.println("------------------------------------------------");	
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewsResult.Row row = rs.getColumns().get(i);
				
				System.out.println(row.id + " " + row.view_name);
				
				 
				 this.saveView(row);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	
	public void saveView (ViewsResult.Row row) {
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_views"
		+ "(view_id, owner, view_name) "
		+ "VALUES (" 
		+ "'" + row.view_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.view_name  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("VIEW " + row.view_name + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	
	public String loadViews (String ip, String port, String sid, String username, String password) {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewsResult rs = new ViewsResult();
		
		String sql = "SELECT id, view_id, owner, view_name FROM all_views";
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			
			System.out.println("---------LOAD_VIEWS--------------");
			
			
			ArrayJson aJson = new ArrayJson ();
			aJson.startJson();
			
			for (int i = 0; i < rs.getColumns().size(); i++) {
				
				ViewsResult.Row row = rs.getColumns().get(i);
				
				// System.out.println(" " + row.table_name);
				 
				
				aJson.addValue("id", "" + row.id);
				aJson.addValue("view_id", "" + row.view_id);
				aJson.addValue("owner", row.owner);
				aJson.addValue("view_name", row.view_name);
				
				aJson.newRow();
			}
			
			aJson.endJson();
			
			
			result = aJson.getJson();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		System.out.println(result);		
		
		return result;
		
		
	}
	
	
	
	public void analyseView(
			String owner, String view_name) {
		
		String sql = "SELECT OWNER, VIEW_NAME, TEXT FROM all_views WHERE owner = '" + owner + "' AND view_name = '" + view_name + "'";
		
		SQLOracle oQuerie = new SQLOracle();
		
		ViewTextResult  rs = new ViewTextResult();
		
		try {
			oQuerie.queryDatabase(sql, rs);
			
			String sqlView = rs.getColumns().get(0).text;
			
			System.out.println("VIEW TEXT: " + sqlView);
			SelectParser selectParser = new SelectParser(sqlView);
			
			saveViewInformation (selectParser, owner, view_name);
			
			
		} catch (Exception e) {
			
			
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void saveViewInformation (SelectParser selectParser, String owner, String view) {
		
		SelectQuery selectQuery = selectParser.getSelectQuery();
		
		
		int viewId = getViewId(view);
		
		System.out.println("viewId: " + viewId);
		
		
		ArrayList <FromTable> tableList =  selectQuery.getTables();
		
		System.out.println("----------------- TABLES ---------------------");
		
		for (FromTable ft: tableList) {
			
			String table = ft.getTable();
			String alias = ft.getAlias();
			
			
			int tableId = getTableId(table);
			
			System.out.println("Table: " + table + " alias: " + alias + " tableId: " + tableId);
			
			
			saveViewTable(viewId, tableId, alias);
			
		}
		
		System.out.println("----------------- COLUMNS ---------------------");
		//ArrayList <>  = selectQuery.
		ArrayList <ColumnSelect> columnList = selectQuery.getColumnList();
		
		for (ColumnSelect cs: columnList) {
			
			
 
			String table = selectQuery.getTableByAlias(cs.getTable()).getTable();
			String column = cs.getColumn();
			String alias = cs.getAlias();
			
			
			
			int tableId = this.getTableId(table);
			int columnId = this.getColumnId(tableId, column);
			
			this.saveColumnSource(viewId, tableId, columnId);
			
			System.out.println("Table: " + table + " table_id: " + tableId  + " column: " + column + " column_id: " + columnId + " alias: " + alias);
			
		}	
		
		
		System.out.println("------------- WHERE --------------------------");
		ArrayList <WhereExpression> whereList = selectQuery.getWhereList();
		
		
		for (WhereExpression we: whereList) {
			
			
			String expression    =  we.getExpression();
			String leftAlias     =  we.getLeftAlias();
			String rightAlias    =  we.getRightAlias();
			
			String leftColumn    =  we.getLeftColumn();
			String rightColumn   =  we.getRightColumn();
			
			String leftTable     = selectQuery.getTableByAlias(leftAlias).getTable();
			String rightTable    = selectQuery.getTableByAlias(rightAlias).getTable();
			
			
			
			int leftTableId     = this.getTableId(leftTable);
			int rightTableId    = this.getTableId(rightTable);
			
			int leftColumnId    = this.getColumnId(leftTableId, leftColumn);
			int rightColumnId    = this.getColumnId(rightTableId, rightColumn);
			
			
			 
			
			System.out.println("Left table: " + leftTable + " (" + leftTableId +") Right Table: " + rightTable + " ("+ rightTableId +") Left Column: " + leftColumn + " (" + leftColumnId + ") Right Column: " + rightColumn + " (" + rightColumnId + ") Expression: " + expression);
			System.out.println("");
			
			// Check if Join Exists
			if (!this.joinExists(leftTableId,
				rightTableId,
				leftColumnId,
				rightColumnId)) {
				
				// Save Join
				this.saveTableJoin (
					leftTableId,
					rightTableId,
					leftColumnId,
					rightColumnId
				);
				
				System.out.println();
				
				
			} else {
				 System.out.println("Join already Exists!");
			}
			
			
			// Find Join ID
			int joinId = this.findJoinId(					
			leftTableId,
			rightTableId,
			leftColumnId,
			rightColumnId);
			
			System.out.println("Found Join_ID: " + joinId);
			
			
			
			// Save Join View LINK
			this.saveJoinLink (
				viewId,
				joinId
			);
		
		}
		
		
	}
	
	
	private int getViewId (String view) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM all_views WHERE view_name = '" + view + "'";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		return result;		
		
		
	}
	
	
	private int getTableId (String table) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM all_tables WHERE table_name = '" + table + "'";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		return result;
		
		
	}
	
	
	private int getColumnId (int table_id, String column) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM all_columns " + 
		" WHERE table_id = " + table_id + " " +
		" AND column_name = '" + column + "'";
				;
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		
		return result;
		
		
	}	
	
	
	
	private void saveViewTable (int viewId, int tableId, String alias) {
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_view_tables"
		+ "(view_id, table_id, alias) "
		+ "VALUES (" 
		+ "" + viewId  + ","
		+ "" + tableId  + ","
		+ "'" + alias  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("VIEW_TABLE viewID: " + viewId + " tableID: " + tableId + " was inserted!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	private void saveTableJoin (
		int leftTableId,
		int rightTableId,
		int leftColumnId,
		int rightColumnId
			) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into all_table_joins "
		+ "(left_table_id, right_table_id, left_column_id, right_column_id) "
		+ "VALUES (" 
		+ "" + leftTableId  + ","
		+ "" + rightTableId  + ","
		+ "" + leftColumnId  + ","
		+ "" + rightColumnId  + ""
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("TABLE_JOIN created! ");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	private boolean joinExists (
			int leftTableId,
			int rightTableId,
			int leftColumnId,
			int rightColumnId	
			) {
		boolean result = false;
		
		
		String sql = "SELECT COUNT(1) CNT FROM all_table_joins " + 
		" WHERE " +
		" ( left_table_id = " + leftTableId + " " +
		" AND right_table_id = " + rightTableId + " " +
		" AND left_column_id = " + leftColumnId + " " +
		" AND right_column_id = " + rightColumnId + " " +
		" ) OR " +
		" ( left_table_id = " + rightTableId + " " +
		" AND right_table_id = " + leftTableId + " " +
		" AND left_column_id = " + rightColumnId + " " +
		" AND right_column_id = " + leftColumnId + " " +
		" ) "
		;
				
		
		
		SQLLite sqlLite = new SQLLite();
		
		CountResult rs = new CountResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			int cnt = rs.getColumns().get(0).cnt;
			
			if (cnt > 0) {
				result = true;
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}
		
		
		
		return result;
		
	}
	
	
	private int findJoinId (
		int leftTableId,
		int rightTableId,
		int leftColumnId,
		int rightColumnId		
			
	 ) {
		int result = -1;
		
		String sql = "SELECT ID  FROM all_table_joins " + 
		" WHERE " +
		" ( left_table_id = " + leftTableId + " " +
		" AND right_table_id = " + rightTableId + " " +
		" AND left_column_id = " + leftColumnId + " " +
		" AND right_column_id = " + rightColumnId + " " +
		" ) OR " +
		" ( left_table_id = " + rightTableId + " " +
		" AND right_table_id = " + leftTableId + " " +
		" AND left_column_id = " + rightColumnId + " " +
		" AND right_column_id = " + leftColumnId + " " +
		" ) "
		;
				
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
		 
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			
		}		
		
		
		return result;
		
	}
	
	
	
	private void saveJoinLink (
			int viewId,
			int joinId
		) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into join_sources"
		+ "(join_id, source_id, source_type) "
		+ "VALUES (" 
		+ "" + joinId  + ","
		+ "" + viewId  + ","
		+ "'" + "VIEW"  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("Inserted into JOIN_SOURCES table value join_id: " + joinId + " view_id: " + viewId);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
		
		
	}
	
	
	private void saveColumnSource(int viewId, int tableId, int columnId) {
		
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into column_sources "
		+ "(column_id, source_id, source_type) "
		+ "VALUES (" 
		+ "" + columnId  + ","
		+ "" + viewId  + ","
		+ "'" + "VIEW"  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("Inserted into COLUMN_SOURCES table value column_id: " + columnId + " table_id: " + tableId + " viewId: " + viewId);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
		
		
	}
	

}
