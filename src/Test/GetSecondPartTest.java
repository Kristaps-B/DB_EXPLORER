package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetSecondPartTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.getSecondPart("SPLIT BY THIS", "BY");
		
		assertEquals(result, " THIS");
	}

}
