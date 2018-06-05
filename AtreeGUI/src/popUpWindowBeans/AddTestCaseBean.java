package popUpWindowBeans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class AddTestCaseBean implements PopUpWindowBeans {
	static double width = 400;
	static double height = 130;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/fxmlFile/addTestCase.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static AddTestCaseBean instanceOfClass = new AddTestCaseBean();

	public String getFxmlFileName() {
		return fxmlFileName;
	}

	public void setFxmlFileName(String fxmlFileName) {
		AddTestCaseBean.fxmlFileName = fxmlFileName;
	}

	private AddTestCaseBean() {

	}

	public static AddTestCaseBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new AddTestCaseBean();
		}
		return instanceOfClass;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		AddTestCaseBean.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		AddTestCaseBean.height = height;
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void setLoader(FXMLLoader loader) {
		AddTestCaseBean.loader = loader;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		AddTestCaseBean.scene = scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		AddTestCaseBean.allFieldsMap=allFieldsMap;
	}


}
