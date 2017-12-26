package Controllers;

import java.net.URL;

import Main.Main;
import Models.GraphPageMod;
import javafx.scene.web.WebEngine;

public class GraphPageContr extends Controller  {
	
	private WebEngine engine;
	
	private GraphPageMod graphPageMod;
	
	public GraphPageContr(WebEngine engine) {
		
		this.engine = engine;
		
		this.graphPageMod = new GraphPageMod();
		
	}
	
	public String generateGraph() {
		
		
		System.out.println("GraphPageContr.generateGraph");
		String result = graphPageMod.generateGraph();
				
				
        return result;				
		
	}
	
	
	public void toMenu () {
		System.out.println("AllViewsContr.toMenu");
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
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
