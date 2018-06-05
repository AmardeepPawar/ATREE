package stepDesigner;

import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class provoker1 extends Application {
	
	StepHierarchyCreator aa = new StepHierarchyCreator();
	public static void main(String arg[])
	{
      launch(arg);      
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	   String file = "E://atreeHello.atry";
       StepHierarchyContainerPanes.completeStepHirarchyContVBox = new VBox();
       StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox = new ScrollPane(); 
	   StepHierarchyContainerPanes.completeStepHirarchyContVBox.setAlignment(Pos.CENTER);
       VBox.setVgrow(StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox, Priority.ALWAYS);	     
       StepHierarchyContainerPanes.completeStepHirarchyContVBox.getChildren().add(StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox);
	   StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy = new Pane(); 
	   StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.setId("START-0"); 
	   TestCaseFileReaderWriter xmlFileOprObj = new TestCaseFileReaderWriter();
	   
	   try {
			xmlFileOprObj.readTestCaseXMLFile(file);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aa.hierarchyCreator();
		//VBox pane = aa.getBox();
		//pane.setAlignment(Pos.CENTER);
		//pane.setStyle("-fx-border-width:4;-fx-border-color:#6B8E23");
		Scene scene = new Scene(StepHierarchyContainerPanes.completeStepHirarchyContVBox,600,600);		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
