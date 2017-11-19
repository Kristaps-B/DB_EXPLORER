package Models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONArray;
import org.json.JSONObject;

import Global.Session;
import PdfUtils.Column;
import PdfUtils.PDFTableGenerator;
import PdfUtils.StandartTable;
import PdfUtils.Table;
import PdfUtils.TableBuilder;

public class ExportDocMod {
	
	

    
    
    public ExportDocMod () {
    	
    }
    
    
    
    public void createPdf(String path) {
    	
		System.out.println(" -> Export Documentation! <- ");
		
		

		
		String filePath =  path + "\\documentation_" + Session.sid +  ".pdf";
		
		System.out.println("File Path: " + filePath);

		PDDocument document = new PDDocument();
		
		
		PDPage page = new PDPage();
		page.setMediaBox(PDRectangle.A4);
		document.addPage(page);
		
		



	
		
		
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
		      contentStream.showText("Created by DATABASE_EXPLORER");  
		      contentStream.endText();
		      
		      

		      
		      
		      
		      
		      contentStream.close();	

		      //Adding text in the form of string 
		       
		      
		      

		      
		      
		      createTableTable(document);

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
    
    
    public void createTableTable(PDDocument document) throws IOException, Exception {
    	
    	
    	
    	AllTablesMod tabMod = new AllTablesMod ();
    	
    	
    	
    	
    	String tablesJson = tabMod.loadTables(-1, -1);
    	
    	
     
    	
    	JSONArray jsonArray = new JSONArray(tablesJson);
    	
    	
    	
		 List<Column> columns = new ArrayList<Column>();
		    columns.add(new Column("Id", 20));
	        columns.add(new Column("Owner", 30));
	        columns.add(new Column("Name", 120));
	        
	        
	    String [][] content = new String [jsonArray.length()][3]; 
 
        
	    for (int i = 0; i < jsonArray.length(); i ++) {
	    	
	    	content [i][0] = jsonArray.getJSONObject(i).getString("table_id");
	    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
	    	content [i][2] = jsonArray.getJSONObject(i).getString("table_name");
	    	
	    }
	        
 
	    
	    
        
        Table table = (new StandartTable(columns, content)).getTable();


	     new PDFTableGenerator().generatePDF(document, table, "1. TABLES");
	      
	      
	      
    }
    
 
}
