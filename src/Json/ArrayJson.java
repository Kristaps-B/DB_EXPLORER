package Json;

public class ArrayJson {
	
	private String json = "";
	
	public ArrayJson () {
		
		
		
	}
	
	
	public void startJson () {
		
		json = "[";
		
	}
	
	
	public void addValue (String key, String value) {
		
		if (json.length() == 1) {
			json = json + "{";
		}
		
		
		json += "\"" + key + "\":\"" + value + "\",";
		
		
	}
	
	public void newRow () {
		
		
		if (json.length() > 1) {
			json = json.substring(0, json.length() - 1);
		}
		
		json += "},{";
	}
	
	public void endJson () {
		if (json.length() == 1) {
			json = json + "]";
		} else {
			
			json = json.substring(0, json.length() - 2);
			
			json += "]";
		}
		
	}
	
	
	public String getJson () {
		return this.json;
	}
	
	
}
