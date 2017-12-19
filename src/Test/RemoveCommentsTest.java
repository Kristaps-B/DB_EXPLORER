package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class RemoveCommentsTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.removeComments("SELECT 1 /* Comment */ FROM dual -- Comment");
		
		assertEquals(result, "SELECT 1  FROM dual ");
	}

}
