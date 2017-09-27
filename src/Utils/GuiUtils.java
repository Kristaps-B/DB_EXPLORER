package Utils;






import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class GuiUtils {
	public GuiUtils () {
		
		
	}
	
	
	public void alert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	
	
	public int confirmation (String message) {
		int res = 0;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		 
	 
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    res = 1;
		} else {
		    res = 0;
		}
		
		return res;
		
	}
	
	
}
