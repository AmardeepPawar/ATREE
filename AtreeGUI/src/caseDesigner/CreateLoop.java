package caseDesigner;

import java.io.IOException;

import application.PopUpWindow;
import caseDesigner.Controllers.CreateLoopController;
import caseDesigner.beans.CreateLoopBean;
import javafx.fxml.FXMLLoader;
import popUpWindowBeans.PopUpWindowBeans;

public class CreateLoop {
	static CreateLoop classInst = new CreateLoop();

	private CreateLoop() {

	}

	public static CreateLoop getInstance() {
		if (classInst == null) {
			classInst = new CreateLoop();
		}
		return classInst;
	}
	
	public void loopBtwTwoStepMethod() {
		
		PopUpWindowBeans stepLoopBean = CreateLoopBean.getInstaceOfClass();
		PopUpWindow popUpObj = PopUpWindow.getInstance();
		try {
			popUpObj.prepareStage(stepLoopBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loader = stepLoopBean.getLoader();
		CreateLoopController controller = loader.getController();
		controller.setFormStep();
	}

	public void createLoopConnection(String gotoStep, String fromStep, String condStr) {
		ConnectTwoStepsClass contObj = new ConnectTwoStepsClass();
		contObj.nonImidiateStepConnector(fromStep,gotoStep,"LOOP",condStr);
	}
	
	

}
