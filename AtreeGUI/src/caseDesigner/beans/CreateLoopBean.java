package caseDesigner.beans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import popUpWindowBeans.*;

public class CreateLoopBean implements PopUpWindowBeans {
	static double width = 450;
	static double height = 200;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/caseDesigner/fxmlFiles/loopCreaterPage.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static CreateLoopBean instanceOfClass = new CreateLoopBean();
	static String headerStr = "Create Loop";
	static String imgPath = "//icons//addflowicon.png";

	public static String getHeaderStr() {
		return headerStr;
	}

	public static void setHeaderStr(String headerStr) {
		CreateLoopBean.headerStr = headerStr;
	}

	public String getFxmlFileName() {
		return fxmlFileName;
	}

	public static String getImgPath() {
		return imgPath;
	}

	public static void setImgPath(String imgPath) {
		CreateLoopBean.imgPath = imgPath;
	}

	public void setFxmlFileName(String fxmlFileName) {
		CreateLoopBean.fxmlFileName = fxmlFileName;
	}

	private CreateLoopBean() {

	}

	public static CreateLoopBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new CreateLoopBean();
		}
		return instanceOfClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		CreateLoopBean.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		CreateLoopBean.height = height;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		CreateLoopBean.loader = loader;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		CreateLoopBean.scene = scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		CreateLoopBean.allFieldsMap=allFieldsMap;
	}


}
