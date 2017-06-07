package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.TableInformationMod;
import Models.ViewInformationMod;
import javafx.scene.web.WebEngine;

public class TableInformationContr {
	private WebEngine engine;
	
	private TableInformationMod tableInformationMod;
	
	public TableInformationContr(WebEngine engine) {
		this.engine = engine;
		tableInformationMod = new TableInformationMod();
	}
	
	public void toAllTables() {
		
		URL url = Main.class.getResource("../WEB/html/all_tables.html");
		engine.load(url.toExternalForm());	
		
	}
	
	
	
	public String getTableName () {
		String result = "Table_Name";
		
		String tableId = Session.currentTableId;
		result = tableInformationMod.getTableName(tableId);
		
		return result;
		
	}
	
	
	
	
	public String loadColumns() {
		String result = "";
		
	
		 result = this.tableInformationMod.loadColumns();
		
		
		return result;
		
	}
	
	
	public String loadTableJoins () {
		String result = "";
		
		System.out.println("TableInformationContr.loadTableJoins");
		
		result = this.tableInformationMod.loadTableJoins();
		
		return result;
		
	}
	
}
