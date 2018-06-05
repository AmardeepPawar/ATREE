package controllers;

import java.util.ArrayList;
import java.util.Map;

import application.ATreeAppFXMLLoaders;
import application.KeyValueMapping;
import application.beans.ModulesGroupPropertyBean;
import application.CreateSelectedModIdList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SelectModuleTypeController implements AtreeSingleStageGUIController {
	@FXML
	Button ModSelOkBtn = new Button();
	@FXML
	GridPane ModulesListCont = new GridPane();
	@FXML
	Label errorMsgLabel = new Label();
	Stage stage;
	ArrayList<CheckBox> checkBoxArray = new ArrayList<CheckBox>();
	ArrayList<String> moduleTypeIds = new ArrayList<String>();
	boolean stageHeight = true;

	public void initialize() {
		moduleTypeIds.clear();
		checkBoxArray.clear();
		ModulesListCont.setVgap(5);
		errorMsgLabel.setStyle("-fx-text-fill:red;");
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String,ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		
		int i = 0, k = 1;
		for (Map.Entry<String, ModulesGroupPropertyBean> pair : modGrpMap.entrySet()) {
			if (k % 2 == 0) {
				CheckBox cb2 = new CheckBox(modGrpMap.get(pair.getKey()).getModuleGrpName());
				cb2.setId(pair.getKey());
				checkBoxArray.add(cb2);
				ModulesListCont.add(cb2, 1, i, 1, 1);				
				ModulesListCont.setPrefHeight(ModulesListCont.getPrefHeight() + 25);
				i++;
			}
			else
			{
				CheckBox cb1 = new CheckBox(modGrpMap.get(pair.getKey()).getModuleGrpName());
				cb1.setId(pair.getKey());
				checkBoxArray.add(cb1);
				ModulesListCont.add(cb1, 0, i, 1, 1);				
			}
			k++;
		}
		
    	ModSelOkBtn.setOnAction(e -> {
			stage = (Stage) ModSelOkBtn.getScene().getWindow();
			for (CheckBox ck : checkBoxArray) {
				if (ck.isSelected()) {
					moduleTypeIds.add(ck.getId());
				}
			}
			if (moduleTypeIds.size() > 0) {
				CreateSelectedModIdList modFileObj = CreateSelectedModIdList.getInstance();
				modFileObj.createSelModIdList(moduleTypeIds);

				FXMLLoader mainLoader = ATreeAppFXMLLoaders.getAtreeMainGUILoader();
				GUIController mainGUIController = mainLoader.getController();
				mainGUIController.displayModuleIcon();
				stage.close();
			} else {
				if (stageHeight) {
					stage.setHeight(stage.getHeight() + 25);
					stageHeight = false;
				}
				errorMsgLabel.setText("At least one module type need to be selected");
			}
		});
	}

	@Override
	public void clearElementsTextValues() {
		// TODO Auto-generated method stub
		errorMsgLabel.setText("");
		moduleTypeIds.clear();
	}

	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub

	}
}
