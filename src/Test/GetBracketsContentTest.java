package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetBracketsContentTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.getBracketsContent("  ( test )  ) ");
		
		assertEquals(result, " test )  ");
	}

}
