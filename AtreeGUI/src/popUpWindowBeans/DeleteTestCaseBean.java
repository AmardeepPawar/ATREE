package popUpWindowBeans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class DeleteTestCaseBean implements PopUpWindowBeans{
	
	static double width = 400;
	static double height = 115;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/fxmlFile/deleteTestCase.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static DeleteTestCaseBean instanceOfClass = new DeleteTestCaseBean();

	@Override
	public String getFxmlFileName() {
		// TODO Auto-generated method stub
		return fxmlFileName;
	}

	@Override
	public void setFxmlFileName(String fxmlFileName) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.fxmlFileName=fxmlFileName;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.width=width;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.height=height;
	}

	@Override
	public FXMLLoader getLoader() {
		// TODO Auto-generated method stub
		return loader;
	}

	@Override
	public void setLoader(FXMLLoader loader) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.loader=loader;
	}

	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return scene;
	}

	@Override
	public void setScene(Scene scene) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.scene=scene;
	}

	private DeleteTestCaseBean() {

	}

	public static DeleteTestCaseBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new DeleteTestCaseBean();
		}
		return instanceOfClass;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		DeleteTestCaseBean.allFieldsMap =allFieldsMap;
	}
}
