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
	
	
	
	public String loadProcFunc () {
		String result = "";
		
		result = this.plsqlInformationMod.loadProcFunc(Session.owner, Session.plsqlName);
		
		return result;
		
	}
	
	
	public String loadArguments () {
		String result = "";
		
		result = this.plsqlInformationMod.getArguments(Session.owner, Session.plsqlName);
		
		return result;
		
	}
	
	
	public String getType() {
		return Session.type;
	}
	
	
	public void goToInformation(String owner, String plsqlName, String type) {
		
		
		Session.owner = owner;
		Session.plsqlName = plsqlName;
		Session.type = type;
		
		System.out.println("AllTableContr.goToInformation");
		URL url = Main.class.getResource("../WEB/html/plsql_information.html");
		engine.load(url.toExternalForm());
		
	}
	
	
	public String loadDml () {
		String result = "";
		
		
		result = this.plsqlInformationMod.getDml(Session.owner, Session.plsqlName);
		
		
		return result;
		
	}
	
	
	public String loadJoins () {
		String result = "";
		
		
		result = this.plsqlInformationMod.loadJoins();
		
		return result;
		
		
	}	
	
	
	
}
