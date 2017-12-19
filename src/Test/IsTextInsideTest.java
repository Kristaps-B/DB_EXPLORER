package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class IsTextInsideTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.isTextInside("EXAMPLE Text is inside", "is");
		
		assertEquals(result, true);
		
		result = parserUtils.isTextInside("EXAMPLE Text is inside", "not");
		
		assertEquals(result, false);
	}

}
