package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.AllViewsMod;
import Models.ViewInformationMod;
import javafx.scene.web.WebEngine;

public class ViewInformationContr {
	private WebEngine engine;
	
	private ViewInformationMod viewInformationMod;
	
	public ViewInformationContr(WebEngine engine) {
		this.engine = engine;
		viewInformationMod = new ViewInformationMod();
	}
	
	
	
	public String getViewName () {
		String result = "View_Name";
		
		String viewId = Session.currentViewId;
		result = viewInformationMod.getViewName(viewId);
		
		return result;
		
	}
	
	
	public void toAllViews() {
		
		URL url = Main.class.getResource("../WEB/html/all_views.html");
		engine.load(url.toExternalForm());	
		
	}
	
	
}
