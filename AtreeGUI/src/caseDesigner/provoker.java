package caseDesigner;

import javax.xml.parsers.ParserConfigurationException;

import controllers.GUIController;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
public class provoker {
	
	static StepHierarchyCreator aa = new StepHierarchyCreator();
	public static void provokeGeneration(String fileName) throws Exception {
		// TODO Auto-generated method stub
	   String file = fileName; //"E://atreeHello.atry";
       StepHierarchyContainerPanes.completeStepHirarchyContVBox = new VBox();       
       StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox = new ScrollPane(); 
       StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setStyle("-fx-focus-color: transparent;");
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
		GUIController guiController = new GUIController();
		guiController.setContentStepDesignTapPane(StepHierarchyContainerPanes.completeStepHirarchyContVBox);		  
		StepHierarchyContainerPanes.completeStepHirarchyContVBox.setStyle("-fx-box-border: transparent;");

	}
}
