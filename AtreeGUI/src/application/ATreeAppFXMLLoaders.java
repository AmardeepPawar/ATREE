package application;

import javafx.fxml.FXMLLoader;

public class ATreeAppFXMLLoaders {
	static FXMLLoader AtreeMainGUILoader = new FXMLLoader();
	static FXMLLoader selectWorkSpaceGUILoader = new FXMLLoader();
	static FXMLLoader addCaseGUILoader = new FXMLLoader();
	public static FXMLLoader getAddCaseGUILoader() {
		return addCaseGUILoader;
	}

	public static void setAddCaseGUILoader(FXMLLoader addCaseGUILoader) {
		ATreeAppFXMLLoaders.addCaseGUILoader = addCaseGUILoader;
	}

	public static FXMLLoader getAtreeMainGUILoader() {
		return AtreeMainGUILoader;
	}

	public static void setAtreeMainGUILoader(FXMLLoader atreeMainGUILoader) {
		AtreeMainGUILoader = atreeMainGUILoader;
	}

	public static FXMLLoader getSelectWorkSpaceGUILoader() {
		return selectWorkSpaceGUILoader;
	}

	public static void setSelectWorkSpaceGUILoader(FXMLLoader selectWorkSpaceGUILoader) {
		ATreeAppFXMLLoaders.selectWorkSpaceGUILoader = selectWorkSpaceGUILoader;
	}
}
