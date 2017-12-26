package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
import Models.TableInformationMod;
import Models.ViewInformationMod;
import javafx.scene.web.WebEngine;

public class TableInformationContr  extends Controller {
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
		
		 result = Session.owner + "." + Session.tableName;
		//result = tableInformationMod.getTableName(tableId);
		
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
	
	
	public void changePage(String pageName) {
		System.out.println(pageName);
		
		MainMenuContr mMenuContr = new MainMenuContr(this.engine);
		
		if (pageName.equals("ALL_USERS")) {
			mMenuContr.allUsers();
		} else if (pageName.equals("ALL_TABLES")) {
			mMenuContr.allTables();
		} else if (pageName.equals("ALL_VIEWS")) {
			mMenuContr.allViews();
		} else if (pageName.equals("ALL_PLSQL")) {
			mMenuContr.allPlsql();
		} else if (pageName.equals("TABLE_GRAPH")) {
			mMenuContr.graphPage();
		} else if (pageName.equals("EXPORT_DOCUMENTATION")) {
			mMenuContr.exportDocumentation();
		} else if (pageName.equals("CHANGE_DATABASE")) {
			mMenuContr.changeDatabase();
		}
		
		
	}	
	
}
