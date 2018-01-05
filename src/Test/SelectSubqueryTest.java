package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class SelectSubqueryTest {

	@Test
	public void test() {
		
		MainSelectQuery selectQuery = new MainSelectQuery("SELECT c.country_name, (SELECT r.region_name FROM hr.regions r WHERE c.region_id = r.region_id) region_name FROM hr.countries c");
		
		assertEquals(selectQuery.getTableList().get(0).getTable(), "COUNTRIES");
		assertEquals(selectQuery.getTableList().get(1).getTable(), "REGIONS");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "COUNTRY_NAME");
		assertEquals(selectQuery.getColumnList().get(1).getColumn(), "REGION_NAME");	
		assertEquals(selectQuery.getWhereList().get(0).getLeftColumn(), "REGION_ID");
		assertEquals(selectQuery.getWhereList().get(0).getRightColumn(), "REGION_ID");	
		
	
	}

}
