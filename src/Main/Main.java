package Main;
import java.awt.Dialog;
import java.net.URL;


import javax.swing.JOptionPane;

import Controllers.AllPlsqlContr;
import Controllers.AllTablesContr;
import Controllers.AllUsersContr;
import Controllers.AllViewsContr;
import Controllers.ConnectDBContr;
import Controllers.Controller;
import Controllers.ExportDocContr;
import Controllers.GraphPageContr;
import Controllers.MainMenuContr;
import Controllers.PlsqlInformationContr;
import Controllers.TableInformationContr;
import Controllers.ViewInformationContr;
 
import SQLParser.MainSelectQuery;
//import Database.Database;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;

public class Main extends Application {
	
	private String title = "DATABASE_EXPLORER (Oracle) project V.0.2 - KBA(2017)";
	public static Controller controller;
	
	private static Stage primaryStage;
	
	public static void main (String [] args) {
		
		System.out.println("==================================================");
		System.out.println("       Database Analyser project");
		System.out.println("==================================================");
		
		//Database db = new Database();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create Window
		primaryStage.setTitle(title);
		StackPane layout = new StackPane();
		
		this.primaryStage = primaryStage;
		
		
		//Add view
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		addEvents(engine);
		
		URL url = Main.class.getResource("../WEB/html/connect_db.html");
		engine.load(url.toExternalForm());
		layout.getChildren().addAll(myWebView);
		
		//Create scene and show Window
		Scene scene = new Scene(layout, 1200, 740);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		 
		
		
		
		
	}
	
	
	private void addEvents(WebEngine engine) {
		engine.setOnAlert(new EventHandler<WebEvent<String>>(){

            @Override
            public void handle(WebEvent<String> arg0) {            
               JOptionPane.showMessageDialog(null,  arg0.getData(),"Message", JOptionPane.INFORMATION_MESSAGE);
            }

        });
		
		
		engine.setConfirmHandler(new Callback<String, Boolean>() {
	   public Boolean call(String msg) {

		     int result = JOptionPane.showConfirmDialog(null,msg, "Message",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

		     boolean b = (result != 0);
		     return !b;
		  }});
		
	  
	 
		
		engine.getLoadWorker().stateProperty().addListener(
		    new ChangeListener<State>() {
		      @Override public void changed(ObservableValue ov, State oldState, State newState) {
		
		          if (newState == Worker.State.SUCCEEDED) {
		        	//engine.executeScript("helloWorld()");
		        	  
		        	  
		        	JSObject window = (JSObject)engine.executeScript("window");
		        	
		            System.out.println("WebView loaded!");
		            
		            //System.out.println(engine.getLocation());
		            
		            String location = engine.getLocation();
		            
		            String page = location.split("/")[location.split("/").length - 1];
		            
		            System.out.println("Page: " + page);
		            
		            
		            // Create PAGE Controllers
		            if (page.equals("connect_db.html")) {
		            	
		            	Main.controller = new ConnectDBContr(engine);
		            	System.out.println("PAGE: CONNECT_DB");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()");
		            	
		            } else if (page.equals("main_menu.html")) {
		            	
		            	Main.controller =  new MainMenuContr(engine) ;
		            	System.out.println("PAGE: MAIN_MENU");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("all_users.html")) {
		            	
		            	Main.controller =  new AllUsersContr(engine);
		            	System.out.println("PAGE: ALL_USERS");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("all_tables.html")) {
		            	
		            	Main.controller = new AllTablesContr(engine);
		            	System.out.println("PAGE: ALL_TABLES");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("all_views.html")) {
		            	
		            	Main.controller = new AllViewsContr(engine);
		            	System.out.println("PAGE: ALL_VIEWS");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("view_information.html")) {
		            	
		            	Main.controller = new ViewInformationContr(engine);
		            	System.out.println("PAGE: VIEW_INFORMATION");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("table_information.html")) {
		            	
		            	Main.controller = new TableInformationContr(engine);
		            	System.out.println("PAGE: TABLE_INFORMATION");
		            	window.setMember("app", Main.controller  );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("all_plsql.html")) {
		            	
		            	Main.controller = new AllPlsqlContr(engine);
		            	System.out.println("PAGE: ALL_PLSQL");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("plsql_information.html")) {
		            	
		            	Main.controller = new PlsqlInformationContr(engine);
		            	System.out.println("PAGE: PLSQL_INFORMATION");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("graph_page.html")) {
		            	
		            	Main.controller = new GraphPageContr(engine);
		            	System.out.println("PAGE: GRAPH_PAGE");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            } else if (page.equals("export_documentation.html")) {
		            	
		            	Main.controller = new ExportDocContr(engine, primaryStage);
		            	System.out.println("PAGE: EXPORT_DOCUMENTATION");
		            	window.setMember("app", Main.controller );
		            	engine.executeScript("create()"); 
		            	
		            }
		            
		            
		           // Starting PLACE of Program 
		            
		           // Database db = new Database();
		            
		           // engine.executeScript("createGraph('"+ db.getNodeJson() +"', '"+ db.getEdgeJson()+"')");
		        }
		          
	        }
	    });
		
		JSObject window = (JSObject)engine.executeScript("window");
        window.setMember("app", new JavaApplication());
	}
	
	public class JavaApplication {
		public void output() {
			System.out.println("Hello World!");
		}
	}
	
	
	
	
	

}




