package SQLParser;

import java.util.ArrayList;

import org.sqlite.util.StringUtils;

public class ParserUtils {
	public ParserUtils() {
		
		
	}
	
	
	ArrayList <String> parseString (String text, String parseBy) {
		ArrayList <String> result = new ArrayList <> ();
		
		int numberOfBrackets = 0;
		boolean isInsideString = false;
		String pieceOfText = "";
		
		for (int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			
			if ( this.atLocation(i, "(", text) == true ) {
				
				numberOfBrackets -= 1;
				
			}
			
			if ( this.atLocation(i, ")", text) == true ) {
				
				numberOfBrackets += 1;
				
			}
			
			
			if (this.atLocation(i, "'", text) == true ) {
				isInsideString = !isInsideString;
			}
			
			
			if (
					numberOfBrackets == 0 &&
					this.atLocation(i, parseBy, text) == true
 					&& isInsideString == false                          // Is inside String?
					
				) {
				
				
				if (result.size() > 0) {
					
					pieceOfText = pieceOfText.substring(parseBy.length());
					
				}
				
				
				result.add(pieceOfText);
				
				pieceOfText = "";
			}
			
			pieceOfText += c;
			
			
			
		}
		
		
		if (result.size() > 0) {
			
			pieceOfText = pieceOfText.substring(parseBy.length());
			
		}
		result.add(pieceOfText);
		
		
		
		
		return result;
	}
	
	
	
	public String getFirstPart(String text, String parseBy) {
		String result = "";
		
		
		ArrayList <String> parseResult = this.parseString(text, parseBy);
		
		
		if (parseResult.size() > 0) {
			result = parseResult.get(0);
		}
		
		
		return result;
		
		
	}
	
	public String getSecondPart(String text, String parseBy) {
		String result = "";
		
		
		ArrayList <String> parseResult = this.parseString(text, parseBy);
		
		
		if (parseResult.size() > 1) {
			result = parseResult.get(1);
		}
		
		
		return result;		
	}
	
	
	
	
	
	
	public boolean isTextInside(String text, String findText) {
		boolean result = false;
		
		
		
		for (int i = 0; i < text.length(); i++) {
			
			
			if (atLocation(i, findText, text) == true) {
				result = true;
			}
			
			
		}
		
		
		return result;
	}
	
	
	public boolean atLocation (int index, String search, String fullString) {
		
		boolean result = false;
		
		// This one needs to be fixed!!!!
		//System.out.println("--------------------------------------------");
		//System.out.println("index: " + index + " search: " + search + " fullString: " + fullString);
	
		if ((index) == fullString.length() - 1) {
			//System.out.println("atLocation 1");			
			return false;
		}	
		else if ( (index + search.length() +1) > fullString.length() ) {
			//System.out.println("atLocation 2");
			return false;
		} 
		 
		 
		String sbstr = fullString.substring(index, index + search.length());
		//System.out.println("Sbstr: " + sbstr);
		
		
		if (sbstr.equals(search)) {
			//System.out.println("RETURN TRUE");
			return true;
		}
		
		
		return result;
	}
	
	
	public String getBracketsContent (String str) {
		
		

		
		String result = "";
		
		
		
		
		
		int start = str.indexOf("(");
		
		int end = str.lastIndexOf(")");
		
		//System.out.println("Get Brackets content: start: " + start + " end: " + end);		
		
		if (start == -1 && end == -1) {
			result = str;
		} else {
			result = str.substring(start + 1, end);
		}
		
		
		return result;
		
	}
	
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	
	public boolean  isQuery (String sql) {
		boolean subquery = true;
		
		sql = sql.trim();
		
		
		if (!this.isTextInside(sql, "SELECT")) {
			subquery = false;
		}
		
		if (!this.isTextInside(sql, "FROM")) {
			subquery = false;
		}
		
		
		if (sql.charAt(0) != '(') {
			subquery = false;
		}
		
		if (sql.charAt(sql.length() - 1) != ')') {
			subquery = false;
		}		
		
		
		return subquery;
		
	}
	
	
	public boolean isSimpleColumn (String column) {
		boolean result = true;
		
		
		
		System.out.println("COLUMN: " + column);
		column = column.trim();
		
		
		if (isTextInside(column, "(") == true || isTextInside(column, ")") == true || isTextInside(column, "||") == true ) {
			result = false;
			
			System.out.println("False 1");
		}
		
		
		if ( countCharMatch(column, ".") > 1  || countCharMatch(column, " ") > 1) {
			result = false;
			System.out.println("False 2 " + countCharMatch(column, ".") + " " + countCharMatch(column, " "));
		}
		
		
		
		return result;
		
		
	}
	
	
	
	private int countCharMatch(String str, String c) {
		
		return (str.length() - str.replace(c, "").length()) / c.length();
		
	}
	
	
	public String removeSpacesNextTo(String str, String s) {
		String result = str;
		
		result = result.replaceAll(" " + s + " ", s);
		result = result.replaceAll(s + " ", s);
		result = result.replaceAll(" " + s, s);
		
		
		return result;
		
		
	}
	
	
	public String removeComments (String inSql) {
		String outSql = "";
		String commentType = "";
		
		System.out.println("Remove comments");
		
		boolean isComment = false;
		
		
		for (int  i = 0; i < inSql.length(); i ++) {
			
			char c = inSql.charAt(i);
			 
			  
			if ( isComment == false) {
				
				if (atLocation(i, "--", inSql) ) {
					
					isComment = true;
					commentType = "--";
					// System.out.println("Comment starts");
				} else if ( atLocation(i, "/*", inSql) ) {
					
					isComment = true;
					commentType = "/*";
				}
			}
				
			
			
			
			if (isComment == false) {
				outSql += c;
			} else if (isComment == true) {
				if ( i > 0 && atLocation(i-1, "*/", inSql) && commentType.equals("/*")) {
					
					isComment = false;
					
				}
				
				if ((c == '\n'  || c == '\r'  ) && commentType.equals("--") ) {
					System.out.println("Character: " + c);
					isComment = false;
				}
				
			}
			
			 
			
			
		}
		
		
		
		
		return outSql;
		
	}
	
	
	public ArrayList <String> getWordsAfter (String fullStr, String findStr) {
		ArrayList <String> result = new ArrayList <> ();
		
		String rightStr = fullStr;
		
		
		while (isTextInside(rightStr, findStr) == true) {
			
			rightStr = getSecondPart(rightStr, findStr);
			
			// System.out.println("RightStr: " + rightStr);
			
			result.add(getNextWord(rightStr));
			
			// System.out.println("INSERT: " + getNextWord(rightStr) );
			
			
		}
		
		
		
		
		
		
		return result;
		
		
	}
	
	
	public String getNextWord (String str) {
		String result = "";
		
		result = str.split("[ (]")[0];
		
		
		return result;
	}
	
	
	
	public String getOwner (String str) {
		String result = "";
		

		String owner = this.getFirstPart(str, ".");
		String table = this.getSecondPart(str, ".");
		
		if (table.equals("")) {
			table= owner;
			owner = "";
		}
		
		result = owner;
		
		
		return result;
		
	}
	
	public String getTable (String str) {
		
		String result = "";
		
		String owner = this.getFirstPart(str, ".");
		String table = this.getSecondPart(str, ".");
		
		if (table.equals("")) {
			table= owner;
			owner = "";
		}
		
		
		result = table;
		
		
		
		return result;
		
	}
	
	
	public boolean checkIfTextExists (String text, String findStr) {
		boolean result = true;
		
		 //System.out.println("CheckIfTextExists:" + this.getSecondPart(text, findStr).length() );
		
		if (this.getSecondPart(text, findStr).length() == 0) {
			
			result = false;
			
		}
		
		return result;
	}
	
	
}
