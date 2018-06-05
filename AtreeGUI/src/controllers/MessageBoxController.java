package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MessageBoxController implements AtreeSingleStageGUIController{
    static String alertMessage;
	@FXML
	Button okbutton = new Button();
	@FXML
	Label alertLabel = new Label();
	public void initialize() {
		
		alertLabel.setWrapText(true);
		okbutton.setOnAction(e->{
			   Stage stage = (Stage) okbutton.getScene().getWindow();
			   stage.close();
		});
	}
	@Override
	public void clearElementsTextValues() {
		// TODO Auto-generated method stub
		alertLabel.setText("");
	}
	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub
	}
	
	public void setMessageToWindow(String msg)
	{
		alertLabel.setText(msg);
	}
}
