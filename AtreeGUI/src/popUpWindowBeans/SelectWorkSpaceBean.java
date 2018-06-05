package popUpWindowBeans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SelectWorkSpaceBean implements PopUpWindowBeans{

	static double width = 400;
	static double height = 140;
	static FXMLLoader loader = new FXMLLoader();
	static String fxmlFileName = "/fxmlFile/selectWorkSpace.fxml";
	static Scene scene;
	static Map<String,String> allFieldsMap;
	static SelectWorkSpaceBean instanceOfClass = new SelectWorkSpaceBean();
	
	private SelectWorkSpaceBean() {

	}

	public static SelectWorkSpaceBean getInstaceOfClass() {
		if (instanceOfClass == null) {
			instanceOfClass = new SelectWorkSpaceBean();
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
		SelectWorkSpaceBean.fxmlFileName=fxmlFileName;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		SelectWorkSpaceBean.width=width;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		SelectWorkSpaceBean.height=height;		
	}

	@Override
	public FXMLLoader getLoader() {
		// TODO Auto-generated method stub
		return loader;
	}

	@Override
	public void setLoader(FXMLLoader loader) {
		// TODO Auto-generated method stub
		SelectWorkSpaceBean.loader=loader;		
	}

	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return scene;
	}

	@Override
	public void setScene(Scene scene) {
		// TODO Auto-generated method stub
		SelectWorkSpaceBean.scene=scene;
	}

	@Override
	public Map<String, String> getAllFieldsMap() {
		// TODO Auto-generated method stub
		return allFieldsMap;
	}

	@Override
	public void setAllFieldsMap(Map<String, String> allFieldsMap) {
		// TODO Auto-generated method stub
		SelectWorkSpaceBean.allFieldsMap=allFieldsMap;
	}

}
