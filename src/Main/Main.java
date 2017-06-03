package Main;
import java.awt.Dialog;
import java.net.URL;


import javax.swing.JOptionPane;

import Controllers.AllTablesContr;
import Controllers.AllUsersContr;
import Controllers.AllViewsContr;
import Controllers.ConnectDBContr;
import Controllers.MainMenuContr;
import Controllers.TableInformationContr;
import Controllers.ViewInformationContr;
import Database.Database;
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
	
	private String title = "DATABASE_EXPLORER (Oracle) project V.0.1 - KBA(2017)";
	
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
		
		
		
		
		//Add view
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		addEvents(engine);
		
		URL url = Main.class.getResource("../WEB/html/connect_db.html");
		engine.load(url.toExternalForm());
		layout.getChildren().addAll(myWebView);
		
		//Create scene and show Window
		Scene scene = new Scene(layout, 960, 740);
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
		            	System.out.println("PAGE: CONNECT_DB");
		            	window.setMember("app", new ConnectDBContr(engine));
		            	engine.executeScript("create()");
		            } else if (page.equals("main_menu.html")) {
		            	
		            	System.out.println("PAGE: MAIN_MENU");
		            	window.setMember("app", new MainMenuContr(engine));
		            	engine.executeScript("create()"); 
		            } else if (page.equals("all_users.html")) {
		            	
		            	System.out.println("PAGE: ALL_USERS");
		            	window.setMember("app", new AllUsersContr(engine));
		            	engine.executeScript("create()"); 
		            } else if (page.equals("all_tables.html")) {
		            	
		            	System.out.println("PAGE: ALL_TABLES");
		            	window.setMember("app", new AllTablesContr(engine));
		            	engine.executeScript("create()"); 
		            } else if (page.equals("all_views.html")) {
		            	
		            	System.out.println("PAGE: ALL_VIEWS");
		            	window.setMember("app", new AllViewsContr(engine));
		            	engine.executeScript("create()"); 
		            } else if (page.equals("view_information.html")) {
		            	
		            	System.out.println("PAGE: VIEW_INFORMATION");
		            	window.setMember("app", new ViewInformationContr(engine));
		            	engine.executeScript("create()"); 
		            } else if (page.equals("table_information.html")) {
		            	
		            	System.out.println("PAGE: TABLE_INFORMATION");
		            	window.setMember("app", new TableInformationContr(engine));
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



/*

# listener.ora Network Configuration File: C:\app\KB\product\12.1.0\dbhome_2\network\admin\listener.ora
# Generated by Oracle configuration tools.



SID_LIST_LISTENER =
  (SID_LIST =
    (SID_DESC =
      (SID_NAME = orcl)
      (ORACLE_HOME = C:\app\KB\product\12.1.0\dbhome_2)
      (PROGRAM = extproc)
      (ENVS = "EXTPROC_DLLS=ONLY:C:\app\KB\product\12.1.0\dbhome_2\bin\oraclr12.dll")
    )
  )

LISTENER =
  (DESCRIPTION_LIST =
    (DESCRIPTION =
     
      (ADDRESS = (PROTOCOL = IPC)(KEY = EXTPROC1521))
    )
	(DESCRIPTION =
      (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.103)(PORT = 1521))
    )


  )
  
  
  
  
  
  
  
  
  # tnsnames.ora Network Configuration File: C:\app\KB\product\12.1.0\dbhome_2\network\admin\tnsnames.ora
# Generated by Oracle configuration tools.

LISTENER_ORCL =
  (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.103)(PORT = 1521))


ORACLR_CONNECTION_DATA =
  (DESCRIPTION =
    (ADDRESS_LIST =
      (ADDRESS = (PROTOCOL = IPC)(KEY = EXTPROC1521))
    )
    (CONNECT_DATA =
      (SID = CLRExtProc)
      (PRESENTATION = RO)
    )
  )

ORCL =
  (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.0.103)(PORT = 1521))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = orcl)
    )
  )

PDBORCL =

  (DESCRIPTION =

    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))

    (CONNECT_DATA =

      (SERVER = DEDICATED)

      (SERVICE_NAME = pdborcl)

    )

  )




 */



