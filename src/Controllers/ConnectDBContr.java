package Controllers;

import java.io.File;
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
import Utils.GuiUtils;
import javafx.scene.web.WebEngine;

public class ConnectDBContr {
	
	private WebEngine engine;
	
	
	private ConnectDBMod connectDBMod = new ConnectDBMod();
	
	public ConnectDBContr(WebEngine engine) {
		this.engine = engine;
		
		 
		
	 
		
	 
		
	}
	
	public void alert(String message) {
		new GuiUtils().alert(message);
	}
	
	public int confirm(String message) {
		return new GuiUtils().confirmation (message);
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
			
			
			File dbfile=new File(".");
			Session.dBUserString = "jdbc:sqlite:"+dbfile.getAbsolutePath()+"\\db\\" + sid + ".db";
 
			
			
			
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
		
		
		String sql = "WITH tabb123 AS (SELECT t1.column1 FROM tab1 t1)" +
		 " SELECT tab2 t2 /* Test comment removal  */ , (select t3.column2, t3.column3 FROM tab3 t3 ) " + 
		" FROM tab4 t4, tab5 t5, (SELECT t6.col5 FROM tab6 t6 ) sq1, tab7 t7 -- FROM TAG \n " +
		", tab8 t8 INNER JOIN tab9 t9  ON (t8.col1 = t9.col2) " +
		" WHERE t4.id = t5.id  -- End comment";
		
		
		SelectParser selectParser = new SelectParser(sql);
		
		
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("                   SQL Information");
		System.out.println("--------------------------------------------------------------------");
		
		
		for (FromTable t: selectParser.getSelectQuery().getTables() ) {
			System.out.println("Table: " + t.getTable());
		}
		
		
		
		System.out.println("Get table of alias D: " + selectParser.getSelectQuery().getTableByAlias("t8").getTable());
		
		
		
		
		System.out.println("#####################################################################");
	}
	
	
	
}
