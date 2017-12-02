package SQLParser;

import java.util.ArrayList;

public class PlsqlParser {
	
	private String code = "";
	
	
	private ParserUtils parserUtils = new ParserUtils();
	
	
	private ArrayList < OwnerTableDml > insertList = new ArrayList <> (); 
	private ArrayList < OwnerTableDml > updateList = new ArrayList <> (); 
	private ArrayList < OwnerTableDml > deletetList = new ArrayList <> (); 	
	
	
	private ArrayList < MainSelectQuery > queryList = new ArrayList <> ();
	
	
	public PlsqlParser (String code) {
		
		
		this.code = code;
		
		this.modifyCode();
		
		
		
		this.findAllInserts();
		this.findAllUpdates();
		this.findAllDeletes();
		
		
		this.parseAllCode();
		
		
	}
	
	
	private void modifyCode() {
		
		this.code = parserUtils.removeComments(this.code);
		
		this.code = this.code.replace("\n", " ").replace("\r", " ");
		this.code = " " + this.code.trim().replaceAll(" +", " ") + " ";
		
		
		this.code = this.code.toUpperCase();
		
		
		
		System.out.println("SQL after Modification: " + this.code);		
		
		
	}
	
	
	private void findAllInserts () {
		System.out.println("Find All Inserts");
		
		String searchStr = " INSERT INTO ";
		
		if ( parserUtils.isTextInside(this.code, searchStr) == false ) {
			
			System.out.println("No INSERT found!");
			
			return;
		}
		
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.code, searchStr);
		
		for (String str: tableOwnerList) {
			
			
			String owner = parserUtils.getOwner(str);
			String table = parserUtils.getTable(str);
			String type = "INSERT";			
			
			
			insertList.add(new OwnerTableDml ( owner, table, type  ) );
		}
		
		
	}
	
	
	private void findAllUpdates () {
		System.out.println("Find All Updates");

		String searchStr = " UPDATE ";		

		if ( parserUtils.isTextInside(this.code, searchStr) == false ) {
			
			System.out.println("No UPDATE found!");
			
			return;
		}	
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.code, searchStr);
		
		for (String str: tableOwnerList) {
			
			
			String owner = parserUtils.getOwner(str);
			String table = parserUtils.getTable(str);
			String type = "UPDATE";
			
			
			updateList.add(new OwnerTableDml ( owner, table, type  ) );
		}		
		
		
	}
	
	
	private void findAllDeletes () {
		System.out.println("Find All Deletes");
		
		String searchStr = " DELETE FROM ";
		
		if ( parserUtils.isTextInside(this.code, searchStr) == false ) {
			
			System.out.println("No DELETE found!");
			
			return;
		}			
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.code, searchStr);
		
		for (String str: tableOwnerList) {
			
			String owner = parserUtils.getOwner(str);
			String table = parserUtils.getTable(str);
			String type = "DELETE";			
			
			updateList.add(new OwnerTableDml ( owner, table, type  ) );
		}		
		
		
	}
	
	
	public ArrayList <OwnerTableDml> getDmls () {
		ArrayList <OwnerTableDml> resultList = new ArrayList <> ();
		
		
		resultList.addAll(this.insertList);
		resultList.addAll(this.updateList);
		resultList.addAll(this.deletetList);
		
		return resultList;
	}
	

	
	public class OwnerTableDml {
		
		private String owner;
		private String table;
		private String type;
		
		
		public OwnerTableDml (String owner, String table, String type) {
			this.owner = owner;
			this.table = table;
			this.type = type;
		}
		
		
		public String getTable () {
			return this.table;
		}
		
		public String getOwner () {
			return this.owner;
		}
		
		
		public String getType () {
			return this.type;
		}
		
	}
	
	
	
	private void parseAllCode () {
		
		
		
		String tempCode = code.replaceAll(" AS ", " IS ").replaceAll(" IS ", " IS; ").replaceAll(" BEGIN ", " BEGIN; ");
		
		ArrayList <String> lineList = parserUtils.parseString(tempCode, ";");
		
		for (String line: lineList) {
			
			//  System.out.println("LINE: " + line);
			
			
			// Check Line if contains query
			if ( this.parserUtils.checkIfTextExists(line, " SELECT ") == true && this.parserUtils.checkIfTextExists(line, " FROM ") == true ) {
				
				parseSelectLine(line);
				
			}
			
			
		}
		
		
		
	}
	
	
	private void parseSelectLine (String line) {
		
		String sql = "";
		
		// Check if CURSOR
		if ( this.parserUtils.checkIfTextExists(line, " CURSOR ") == true ) {
			
			
			sql = this.parserUtils.getSecondPart(line, " IS ");
			
		}
		
		
		
		// Check if inline cursor
		else if ( this.parserUtils.checkIfTextExists(line, " INTO ") == true) {
			
			 
			sql = this.parserUtils.getFirstPart(line, " INTO ") + " FROM " + this.parserUtils.getSecondPart(line, " FROM ");
			
		}
		
		
		
		System.out.println("SQL: " + sql);
		
		
		MainSelectQuery selectQuery = new MainSelectQuery(sql);
		
		this.queryList.add(selectQuery);
		
		
	}
	
	
	
	public ArrayList < WhereExpression > getExpressionList () {
		
		ArrayList < WhereExpression > whereExpressionList = new ArrayList <> ();
		
		
		for (MainSelectQuery msq : this.queryList) {
			
			whereExpressionList.addAll( msq.getWhereList() );
			
		}
		
		
		return whereExpressionList;
		
	}
	
	
}



