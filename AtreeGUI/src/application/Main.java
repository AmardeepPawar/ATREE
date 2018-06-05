package application;
	
import controllers.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	Scene scene;
	static Stage MainStage;
	@Override
	public void start(Stage mainGUIStage) {
		try {
			AtreeFolder.getInstance().setATreeFolder(System.getProperty("user.dir"));
			ReadConfigurationFileClass configProp = ReadConfigurationFileClass.getConfigFileObj();
			ConfigVariables.setMODTYNFLDMAP_XMLFILE(configProp.getPropertyValue("MODTYNFLDMAP_XMLFILE"));
			ConfigVariables.setModulesFldr(configProp.getPropertyValue("MODULES_FOLDER"));
			
			//Read XML file and set property of module group prop - like testing, ETL 
			SetModuleGroupProperties modGrpProp = SetModuleGroupProperties.getInstance();
			modGrpProp.setModuleGrpProp();
			
			final FXMLLoader mainGUIloader = new FXMLLoader(getClass().getResource("/fxmlFile/aTreeMainGUI.fxml"));
			final Parent root =  mainGUIloader.load();	
			
			ATreeAppFXMLLoaders.setAtreeMainGUILoader(mainGUIloader);	
			
			scene = new Scene(root,800,500);
            scene.getStylesheets().add("application.css");
            mainGUIStage.setScene(scene);
            mainGUIStage.getIcons().add(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//ATree.png"));			
	        //Adjust the width and height of scene to fit the screen. 
	        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	        mainGUIStage.setX(primaryScreenBounds.getMinX());
	        mainGUIStage.setY(primaryScreenBounds.getMinY());        
	        mainGUIStage.setWidth(primaryScreenBounds.getWidth());
	        mainGUIStage.setHeight(primaryScreenBounds.getHeight());	        
	        mainGUIStage.setTitle("A-Tree SoftTest");
	        mainGUIStage.show();
	        MainStage = mainGUIStage;	
			GUIController cnt = mainGUIloader.getController();
			cnt.setHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void closeMainWindow()
	{
		MainStage.close();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
