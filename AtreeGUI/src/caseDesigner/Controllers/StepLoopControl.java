package caseDesigner.Controllers;

import application.AtreeFolder;
import application.PopUpWindow;
import caseDesigner.StepHierarchyMapping;
import caseDesigner.TestCaseFileReaderWriter;
import caseDesigner.beans.StepLoopBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import controllers.AtreeSingleStageGUIController;

public class StepLoopControl implements AtreeSingleStageGUIController {
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
	Label formStep = new Label();
	@FXML
	Label toStep = new Label();
	
	public static Stage stage;
	PopUpWindow popUpWinObj;

	public void initialize() {
		//popUpWinObj = PopUpWindow.getInstance();
		TestCaseFileReaderWriter writter = new TestCaseFileReaderWriter();
		headerlbl.setText(StepLoopBean.getHeaderStr());
		headerImg.setImage(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + StepLoopBean.getImgPath()));
		
		okbutton.setOnAction(e -> {
			try {
				System.out.println(StepHierarchyMapping.getStepNameAndItsId().get(toStep.getText()) + " " + StepHierarchyMapping.getStepNameAndItsId().get(formStep.getText()) + " " + conditionTxt.getText());

				writter.writeConditionToLoop(StepHierarchyMapping.getStepNameAndItsId().get(toStep.getText()), StepHierarchyMapping.getStepNameAndItsId().get(formStep.getText()), conditionTxt.getText());
				writter.displayHierarchy();
				stage = (Stage) cancelbutton.getScene().getWindow();
				stage.close();
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

	}

	public void setConditionTxt(String conditionTxt) {
		this.conditionTxt.setText(conditionTxt);
	}


	public void setFormStep(String formStep) {
		this.formStep.setText(formStep);
	}


	public void setToStep(String toStep) {
		this.toStep.setText(toStep);
	}


	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub

	}
}
