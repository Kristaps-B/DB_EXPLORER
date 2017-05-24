package Controllers;

import java.net.URL;

import Global.Session;
import Main.Main;
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
		
		this.allUsersMod.queryUsers();
		
	}
	
	
	
	public String loadUsers() {
		
		
		String res = "";
		System.out.println("AllUsersContr.loadUsers");
		
		try {
				res = this.allUsersMod.loadUsers();
				
				
		} catch (Exception e) {
			System.out.println("EXCEPTION " + e.getMessage());
		}
		
		
		
		
		return res;
	}
	
	public void toMenu () {
		
		URL url = Main.class.getResource("../WEB/html/main_menu.html");
		engine.load(url.toExternalForm());
	}
	
	
	
	public void updateActive (int id, boolean chk) {
		
		String active = "";
		
		
		System.out.println("AllUsersContr.updateActive id: " + id + " chk: " + chk);
		
		if (chk == true) {
			active = "Y";
		} else {
			active = "N";
		}
		
		
		
		try {
			this.allUsersMod.updateActive(id, active);
		} catch (Exception e) {
			System.out.println("EXCEPTION " + e.getMessage());
		}
		
		
		
		
	}
	
	
}
