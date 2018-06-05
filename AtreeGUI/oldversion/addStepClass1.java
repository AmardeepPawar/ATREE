package stepDesigner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import application.popUpWindow;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class addStepClass1 {
	currentStatusIndicatorVariables curStatus = new currentStatusIndicatorVariables();
	stepHierarchyContainerPanes contPanes = new stepHierarchyContainerPanes();
	stepHierarchyMapping Mapping = new stepHierarchyMapping();
	StepProperties startStepProp = new StepProperties();
	declareSettingVariable setupVariables = new declareSettingVariable();
    setStartStep startStepSetup = new setStartStep();
	
	public void addChildStepMethod()
	{
		if(currentStatusIndicatorVariables.selectedStepNode != null)
    	{
         if(!declareSettingVariable.manualStepEntry)
         {
        	 functionToAddNewStep("THISSTEPNAMEISNOTALLOWED","Auto");                
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
        
        getStepNameStage.setScene(stepNameScene);
        getStepNameStage.getIcons().add(new Image("icons//ATree.png"));
        getStepNameStage.initModality(Modality.APPLICATION_MODAL);
        getStepNameStage.show();

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	
            	if(currentStatusIndicatorVariables.selectedStepNode != null)
            	{
                    
                	String StepName = stepNameTextField.getText();
                	boolean stringmatch = Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]*", StepName);
                	if(stringmatch)
                	{
                	 if(!stepHierarchyMapping.stepNameAndItsId.containsKey(StepName))
                	 {
                	  functionToAddNewStep(StepName,"Manual"); 
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
    
    public String getStepNumber(String stepId)
    {
        String[] indexString = stepId.split("-");
    	String index = indexString[1].trim();
    	return index;
    }
	
	void functionToAddNewStep(String stepNameToDisplay, String typeOfInput)
	{
    	//String selectedStepIdarry[] = currentStatusIndicatorVariables.selectedStepNode.getId().split("_");
    	currentStatusIndicatorVariables.selectedStepId = currentStatusIndicatorVariables.selectedStepNode.getId();//selectedStepIdarry[0];
    	String selectedStepNum = getStepNumber(currentStatusIndicatorVariables.selectedStepId);
    	StepProperties selectedStepProp = new StepProperties();
    	selectedStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(currentStatusIndicatorVariables.selectedStepId);
        int selectedStepChildrenCount = selectedStepProp.getStepChildrenCount();
    	int selectedStepChildMaxIndexOrg = selectedStepProp.getStepChildMaxIndex();
    	int selectedStepChildMaxIndex = selectedStepChildMaxIndexOrg;
    	double selectedStepBoxHeight = selectedStepProp.getStepHeight(); //currentStatusIndicatorVariables.selectedStepNode.getHeight(); // //
    	double selectedStepBoxWidth = selectedStepProp.getStepWidth();
    	int tmpMaxIndexForFileSteps = 0;
    	String fileStepId = null;
    	double selectedStepXPos = selectedStepProp.getxPos();
    	double selectedStepYPos = selectedStepProp.getyPos();
    	//System.out.println(selectedStepXPos);
    	double newStepXPos = 0;
    	double newStepYPos = 0;

    	if(typeOfInput.equals("file"))
    	{	
    		String selectedStepIdNamearry[] = stepNameToDisplay.split("\\|");
    		fileStepId = selectedStepIdNamearry[0];
    		stepNameToDisplay = selectedStepIdNamearry[1];
    		String selectedStepDotSeparatedarry[] = fileStepId.split("\\.");
    		int lengthOfArray = selectedStepDotSeparatedarry.length;
    		if(lengthOfArray > 1)
    		{
    		  tmpMaxIndexForFileSteps = Integer.parseInt(selectedStepDotSeparatedarry[lengthOfArray - 1]);
    		}   
    		selectedStepChildMaxIndex = tmpMaxIndexForFileSteps;
    	}  
    	Pane selectedStepContainer = selectedStepProp.getStepPane(); 
    	HBox sibContainer = selectedStepProp.getSiblingHBoxOfStep();
    	String newlyAddedStepsDotSepNum = null;
        String newStepsId;
    	double tmpStepsVerticalGap = declareSettingVariable.stepsVerticalGap;
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

    	if(stepHierarchyMapping.rowAndItsProperties.containsKey(newlyAddedStepRowNum))
    	{
    		newStepsRowPro = stepHierarchyMapping.rowAndItsProperties.get(newlyAddedStepRowNum);
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
    	
    	if(typeOfInput.equals("file"))
    	{	
    		String getDotSepNumArray[] = fileStepId.split("-");
    		newlyAddedStepsDotSepNum = getDotSepNumArray[1];
    	}
    	else if(selectedStepNumDotSepArray.length > 1)
    	{
    		newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepNum.substring(2, selectedStepNum.length())+ "." + selectedStepChildMaxIndex;
    	}
    	else
    	{
    		if(selectedStepChildrenCount != 0)
    		{
    			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepChildMaxIndex;
    		}
    		else
    		{
    			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "";	
    		}
    	}     
    	tmpStepsVerticalGap = newStepsRowPro.getRowHeight();
   	    newStepYPos = selectedStepYPos + selectedStepBoxHeight + tmpStepsVerticalGap;
    	if(selectedStepChildrenCount == 0)
    	{
    		newStepXPos = selectedStepXPos;    		
    	}
    	else
    	{
    		newStepXPos = selectedStepXPos + selectedStepProp.getSiblingHBoxWidth() + declareSettingVariable.stepsHorizontalGap;// + selectedStepBoxWidth + declareSettingVariable.stepsHorizontalGap;
    	}
    	//System.out.println(newStepXPos + "=" + selectedStepChildrenCount + "*(" + selectedStepBoxWidth + "+" + declareSettingVariable.stepsHorizontalGap);
    	selectedStepChildrenCount = selectedStepChildrenCount + 1;
    	selectedStepChildMaxIndex = selectedStepChildMaxIndex + 1;
    	
    	selectedStepProp.setStepChildrenCount(selectedStepChildrenCount);
    	if(selectedStepChildMaxIndex >= selectedStepChildMaxIndexOrg)
    	{
    	 selectedStepProp.setStepChildMaxIndex(selectedStepChildMaxIndex);
    	}
       	
    	StepProperties newStepPro = new StepProperties();
    	VBox newStepComboBox = new VBox();
    	HBox newStepNameBox = new HBox();
    	Label newStepNameLabel = new Label();
    	HBox newStepsSubStepsHBox = new HBox();
    	ScrollPane newStepScrollPane = new ScrollPane();
    	
    	newStepScrollPane.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        event.acceptTransferModes(TransferMode.COPY);   		        
		        event.consume();
		    }
		});
    	
    	newStepScrollPane.setOnDragDropped(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        Dragboard db = event.getDragboard();
		        String moduleId = db.getString();
		        boolean success = false;
		        if (db.hasString()) {
		          ImageView folderIcon = new ImageView(new Image("file:///C:/Users/ACER/workspace/AtreeGUI/bin/moduleIcons/" + moduleId));
		  		  folderIcon.setFitWidth(35);
		  		  folderIcon.setFitHeight(35);
		  		  //newStepsSubStepsHBox.getChildren().add(folderIcon);
		  		  newStepScrollPane.setContent(folderIcon);
		          success = true;
		        }
		        event.setDropCompleted(success);			        
		        event.consume();
		     }
		});
    	
    	//newStepsSubStepsHBox.setStyle("-fx-spacing:5");

        Pane newStepsHierarchyContainerPane = new Pane();
        HBox siblingContainerBoxWithNewStepsName = new HBox();
    	double yCoordinateOfSelectedStep = currentStatusIndicatorVariables.selectedStepNode.getLayoutY();
    	double xCoordinateOfSelectedStep = currentStatusIndicatorVariables.selectedStepNode.getLayoutX();

    	if(currentStatusIndicatorVariables.selectedStepId.equals("START-0"))
    	{
    		xCoordinateOfSelectedStep = currentStatusIndicatorVariables.selectedStepNode.getLayoutX() - 20;
    	}
    	newStepsId = "Step-" + newlyAddedStepsDotSepNum;
    	
    	if(!declareSettingVariable.manualStepEntry && !typeOfInput.equals("file"))
    	{
    		newStepNameLabel.setText(newStepsId);
        	newStepPro.setStepDisplayName(newStepsId);        	
        	stepHierarchyMapping.stepNameAndItsId.put(newStepsId, newStepsId);
    	}
    	else
    	{
    		newStepNameLabel.setText(stepNameToDisplay);
    		newStepPro.setStepDisplayName(stepNameToDisplay);
    		stepHierarchyMapping.stepNameAndItsId.put(stepNameToDisplay,newStepsId);
    	}
    	newStepPro.setStepName(newStepsId);
    	newStepPro.setSiblingHBoxWidth(0);
    	newStepPro.setxPos(newStepXPos);
    	newStepPro.setyPos(newStepYPos);
    	newStepNameBox.getChildren().add(newStepNameLabel);
    	//newStepScrollPane.setContent(newStepsSubStepsHBox);
    	newStepsSubStepsHBox.getChildren().add(newStepScrollPane);
    	newStepComboBox.getChildren().addAll(newStepNameBox,newStepsSubStepsHBox);
    	newStepNameBox.setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 20 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020);    -fx-background-radius: 5.5 5.5 0 0;    -fx-background-insets: 0, 1;"); 
    	newStepNameLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
    	newStepScrollPane.setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-background-color:white");
    	newStepScrollPane.setPrefHeight(51);
    	newStepScrollPane.setPrefWidth(116);
    	newStepsSubStepsHBox.setPrefHeight(55);
    	newStepsSubStepsHBox.setPrefWidth(120);
    	newStepsSubStepsHBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(newStepsSubStepsHBox, Priority.ALWAYS);
    	newStepNameBox.setPrefHeight(20);
    	newStepNameBox.setPrefWidth(120);
    	double xPosOfSelectedSibCon = 0;
    	double yPosOfSelectedSibCon = 0;  
    	newStepPro.setStepHeight(82);
    	newStepPro.setStepWidth(120);
    	xPosOfSelectedSibCon = xCoordinateOfSelectedStep;
    	yPosOfSelectedSibCon = yCoordinateOfSelectedStep + selectedStepBoxHeight + tmpStepsVerticalGap;
        sibContainer.setLayoutX(xPosOfSelectedSibCon);
        sibContainer.setLayoutY(yPosOfSelectedSibCon);
        String sibSpacing = "-fx-spacing:"+ declareSettingVariable.stepsHorizontalGap;
        sibContainer.setStyle(sibSpacing);
        
        List<Pane> childList = selectedStepProp.getStepChildrenList();
        
        int connectorAdjustmentFactor=0;
        if(selectedStepChildrenCount != 1)
          {
	       connectorAdjustmentFactor = (int) declareSettingVariable.stepsHorizontalGap;
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
    	 startpointX = xCoordinateOfSelectedStep  + selectedStepBoxWidth/2 + 20;
    	 startpointY = yCoordinateOfSelectedStep  + selectedStepBoxHeight;    	 
    	 midPoint1X = startpointX;
    	 midPoint1Y = startpointY + 25;
    	 //EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() + 120 + connectorAdjustmentFactor;
    	 EndpointX = selectedStepProp.getSiblingHBoxWidth() + 120 + connectorAdjustmentFactor;
    	 //selectedStepProp.setSiblingHBoxWidth(selectedStepProp.getSiblingHBoxWidth() + 120 + connectorAdjustmentFactor);
    	 EndpointY = startpointY + tmpStepsVerticalGap;
    	 midPoint2X = EndpointX;
    	 midPoint2Y = midPoint1Y;    	 
        }
        else
        {
       	 startpointX = xCoordinateOfSelectedStep  + selectedStepBoxWidth/2;
       	 startpointY = yCoordinateOfSelectedStep  + selectedStepBoxHeight ;
       	 midPoint1X = startpointX;
       	 midPoint1Y = startpointY + (declareSettingVariable.stepsVerticalGap/2);  
       	 //EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() + 60 + connectorAdjustmentFactor;
         EndpointX = selectedStepProp.getSiblingHBoxWidth() + 60 + connectorAdjustmentFactor;         
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
			lineTo1.setY(midPoint1Y - declareSettingVariable.curveRadius);
		}

	    QuadCurveTo quadTo1 = new QuadCurveTo();
	    quadTo1.setControlX(midPoint1X);
	    quadTo1.setControlY(midPoint1Y);
    	quadTo1.setX(midPoint1X + declareSettingVariable.curveRadius);
	    quadTo1.setY(midPoint1Y);
	    
		LineTo lineTo2 = new LineTo();
		if(selectedStepChildrenCount == 1)
		{
			lineTo2.setX(midPoint2X);
		}
		else
		{
			lineTo2.setX(midPoint2X - declareSettingVariable.curveRadius);
		}
		lineTo2.setY(midPoint2Y);
		
	    QuadCurveTo quadTo2 = new QuadCurveTo();
	    quadTo2.setControlX(midPoint2X);
	    quadTo2.setControlY(midPoint2Y);
    	quadTo2.setX(midPoint2X);
	    quadTo2.setY(midPoint2Y + declareSettingVariable.curveRadius);
	    
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
		newStepsHierarchyContainerPane.getChildren().add(newStepComboBox);
        sibContainer.getChildren().add(newStepsHierarchyContainerPane);
        newStepComboBox.setLayoutX(0);
        newStepComboBox.setLayoutY(0);

        if(!selectedStepContainer.getChildren().contains(sibContainer))
        {
        	selectedStepContainer.getChildren().add(sibContainer);	
        }
            	
        selectedStepContainer.getChildren().addAll(regularConnectPath); //stepNavigation
        //newStepComboBox.setId(newStepsId +"_"+selectedStepChildMaxIndex);
        //newStepsHierarchyContainerPane.setId(newStepsId +"_"+selectedStepChildMaxIndex);
        newStepComboBox.setId(newStepsId);
        newStepsHierarchyContainerPane.setId(newStepsId);
        siblingContainerBoxWithNewStepsName.setId(newStepsId);
 
        newStepPro.setStepChildrenCount(0);
        newStepPro.setStepChildMaxIndex(0);
        newStepPro.setStepIndex(selectedStepChildMaxIndex);

    	List<String> updatedRowMemberList = new ArrayList<String>();
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
        
        String pathId = currentStatusIndicatorVariables.selectedStepId + "_" + newStepsId; 
        regularConnectPath.setId(pathId);
        
		PathProperties curConnectorPath = new PathProperties();		
		curConnectorPath.setTotalElmInPath(regularConnectPath.getElements().size());
		curConnectorPath.setChildStep(newStepsId);
		curConnectorPath.setParentStep(currentStatusIndicatorVariables.selectedStepId);
		curConnectorPath.setPath(regularConnectPath);
		curConnectorPath.setPathCondition(null);
		curConnectorPath.setPathName(pathId);
		curConnectorPath.setPathContainer(selectedStepContainer);
		
		stepHierarchyMapping.stepsConnectPath.put(pathId,regularConnectPath);

        
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
        
        stepHierarchyMapping.rowAndItsProperties.put(newlyAddedStepRowNum,newStepsRowPro);
        stepHierarchyMapping.stepIdAndItsProperties.put(newStepsId, newStepPro);
        stepHierarchyMapping.pathAndItsProperties.put(pathId,curConnectorPath);
        
        if(!typeOfInput.equals("file"))
        {
         newStepComboBox.setEffect(new DropShadow()); 
         newStepComboBox.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
        }
         currentStatusIndicatorVariables.selectedStepNode.setEffect(null);        
        
   		if(currentStatusIndicatorVariables.selectedStepNode != setStartStep.startpane)
    	{
   			currentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
    	}
    	else
    	{
    		setStartStep.startpane.setStyle("-fx-background-color: linear-gradient(#32CD32, #00FF00);-fx-border-width:1; -fx-border-color:#6B8E23;-fx-background-radius: 10.0;-fx-border-radius:6 6 6 6;");
    	}
   		
   		if(!typeOfInput.equals("file"))
   		{
   		  testCaseFileReaderWriter fileWrtObj = new testCaseFileReaderWriter();
   		  try {
   			if(!declareSettingVariable.manualStepEntry)
   	    	{    	
   				fileWrtObj.writeTestCaseXMLFile(newStepsId, newStepsId);
   	    	}
   	    	else
   	    	{
   	    		fileWrtObj.writeTestCaseXMLFile(stepNameToDisplay,newStepsId);
   	    	}
		  } catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
   		}
   		
   		/*if(!typeOfInput.equals("file"))
   		{
   		  testCaseFileReaderWriter fileWrtObj = new testCaseFileReaderWriter();
   		  try {
   			if(!declareSettingVariable.manualStepEntry)
   	    	{    	
   				fileWrtObj.writeTestCaseXMLFile(newStepsId, newStepsId);
   	    	}
   	    	else
   	    	{
   	    		fileWrtObj.writeTestCaseXMLFile(stepNameToDisplay,newStepsId);
   	    	}
		  } catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
   		}*/
   		currentStatusIndicatorVariables.selectedStepNode = newStepComboBox; 
    	
    	//--------------------------------------------------------------------
    	regularConnectPath.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	if(currentStatusIndicatorVariables.selectedPath != null)
            	{
            	 //selectedPath.setStyle("-fx-stroke: black;");
            		currentStatusIndicatorVariables.selectedPath.setEffect(null);
            	}
            	if(currentStatusIndicatorVariables.selectedStepNode != null)
            	{
            		currentStatusIndicatorVariables.selectedStepNode.setEffect(null);
            		if(currentStatusIndicatorVariables.selectedStepNode != setStartStep.startpane)
            		{
            			currentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            	}
            	selectedStepContainer.getChildren().remove(regularConnectPath);
            	selectedStepContainer.getChildren().add(regularConnectPath);
            	//regularConnectPath.setStyle("-fx-stroke: #e59866;");
            	regularConnectPath.setEffect(new DropShadow());
            	currentStatusIndicatorVariables.selectedPath = regularConnectPath;
            	currentStatusIndicatorVariables.selectedStepNode = null;
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
            	if(currentStatusIndicatorVariables.selectedPath != null)
            	{
            		//selectedPath.setStyle("-fx-stroke: black;");
            		currentStatusIndicatorVariables.selectedPath.setEffect(null);
            	}
              	if(currentStatusIndicatorVariables.selectedStepNode != null && currentStatusIndicatorVariables.selectedStepNode != newStepComboBox )
            	{
              		currentStatusIndicatorVariables.selectedStepNode.setEffect(null);
            		if(currentStatusIndicatorVariables.selectedStepNode != setStartStep.startpane)
            		{
            			currentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 
            		}
            		else
            		{
            			setStartStep.startpane.setStyle("-fx-background-color: linear-gradient(#32CD32, #00FF00);-fx-border-width:1; -fx-border-color:#6B8E23;-fx-background-radius: 10.0;-fx-border-radius:6 6 6 6;");
            		}
            	}
            	newStepComboBox.setEffect(new DropShadow()); 
            	newStepComboBox.getChildren().get(0).setStyle("-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;"); 

            	currentStatusIndicatorVariables.selectedStepNode = newStepComboBox;
            	currentStatusIndicatorVariables.selectedPath = null;
            	if(mouseEvent.getClickCount() == 2)
            	{
            		System.out.println("mouse Double Clicked");
            	}
            }                     
        });
       adjustStepNavigationForAdd(newStepsId);
       siblingHBoxWidthIncrement(newStepsId);
	}

	
	public void siblingHBoxWidthIncrement(String newlyAddedStepId)
    {
      String stepId = newlyAddedStepId;
  	  int childCount = 0;
  	  //int parentIndex = 0;
      StepProperties curStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(stepId);
      Pane parentStepCont = curStepProp.getParentStep();
      //String parentIdArry1[] = parentStepCont.getId().split("_");
  	  String parentId = parentStepCont.getId();//parentIdArry1[0];
  	  StepProperties parentStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(parentId);
  	  if (newlyAddedStepId.equals(stepId))
    	{
  		 childCount = parentStepProp.getStepChildrenCount();
    	}
  	  if(childCount == 1)
  	  {  		  
  		parentStepProp.setSiblingHBoxWidth(parentStepProp.getSiblingHBoxWidth() + 120);   		
  	  }
  	  if(childCount > 1)
  	  {
      while(!stepId.equals("START-0"))
       {
        curStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(stepId);
        parentStepCont = curStepProp.getParentStep();
    	//String parentIdArry[] = parentStepCont.getId().split("_");
    	parentId = parentStepCont.getId();//parentIdArry[0];
    	parentStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(parentId);
    	parentStepProp.setSiblingHBoxWidth(parentStepProp.getSiblingHBoxWidth() + 120 + 100);
    	stepId = parentId;
       }
  	  }
   	} 	
	
	public void adjustStepNavigationForAdd(String newlyAddedStepId)
	    {
	      String stepId = newlyAddedStepId;
	  	  int childCount = 0;
	  	  int parentIndex = 0;
	      while(!stepId.equals("START-0"))
	       {
	        StepProperties curStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(stepId);
	        Pane parentStepCont = curStepProp.getParentStep();
	    	//String parentIdArry[] = parentStepCont.getId().split("_");
	    	String parentId = parentStepCont.getId();//parentIdArry[0];
	    	StepProperties parentStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(parentId);
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
	    		//String childIdarry[] = childpane.getId().split("_");
	    	   String childId = childpane.getId();//childIdarry[0];   
	    	   StepProperties childStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(childId);
	    	   int nodeIndex = childStepProp.getStepIndex();//Integer.parseInt(childIdarry[1]);
	    		//System.out.println(childStepProp.getStepIndex() + " " + nodeIndex);
	    	   if(nodeIndex > parentIndex)
	    	   {
	    		stepPath = (Path) stepHierarchyMapping.stepsConnectPath.get(parentId+"_"+childId);
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
	    		
	    		line2XCo = line2XCo + 120.0 + declareSettingVariable.stepsHorizontalGap;
	    		quad2ControlX = quad2ControlX + 120.0 + declareSettingVariable.stepsHorizontalGap;
	    		quad2X = quad2X + 120.0 + declareSettingVariable.stepsHorizontalGap;
	    		line4XCo = line4XCo + 120.0 + declareSettingVariable.stepsHorizontalGap;
	    		line6XCo = line6XCo + 120.0 + declareSettingVariable.stepsHorizontalGap;
	    		line2.setX(line2XCo);
	    		line3.setX(line2XCo + declareSettingVariable.curveRadius);
	    		quad2.setControlX(quad2ControlX);
	    		quad2.setX(quad2X);
	    		line4.setX(line4XCo);
	    		line6.setX(line6XCo);
	    		line5.setX(line2XCo + declareSettingVariable.curveRadius); 		
	    				
	    		stepHierarchyMapping.stepsConnectPath.put(parentId+"_"+childId, stepPath);
	    		parentStepCont.getChildren().remove(stepPath);
	    		parentStepCont.getChildren().add(stepPath);
	    		stepHierarchyMapping.tmpColumnNumberOccupied.clear();
	        	// This function (adjustConnectLinesSubStepsForAdd) is used to adjust connection lines(connection lines to show extra dependencies) between two step.  
	        	adjustConnectLinesOfSubStepsForAddAction(childId);
	        	//stepHierarchyMapping.columnNumberOccupied.clear();
	        	stepHierarchyMapping.columnNumberOccupied.putAll(stepHierarchyMapping.tmpColumnNumberOccupied);
	    		}
	    	}
	       }
	    	stepId = parentId;
	    	if(!parentId.equals("START-0"))
	    	{
	    	    //parentIndex = Integer.parseInt(parentIdArry[1].trim());
	    		parentIndex = parentStepProp.getStepIndex();
	    	}
	      }
	   	} 
	   
	   public void adjustConnectLinesOfSubStepsForAddAction(String id)
	    {
	    	StepProperties childStepProp = stepHierarchyMapping.stepIdAndItsProperties.get(id);
	    	double newXPos = childStepProp.getxPos() + childStepProp.getStepWidth() + declareSettingVariable.stepsHorizontalGap; 
	    	//System.out.println(childStepProp.getStepDisplayName() + " " + childStepProp.getxPos() + " " + newXPos);
	    	childStepProp.setxPos(newXPos);
	    	if(stepHierarchyMapping.sourceStepAndListOfPath.get(id) != null)
	    	{
	    	 List<Path> listOfSourcePath = stepHierarchyMapping.sourceStepAndListOfPath.get(id);
	    	 for(Path cutSourcePath: listOfSourcePath)
	    	 {
	    		    int pathEleCnt = cutSourcePath.getElements().size();
	    		    MoveTo startPoint = (MoveTo) cutSourcePath.getElements().get(0);
	    	   		LineTo line1 = (LineTo) cutSourcePath.getElements().get(1);
	         		QuadCurveTo quad2 = (QuadCurveTo) cutSourcePath.getElements().get(2);

	         		startPoint.setX(startPoint.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		line1.setX(line1.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		quad2.setControlX(quad2.getControlX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		quad2.setX(quad2.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap ); 
	         		//System.out.println(pathEleCnt);
	         		if(pathEleCnt > 10)
	         		{
	        	   	  LineTo line3 = (LineTo) cutSourcePath.getElements().get(3);
	             	  QuadCurveTo quad4 = (QuadCurveTo) cutSourcePath.getElements().get(4);
	        	   	  LineTo line5 = (LineTo) cutSourcePath.getElements().get(5);
	             	  QuadCurveTo quad6 = (QuadCurveTo) cutSourcePath.getElements().get(6);
	             	  LineTo line7 = (LineTo) cutSourcePath.getElements().get(7);
	             	  double colIndex = line3.getX();
	         		  line3.setX(line3.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		  quad4.setControlX(quad4.getControlX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		  quad4.setX(quad4.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap );
	         		  //System.out.println(id + " " + line7.getX() + " " + line5.getX() + " " + quad6.getX());
	         		  if(line7.getX() > line5.getX() &&  line7.getX() < (line5.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap))
	         		  {
	         			 quad6.setX(quad6.getX() - (2 * declareSettingVariable.curveRadius)); 
	         			 line7.setX(line7.getX() + (2 * declareSettingVariable.curveRadius)); 
	         			 //System.out.println(id + " " + line7.getX() + " " + line5.getX());
	         		  }
	         		  line5.setX(line5.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		  quad6.setControlX(quad6.getControlX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	         		  quad6.setX(quad6.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap );
	         		  //System.out.println(id + " " + line7.getX() + " " + line5.getX() + " " + quad6.getX());

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
	        		  int newVerticalIndex = (int) (VerticalIndex + 120 + declareSettingVariable.stepsHorizontalGap);	        		  
	        		  //System.out.println(VerticalIndex + " " + newVerticalIndex);
	        		  stepHierarchyMapping.tmpColumnNumberOccupied.put(newVerticalIndex, stepId);      		 
	         	  }  		
	     	  }    	 
	    	}
	    
	    	if(stepHierarchyMapping.destinationStepAndListOfPath.get(id) != null)
	    	{
	     	 List<Path> listOfDestPath = stepHierarchyMapping.destinationStepAndListOfPath.get(id);
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

	   		    line1.setX(line1.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    line2.setX(line2.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    line3.setX(line3.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    line4.setX(line4.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    line5.setX(line5.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    line7.setX(line7.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	   		    quad6.setControlX(quad6.getControlX() + 120.0 + declareSettingVariable.stepsHorizontalGap);
	        	quad6.setX(quad6.getX() + 120.0 + declareSettingVariable.stepsHorizontalGap );
	        	}
	    	}
	    	int tmpCnt = childStepProp.getStepChildrenCount();
	    	if(tmpCnt > 0)
	    	{
	    	List<Pane> childPaneList = childStepProp.getStepChildrenList();
	    	 for(Pane childPane : childPaneList)
	    	 {
	     		//String idarry[] = childPane.getId().split("_");
	     		String childId = childPane.getId();//idarry[0];   
	     		adjustConnectLinesOfSubStepsForAddAction(childId);
	    	 }
	    	}       
	    }

}
