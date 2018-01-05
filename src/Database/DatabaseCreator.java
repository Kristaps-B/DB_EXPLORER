package Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Global.Session;

public class DatabaseCreator {
	
	
	private String dbName = DBGlobal.dBMainString;
	
	public DatabaseCreator() {
		
		
	}
	
	
	public void createMainDatabase () {
		System.out.println("1 - DatabaseCreator.createMainDatabase");
		
		File dbfile=new File(".");
		DBGlobal.dBMainString = "jdbc:sqlite:"+dbfile.getAbsolutePath()+"\\db\\MAIN.db";
		dbName = DBGlobal.dBMainString;
		
		try ( Connection c = DriverManager.getConnection(dbName);
				Statement stmt = c.createStatement();
				) {
			Class.forName("org.sqlite.JDBC");
		
			
			System.out.println("Created SQLLite database!");
			int result = 0;
			
			
			// Create table databases
			String sql = "";
			sql = "CREATE TABLE IF NOT EXISTS databases (" +
					"id          INTEGER PRIMARY KEY AUTOINCREMENT," +
					"ip          CHAR(30)," +
					"port        CHAR(30)," +
					"sid         CHAR(30) unique," +
					"username    CHAR(30)," +
					"password    CHAR(30)"  +
					")"
					;
			
			result = stmt.executeUpdate(sql);
			System.out.println("Created table 'databases'");
			
		
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
		
	}
	
	
	public void createUserDatabase () {
		System.out.println("1 - DatabaseCreator.createUserDatabase");
		
 

		
		try ( Connection c = DriverManager.getConnection(Session.dBUserString);
				Statement stmt = c.createStatement();
				) {
			Class.forName("org.sqlite.JDBC");
		
			
			System.out.println("Created SQLLite database!");
			int result = 0;
			
			String sql = "";
			
			// Create table tables
			
			sql = "CREATE TABLE IF NOT EXISTS tables (" +
			"id                INTEGER PRIMARY KEY AUTOINCREMENT," +
			"table_id          INTEGER," +
			"owner             CHAR(30)," +
			"table_name        CHAR(30)," +
			"comments          CHAR(300)," +
			"examine_time      DATETIME," +
			"unique (owner, table_name)" +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'tables'");
			
			
			sql = "CREATE TABLE IF NOT EXISTS users (" +
					"id                 INTEGER PRIMARY KEY AUTOINCREMENT," +
					"user_id            CHAR(30)," +
					"username           CHAR(30) unique," +
					"active             CHAR(1), " +
					"examine_time       DATETIME" +
					")"
					;
					
					
					
					
			result = stmt.executeUpdate(sql);
			
			
			System.out.println("Created table 'users'");
			
			
			
			// Create table views
			
			sql = "CREATE TABLE IF NOT EXISTS views (" +
			"id                INTEGER PRIMARY KEY AUTOINCREMENT," +
			"view_id          INTEGER," +
			"owner             CHAR(30)," +
			"view_name        CHAR(30)," +
			"examine_time       DATETIME," +
			"comments          CHAR(300)," +
			"unique (owner, view_name)" +
			")"
			;
			
			
	
			
			result = stmt.executeUpdate(sql);

			
			
			
			System.out.println("Created table 'columns'");
			
			
			
			// Create table columns
			
			sql = "CREATE TABLE IF NOT EXISTS columns (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"column_id             INTEGER,"  +
			"column_name           CHAR(30)," +
			"table_id              INTEGER,"  +
			"data_type             CHAR(30)," +
			"comments              CHAR(300)," +
			"unique (column_name, table_id)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'columns'");
			
			
			
			// Create table view_tables	
			sql = "CREATE TABLE IF NOT EXISTS view_tables (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"view_id               INTEGER,"  +
			"source_owner          CHAR(30)," +
			"source_name           CHAR(30)," +
			"alias                 CHAR(30)," +
			"unique (view_id, source_owner, source_name)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'view_tables'");
			
			
			
			
			// Create table view_tables

			sql = "CREATE TABLE IF NOT EXISTS joins (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"left_owner            CHAR(30)," +
			"right_owner           CHAR(30)," +
			"left_table            CHAR(30)," +
			"right_table           CHAR(30)," +
			"left_column           CHAR(30)," +
			"right_column          CHAR(30)," +
			"unique (left_owner, right_owner, left_table, right_table, left_column, right_column)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'joins'");
			
			
			
			// Create table join_sources
			sql = "CREATE TABLE IF NOT EXISTS join_sources (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"join_id               INTEGER," +
			"source_id             INTEGER," +
			"source_type           CHAR(30)," +
			"unique (join_id, source_id, source_type)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'join_sources'");
			
			
			
			// Create table column_sources
			sql = "CREATE TABLE IF NOT EXISTS view_columns (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"view_id               INTEGER," +		
			"object_owner          CHAR(30)," +
			"object_name           CHAR(30)," +
			"column_name           CHAR(30)," +
			"alias                 CHAR(30)," +
			"unique (view_id, column_name, alias)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'view_columns'");
			
			
			// Create table plsql
			sql = "CREATE TABLE IF NOT EXISTS plsql (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"plsql_id              INTEGER,"  +
			"owner                 CHAR(30)," +
			"name                  CHAR(30)," +
			"type                  CHAR(30)," +
			"parent                CHAR(30)," +
			"examine_time         DATETIME," +
			"unique (owner, name)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'plsql'");		
			
			
			
			
			// Create table arguments
			sql = "CREATE TABLE IF NOT EXISTS arguments (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"plsql_id              INTEGER,"  +
			"argument_name         CHAR(30)," +
			"data_type             CHAR(30)," +
			"position              INTEGER," +
			"in_out                CHAR(30)," +
			"unique (plsql_id, argument_name)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'arguments'");				
			
			
			// Create table dml_statements
			sql = "CREATE TABLE IF NOT EXISTS dml_statements (" +
			"id                    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"plsql_id              INTEGER,"  +
			"table_id              INTEGER," +
			"type                  CHAR(30)," +
			"unique (plsql_id, table_id, type)"  +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'dml_statements'");				
			
			
		
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
	}
	
	
}
