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
import org.json.JSONException;
import org.json.JSONObject;

import Global.Session;
import PdfUtils.Column;
import PdfUtils.DocumentWrapper;
import PdfUtils.PDFTableGenerator;
import PdfUtils.StandartTable;
import PdfUtils.Table;
import PdfUtils.TableBuilder;

public class ExportDocMod {
	
	private boolean exportTables;
	private boolean exportViews;
	private boolean exportPlsql;

    
    
    public ExportDocMod () {
    	
    }
    
    
    
    public void createPdf(String path, boolean exportTables, boolean exportViews, boolean exportPlsql) {
    	
    	this.exportTables = exportTables;
    	this.exportViews = exportViews;
    	this.exportPlsql = exportPlsql;
    	
		System.out.println(" -> Export Documentation! <- ");
		
		

		
		String filePath =  path + "\\documentation_" + Session.sid +  ".pdf";
		
		System.out.println("File Path: " + filePath);

		PDDocument document = new PDDocument();
		
		
		
		
		
        DocumentWrapper documentWrapper = new DocumentWrapper(document);


	
		
		
		try {
			
			
		      	

		      //Adding text in the form of string 
		       
		      
		      

		      createMainPage(documentWrapper);
		      
		      
		      if (this.exportTables) {
		    	  createTableInformation(documentWrapper);
		      }
		      if (this.exportViews) {
		    	  createViewInformation(documentWrapper);
		      }
		      if (this.exportPlsql) {
		    	  createPlsqlInformation(documentWrapper);
		      }
		      
		      
		      
		      
		      
		      
		      

		      //Ending the content stream
		      

		      System.out.println("Content added");
		      
		      
		      

		      //Closing the content stream
		      documentWrapper.getContentStream().close();			
			
			
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
    
    
    public void createMainPage(DocumentWrapper documentWrapper) {
    	
    	
        
    	 
    	
    	
    	
		try {
			 
			  documentWrapper.createPage();
			  PDPageContentStream contentStream = documentWrapper.getContentStream();
			
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
		      
		      

		      
		      
		      
		      
		      
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		 
		documentWrapper.setCurrentLineY(0);
    	
    }
    
    
    public void createTableInformation(DocumentWrapper documentWrapper) throws IOException, Exception {
    	
    	
    	documentWrapper.addHeader("TABLES");
    	
    	AllTablesMod tabMod = new AllTablesMod ();

    	
    	String tablesJson = tabMod.loadTables(-1, -1);

    	
    	JSONArray jsonArray = new JSONArray(tablesJson);
    	
    	
    	
		 List<Column> columns = new ArrayList<Column>();
		    columns.add(new Column("Id", 60));
	        columns.add(new Column("Owner", 80));
	        columns.add(new Column("Name", 260));
	        
	        
	    String [][] content = new String [jsonArray.length()][3]; 
 
        
	    for (int i = 0; i < jsonArray.length(); i ++) {
	    	
	    	content [i][0] = jsonArray.getJSONObject(i).getString("table_id");
	    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
	    	content [i][2] = jsonArray.getJSONObject(i).getString("table_name");
	    	
	    }

	    
        
        Table table = (new StandartTable(columns, content)).getTable();


	     new PDFTableGenerator().generatePDF(documentWrapper, table);
	     
	     
	     
	    addTableInformation(documentWrapper, jsonArray );
	      
	      
	      
    }
    
    public void addTableInformation(DocumentWrapper documentWrapper, JSONArray jsonArray ) {
    	 for (int i = 0; i < jsonArray.length(); i ++) {
 	    	
 	    	 try {
 	    		 
				 documentWrapper.addSubHeader("Table: " + jsonArray.getJSONObject(i).getString("owner") + "." + jsonArray.getJSONObject(i).getString("table_name"));
			     
				 
				 
				 
				 Session.owner = jsonArray.getJSONObject(i).getString("owner");
				 Session.tableName = jsonArray.getJSONObject(i).getString("table_name");
				 
				 
				 this.addTableColumns(documentWrapper, jsonArray.getJSONObject(i).getString("table_id"));
				 
				
				 this.addTableJoins(documentWrapper);
				 
			    
 	    	 } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
 	    }
    	
    }
    
    
    public void addTableColumns(DocumentWrapper documentWrapper, String tableId) {
    	
    	TableInformationMod tabInformMod = new TableInformationMod();
    	
 
    	String jsonStr = tabInformMod.loadColumns();
    	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			documentWrapper.addSubSubHeader("Columns");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Id", 30));
		        columns.add(new Column("Name", 200));
		        columns.add(new Column("Data Type", 200));
		        
		        
		    String [][] content = new String [jsonArray.length()][3]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("column_id");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("column_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("data_type");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    }
    
    
    public void addTableJoins(DocumentWrapper documentWrapper ) {
    	
    	
    	TableInformationMod tabInformMod = new TableInformationMod();
    	
    	 
    	String jsonStr = tabInformMod.loadTableJoins();
    	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			 documentWrapper.addSubSubHeader("Joins");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Left Table", 140));
		        columns.add(new Column("Left Column", 140));
		        columns.add(new Column("Right Table", 140));
		        columns.add(new Column("Right Column", 140));
		        
		        
		    String [][] content = new String [jsonArray.length()][4]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("left_table_name");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("left_column_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("right_table_name");
		    	content [i][3] = jsonArray.getJSONObject(i).getString("right_column_name");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	        
    	
    }
    
    
    
    private  void createViewInformation(DocumentWrapper documentWrapper) {
    	documentWrapper.addHeader("VIEWS");
    	
    	AllViewsMod viewMod = new AllViewsMod ();
    	
    	String tablesJson = viewMod.loadViews(-1, -1);

    	
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(tablesJson);
			
			
			
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Id", 30));
		        columns.add(new Column("Owner", 60));
		        columns.add(new Column("Name", 210));
		        
		        
		    String [][] content = new String [jsonArray.length()][3]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("view_id");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("view_name");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     new PDFTableGenerator().generatePDF(documentWrapper, table);			
			
			
		     
		     addViewInformation (documentWrapper, jsonArray );
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	

    	
    	
    	
    }
    
    
    private void addViewInformation (DocumentWrapper documentWrapper, JSONArray jsonArray ) {
      	 for (int i = 0; i < jsonArray.length(); i ++) {
  	    	
 	    	 try {
 	    		 
				 documentWrapper.addSubHeader("View: " + jsonArray.getJSONObject(i).getString("owner") + "." + jsonArray.getJSONObject(i).getString("view_name"));
			     
				 
				 
				 
				 Session.owner = jsonArray.getJSONObject(i).getString("owner");
				 Session.viewName = jsonArray.getJSONObject(i).getString("view_name");
				 
				 
				 this.addViewsTables(documentWrapper);
				 
				 
				 this.addViewsColumns(documentWrapper);
				 
				 
				 this.addViewJoins(documentWrapper);
				 
			    
 	    	 } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
 	    }
    	
    }
    
    public void addViewsTables(DocumentWrapper documentWrapper) {
    	
    	ViewInformationMod viewInformMod = new  ViewInformationMod();
    	
 
    	String jsonStr = viewInformMod.loadViewsTables();
    	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			documentWrapper.addSubSubHeader("Tables/Views");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Id", 30));
		        columns.add(new Column("Table Name", 200));
		        columns.add(new Column("Table Alias", 200));
		        
		        
		    String [][] content = new String [jsonArray.length()][3]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("id");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("source_owner") + "." + jsonArray.getJSONObject(i).getString("source_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("alias");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    }  
    
    
    public void addViewsColumns(DocumentWrapper documentWrapper) {
    	
    	ViewInformationMod viewInformMod = new  ViewInformationMod();
    	
 
    	String jsonStr = viewInformMod.loadColumns();
    	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			documentWrapper.addSubSubHeader("Columns");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Id", 30));
		        columns.add(new Column("Table/View Name", 200));
		        columns.add(new Column("Column", 170));
		        columns.add(new Column("Column Alias", 170));
		        
		        
		    String [][] content = new String [jsonArray.length()][4]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("id");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("object_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("column_name");
		    	content [i][3] = jsonArray.getJSONObject(i).getString("alias");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    }  
    
    
   public void addViewJoins(DocumentWrapper documentWrapper ) {
    	
    	
    	ViewInformationMod viewInformMod = new ViewInformationMod();
    	
    	 
    	String jsonStr = viewInformMod.loadJoins();
    	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			
			documentWrapper.addSubSubHeader("Joins");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Left Table", 140));
		        columns.add(new Column("Left Column", 140));
		        columns.add(new Column("Right Table", 140));
		        columns.add(new Column("Right Column", 140));
		        
		        
		    String [][] content = new String [jsonArray.length()][4]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("left_table_name");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("left_column_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("right_table_name");
		    	content [i][3] = jsonArray.getJSONObject(i).getString("right_column_name");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	        
    	
    }    
    
    
    private void createPlsqlInformation(DocumentWrapper documentWrapper) {
    	documentWrapper.addHeader("PLSQL");
    	
    	
 
    	
    	AllPlsqlMod plsqlMod = new AllPlsqlMod ();
    	
    	String tablesJson = plsqlMod.loadPlsql(-1, -1);

    	
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(tablesJson);
			
			
			
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Id", 30));
		        columns.add(new Column("Owner", 60));
		        columns.add(new Column("Name", 210));
		        columns.add(new Column("Type", 140));
		        
		        
		    String [][] content = new String [jsonArray.length()][4]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("plsql_id");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("name");
		    	content [i][3] = jsonArray.getJSONObject(i).getString("type");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     new PDFTableGenerator().generatePDF(documentWrapper, table);			
			
			
		     this.addPlsqlInformation(documentWrapper, jsonArray);
		     
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	

    	
    	
    	
    }
    
    
    private void addPlsqlInformation (DocumentWrapper documentWrapper, JSONArray jsonArray ) {
     	 for (int i = 0; i < jsonArray.length(); i ++) {
 	    	
	    	 try {
	    		 
				 documentWrapper.addSubHeader("Plsql: " + jsonArray.getJSONObject(i).getString("owner") + "." + jsonArray.getJSONObject(i).getString("name"));
			     
				 
				 
				 
				 Session.owner = jsonArray.getJSONObject(i).getString("owner");
				 Session.plsqlName = jsonArray.getJSONObject(i).getString("name");
				 
				 
				 this.addPlsqlDml(documentWrapper);
				  
				 
				 
				 this.addPlsqlJoins(documentWrapper );
				 
				 
				 if (jsonArray.getJSONObject(i).getString("type").equals("PACKAGE")) {
						
						this.addPlsqlProcFunc ( documentWrapper );
						
				 }
				 
				 if (jsonArray.getJSONObject(i).getString("type").equals("PROCEDURE") || jsonArray.getJSONObject(i).getString("type").equals("FUNCTION")) {
						
						this.addPlsqlArguments (documentWrapper);
						
				 }
				 
				 // this.addViewJoins(documentWrapper);
				 
			    
	    	 } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
   	
   }
    
    
   private void addPlsqlDml (DocumentWrapper documentWrapper) {

   	PlsqlInformationMod plsqlInformMod = new  PlsqlInformationMod();
	
    
   	String jsonStr = plsqlInformMod.getDml(Session.owner, Session.plsqlName);
   	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			documentWrapper.addSubSubHeader("DML");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Owner", 100));
		        columns.add(new Column("Table/Name", 200));
		        columns.add(new Column("DML Type", 100));
		  
		        
		        
		    String [][] content = new String [jsonArray.length()][3]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("owner");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("type");

		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
	   
   }
   
   public void addPlsqlJoins(DocumentWrapper documentWrapper ) {
   	
   	
   	PlsqlInformationMod plsqlInformMod = new PlsqlInformationMod();
   	
   	 
   	String jsonStr = plsqlInformMod.loadJoins();
   	try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() == 0) return;
			documentWrapper.addSubSubHeader("Joins");
		  	
			 List<Column> columns = new ArrayList<Column>();
			    columns.add(new Column("Left Table", 140));
		        columns.add(new Column("Left Column", 140));
		        columns.add(new Column("Right Table", 140));
		        columns.add(new Column("Right Column", 140));
		        
		        
		    String [][] content = new String [jsonArray.length()][4]; 
	 
	        
		    for (int i = 0; i < jsonArray.length(); i ++) {
		    	
		    	content [i][0] = jsonArray.getJSONObject(i).getString("left_table_name");
		    	content [i][1] = jsonArray.getJSONObject(i).getString("left_column_name");
		    	content [i][2] = jsonArray.getJSONObject(i).getString("right_table_name");
		    	content [i][3] = jsonArray.getJSONObject(i).getString("right_column_name");
		    	
		    }

		    
	        
	        Table table = (new StandartTable(columns, content)).getTable();


		     try {
				new PDFTableGenerator().generatePDF(documentWrapper, table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
   	
   	        
   	
   }       

   private void addPlsqlProcFunc (DocumentWrapper documentWrapper) {

	   	PlsqlInformationMod plsqlInformMod = new  PlsqlInformationMod();
		
	    
	   	String jsonStr = plsqlInformMod.loadProcFunc ( Session.owner, Session.plsqlName) ;
	   	try {
				JSONArray jsonArray = new JSONArray(jsonStr);
				
				if (jsonArray.length() == 0) return;
				
				documentWrapper.addSubSubHeader("Procedures/Functions");
			  	
				 List<Column> columns = new ArrayList<Column>();
				    columns.add(new Column("Id", 30));
			        columns.add(new Column("Owner", 200));
			        columns.add(new Column("Name", 140));
			        columns.add(new Column("Type", 100));
			  
			        
			        
			    String [][] content = new String [jsonArray.length()][4]; 
		 
		        
			    for (int i = 0; i < jsonArray.length(); i ++) {
			    	
			    	content [i][0] = jsonArray.getJSONObject(i).getString("plsql_id");
			    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
			    	content [i][2] = jsonArray.getJSONObject(i).getString("name");
			    	content [i][3] = jsonArray.getJSONObject(i).getString("type");
			    	
			    }

			    
		        
		        Table table = (new StandartTable(columns, content)).getTable();


			     try {
					new PDFTableGenerator().generatePDF(documentWrapper, table);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   
	   }   
   
   private void addPlsqlArguments (DocumentWrapper documentWrapper) {

	   	PlsqlInformationMod plsqlInformMod = new  PlsqlInformationMod();
		
	    
	   	String jsonStr = plsqlInformMod.getArguments ( Session.owner, Session.plsqlName) ;
	   	
	   	
	   	
	   	try {
				JSONArray jsonArray = new JSONArray(jsonStr);
				if (jsonArray.length() == 0) return;
				
				documentWrapper.addSubSubHeader("Arguments");
			  	
				 List<Column> columns = new ArrayList<Column>();
				    columns.add(new Column("Id", 30));
			        columns.add(new Column("Owner", 100));
			        columns.add(new Column("Argument Name", 140));
			        columns.add(new Column("Data Type", 100));
			        columns.add(new Column("Position", 100));
			        columns.add(new Column("In/Out", 40));
			  
			        
			        
			    String [][] content = new String [jsonArray.length()][6]; 
		 
		        
			    for (int i = 0; i < jsonArray.length(); i ++) {
			    	
			    	content [i][0] = jsonArray.getJSONObject(i).getString("id");
			    	content [i][1] = jsonArray.getJSONObject(i).getString("owner");
			    	content [i][2] = jsonArray.getJSONObject(i).getString("argument_name");
			    	content [i][3] = jsonArray.getJSONObject(i).getString("data_type");
			    	content [i][4] = jsonArray.getJSONObject(i).getString("position");
			    	content [i][5] = jsonArray.getJSONObject(i).getString("in_out");
			    	
			    }

			    
		        
		        Table table = (new StandartTable(columns, content)).getTable();


			     try {
					new PDFTableGenerator().generatePDF(documentWrapper, table);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   
	   }     
    
 
}
