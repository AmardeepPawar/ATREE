package caseDesigner;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModuleSetupStage {
	public Stage setupStage = new Stage();
	static ModuleSetupStage inst = new ModuleSetupStage();
	
	private ModuleSetupStage()
	{	
		setupStage.initModality(Modality.APPLICATION_MODAL);
	}

	public static ModuleSetupStage getClassInstance()
	{
		if(inst == null)
		{
			inst = new ModuleSetupStage();
		}
		return inst;
	}

	
	public Stage getSetupStage() {
		return setupStage;
	}
}
