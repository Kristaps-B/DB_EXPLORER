package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetTableTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.getTable("HR.EMPLYEES");
		
		assertEquals(result, "EMPLYEES");
	}

}
