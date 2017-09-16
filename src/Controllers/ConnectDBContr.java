package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Database.DatabaseCreator;
import Global.Session;
import Main.Main;
import Models.ConnectDBMod;
import SQLParser.FromTable;
import SQLParser.SelectParser;
import javafx.scene.web.WebEngine;

public class ConnectDBContr {
	
	private WebEngine engine;
	
	
	private ConnectDBMod connectDBMod = new ConnectDBMod();
	
	public ConnectDBContr(WebEngine engine) {
		this.engine = engine;
		
		
		
		testSQLParsing();
		
	}
	
	public void test(String val) {
		
		System.out.println(val);
	}
	
	
	
	public void checkConnection(String ip, String port, String sid, String un, String pass) {
		
		
		
		try {
			connectDBMod.checkConnection(ip, port, sid, un, pass);
			engine.executeScript("pageAlert('Connection: SUCCESS')");
		 
		} catch (Exception e) {
			engine.executeScript("pageAlert('EXCEPTION "+ e.getMessage() +"')");
		}
		
		
		
	}
	
	
	public void creatingMainDB() {
		System.out.println("Creating LOCAL DB - START");
		
		
		DatabaseCreator dbCreator = new DatabaseCreator();
		
		dbCreator.createMainDatabase();
		
	}
	
	
	public void saveDatabase(String ip, String port, String sid, String username, String password) {
		
		System.out.println("Start SAVING_DB - IP: " + ip + " PORT: " + port+ " SID: " + sid);
		
		try {
			connectDBMod.insertDatabase(ip, port, sid, username, password);
		} catch (Exception e) {
			engine.executeScript("pageAlert('EXCEPTION "+ e.getMessage() +"')");
		}
		
		
	}
	
	public void goToMenu(String ip, String port, String sid, String username, String password) {
		

		try {
			// connectDBMod.checkConnection(ip, port, sid, username, password);
			
			
			Session.ip = ip;
			Session.port = port;
			Session.sid = sid;
			Session.username = username;
			Session.password = password;
			Session.dBUserString = "jdbc:sqlite:" + sid + ".db";
			
			
			
			DatabaseCreator dbCreator = new DatabaseCreator();
			
			dbCreator.createUserDatabase();
			
			
			
			URL url = Main.class.getResource("../WEB/html/main_menu.html");
			engine.load(url.toExternalForm());
			
			
			
			
		} catch (Exception e) {
			engine.executeScript("pageAlert('EXCEPTION "+ e.getMessage() +"')");
		}
		
		
		
	}
	
	
	
	public String getDatabases() {
		String result = "";
		
		
		result = connectDBMod.loadDatabases();
		
		return result;
	}
	
	
	public void deleteDatabase(int id) {
		System.out.println("Delete database id: " + id);
		
		try {
			connectDBMod.deleteDatabase(id);
			
			engine.executeScript("pageAlert('Database deleted id: "+ id +"!')");
			
		} catch (Exception e) {
			engine.executeScript("pageAlert('EXCEPTION "+ e.getMessage() +"')");
		}
			
		
	}
	
	
	public void testSQLParsing() {
		System.out.println("=======================================================");
		System.out.println("             Test SQL Parsing");
		System.out.println("=======================================================");
		
		
		String sql = "WITH tabb123 AS (SELECT 1 FROM dual) SELECT d /* Test comment removal  */ , (select a, b FROM tab123 ) FROM dual a, tabula b, (SELECT 1 FROM table1 d) c -- FROM TAG \n WHERE 1=1  -- End comment";
		
		
		SelectParser selectParser = new SelectParser(sql);
		
		
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("                   SQL Information");
		System.out.println("--------------------------------------------------------------------");
		
		
		for (FromTable t: selectParser.getSelectQuery().getTables() ) {
			System.out.println("Table: " + t.getTable());
		}
		
		
		
		System.out.println("Get table of alias D: " + selectParser.getSelectQuery().getTableByAlias("D").getTable());
		
		
		
		
		System.out.println("#####################################################################");
	}
	
	
	
}
