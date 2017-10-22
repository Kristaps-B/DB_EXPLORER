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
	
}
