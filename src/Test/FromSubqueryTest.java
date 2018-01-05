package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class FromSubqueryTest {

	@Test
	public void test() {
		MainSelectQuery selectQuery = new MainSelectQuery("SELECT c.country_name FROM (SELECT c1.country_name FROM hr.countries c1) c");
		
		assertEquals(selectQuery.getTableList().get(0).getTable(), "COUNTRIES");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "COUNTRY_NAME");

	}

}
