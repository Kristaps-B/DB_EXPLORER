package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import javafx.scene.web.WebEngine;

public class MainMenuContr {
	
	private WebEngine engine;
	
	public MainMenuContr(WebEngine engine) {
		this.engine = engine;
		
	}
	
	
	public void changeDatabase() {
		
		URL url = Main.class.getResource("../WEB/html/connect_db.html");
		engine.load(url.toExternalForm());
		
	}
	
	
	public void allTables() {
		URL url = Main.class.getResource("../WEB/html/all_tables.html");
		engine.load(url.toExternalForm());
	}
	
	
	public void allUsers() {
		URL url = Main.class.getResource("../WEB/html/all_users.html");
		engine.load(url.toExternalForm());
		
	}
	
	
	public void allViews() {
		URL url = Main.class.getResource("../WEB/html/all_views.html");
		engine.load(url.toExternalForm());		
	}
	
	
	public void allPlsql() {
		
		URL url = Main.class.getResource("../WEB/html/all_plsql.html");
		engine.load(url.toExternalForm());		
	}
	
	public void graphPage () {
		URL url = Main.class.getResource("../WEB/html/graph_page.html");
		engine.load(url.toExternalForm());			
		
	}
	
	
	public String getDBInformation () {
		
		String result = "{";
		
		result += "\"ip\":\"" + Session.ip + "\",";
		result += "\"port\":\"" + Session.port + "\",";
		result += "\"sid\":\"" + Session.sid + "\",";
		result += "\"username\":\"" + Session.username + "\",";
		result += "\"password\":\"" + Session.password + "\"";
		
		result += "}";
		
		return result;
	}
	
}
