package caseDesigner.beans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import popUpWindowBeans.*;

public class ConnectStepBean implements PopUpWindowBeans {
	static double width = 450;
	static double height = 200;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/caseDesigner/fxmlFiles/connectTwoSteps.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static ConnectStepBean instanceOfClass = new ConnectStepBean();
	static String headerStr = "Connect Steps";
	static String imgPath = "//icons//addflowicon.png";

	public static String getHeaderStr() {
		return headerStr;
	}

	public static void setHeaderStr(String headerStr) {
		ConnectStepBean.headerStr = headerStr;
	}

	public String getFxmlFileName() {
		return fxmlFileName;
	}

	public static String getImgPath() {
		return imgPath;
	}

	public static void setImgPath(String imgPath) {
		ConnectStepBean.imgPath = imgPath;
	}

	public void setFxmlFileName(String fxmlFileName) {
		ConnectStepBean.fxmlFileName = fxmlFileName;
	}

	private ConnectStepBean() {

	}

	public static ConnectStepBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new ConnectStepBean();
		}
		return instanceOfClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		ConnectStepBean.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		ConnectStepBean.height = height;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		ConnectStepBean.loader = loader;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		ConnectStepBean.scene = scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		ConnectStepBean.allFieldsMap=allFieldsMap;
	}


}
