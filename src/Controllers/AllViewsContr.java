package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.AllViewsMod;
import javafx.scene.web.WebEngine;

public class AllViewsContr extends Controller  {
	
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
		
		this.allViewsMod.updateViews();
		
		
		
	}
	
	
	public String loadViews(int limit, int offset) {
		String result = "";
		
		
		result = this.allViewsMod.loadViews(
				 limit, offset);
		
		
		return result;		
		
		
	}
	
	
	
	public void analyseView (String owner, String view_name) {
		System.out.println("ANALYSE: " + owner + "." + view_name);
		
		
		this.allViewsMod.analyseView(
				owner, 
				view_name);
	}
	
	
	
	public void goToInformation(String owner, String viewName) {
		
		
		Session.owner = owner;
		Session.viewName = viewName;
		
		System.out.println("AllViewsContr.goToInformation");
		URL url = Main.class.getResource("../WEB/html/view_information.html");
		engine.load(url.toExternalForm());
		
	}
	
}
