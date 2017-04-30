package Database;

import java.util.HashMap;

public class Table {
	private String tableName;
	
	private HashMap <Integer, String> columns = new HashMap <> ();
	
	
	public Table (String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void addColumn(int key, String value) {
		columns.put(key, value);
	}
	
	public HashMap <Integer, String> getColumns() {
		return this.columns;
	}
	
	
}
