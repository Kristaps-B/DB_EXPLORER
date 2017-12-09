package PdfUtils;

import java.io.IOException;
import java.util.Arrays;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;


public class PDFTableGenerator {
	
	
	private String header;
	
	private DocumentWrapper documentWrapper;

    // Generates document from Table object
    public void generatePDF(DocumentWrapper documentWrapper, Table table ) throws IOException, Exception {
    	
    	
   
    	this.documentWrapper = documentWrapper;
    			
        PDDocument doc = null;
        try {
            doc = documentWrapper.getDocument();
            drawTable(doc, table);
            // doc.save("sample.pdf");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
    	
    	

    	
        // Calculate pagination
     
    	

    	
    	// PDPage page =  documentWrapper.getLastPage();  // generatePage(doc, table);
       // PDPageContentStream contentStream = generateContentStream(doc, page, table);
        
        
    	
    	float nextTextX = table.getMargin() + table.getCellMargin();
    	
    	
    	// Write Header!
    	writeContentLine(table.getColumnsNamesAsArray(), documentWrapper.getContentStream(), nextTextX, documentWrapper.getCurrentLineY(),
	            table);
    	drawGrid( table, documentWrapper.getContentStream(), documentWrapper.getCurrentLineY() );
    	this.documentWrapper.setCurrentLineY( this.documentWrapper.getCurrentLineY() - table.getRowHeight() );
    	
    	
    	for (int i = 0; i < table.getContent().length; i++) {
    		
    	 
    		
    		
          
    		
    		// drawTableGrid(table, currentPageContent, contentStream, tableTopY);
    		
    		drawGrid( table, documentWrapper.getContentStream(), documentWrapper.getCurrentLineY() );
            
    		
    		writeContentLine(table.getContent()[i], documentWrapper.getContentStream(), nextTextX, documentWrapper.getCurrentLineY(),
    	            table);
    		
    		this.documentWrapper.setCurrentLineY( this.documentWrapper.getCurrentLineY() - table.getRowHeight() );
    		
    		
    		
    		
    	}
    	
    	
    	
    	 
    	
    }
    
    
    private void drawGrid (Table table, PDPageContentStream contentStream, float tableTopY)   throws IOException  {
    	
    	
        // Draw row lines
        float nextY = tableTopY + table.getRowHeight();
        // for (int i = 0; i <= currentPageContent.length + 1; i++) {
            
        	//contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            contentStream.moveTo(table.getMargin(), nextY - 4);
            contentStream.lineTo(table.getMargin() + table.getWidth(), nextY - 4);
            contentStream.stroke();
            
            
            contentStream.moveTo(table.getMargin(), nextY - 4  - table.getRowHeight());
            contentStream.lineTo(table.getMargin() + table.getWidth(), nextY - 4 - table.getRowHeight());
            contentStream.stroke();
            
            
            // nextY -= table.getRowHeight();
            
       //  }

        // Draw column lines
        // final float tableYLength = table.getRowHeight() + (table.getRowHeight());
        // final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            // contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            contentStream.moveTo(nextX, nextY - 4);
            contentStream.lineTo(nextX, nextY - table.getRowHeight() - 4);
            contentStream.stroke();            
            
            
           nextX += table.getColumns().get(i).getWidth();
       }
        
        // contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
        contentStream.moveTo(nextX, nextY - 4);
        contentStream.lineTo(nextX, nextY - table.getRowHeight() - 4);
        
        contentStream.stroke();    	
    	
    }
    
 

 
    // Writes the content for one line
    private void writeContentLine(String[] lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
            Table table) throws IOException {
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent[i];
            contentStream.beginText();
            // contentStream.moveTextPositionByAmount(nextTextX, nextTextY);
            
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.newLineAtOffset(nextTextX, nextTextY);
            
            contentStream.showText(text != null ? text : "");
            contentStream.endText();
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

 

}