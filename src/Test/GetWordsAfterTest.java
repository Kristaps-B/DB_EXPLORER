package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import SQLParser.ParserUtils;

public class GetWordsAfterTest {

	@Test
	public void test() {
		ParserUtils parserUtils = new ParserUtils();
		
		ArrayList <String> result = parserUtils.getWordsAfter("aaaaaa_bbbb_cccc", "_");
	 
		
		assertEquals(result.get(0), "bbbb");
 
	}

}
