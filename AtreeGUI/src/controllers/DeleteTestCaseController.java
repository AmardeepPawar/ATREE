package controllers;

import java.io.File;
import java.nio.file.NoSuchFileException;

import application.ATreeWorkSpace;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteTestCaseController implements AtreeSingleStageGUIController {
	@FXML
	Button okbutton = new Button();
	@FXML
	Button cancelbutton = new Button();
	@FXML
	Label removeTCLabel = new Label();
	public void initialize() throws NoSuchFileException {
/*		String fileName = GUIController.getSelectedItem();
		String displayString = "are you sure, you want to delete testcase \"" + fileName + "\"?";		
		removeTCLabel.setText(displayString);*/
		okbutton.setOnAction(e->{
			String WorkSpace = ATreeWorkSpace.getAtreeWorkSpace();
			//String fileName = GUIController.getSelectedTreeItem();
			String ConFolderFile =  WorkSpace + "\\" + GUIController.getSelectedItem();
			File deleteFile = new File(ConFolderFile);
			deleteFile.delete();
			Stage stage = (Stage) cancelbutton.getScene().getWindow();
     		stage.close();
     		GUIController.deleteTestCase();
			if(GUIController.getSelectedItem() == GUIController.activeTreeItem.getValue())
			{
				GUIController.setIconOnDisplay();
			}
		});
		cancelbutton.setOnAction(e->{
			   Stage stage = (Stage) cancelbutton.getScene().getWindow();
     		   stage.close();
			   });
	 }
	@Override
	public void clearElementsTextValues() {
		// TODO Auto-generated method stub
		removeTCLabel.setText("");
		
	}
	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub
		String fileName = GUIController.getSelectedItem();
		String displayString = "are you sure, you want to delete testcase \"" + fileName + "\"?";		
		removeTCLabel.setText(displayString);	

	}
}
