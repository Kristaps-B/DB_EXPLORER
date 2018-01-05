package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLParser.MainSelectQuery;

public class EmployeesQueryTest {

	@Test
	public void test() {
		MainSelectQuery selectQuery = new MainSelectQuery("select e.first_name, e.last_name FROM employees e");
		assertEquals(selectQuery.getTableList().get(0).getTable(), "EMPLOYEES");
		assertEquals(selectQuery.getColumnList().get(0).getColumn(), "FIRST_NAME");
		assertEquals(selectQuery.getColumnList().get(1).getColumn(), "LAST_NAME");
	}

}
