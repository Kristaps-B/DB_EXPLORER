package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class UnionQueryTest {

	@Test
	public void test() {
		MainSelectQuery selectQuery = new MainSelectQuery("SELECT c.country_name FROM hr.countries c UNION SELECT r.region_name FROM hr.regions r");
		
		assertEquals(selectQuery.getTableList().get(0).getTable(), "COUNTRIES");
		assertEquals(selectQuery.getTableList().get(1).getTable(), "REGIONS");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "COUNTRY_NAME");
		assertEquals(selectQuery.getColumnList().get(1).getColumn(), "REGION_NAME");
	}

}
