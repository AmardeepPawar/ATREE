package caseDesigner.beans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import popUpWindowBeans.*;

public class StepLoopBean implements PopUpWindowBeans {
	static double width = 400;
	static double height = 180;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/caseDesigner/fxmlFiles/stepLoop.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static StepLoopBean instanceOfClass = new StepLoopBean();
	static String headerStr = "Loop Details";
	static String imgPath = "//icons//loopIcon.png";

	public static String getHeaderStr() {
		return headerStr;
	}

	public static void setHeaderStr(String headerStr) {
		StepLoopBean.headerStr = headerStr;
	}

	public String getFxmlFileName() {
		return fxmlFileName;
	}

	public static String getImgPath() {
		return imgPath;
	}

	public static void setImgPath(String imgPath) {
		StepLoopBean.imgPath = imgPath;
	}

	public void setFxmlFileName(String fxmlFileName) {
		StepLoopBean.fxmlFileName = fxmlFileName;
	}

	private StepLoopBean() {

	}

	public static StepLoopBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new StepLoopBean();
		}
		return instanceOfClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		StepLoopBean.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		StepLoopBean.height = height;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		StepLoopBean.loader = loader;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		StepLoopBean.scene = scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		StepLoopBean.allFieldsMap=allFieldsMap;
	}


}
