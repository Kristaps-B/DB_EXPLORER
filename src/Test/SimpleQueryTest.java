package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class SimpleQueryTest {

	@Test
	public void test() {
		
		MainSelectQuery selectQuery = new MainSelectQuery("select 1 from dual");
		assertEquals(selectQuery.getTableList().get(0).getTable(), "DUAL");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "1");
		
		
	}

}
