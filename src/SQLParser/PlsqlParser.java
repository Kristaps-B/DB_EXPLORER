package SQLParser;

import java.util.ArrayList;

public class PlsqlParser {
	
	private String sql = "";
	
	
	private ParserUtils parserUtils = new ParserUtils();
	
	
	private ArrayList < OwnerTableDml > insertList = new ArrayList <> (); 
	private ArrayList < OwnerTableDml > updateList = new ArrayList <> (); 
	private ArrayList < OwnerTableDml > deletetList = new ArrayList <> (); 	
	
	
	public PlsqlParser (String code) {
		
		
		this.sql = code;
		
		modifyCode();
		
		
		
		findAllInserts();
		findAllUpdates();
		findAllDeletes();
		
		
	}
	
	
	private void modifyCode() {
		
		this.sql = parserUtils.removeComments(this.sql);
		
		this.sql = this.sql.replace("\n", " ").replace("\r", " ");
		this.sql = " " + this.sql.trim().replaceAll(" +", " ") + " ";
		
		
		this.sql = this.sql.toUpperCase();
		
		
		
		System.out.println("SQL after Modification: " + this.sql);		
		
		
	}
	
	
	private void findAllInserts () {
		System.out.println("Find All Inserts");
		
		String searchStr = " INSERT INTO ";
		
		if ( parserUtils.isTextInside(this.sql, searchStr) == false ) {
			
			System.out.println("No INSERT found!");
			
			return;
		}
		
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.sql, searchStr);
		
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

		if ( parserUtils.isTextInside(this.sql, searchStr) == false ) {
			
			System.out.println("No UPDATE found!");
			
			return;
		}	
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.sql, searchStr);
		
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
		
		if ( parserUtils.isTextInside(this.sql, searchStr) == false ) {
			
			System.out.println("No DELETE found!");
			
			return;
		}			
		
		ArrayList <String> tableOwnerList = parserUtils.getWordsAfter(this.sql, searchStr);
		
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
	
	
}



