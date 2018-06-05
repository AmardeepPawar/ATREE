package stepDesigner;

import java.io.IOException;
import java.util.List;

import application.popUpWindow;
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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class deleteStepClass {
	currentStatusIndicatorVariables curStatus = new currentStatusIndicatorVariables();
	stepHierarchyContainerPanes contPanes = new stepHierarchyContainerPanes();
	stepHierarchyMapping Mapping = new stepHierarchyMapping();
	StepProperties startStepProp = new StepProperties();
	declareSettingVariable setupVariables = new declareSettingVariable();
    setStartStep startStepSetup = new setStartStep();
	
	public void deleteChildStepMethod()
	{
    	if(currentStatusIndicatorVariables.selectedStepNode != null && !currentStatusIndicatorVariables.selectedStepNode.getId().equals("START-0"))
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
        deleteMessageStage.setTitle("Delete Step");
        deleteMessageStage.setScene(scene);
        deleteMessageStage.initModality(Modality.APPLICATION_MODAL);
        deleteMessageStage.getIcons().add(new Image("icons//ATree.png"));
        deleteMessageStage.show();
    	String selectNodeId = currentStatusIndicatorVariables.selectedStepNode.getId();
    	String selectNodeIdSplitByUnderScoreArray[] = selectNodeId.split("_");
     	StepProperties selectedStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(selectNodeIdSplitByUnderScoreArray[0]);
    	deleteMessageLabel.setText("Are you sure, you want to delete \"" + selectedStepProp.getStepDisplayName() + "\"?"
    			+ "\n\nNote:- All children steps of " + selectedStepProp.getStepDisplayName() +  " will get deleted");
      okDeleteButton.setOnMouseClicked(new EventHandler<MouseEvent>()
      {
      @Override
      public void handle(MouseEvent t) {       
    	Pane deleteStepContPane = selectedStepProp.getStepPane();
    	double width =  deleteStepContPane.getWidth();
    	Pane deleteStepsParentContainerPane = selectedStepProp.getParentStep();
    	//Pane parentContainer = (Pane) childParent.get(selectNodeIdArray[0]);             	
    	String parentStepId = deleteStepsParentContainerPane.getId(); 
    	//int selectStepIndex = Integer.parseInt(selectNodeIdSplitByUnderScoreArray[1]);
    	String parentStepIdArray[] = parentStepId.split("_");
    	StepProperties parentStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(parentStepIdArray[0]);
    	//HBox parentHBox = (HBox) stepAndSiblingContainer.get(parentNodeIdArray[0]);
    	HBox delStepNSiblingCntHBox = parentStepProp.getSiblingHBoxOfStep();
        // int childCount = (int) childrenCountToStep.get(parentNodeIdArray[0]);`
        int childCount = parentStepProp.getStepChildrenCount(); 
    	//VBox parentComboBox = (VBox) stepComboBoxList.get(parentNodeIdArray[0]);
        //VBox parentComboBox = parentStepProp.getStepNameAndModuleVBox();
        //String ployLineName = parentStepIdArray[0] + "_" + selectNodeIdSplitByUnderScoreArray[0];
        String delStepToParentStepPathName = parentStepIdArray[0] + "_" + selectNodeIdSplitByUnderScoreArray[0];
        //List<Pane> childList = parentTochildren.get(parentNodeIdArray[0]);
        List<Pane> childList = parentStepProp.stepChildrenList;
        childList.remove(deleteStepContPane);
        //parentTochildren.put(parentNodeIdArray[0],childList);
        parentStepProp.setStepChildrenList(childList);
        Path delStepPath = (Path) stepHierarchyMapping.stepsConnectPath.get(delStepToParentStepPathName);
        childCount = childCount - 1;                
        //childrenCountToStep.put(parentNodeIdArray[0],childCount);
        parentStepProp.setStepChildrenCount(childCount);
        delStepNSiblingCntHBox.getChildren().remove(deleteStepContPane);                
        deleteStepsParentContainerPane.getChildren().remove(delStepPath);
        stepHierarchyMapping.stepsConnectPath.remove(delStepPath);
        //stepAndSiblingContainer.remove(selectNodeIdArray[0]);
        deleteSubStepsFromRowPropAndParentProp(selectNodeIdSplitByUnderScoreArray[0]);
        adjustStepNavigationForDelete(selectNodeIdSplitByUnderScoreArray[0],width);
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
   else if(currentStatusIndicatorVariables.selectedPath != null)
   {
      	  
   }
   else
   {
		popUpWindow alertBoxStage = new popUpWindow();
		String alertMsg = "No Step is selected for deletion. \nNote:- Start button can not be deleted";
		try {
			alertBoxStage.alterMessage("alertBox.fxml", alertMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
   }
 }
	
    public void deleteSubStepsFromRowPropAndParentProp(String selectNodeStep)
    {
    	StepProperties selectedStep = stepHierarchyMapping.stepIdAndItsProperties.get(selectNodeStep);
    	String[] delStepArraySepByDash = selectNodeStep.split("-");
    	String[] delStepArraySepByDot = delStepArraySepByDash[1].split("\\.");
    	int deleteStepsRow = 0;
   		deleteStepsRow = Integer.parseInt(delStepArraySepByDot[0]);
    	//System.out.println(deleteStepsRow);
    	RowProperties deleteStepsRowProp = stepHierarchyMapping.rowAndItsProperties.get(deleteStepsRow);
    	List<String> deleteStepsRowMems = deleteStepsRowProp.getRowMember();
    	deleteStepsRowMems.remove(selectNodeStep);
    	if(selectedStep.getStepChildrenCount() > 0)
    	{
    		RowProperties deleteStepsNextRowProp = stepHierarchyMapping.rowAndItsProperties.get(deleteStepsRow + 1);
        	List<String> deleteStepsNextRowMems = deleteStepsNextRowProp.getRowMember();
        	//System.out.println(selectedStep.getStepName());
       		List<Pane> childrenStepPane = selectedStep.getStepChildrenList();
       		for(Pane childStepPane : childrenStepPane)
       		{
            	String[] childStepArray = childStepPane.getId().split("_");
            	String childId = childStepArray[0];
            	deleteStepsNextRowMems.remove(childId);            	            	
            	deleteSubStepsFromRowProp(childId,deleteStepsRow + 1);
       		}  
       		//System.out.println(rowMem);
    	}  
    	//stepIdAndItsProperties.remove(selectNodeStep);
    }
    
    public void deleteSubStepsFromRowProp(String stepId, int rowNum)
    {
    	StepProperties curChildStep = stepHierarchyMapping.stepIdAndItsProperties.get(stepId);
    	//System.out.println(child + " " + selectedStep.getStepChildrenCount() + " " + rowNum);
    	rowNum = rowNum + 1;
	   	List<Pane> childrenPane = curChildStep.getStepChildrenList();
   		String childId = null;
    	if(curChildStep.getStepChildrenCount() > 0)
    	{
       		for(Pane childPane : childrenPane)
       		{	    	
       			RowProperties curRowProp = stepHierarchyMapping.rowAndItsProperties.get(rowNum);
	        	List<String> curRowMem = curRowProp.getRowMember();
            	String[] paneRemoveArray = childPane.getId().split("_");
            	childId = paneRemoveArray[0];
               	curRowMem.remove(childId);
            	deleteSubStepsFromRowProp(childId, rowNum);
       		}    		
    	}        	
    	//stepIdAndItsProperties.remove(stepId);
    }
    
    public void adjustStepNavigationForDelete(String stepId, double width)
    {
      String stepIdOrg = stepId;
  	  int childcount = 0;
  	  int parentindex = 0;
      while(!stepId.equals("START-0"))
       {
    	StepProperties deletedStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(stepId);
    	//Pane parentNodeCon = (Pane) childParent.get(stepId);
    	Pane parentNodeCon = deletedStepProp.getParentStep();
    	String[] parentindexarry = parentNodeCon.getId().split("_");
    	String parentId = parentindexarry[0];
    	StepProperties parentStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(parentId);
    	//childcount = (int) childrenCountToStep.get(parentId);
    	childcount = parentStepProp.getStepChildrenCount();
    	//VBox currentNode = (VBox) stepComboBoxList.get(stepId);
    	VBox currentNode = deletedStepProp.getStepNameAndModuleVBox();
    	String[] childId = currentNode.getId().split("_");
   
    	parentindex = Integer.parseInt(childId[1].trim());
   	
     	if (childcount == 0)
    	{
     	width = width - 120 - declareSettingVariable.stepsHorizontalGap;
    	}
    	
     	if (childcount >= 1 && width != -declareSettingVariable.stepsHorizontalGap)
    	{
    	//List<Pane> childrenlist = parentTochildren.get(parentId);
    	List<Pane> childrenlist = parentStepProp.getStepChildrenList();
    	Path stepPath = new Path();
    	String id = null;
    	for (Pane childpane: childrenlist)
    	{
        	String idarry[] = childpane.getId().split("_");
        	StepProperties curStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(idarry[0]);
        	//VBox curChild = (VBox) stepComboBoxList.get(idarry[0]);
        	
        	VBox curChild =  curStepProp.getStepNameAndModuleVBox();
    		id = idarry[0];
    		int nodeIndex = Integer.parseInt(idarry[1]);

    	  if(nodeIndex > parentindex )
    	  {
     		stepPath = (Path) stepHierarchyMapping.stepsConnectPath.get(parentId+"_"+id);
        	LineTo line1 = (LineTo) stepPath.getElements().get(1);
    		LineTo line2 = (LineTo) stepPath.getElements().get(3);
    		QuadCurveTo quad2 = (QuadCurveTo) stepPath.getElements().get(4);
    		LineTo line3 = (LineTo) stepPath.getElements().get(5);
    		LineTo line4 = (LineTo) stepPath.getElements().get(6);
    		LineTo line5 = (LineTo) stepPath.getElements().get(7);
    		LineTo line6 = (LineTo) stepPath.getElements().get(8);
        	MoveTo moveto = (MoveTo) stepPath.getElements().get(0);
 		
    		double curLine2XCoOrdinate = line2.getX();
    		double quad2ControlX = quad2.getControlX();
    		double quad2X = quad2.getX();
    		double line4X = line4.getX();
    		double line6X = line6.getX();
    		double newXCoOrdinate = curLine2XCoOrdinate - (width + declareSettingVariable.stepsHorizontalGap);      	
 
    		line2.setX(newXCoOrdinate);
    		line3.setX(newXCoOrdinate + declareSettingVariable.curveRadius);
    		line5.setX(newXCoOrdinate + declareSettingVariable.curveRadius);
    		quad2.setControlX(quad2ControlX - (width + declareSettingVariable.stepsHorizontalGap));
    		quad2.setX(quad2X - (width + declareSettingVariable.stepsHorizontalGap));
    		line4.setX(line4X - (width + declareSettingVariable.stepsHorizontalGap));
    		line6.setX(line6X - (width + declareSettingVariable.stepsHorizontalGap));
          	if( line1.getX() == line3.getX())
        	{   		
                stepPath.getElements().clear();
                stepPath.getElements().addAll(moveto,line3,line4,line5,line6);
        	}
          	stepHierarchyMapping.tmpColumnNumberOccupied.clear();
          	adjustConnectLineForSubStepsForDelete(idarry[0],width);
          	stepHierarchyMapping.columnNumberOccupied.putAll(stepHierarchyMapping.tmpColumnNumberOccupied);

    		if(stepId.equals(stepIdOrg))
    		{
    		nodeIndex = nodeIndex - 1;            	  
        	String setid =  id  + "_" + nodeIndex;
        	childpane.setId(setid);
        	curChild.setId(setid); 
    		}
    	   }
    	}
       }
      stepId = parentId;        
      }       
	  //List<Pane> childrenlist = parentTochildren.get(stepId2);
	  StepProperties deletedStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(stepIdOrg);
	  if(stepHierarchyMapping.sourceStepAndListOfPath.containsKey(stepIdOrg))
	  {
      List<Path> allSrcPath = stepHierarchyMapping.sourceStepAndListOfPath.get(stepIdOrg);
      for(Path curPath : allSrcPath)
       {
    	  String pathName = curPath.getId();
    	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
    	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
    	  stepHierarchyMapping.sourceStepAndListOfPath.remove(stepIdOrg);
       }	
	  }
	  if(stepHierarchyMapping.destinationStepAndListOfPath.containsKey(stepIdOrg))
	  {
      List<Path> allDestPath = stepHierarchyMapping.destinationStepAndListOfPath.get(stepIdOrg);
      for(Path curPath : allDestPath)
       {
    	  String pathName = curPath.getId();
    	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
    	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
    	  stepHierarchyMapping.destinationStepAndListOfPath.remove(stepIdOrg);
       }
	  }	  
	  List<Pane> childrenlist = deletedStepProp.getStepChildrenList();
	  stepHierarchyMapping.stepNameAndItsId.remove(deletedStepProp.getStepDisplayName());
	  if(childrenlist != null)
	  {
  	    deleteSubStepsAndItsPaths(childrenlist,stepIdOrg);
	  }
	  stepHierarchyMapping.stepIdAndItsProperties.remove(stepIdOrg);
      currentStatusIndicatorVariables.selectedStepNode = null;
   	}
    
    public void adjustConnectLineForSubStepsForDelete(String curStep, double width)
    {
    	StepProperties curStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(curStep);
        double subFactor =  width + declareSettingVariable.stepsHorizontalGap;
    	if(stepHierarchyMapping.sourceStepAndListOfPath.get(curStep) != null)
    	{
    	 List<Path> listOfSourcePath = stepHierarchyMapping.sourceStepAndListOfPath.get(curStep);
    	 for(Path cutSourcePath: listOfSourcePath)
    	 {
    		    int pathEleCnt = cutSourcePath.getElements().size();
    		    MoveTo startPoint = (MoveTo) cutSourcePath.getElements().get(0);
    	   		LineTo line1 = (LineTo) cutSourcePath.getElements().get(1);
         		QuadCurveTo quad2 = (QuadCurveTo) cutSourcePath.getElements().get(2);
         		startPoint.setX(startPoint.getX() - subFactor);
         		line1.setX(line1.getX() - subFactor);
         		quad2.setControlX(quad2.getControlX() - subFactor);
         		quad2.setX(quad2.getX() - subFactor ); 
         		//System.out.println(pathEleCnt);
         		if(pathEleCnt > 10)
         		{
        	   	  LineTo line3 = (LineTo) cutSourcePath.getElements().get(3);
             	  QuadCurveTo quad4 = (QuadCurveTo) cutSourcePath.getElements().get(4);
        	   	  LineTo line5 = (LineTo) cutSourcePath.getElements().get(5);
             	  QuadCurveTo quad6 = (QuadCurveTo) cutSourcePath.getElements().get(6);
             	  double colIndex = line3.getX();
         		  line3.setX(line3.getX() - subFactor);
         		  quad4.setControlX(quad4.getControlX() - subFactor);
         		  quad4.setX(quad4.getX() - subFactor);
         		  line5.setX(line5.getX() - subFactor);
         		  quad6.setControlX(quad6.getControlX() - subFactor);
         		  quad6.setX(quad6.getX() - subFactor );
         		  int VerticalIndex;
         		  if(line1.getX() > line3.getX())
         		  {
         			VerticalIndex = (int) (colIndex - declareSettingVariable.curveRadius);
         		  }
         		  else
         		  {
         			VerticalIndex = (int) (colIndex + declareSettingVariable.curveRadius);
         		  }     
         		  String stepId = (String) stepHierarchyMapping.columnNumberOccupied.get(VerticalIndex);
         		  stepHierarchyMapping.columnNumberOccupied.remove(VerticalIndex);
        		  int newVerticalIndex = (int) (VerticalIndex - subFactor);
        		  stepHierarchyMapping.tmpColumnNumberOccupied.put(newVerticalIndex, stepId);
         		  //columnNumberOccupied.put(newVerticalIndex, cutSourcePath);         		 
         		}  	
     	    }    	 
    	}
    	
    	if(stepHierarchyMapping.destinationStepAndListOfPath.get(curStep) != null)
    	{
     	 List<Path> listOfDestPath = stepHierarchyMapping.destinationStepAndListOfPath.get(curStep);
   	     for(Path cutDestPath: listOfDestPath)
   	     {
   		    int pathEleCnt = cutDestPath.getElements().size();
   		    LineTo line1 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 1);
   		    LineTo line2 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 2);
   		    LineTo line3 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 3);
   		    LineTo line4 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 4);
   		    LineTo line5 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 5);
   		    QuadCurveTo quad6 = (QuadCurveTo) cutDestPath.getElements().get(pathEleCnt - 6);
   		    LineTo line7 = (LineTo) cutDestPath.getElements().get(pathEleCnt - 7);
   		    QuadCurveTo quad8 = (QuadCurveTo) cutDestPath.getElements().get(pathEleCnt - 8);
   		    System.out.println(quad8.getX() + " " + line7.getX() + " " + line5.getX() + " " + quad6.getX());
   		    line1.setX(line1.getX() - subFactor);
   		    line2.setX(line2.getX() - subFactor);
   		    line3.setX(line3.getX() - subFactor);
   		    line4.setX(line4.getX() - subFactor);
            line5.setX(line5.getX() - subFactor);
    	    quad6.setControlX(quad6.getControlX() - subFactor);
            quad6.setX(quad6.getX() - subFactor );
   		    line7.setX(line7.getX() - subFactor);
          }
    	}
    	
 		int tmpCnt = curStepProp.getStepChildrenCount();
    	if(tmpCnt > 0)
    	{
    	List<Pane> childPaneList = curStepProp.getStepChildrenList();
    	 for(Pane childPane : childPaneList)
    	 {
     		String idarry[] = childPane.getId().split("_");
     		String childId = idarry[0];   
     		adjustConnectLineForSubStepsForDelete(childId,width);
    	 }
    	}    	
    }
    
    void deleteSubStepsAndItsPaths(List<Pane> list, String parentId)
    {
    	  for (Pane childpane: list)
     	   {
     		String childId = childpane.getId();
     		String pathId = parentId + "_" + childId;
     		stepHierarchyMapping.stepsConnectPath.remove(pathId);
            String[] childIdArray = childId.split("_");
            StepProperties childrenSteps = stepHierarchyMapping.stepIdAndItsProperties.get(childIdArray[0]);
            stepHierarchyMapping.stepNameAndItsId.remove(childrenSteps.getStepDisplayName());
            List<Pane> childrenlists = childrenSteps.getStepChildrenList();
     		//List<Pane> childrenlists = parentTochildren.get(childIdArray[0]);
     		if(childrenlists == null)
     		{
     			stepHierarchyMapping.stepIdAndItsProperties.remove(childIdArray[0]);
   		      if(stepHierarchyMapping.sourceStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allSrcPath = stepHierarchyMapping.sourceStepAndListOfPath.get(childIdArray[0]);
	          String sourcePathIndex = stepHierarchyMapping.pathIndexOfStepAsSource.get(childIdArray[0]);
	          stepHierarchyMapping.rowNumberOccupied.remove(sourcePathIndex);
	          for(Path curPath : allSrcPath)
	           {
	        	  String pathName = curPath.getId();
	        	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
	        	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
	        	  stepHierarchyMapping.sourceStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
   		      if(stepHierarchyMapping.destinationStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allDestPath = stepHierarchyMapping.destinationStepAndListOfPath.get(childIdArray[0]);
	          String destPathIndex = stepHierarchyMapping.pathIndexOfStepAsDestination.get(childIdArray[0]);
	          stepHierarchyMapping.rowNumberOccupied.remove(destPathIndex);
	          for(Path curPath : allDestPath)
	          {
	        	  String pathName = curPath.getId();
	        	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
	        	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
	        	  stepHierarchyMapping.destinationStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
     		}
     		else
     		{
     		  stepHierarchyMapping.stepIdAndItsProperties.remove(childIdArray[0]);
   		      if(stepHierarchyMapping.sourceStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allSrcPath = stepHierarchyMapping.sourceStepAndListOfPath.get(childIdArray[0]);
	          String sourcePathIndex = stepHierarchyMapping.pathIndexOfStepAsSource.get(childIdArray[0]);
	          stepHierarchyMapping.rowNumberOccupied.remove(sourcePathIndex);
	          for(Path curPath : allSrcPath)
	           {
	        	  String pathName = curPath.getId();
	        	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
	        	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
	        	  stepHierarchyMapping.sourceStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
   		      if(stepHierarchyMapping.destinationStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allDestPath = stepHierarchyMapping.destinationStepAndListOfPath.get(childIdArray[0]);
	          String destPathIndex = stepHierarchyMapping.pathIndexOfStepAsDestination.get(childIdArray[0]);
	          stepHierarchyMapping.rowNumberOccupied.remove(destPathIndex);
	          for(Path curPath : allDestPath)
	          {
	        	  String pathName = curPath.getId();
	        	  stepHierarchyMapping.stepsConnectPath.remove(pathName);
	        	  stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(curPath);
	        	  stepHierarchyMapping.destinationStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
	          deleteSubStepsAndItsPaths(childrenlists,childIdArray[0]);
     		}     		
     		//parentTochildren.remove(childIdArray[0]);
          }
    }
}
