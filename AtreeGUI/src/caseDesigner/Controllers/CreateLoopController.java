package caseDesigner.Controllers;

import java.util.Set;

import application.AtreeFolder;
import application.PopUpWindow;
import caseDesigner.CreateLoop;
import caseDesigner.StepHierarchyMapping;
import caseDesigner.TestCaseFileReaderWriter;
import caseDesigner.beans.CreateLoopBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import controllers.AtreeSingleStageGUIController;

public class CreateLoopController implements AtreeSingleStageGUIController {
	@FXML
	Button okbutton = new Button();
	@FXML
	Button cancelbutton = new Button();
	@FXML
	TextArea conditionTxt = new TextArea();
	@FXML
	Label headerlbl = new Label();
	@FXML
	ImageView headerImg;
	@FXML
	ComboBox<String> formStep = new ComboBox<String>();
	@FXML
	ComboBox<String> toStep = new ComboBox<String>();
	@FXML
	Label errorMsg = new Label();
	
	public static Stage stage;
	PopUpWindow popUpWinObj;
	CreateLoop crtLoop;
	boolean dirConnect;
	static Set<String> setOfStepNames;
	static String[] stepSourceNameArray;
	String swipeVar;
	
	public void initialize() {
		//popUpWinObj = PopUpWindow.getInstance();
		headerlbl.setText(CreateLoopBean.getHeaderStr());
		headerImg.setImage(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + CreateLoopBean.getImgPath()));
		
		formStep.setOnAction(e -> {
			String selectedSource = (String) formStep.getValue();
			toStep.getItems().clear();
			toStep.getItems().addAll(stepSourceNameArray);
			toStep.getItems().remove(selectedSource);
		});
		
		
		okbutton.setOnAction(e -> {
			
			try {
				TestCaseFileReaderWriter conectObj = new TestCaseFileReaderWriter();
				String sourceStepName = formStep.getValue().toString();
				String sourceStepNameId = (String) StepHierarchyMapping.getStepNameAndItsId()
						.get(sourceStepName);
				String destinationStepName = toStep.getValue().toString();
				String destinationStepNameId = (String) StepHierarchyMapping.getStepNameAndItsId()
						.get(destinationStepName);
				


				if (StepHierarchyMapping.getStepsConnectPath().get("L-" + destinationStepNameId + "_" + sourceStepNameId) == null) {
					conectObj.addStepLoopInFile(destinationStepNameId, sourceStepNameId);						
					conectObj.displayHierarchy();
					conectObj.writeConditionToLoop(destinationStepNameId, sourceStepNameId, conditionTxt.getText());
					conectObj.displayHierarchy();
					stage = (Stage) cancelbutton.getScene().getWindow();
					stage.close();
			}else {
				errorMsg.setText("This loop is already there");
			}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		cancelbutton.setOnAction(e -> {
			stage = (Stage) cancelbutton.getScene().getWindow();
			stage.close();
		});
	}


	@Override
	public void clearElementsTextValues() {
		formStep.getItems().clear();
		toStep.getItems().clear();
		conditionTxt.setText("");
		errorMsg.setText("");
	}
	
	

	public void setFormStep() {
		setOfStepNames = StepHierarchyMapping.getStepNameAndItsId().keySet();
		stepSourceNameArray = setOfStepNames.toArray(new String[setOfStepNames.size()]);
    	formStep.getItems().clear();
		formStep.getItems().addAll(stepSourceNameArray);
	}

	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub

	}
}
