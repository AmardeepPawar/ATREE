package application;

import javafx.scene.Scene;

public class AtreeAppGUIScenes {
	static Scene selectWSScene;
	static Scene addCaseScene;

	public static Scene getAddCaseScene() {
		return addCaseScene;
	}

	public static void setAddCaseScene(Scene addCaseScene) {
		AtreeAppGUIScenes.addCaseScene = addCaseScene;
	}

	public static Scene getSelectWSScene() {
		return selectWSScene;
	}

	public static void setSelectWSScene(Scene selectWSScene) {
		AtreeAppGUIScenes.selectWSScene = selectWSScene;
	}

}
