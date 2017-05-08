package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.AllTablesMod;
 
import javafx.scene.web.WebEngine;

public class AllTablesContr {
	
	private WebEngine engine;
	private AllTablesMod allTablesMod;
	
	public AllTablesContr (WebEngine engine) {
		
		
		this.engine = engine;
		this.allTablesMod = new AllTablesMod();		
		
	}
	
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
	}
	
	
	public void updateTables () {
		System.out.println("AllTablesConr.updateTables");
		
		
		
		this.allTablesMod.updateTables(
				Session.ip, 
				Session.port, 
				Session.sid, 
				Session.username, 
				Session.password);
		
		
	}
	
	
	public String loadTables() {
		String result = "";
		
		
		result = this.allTablesMod.loadTables(
				Session.ip, 
				Session.port, 
				Session.sid, 
				Session.username, 
				Session.password);
		
		
		return result;
		
	}
	
	
	
	
		
	
	
}
