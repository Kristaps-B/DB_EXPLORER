package Controllers;

import java.net.URL;

import Main.Main;
import Models.AllPlsqlMod;

import javafx.scene.web.WebEngine;

public class AllPlsqlContr {
	private WebEngine engine;
	private AllPlsqlMod allPlsqlMod;
	
	public AllPlsqlContr (WebEngine engine) {
		
		
		this.engine = engine;
		this.allPlsqlMod = new AllPlsqlMod();		
		
	}
	
	
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
	}
	
	
	public String loadPlsql() {
		String result = "";
		System.out.println("Load all Plsql");
		
		result = this.allPlsqlMod.loadPlsql();
		
		
		return result;
		
		
		
	}
	
	
	public void updatePlsql() {
		
		System.out.println("Update Plsql");
		
		this.allPlsqlMod.updatePlsql();
		
		
	}
}
