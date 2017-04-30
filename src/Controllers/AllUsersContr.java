package Controllers;

import Global.Session;
import Models.AllUsersMod;
import javafx.scene.web.WebEngine;

public class AllUsersContr {
	
	private WebEngine engine;
	private AllUsersMod allUsersMod;
	
	public AllUsersContr (WebEngine engine) {
		
		
		this.engine = engine;
		this.allUsersMod = new AllUsersMod();
		
	}
	
	
	public void updateUsers () {
		
		System.out.println("AllUsersContr.updateUsers");
		
		this.allUsersMod.queryUsers(
			Session.ip,
			Session.port,
			Session.sid,
			Session.username,
			Session.password
				
			);
		
	}
	
	
	
	public void loadUsers() {
		
		System.out.println("AllUsersContr.loadUsers");
	}
	
	
}
