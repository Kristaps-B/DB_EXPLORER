package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class CheckIfTextExistsTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.checkIfTextExists ("Check test inside.", "inside") ;
		
		assertEquals(result, true);
	}

}
