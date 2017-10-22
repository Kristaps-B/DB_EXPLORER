package Controllers;

import java.net.URL;

import Database.SQLLite;
import Database.SQLOracle;
import Global.Session;
import Json.ArrayJson;
import Main.Main;
import Models.AllViewsMod;
import Models.ViewInformationMod;
import Results.ViewTableResult;
import Results.ViewsResult;
import javafx.scene.web.WebEngine;

public class ViewInformationContr extends Controller  {
	private WebEngine engine;
	
	private ViewInformationMod viewInformationMod;
	
	public ViewInformationContr(WebEngine engine) {
		this.engine = engine;
		viewInformationMod = new ViewInformationMod();
	}
	
	
	
	public String getViewName () {
		String result = "View_Name";
		
		  result = Session.owner + "." + Session.viewName;
		
		return result;
		
	}
	
	
	public void toAllViews() {
		
		URL url = Main.class.getResource("../WEB/html/all_views.html");
		engine.load(url.toExternalForm());	
		
	}
	
	
	
	
	
	
	public String loadViewsTables() {
		String result = "";
		
		
		result = viewInformationMod.loadViewsTables();
		
		return result;
		
		
	}
	
	
	public String loadJoins () {
		String result = "";
		
		
		result = viewInformationMod.loadJoins();
		
		return result;
		
		
	}
	
	
	public String loadColumns() {
		String result = "";
		
		result = viewInformationMod.loadColumns();
		
		return result;
		
	}
	
	
}
