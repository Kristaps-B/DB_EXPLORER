package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.PlsqlInformationMod;
import Models.TableInformationMod;
import Models.ViewInformationMod;
import javafx.scene.web.WebEngine;

public class PlsqlInformationContr extends Controller  {
	private WebEngine engine;
	
	private PlsqlInformationMod plsqlInformationMod;
	
	public PlsqlInformationContr(WebEngine engine) {
		this.engine = engine;
		plsqlInformationMod = new PlsqlInformationMod();
	}
	
	
	public void toAllPlsql() {
		System.out.println("To all_Plsql");
		URL url = Main.class.getResource("../WEB/html/all_plsql.html");
		engine.load(url.toExternalForm());	
		
	}
	
	
	
	public String getPlsqlName() {
		String result = "";
				
		result = Session.owner + "." + Session.plsqlName;		
				
		return result;		
	}
	
	
	
}
