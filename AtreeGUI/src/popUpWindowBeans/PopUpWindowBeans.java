package popUpWindowBeans;

import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public interface PopUpWindowBeans {

	public String getFxmlFileName();
	
	public Map<String,String> getAllFieldsMap();

	public void setAllFieldsMap(Map<String,String> allFieldsMap);
	
	public void setFxmlFileName(String fxmlFileName);

	public double getWidth();

	public void setWidth(double width);

	public double getHeight();

	public void setHeight(double height);

	public FXMLLoader getLoader();

	public void setLoader(FXMLLoader loader);

	public Scene getScene();

	public void setScene(Scene scene);

}
