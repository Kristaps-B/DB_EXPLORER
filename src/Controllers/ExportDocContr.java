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
	
	
    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.A3;
    private static final float MARGIN = 20;
    private static final boolean IS_LANDSCAPE = true;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;	
	
	
	public ExportDocContr (WebEngine engine, Stage primaryStage) {
		this.engine = engine;
		this.primaryStage = primaryStage;
	}
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
		
		
	}
	
	
	public void exportDoc (String path) {
		
		System.out.println(" -> Export Documentation! <- ");
		
		

		
		String filePath =  path + "\\documentation_" + Session.sid +  ".pdf";
		
		System.out.println("File Path: " + filePath);

		PDDocument document = new PDDocument();
		
		
		PDPage page = new PDPage();
		document.addPage(page);
		
		
		 List<Column> columns = new ArrayList<Column>();
	        columns.add(new Column("FirstName", 90));
	        columns.add(new Column("LastName", 90));
	        columns.add(new Column("Email", 230));
	        columns.add(new Column("ZipCode", 43));
	        columns.add(new Column("MailOptIn", 50));
	        columns.add(new Column("Code", 80));
	        columns.add(new Column("Branch", 39));
	        columns.add(new Column("Product", 300));
	        columns.add(new Column("Date", 120));
	        columns.add(new Column("Channel", 43));

	        String[][] content = { 
	                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" },
	                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" },
	                { "FirstName", "LastName", "fakemail@mock.com", "12345", "yes", "XH4234FSD", "4334", "yFone 5 XS", "31/05/2013 07:15 am", "WEB" }
	        };

	        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

	        Table table = new TableBuilder()
	            .setCellMargin(CELL_MARGIN)
	            .setColumns(columns)
	            .setContent(content)
	            .setHeight(tableHeight)
	            .setNumberOfRows(content.length)
	            .setRowHeight(ROW_HEIGHT)
	            .setMargin(MARGIN)
	            .setPageSize(PAGE_SIZE)
	            .setLandscape(IS_LANDSCAPE)
	            .setTextFont(TEXT_FONT)
	            .setFontSize(FONT_SIZE)
	            .build();
	
		
		
		try {
			
			
		      PDPageContentStream contentStream = new PDPageContentStream(document, page);
		      
		      //Begin the Content stream 
		      contentStream.beginText(); 
		      contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
		      contentStream.newLineAtOffset(80, 500);
		      contentStream.showText("documentation: " + Session.sid.toUpperCase()  );
		      contentStream.endText();
	 
		      
		      contentStream.beginText(); 
		      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		      contentStream.newLineAtOffset(80, 480);
		      contentStream.showText("Created by Kristaps B");  
		      contentStream.endText();
		      
		      

		      
		      
		      
		      
		      contentStream.close();	

		      //Adding text in the form of string 
		       
		      
		      
		      new PDFTableGenerator().generatePDF(document, table);

		      //Ending the content stream
		      

		      System.out.println("Content added");
		      
		      
		      

		      //Closing the content stream
		      			
			
			
			document.save(filePath);
		
		
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
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
	
	
}
