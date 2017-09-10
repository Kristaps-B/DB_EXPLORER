package SQLParser;

import java.util.ArrayList;

public class ParserUtils {
	public ParserUtils() {
		
		
	}
	
	
	ArrayList <String> parseString (String text, String parseBy) {
		ArrayList <String> result = new ArrayList <> ();
		
		int numberOfBrackets = 0;
		String pieceOfText = "";
		
		for (int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			
			if ( this.atLocation(i, "(", text) == true ) {
				
				numberOfBrackets -= 1;
				
			}
			
			if ( this.atLocation(i, ")", text) == true ) {
				
				numberOfBrackets += 1;
				
			}
			
			
			if (
					numberOfBrackets == 0 &&
					this.atLocation(i, parseBy, text) == true
					
				) {
				
				
				if (result.size() > 0) {
					
					pieceOfText = pieceOfText.substring(parseBy.length());
					
				}
				
				
				result.add(pieceOfText);
				
				pieceOfText = "";
			}
			
			pieceOfText += c;
			
			
			
		}
		
		
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
	
		if ((index) == fullString.length() - 1) {
			System.out.println("atLocation 1");			
			return false;
		}	
		else if ((index + search.length() +1) >= fullString.length() - 1) {
			System.out.println("atLocation 2");
			return false;
		} 
		 
		 
		String sbstr = fullString.substring(index, index + search.length());
		System.out.println("Sbstr: " + sbstr);
		
		
		if (sbstr.equals(search)) {
			return true;
		}
		
		
		return result;
	}
	
}
