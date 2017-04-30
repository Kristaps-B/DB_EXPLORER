package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	
	// sqlplus hr_db/hr_db@//127.0.0.1:6060/ORCL
	// Virtual box IP: 10.0.2.15
	// 192.168.0.102:1521 
	// sqlplus hr_db/hr_db@//192.168.0.102:1521/ORCL
	private String URL = "jdbc:oracle:thin:@192.168.0.102:1521:ORCL"; // "jdbc:oracle:thin:@127.0.0.1:6060:ORCL";
	private String username = "SYSTEM";
	private String password = "kristaps";
	
	private HashMap <Integer, Table> tableList = new HashMap <>();
	
	private Connection conn;
	private int sequence = 0;
	
	private SQLLiteOld sqlLite;
	
	public Database () {
		sqlLite = new SQLLiteOld();
		
		
		
		
		queryTables();
		//queryColumns();
	}
	
	private void queryTables () {
		System.out.println("Query Tables Start");
		
		try {
			conn = DriverManager.getConnection(URL, username, password);
			
			
			
			System.out.println("Veiksmigs pieslegums!");
			/*
			
			Statement command = conn.createStatement();
			
			String sql = "SELECT table_name FROM user_tables";
			
			ResultSet rez = command.executeQuery(sql);
			
			System.out.println("Tables:");
			*/
			
			/*
			int i = 0;
			while (rez.next()) {
				String tableName = rez.getString("table_name");
				System.out.println(tableName);
				tableList.put(i, new Table(tableName));
				
				i++;
				sequence++;
			}
			*/
			
			//sqlLite.saveTables(rez);
			
			
			 conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL_EXCEPTION: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
		}
		
		
		System.out.println("Query Tables END");
	}
	
	
	private void queryColumns() {
		System.out.println("Query Columns Start");
		
		try {
			conn = DriverManager.getConnection(URL, username, password);
			
			System.out.println("Veiksmigs pieslegums!");
			
			Statement command = conn.createStatement();
			
			String sql = "SELECT table_name, column_name FROM user_tab_columns";
			
			ResultSet rez = command.executeQuery(sql);
			
			System.out.println("Columns:");
			int i = sequence;
			
			sqlLite.saveColumns(rez);
			/*
			while (rez.next()) {
				String tableName = rez.getString("table_name");
				String columnName = rez.getString("column_name");
				System.out.println(tableName + ": " + columnName);
				//tableList.add(tableName);
				Table table = findTable(tableName);
				table.addColumn(i, columnName);
				i++;
				sequence ++;
			}
			*/
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public String getNodeJson() {
		String rez = "{\"nodes\":[ ";
		
		for (int i : tableList.keySet()) {
			rez += "{\"id\": "+ i +", \"font\":{\"size\":30}, \"label\": \""+ tableList.get(i).getTableName() +"\", \"color\": \"red\", \"size\":40, \"shape\": \"box\"},";
			
			for (int j: tableList.get(i).getColumns().keySet()) {
				rez += "{\"id\": "+ (-j) +", \"font\":{\"size\":20}, \"label\": \""+ tableList.get(i).getColumns().get(j) +"\"},";
			}
			
			
		}
		
		rez = rez.substring(0,rez.length()-1);
		
		rez += "]}";
		
		
		System.out.println(rez);
		
		return rez;
	}
	
	public String getEdgeJson() {
		String rez = "{\"edges\":[";
		
		/*
		    {from: 1, to: 3},
		    {from: 1, to: 2},
		    {from: 2, to: 4},
		    {from: 2, to: 5} 
		 
		 */
		
		for (int i : tableList.keySet()) {
			//rez += "{\"id\": "+ i +", \"font\":{\"size\":30}, \"label\": \""+ tableList.get(i).getTableName() +"\", \"color\": \"red\", \"size\":40, \"shape\": \"box\"},";
			
			for (int j: tableList.get(i).getColumns().keySet()) {
				//rez += "{\"id\": "+ j +", \"font\":{\"size\":20}, \"label\": \""+ tableList.get(i).getColumns().get(j) +"\"},";
				
				rez += "{\"from\": "+i+", \"to\": "+ (-j) +"},";
			}
			
			
		}
		if (rez.length() > 11)
		rez = rez.substring(0,rez.length()-1);
		
		
		
		rez += "]}";
		
		System.out.println(rez);
		
		return rez;
	}
	
	
	public Table findTable(String name) {
		Table rez = this.tableList.get(0);
		
		for (int i = 0; i < tableList.size(); i++) {
			rez = this.tableList.get(i);
			
			if (rez.getTableName().equals(name)) {
				break;
			}
		}
		
		return rez;
	}
}
