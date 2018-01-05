package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class WhereSubqueryTest {

	@Test
	public void test() {
		MainSelectQuery selectQuery = new MainSelectQuery("SELECT r.region_name FROM hr.regions r WHERE r.region_id = (SELECT c.region_id FROM hr.countries c WHERE c.country_name = 'FRANCE')");
		
		assertEquals(selectQuery.getTableList().get(0).getTable(), "REGIONS");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "REGION_NAME");
		
		
	}

}
