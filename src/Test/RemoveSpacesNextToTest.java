package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.ParserUtils;

public class RemoveSpacesNextToTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		String result = parserUtils.removeSpacesNextTo("SELECT 1 FROM dual", "FROM");
		
		assertEquals(result, "SELECT 1FROMdual");
	}

}
