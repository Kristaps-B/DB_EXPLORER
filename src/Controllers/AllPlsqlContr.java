package Controllers;

import java.net.URL;

import Global.Session;
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
	
	
	public String loadPlsql(int limit, int offset) {
		String result = "";
		System.out.println("Load all Plsql");
		
		result = this.allPlsqlMod.loadPlsql(limit, offset);
		
		
		return result;
		
		
		
	}
	
	
	public void updatePlsql() {
		
		System.out.println("Update Plsql");
		
		this.allPlsqlMod.updatePlsql();
		
		
	}
	
	
	public void goToInformation(String owner, String plsqlName) {
		
		
		Session.owner = owner;
		Session.plsqlName = plsqlName;
		
		System.out.println("AllTableContr.goToInformation");
		URL url = Main.class.getResource("../WEB/html/plsql_information.html");
		engine.load(url.toExternalForm());
		
	}
	
	
	public void analysePlsql ( String owner, String name, String type) {
		System.out.println("AllPlsqlContr.analysePlsql");
		
		this.allPlsqlMod.analysePlsql(owner, name, type);
		
	}
		
	
}
