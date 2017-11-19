package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Database.DBGlobal;
import Database.SQLLite;

public class ConnectDBMod {
	public ConnectDBMod () {
		
	}
	
	public void insertDatabase(String ip, String port, String sid, String username, String password) throws Exception {
		
		
		SQLLite lInsert = new SQLLite();
		
		
		String sql = "INSERT INTO databases (ip, port, sid, username, password) " +
				" VALUES (" +
				"'"+ ip + "'," +
				"'"+ port + "'," +
				"'"+ sid + "'," +
				"'"+ username + "'," +
				"'"+ password + "'" +
				")";
		
		
		lInsert.insertUpdate(sql, DBGlobal.dBMainString);
		
	
		
	}
	
	
	public void checkConnection (String ip, String port, String sid, String un, String pass) throws Exception {
		

		
		
		
		String URL = "jdbc:oracle:thin:@" + ip + ":" + port+ ":" + sid; 
		
		String username = un;
		String password = pass;
		
		System.out.println("IP: " + ip + " PORT: " + port+ " SID: " + sid);
		
		try ( Connection conn  = DriverManager.getConnection(URL, username, password) ) {
			
			
			
			System.out.println("Connection: SUCCESS");
			
			
			
			
		
		}
		catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
			
			
			throw new Exception("Connection ERROR!");
		}
		
	}
	
	
	
	
	
	public String loadDatabases () {
		String result = "";
		
		System.out.println("ConnectDBMod.LoadDatabases");
		
		
		result = "[";
		
		
		try ( Connection c = DriverManager.getConnection(DBGlobal.dBMainString);
				Statement stmt = c.createStatement();
				) {
			 
		 
		    
		    Class.forName("org.sqlite.JDBC");
			
			
		    c.setAutoCommit(false);
		 
			
			
				
				String sql = "SELECT id, ip, port, sid, username, password " +
				" FROM databases"
						;
				
				
			
			 
				
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while (rs.next()) {

				result += "{";
				
				result += "\"id\":\"" + rs.getString("id") + "\",";
				result += "\"ip\":\"" + rs.getString("ip") + "\",";
				result += "\"port\":\"" + rs.getString("port") + "\","; 
				result += "\"sid\":\"" + rs.getString("sid") + "\",";
				result += "\"username\":\"" + rs.getString("username") + "\",";
				result += "\"password\":\"" + rs.getString("password") + "\"";
			 
				
				
				result += "},";
			}
			
			if (result.length() > 1) {
				result = result.substring(0, result.length() - 1);
			}
				
			
			result += "]";
			
			
			//result = "[{a:a},{a:b}]";
			
			//c.commit();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
		
		System.out.println(result);
		
		return result;
	}
	
	
	public void deleteDatabase (int id) throws Exception  {
		
		try ( Connection c = DriverManager.getConnection(DBGlobal.dBMainString);
				Statement stmt = c.createStatement();
				) {
			 
		 
		    
		    Class.forName("org.sqlite.JDBC");
			
			c.setAutoCommit(false);
			
		 
			
			
				
				String sql = /* "INSERT INTO all_databases (ip, port, sid, username, password) " +
						" VALUES (" +
						"'"+ ip + "'," +
						"'"+ port + "'," +
						"'"+ sid + "'," +
						"'"+ username + "'," +
						"'"+ password + "'" +
						")";
						*/
						"DELETE FROM databases WHERE id = " + id;
					 
						
			 
				stmt.executeUpdate(sql);
				
				
			
			
			// System.out.println("Tik!");
			
			
			c.commit();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			
			throw new Exception(e.getMessage());
		}
	}
	
}
