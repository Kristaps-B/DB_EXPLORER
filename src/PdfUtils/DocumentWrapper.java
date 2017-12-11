package PdfUtils;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import Global.Session;

public class DocumentWrapper {
	
	
	private PDDocument document;
	
	private int lastPageNumber = 0;
	
	private PDPage lastPage;
	
	private float currentLineY = 0;
	
	private int headerNumber = 0;
	private int subHeaderNumber = 0;
	private int subSubHeaderNumber = 0;
	
	
	private PDPageContentStream contentStream;
	
	public DocumentWrapper (PDDocument document) {
		
		this.document = document;
		
	}
	
	
	
	public PDDocument getDocument () {
		
		return this.document;
	}
	
	
	public void setLastPageNumber (int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	
	
	public int getLastPageNumber() {
		return this.lastPageNumber;
	}
	
	
	public void createPage() {
		
		PDPage page = new PDPage();
		page.setMediaBox(PDRectangle.A4);
		this.document.addPage(page);
		
		
		if (this.lastPageNumber > 0) {
			try {
				this.contentStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		try {
			
			this.contentStream = new PDPageContentStream(this.document, page, PDPageContentStream.AppendMode.APPEND, false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.lastPage = page;
		
		
		
		this.setCurrentLineY(780);
		
		this.lastPageNumber ++;
		
		try {
			this.drawPageNumber();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public PDPage getLastPage() {
		
		return this.lastPage;
		
	}
	
	public float getCurrentLineY () {
		return this.currentLineY;
	}
	
	
	public void setCurrentLineY (float currentLineY) {
		
		
		if (currentLineY < 100) {
			// Create new page
			 
			 
		  this.createPage();
	        
			
			
		} else {
			
			 this.currentLineY = currentLineY;
		}
		

	}

	
	private void drawPageNumber() throws IOException {
		
		
		if (this.lastPageNumber == 1) {
			return;
		}
		
		PDPageContentStream  contentStream = this.getContentStream();
		
		 //Begin the Content stream 
	      contentStream.beginText(); 
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	      contentStream.newLineAtOffset(540, 20);
	      contentStream.showText("" + this.lastPageNumber + ""  );
	      contentStream.endText();

	      
 
	      

	      
	      
	      
	      
	      contentStream.close();
		
	}
	
	public void addHeader(String text ) {
		try {
			
			  this.headerNumber ++;
			  this.subHeaderNumber = 0;
			  this.subSubHeaderNumber = 0;

			  this.setCurrentLineY(this.currentLineY - 20);
            
			  PDPageContentStream  contentStream = this.getContentStream();
			
			 //Begin the Content stream 
		      contentStream.beginText(); 
		      contentStream.setFont(PDType1Font.TIMES_ROMAN, 14 );
		      contentStream.newLineAtOffset(200, this.getCurrentLineY() );
		      contentStream.showText( this.headerNumber + ". " + text + ""  );
		      contentStream.endText();

		      
	 
		      
		      this.setCurrentLineY(this.currentLineY - 20);

		      
		      
		      
		      contentStream.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void addSubHeader(String text ) {
		try {
			  this.subHeaderNumber ++ ;
			  this.subSubHeaderNumber = 0;
			  
              this.setCurrentLineY(this.currentLineY - 15);
			  PDPageContentStream  contentStream = this.getContentStream();
			  
			  
			  
			
			 //Begin the Content stream 
		      contentStream.beginText(); 
		      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12 );
		      contentStream.newLineAtOffset(210, this.getCurrentLineY() );
		      contentStream.showText( this.headerNumber + "." + this.subHeaderNumber + ". " + text + ""  );
		      contentStream.endText();

		      
	 
		      
		      this.setCurrentLineY(this.currentLineY - 20);
		      
		      
		      
		      
		      contentStream.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void addSubSubHeader(String text ) {
		try {
			  this.subSubHeaderNumber ++ ;

			  PDPageContentStream  contentStream = this.getContentStream();
			  
			
			 //Begin the Content stream 
		      contentStream.beginText(); 
		      contentStream.setFont(PDType1Font.TIMES_ROMAN, 10 );
		      contentStream.newLineAtOffset(220, this.getCurrentLineY() );
		      contentStream.showText( this.headerNumber + "." + this.subHeaderNumber + "." + this.subSubHeaderNumber + ". " + text + ""  );
		      contentStream.endText();

		      
	 
		      
		      this.setCurrentLineY(this.currentLineY - 20);
		      
		      
		      
		      
		      contentStream.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	
	public PDPageContentStream getContentStream() {
		return this.contentStream;
	}
	
	
	
}
