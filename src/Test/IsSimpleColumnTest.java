package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class IsSimpleColumnTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		boolean result = parserUtils.isSimpleColumn("A.COL1 || A.COL2");
		
		assertEquals(result, false);
		
		
		result = parserUtils.isSimpleColumn("A.COL2");
		
		assertEquals(result, true);
		
 
	}

}
