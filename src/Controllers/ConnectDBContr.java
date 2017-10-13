package Controllers;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Database.DatabaseCreator;
import Global.Session;
import Main.Main;
import Models.ConnectDBMod;
import SQLParser.FromTable;
import SQLParser.SelectParser;
import Utils.GuiUtils;
import javafx.scene.web.WebEngine;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.*;

public class ConnectDBContr {
	
	private WebEngine engine;
	
	
	private ConnectDBMod connectDBMod = new ConnectDBMod();
	
	public ConnectDBContr(WebEngine engine) {
		this.engine = engine;
		
		 
		
	 
		testSQLParsing();
	 
		
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
		
		System.out.println("------------------------- PARSER ---------------------------------");
		
		 try {
			Statement stmts = CCJSqlParserUtil.parse("SELECT * FROM tab1");
			
			Select selectStatement = (Select) stmts;
			
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			
			List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
			
			
			
			for (String t: tableList) {
				System.out.println("Table: " + t);
			}
			
			
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 System.out.println("------------------------- PARSER ---------------------------------");
	}
	
	
	
}
