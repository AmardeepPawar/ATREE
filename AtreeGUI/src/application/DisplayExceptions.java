package application;

import java.io.IOException;

import controllers.AtreeSingleStageGUIController;
import controllers.MessageBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import popUpWindowBeans.MessageBoxBean;
import popUpWindowBeans.PopUpWindowBeans;

public class DisplayExceptions {
	// GUIController mailGuiObj = new GUIController();
	public static Stage popUPStage = new Stage();
	static int numberOfRows;
	static Parent root;
    static double width;
    static double height;

    static DisplayExceptions classInst = new DisplayExceptions();
    
    public static DisplayExceptions getInstance()
    {    
    	if(classInst == null)
    	{
    		classInst = new DisplayExceptions();
    	}
		return classInst;    	
    }
    
	private DisplayExceptions() {

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
		DisplayExceptions.popUPStage = popUPStage;
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
	
	public void setException(String msg)
	{
		PopUpWindowBeans msgBoxBean = MessageBoxBean.getInstaceOfClass();
		try {
			prepareStage(msgBoxBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader msgBxLdr = msgBoxBean.getLoader();
		MessageBoxController aa = msgBxLdr.getController();
		aa.setMessageToWindow(msg);
	}
}
