package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	GetFirstPartTest.class, 
	GetSecondPartTest.class,
	IsTextInsideTest.class,
	AtLocationTest.class,
	GetBracketsContentTest.class,
	IsNumericTest.class,
	IsQueryTest.class,
	IsSimpleColumnTest.class,
	RemoveSpacesNextToTest.class,
	RemoveCommentsTest.class,
	GetWordsAfterTest.class,
	GetOwnerTest.class,
	GetTableTest.class,
	CheckIfTextExistsTest.class,
	
	SimpleQueryTest.class,
	EmployeesQueryTest.class,
	WhereQueryTest.class,
	AnsiQueryTest.class,
	SelectSubqueryTest.class,
	FromSubqueryTest.class,
	WhereSubqueryTest.class,
	SubquerySubqueryTest.class,
	UnionQueryTest.class,
	WithQueryTest.class
	})
public class RunAllTests {

}
