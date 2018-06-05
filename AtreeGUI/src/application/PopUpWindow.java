package application;

import java.io.IOException;

import controllers.AtreeSingleStageGUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import popUpWindowBeans.PopUpWindowBeans;

public class PopUpWindow {
	// GUIController mailGuiObj = new GUIController();
	public static Stage popUPStage = new Stage();
	static int numberOfRows;
	static Parent root;
    static double width;
    static double height;

    static PopUpWindow classInst = new PopUpWindow();
    
    public static PopUpWindow getInstance()
    {    
    	if(classInst == null)
    	{
    		classInst = new PopUpWindow();
    	}
		return classInst;    	
    }
    
	private PopUpWindow() {

	}
	
	public void prepareStage(PopUpWindowBeans popUpBeanObj) throws IOException
	{
        width= popUpBeanObj.getWidth();
        height=popUpBeanObj.getHeight();
		FXMLLoader loader = popUpBeanObj.getLoader();
		if (loader.getController() == null) {
			loader = new FXMLLoader(getClass().getResource(popUpBeanObj.getFxmlFileName()));
			popUpBeanObj.setLoader(loader);
			root = loader.load();
			Scene scene = new Scene(root, width, height);
			popUpBeanObj.setScene(scene);
		}
		AtreeSingleStageGUIController controller = loader.getController();

		//Map<String,String> map = popUpBeanObj.getAllFieldsMap();
		controller.clearElementsTextValues();
		controller.setElementsTextValues();
		popUPStage.setScene(popUpBeanObj.getScene());
		popUPStage.setHeight(height + 50);
		setIconToStage();
		if(popUPStage.getModality().name().equals("NONE"))
		{
		  popUPStage.initModality(Modality.APPLICATION_MODAL);
		}		
		popUPStage.show();		
	}

	public void setStageHeight(double hgt)
	{
		popUPStage.setHeight(hgt);
	}

	public static Stage getPopUPStage() {
		return popUPStage;
	}

	public static void setPopUPStage(Stage popUPStage) {
		PopUpWindow.popUPStage = popUPStage;
	}

	public void setIconToStage() {
		popUPStage.getIcons().add(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//ATree.png"));
	}

	public void setModalityToStage() {
		popUPStage.initModality(Modality.APPLICATION_MODAL);
	}

	public void setTitleToStage(String title) {
		popUPStage.setTitle(title);
	}

	public void setSceneToStage(Scene scene) {
		setIconToStage();
		popUPStage.setScene(scene);
		popUPStage.show();
	}
}
