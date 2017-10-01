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
		
		
		
		this.allTablesMod.updateTables();
		
		
	}
	
	
	public String loadTables(int limit, int offset) {
		String result = "";
		
		
		result = this.allTablesMod.loadTables(limit, offset - 1);
		
		
		return result;
		
	}
	
	
	public void goToInformation(String owner, String tableName) {
		
		Session.owner = owner;
		Session.tableName= tableName;
		System.out.println("AllTableContr.goToInformation");
		URL url = Main.class.getResource("../WEB/html/table_information.html");
		engine.load(url.toExternalForm());
		
	}
	
	
	public void analyseTable(int id, String owner, String table) {
		
		System.out.println("AllTablesContr.analyseTable id: " + id + " owner: " + owner + " table: " + table);
		
		this.allTablesMod.analyseTable(id, owner, table);
		
	}
	
		
	
	
}
