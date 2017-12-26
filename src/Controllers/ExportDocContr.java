package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import Global.Session;
import Main.Main;
import Models.ExportDocMod;
import PdfUtils.Column;
import PdfUtils.PDFTableGenerator;
import PdfUtils.Table;
import PdfUtils.TableBuilder;
import javafx.scene.web.WebEngine;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ExportDocContr extends Controller {
	
	private WebEngine engine;	
	
	private Stage primaryStage;
	
	
	private ExportDocMod exportDocMod = new ExportDocMod();
	
	

	
	
	public ExportDocContr (WebEngine engine, Stage primaryStage) {
		this.engine = engine;
		this.primaryStage = primaryStage;
	}
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
		
		
	}
	
	
	public void exportDoc (String path, boolean exportTables, boolean exportViews, boolean exportPlsql) {
		

		exportDocMod.createPdf(path, exportTables, exportViews, exportPlsql);
		
		
		
	}
	
	
	public String setPath() {
		
		String result = "";
		
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File("c:/");
		chooser.setInitialDirectory(defaultDirectory);
		
		
		File selectedDirectory = chooser.showDialog(this.primaryStage);
		
		
		result = selectedDirectory.getAbsolutePath();
		
		
		
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
