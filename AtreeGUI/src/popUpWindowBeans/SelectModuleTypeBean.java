package popUpWindowBeans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SelectModuleTypeBean implements PopUpWindowBeans{

	static double width = 400;
	static double height = 125;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/fxmlFile/selectModulesType.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static SelectModuleTypeBean instanceOfClass = new SelectModuleTypeBean();
	
	private SelectModuleTypeBean() {

	}

	public static SelectModuleTypeBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new SelectModuleTypeBean();
		}
		return instanceOfClass;
	}
	
	@Override
	public String getFxmlFileName() {
		// TODO Auto-generated method stub
		return fxmlFileName;
	}

	@Override
	public void setFxmlFileName(String fxmlFileName) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.fxmlFileName=fxmlFileName;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.width=width;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.height=height;		
	}

	@Override
	public FXMLLoader getLoader() {
		// TODO Auto-generated method stub
		return loader;
	}

	@Override
	public void setLoader(FXMLLoader loader) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.loader=loader;		
	}

	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return scene;
	}

	@Override
	public void setScene(Scene scene) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.scene=scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		SelectModuleTypeBean.allFieldsMap=allFieldsMap;
	}

}