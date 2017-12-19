package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class IsQueryTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.isQuery("(SELECT 1 FROM DUAL)");
		
		assertEquals(result, true);
		
		result = parserUtils.isQuery("DELETE FROM dual");
		
		assertEquals(result, false);
	}

}
