package Models;

import java.util.ArrayList;

import Database.DbUtils;
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
import SQLParser.MainSelectQuery;
import SQLParser.SelectQuery;
import SQLParser.WhereExpression;

public class AllViewsMod {
	
	private String loadActiveUsers () {
		
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		UsersResult rs = new UsersResult();
		
		String sql = "SELECT id, user_id, username, active FROM users WHERE active='Y'";
		
		
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
		
		
		String sql = "SELECT rownum ID, rownum VIEW_ID, OWNER, VIEW_NAME, '' EXAMINE_TIME FROM all_views WHERE 1=1 " + activeUsers;
		
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
		
		String sql = "insert into views"
		+ "(view_id, owner, view_name, examine_time) "
		+ "VALUES (" 
		+ "'" + row.view_id  + "',"
		+ "'" + row.owner  + "',"
		+ "'" + row.view_name  + "',"
		+ "NULL"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("VIEW " + row.view_name + " was inserted!!");
			
		} catch (Exception e) {
			System.out.println("saveView exception: " +e.getMessage());
		}
		
	}
	
	
	
	
	public String loadViews (int limit, int offset) {
		String result = "";
		
		
		SQLLite sqlLite = new SQLLite();
		
		ViewsResult rs = new ViewsResult();
		
		String sql = "SELECT id, view_id, owner, view_name, examine_time FROM views LIMIT " + limit + "  OFFSET " + offset;
		
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
				aJson.addValue("examine_time", row.examine_time);
				
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
	
	
	
	private void update_examine_time (String owner, String viewName) throws Exception {
		SQLLite sqlLite = new SQLLite();
		
		String sql  = "UPDATE views " +
		"SET examine_time = DATETIME('now', 'localtime')" +
	    "WHERE owner = '" + owner +"' AND view_name = '" + viewName + "' ";
		
		
		try {
			
			System.out.println("Updating examine_time " + owner + " view_name: " + viewName);
			
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
		 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
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
			
			
			
			
			System.out.println("##############################################################################");
			System.out.println("##############################################################################");
			System.out.println("###                       PARSE START                                       ##");
			System.out.println("##############################################################################");
			System.out.println("##############################################################################");
			MainSelectQuery selectParser = new MainSelectQuery(sqlView);
			
			System.out.println("##############################################################################");
			System.out.println("##############################################################################");
			System.out.println("##                        PARSE END                                         ##");
			System.out.println("##############################################################################");
			System.out.println("##############################################################################");
			
			
			update_examine_time (owner, view_name);
			
			saveViewInformation (selectParser, owner, view_name);
			
			
			
			
			
		} catch (Exception e) {
			
			
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private void saveViewInformation (MainSelectQuery selectParser, String owner, String view) {
		
		// SelectQuery selectQuery = selectParser.getSelectQuery();
		
		DbUtils dbUtils = new DbUtils();
		
		
		int viewId = getViewId(owner, view);
		
		System.out.println("viewId: " + viewId);
		
		
		ArrayList <FromTable> tableList =  selectParser.getTableList();
		
		System.out.println("----------------- TABLES ---------------------");
		
		for (FromTable ft: tableList) {
			String tabOwner = ft.getOwner();
			String table = ft.getTable();
			String alias = ft.getAlias();
			
			// Set default owner
			if (tabOwner.equals("")) {
				tabOwner = owner;
			}
			
			
			int tableId = dbUtils.getTableId(tabOwner, table);
			
			System.out.println("Owner: " + tabOwner + "Table: " + table + " alias: " + alias + " tableId: " + tableId);
			
			
			saveViewTable(viewId, tabOwner, table, alias);
			
		}
		
		System.out.println("----------------- COLUMNS ---------------------");
		//ArrayList <>  = selectQuery.
		ArrayList <ColumnSelect> columnList = selectParser.getColumnList();
		
		System.out.println("[[ Got " + columnList.size() + " columns!");
		
		for (ColumnSelect cs: columnList) {
			
			
 
			// String table = selectParser.getColumnTable( cs.getTableAlias(), cs.getColumn()).getTable();
			String table = cs.getTable();
			String column = cs.getColumn();
			String alias = cs.getAlias();
			
			System.out.println("table: " + table);
			System.out.println("column: " + column);
			System.out.println("alias: " + alias);
			
			
			
			int tableId = dbUtils.getTableId(owner, table);
			int columnId = dbUtils.getColumnId(tableId, column);
			
			this.saveColumnSource(viewId, tableId, columnId);
			
			System.out.println("Table: " + table + " table_id: " + tableId  + " column: " + column + " column_id: " + columnId + " alias: " + alias);
			
		}	
		
		
		System.out.println("------------- JOINS --------------------------");
		ArrayList <WhereExpression> whereList = selectParser.getWhereList();
		
		
		for (WhereExpression we: whereList) {
			
			
			String expression    =  we.getExpression();
			String leftAlias     =  we.getLeftAlias();
			String rightAlias    =  we.getRightAlias();
			
			String leftColumn    =  we.getLeftColumn();
			String rightColumn   =  we.getRightColumn();
			
			String leftTable     =  we.getLeftTable();
			String rightTable    =  we.getRightTable();
			
			String leftOwner     = owner;
			String rightOwner    = owner;
			
	
			
			
			 
			
			// System.out.println("Left table: " + leftTable + " (" + leftTableId +") Right Table: " + rightTable + " ("+ rightTableId +") Left Column: " + leftColumn + " (" + leftColumnId + ") Right Column: " + rightColumn + " (" + rightColumnId + ") Expression: " + expression);
			System.out.println("-------------------------");
			
			// Check if Join Exists
			if (!dbUtils.joinExists(

				leftOwner,
				rightOwner,
				leftTable,
				rightTable,
				leftColumn,
				rightColumn
					)) {
				
				// Save Join
				System.out.println("Join not exists!");
				dbUtils.saveTableJoin (
						leftOwner,
						rightOwner,
						leftTable,
						rightTable,
						leftColumn,
						rightColumn
				);
				
				System.out.println("Saved Join");
				
				
			} else {
				 System.out.println("Join already Exists!");
			}
			
			
			// Find Join ID
			int joinId = dbUtils.findJoinId(					
					leftOwner,
					rightOwner,
					leftTable,
					rightTable,
					leftColumn,
					rightColumn);
			
			System.out.println("Found Join_ID: " + joinId);
			
			
			
			// Save Join View LINK
			dbUtils.saveJoinLink (
				viewId,
				joinId,
				"VIEW"
			);
		
		}
		
		
	}
	
	
	private int getViewId (String owner, String view) {
		int result = -1;
		
		
		
		
		String sql = "SELECT id FROM views WHERE view_name = '" + view + "' AND owner = '" + owner + "' ";
		
		
		SQLLite sqlLite = new SQLLite();
		
		IdResult rs = new IdResult();
		
		try {
			sqlLite.query(sql, rs, Session.dBUserString);
			
			result = rs.getColumns().get(0).id;
			
			
		} catch (Exception e) {
			System.out.println("AllViewsMod.getViewId: " + e.getMessage());
			
			
		}
		
		
		
		
		return result;		
		
		
	}
	
	
 
 
	
	
	
	private void saveViewTable (int viewId, String sourceOwner, String sourceName, String alias) {
		SQLLite  sqlLite = new SQLLite();
		
		String sql = "insert into view_tables"
		+ "(view_id, source_owner, source_name, alias) "
		+ "VALUES (" 
		+ "" + viewId  + ","
		+ "'" + sourceOwner  + "',"
		+ "'" + sourceName  + "',"
		+ "'" + alias  + "'"
		+ ")"
		;
		
		
		
		try {
			sqlLite.insertUpdate(sql, Session.dBUserString);
			
			System.out.println("VIEW_TABLE viewID: " + viewId + " sourceOwner: " + sourceOwner + " sourceName: " + sourceName + " was inserted!");
			
		} catch (Exception e) {
			System.out.println("AllViewsMod.saveViewTable: " + e.getMessage());
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
			System.out.println("AllViewsModel.saveColumnSource: " + e.getMessage());
		}		
		
		
		
	}
	

}
