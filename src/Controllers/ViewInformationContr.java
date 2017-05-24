package Controllers;

import Global.Session;
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
}
