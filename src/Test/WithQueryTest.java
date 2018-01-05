package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class WithQueryTest {

	@Test
	public void test() {
		MainSelectQuery selectQuery = new MainSelectQuery("WITH t_countries IS (SELECT c.country_name FROM hr.countries c) SELECT c.country_name FROM t_countries c");
		
		assertEquals(selectQuery.getTableList().get(0).getTable(), "COUNTRIES");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "COUNTRY_NAME");

	}

}
