package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class AtLocationTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.atLocation(9, "FROM", "SELECT 1 FROM dual");
		
		assertEquals(result, true);
	}

}
