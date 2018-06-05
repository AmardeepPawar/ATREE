package caseDesigner.beans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import popUpWindowBeans.*;

public class StepConnectorBean implements PopUpWindowBeans {
	static double width = 400;
	static double height = 170;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/caseDesigner/fxmlFiles/stepConnector.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static StepConnectorBean instanceOfClass = new StepConnectorBean();
	static String headerStr = "Connection Details";
	static String imgPath = "//icons//addflowicon.png";

	public static String getHeaderStr() {
		return headerStr;
	}

	public static void setHeaderStr(String headerStr) {
		StepConnectorBean.headerStr = headerStr;
	}

	public String getFxmlFileName() {
		return fxmlFileName;
	}

	public static String getImgPath() {
		return imgPath;
	}

	public static void setImgPath(String imgPath) {
		StepConnectorBean.imgPath = imgPath;
	}

	public void setFxmlFileName(String fxmlFileName) {
		StepConnectorBean.fxmlFileName = fxmlFileName;
	}

	private StepConnectorBean() {

	}

	public static StepConnectorBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new StepConnectorBean();
		}
		return instanceOfClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		StepConnectorBean.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		StepConnectorBean.height = height;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		StepConnectorBean.loader = loader;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		StepConnectorBean.scene = scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		StepConnectorBean.allFieldsMap=allFieldsMap;
	}


}
