package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class CheckIfTextExistsTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.checkIfTextExists ("SELECT first_name FROM hr.employees", "first_name") ;
		
		assertEquals(result, true);
	}

}


