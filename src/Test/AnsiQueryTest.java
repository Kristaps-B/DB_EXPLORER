package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class AnsiQueryTest {

	@Test
	public void test() {
		
		MainSelectQuery selectQuery = new MainSelectQuery("SELECT c.region_name, r.country_name FROM hr.countries c JOIN hr.regions r ON (c.region_id = r.region_id)");
		assertEquals(selectQuery.getTableList().get(0).getTable(), "COUNTRIES");
		assertEquals(selectQuery.getTableList().get(1).getTable(), "REGIONS");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "REGION_NAME");
		assertEquals(selectQuery.getColumnList().get(1).getColumn(), "COUNTRY_NAME");		
		assertEquals(selectQuery.getWhereList().get(0).getLeftColumn(), "REGION_ID");
		assertEquals(selectQuery.getWhereList().get(0).getRightColumn(), "REGION_ID");	
		
	
		
	}

}
