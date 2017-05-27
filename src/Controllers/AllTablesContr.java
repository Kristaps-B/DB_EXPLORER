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
	
	
	public String loadTables() {
		String result = "";
		
		
		result = this.allTablesMod.loadTables();
		
		
		return result;
		
	}
	
	
	public void goToInformation(String tableId) {
		
		
		Session.currentTableId = tableId;
		System.out.println("AllTableContr.goToInformation");
		URL url = Main.class.getResource("../WEB/html/table_information.html");
		engine.load(url.toExternalForm());
		
	}
	
		
	
	
}
