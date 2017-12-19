package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class IsNumericTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.isNumeric("32313.123");
		
		assertEquals(result, true);
		
		result = parserUtils.isNumeric("32313.123A");
		
		assertEquals(result, false);
	}

}
