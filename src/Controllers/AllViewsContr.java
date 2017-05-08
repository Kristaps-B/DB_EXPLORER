package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.AllViewsMod;
import javafx.scene.web.WebEngine;

public class AllViewsContr {
	
	private WebEngine engine;
	
	private AllViewsMod allViewsMod;
	
	
	public AllViewsContr (WebEngine engine) {
		

		this.engine = engine;		
		this.allViewsMod = new AllViewsMod();
		
	}
	
	public void toMenu () {
		System.out.println("AllViewsContr.toMenu");
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
	}
	
	
	
	public void updateViews () {
		
		System.out.println("AllViewsContr.updateViews");
		
		this.allViewsMod.updateViews(Session.ip, 
				Session.port, 
				Session.sid, 
				Session.username, 
				Session.password);
		
		
		
	}
	
	
	public String loadViews() {
		String result = "";
		
		
		result = this.allViewsMod.loadViews(
				Session.ip, 
				Session.port, 
				Session.sid, 
				Session.username, 
				Session.password);
		
		
		return result;		
		
		
	}
	
	
	
	public void analyseView (String owner, String view_name) {
		System.out.println("ANALYSE: " + owner + "." + view_name);
		
		
		this.allViewsMod.analyseView(Session.ip, 
				Session.port, 
				Session.sid, 
				Session.username, 
				Session.password, 
				owner, 
				view_name);
	}
	
}
