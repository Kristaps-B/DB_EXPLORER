package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseCreator {
	
	
	private String dbName = DBGlobal.dBString;
	
	public DatabaseCreator() {
		
		
	}
	
	
	public void createDatabase () {
		System.out.println("1 - DatabaseCreator.createDatabase");

		
		try ( Connection c = DriverManager.getConnection(dbName);
				Statement stmt = c.createStatement();
				) {
			Class.forName("org.sqlite.JDBC");
		
			
			System.out.println("Created SQLLite database!");
			int result = 0;
			
			
			// Create table all_databases
			String sql = "";
			sql = "CREATE TABLE IF NOT EXISTS all_databases (" +
					"id          INTEGER PRIMARY KEY AUTOINCREMENT," +
					"ip          CHAR(30)," +
					"port        CHAR(30)," +
					"sid         CHAR(30) unique," +
					"username    CHAR(30)," +
					"password    CHAR(30)"  +
					")"
					;
			
			result = stmt.executeUpdate(sql);
			System.out.println("Created table 'all_databases'");
			
			// Create table all_tables
			
			sql = "CREATE TABLE IF NOT EXISTS all_tables (" +
			"id          INTEGER PRIMARY KEY AUTOINCREMENT," +
			"name        CHAR(30)," +
			"type        CHAR(30)" +
			")"
			;
			
			
			
			
			result = stmt.executeUpdate(sql);
			
			System.out.println("Created table 'all_tables'");
			
			
			sql = "CREATE TABLE IF NOT EXISTS all_users (" +
					"id                INTEGER PRIMARY KEY AUTOINCREMENT," +
					"user_id           CHAR(30)," +
					"username           CHAR(30) unique" +
					")"
					;
					
					
					
					
			result = stmt.executeUpdate(sql);
			
			
			System.out.println("Created table 'all_users'");
			
			
			
		
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
	}
	
	
}
