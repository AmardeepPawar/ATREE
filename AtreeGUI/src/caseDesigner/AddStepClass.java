package caseDesigner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import application.AtreeFolder;
import application.DisplayExceptions;
import application.ImageAndImageViews;
import application.KeyValueMapping;
import application.SelectedCase;
import application.beans.ModulePropertyBean;
import atreeInterfaces.ModuleSetup;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddStepClass {
	CurrentStatusIndicatorVariables curStatus = new CurrentStatusIndicatorVariables();
	StepHierarchyContainerPanes contPanes = new StepHierarchyContainerPanes();
	StepHierarchyMapping Mapping = new StepHierarchyMapping();
	StepProperties startStepProp = new StepProperties();
	DeclareSettingVariable setupVariables = new DeclareSettingVariable();
	SetStartStep startStepSetup = new SetStartStep();
	public static List<String> modNotFnd = new ArrayList<String>();
	Tooltip modNtFndTip = new Tooltip();

	public void addChildStepMethod() {
		if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
			if (!DeclareSettingVariable.manualStepEntry) {
				// functionToAddNewStep("THISSTEPNAMEISNOTALLOWED", "Auto");
				addStepAndCallCreateHirarchy("Auto");
			} else {
				getStepName();
			}
		} else {
			DisplayExceptions popUpWinObj1 = DisplayExceptions.getInstance();
			popUpWinObj1.setException("Please select the step to which new step need to be added.");
		}
	}

	void addStepAndCallCreateHirarchy(String stepNameToDisplay) {
		String newStepsId = null;
		CurrentStatusIndicatorVariables.selectedStepId = CurrentStatusIndicatorVariables.selectedStepNode.getId();// selectedStepIdarry[0];
		String selectedStepNum = getStepNumber(CurrentStatusIndicatorVariables.selectedStepId);
		StepProperties selectedStepProp = StepHierarchyMapping.stepIdAndItsProperties
				.get(CurrentStatusIndicatorVariables.selectedStepId);
		int newlyAddedStepRowNum = 0;
		String newlyAddedStepsDotSepNum = null;
		int selectedStepChildrenCount = selectedStepProp.getStepChildrenCount();
		int selectedStepChildMaxIndexOrg = selectedStepProp.getStepChildMaxIndex();
		int selectedStepChildMaxIndex = selectedStepChildMaxIndexOrg;
		String[] selectedStepNumDotSepArray = { "0" };
		if (!selectedStepNum.equals("0")) {
			selectedStepNumDotSepArray = selectedStepNum.split("\\.");
			newlyAddedStepRowNum = Integer.parseInt(selectedStepNumDotSepArray[0]) + 1;
		} else {
			newlyAddedStepRowNum = Integer.parseInt(selectedStepNum) + 1;
		}

		if (selectedStepNumDotSepArray.length > 1) {
			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "."
					+ selectedStepNum.substring(2, selectedStepNum.length()) + "." + selectedStepChildMaxIndex;
		} else {
			if (selectedStepChildrenCount != 0) {
				newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepChildMaxIndex;
			} else {
				newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "";
			}
		}
		newStepsId = "Step-" + newlyAddedStepsDotSepNum;
		TestCaseFileReaderWriter fileWrtObj = new TestCaseFileReaderWriter();
		try {
			if (!DeclareSettingVariable.manualStepEntry) {
				fileWrtObj.writeTestCaseXMLFile(newStepsId, newStepsId);
			} else {
				fileWrtObj.writeTestCaseXMLFile(stepNameToDisplay, newStepsId);
			}
			fileWrtObj.displayHierarchy();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StepProperties newStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(newStepsId);
		VBox newStepComboBox = newStepProp.getStepNameAndModuleVBox();

		newStepComboBox.setEffect(new DropShadow());
		newStepComboBox.getChildren().get(0).setStyle(
				"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
		CurrentStatusIndicatorVariables.selectedStepNode = newStepComboBox;
	}

	public void getStepName() {
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
		containerVBoxOfNameAndErrMsg.getChildren().addAll(stepNamePaneHBox, stepNameOKBtnPane, errorMessageHBox);
		Scene stepNameScene = new Scene(containerVBoxOfNameAndErrMsg, 300, 95);
		stepNamePaneHBox.getChildren().addAll(stepNameLabel, stepNameTextField);
		stepNameOKBtnPane.getChildren().addAll(okButton);
		getStepNameStage.setTitle("Step Name");

		getStepNameStage.setScene(stepNameScene);
		getStepNameStage.getIcons()
				.add(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//ATree.png"));
		getStepNameStage.initModality(Modality.APPLICATION_MODAL);
		getStepNameStage.show();

		okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {

				if (CurrentStatusIndicatorVariables.selectedStepNode != null) {

					String StepName = stepNameTextField.getText();
					boolean stringmatch = Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]*", StepName);
					if (stringmatch) {
						if (!StepHierarchyMapping.stepNameAndItsId.containsKey(StepName)) {

							// functionToAddNewStep(StepName, "Manual");
							addStepAndCallCreateHirarchy(StepName);
							Stage stage = (Stage) okButton.getScene().getWindow();
							stage.close();
						} else {
							errorMsg.setText("Step name already exist.");
							errorMsg.setStyle("-fx-text-fill:red;");
							errorMsg.setAlignment(Pos.CENTER);
							getStepNameStage.setHeight(170);
						}

					} else {
						errorMsg.setText(
								"* Step name must start with alphabet and only \"_\" and \"-\" are allowed characters in the name.");
						errorMsg.setStyle("-fx-text-fill:red;");
						errorMsg.setAlignment(Pos.CENTER);
						getStepNameStage.setHeight(170);
					}
				}
			};
		});
	}

	public String getStepNumber(String stepId) {
		String[] indexString = stepId.split("-");
		String index = indexString[1].trim();
		return index;
	}

	void functionToAddNewStep(String stepNameToDisplay, String typeOfInput) {
		CurrentStatusIndicatorVariables.selectedStepId = CurrentStatusIndicatorVariables.selectedStepNode.getId();// selectedStepIdarry[0];
		String selectedStepNum = getStepNumber(CurrentStatusIndicatorVariables.selectedStepId);
		StepProperties selectedStepProp = StepHierarchyMapping.stepIdAndItsProperties
				.get(CurrentStatusIndicatorVariables.selectedStepId);
		int selectedStepChildrenCount = selectedStepProp.getStepChildrenCount();
		int selectedStepChildMaxIndexOrg = selectedStepProp.getStepChildMaxIndex();
		int selectedStepChildMaxIndex = selectedStepChildMaxIndexOrg;
		double selectedStepBoxHeight = selectedStepProp.getStepHeight();

		int tmpMaxIndexForFileSteps = 0;
		String fileStepId = null;
		double selectedStepXPos = selectedStepProp.getxPos();
		double selectedStepYPos = selectedStepProp.getyPos();
		double newStepXPos = 0;
		double newStepYPos = 0;

		if (typeOfInput.equals("file")) {
			String selectedStepIdNameArry[] = stepNameToDisplay.split("\\|");
			fileStepId = selectedStepIdNameArry[0];
			stepNameToDisplay = selectedStepIdNameArry[1];
			String selectedStepDotSeparatedArry[] = fileStepId.split("\\.");
			int lengthOfArray = selectedStepDotSeparatedArry.length;
			if (lengthOfArray > 1) {
				tmpMaxIndexForFileSteps = Integer.parseInt(selectedStepDotSeparatedArry[lengthOfArray - 1]);
			}
			selectedStepChildMaxIndex = tmpMaxIndexForFileSteps;
		}
		Pane selectedStepContainer = selectedStepProp.getStepPane();
		HBox sibContainer = selectedStepProp.getSiblingHBoxOfStep();
		String newlyAddedStepsDotSepNum = null;
		String newStepsId;
		double tmpStepsVerticalGap = DeclareSettingVariable.stepsVerticalGap;
		int newlyAddedStepRowNum = 0;
		String[] selectedStepNumDotSepArray = { "0" };
		if (!selectedStepNum.equals("0")) {
			selectedStepNumDotSepArray = selectedStepNum.split("\\.");
			newlyAddedStepRowNum = Integer.parseInt(selectedStepNumDotSepArray[0]) + 1;
		} else {
			newlyAddedStepRowNum = Integer.parseInt(selectedStepNum) + 1;
		}
		if (typeOfInput.equals("file")) {
			String getDotSepNumArray[] = fileStepId.split("-");
			newlyAddedStepsDotSepNum = getDotSepNumArray[1];
		} else if (selectedStepNumDotSepArray.length > 1) {
			newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "."
					+ selectedStepNum.substring(2, selectedStepNum.length()) + "." + selectedStepChildMaxIndex;
		} else {
			if (selectedStepChildrenCount != 0) {
				newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "." + selectedStepChildMaxIndex;
			} else {
				newlyAddedStepsDotSepNum = newlyAddedStepRowNum + "";
			}
		}
		newStepsId = "Step-" + newlyAddedStepsDotSepNum;
		int newStepsRowMemberCnt = 1;
		RowProperties newStepsRowPro = new RowProperties();

		if (StepHierarchyMapping.rowAndItsProperties.containsKey(newlyAddedStepRowNum)) {
			newStepsRowPro = StepHierarchyMapping.rowAndItsProperties.get(newlyAddedStepRowNum);
			newStepsRowMemberCnt = newStepsRowPro.getTotalStepsForRow();
			newStepsRowMemberCnt = newStepsRowMemberCnt + 1;
			newStepsRowPro.setTotalStepsForRow(newStepsRowMemberCnt);
		} else {
			newStepsRowPro.setRowNum(newlyAddedStepRowNum);
			newStepsRowPro.setTotalStepsForRow(newStepsRowMemberCnt);
			newStepsRowPro.setRowHeight(tmpStepsVerticalGap);
		}

		tmpStepsVerticalGap = newStepsRowPro.getRowHeight();
		newStepYPos = selectedStepYPos + selectedStepBoxHeight + tmpStepsVerticalGap;
		if (selectedStepChildrenCount == 0) {
			newStepXPos = selectedStepXPos;
		} else {
			newStepXPos = selectedStepXPos + selectedStepProp.getSiblingHBoxWidth()
					+ DeclareSettingVariable.stepsHorizontalGap;
		}
		selectedStepChildrenCount = selectedStepChildrenCount + 1;
		selectedStepChildMaxIndex = selectedStepChildMaxIndex + 1;

		selectedStepProp.setStepChildrenCount(selectedStepChildrenCount);
		if (selectedStepChildMaxIndex >= selectedStepChildMaxIndexOrg) {
			selectedStepProp.setStepChildMaxIndex(selectedStepChildMaxIndex);
		}

		StepProperties newStepPro = new StepProperties();
		VBox newStepComboBox = new VBox();
		HBox newStepNameBox = new HBox();
		Label newStepNameLabel = new Label();
		HBox newStepsSubStepsHBox = new HBox();
		ScrollPane newStepScrollPane = new ScrollPane();

		newStepsSubStepsHBox.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.COPY);
				event.consume();
			}
		});

		newStepsSubStepsHBox.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				String moduleId = db.getString();
				boolean success = false;
				if (db.hasString()) {
					// TestCaseFileReaderWriter tsFileReadWrite= new
					// TestCaseFileReaderWriter();
					addModulesToStep(newStepsSubStepsHBox, moduleId);
					TestCaseFileReaderWriter.addModuleToStep(newStepsSubStepsHBox.getId(), moduleId);
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		// newStepsSubStepsHBox.setStyle("-fx-spacing:5");

		Pane newStepsHierarchyContainerPane = new Pane();
		HBox siblingContainerBoxWithNewStepsName = new HBox();
		double yCoordinateOfSelectedStep = CurrentStatusIndicatorVariables.selectedStepNode.getLayoutY();
		double xCoordinateOfSelectedStep = CurrentStatusIndicatorVariables.selectedStepNode.getLayoutX();

		if (CurrentStatusIndicatorVariables.selectedStepId.equals("START-0")) {
			xCoordinateOfSelectedStep = CurrentStatusIndicatorVariables.selectedStepNode.getLayoutX() - 20;
		}
		// newStepsId = "Step-" + newlyAddedStepsDotSepNum;
		if (!DeclareSettingVariable.manualStepEntry && !typeOfInput.equals("file")) {
			newStepNameLabel.setText(newStepsId);
			newStepPro.setStepName(newStepsId);
			StepHierarchyMapping.stepNameAndItsId.put(newStepsId, newStepsId);
		} else {
			newStepNameLabel.setText(stepNameToDisplay);
			newStepPro.setStepName(stepNameToDisplay);
			StepHierarchyMapping.stepNameAndItsId.put(stepNameToDisplay, newStepsId);
		}
		newStepPro.setStepId(newStepsId);
		newStepsSubStepsHBox.setId(newStepsId);
		newStepPro.setSiblingHBoxWidth(0);
		newStepPro.setxPos(newStepXPos);
		newStepPro.setyPos(newStepYPos);
		newStepNameBox.getChildren().add(newStepNameLabel);
		newStepScrollPane.setContent(newStepsSubStepsHBox);
		// newStepsSubStepsHBox.getChildren().add(newStepScrollPane);
		// newStepComboBox.getChildren().addAll(newStepNameBox,
		// newStepScrollPane);
		newStepComboBox.getChildren().addAll(newStepNameBox, newStepsSubStepsHBox);
		newStepNameBox.setStyle(
				"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 20 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020);    -fx-background-radius: 5.5 5.5 0 0;    -fx-background-insets: 0, 1;");
		newStepNameLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
		newStepsSubStepsHBox
				.setStyle("-fx-background-color:white;padding:10;-fx-border-width:2; -fx-border-color:#202020;");
		// newStepsSubStepsHBox.setStyle("-fx-border-width:2;
		// -fx-border-color:#202020;");
		newStepScrollPane.setPrefHeight(55);
		newStepScrollPane.setPrefWidth(120);
		newStepsSubStepsHBox.setPrefHeight(53);
		newStepsSubStepsHBox.setPrefWidth(114);
		newStepsSubStepsHBox.setAlignment(Pos.TOP_CENTER);
		newStepsSubStepsHBox.setPadding(new Insets(3, 0, 0, 0));
		// HBox.setHgrow(newStepsSubStepsHBox, Priority.ALWAYS);
		newStepScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
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
		String sibSpacing = "-fx-spacing:" + DeclareSettingVariable.stepsHorizontalGap;
		sibContainer.setStyle(sibSpacing);

		List<Pane> childList = selectedStepProp.getStepChildrenList();

		if (childList == null) {
			selectedStepProp.setStepChildrenList(childList = new ArrayList<Pane>());
		}

		newStepsHierarchyContainerPane.getChildren().add(newStepComboBox);
		sibContainer.getChildren().add(newStepsHierarchyContainerPane);
		newStepComboBox.setLayoutX(0);
		newStepComboBox.setLayoutY(0);

		if (!selectedStepContainer.getChildren().contains(sibContainer)) {
			selectedStepContainer.getChildren().add(sibContainer);
		}

		newStepComboBox.setId(newStepsId);
		newStepsHierarchyContainerPane.setId(newStepsId);
		siblingContainerBoxWithNewStepsName.setId(newStepsId);

		newStepPro.setStepChildrenCount(0);
		newStepPro.setStepChildMaxIndex(0);
		newStepPro.setStepIndex(selectedStepChildMaxIndex);

		List<String> updatedRowMemberList = new ArrayList<String>();
		if (newStepsRowPro.getRowMember() == null) {
			updatedRowMemberList.add(newStepsId);
			newStepsRowPro.setRowMember(updatedRowMemberList);
		} else {
			updatedRowMemberList = newStepsRowPro.getRowMember();
			updatedRowMemberList.add(newStepsId);
			newStepsRowPro.setRowMember(updatedRowMemberList);
		}

		newStepPro.setSiblingHBoxOfStep(siblingContainerBoxWithNewStepsName);
		newStepPro.setStepNameAndModuleVBox(newStepComboBox);
		newStepPro.setStepPane(newStepsHierarchyContainerPane);
		newStepPro.setParentStep(selectedStepContainer);
		childList.add(newStepsHierarchyContainerPane);
		selectedStepProp.setStepChildrenList(childList);

		StepHierarchyMapping.rowAndItsProperties.put(newlyAddedStepRowNum, newStepsRowPro);
		StepHierarchyMapping.stepIdAndItsProperties.put(newStepsId, newStepPro);

		CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);

		if (!typeOfInput.equals("file")) {
			TestCaseFileReaderWriter fileWrtObj = new TestCaseFileReaderWriter();
			try {
				if (!DeclareSettingVariable.manualStepEntry) {
					fileWrtObj.writeTestCaseXMLFile(newStepsId, newStepsId);
				} else {
					fileWrtObj.writeTestCaseXMLFile(stepNameToDisplay, newStepsId);
				}
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		CurrentStatusIndicatorVariables.selectedStepNode = newStepComboBox;

		// ----------------------------------------------------------------
		newStepComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {

				if (CurrentStatusIndicatorVariables.selectedPath != null) {
					CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
					CurrentStatusIndicatorVariables.selectedPath = null;
				}

				if (CurrentStatusIndicatorVariables.selectedModuleId != newStepComboBox.getId()
						&& CurrentStatusIndicatorVariables.selectedModuleId != null) {
					CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
					CurrentStatusIndicatorVariables.selModuleIcon = null;
					CurrentStatusIndicatorVariables.selectedModuleId = null;
				}

				if (CurrentStatusIndicatorVariables.selectedStepNode != null
						&& CurrentStatusIndicatorVariables.selectedStepNode != newStepComboBox) {
					CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
					if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
						CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
								"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
					} else {
						SetStartStep.startpane.setStyle(
								"-fx-background-color: linear-gradient(#32CD32, #00FF00);-fx-border-width:1; -fx-border-color:#6B8E23;-fx-background-radius: 10.0;-fx-border-radius:6 6 6 6;");
					}
				}
				newStepComboBox.setEffect(new DropShadow());

				if (CurrentStatusIndicatorVariables.selectedModuleId != newStepComboBox.getId()) {
					newStepComboBox.getChildren().get(0).setStyle(
							"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#E2E5F0, #172550);    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
				} else {
					newStepComboBox.getChildren().get(0).setStyle(
							"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
				}

				CurrentStatusIndicatorVariables.selectedStepNode = newStepComboBox;
				CurrentStatusIndicatorVariables.selectedPath = null;
				if (mouseEvent.getClickCount() == 2) {
					System.out.println("comobo : mouse Double Clicked");
				}
			}
		});
		// adjustStepNavigationForAdd(newStepsId);
		siblingHBoxWidthIncrement(newStepsId);
	}

	public void addModulesToStep(HBox stepHBox, String moduleId) {
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String, ModulePropertyBean> modMap = keyValMap.getModulePropertyBean();
		ModulePropertyBean modProp = modMap.get(moduleId);
		if (modMap.get(moduleId) != null) {
			if (moduleId != null && !moduleId.trim().equals("")) {
			    ImageView moduleIcon = new ImageView(modProp.getImgOfModule());
				moduleIcon.setFitWidth(35);
				moduleIcon.setFitHeight(35);
				moduleIcon.setId(stepHBox.getId());
				stepHBox.getChildren().add(moduleIcon);
	
				Tooltip.install(moduleIcon, KeyValueMapping.getInstance().getKeyAndTipMap().get(moduleId));
		        moduleIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						moduleIcon.setEffect(new DropShadow());
					}
				});

		        moduleIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						if (CurrentStatusIndicatorVariables.getSelModuleIcon() != null) {
							CurrentStatusIndicatorVariables.getSelModuleIcon().setEffect(null);
						}
						moduleIcon.setEffect(new DropShadow());
						CurrentStatusIndicatorVariables.setSelectedModuleId(moduleIcon.getId());
						CurrentStatusIndicatorVariables.setSelModuleIcon(moduleIcon);
						if (t.getClickCount() == 2) {
							Stage setupStg  = ModuleSetupStage.getClassInstance().getSetupStage();
							ActiveModule.getInstance().setActiveModId(moduleId);
							ModuleSetup setupObj =  modProp.getStartModuleSetupObj();
							setupStg.setTitle(SelectedCase.getSelectedTestCase() + " : " +stepHBox.getId());
							setupStg.getIcons().add(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//ATree.png"));
							setupObj.startSetupAction(setupStg);
							System.out.println("mouse Double Clicked");
						}
					}
				});

		        moduleIcon.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						if (moduleIcon.getId() != CurrentStatusIndicatorVariables.getSelectedModuleId()) {
							moduleIcon.setEffect(null);
						}
					}
				});
			}
		} else {
			if(!moduleId.equals(""))
			{
			ImageView modNotFndIcn = ImageAndImageViews.getClassInstance().getModNotFndImgView();
			Tooltip.install(modNotFndIcn, modNtFndTip);
			modNotFndIcn.setFitWidth(35);
			modNotFndIcn.setFitHeight(35);
			modNtFndTip.setText("Module not found.\n"+moduleId);
			stepHBox.getChildren().add(modNotFndIcn);
			if(!modNotFnd.contains(moduleId))
			{
			  modNotFnd.add(moduleId);
			}
			}
		}

	}

	public void siblingHBoxWidthIncrement(String newlyAddedStepId) {
		String stepId = newlyAddedStepId;
		int childCount = 0;
		// int parentIndex = 0;
		StepProperties curStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(stepId);
		Pane parentStepCont = curStepProp.getParentStep();
		// String parentIdArry1[] = parentStepCont.getId().split("_");
		String parentId = parentStepCont.getId();// parentIdArry1[0];
		StepProperties parentStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(parentId);
		if (newlyAddedStepId.equals(stepId)) {
			childCount = parentStepProp.getStepChildrenCount();
		}
		if (childCount == 1) {
			parentStepProp.setSiblingHBoxWidth(parentStepProp.getSiblingHBoxWidth() + 120);
		}
		if (childCount > 1) {
			while (!stepId.equals("START-0")) {
				curStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(stepId);
				parentStepCont = curStepProp.getParentStep();
				// String parentIdArry[] = parentStepCont.getId().split("_");
				parentId = parentStepCont.getId();// parentIdArry[0];
				parentStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(parentId);
				parentStepProp.setSiblingHBoxWidth(parentStepProp.getSiblingHBoxWidth() + 120 + 100);
				stepId = parentId;
			}
		}
	}

	/*
	 * public void adjustStepNavigationForAdd(String newlyAddedStepId) { String
	 * stepId = newlyAddedStepId; int childCount = 0; int parentIndex = 0; while
	 * (!stepId.equals("START-0")) { StepProperties curStepProp =
	 * StepHierarchyMapping.stepIdAndItsProperties.get(stepId); Pane
	 * parentStepCont = curStepProp.getParentStep(); // String parentIdArry[] =
	 * parentStepCont.getId().split("_"); String parentId =
	 * parentStepCont.getId();// parentIdArry[0]; StepProperties parentStepProp
	 * = StepHierarchyMapping.stepIdAndItsProperties.get(parentId); if
	 * (newlyAddedStepId.equals(stepId)) { childCount =
	 * parentStepProp.getStepChildrenCount(); } if (childCount >= 2 &&
	 * !newlyAddedStepId.equals(stepId)) { List<Pane> childrenlist =
	 * parentStepProp.getStepChildrenList(); Path stepPath = new Path(); for
	 * (Pane childpane : childrenlist) { // String childIdarry[] =
	 * childpane.getId().split("_"); String childId = childpane.getId();//
	 * childIdarry[0]; StepProperties childStepProp =
	 * StepHierarchyMapping.stepIdAndItsProperties.get(childId); int nodeIndex =
	 * childStepProp.getStepIndex();// Integer.parseInt(childIdarry[1]); //
	 * System.out.println(childStepProp.getStepIndex() + " " + // nodeIndex); if
	 * (nodeIndex > parentIndex) { stepPath = (Path)
	 * StepHierarchyMapping.stepsConnectPath.get(parentId + "_" + childId);
	 * LineTo line2 = (LineTo) stepPath.getElements().get(3); LineTo line3 =
	 * (LineTo) stepPath.getElements().get(5); QuadCurveTo quad2 = (QuadCurveTo)
	 * stepPath.getElements().get(4); LineTo line4 = (LineTo)
	 * stepPath.getElements().get(6); LineTo line5 = (LineTo)
	 * stepPath.getElements().get(7); LineTo line6 = (LineTo)
	 * stepPath.getElements().get(8);
	 * 
	 * double line2XCo = line2.getX(); double quad2ControlX =
	 * quad2.getControlX(); double quad2X = quad2.getX(); double line4XCo =
	 * line4.getX(); double line6XCo = line6.getX();
	 * 
	 * line2XCo = line2XCo + 120.0 + DeclareSettingVariable.stepsHorizontalGap;
	 * quad2ControlX = quad2ControlX + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap; quad2X = quad2X + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap; line4XCo = line4XCo + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap; line6XCo = line6XCo + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap; line2.setX(line2XCo);
	 * line3.setX(line2XCo + DeclareSettingVariable.curveRadius);
	 * quad2.setControlX(quad2ControlX); quad2.setX(quad2X);
	 * line4.setX(line4XCo); line6.setX(line6XCo); line5.setX(line2XCo +
	 * DeclareSettingVariable.curveRadius);
	 * 
	 * StepHierarchyMapping.stepsConnectPath.put(parentId + "_" + childId,
	 * stepPath); parentStepCont.getChildren().remove(stepPath);
	 * parentStepCont.getChildren().add(stepPath);
	 * StepHierarchyMapping.tmpColumnNumberOccupied.clear(); // This function
	 * (adjustConnectLinesSubStepsForAdd) is // used to adjust connection
	 * lines(connection lines to // show extra dependencies) between two step.
	 * adjustConnectLinesOfSubStepsForAddAction(childId); //
	 * stepHierarchyMapping.columnNumberOccupied.clear();
	 * StepHierarchyMapping.columnNumberOccupied.putAll(StepHierarchyMapping.
	 * tmpColumnNumberOccupied); } } } stepId = parentId; if
	 * (!parentId.equals("START-0")) { // parentIndex =
	 * Integer.parseInt(parentIdArry[1].trim()); parentIndex =
	 * parentStepProp.getStepIndex(); } } }
	 */
	/*
	 * public void adjustConnectLinesOfSubStepsForAddAction(String id) {
	 * StepProperties childStepProp =
	 * StepHierarchyMapping.stepIdAndItsProperties.get(id); double newXPos =
	 * childStepProp.getxPos() + childStepProp.getStepWidth() +
	 * DeclareSettingVariable.stepsHorizontalGap; //
	 * System.out.println(childStepProp.getStepDisplayName() + " " + //
	 * childStepProp.getxPos() + " " + newXPos); childStepProp.setxPos(newXPos);
	 * if (StepHierarchyMapping.sourceStepAndListOfPath.get(id) != null) {
	 * List<Path> listOfSourcePath =
	 * StepHierarchyMapping.sourceStepAndListOfPath.get(id); for (Path
	 * cutSourcePath : listOfSourcePath) { int pathEleCnt =
	 * cutSourcePath.getElements().size(); MoveTo startPoint = (MoveTo)
	 * cutSourcePath.getElements().get(0); LineTo line1 = (LineTo)
	 * cutSourcePath.getElements().get(1); QuadCurveTo quad2 = (QuadCurveTo)
	 * cutSourcePath.getElements().get(2);
	 * 
	 * startPoint.setX(startPoint.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); line1.setX(line1.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap);
	 * quad2.setControlX(quad2.getControlX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); quad2.setX(quad2.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap); //
	 * System.out.println(pathEleCnt); if (pathEleCnt > 10) { LineTo line3 =
	 * (LineTo) cutSourcePath.getElements().get(3); QuadCurveTo quad4 =
	 * (QuadCurveTo) cutSourcePath.getElements().get(4); LineTo line5 = (LineTo)
	 * cutSourcePath.getElements().get(5); QuadCurveTo quad6 = (QuadCurveTo)
	 * cutSourcePath.getElements().get(6); LineTo line7 = (LineTo)
	 * cutSourcePath.getElements().get(7); double colIndex = line3.getX();
	 * line3.setX(line3.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap);
	 * quad4.setControlX(quad4.getControlX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); quad4.setX(quad4.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap); //
	 * System.out.println(id + " " + line7.getX() + " " + // line5.getX() + " "
	 * + quad6.getX()); if (line7.getX() > line5.getX() && line7.getX() <
	 * (line5.getX() + 120.0 + DeclareSettingVariable.stepsHorizontalGap)) {
	 * quad6.setX(quad6.getX() - (2 * DeclareSettingVariable.curveRadius));
	 * line7.setX(line7.getX() + (2 * DeclareSettingVariable.curveRadius)); //
	 * System.out.println(id + " " + line7.getX() + " " + // line5.getX()); }
	 * line5.setX(line5.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap);
	 * quad6.setControlX(quad6.getControlX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); quad6.setX(quad6.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap); //
	 * System.out.println(id + " " + line7.getX() + " " + // line5.getX() + " "
	 * + quad6.getX());
	 * 
	 * int VerticalIndex; if (line1.getX() > line3.getX()) { VerticalIndex =
	 * (int) (colIndex - DeclareSettingVariable.curveRadius); } else {
	 * VerticalIndex = (int) (colIndex + DeclareSettingVariable.curveRadius); }
	 * String stepId = (String)
	 * StepHierarchyMapping.columnNumberOccupied.get(VerticalIndex);
	 * StepHierarchyMapping.columnNumberOccupied.remove(VerticalIndex); int
	 * newVerticalIndex = (int) (VerticalIndex + 120 +
	 * DeclareSettingVariable.stepsHorizontalGap); //
	 * System.out.println(VerticalIndex + " " + // newVerticalIndex);
	 * StepHierarchyMapping.tmpColumnNumberOccupied.put(newVerticalIndex,
	 * stepId); } } }
	 * 
	 * if (StepHierarchyMapping.destinationStepAndListOfPath.get(id) != null) {
	 * List<Path> listOfDestPath =
	 * StepHierarchyMapping.destinationStepAndListOfPath.get(id); for (Path
	 * cutDestPath : listOfDestPath) { int pathEleCnt =
	 * cutDestPath.getElements().size(); LineTo line1 = (LineTo)
	 * cutDestPath.getElements().get(pathEleCnt - 1); LineTo line2 = (LineTo)
	 * cutDestPath.getElements().get(pathEleCnt - 2); LineTo line3 = (LineTo)
	 * cutDestPath.getElements().get(pathEleCnt - 3); LineTo line4 = (LineTo)
	 * cutDestPath.getElements().get(pathEleCnt - 4); LineTo line5 = (LineTo)
	 * cutDestPath.getElements().get(pathEleCnt - 5); QuadCurveTo quad6 =
	 * (QuadCurveTo) cutDestPath.getElements().get(pathEleCnt - 6); LineTo line7
	 * = (LineTo) cutDestPath.getElements().get(pathEleCnt - 7);
	 * 
	 * line1.setX(line1.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); line2.setX(line2.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap);
	 * line3.setX(line3.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); line4.setX(line4.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap);
	 * line5.setX(line5.getX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); line7.setX(line7.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap);
	 * quad6.setControlX(quad6.getControlX() + 120.0 +
	 * DeclareSettingVariable.stepsHorizontalGap); quad6.setX(quad6.getX() +
	 * 120.0 + DeclareSettingVariable.stepsHorizontalGap); } } int tmpCnt =
	 * childStepProp.getStepChildrenCount(); if (tmpCnt > 0) { List<Pane>
	 * childPaneList = childStepProp.getStepChildrenList(); for (Pane childPane
	 * : childPaneList) { // String idarry[] = childPane.getId().split("_");
	 * String childId = childPane.getId();// idarry[0];
	 * adjustConnectLinesOfSubStepsForAddAction(childId); } } }
	 */
}
