package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetOwnerTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.getOwner("HR.EMPLYEES");
		
		assertEquals(result, "HR");
	}

}
