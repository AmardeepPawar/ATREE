package controllers;

import java.io.File;
import java.util.Map;

import application.ATreeWorkSpace;
import application.KeyValueMapping;
import application.PopUpWindow;
import application.beans.ModulesGroupPropertyBean;
import caseDesigner.CurrentStatusIndicatorVariables;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import popUpWindowBeans.PopUpWindowBeans;
import popUpWindowBeans.SelectModuleTypeBean;
import popUpWindowBeans.SelectWorkSpaceBean;

public class SelectWorkSpaceController implements AtreeSingleStageGUIController{
	@FXML
	Button okbutton = new Button();
	@FXML
	Button cancelButton = new Button();
	@FXML
	Button DirectoryChooser = new Button();
	@FXML
	TextField workSpaceName = new TextField();
	@FXML
	Label selectStepMsgBx = new Label();
	@FXML
	HBox labelHBox = new HBox();
	@FXML
	Label noteId = new Label();
	static boolean flagSW = true;
	Stage stage;
	static int numberOfRows;
	PopUpWindow selModType;

	public void initialize() {
		selModType = PopUpWindow.getInstance();
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String,ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		selectStepMsgBx.setMaxWidth(300);
		selectStepMsgBx.setMaxHeight(200);
		numberOfRows = modGrpMap.size()/2 + modGrpMap.size()%2;

		DirectoryChooser.setOnAction(e -> {
			javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
			stage = (Stage) cancelButton.getScene().getWindow();
			File selectedDirectory = directoryChooser.showDialog(stage);
			if (selectedDirectory != null) {
				workSpaceName.setText(selectedDirectory.getAbsolutePath());
			}
		});
		/*PopUpWindow.popUPStage.setOnCloseRequest(e -> closeMainWindow());*/
		okbutton.setOnAction(e -> {			
			String workSpace = workSpaceName.getText().trim();
			GUIController.setSelectedItem(null);
			GUIController.setIconOnDisplay();
			File foldername = new File(workSpace);			
			if (foldername.exists() && foldername.isDirectory()) {
				ATreeWorkSpace.setAtreeWorkSpace(workSpace);
				GUIController.activeItem = null;
				GUIController.displayTreeContent();
				stage = (Stage) cancelButton.getScene().getWindow();
				stage.close();
				flagSW = false;
				try {
					double sceneHeight = 125;					
					if (numberOfRows > 2) {
						sceneHeight = 125 + (22 * (numberOfRows - 1));
					}		
					PopUpWindowBeans selectModuleTypeBean = SelectModuleTypeBean.getInstaceOfClass();
					selectModuleTypeBean.setHeight(sceneHeight);
					selModType.prepareStage(selectModuleTypeBean);	
					//Once new workSpace selected reset/clear all case and step parameters which was active before ws change.
					CurrentStatusIndicatorVariables.resetStepParameters();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				selectStepMsgBx.setMaxWidth(350);
				labelHBox.setPrefHeight(55);
				if((SelectWorkSpaceBean.getInstaceOfClass().getHeight() + 50) == PopUpWindow.getPopUPStage().getHeight()){
					selModType.setStageHeight(PopUpWindow.getPopUPStage().getHeight() + 30);
				}
				noteId.setText("Note: ");
				selectStepMsgBx.setText("\"" + workSpace + "\" workspace does not exist.");
				selectStepMsgBx.setWrapText(true);
				selectStepMsgBx.setTextAlignment(TextAlignment.JUSTIFY);
			}
		});
		cancelButton.setOnAction(e -> {
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		});
	}
	
/*	public void closeMainWindow(){
		if(flagSW)
		{
			Main.closeMainWindow();
		}
	}*/

	@Override
	public void clearElementsTextValues() {
		// TODO Auto-generated method stub
		selectStepMsgBx.setText("");
		noteId.setText("");
		labelHBox.setPrefHeight(5);		
	}

	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub
		
	}

}
