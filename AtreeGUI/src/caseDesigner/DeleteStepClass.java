package caseDesigner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import application.AtreeFolder;
import application.DisplayExceptions;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteStepClass {
	CurrentStatusIndicatorVariables curStatus = new CurrentStatusIndicatorVariables();
	//stepHierarchyContainerPanes contPanes = new stepHierarchyContainerPanes();
	StepHierarchyMapping Mapping = new StepHierarchyMapping();
	//StepProperties startStepProp = new StepProperties();
	DeclareSettingVariable setupVariables = new DeclareSettingVariable();
	TestCaseFileReaderWriter delStepObj;
    Map<String,String> allFieldMap = new HashMap<String,String>();
    //setStartStep startStepSetup = new setStartStep();
	
	public void deleteChildStepMethod()
	{
    	if((CurrentStatusIndicatorVariables.selectedStepNode != null && !CurrentStatusIndicatorVariables.selectedStepNode.getId().equals("START-0")) || CurrentStatusIndicatorVariables.selectedPath != null) 
    	{
    	Stage deleteMessageStage = new Stage();
    	Label deleteMessageLabel = new Label();
    	Button okDeleteButton = new Button("OK");
    	Button cancelDeleteButton = new Button("Cancel");
    	HBox deleteMessageHBox = new HBox();
      	HBox okCancleBtnCntHBox = new HBox();
    	VBox deleteStepMsgNBtnContainerVBox = new VBox();
    	deleteMessageHBox.setPrefWidth(400);
    	deleteMessageHBox.setPrefHeight(90);
    	okCancleBtnCntHBox.setPrefHeight(35);
    	okCancleBtnCntHBox.setPrefWidth(400);
    	deleteMessageHBox.setAlignment(Pos.CENTER);
    	deleteMessageLabel.setWrapText(true);
    	okCancleBtnCntHBox.setAlignment(Pos.CENTER);
    	deleteStepMsgNBtnContainerVBox.setAlignment(Pos.CENTER);
    	okCancleBtnCntHBox.setPadding(new Insets(0, 0, 10, 0));
    	deleteMessageHBox.setPadding(new Insets(10, 10, 10, 10));
    	deleteStepMsgNBtnContainerVBox.getChildren().addAll(deleteMessageHBox,okCancleBtnCntHBox);
        Scene scene = new Scene(deleteStepMsgNBtnContainerVBox,400,125);
        deleteMessageHBox.getChildren().addAll(deleteMessageLabel);
        okCancleBtnCntHBox.getChildren().addAll(okDeleteButton,cancelDeleteButton);
        okCancleBtnCntHBox.setStyle("-fx-spacing:15");
        okDeleteButton.setPrefWidth(55);
        cancelDeleteButton.setPrefWidth(55);
        deleteMessageStage.setTitle("Deletion");
        deleteMessageStage.setScene(scene);
        deleteMessageStage.initModality(Modality.APPLICATION_MODAL);
        deleteMessageStage.getIcons().add(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//ATree.png"));
        deleteMessageStage.show();
    	StepProperties selectedStepProp = null;

    	if(CurrentStatusIndicatorVariables.selectedStepNode != null )
    	{
    		selectedStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(CurrentStatusIndicatorVariables.selectedStepNode.getId());
    		if(CurrentStatusIndicatorVariables.selectedModuleId == null)
    		{     	
    	deleteMessageLabel.setText("Are you sure, you want to delete \"" + selectedStepProp.getStepName() + "\"?"
    			+ "\n\nNote:- All children steps of " + selectedStepProp.getStepName() +  " will get deleted");
    		}
    		else
    		{
    	    	deleteMessageLabel.setText("Are you sure, you want to delete module added to step \"" + selectedStepProp.getStepName() + "\"?");
         	}
    	}
    	else
    	{
     		if(CurrentStatusIndicatorVariables.selectedPath.getId().split("-").length == 3)
    		{
    		   deleteMessageLabel.setText("Are you sure, you want to delete connect between\"" + StepHierarchyMapping.stepIdAndItsProperties.get(CurrentStatusIndicatorVariables.selectedPath.getId().split("_")[0]).getStepName() + "\" and \"" + StepHierarchyMapping.stepIdAndItsProperties.get(CurrentStatusIndicatorVariables.selectedPath.getId().split("_")[1]).getStepName() + "\"?");
    		}
    		else
    		{
     		   deleteMessageLabel.setText("Are you sure, you want to delete loop between\"" + StepHierarchyMapping.stepIdAndItsProperties.get(CurrentStatusIndicatorVariables.selectedPath.getId().substring(2).split("_")[0]).getStepName() + "\" and \"" + StepHierarchyMapping.stepIdAndItsProperties.get(CurrentStatusIndicatorVariables.selectedPath.getId().substring(2).split("_")[1]).getStepName() + "\"?");    			
    		}
   	
    	}
      okDeleteButton.setOnMouseClicked(new EventHandler<MouseEvent>()
      {
      @Override
      public void handle(MouseEvent t) {  
      if(CurrentStatusIndicatorVariables.selectedStepNode != null )
      	{	
    	String selectedNodeId = CurrentStatusIndicatorVariables.selectedStepNode.getId();
    	Pane deleteStepsParentContainerPane = StepHierarchyMapping.stepIdAndItsProperties.get(selectedNodeId).getParentStep();
    	String parentStepId = deleteStepsParentContainerPane.getId();     	
        delStepObj = new TestCaseFileReaderWriter();
				try {
					if(CurrentStatusIndicatorVariables.selectedModuleId == null)
					{
					  deleteConnectionsFromAllStep(selectedNodeId);
					}
					  delStepObj.deleteStepFromTestCaseXMLFile(parentStepId,selectedNodeId);			  
		  			  delStepObj.displayHierarchy();
				} catch (TransformerException | ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      	}
      else
      {
    	  delStepObj = new TestCaseFileReaderWriter();
    	  try {
			delStepObj.deletePathFromTestCaseXMLFile(CurrentStatusIndicatorVariables.selectedPath.getId());
			delStepObj.displayHierarchy();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
    	  //Sy[[stem.out.println(CurrentStatusIndicatorVariables.selectedPath.getId());
      }
       	Stage stage = (Stage) okDeleteButton.getScene().getWindow();
       	stage.close();  
       }
    });
    cancelDeleteButton.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
    @Override
    public void handle(MouseEvent t) {
    	Stage stage = (Stage) cancelDeleteButton.getScene().getWindow();
       	stage.close();  
    }
    });	
   }
   else if(CurrentStatusIndicatorVariables.selectedPath != null)
   {
	   
      	  
   }
   else
   {
		DisplayExceptions popUpWinObj1 = DisplayExceptions.getInstance();
		popUpWinObj1.setException("No Step is selected for deletion. \nNote:- Start button can not be deleted");
      }    	
    }
	
	public void deleteConnectionsFromAllStep(String deleteStepId) throws TransformerException, ParserConfigurationException
	    {
	    	  StepProperties childStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(deleteStepId);
			  List<Path> sourceSteplistOfPath = StepHierarchyMapping.sourceStepAndListOfPath.get(deleteStepId);
			  
			  if(sourceSteplistOfPath != null)
			  {
				  Iterator<Path> sourceIterator = sourceSteplistOfPath.iterator();					  
			      while (sourceIterator.hasNext()) {
					Path curPath = sourceIterator.next();
					delStepObj.deletePathFromTestCaseXMLFile(curPath.getId());
			      }
			  }
	    	
	    	 List<Pane> childPaneList = childStepProp.getStepChildrenList();
	    	 if(childPaneList != null)
	    	 {
	    	  for(Pane childPane : childPaneList)
	    	  {
	     		String childId = childPane.getId();   
	     		deleteConnectionsFromAllStep(childId);
	    	  }
	    	 }
	    }
}

