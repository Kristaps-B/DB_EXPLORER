package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import Global.Session;
import Main.Main;
import javafx.scene.web.WebEngine;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ExportDocContr extends Controller {
	
	private WebEngine engine;	
	
	private Stage primaryStage;
	
	public ExportDocContr (WebEngine engine, Stage primaryStage) {
		this.engine = engine;
		this.primaryStage = primaryStage;
	}
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
		
		
	}
	
	
	public void exportDoc () {
		
		System.out.println(" -> Export Documentation! <- ");
		
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File("c:/");
		chooser.setInitialDirectory(defaultDirectory);
		
		
		File selectedDirectory = chooser.showDialog(this.primaryStage);
		
		String filePath =  selectedDirectory.getAbsolutePath() + "\\documentation_" + Session.sid +  ".pdf";
		
		System.out.println("File Path: " + filePath);

		PDDocument document = new PDDocument();
		
		
		PDPage my_page = new PDPage();
		document.addPage(my_page);
		
		
		try {
			
			
			
			document.save(filePath);
		
		
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
}
