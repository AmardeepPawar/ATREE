package stepDesigner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.Scrollable;

import application.alertController;
import application.popUpWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StepHierarchyCreator14 {

    Button addStep;
    Button deleteStep; 
    Button connectStep;
    Button createLoop;
    
	//TextField stepNameField;
	//ComboBox sourceStepList;
	//ComboBox destinationStepList;
	
    String sourceStepName;
    String destinationStepName;
    
    VBox previousStepNode;
    VBox selectedStepNode;
    Path selectedPath;
    

    ScrollPane scrollPane;
    
    VBox startpane;
    
    VBox box;
    Pane vb;    

    static int currentStepIndex;
    
    Map stepNameAndItsId = new HashMap();
    Map<String,List<Path>> sourceStepAndListOfPath = new HashMap();
    Map<String,List<Path>> destinationStepAndListOfPath = new HashMap();
    Map<String,Path> stepsConnectPath = new HashMap();
    Map columnNumberOccupied = new HashMap();
    Map tmpColumnNumberOccupied = new HashMap();
    Map rowNumberOccupied = new HashMap();
    Map pathIndexOfStepAsSource = new HashMap();
    Map pathIndexOfStepAsDestination = new HashMap();  
    Map<String,StepProperties> stepIdAndItsProperties = new HashMap();  
    Map<Integer,RowProperties> rowAndItsProperties = new HashMap();
    Map<String,PathProperties> pathAndItsProperties = new HashMap();
    List<String> connectorList;
    String status = null;
    String selectedStepId;

    Scene scene;
    double stepsVerticalGap = 60;
    double stepsHorizontalGap = 60;
    int curveRadius = 10;

    boolean manualStepEntry = false;
    
//  step name has two part name and index, getIndexOfStep splits them. step index also represent row number.
    public String getIndexOfStep(String stepId)
    {
        String[] indexString = stepId.split("-");
    	String index = indexString[1].trim();
    	return index;
    }

// hierarchyCreator method should be called from outside of class to add, delete,connect steps.also to create loop
    public void hierarchyCreator()
    {
    	// add,delete and connect buttons on scene
		addStep = new Button("Add Step");
		deleteStep = new Button("Delete Step");
		connectStep = new Button("Connect steps");
		createLoop = new Button("Create Loop");
		// stepNameField text field is used in "add step" method. to get step name for newly added step.

        // sourceStepList combo box is used in connect step method to get and display list of source step names.
		//ComboBox sourceStepList = new ComboBox();
    	// destinationStepList combo box is used in connect step method to get and display list of source step names.

        // previousStepNode stores node which just de-selected. this is used to perform some cosmetic changes to it since it was transform from selected to deselected mode.
        previousStepNode = new VBox();
        // selectedStepNode stores selected node. this is used to perform some cosmetic changes to it and mainly used in add step and delete step method. not used in connect step.
	    selectedStepNode = new VBox();
        StepProperties startStepProp = new StepProperties();
	    scrollPane = new ScrollPane();	    
	    box = new VBox();
	    box.setAlignment(Pos.CENTER);
	    vb = new Pane();	    
	    HBox siblingContainer = new HBox();
	    startpane = new VBox();
        Label start = new Label("START");
        selectedStepNode=null;
        //scene = new Scene(box, 600, 500);
        box.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        start.setAlignment(Pos.CENTER);
        start.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
        start.setPrefHeight(25);
        start.setPrefWidth(80);
        startpane.getChildren().add(start);
        startpane.setEffect(new DropShadow()); 
        startpane.setStyle("-fx-background-color: linear-gradient( #00FF00 , #32CD32);-fx-background-radius: 10.0;-fx-border-width:1; -fx-border-color:#6B8E23;-fx-border-radius:6 6 6 6;-fx-width:80;-fx-height:10;");        
        startpane.setLayoutX(80);
        startpane.setLayoutY(30);
        startpane.setId("START-0");
        vb.setId("START-0");
        
        startStepProp.setStepChildrenCount(0);
        startStepProp.setStepName("START-0");
        startStepProp.setStepDisplayName("START-0");
        startStepProp.setStepMaxIndex(0);
        startStepProp.setSiblingHBoxOfStep(siblingContainer);
        startStepProp.setStepPane(vb);
        startStepProp.setStepNameAndModuleVBox(startpane);
        stepIdAndItsProperties.put("START-0", startStepProp);
                
        selectedStepNode=startpane;
        Button okButtonForConnect = new Button();
       
        startpane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	if(selectedPath != null)
            	{
            		//selectedPath.setStyle("-fx-stroke: black;");
            		selectedPath.setEffect(null);
            	}
            	startpane.setEffect(new DropShadow()); 
            	startpane.setStyle("-fx-background-color: linear-gradient( #00FF00 , #32CD32);-fx-background-radius: 10.0;-fx-border-width:1; -fx-border-color:#6B8E23;-fx-border-radius:6 6 6 6; ");

            	if(selectedStepNode != null && selectedStepNode != startpane )
            	{
            		selectedStepNode.setEffect(null);
            		if(selectedStepNode != startpane)
            		{
            		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            	}
            	selectedStepNode = startpane;                    	
            }
        });
       
        addStep.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if(selectedStepNode != null)
            	{
                 if(!manualStepEntry)
                 {
                	 functionToAddNewStep("THISSTEPNAMEISNOTALLOWED");                
            	 }
                 else
                 {
                	getStepName();
                 }          
	           }
            	else
            	{
    				popUpWindow alertBoxStage = new popUpWindow();
    				String alertMsg = "Please select the step to which new step need to be added.";
    				try {
						alertBoxStage.alterMessage("alertBox.fxml", alertMsg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
    				
            	}
            }  
        });
        
        deleteStep.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	if(selectedStepNode != null && !selectedStepNode.getId().equals("START-0"))
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
            	String selectNodeId = selectedStepNode.getId();
            	String selectNodeIdSplitByUnderScoreArray[] = selectNodeId.split("_");
             	StepProperties selectedStepProp = stepIdAndItsProperties.get(selectNodeIdSplitByUnderScoreArray[0]);
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
            	StepProperties parentStepProp = stepIdAndItsProperties.get(parentStepIdArray[0]);
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
                Path delStepPath = (Path) stepsConnectPath.get(delStepToParentStepPathName);
                childCount = childCount - 1;                
                //childrenCountToStep.put(parentNodeIdArray[0],childCount);
                parentStepProp.setStepChildrenCount(childCount);
                delStepNSiblingCntHBox.getChildren().remove(deleteStepContPane);                
                deleteStepsParentContainerPane.getChildren().remove(delStepPath);
                stepsConnectPath.remove(delStepPath);
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
           else if(selectedPath != null)
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
        });  
        
        connectStep.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
              	Stage stepConnectStage = new Stage();
            	Label stepSourceLabel = new Label();
            	Label stepDestinationLabel = new Label();
            	Label errorMsg = new Label();
            	Button okButtonForConnect = new Button("Connect");
            	stepSourceLabel.setText("Source step: ");
            	stepDestinationLabel.setText("Destination Step: ");
            	HBox sourceStepHBox = new HBox();
            	HBox destinationStepHBox = new HBox();
            	HBox errorLabelHBox = new HBox();
            	HBox OKBtnHBox = new HBox();
            	VBox containerVBox = new VBox();
            	ComboBox sourceStepListComboBox = new ComboBox();
        		ComboBox destinationStepListComboBox = new ComboBox();
            	Set<String> setOfStepNames = stepNameAndItsId.keySet();
              	String[] stepSourceNameArray = setOfStepNames.toArray(new String[setOfStepNames.size()]);
              	//String[] stepDestinationNameArray = setOfStepNames.toArray(new String[setOfStepNames.size()]); 
              	sourceStepListComboBox.getItems().clear();            	
       
              	sourceStepListComboBox.setOnAction(e -> {
            	String selectedSourceOrig = (String) sourceStepListComboBox.getValue();
            	destinationStepListComboBox.getItems().clear();
            	destinationStepListComboBox.getItems().addAll(stepSourceNameArray);
            	String selectedSource = selectedSourceOrig;
            	destinationStepListComboBox.getItems().remove(selectedSource); 
            	            	
               	while(!selectedSource.equals("START-0"))
            	 {
               		String sourceStepNameId = (String) stepNameAndItsId.get(selectedSource);               		
               		StepProperties curStep = stepIdAndItsProperties.get(sourceStepNameId);
               		Pane paneRemove =  curStep.getParentStep();
                	String[] paneRemoveArray = paneRemove.getId().split("_");
                	String parentId = paneRemoveArray[0];
                	StepProperties parentStep = stepIdAndItsProperties.get(parentId);
                	destinationStepListComboBox.getItems().remove(parentStep.getStepDisplayName());
            		selectedSource = parentStep.getStepDisplayName();            		
                  }  
                 deleteChildrenFromDestSelectList(selectedSourceOrig,destinationStepListComboBox);                 
              	});          

              	sourceStepListComboBox.getItems().addAll(stepSourceNameArray);            	
              	sourceStepListComboBox.setPrefHeight(30);
                
              	sourceStepListComboBox.setPrefWidth(200);
              	destinationStepListComboBox.setPrefWidth(200);
            	sourceStepHBox.setPrefHeight(40);
            	sourceStepHBox.setPadding(new Insets(15, 0, 0, 0));
            	destinationStepHBox.setPadding(new Insets(10, 0, 0, 0));
            	OKBtnHBox.setPadding(new Insets(10, 0, 0, 0));
            	
            	sourceStepHBox.setPrefWidth(300);
            	destinationStepHBox.setPrefHeight(40);
            	destinationStepHBox.setPrefWidth(300);
            	OKBtnHBox.setPrefHeight(35);
            	OKBtnHBox.setPrefWidth(300);
            	sourceStepHBox.setAlignment(Pos.CENTER);
            	destinationStepHBox.setAlignment(Pos.CENTER);
            	errorLabelHBox.setAlignment(Pos.CENTER);
            	errorLabelHBox.getChildren().add(errorMsg);
            	errorLabelHBox.setPrefWidth(300);
            	errorMsg.setMaxWidth(250);
            	errorMsg.setWrapText(true);
            	OKBtnHBox.setAlignment(Pos.CENTER);
            	containerVBox.setAlignment(Pos.CENTER);
            	containerVBox.getChildren().addAll(sourceStepHBox,destinationStepHBox,OKBtnHBox,errorLabelHBox);
                Scene connectStepScene = new Scene(containerVBox,340,125);
                sourceStepHBox.getChildren().addAll(stepSourceLabel,sourceStepListComboBox);
                destinationStepHBox.getChildren().addAll(stepDestinationLabel,destinationStepListComboBox);
                errorMsg.setStyle("-fx-text-fill:red;");
                okButtonForConnect.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                       	sourceStepName = sourceStepListComboBox.getValue().toString();
                       	String sourceStepNameId = (String) stepNameAndItsId.get(sourceStepName);
                       	destinationStepName = destinationStepListComboBox.getValue().toString();
                       	String destinationStepNameId = (String) stepNameAndItsId.get(destinationStepName);
                       	status = twoStepConnector(sourceStepNameId,destinationStepNameId);
                        errorMsg.setText(status);
                      }
                });
                OKBtnHBox.getChildren().addAll(okButtonForConnect);
                stepConnectStage.setTitle("Connect two steps");
                stepConnectStage.setScene(connectStepScene);
                stepConnectStage.getIcons().add(new Image("icons//ATree.png"));
                stepConnectStage.initModality(Modality.APPLICATION_MODAL);
                stepConnectStage.show();
            }
        });        
        addStep.setLayoutX(0);
        deleteStep.setLayoutX(80);
        connectStep.setLayoutX(160);
        createLoop.setLayoutX(280);
        vb.getChildren().addAll(addStep,deleteStep,connectStep,createLoop ,startpane);
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(vb);
    } 
    
    public void deleteChildrenFromDestSelectList(String child, ComboBox destinationStepListComboBox)
    {       	
    	String childStepNameId = (String) stepNameAndItsId.get(child);
    	StepProperties selectedStep = stepIdAndItsProperties.get(childStepNameId);
       	if(selectedStep.getStepChildrenCount() > 0)
       	{
       		List<Pane> childrenPane = selectedStep.getStepChildrenList();
       		for(Pane childPane : childrenPane)
       		{
            	String[] paneRemoveArray = childPane.getId().split("_");
            	String childId = paneRemoveArray[0];
            	StepProperties curStep = stepIdAndItsProperties.get(childId);
            	destinationStepListComboBox.getItems().remove(curStep.getStepDisplayName());
            	child = curStep.getStepDisplayName();  
            	deleteChildrenFromDestSelectList(child,destinationStepListComboBox);
       		}
       	}
    }
    
    public void deleteSubStepsFromRowPropAndParentProp(String selectNodeStep)
    {
    	StepProperties selectedStep = stepIdAndItsProperties.get(selectNodeStep);
    	String[] delStepArraySepByDash = selectNodeStep.split("-");
    	String[] delStepArraySepByDot = delStepArraySepByDash[1].split("\\.");
    	int deleteStepsRow = 0;
   		deleteStepsRow = Integer.parseInt(delStepArraySepByDot[0]);
    	//System.out.println(deleteStepsRow);
    	RowProperties deleteStepsRowProp = rowAndItsProperties.get(deleteStepsRow);
    	List<String> deleteStepsRowMems = deleteStepsRowProp.getRowMember();
    	deleteStepsRowMems.remove(selectNodeStep);
    	if(selectedStep.getStepChildrenCount() > 0)
    	{
    		RowProperties deleteStepsNextRowProp = rowAndItsProperties.get(deleteStepsRow + 1);
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
    	StepProperties curChildStep = stepIdAndItsProperties.get(stepId);
    	//System.out.println(child + " " + selectedStep.getStepChildrenCount() + " " + rowNum);
    	rowNum = rowNum + 1;
	   	List<Pane> childrenPane = curChildStep.getStepChildrenList();
   		String childId = null;
    	if(curChildStep.getStepChildrenCount() > 0)
    	{
       		for(Pane childPane : childrenPane)
       		{	    	
       			RowProperties curRowProp = rowAndItsProperties.get(rowNum);
	        	List<String> curRowMem = curRowProp.getRowMember();
            	String[] paneRemoveArray = childPane.getId().split("_");
            	childId = paneRemoveArray[0];
               	curRowMem.remove(childId);
            	deleteSubStepsFromRowProp(childId, rowNum);
       		}    		
    	}        	
    	//stepIdAndItsProperties.remove(stepId);
    }
 
    public void getStepName(){
    	Stage getStepNameStage = new Stage();
		TextField stepNameTextField = new TextField();
    	Label stepNameLabel = new Label();
    	Label errorMsg = new Label();
    	Button okButton = new Button("OK");
    	stepNameLabel.setText("Step Name: ");
    	HBox stepNamePaneHBox = new HBox();
    	HBox errorMessageHBox = new HBox();
    	HBox stepNameOKBtnPane = new HBox();
    	VBox containerVBoxOfNameAndErrMsg = new VBox();
    	stepNameTextField.setPrefWidth(200);
    	stepNamePaneHBox.setPrefHeight(60);
    	stepNamePaneHBox.setPrefWidth(300);
    	stepNameOKBtnPane.setPrefHeight(35);
    	stepNameOKBtnPane.setPrefWidth(300);
    	stepNamePaneHBox.setAlignment(Pos.CENTER);
    	errorMessageHBox.setAlignment(Pos.CENTER);
    	errorMessageHBox.getChildren().add(errorMsg);
    	errorMessageHBox.setPrefWidth(300);
    	errorMsg.setMaxWidth(250);
    	errorMsg.setWrapText(true);
    	stepNameOKBtnPane.setAlignment(Pos.CENTER);
    	containerVBoxOfNameAndErrMsg.setAlignment(Pos.CENTER);
    	containerVBoxOfNameAndErrMsg.getChildren().addAll(stepNamePaneHBox,stepNameOKBtnPane,errorMessageHBox);
        Scene stepNameScene = new Scene(containerVBoxOfNameAndErrMsg,300,95);
        stepNamePaneHBox.getChildren().addAll(stepNameLabel,stepNameTextField);
        stepNameOKBtnPane.getChildren().addAll(okButton);
        getStepNameStage.setTitle("Step Name");
        
        //popUpWindow getStepNamePopUp = new popUpWindow();
        //getStepNamePopUp.setModalityToStage();
        //getStepNamePopUp.setSceneToStage(stepNameScene);
        //getStepNamePopUp.setTitleToStage("Step Name");

        getStepNameStage.setScene(stepNameScene);
        getStepNameStage.getIcons().add(new Image("icons//ATree.png"));
        getStepNameStage.initModality(Modality.APPLICATION_MODAL);
        getStepNameStage.show();

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	
            	if(selectedStepNode != null)
            	{
                    
                	String StepName = stepNameTextField.getText();
                	boolean stringmatch = Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]*", StepName);
                	if(stringmatch)
                	{
                	 if(!stepNameAndItsId.containsKey(StepName))
                	 {
                	  functionToAddNewStep(StepName); 
                	  Stage stage = (Stage) okButton.getScene().getWindow();
                 	  stage.close();  
                	 }
                	 else
                	 {
                		errorMsg.setText("Step name already exist.");
                 		errorMsg.setStyle("-fx-text-fill:red;");
                 		errorMsg.setAlignment(Pos.CENTER);
                 		getStepNameStage.setHeight(170);                 		 
                	 }
                	
            	    }            	
                	else
                	{
            		errorMsg.setText("* Step name must start with alphabet and only \"_\" and \"-\" are allowed characters in the name.");
            		errorMsg.setStyle("-fx-text-fill:red;");
            		errorMsg.setAlignment(Pos.CENTER);
            		getStepNameStage.setHeight(170); 
            		}
            	}
             };                   
         }); 
    }
    
    public void adjustStepNavigationForAdd(String newlyAddedStepId)
    {
      String stepId = newlyAddedStepId;
  	  int childCount = 0;
  	  int parentIndex = 0;
      while(!stepId.equals("START-0"))
       {
        StepProperties curStepProp = stepIdAndItsProperties.get(stepId);
        Pane parentStepCont = curStepProp.getParentStep();
    	String parentIdArry[] = parentStepCont.getId().split("_");
    	String parentId = parentIdArry[0];
    	StepProperties parentStepProp = stepIdAndItsProperties.get(parentId);
    	if (newlyAddedStepId.equals(stepId))
    	{
    		childCount = parentStepProp.getStepChildrenCount();
    	}
    	if (childCount >= 2 && !newlyAddedStepId.equals(stepId))
    	{
    	List<Pane> childrenlist = parentStepProp.getStepChildrenList();
    	Path stepPath = new Path();
    	for (Pane childpane: childrenlist)
    	{
    		String childIdarry[] = childpane.getId().split("_");
    		String childId = childIdarry[0];    		
    		int nodeIndex = Integer.parseInt(childIdarry[1]);
    		if(nodeIndex > parentIndex)
    		{
    		stepPath = (Path) stepsConnectPath.get(parentId+"_"+childId);
    		LineTo line2 = (LineTo) stepPath.getElements().get(3);
    		LineTo line3 = (LineTo) stepPath.getElements().get(5);
    		QuadCurveTo quad2 = (QuadCurveTo) stepPath.getElements().get(4);
    		LineTo line4 = (LineTo) stepPath.getElements().get(6);
    		LineTo line5 = (LineTo) stepPath.getElements().get(7);
    		LineTo line6 = (LineTo) stepPath.getElements().get(8);
    		
    		double line2XCo = line2.getX();
    		double quad2ControlX = quad2.getControlX();
    		double quad2X = quad2.getX();
    		double line4XCo = line4.getX();
    		double line6XCo = line6.getX();
    		
    		line2XCo = line2XCo + 120.0 + stepsHorizontalGap;
    		quad2ControlX = quad2ControlX + 120.0 + stepsHorizontalGap;
    		quad2X = quad2X + 120.0 + stepsHorizontalGap;
    		line4XCo = line4XCo + 120.0 + stepsHorizontalGap;
    		line6XCo = line6XCo + 120.0 + stepsHorizontalGap;
    		line2.setX(line2XCo);
    		line3.setX(line2XCo + curveRadius);
    		quad2.setControlX(quad2ControlX);
    		quad2.setX(quad2X);
    		line4.setX(line4XCo);
    		line6.setX(line6XCo);
    		line5.setX(line2XCo + curveRadius); 		
    				
     		stepsConnectPath.put(parentId+"_"+childId, stepPath);
    		parentStepCont.getChildren().remove(stepPath);
    		parentStepCont.getChildren().add(stepPath);
        	tmpColumnNumberOccupied.clear();
        	// This function (adjustConnectLinesSubStepsForAdd) is used to adjust connection lines(connection lines to show extra dependencies) between two step.  
        	adjustConnectLinesOfSubStepsForAddAction(childId);
    		columnNumberOccupied.putAll(tmpColumnNumberOccupied);
    		}
    	}
       }
    	stepId = parentId;
    	if(!parentIdArry[0].trim().equals("START-0"))
    	{
    		parentIndex = Integer.parseInt(parentIdArry[1].trim());
    	}
      }
   	}   
    
    public void adjustConnectLinesOfSubStepsForAddAction(String id)
    {
    	StepProperties childStepProp = stepIdAndItsProperties.get(id);
    	if(sourceStepAndListOfPath.get(id) != null)
    	{
    	 List<Path> listOfSourcePath = sourceStepAndListOfPath.get(id);
    	 for(Path cutSourcePath: listOfSourcePath)
    	 {
    		    int pathEleCnt = cutSourcePath.getElements().size();
    		    MoveTo startPoint = (MoveTo) cutSourcePath.getElements().get(0);
    	   		LineTo line1 = (LineTo) cutSourcePath.getElements().get(1);
         		QuadCurveTo quad2 = (QuadCurveTo) cutSourcePath.getElements().get(2);

         		startPoint.setX(startPoint.getX() + 120.0 + stepsHorizontalGap);
         		line1.setX(line1.getX() + 120.0 + stepsHorizontalGap);
         		quad2.setControlX(quad2.getControlX() + 120.0 + stepsHorizontalGap);
         		quad2.setX(quad2.getX() + 120.0 + stepsHorizontalGap ); 
         		//System.out.println(pathEleCnt);
         		if(pathEleCnt > 10)
         		{
        	   	  LineTo line3 = (LineTo) cutSourcePath.getElements().get(3);
             	  QuadCurveTo quad4 = (QuadCurveTo) cutSourcePath.getElements().get(4);
        	   	  LineTo line5 = (LineTo) cutSourcePath.getElements().get(5);
             	  QuadCurveTo quad6 = (QuadCurveTo) cutSourcePath.getElements().get(6);
             	  LineTo line7 = (LineTo) cutSourcePath.getElements().get(7);
             	  double colIndex = line3.getX();
         		  line3.setX(line3.getX() + 120.0 + stepsHorizontalGap);
         		  quad4.setControlX(quad4.getControlX() + 120.0 + stepsHorizontalGap);
         		  quad4.setX(quad4.getX() + 120.0 + stepsHorizontalGap );
         		  line5.setX(line5.getX() + 120.0 + stepsHorizontalGap);
         		  quad6.setControlX(quad6.getControlX() + 120.0 + stepsHorizontalGap);
         		  quad6.setX(quad6.getX() + 120.0 + stepsHorizontalGap );
         		  //System.out.println(quad6.getX() + " " + quad6.getControlX());
         		  if(line7.getX() < line5.getX() &&  line5.getX() < quad6.getX())
         		  {
         			 quad6.setX(quad6.getX() - (2 * curveRadius)); 
         			 line7.setX(line7.getX() + (2 * curveRadius)); 
         			 //System.out.println(quad6.getX() + " " + quad6.getControlX());
         		  }
           		  int VerticalIndex;
         		  if(line1.getX() > line3.getX())
         		  {
         			VerticalIndex = (int) (colIndex - curveRadius);
         		  }
         		  else
         		  {
         			VerticalIndex = (int) (colIndex + curveRadius);
         		  }     
         		  String stepId = (String) columnNumberOccupied.get(VerticalIndex);
        		  columnNumberOccupied.remove(VerticalIndex);
        		  int newVerticalIndex = (int) (VerticalIndex + 120 + stepsHorizontalGap);
        		  tmpColumnNumberOccupied.put(newVerticalIndex, stepId);      		 
         	  }  		
     	  }    	 
    	}
    
    	if(destinationStepAndListOfPath.get(id) != null)
    	{
     	 List<Path> listOfDestPath = destinationStepAndListOfPath.get(id);
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

   		    line1.setX(line1.getX() + 120.0 + stepsHorizontalGap);
   		    line2.setX(line2.getX() + 120.0 + stepsHorizontalGap);
   		    line3.setX(line3.getX() + 120.0 + stepsHorizontalGap);
   		    line4.setX(line4.getX() + 120.0 + stepsHorizontalGap);
   		    line5.setX(line5.getX() + 120.0 + stepsHorizontalGap);
   		    line7.setX(line7.getX() + 120.0 + stepsHorizontalGap);
   		    quad6.setControlX(quad6.getControlX() + 120.0 + stepsHorizontalGap);
        	quad6.setX(quad6.getX() + 120.0 + stepsHorizontalGap );
        	}
    	}
    	int tmpCnt = childStepProp.getStepChildrenCount();
    	if(tmpCnt > 0)
    	{
    	List<Pane> childPaneList = childStepProp.getStepChildrenList();
    	 for(Pane childPane : childPaneList)
    	 {
     		String idarry[] = childPane.getId().split("_");
     		String childId = idarry[0];   
     		adjustConnectLinesOfSubStepsForAddAction(childId);
    	 }
    	}       
    }
    
    public void adjustStepNavigationForDelete(String stepId, double width)
    {
      String stepIdOrg = stepId;
  	  int childcount = 0;
  	  int parentindex = 0;
      while(!stepId.equals("START-0"))
       {
    	StepProperties deletedStepProp = stepIdAndItsProperties.get(stepId);
    	//Pane parentNodeCon = (Pane) childParent.get(stepId);
    	Pane parentNodeCon = deletedStepProp.getParentStep();
    	String[] parentindexarry = parentNodeCon.getId().split("_");
    	String parentId = parentindexarry[0];
    	StepProperties parentStepProp = stepIdAndItsProperties.get(parentId);
    	//childcount = (int) childrenCountToStep.get(parentId);
    	childcount = parentStepProp.getStepChildrenCount();
    	//VBox currentNode = (VBox) stepComboBoxList.get(stepId);
    	VBox currentNode = deletedStepProp.getStepNameAndModuleVBox();
    	String[] childId = currentNode.getId().split("_");
   
    	parentindex = Integer.parseInt(childId[1].trim());
   	
     	if (childcount == 0)
    	{
     	width = width - 120 - stepsHorizontalGap;
    	}
    	
     	if (childcount >= 1 && width != -stepsHorizontalGap)
    	{
    	//List<Pane> childrenlist = parentTochildren.get(parentId);
    	List<Pane> childrenlist = parentStepProp.getStepChildrenList();
    	Path stepPath = new Path();
    	String id = null;
    	for (Pane childpane: childrenlist)
    	{
        	String idarry[] = childpane.getId().split("_");
        	StepProperties curStepProp = stepIdAndItsProperties.get(idarry[0]);
        	//VBox curChild = (VBox) stepComboBoxList.get(idarry[0]);
        	
        	VBox curChild =  curStepProp.getStepNameAndModuleVBox();
    		id = idarry[0];
    		int nodeIndex = Integer.parseInt(idarry[1]);

    	  if(nodeIndex > parentindex )
    	  {
     		stepPath = (Path) stepsConnectPath.get(parentId+"_"+id);
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
    		double newXCoOrdinate = curLine2XCoOrdinate - (width + stepsHorizontalGap);      	
 
    		line2.setX(newXCoOrdinate);
    		line3.setX(newXCoOrdinate + curveRadius);
    		line5.setX(newXCoOrdinate + curveRadius);
    		quad2.setControlX(quad2ControlX - (width + stepsHorizontalGap));
    		quad2.setX(quad2X - (width + stepsHorizontalGap));
    		line4.setX(line4X - (width + stepsHorizontalGap));
    		line6.setX(line6X - (width + stepsHorizontalGap));
          	if( line1.getX() == line3.getX())
        	{   		
                stepPath.getElements().clear();
                stepPath.getElements().addAll(moveto,line3,line4,line5,line6);
        	}
        	tmpColumnNumberOccupied.clear();
          	adjustConnectLineForSubStepsForDelete(idarry[0],width);
    		columnNumberOccupied.putAll(tmpColumnNumberOccupied);

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
	  StepProperties deletedStepProp = stepIdAndItsProperties.get(stepIdOrg);
	  if(sourceStepAndListOfPath.containsKey(stepIdOrg))
	  {
      List<Path> allSrcPath = sourceStepAndListOfPath.get(stepIdOrg);
      for(Path curPath : allSrcPath)
       {
    	  String pathName = curPath.getId();
    	  stepsConnectPath.remove(pathName);
    	  vb.getChildren().remove(curPath);
    	  sourceStepAndListOfPath.remove(stepIdOrg);
       }	
	  }
	  if(destinationStepAndListOfPath.containsKey(stepIdOrg))
	  {
      List<Path> allDestPath = destinationStepAndListOfPath.get(stepIdOrg);
      for(Path curPath : allDestPath)
       {
    	  String pathName = curPath.getId();
    	  stepsConnectPath.remove(pathName);
    	  vb.getChildren().remove(curPath);
    	  destinationStepAndListOfPath.remove(stepIdOrg);
       }
	  }	  
	  List<Pane> childrenlist = deletedStepProp.getStepChildrenList();
	  stepNameAndItsId.remove(deletedStepProp.getStepDisplayName());
	  if(childrenlist != null)
	  {
  	    deleteSubStepsAndItsPaths(childrenlist,stepIdOrg);
	  }
      stepIdAndItsProperties.remove(stepIdOrg);
      selectedStepNode = null;
   	}
    
    public void adjustConnectLineForSubStepsForDelete(String curStep, double width)
    {
    	StepProperties curStepProp = stepIdAndItsProperties.get(curStep);
        double subFactor =  width + stepsHorizontalGap;
    	if(sourceStepAndListOfPath.get(curStep) != null)
    	{
    	 List<Path> listOfSourcePath = sourceStepAndListOfPath.get(curStep);
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
         			VerticalIndex = (int) (colIndex - curveRadius);
         		  }
         		  else
         		  {
         			VerticalIndex = (int) (colIndex + curveRadius);
         		  }     
         		  String stepId = (String) columnNumberOccupied.get(VerticalIndex);
        		  columnNumberOccupied.remove(VerticalIndex);
        		  int newVerticalIndex = (int) (VerticalIndex - subFactor);
        		  tmpColumnNumberOccupied.put(newVerticalIndex, stepId);
         		  //columnNumberOccupied.put(newVerticalIndex, cutSourcePath);         		 
         		}  	
     	    }    	 
    	}
    	
    	if(destinationStepAndListOfPath.get(curStep) != null)
    	{
     	 List<Path> listOfDestPath = destinationStepAndListOfPath.get(curStep);
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

   		    line1.setX(line1.getX() - subFactor);
   		    line2.setX(line2.getX() - subFactor);
   		    line3.setX(line3.getX() - subFactor);
   		    line4.setX(line4.getX() - subFactor);
   		    line5.setX(line5.getX() - subFactor);
   		    line7.setX(line7.getX() - subFactor);
   		    quad6.setControlX(quad6.getControlX() - subFactor);
        	quad6.setX(quad6.getX() - subFactor );
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
     		stepsConnectPath.remove(pathId);
            String[] childIdArray = childId.split("_");
            StepProperties childrenSteps = stepIdAndItsProperties.get(childIdArray[0]);
            stepNameAndItsId.remove(childrenSteps.getStepDisplayName());
            List<Pane> childrenlists = childrenSteps.getStepChildrenList();
     		//List<Pane> childrenlists = parentTochildren.get(childIdArray[0]);
     		if(childrenlists == null)
     		{
   		      stepIdAndItsProperties.remove(childIdArray[0]);
   		      if(sourceStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allSrcPath = sourceStepAndListOfPath.get(childIdArray[0]);
	          for(Path curPath : allSrcPath)
	           {
	        	  String pathName = curPath.getId();
	        	  stepsConnectPath.remove(pathName);
	        	  vb.getChildren().remove(curPath);
	        	  sourceStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
   		      if(destinationStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allDestPath = destinationStepAndListOfPath.get(childIdArray[0]);
	          for(Path curPath : allDestPath)
	          {
	        	  String pathName = curPath.getId();
	        	  stepsConnectPath.remove(pathName);
	        	  vb.getChildren().remove(curPath);
	        	  destinationStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
     		}
     		else
     		{
 		      stepIdAndItsProperties.remove(childIdArray[0]);
   		      if(sourceStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allSrcPath = sourceStepAndListOfPath.get(childIdArray[0]);
	          for(Path curPath : allSrcPath)
	           {
	        	  String pathName = curPath.getId();
	        	  stepsConnectPath.remove(pathName);
	        	  vb.getChildren().remove(curPath);
	        	  sourceStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
   		      if(destinationStepAndListOfPath.containsKey(childIdArray[0]))
   		      {
	          List<Path> allDestPath = destinationStepAndListOfPath.get(childIdArray[0]);
	          for(Path curPath : allDestPath)
	          {
	        	  String pathName = curPath.getId();
	        	  stepsConnectPath.remove(pathName);
	        	  vb.getChildren().remove(curPath);
	        	  destinationStepAndListOfPath.remove(childIdArray[0]);
	           }
   		      }
	          deleteSubStepsAndItsPaths(childrenlists,childIdArray[0]);
     		}     		
     		//parentTochildren.remove(childIdArray[0]);
          }
    }

	public VBox getBox()
	{
		return box;
	}
	
	void rowHeightAdjustment(String StepId,String stepType)
	{
		String[] stepIDArray = StepId.split("-");
		String[] stepIDNumArray = stepIDArray[1].split("\\.");
		int rowNum = Integer.parseInt(stepIDNumArray[0]);
		if(stepType.equals("destination"))
		{
			rowNum = rowNum - 1;
		}
	    List<String> siblingDone = new ArrayList();
	    siblingDone.add("dummy");
	    RowProperties rowProp = new RowProperties();
	    //rowAndItsProperties.get(rowNum + 1);
    	//if(rowAndItsHeight.get(rowNum + 1) == null)
    	if(rowAndItsProperties.get(rowNum + 1) == null)
    	{
    		//rowAndItsHeight.put(rowNum + 1,stepsVerticalGap + 15);
    		rowProp = rowAndItsProperties.get(rowNum + 1);
    		rowProp.setRowHeight(stepsVerticalGap + 15);
    	}
    	else
    	{
    	  rowProp = rowAndItsProperties.get(rowNum + 1);
    	  double rowStepsVerticalGap = rowProp.getRowHeight();
    	  //if(rowAndItsMembers.get(rowNum + 1) != null)
    	  if(rowProp.getRowMember() != null)
    	  {
	      List<String> allElementsInRow = rowProp.getRowMember();
	      for(String rowEle: allElementsInRow)
		   {
	       StepProperties Step = stepIdAndItsProperties.get(rowEle);
		   Pane parentPane = Step.getParentStep();
		   String parentId = parentPane.getId();
		   String[] parentIdArr = parentId.split("_");
		   StepProperties parentStep = stepIdAndItsProperties.get(parentIdArr[0]);
		   if(!siblingDone.contains(parentIdArr[0]))
		   {
		   HBox SiblingBox = parentStep.getSiblingHBoxOfStep();
		   Bounds SiblingBoxBoundsInScene = SiblingBox.sceneToLocal(SiblingBox.getBoundsInLocal());
		   SiblingBox.setLayoutY(SiblingBox.getLayoutY() + 15);
		   siblingDone.add(parentIdArr[0]);
		   }
 		   String idString = parentIdArr[0] + "_" + rowEle;
 		   Path directPath = stepsConnectPath.get(idString);
 		   int sizeOfPath = directPath.getElements().size();
 		   LineTo tempLineTo1 = (LineTo) directPath.getElements().get(sizeOfPath - 4);
 		   LineTo tempLineTo2 = (LineTo) directPath.getElements().get(sizeOfPath - 3);
 		   LineTo tempLineTo3 = (LineTo) directPath.getElements().get(sizeOfPath - 2);
 		   LineTo tempLineTo4 = (LineTo) directPath.getElements().get(sizeOfPath - 1);
 		   tempLineTo1.setY(tempLineTo1.getY() + 15);
 		   tempLineTo2.setY(tempLineTo2.getY() + 15);
 		   tempLineTo3.setY(tempLineTo3.getY() + 15);
 		   tempLineTo4.setY(tempLineTo4.getY() + 15);
           if(destinationStepAndListOfPath.get(rowEle) != null)
           {
        	   List<Path> listOfDestinationStepPath = destinationStepAndListOfPath.get(rowEle);
        	   for( Path curPath : listOfDestinationStepPath )
        	   {
        		   sizeOfPath = curPath.getElements().size();
        		   LineTo tempLineTo5 = (LineTo) curPath.getElements().get(sizeOfPath - 5);
         		   tempLineTo1 = (LineTo) curPath.getElements().get(sizeOfPath - 4);
         		   tempLineTo2 = (LineTo) curPath.getElements().get(sizeOfPath - 3);
         		   tempLineTo3 = (LineTo) curPath.getElements().get(sizeOfPath - 2);
         		   tempLineTo4 = (LineTo) curPath.getElements().get(sizeOfPath - 1);
         		   tempLineTo1.setY(tempLineTo1.getY() + 15);
         		   tempLineTo2.setY(tempLineTo2.getY() + 15);
         		   tempLineTo3.setY(tempLineTo3.getY() + 15);
         		   tempLineTo4.setY(tempLineTo4.getY() + 15);
         		   tempLineTo5.setY(tempLineTo5.getY() + 15);        		   
        	   }
            } 		   
	       }	
    	  }
    	  double gap = rowStepsVerticalGap + 15;
    	  rowProp.setRowHeight(gap);
    	}
    	int i = 2;
    	while(rowAndItsProperties.get(rowNum + i) != null)
    	{
    	  rowProp = rowAndItsProperties.get(rowNum + i);
    	  //System.out.println(rowNum + i);
    	  List<String> allElementsInRow = rowProp.getRowMember();
    	  if(allElementsInRow != null)
    	  {
  	      for(String rowEle: allElementsInRow)
  		   {
  	    	 // System.out.println(rowEle);
             if(destinationStepAndListOfPath.get(rowEle) != null)
             {
          	   List<Path> listOfDestinationStepPath = destinationStepAndListOfPath.get(rowEle);
          	   for( Path curPath : listOfDestinationStepPath )
          	   {
          		     int sizeOfPath = curPath.getElements().size();
          		     LineTo tempLineTo9 = (LineTo) curPath.getElements().get(sizeOfPath - 9);
          		     QuadCurveTo tempQuadTo8 = (QuadCurveTo) curPath.getElements().get(sizeOfPath - 8);
          		     LineTo tempLineTo7 = (LineTo) curPath.getElements().get(sizeOfPath - 7);
          		     QuadCurveTo tempQuadTo6 = (QuadCurveTo) curPath.getElements().get(sizeOfPath - 6);
          		     LineTo tempLineTo5 = (LineTo) curPath.getElements().get(sizeOfPath - 5);
          		     LineTo tempLineTo1 = (LineTo) curPath.getElements().get(sizeOfPath - 4);
          		     LineTo tempLineTo2 = (LineTo) curPath.getElements().get(sizeOfPath - 3);
          		     LineTo tempLineTo3 = (LineTo) curPath.getElements().get(sizeOfPath - 2);
          		     LineTo tempLineTo4 = (LineTo) curPath.getElements().get(sizeOfPath - 1);
          		     
          		     tempLineTo1.setY(tempLineTo1.getY() + 15);
           		     tempLineTo2.setY(tempLineTo2.getY() + 15);
           		     tempLineTo3.setY(tempLineTo3.getY() + 15);
           		     tempLineTo4.setY(tempLineTo4.getY() + 15);
           		     tempLineTo5.setY(tempLineTo5.getY() + 15); 
           		     tempLineTo7.setY(tempLineTo7.getY() + 15);
           		     tempLineTo9.setY(tempLineTo9.getY() + 15); 
           		     tempQuadTo6.setControlY(tempQuadTo6.getControlY() + 15);
           		     tempQuadTo8.setControlY(tempQuadTo8.getControlY() + 15);
           		     tempQuadTo6.setY(tempQuadTo6.getY() + 15);
           		     tempQuadTo8.setY(tempQuadTo8.getY() + 15);
          	   }
              } 		   
  	       }
    	  }
    	  i= i + 1;
    	}   
    	i = 1;
    	while(rowAndItsProperties.get(rowNum + i) != null)
    	{
    	  rowProp = rowAndItsProperties.get(rowNum + i);
    	  List<String> allElementsInRow = rowProp.getRowMember(); //rowAndItsMembers.get(rowNum + i);
  	      for(String rowEle: allElementsInRow)
  		   {
             if(sourceStepAndListOfPath.get(rowEle) != null)
             {
          	   List<Path> listOfDestinationStepPath = sourceStepAndListOfPath.get(rowEle);
          	   for( Path curPath : listOfDestinationStepPath )
          	   {
          		     QuadCurveTo tempQuadTo4 = (QuadCurveTo) curPath.getElements().get(4);
          		     QuadCurveTo tempQuadTo2 = (QuadCurveTo) curPath.getElements().get(2);
          		     LineTo tempLineTo3 = (LineTo) curPath.getElements().get(3);
          		     LineTo tempLineTo1 = (LineTo) curPath.getElements().get(1);
          		     MoveTo tmpMoveTo0 = (MoveTo) curPath.getElements().get(0);
          		     
          		     tempLineTo1.setY(tempLineTo1.getY() + 15);
           		     tempLineTo3.setY(tempLineTo3.getY() + 15);
           		     tempQuadTo2.setControlY(tempQuadTo2.getControlY() + 15);
           		     tempQuadTo4.setControlY(tempQuadTo4.getControlY() + 15);
           		     tempQuadTo2.setY(tempQuadTo2.getY() + 15);
           		     tempQuadTo4.setY(tempQuadTo4.getY() + 15);
           		     tmpMoveTo0.setY(tmpMoveTo0.getY() + 15);
          	   }
              } 		   
  	       }	
    	  i= i + 1;
    	}   	
	}
	
	public String twoStepConnector(String sourceStep, String destinationStep )
	{
		if(stepsConnectPath.get(sourceStep + "_" + destinationStep) == null)
		{
		 if(selectedStepNode != null)
		 {
		  selectedStepNode.setEffect(null);
		 }
		double scrollXpos = scrollPane.getHvalue();
		double scrollYpos = scrollPane.getVvalue();
		String pathName = sourceStep + "_" + destinationStep;
		String[] sourceNameSplitByDash = sourceStep.split("-");
		String[] sourceNumSplitByDot = sourceNameSplitByDash[1].split("\\.");
		int sourceRowNum = Integer.parseInt(sourceNumSplitByDot[0]);
		String[] destNameSplitByDash = destinationStep.split("-");
		String[] destNumSplitByDot = destNameSplitByDash[1].split("\\.");
		int destRowNum = Integer.parseInt(destNumSplitByDot[0]) - 1;
		RowProperties sourceNextRowProp = new RowProperties();
		//System.out.println(sourceRowNum + " " + destRowNum);
		if(rowAndItsProperties.containsKey(sourceRowNum + 1))
		{
			sourceNextRowProp = rowAndItsProperties.get(sourceRowNum + 1);
		}
		else
		{			
			sourceNextRowProp.setRowNum(sourceRowNum + 1);
			sourceNextRowProp.setTotalStepsForRow(0);
			sourceNextRowProp.setRowHeight(stepsVerticalGap);
			rowAndItsProperties.put(sourceRowNum + 1, sourceNextRowProp);			
		}
		RowProperties destRowProp = rowAndItsProperties.get(destRowNum + 1);
		StepProperties sourceStepProp = stepIdAndItsProperties.get(sourceStep);
		StepProperties destStepProp = stepIdAndItsProperties.get(destinationStep);
		double sourceRowHeight = sourceNextRowProp.getRowHeight();
		double destRowHeight =	destRowProp.getRowHeight();
		scrollPane.setHvalue(0);
		scrollPane.setVvalue(0);
		VBox sourceVbox = sourceStepProp.getStepNameAndModuleVBox(); 
    	VBox destVbox = destStepProp.getStepNameAndModuleVBox(); 
		Bounds sourceStepBoundsInScene = sourceVbox.localToScene(sourceVbox.getBoundsInLocal());
		Bounds destStepBoundsInScene = destVbox.localToScene(destVbox.getBoundsInLocal());
		double sourcePosX = sourceStepBoundsInScene.getMinX();
		double sourcePosY = sourceStepBoundsInScene.getMinY();
		double sourceWidth = sourceStepBoundsInScene.getWidth();
		double sourceHeight = sourceStepBoundsInScene.getHeight();
		double destPosX = destStepBoundsInScene.getMinX();
		double destPosY = destStepBoundsInScene.getMinY();
		double destWidth = destStepBoundsInScene.getWidth();
		double destHeight = destStepBoundsInScene.getHeight();
		
		int columnLineLimit = 3, sideLineGapFromStep = 13, gapBetweenTwoVertLines = 13;
		int colMaxLimit = gapBetweenTwoVertLines * (columnLineLimit + 1);
		
		int sourceHorizontalFactorTemp = 15, sourceHorizontalFactor = 15;		
		int destinationHorizontalFactor = 15;
        int sourceHoriFactorSignDecider = 1;
        // connector line index
        int VerticalIndex = 0;
        String sourceRowAndIndexString;
        String destinationRowAndIndexString = null;
        
        double sourcePosXTemp = sourcePosX;
        //
        double baseHorizonatlIndex = sourcePosY + sourceHeight + sourceHorizontalFactorTemp;
        String baseRowAndIndexString = (sourceRowNum + 1) + "_" + sourceHorizontalFactorTemp;
        double leapFactor =  (sourceWidth + stepsHorizontalGap);
    	if(sourceRowNum != destRowNum)
    	{        
        while(true)
        {
        	//set vertical gap from step
        	VerticalIndex = (int) (sourcePosXTemp - sideLineGapFromStep); 
        	if(columnNumberOccupied.get(VerticalIndex) == null)
        	{
        		break;
        	}
        	sideLineGapFromStep = sideLineGapFromStep + gapBetweenTwoVertLines;
        	if(sideLineGapFromStep == colMaxLimit && VerticalIndex < 25)
        	{
        		leapFactor = leapFactor * (-1);
        		sourcePosXTemp = sourcePosX;
        	}
        	if(sideLineGapFromStep == colMaxLimit)
        	{
        		sideLineGapFromStep = gapBetweenTwoVertLines;
        		sourcePosXTemp = sourcePosXTemp - leapFactor;
        	}    
          }
        
        if(pathIndexOfStepAsSource.get(sourceStep) == null)
        {
        while(true)
        {   
         if(sourcePosXTemp != sourcePosX)
          {
        	sourceRowAndIndexString = (sourceRowNum + 1) + "_" + sourceHorizontalFactorTemp;
        	if(rowNumberOccupied.get(sourceRowAndIndexString) == null)
        	{
        		break;
        	}
	        sourceHorizontalFactorTemp = sourceHorizontalFactorTemp - (15 * sourceHoriFactorSignDecider);
        	if(sourceHorizontalFactorTemp <= 8 || sourceHorizontalFactorTemp > sourceHorizontalFactor)
        	{
        	  if(sourceHoriFactorSignDecider == 1)
        	  {
        	  sourceHorizontalFactorTemp = sourceHorizontalFactor + 30;
        	  sourceHoriFactorSignDecider = -1;
        	  }
        	}
          }
          else
          {   
        	sourceRowAndIndexString = (sourceRowNum + 1) + "_" + sourceHorizontalFactorTemp;
        	//System.out.println(sourceRowAndIndexString);
          	break;
          }
         }
       }
       else
       {     
    	    sourceRowAndIndexString = (String) pathIndexOfStepAsSource.get(sourceStep);
    	    if(sourceRowAndIndexString.equals(baseRowAndIndexString) && sourcePosXTemp != sourcePosX)
    	    {
    	        while(true)
    	        {   
    	        	sourceRowAndIndexString =  (sourceRowNum + 1 )+ "_" + sourceHorizontalFactorTemp;
    	        	if(rowNumberOccupied.get(sourceRowAndIndexString) == null)
    	        	{    	        		
    	        		adjustPreviousLineOfSource(sourceStep,sourceHorizontalFactorTemp - sourceHorizontalFactor);
    	        		break;
    	        	}
     	        	sourceHorizontalFactorTemp = sourceHorizontalFactorTemp - (15 * sourceHoriFactorSignDecider);
    	        	if(sourceHorizontalFactorTemp <= 8 || sourceHorizontalFactorTemp > sourceHorizontalFactor)
    	        	{
    	        	  if(sourceHoriFactorSignDecider == 1)
    	        	  {
    	        	  sourceHorizontalFactorTemp = sourceHorizontalFactor + 30;
    	        	  sourceHoriFactorSignDecider = -1;
    	        	  }
    	        	}
    	          } 
    	     }
    	    else
    	    {
    	    String sourceHoriIndexArry[] = sourceRowAndIndexString.split("_");
        	sourceHorizontalFactorTemp = Integer.parseInt(sourceHoriIndexArry[1]); //(int) (sourceHorizontalIndex - (sourcePosY + sourceHeight));
    	    }
       }
     if(pathIndexOfStepAsDestination.get(destinationStep) == null)
      {
        while(true)
        {
        	//destinationHorizontalIndex = (int) (destPosY - (60 - destinationHorizontalFactor)); 
        	destinationRowAndIndexString = destRowNum + "_" + destinationHorizontalFactor;
        	int mainLineIndex = (int) (stepsHorizontalGap/2);
        	if(destinationHorizontalFactor != mainLineIndex)
        	{
        	 if(rowNumberOccupied.get(destinationRowAndIndexString) == null)
        	 {
        		break;
        	 } 
        	}
        	destinationHorizontalFactor = destinationHorizontalFactor + 15;       	
        }
	   }
      else
        {
        	//destinationHorizontalIndex = (int) stepDestinationOccupied.get(destinationStep);
    	    destinationRowAndIndexString = (String) pathIndexOfStepAsDestination.get(destinationStep);
        	String[] destHoriIndexArray = destinationRowAndIndexString.split("_");
        	destinationHorizontalFactor = Integer.parseInt(destHoriIndexArray[1]);//(int) (destinationHorizontalIndex - destPosY + 60);
        }
        columnNumberOccupied.put(VerticalIndex, pathName);
        rowNumberOccupied.put(sourceRowAndIndexString, pathName);
        rowNumberOccupied.put(destinationRowAndIndexString, pathName);
        pathIndexOfStepAsSource.put(sourceStep, sourceRowAndIndexString);
        pathIndexOfStepAsDestination.put(destinationStep, destinationRowAndIndexString);

		double sourceConectPosX = sourcePosX + (sourceWidth/2) - 8;//(8 * outConnCount);		
		double sourceConectPosY = sourcePosY + sourceHeight;
		Path connectPath = new Path();
		MoveTo moveTo = new MoveTo();
		moveTo.setX(sourceConectPosX);
		moveTo.setY(sourceConectPosY);

		double sourceDestMidPoint1X = sourcePosX + (sourceWidth/2) - 8;//(8 * outConnCount);
		double sourceDestMidPoint1Y = sourcePosY + sourceHeight + sourceHorizontalFactorTemp;//(32 - 7 * outConnCount);
		
		LineTo lineTo1 = new LineTo();
		lineTo1.setX(sourceDestMidPoint1X);
		lineTo1.setY(sourceDestMidPoint1Y - curveRadius);

	    QuadCurveTo quadTo1 = new QuadCurveTo();
	    quadTo1.setControlX(sourceDestMidPoint1X);
	    quadTo1.setControlY(sourceDestMidPoint1Y);
	    if(sourcePosXTemp <= sourcePosX)
	    {
	      quadTo1.setX(sourceDestMidPoint1X - curveRadius);
	    }
	    else
	    {
	      quadTo1.setX(sourceDestMidPoint1X + curveRadius);
	    }
	    quadTo1.setY(sourceDestMidPoint1Y);
		
		double sourceDestMidPoint2X = sourcePosXTemp - sideLineGapFromStep;
		double sourceDestMidPoint2Y = sourcePosY + sourceHeight + sourceHorizontalFactorTemp;//(32 - 7 * outConnCount);
		
		LineTo lineTo2 = new LineTo();
		if(sourcePosXTemp <= sourcePosX)
		{
		lineTo2.setX(sourceDestMidPoint2X + curveRadius);
		}
		else
		{
		 lineTo2.setX(sourceDestMidPoint2X - curveRadius);	
		}
		lineTo2.setY(sourceDestMidPoint2Y);

	    QuadCurveTo quadTo2 = new QuadCurveTo();
	    quadTo2.setControlX(sourceDestMidPoint2X);
	    quadTo2.setControlY(sourceDestMidPoint2Y);
	    quadTo2.setX(sourceDestMidPoint2X);
	    
	    if(sourcePosY >= destPosY)
	    	{
	    	 quadTo2.setY(sourceDestMidPoint2Y - curveRadius);
	    	}
	    else if (sourcePosY < destPosY)
	        {
	    	 quadTo2.setY(sourceDestMidPoint2Y + curveRadius);
	        }
	    
	    double sourceDestMidPoint3X = sourcePosXTemp - sideLineGapFromStep;
		double sourceDestMidPoint3Y = destPosY - (destRowHeight - destinationHorizontalFactor);//(25 + 5 * inConnCount)));
		
		LineTo lineTo3 = new LineTo();
		lineTo3.setX(sourceDestMidPoint3X);
		
	    if(sourcePosY >= destPosY)
    	{
	    	lineTo3.setY(sourceDestMidPoint3Y + curveRadius);
    	}
         else if (sourcePosY < destPosY)
        {
        	 lineTo3.setY(sourceDestMidPoint3Y - curveRadius);
        }

	    QuadCurveTo quadTo3 = new QuadCurveTo();
	    quadTo3.setControlX(sourceDestMidPoint3X);
	    quadTo3.setControlY(sourceDestMidPoint3Y);
	    
	    
	    if(sourcePosXTemp > destPosX)
    	{
	    	quadTo3.setX(sourceDestMidPoint3X - curveRadius);
    	}
         else if (sourcePosXTemp <= destPosX)
        {
        	quadTo3.setX(sourceDestMidPoint3X + curveRadius);
        }
	    quadTo3.setY(sourceDestMidPoint3Y);
		
		double sourceDestMidPoint4X = destPosX + (sourceWidth/2) + 8;//(8 * inConnCount);
		double sourceDestMidPoint4Y = destPosY - (destRowHeight - destinationHorizontalFactor);//(25 + 5 * inConnCount)) ;

		LineTo lineTo4 = new LineTo();

		lineTo4.setY(sourceDestMidPoint4Y);
		
	    if(sourcePosXTemp > destPosX)
    	{
			lineTo4.setX(sourceDestMidPoint4X + curveRadius);
    	}
         else if (sourcePosXTemp <= destPosX)
        {
     		lineTo4.setX(sourceDestMidPoint4X - curveRadius);
        }

	    QuadCurveTo quadTo4 = new QuadCurveTo();
	    quadTo4.setControlX(sourceDestMidPoint4X);
	    quadTo4.setControlY(sourceDestMidPoint4Y);
	    quadTo4.setX(sourceDestMidPoint4X);	    
	    quadTo4.setY(sourceDestMidPoint4Y + curveRadius);

		double destConectPosX = destPosX + (destWidth/2) + 8;//(8 * inConnCount);
		double destConectPosY = destPosY;		
		
		LineTo lineTo5 = new LineTo();
		lineTo5.setX(destConectPosX);
		lineTo5.setY(destConectPosY - 2);
		
		LineTo lineTo6 = new LineTo();
		lineTo6.setX(destConectPosX + 3);
		lineTo6.setY(destConectPosY - 8);
		
		LineTo lineTo7 = new LineTo();
		lineTo7.setX(destConectPosX);
		lineTo7.setY(destConectPosY - 2);
		
		LineTo lineTo8 = new LineTo();
		lineTo8.setX(destConectPosX - 3);
		lineTo8.setY(destConectPosY - 8);
		
		LineTo lineTo9 = new LineTo();
		lineTo9.setX(destConectPosX);
		lineTo9.setY(destConectPosY - 2);
		
		connectPath.getElements().addAll(moveTo,lineTo1,quadTo1,lineTo2,quadTo2,lineTo3,quadTo3,lineTo4,quadTo4,lineTo5,lineTo6,lineTo7,lineTo8,lineTo9);
		connectPath.setStrokeWidth(1.5);

		vb.getChildren().add(connectPath);
        if(selectedStepNode != null)
        {
		 selectedStepNode.setEffect(new DropShadow());
        }
		scrollPane.setHvalue(scrollXpos);
		scrollPane.setVvalue(scrollYpos);
		stepsConnectPath.put(pathName,connectPath);
		connectPath.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	if(selectedPath != null)
            	{
            	 //selectedPath.setStyle("-fx-stroke: black;");
            	 selectedPath.setEffect(null);
            	}
            	if(selectedStepNode != null)
            	{
            		selectedStepNode.setEffect(null);
            		if(selectedStepNode != startpane)
            		{
            		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            	}
            	vb.getChildren().remove(connectPath);
            	vb.getChildren().add(connectPath);
            	//regularConnectPath.setStyle("-fx-stroke: #e59866;");
            	connectPath.setEffect(new DropShadow());
            	selectedPath = connectPath;
            	selectedStepNode = null;
            	if(mouseEvent.getClickCount() == 2)
            	{
            		System.out.println("mouse Double Clicked");
            	}
            }
        });
		
		if(sourceStepAndListOfPath.get(sourceStep) != null)
		{
			List<Path> listOfSourcePath = sourceStepAndListOfPath.get(sourceStep);
			listOfSourcePath.add(connectPath);
			sourceStepAndListOfPath.put(sourceStep,listOfSourcePath);
		}
		else
		{
			List<Path> listOfSourcePath = new ArrayList();
			listOfSourcePath.add(connectPath);
			sourceStepAndListOfPath.put(sourceStep,listOfSourcePath);			
		}
		
		if(destinationStepAndListOfPath.get(destinationStep) != null)
		{
			List<Path> listOfDestPath = destinationStepAndListOfPath.get(destinationStep);
			listOfDestPath.add(connectPath);
			destinationStepAndListOfPath.put(destinationStep,listOfDestPath);
		}
		else
		{
			List<Path> listOfDestPath = new ArrayList();
			listOfDestPath.add(connectPath);
			destinationStepAndListOfPath.put(destinationStep,listOfDestPath);			
		}

    	 if(sourceHorizontalFactorTemp >= sourceRowHeight)
    	 {
    	   rowHeightAdjustment(sourceStep,"source");	
    	 }
		}
    	//when destination is in exact next row of source
    	else
    	{
    	     if(pathIndexOfStepAsDestination.get(destinationStep) == null)
    	      {
    	        while(true)
    	        {
    	        	//destinationHorizontalIndex = (int) (destPosY - (60 - destinationHorizontalFactor)); 
    	        	destinationRowAndIndexString = (destRowNum + 1) + "_" + destinationHorizontalFactor;
    	        	System.out.println(destinationStep + " " + destinationRowAndIndexString);
    	        	int mainLineIndex = (int) (stepsHorizontalGap/2);
    	        	if(destinationHorizontalFactor != mainLineIndex)
    	        	{
    	        	 if(rowNumberOccupied.get(destinationRowAndIndexString) == null)
    	        	 {
    	        		break;
    	        	 } 
    	        	}
    	        	destinationHorizontalFactor = destinationHorizontalFactor + 15;       	
    	         }
    		    }
    	       else
    	        {
    	    	    destinationRowAndIndexString = (String) pathIndexOfStepAsDestination.get(destinationStep);
    	        	System.out.println(destinationStep + " " + destinationRowAndIndexString);
    	        	String[] destHoriIndexArray = destinationRowAndIndexString.split("_");
    	        	destinationHorizontalFactor = Integer.parseInt(destHoriIndexArray[1]);//(int) (destinationHorizontalIndex - destPosY + 60);
    	        }
    	        columnNumberOccupied.put(VerticalIndex, pathName);
    	        //System.out.println(VerticalIndex);
    	        rowNumberOccupied.put(destinationRowAndIndexString, pathName);
    	        pathIndexOfStepAsDestination.put(destinationStep, destinationRowAndIndexString);
    			double sourceConectPosX = sourcePosX + (sourceWidth/2) - 8;//(8 * outConnCount);		
    			double sourceConectPosY = sourcePosY + sourceHeight;
    			Path connectPath = new Path();
    			MoveTo moveTo = new MoveTo();
    			moveTo.setX(sourceConectPosX);
    			moveTo.setY(sourceConectPosY);

    			double sourceDestMidPoint1X = sourcePosX + (sourceWidth/2) - 8;//(8 * outConnCount);
    			double sourceDestMidPoint1Y = sourcePosY + sourceHeight + destinationHorizontalFactor;//(32 - 7 * outConnCount);
    			
    			LineTo lineTo1 = new LineTo();
    			lineTo1.setX(sourceDestMidPoint1X);
    			lineTo1.setY(sourceDestMidPoint1Y - curveRadius);

    		    QuadCurveTo quadTo1 = new QuadCurveTo();
    		    quadTo1.setControlX(sourceDestMidPoint1X);
    		    quadTo1.setControlY(sourceDestMidPoint1Y);
    		    if(destPosX <= sourcePosX)
    		    {
    		      quadTo1.setX(sourceDestMidPoint1X - curveRadius);
    		    }
    		    else
    		    {
    		      quadTo1.setX(sourceDestMidPoint1X + curveRadius);
    		    }
    		    quadTo1.setY(sourceDestMidPoint1Y);    			
    			 			
    			double sourceDestMidPoint4X = destPosX + (sourceWidth/2) + 8;//(8 * inConnCount);
    			double sourceDestMidPoint4Y = destPosY - (destRowHeight - destinationHorizontalFactor);//(25 + 5 * inConnCount)) ;

    			LineTo lineTo4 = new LineTo();

    			lineTo4.setY(sourceDestMidPoint4Y);
    			
    		    if(sourcePosXTemp > destPosX)
    	    	{
    				lineTo4.setX(sourceDestMidPoint4X + curveRadius);
    	    	}
    	         else if (sourcePosXTemp <= destPosX)
    	        {
    	     		lineTo4.setX(sourceDestMidPoint4X - curveRadius);
    	        }

    		    QuadCurveTo quadTo4 = new QuadCurveTo();
    		    quadTo4.setControlX(sourceDestMidPoint4X);
    		    quadTo4.setControlY(sourceDestMidPoint4Y);
    		    quadTo4.setX(sourceDestMidPoint4X);	    
    		    quadTo4.setY(sourceDestMidPoint4Y + curveRadius);

    			double destConectPosX = destPosX + (destWidth/2) + 8;//(8 * inConnCount);
    			double destConectPosY = destPosY;		
    			
    			LineTo lineTo5 = new LineTo();
    			lineTo5.setX(destConectPosX);
    			lineTo5.setY(destConectPosY - 2);
    			
    			LineTo lineTo6 = new LineTo();
    			lineTo6.setX(destConectPosX + 3);
    			lineTo6.setY(destConectPosY - 8);
    			
    			LineTo lineTo7 = new LineTo();
    			lineTo7.setX(destConectPosX);
    			lineTo7.setY(destConectPosY - 2);
    			
    			LineTo lineTo8 = new LineTo();
    			lineTo8.setX(destConectPosX - 3);
    			lineTo8.setY(destConectPosY - 8);
    			
    			LineTo lineTo9 = new LineTo();
    			lineTo9.setX(destConectPosX);
    			lineTo9.setY(destConectPosY - 2);
    			
    			connectPath.getElements().addAll(moveTo,lineTo1,quadTo1,lineTo4,quadTo4,lineTo5,lineTo6,lineTo7,lineTo8,lineTo9);
    			connectPath.setStrokeWidth(1.5);

    			vb.getChildren().add(connectPath);
    			connectPath.setOnMouseClicked(new EventHandler<MouseEvent>()
    	        {
    	            @Override
    	            public void handle(MouseEvent mouseEvent) {
    	            	if(selectedPath != null)
    	            	{
    	            	 //selectedPath.setStyle("-fx-stroke: black;");
    	            	 selectedPath.setEffect(null);
    	            	}
    	            	if(selectedStepNode != null)
    	            	{
    	            		selectedStepNode.setEffect(null);
    	            		if(selectedStepNode != startpane)
    	            		{
    	            		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
    	            		}
    	            	}
    	            	vb.getChildren().remove(connectPath);
    	            	vb.getChildren().add(connectPath);
    	            	//regularConnectPath.setStyle("-fx-stroke: #e59866;");
    	            	connectPath.setEffect(new DropShadow());
    	            	selectedPath = connectPath;
    	            	selectedStepNode = null;
    	            	if(mouseEvent.getClickCount() == 2)
    	            	{
    	            		System.out.println("mouse Double Clicked");
    	            	}
    	            }
    	        });
    		  if(selectedStepNode != null)
    		   {
    			selectedStepNode.setEffect(new DropShadow());
    		   }
    			scrollPane.setHvalue(scrollXpos);
    			scrollPane.setVvalue(scrollYpos);
    			stepsConnectPath.put(pathName,connectPath);
    			if(sourceStepAndListOfPath.get(sourceStep) != null)
    			{
    				List<Path> listOfSourcePath = sourceStepAndListOfPath.get(sourceStep);
    				listOfSourcePath.add(connectPath);
    				sourceStepAndListOfPath.put(sourceStep,listOfSourcePath);
    			}
    			else
    			{
    				List<Path> listOfSourcePath = new ArrayList();
    				listOfSourcePath.add(connectPath);
    				sourceStepAndListOfPath.put(sourceStep,listOfSourcePath);			
    			}
    			if(destinationStepAndListOfPath.get(destinationStep) != null)
    			{
    				List<Path> listOfDestPath = destinationStepAndListOfPath.get(destinationStep);
    				listOfDestPath.add(connectPath);
    				destinationStepAndListOfPath.put(destinationStep,listOfDestPath);
    			}
    			else
    			{
    				List<Path> listOfDestPath = new ArrayList();
    				listOfDestPath.add(connectPath);
    				destinationStepAndListOfPath.put(destinationStep,listOfDestPath);			
    			}

    	}
    	
    	 if(destinationHorizontalFactor >= destRowHeight)
    	 {
    		 //System.out.println(destinationHorizontalFactor + " " + destRowHeight);
    		 rowHeightAdjustment(destinationStep,"destination");	
    	 }
    	
		return null;
		}
		else
		{
		return "Steps are already connected";
		}
	}
	
	public void adjustPreviousLineOfSource(String sourceStep,int sourceHorizontalFactorGap) 
	{
		List<Path> tmpConnectionPath = sourceStepAndListOfPath.get(sourceStep);
		for(Path tmpPath : tmpConnectionPath)
		{
		 int pathSize = tmpPath.getElements().size();
		 if( pathSize == 14)
		 {
			LineTo line1 = (LineTo) tmpPath.getElements().get(1);	
			LineTo line3 = (LineTo) tmpPath.getElements().get(3);
			QuadCurveTo quad2 = (QuadCurveTo) tmpPath.getElements().get(2);
			QuadCurveTo quad4 = (QuadCurveTo) tmpPath.getElements().get(4);
			line1.setY(line1.getY() + sourceHorizontalFactorGap);
			line3.setY(line3.getY() + sourceHorizontalFactorGap);
			quad2.setControlY(quad2.getControlY() + sourceHorizontalFactorGap);
			quad4.setControlY(quad4.getControlY() + sourceHorizontalFactorGap);
			quad2.setY(quad2.getY() + sourceHorizontalFactorGap);
			quad4.setY(quad4.getY() + sourceHorizontalFactorGap);
		 }
		}
	}
	
	void functionToAddNewStep(String stepNameToDisplay)
	{
    	String selectedStepIdarry[] = selectedStepNode.getId().split("_");
    	selectedStepId = selectedStepIdarry[0];
    	String selectedStepNum = getIndexOfStep(selectedStepId); 
    	StepProperties selectedStepProp = new StepProperties();
    	selectedStepProp = stepIdAndItsProperties.get(selectedStepId);
        int selectedStepChildrenCount = selectedStepProp.getStepChildrenCount();
    	int selectedStepMaxIndex = selectedStepProp.getStepMaxIndex();   
    	Pane selectedStepContainer = selectedStepProp.getStepPane(); 
    	HBox sibContainer = selectedStepProp.getSiblingHBoxOfStep();
    	String newlyAddedStepsDotSepNum = null;
        String newStepsId;
    	double tmpStepsVerticalGap = 60;
    	int newlyAddedStepRowNum=0;
    	String[] selectedStepNumDotSepArray = {"0"};
    	if( !selectedStepNum.equals("0"))
    	{
    	  selectedStepNumDotSepArray = selectedStepNum.split("\\.");
    	  newlyAddedStepRowNum = Integer.parseInt(selectedStepNumDotSepArray[0]) + 1;
    	}
    	else
    	{
    		newlyAddedStepRowNum = Integer.parseInt(selectedStepNum) + 1;
    	} 
    	int newStepsRowMemberCnt = 1;
    	RowProperties newStepsRowPro = new RowProperties();

    	if(rowAndItsProperties.containsKey(newlyAddedStepRowNum))
    	{
    		newStepsRowPro = rowAndItsProperties.get(newlyAddedStepRowNum);
    		newStepsRowMemberCnt = newStepsRowPro.getTotalStepsForRow();
    		newStepsRowMemberCnt = newStepsRowMemberCnt + 1;
    		newStepsRowPro.setTotalStepsForRow(newStepsRowMemberCnt);
    	}
    	else
    	{
    		newStepsRowPro.setRowNum(newlyAddedStepRowNum);
    		newStepsRowPro.setTotalStepsForRow(newStepsRowMemberCnt);
    		newStepsRowPro.setRowHeight(tmpStepsVerticalGap);
       	}
    	if(selectedStepNumDotSepArray.length > 1)
    	{
    		newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepNum.substring(2, selectedStepNum.length())+ "." + selectedStepMaxIndex;
    	}
    	else
    	{
    		if(selectedStepChildrenCount != 0)
    		{
    			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepMaxIndex;
    		}
    		else
    		{
    			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "";	
    		}
    	}     

    	tmpStepsVerticalGap = newStepsRowPro.getRowHeight();
    	selectedStepChildrenCount = selectedStepChildrenCount + 1;
    	selectedStepMaxIndex = selectedStepMaxIndex + 1;
    	selectedStepProp.setStepChildrenCount(selectedStepChildrenCount);
    	selectedStepProp.setStepMaxIndex(selectedStepMaxIndex);
       	
    	StepProperties newStepPro = new StepProperties();
    	VBox newStepComboBox = new VBox();
    	HBox newStepNameBox = new HBox();
    	Label newStepNameLabel = new Label();
    	HBox newStepsSubStepsHBox = new HBox();

        Pane newStepsHierarchyContainerPane = new Pane();
        HBox siblingContainerBoxWithNewStepsName = new HBox();
    	double yCoordinateOfSelectedStep = selectedStepNode.getLayoutY();
    	double xCoordinateOfSelectedStep = selectedStepNode.getLayoutX();
    	if(selectedStepId.equals("START-0"))
    	{
    		xCoordinateOfSelectedStep = selectedStepNode.getLayoutX() - 20;
    	}
    	double selectedStepBoxHeight = selectedStepNode.getHeight();
    	double selectedStepBoxWidth = selectedStepNode.getWidth();
    	newStepsId = "Step-" + newlyAddedStepsDotSepNum;
    	if(!manualStepEntry)
    	{
    		newStepNameLabel.setText(newStepsId);
        	newStepPro.setStepDisplayName(newStepsId);        	
        	stepNameAndItsId.put(newStepsId, newStepsId);
    	}
    	else
    	{
    		newStepNameLabel.setText(stepNameToDisplay);
    		newStepPro.setStepDisplayName(stepNameToDisplay);
    		stepNameAndItsId.put(stepNameToDisplay,newStepsId);
    	}
    	newStepPro.setStepName(newStepsId);
    	newStepNameBox.getChildren().add(newStepNameLabel);
    	newStepComboBox.getChildren().addAll(newStepNameBox,newStepsSubStepsHBox);
    	newStepNameBox.setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 20 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020);    -fx-background-radius: 5.5 5.5 0 0;    -fx-background-insets: 0, 1;"); 
    	newStepNameLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
    	newStepsSubStepsHBox.setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-background-color:white");
    	newStepsSubStepsHBox.setPrefHeight(55);
    	newStepsSubStepsHBox.setPrefWidth(120);
    	newStepNameBox.setPrefHeight(20);
    	newStepNameBox.setPrefWidth(120);
    	double xPosOfSelectedSibCon = 0;
    	double yPosOfSelectedSibCon = 0;             	
    	xPosOfSelectedSibCon = xCoordinateOfSelectedStep ;
    	yPosOfSelectedSibCon = yCoordinateOfSelectedStep + selectedStepBoxHeight + tmpStepsVerticalGap;
        sibContainer.setLayoutX(xPosOfSelectedSibCon);
        sibContainer.setLayoutY(yPosOfSelectedSibCon);
        String sibSpacing = "-fx-spacing:"+ stepsHorizontalGap;
        sibContainer.setStyle(sibSpacing);
        
        List<Pane> childList = selectedStepProp.getStepChildrenList();
        
        int connectorAdjustmentFactor=0;
        if(selectedStepChildrenCount != 1)
          {
	       connectorAdjustmentFactor = 60;
          }

        double startpointX = 0;
    	double startpointY = 0;
    	double midPoint1X = 0;
    	double midPoint1Y = 0;
    	double midPoint2X = 0;
    	double midPoint2Y = 0;
    	double EndpointX = 0;
    	double EndpointY = 0;

        if(selectedStepContainer.getId().equals("START-0"))
        {
    	 startpointX = xCoordinateOfSelectedStep  + selectedStepBoxWidth/2 + 19;
    	 startpointY = yCoordinateOfSelectedStep  + selectedStepBoxHeight;
    	 midPoint1X = startpointX;
    	 midPoint1Y = startpointY + 25;
    	 EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() + 120 + connectorAdjustmentFactor;
    	 EndpointY = startpointY + tmpStepsVerticalGap;
    	 midPoint2X = EndpointX;
    	 midPoint2Y = midPoint1Y;
        }
        else
        {
       	 startpointX = xCoordinateOfSelectedStep  + selectedStepBoxWidth/2;
       	 startpointY = yCoordinateOfSelectedStep  + selectedStepBoxHeight ;
       	 midPoint1X = startpointX;
       	 midPoint1Y = startpointY + (stepsVerticalGap/2);  
       	 EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() + 60 + connectorAdjustmentFactor;
    	 EndpointY = startpointY + tmpStepsVerticalGap;
    	 midPoint2X = EndpointX;
    	 midPoint2Y = midPoint1Y;                	
        }
        
		Path regularConnectPath = new Path();
		
		MoveTo moveTo = new MoveTo();
		moveTo.setX(startpointX);
		moveTo.setY(startpointY); 
		
		LineTo lineTo1 = new LineTo();
		lineTo1.setX(midPoint1X);
		if(selectedStepChildrenCount == 1)
		{
	    	lineTo1.setY(midPoint1Y);
		}
		else
		{
			lineTo1.setY(midPoint1Y - curveRadius);
		}

	    QuadCurveTo quadTo1 = new QuadCurveTo();
	    quadTo1.setControlX(midPoint1X);
	    quadTo1.setControlY(midPoint1Y);
    	quadTo1.setX(midPoint1X + curveRadius);
	    quadTo1.setY(midPoint1Y);
	    
		LineTo lineTo2 = new LineTo();
		if(selectedStepChildrenCount == 1)
		{
			lineTo2.setX(midPoint2X);
		}
		else
		{
			lineTo2.setX(midPoint2X - curveRadius);
		}
		lineTo2.setY(midPoint2Y);
		
	    QuadCurveTo quadTo2 = new QuadCurveTo();
	    quadTo2.setControlX(midPoint2X);
	    quadTo2.setControlY(midPoint2Y);
    	quadTo2.setX(midPoint2X);
	    quadTo2.setY(midPoint2Y + curveRadius);
	    
		LineTo lineTo3 = new LineTo();
		lineTo3.setX(EndpointX);
		lineTo3.setY(EndpointY - 1);
		
		LineTo lineTo4 = new LineTo();
		lineTo4.setX(EndpointX - 3);
		lineTo4.setY(EndpointY - 6);
		
		LineTo lineTo5 = new LineTo();
		lineTo5.setX(EndpointX);
		lineTo5.setY(EndpointY - 1);
		
		LineTo lineTo6 = new LineTo();
		lineTo6.setX(EndpointX + 3);
		lineTo6.setY(EndpointY - 6);		
		
		if(selectedStepChildrenCount == 1)
		{
			regularConnectPath.getElements().addAll(moveTo,lineTo3,lineTo4,lineTo5,lineTo6);
		}
		else
		{
			regularConnectPath.getElements().addAll(moveTo,lineTo1,quadTo1,lineTo2,quadTo2,lineTo3,lineTo4,lineTo5,lineTo6);
		}
		
		regularConnectPath.setStrokeWidth(1.5);
		//regularConnectPath.setStyle("-fx-padding: 2 2 2 2");
		newStepsHierarchyContainerPane.getChildren().add(newStepComboBox);
        sibContainer.getChildren().add(newStepsHierarchyContainerPane);
        newStepComboBox.setLayoutX(0);
        newStepComboBox.setLayoutY(0);

        if(!selectedStepContainer.getChildren().contains(sibContainer))
        {
        	selectedStepContainer.getChildren().add(sibContainer);	
        }
            	
        selectedStepContainer.getChildren().addAll(regularConnectPath); //stepNavigation
        newStepComboBox.setId(newStepsId +"_"+selectedStepMaxIndex);
        newStepsHierarchyContainerPane.setId(newStepsId +"_"+selectedStepMaxIndex);
        siblingContainerBoxWithNewStepsName.setId(newStepsId);
 
        newStepPro.setStepChildrenCount(0);
        newStepPro.setStepMaxIndex(0);

    	List<String> updatedRowMemberList = new ArrayList();
        if(newStepsRowPro.getRowMember() == null)
        {
        	updatedRowMemberList.add(newStepsId); 
        	newStepsRowPro.setRowMember(updatedRowMemberList);
        }
        else
        {
        	updatedRowMemberList = newStepsRowPro.getRowMember();
        	updatedRowMemberList.add(newStepsId); 
        	newStepsRowPro.setRowMember(updatedRowMemberList);
        }

        newStepPro.setSiblingHBoxOfStep(siblingContainerBoxWithNewStepsName);
        newStepPro.setStepNameAndModuleVBox(newStepComboBox);
        newStepPro.setStepPane(newStepsHierarchyContainerPane);
        newStepPro.setParentStep(selectedStepContainer);
        
        String pathId = selectedStepId + "_" + newStepsId; 
        regularConnectPath.setId(pathId);
        
		PathProperties curConnectorPath = new PathProperties();		
		curConnectorPath.setTotalElmInPath(regularConnectPath.getElements().size());
		curConnectorPath.setChildStep(newStepsId);
		curConnectorPath.setParentStep(selectedStepId);
		curConnectorPath.setPath(regularConnectPath);
		curConnectorPath.setPathCondition(null);
		curConnectorPath.setPathName(pathId);
		curConnectorPath.setPathContainer(selectedStepContainer);
		
        stepsConnectPath.put(pathId,regularConnectPath);
        
        List<Path> listOfPath = newStepPro.getStepIncomingPaths();
        if (listOfPath == null)
        {
        newStepPro.setStepIncomingPaths(listOfPath = new ArrayList<Path>());
        }
        listOfPath.add(regularConnectPath);
        newStepPro.setStepIncomingPaths(listOfPath);
        
        listOfPath = selectedStepProp.getStepOutgoingPaths();
        if (listOfPath == null)
        {
        	selectedStepProp.setStepOutgoingPaths(listOfPath = new ArrayList<Path>());
        }
        listOfPath.add(regularConnectPath);
        selectedStepProp.setStepOutgoingPaths(listOfPath);

        if (childList == null)
        {
        	selectedStepProp.setStepChildrenList(childList = new ArrayList<Pane>());
        }
        childList.add(newStepsHierarchyContainerPane);               
        selectedStepProp.setStepChildrenList(childList); 
        
        rowAndItsProperties.put(newlyAddedStepRowNum,newStepsRowPro);
        stepIdAndItsProperties.put(newStepsId, newStepPro);
        pathAndItsProperties.put(pathId,curConnectorPath);
        
        newStepComboBox.setEffect(new DropShadow()); 
        newStepComboBox.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
   		selectedStepNode.setEffect(null);

   		if(selectedStepNode != startpane)
    	{
    		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
    	}
    	else
    	{
    		startpane.setStyle("-fx-background-color: linear-gradient(#32CD32, #00FF00);-fx-border-width:1; -fx-border-color:#6B8E23;-fx-background-radius: 10.0;-fx-border-radius:6 6 6 6;");
    	}
    	selectedStepNode = newStepComboBox; 
    	
    	//--------------------------------------------------------------------
    	regularConnectPath.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	if(selectedPath != null)
            	{
            	 //selectedPath.setStyle("-fx-stroke: black;");
            	 selectedPath.setEffect(null);
            	}
            	if(selectedStepNode != null)
            	{
            		selectedStepNode.setEffect(null);
            		if(selectedStepNode != startpane)
            		{
            		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            	}
            	selectedStepContainer.getChildren().remove(regularConnectPath);
            	selectedStepContainer.getChildren().add(regularConnectPath);
            	//regularConnectPath.setStyle("-fx-stroke: #e59866;");
            	regularConnectPath.setEffect(new DropShadow());
            	selectedPath = regularConnectPath;
            	selectedStepNode = null;
            	if(mouseEvent.getClickCount() == 2)
            	{
            		System.out.println("mouse Double Clicked");
            	}
            }
        });
    	//----------------------------------------------------------------
    	newStepComboBox.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	if(selectedPath != null)
            	{
            		//selectedPath.setStyle("-fx-stroke: black;");
            		selectedPath.setEffect(null);
            	}
              	if(selectedStepNode != null && selectedStepNode != newStepComboBox )
            	{
            		selectedStepNode.setEffect(null);
            		if(selectedStepNode != startpane)
            		{
            		selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            		else
            		{
            			startpane.setStyle("-fx-background-color: linear-gradient(#32CD32, #00FF00);-fx-border-width:1; -fx-border-color:#6B8E23;-fx-background-radius: 10.0;-fx-border-radius:6 6 6 6;");
            		}
            	}
            	newStepComboBox.setEffect(new DropShadow()); 
            	newStepComboBox.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 

            	selectedStepNode = newStepComboBox;  
            	selectedPath = null;
            	if(mouseEvent.getClickCount() == 2)
            	{
            		System.out.println("mouse Double Clicked");
            	}
            }                     
        });
        adjustStepNavigationForAdd(newStepsId);
	}
}