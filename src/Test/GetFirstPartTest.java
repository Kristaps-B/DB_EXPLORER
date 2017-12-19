package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetFirstPartTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.getFirstPart("SPLIT BY THIS", "BY");
		
		assertEquals(result, "SPLIT ");
		
	}

}
