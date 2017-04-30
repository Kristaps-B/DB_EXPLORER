package Database;

import java.sql.*;

public class SQLLiteOld {
	
	private String dbName = "jdbc:sqlite:visualization.db";
	
	public SQLLiteOld() {
		
		
		
		Connection c = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(dbName);
			
			System.out.println("Created SQLLite database!");
			
			//Create tables
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS nodes (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"name CHAR(30)," +
			"type CHAR(30)" +
			")"
			;
			
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS edges (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"node1 INT," +
					"node2 INT" +
					")"
					;
			
			
			stmt.close();
			c.close();
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
	
	
	public void saveTables(ResultSet result) {
		try {
			Connection c = null;
		    Statement stmt = null;
		    
		    Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(dbName);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
			while (result.next()) {
				if (nodeExists(c, result.getString("table_name"), "TABLE")) {
					System.out.println("Table " + result.getString("table_name") + " already exists!");
					continue;
				}
				
				String sql = "INSERT INTO nodes (name, type) " +
						" VALUES (" +
						"'"+result.getString("table_name") + "'," +
						"'TABLE'" +
						")";
				System.out.println("Inserted in SQLLite DB table: " + result.getString("table_name"));
				stmt.executeUpdate(sql);
				
				
			}
			
			System.out.println("Tabulas tika pievienotas DB!");
			
			stmt.close();
			c.commit();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
	
	
	public void saveColumns(ResultSet result) {
		try {
			Connection c = null;
		    Statement stmt = null;
		    
		    Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(dbName);
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			
			while (result.next()) {
				if (nodeExists(c, result.getString("column_name"), "COLUMN")) {
					System.out.println("Column " + result.getString("column_name") + " already exists!");
					continue;
				}
				
				
				String sql = "INSERT INTO nodes (name, type) " +
						" VALUES (" +
						"'"+result.getString("column_name") + "'," +
						"'COLUMN'" +
						")";
				System.out.println("Inserted in SQLLite DB column: " + result.getString("column_name"));
				stmt.executeUpdate(sql);
			}
			
			System.out.println("Kolonnas tika pievienotas DB!");
			
			stmt.close();
			c.commit();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
	
	private boolean nodeExists (Connection c, String name, String type) throws Exception {
		boolean result = false;
		Statement stmt = c.createStatement();
		
		String sql = "SELECT 1 FROM nodes WHERE name = '"+ name +"' AND type='"+type+"' LIMIT 1";
		
		ResultSet res = stmt.executeQuery(sql);
		while (res.next()) {
			result = true;
		}
		stmt.close();
		return result;
		
	}
	
	
}
