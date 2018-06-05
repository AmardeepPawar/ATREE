package caseDesigner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.PopUpWindow;
import caseDesigner.Controllers.ConnectStepsController;
import caseDesigner.Controllers.StepConnectorControl;
import caseDesigner.Controllers.StepLoopControl;
import caseDesigner.beans.ConnectStepBean;
import caseDesigner.beans.StepConnectorBean;
import caseDesigner.beans.StepLoopBean;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurveTo;
import popUpWindowBeans.PopUpWindowBeans;

public class ConnectTwoStepsClass {
	CurrentStatusIndicatorVariables curStatus = new CurrentStatusIndicatorVariables();
	StepHierarchyContainerPanes contPanes = new StepHierarchyContainerPanes();
	StepHierarchyMapping Mapping = new StepHierarchyMapping();
	StepProperties startStepProp = new StepProperties();
	DeclareSettingVariable setupVariables = new DeclareSettingVariable();
	SetStartStep startStepSetup = new SetStartStep();
	String status = null;
	boolean dirConnect;

	public void connectTwoStepMethod() {
		PopUpWindowBeans stepContBean = ConnectStepBean.getInstaceOfClass();
		PopUpWindow popUpObj = PopUpWindow.getInstance();
		try {
			popUpObj.prepareStage(stepContBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loader = stepContBean.getLoader();
		ConnectStepsController controller = loader.getController();
		controller.setFormStep();
	
/*	
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
		ComboBox<String> sourceStepListComboBox = new ComboBox<String>();
		ComboBox<String> destinationStepListComboBox = new ComboBox<String>();
		Set<String> setOfStepNames = StepHierarchyMapping.stepNameAndItsId.keySet();
		String[] stepSourceNameArray = setOfStepNames.toArray(new String[setOfStepNames.size()]);
		// String[] stepDestinationNameArray = setOfStepNames.toArray(new
		// String[setOfStepNames.size()]);
		sourceStepListComboBox.getItems().clear();

		sourceStepListComboBox.setOnAction(e -> {
			String selectedSourceOrig = (String) sourceStepListComboBox.getValue();
			destinationStepListComboBox.getItems().clear();
			destinationStepListComboBox.getItems().addAll(stepSourceNameArray);
			String selectedSource = selectedSourceOrig;
			destinationStepListComboBox.getItems().remove(selectedSource);

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
		containerVBox.getChildren().addAll(sourceStepHBox, destinationStepHBox, OKBtnHBox, errorLabelHBox);
		Scene connectStepScene = new Scene(containerVBox, 340, 125);
		sourceStepHBox.getChildren().addAll(stepSourceLabel, sourceStepListComboBox);
		destinationStepHBox.getChildren().addAll(stepDestinationLabel, destinationStepListComboBox);
		errorMsg.setStyle("-fx-text-fill:red;");
		okButtonForConnect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				TestCaseFileReaderWriter conectObj = new TestCaseFileReaderWriter();
				String sourceStepName = sourceStepListComboBox.getValue().toString();
				String sourceStepNameId = (String) StepHierarchyMapping.stepNameAndItsId
						.get(sourceStepName);
				String destinationStepName = destinationStepListComboBox.getValue().toString();
				String destinationStepNameId = (String) StepHierarchyMapping.stepNameAndItsId
						.get(destinationStepName);

				if (StepHierarchyMapping.stepsConnectPath.get(sourceStepNameId + "_" + destinationStepNameId) == null) {
					dirConnect = checkDirectStep(sourceStepNameId, destinationStepNameId);
					conectObj.addStepConnectionInFile(sourceStepNameId, destinationStepNameId, dirConnect);
					try {
						conectObj.displayHierarchy();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stepConnectStage.close();
				} else {
					errorMsg.setText("Steps are already connected");
				}
			}
		});
		OKBtnHBox.getChildren().addAll(okButtonForConnect);
		stepConnectStage.setTitle("Connect two steps");
		stepConnectStage.setScene(connectStepScene);
		stepConnectStage.getIcons().add(new Image("icons//ATree.png"));
		stepConnectStage.initModality(Modality.APPLICATION_MODAL);
		stepConnectStage.show();*/
	}

	public String twoStepConnector(String sourceStep, String destinationStep, boolean dirFlag, String conditionStr) {
		String status = null;
		if (dirFlag) {
			imidiateStepConnector(sourceStep, destinationStep, conditionStr);
		} else {
			nonImidiateStepConnector(sourceStep, destinationStep, "NORMAL", conditionStr);
		}
		// boolean imdStepChk = checkImidiateStep(sourceStep, destinationStep,
		// "file");
		return status;
	}

	public String nonImidiateStepConnector(String sourceStep, String destinationStep, String typeOFCont, String conditionStr) {

		if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
			CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
		}
		double scrollXpos = StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.getHvalue();
		double scrollYpos = StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.getVvalue();
		String pathName = sourceStep + "_" + destinationStep;
		if (typeOFCont.equals("LOOP")) {
			pathName = "L-" + sourceStep + "_" + destinationStep;
		}
		String[] sourceNameSplitByDash = sourceStep.split("-");
		String[] sourceNumSplitByDot = sourceNameSplitByDash[1].split("\\.");
		int sourceNextRowNum = Integer.parseInt(sourceNumSplitByDot[0]) + 1;
		String[] destNameSplitByDash = destinationStep.split("-");
		String[] destNumSplitByDot = destNameSplitByDash[1].split("\\.");
		int destRowNum = Integer.parseInt(destNumSplitByDot[0]);
		RowProperties sourceNextRowProp = new RowProperties();
		// System.out.println(sourceRowNum + " " + destRowNum);
		if (StepHierarchyMapping.rowAndItsProperties.containsKey(sourceNextRowNum)) {
			sourceNextRowProp = StepHierarchyMapping.rowAndItsProperties.get(sourceNextRowNum);
		} else {
			sourceNextRowProp.setRowNum(sourceNextRowNum);
			sourceNextRowProp.setTotalStepsForRow(0);
			sourceNextRowProp.setRowHeight(DeclareSettingVariable.stepsVerticalGap);
			StepHierarchyMapping.rowAndItsProperties.put(sourceNextRowNum, sourceNextRowProp);
		}
		RowProperties destRowProp = StepHierarchyMapping.rowAndItsProperties.get(destRowNum);
		StepProperties sourceStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(sourceStep);
		StepProperties destStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(destinationStep);
		double sourceRowHeight = sourceNextRowProp.getRowHeight();
		double destRowHeight = destRowProp.getRowHeight();
		StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setHvalue(0);
		StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setVvalue(0);

		double sourcePosX = sourceStepProp.getxPos();
		double sourcePosY = sourceStepProp.getyPos();
		double sourceWidth = sourceStepProp.getStepWidth();// sourceStepBoundsInScene.getWidth();
		double sourceHeight = sourceStepProp.getStepHeight();// sourceStepBoundsInScene.getHeight();
		double destPosX = destStepProp.getxPos();// destStepBoundsInScene.getMinX();
		double destPosY = destStepProp.getyPos();// destStepBoundsInScene.getMinY();
		double destWidth = destStepProp.getStepWidth();// destStepBoundsInScene.getWidth();

		int sideVerticalLineLimit = 3, sideLineGapFromStep = 25, gapBetweenTwoVertLines = 25;

		int colMaxLimit = gapBetweenTwoVertLines * (sideVerticalLineLimit + 1);
		String srcStpFrLp = "L-" + sourceStep;
		String destStpFrLp = "L-" + destinationStep;
		// source and destination factor represent the distance between two
		// connector lines.
		int sourceHorizontalFactorTemp = 30, sourceHorizontalFactor = 30;
		int destinationHorizontalFactor = 15;
		// sign indicator represents lines on right or left side.
		int sourceHoriFactorSignDecider = 1;
		// connector vertical line index
		int VerticalLineIndex = 0;
		// this variable represents distance of connector for direct connection
		// line
		int sepFrmDirLine = 8;
		if (typeOFCont.equals("LOOP")) {
			sepFrmDirLine = -8;
		}

		// sourceRowAndIndexString and destinationRowAndIndexString are used for
		// checking the rows occupied between two horizontal steps rows.
		String sourceRowAndIndexString;
		String destinationRowAndIndexString = null;

		double sourcePosXTemp = sourcePosX;

		// baseRowAndIndexString represents the row index which is used in step
		// addition process as default row occupied.
		String baseRowAndIndexString = (sourceNextRowNum) + "_" + sourceHorizontalFactorTemp;
		double leapFactor = (sourceWidth + DeclareSettingVariable.stepsHorizontalGap);

		if (sourceNextRowNum != destRowNum) {

			while (true) {
				// set vertical gap from step
				VerticalLineIndex = (int) (sourcePosXTemp - sideLineGapFromStep);
				if (StepHierarchyMapping.columnNumberOccupied.get(VerticalLineIndex) == null) {
					break;
				}
				sideLineGapFromStep = sideLineGapFromStep + gapBetweenTwoVertLines;

				if ((VerticalLineIndex < 25)) {
					leapFactor = leapFactor * (-1);
					sourcePosXTemp = sourcePosX;
					sideLineGapFromStep = colMaxLimit;
				}
				if (sideLineGapFromStep == colMaxLimit) {
					sideLineGapFromStep = gapBetweenTwoVertLines;
					sourcePosXTemp = sourcePosXTemp - leapFactor;
				}
			}

			if ((StepHierarchyMapping.pathIndexOfStepAsSource.get(sourceStep) == null && typeOFCont.equals("NORMAL"))
					|| (StepHierarchyMapping.pathIndexOfStepAsSource.get(srcStpFrLp) == null
							&& typeOFCont.equals("LOOP"))) {
				sourceHorizontalFactorTemp = 15;
				while (true) {
					if (sourcePosXTemp != sourcePosX || typeOFCont.equals("LOOP")) {

						sourceRowAndIndexString = (sourceNextRowNum) + "_" + sourceHorizontalFactorTemp;

						if (StepHierarchyMapping.rowNumberOccupied.get(sourceRowAndIndexString) == null) {
							break;
						}
						sourceHorizontalFactorTemp = sourceHorizontalFactorTemp - (15 * sourceHoriFactorSignDecider);
						if (sourceHorizontalFactorTemp <= 15)
						{
							if (sourceHoriFactorSignDecider == 1) {
								sourceHorizontalFactorTemp = sourceHorizontalFactor + 15;
								sourceHoriFactorSignDecider = -1;
							}
						}
					} else {
						sourceHorizontalFactorTemp = 30;
						sourceRowAndIndexString = (sourceNextRowNum) + "_" + sourceHorizontalFactor;
						break;
					}
				}
			} else {
				if (typeOFCont.equals("NORMAL")) {
					sourceRowAndIndexString = (String) StepHierarchyMapping.pathIndexOfStepAsSource.get(sourceStep);
				} else {
					sourceRowAndIndexString = (String) StepHierarchyMapping.pathIndexOfStepAsSource.get(srcStpFrLp);
				}
				// System.out.println(baseRowAndIndexString + " " +
				// sourceRowAndIndexString);
				if (sourceRowAndIndexString.equals(baseRowAndIndexString) && sourcePosXTemp != sourcePosX) {
					while (true) {
						sourceRowAndIndexString = (sourceNextRowNum) + "_" + sourceHorizontalFactorTemp;
						// System.out.println(sourceHorizontalFactorTemp + " " +
						// sourceHorizontalFactor);
						if (StepHierarchyMapping.rowNumberOccupied.get(sourceRowAndIndexString) == null) {
							adjustPreviousLineOfSource(sourceStep,
									sourceHorizontalFactorTemp - (sourceHorizontalFactor));
							break;
						}
						sourceHorizontalFactorTemp = sourceHorizontalFactorTemp - (15 * sourceHoriFactorSignDecider);
						if (sourceHorizontalFactorTemp <= 15)// ||
																// sourceHorizontalFactorTemp
																// >
																// sourceHorizontalFactor)
						{
							if (sourceHoriFactorSignDecider == 1) {
								sourceHorizontalFactorTemp = sourceHorizontalFactor + 15;
								sourceHoriFactorSignDecider = -1;
							}
						}
					}
				} else {
					String sourceHoriIndexArry[] = sourceRowAndIndexString.split("_");
					sourceHorizontalFactorTemp = Integer.parseInt(sourceHoriIndexArry[1]); 
				}
			}

			if ((StepHierarchyMapping.pathIndexOfStepAsDestination.get(destinationStep) == null
					&& typeOFCont.equals("NORMAL"))
					|| (StepHierarchyMapping.pathIndexOfStepAsDestination.get(destStpFrLp) == null
							&& typeOFCont.equals("LOOP"))) {
				while (true) {
					destinationRowAndIndexString = destRowNum + "_" + destinationHorizontalFactor;
					int mainLineIndex = (int) (DeclareSettingVariable.stepsVerticalGap / 2);
					if (destinationHorizontalFactor != mainLineIndex) {
						if (StepHierarchyMapping.rowNumberOccupied.get(destinationRowAndIndexString) == null) {
							break;
						}
					}
					destinationHorizontalFactor = destinationHorizontalFactor + 15;
				}
			} else {
				if (typeOFCont.equals("NORMAL")) {
					destinationRowAndIndexString =  StepHierarchyMapping.pathIndexOfStepAsDestination.get(destinationStep);
				} else {
					destinationRowAndIndexString =  StepHierarchyMapping.pathIndexOfStepAsDestination.get(destStpFrLp);
				}
				String[] destHoriIndexArray = destinationRowAndIndexString.split("_");
				destinationHorizontalFactor = Integer.parseInt(destHoriIndexArray[1]);
			}
			StepHierarchyMapping.columnNumberOccupied.put(VerticalLineIndex, pathName);
			StepHierarchyMapping.rowNumberOccupied.put(sourceRowAndIndexString, pathName);

			StepHierarchyMapping.rowNumberOccupied.put(destinationRowAndIndexString, pathName);
			if (typeOFCont.equals("NORMAL"))
			{
				StepHierarchyMapping.pathIndexOfStepAsSource.put(sourceStep, sourceRowAndIndexString);
				StepHierarchyMapping.pathIndexOfStepAsDestination.put(destinationStep, destinationRowAndIndexString);
			}else
			{
				StepHierarchyMapping.pathIndexOfStepAsSource.put(srcStpFrLp, sourceRowAndIndexString);
				StepHierarchyMapping.pathIndexOfStepAsDestination.put(destStpFrLp, destinationRowAndIndexString);
			}

			double sourceConectPosX = sourcePosX + (sourceWidth / 2) - sepFrmDirLine; 
			double sourceConectPosY = sourcePosY + sourceHeight - 2;
			Path connectPath = new Path();
			MoveTo moveTo = new MoveTo();
			moveTo.setX(sourceConectPosX);
			moveTo.setY(sourceConectPosY);

			double sourceDestMidPoint1X = sourcePosX + (sourceWidth / 2) - sepFrmDirLine; 
			double sourceDestMidPoint1Y = sourcePosY + sourceHeight + sourceHorizontalFactorTemp; 

			LineTo lineTo1 = new LineTo();
			lineTo1.setX(sourceDestMidPoint1X);
			lineTo1.setY(sourceDestMidPoint1Y - DeclareSettingVariable.curveRadius);

			QuadCurveTo quadTo1 = new QuadCurveTo();
			quadTo1.setControlX(sourceDestMidPoint1X);
			quadTo1.setControlY(sourceDestMidPoint1Y);

			if (sourcePosXTemp <= sourcePosX) {
				quadTo1.setX(sourceDestMidPoint1X - DeclareSettingVariable.curveRadius);
			} else {
				quadTo1.setX(sourceDestMidPoint1X + DeclareSettingVariable.curveRadius);
			}
			quadTo1.setY(sourceDestMidPoint1Y);

			double sourceDestMidPoint2X = sourcePosXTemp - sideLineGapFromStep;
			double sourceDestMidPoint2Y = sourcePosY + sourceHeight + sourceHorizontalFactorTemp;

			LineTo lineTo2 = new LineTo();
			if (sourcePosXTemp <= sourcePosX) {
				lineTo2.setX(sourceDestMidPoint2X + DeclareSettingVariable.curveRadius);
			} else {
				lineTo2.setX(sourceDestMidPoint2X - DeclareSettingVariable.curveRadius);
			}
			lineTo2.setY(sourceDestMidPoint2Y);

			QuadCurveTo quadTo2 = new QuadCurveTo();
			quadTo2.setControlX(sourceDestMidPoint2X);
			quadTo2.setControlY(sourceDestMidPoint2Y);
			quadTo2.setX(sourceDestMidPoint2X);

			if (sourcePosY >= destPosY) {
				quadTo2.setY(sourceDestMidPoint2Y - DeclareSettingVariable.curveRadius);
			} else if (sourcePosY < destPosY) {
				quadTo2.setY(sourceDestMidPoint2Y + DeclareSettingVariable.curveRadius);
			}

			double sourceDestMidPoint3X = sourcePosXTemp - sideLineGapFromStep;
			double sourceDestMidPoint3Y = destPosY - (destRowHeight - destinationHorizontalFactor);

			LineTo lineTo3 = new LineTo();
			lineTo3.setX(sourceDestMidPoint3X);

			if (sourcePosY >= destPosY) {
				lineTo3.setY(sourceDestMidPoint3Y + DeclareSettingVariable.curveRadius);
			} else if (sourcePosY < destPosY) {
				lineTo3.setY(sourceDestMidPoint3Y - DeclareSettingVariable.curveRadius);
			}

			QuadCurveTo quadTo3 = new QuadCurveTo();
			quadTo3.setControlX(sourceDestMidPoint3X);
			quadTo3.setControlY(sourceDestMidPoint3Y);

			if (sourcePosXTemp > destPosX) {
				quadTo3.setX(sourceDestMidPoint3X - DeclareSettingVariable.curveRadius);
			} else if (sourcePosXTemp <= destPosX) {
				quadTo3.setX(sourceDestMidPoint3X + DeclareSettingVariable.curveRadius);
			}
			quadTo3.setY(sourceDestMidPoint3Y);

			double sourceDestMidPoint4X = destPosX + (sourceWidth / 2) + sepFrmDirLine;
			double sourceDestMidPoint4Y = destPosY - (destRowHeight - destinationHorizontalFactor);
			LineTo lineTo4 = new LineTo();

			lineTo4.setY(sourceDestMidPoint4Y);

			if (sourcePosXTemp > destPosX) {
				lineTo4.setX(sourceDestMidPoint4X + DeclareSettingVariable.curveRadius);
			} else if (sourcePosXTemp <= destPosX) {
				lineTo4.setX(sourceDestMidPoint4X - DeclareSettingVariable.curveRadius);
			}

			QuadCurveTo quadTo4 = new QuadCurveTo();
			quadTo4.setControlX(sourceDestMidPoint4X);
			quadTo4.setControlY(sourceDestMidPoint4Y);
			quadTo4.setX(sourceDestMidPoint4X);
			quadTo4.setY(sourceDestMidPoint4Y + DeclareSettingVariable.curveRadius);

			double destConectPosX = destPosX + (destWidth / 2) + sepFrmDirLine; 
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

			connectPath.getElements().addAll(moveTo, lineTo1, quadTo1, lineTo2, quadTo2, lineTo3, quadTo3, lineTo4,
					quadTo4, lineTo5, lineTo6, lineTo7, lineTo8, lineTo9);
			connectPath.setStrokeWidth(1.5);
			Polygon polygon = ConditionBlock.getConditionPolygon(sourceDestMidPoint3X, sourceDestMidPoint2Y + (sourceDestMidPoint4Y - sourceDestMidPoint2Y)/2, conditionStr );
            if(typeOFCont.equals("LOOP"))
            {
            	polygon.setStroke(Color.BLUE);
            }
			StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().addAll(connectPath,polygon);

			StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setHvalue(scrollXpos);
			StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setVvalue(scrollYpos);
			StepHierarchyMapping.stepsConnectPath.put(pathName, connectPath);
			connectPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (CurrentStatusIndicatorVariables.selectedPath != null) {
						CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
					}
					if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
						CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
						if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
							CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
									"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
						}
						CurrentStatusIndicatorVariables.selectedStepNode = null;
					}
					if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
						CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
						CurrentStatusIndicatorVariables.selModuleIcon = null;
						CurrentStatusIndicatorVariables.selectedModuleId=null;
					}					
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(connectPath);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(connectPath);
					Polygon polyG = StepHierarchyMapping.idAndPolygon.get(connectPath.getId());

					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(polyG);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(polyG);
					connectPath.setEffect(new DropShadow());
					CurrentStatusIndicatorVariables.selectedPath = connectPath;
					CurrentStatusIndicatorVariables.selectedStepNode = null;
					if (mouseEvent.getClickCount() == 2) {
						createStageFunc(connectPath);
					}
				}
			});
			polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (CurrentStatusIndicatorVariables.selectedPath != null) {
						CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
					}
					if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
						CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
						if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
							CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
									"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
						}
						CurrentStatusIndicatorVariables.selectedStepNode = null;
					}
					if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
						CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
						CurrentStatusIndicatorVariables.selModuleIcon = null;
						CurrentStatusIndicatorVariables.selectedModuleId=null;
					}					
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(connectPath);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(connectPath);
					Polygon polyG = StepHierarchyMapping.idAndPolygon.get(connectPath.getId());

					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(polyG);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(polyG);
					connectPath.setEffect(new DropShadow());
					CurrentStatusIndicatorVariables.selectedPath = connectPath;
					CurrentStatusIndicatorVariables.selectedStepNode = null;
					if (mouseEvent.getClickCount() == 2) {
						createStageFunc(polygon);
					}
				}
			});
			connectPath.setId(pathName);
			polygon.setId(pathName);
			StepHierarchyMapping.idAndPolygon.put(pathName, polygon);
			PathProperties curConnectorPath = new PathProperties();
			curConnectorPath.setTotalElmInPath(connectPath.getElements().size());
			curConnectorPath.setParentStep(CurrentStatusIndicatorVariables.selectedStepId);
			curConnectorPath.setPath(connectPath);
			curConnectorPath.setPathCondition(conditionStr);
			curConnectorPath.setPathName(pathName);
			StepHierarchyMapping.pathAndItsProperties.put(pathName, curConnectorPath);
			if (typeOFCont.equals("LOOP")) {
				connectPath.setStyle("-fx-stroke: blue;");
			}

			if (StepHierarchyMapping.sourceStepAndListOfPath.get(sourceStep) != null) {
				List<Path> listOfSourcePath = StepHierarchyMapping.sourceStepAndListOfPath.get(sourceStep);
				listOfSourcePath.add(connectPath);
				StepHierarchyMapping.sourceStepAndListOfPath.put(sourceStep, listOfSourcePath);
				// System.out.println(sourceStep + " " + listOfSourcePath);
			} else {
				List<Path> listOfSourcePath = new ArrayList<Path>();
				listOfSourcePath.add(connectPath);
				StepHierarchyMapping.sourceStepAndListOfPath.put(sourceStep, listOfSourcePath);
				// System.out.println(sourceStep + " " + listOfSourcePath);
			}

			if (StepHierarchyMapping.destinationStepAndListOfPath.get(destinationStep) != null) {
				List<Path> listOfDestPath = StepHierarchyMapping.destinationStepAndListOfPath.get(destinationStep);
				listOfDestPath.add(connectPath);
				StepHierarchyMapping.destinationStepAndListOfPath.put(destinationStep, listOfDestPath);
			} else {
				List<Path> listOfDestPath = new ArrayList<Path>();
				listOfDestPath.add(connectPath);
				StepHierarchyMapping.destinationStepAndListOfPath.put(destinationStep, listOfDestPath);
			}

			if (sourceHorizontalFactorTemp >= sourceRowHeight) {
				rowHeightAdjustment(sourceStep, "source");
			}
		}
		// when destination is in exact next row of source
		else {
			if (StepHierarchyMapping.pathIndexOfStepAsDestination.get(destinationStep) == null) {
				while (true) {

					destinationRowAndIndexString = destRowNum + "_" + destinationHorizontalFactor;

					int mainLineIndex = (int) (DeclareSettingVariable.stepsHorizontalGap / 2);
					if (destinationHorizontalFactor != mainLineIndex) {
						if (StepHierarchyMapping.rowNumberOccupied.get(destinationRowAndIndexString) == null
								&& destinationHorizontalFactor != DeclareSettingVariable.stepsVerticalGap / 2) {
							break;
						}
					}
					destinationHorizontalFactor = destinationHorizontalFactor + 15;
				}
			} else {
				destinationRowAndIndexString = (String) StepHierarchyMapping.pathIndexOfStepAsDestination
						.get(destinationStep);

				String[] destHoriIndexArray = destinationRowAndIndexString.split("_");
				destinationHorizontalFactor = Integer.parseInt(destHoriIndexArray[1]);
			}
			StepHierarchyMapping.columnNumberOccupied.put(VerticalLineIndex, pathName);
			// System.out.println(VerticalIndex);
			StepHierarchyMapping.rowNumberOccupied.put(destinationRowAndIndexString, pathName);
			StepHierarchyMapping.pathIndexOfStepAsDestination.put(destinationStep, destinationRowAndIndexString);
			double sourceConectPosX = sourcePosX + (sourceWidth / 2) - 8;
			double sourceConectPosY = sourcePosY + sourceHeight - 2;
			Path connectPath = new Path();
			MoveTo moveTo = new MoveTo();
			moveTo.setX(sourceConectPosX);
			moveTo.setY(sourceConectPosY);

			double sourceDestMidPoint1X = sourcePosX + (sourceWidth / 2) - 8;
			double sourceDestMidPoint1Y = sourcePosY + sourceHeight + destinationHorizontalFactor;

			LineTo lineTo1 = new LineTo();
			lineTo1.setX(sourceDestMidPoint1X);
			lineTo1.setY(sourceDestMidPoint1Y - DeclareSettingVariable.curveRadius);

			QuadCurveTo quadTo1 = new QuadCurveTo();
			quadTo1.setControlX(sourceDestMidPoint1X);
			quadTo1.setControlY(sourceDestMidPoint1Y);
			if (destPosX <= sourcePosX) {
				quadTo1.setX(sourceDestMidPoint1X - DeclareSettingVariable.curveRadius);
			} else {
				quadTo1.setX(sourceDestMidPoint1X + DeclareSettingVariable.curveRadius);
			}
			quadTo1.setY(sourceDestMidPoint1Y);

			double sourceDestMidPoint4X = destPosX + (sourceWidth / 2) + 8;
			double sourceDestMidPoint4Y = destPosY - (destRowHeight - destinationHorizontalFactor);

			LineTo lineTo4 = new LineTo();

			lineTo4.setY(sourceDestMidPoint4Y);

			if (sourcePosXTemp > destPosX) {
				lineTo4.setX(sourceDestMidPoint4X + DeclareSettingVariable.curveRadius);
			} else if (sourcePosXTemp <= destPosX) {
				lineTo4.setX(sourceDestMidPoint4X - DeclareSettingVariable.curveRadius);
			}

			QuadCurveTo quadTo4 = new QuadCurveTo();
			quadTo4.setControlX(sourceDestMidPoint4X);
			quadTo4.setControlY(sourceDestMidPoint4Y);
			quadTo4.setX(sourceDestMidPoint4X);
			quadTo4.setY(sourceDestMidPoint4Y + DeclareSettingVariable.curveRadius);

			double destConectPosX = destPosX + (destWidth / 2) + 8;// (8 *
																	// inConnCount);
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

			connectPath.getElements().addAll(moveTo, lineTo1, quadTo1, lineTo4, quadTo4, lineTo5, lineTo6, lineTo7,
					lineTo8, lineTo9);
			connectPath.setStrokeWidth(1.5);
			Polygon polygon;
			if((destConectPosX - sourceDestMidPoint1X) > 0)
			{
				polygon = ConditionBlock.getConditionPolygon(sourceDestMidPoint1X + sourceWidth/3, sourceDestMidPoint4Y, conditionStr);
			}
			else
			{
				polygon = ConditionBlock.getConditionPolygon(sourceDestMidPoint1X  - sourceWidth/3, sourceDestMidPoint4Y, conditionStr);
			}
		
			StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().addAll(connectPath, polygon);
			
			connectPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override				
				public void handle(MouseEvent mouseEvent) {
					if (CurrentStatusIndicatorVariables.selectedPath != null) {
						// selectedPath.setStyle("-fx-stroke: black;");
						CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
					}
					if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
						CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
						if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
							CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
									"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
						}
						CurrentStatusIndicatorVariables.selectedStepNode = null;
					}
					if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
						CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
						CurrentStatusIndicatorVariables.selModuleIcon = null;
						CurrentStatusIndicatorVariables.selectedModuleId=null;
					}
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(connectPath);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(connectPath);
					Polygon polyG = StepHierarchyMapping.idAndPolygon.get(connectPath.getId());
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(polyG);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(polyG);
					// regularConnectPath.setStyle("-fx-stroke: #e59866;");
					connectPath.setEffect(new DropShadow());
					CurrentStatusIndicatorVariables.selectedPath = connectPath;
					CurrentStatusIndicatorVariables.selectedStepNode = null;
					if (mouseEvent.getClickCount() == 2) {
						createStageFunc(connectPath);
					}
				}
			});
			
			polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override				
				public void handle(MouseEvent mouseEvent) {
					if (CurrentStatusIndicatorVariables.selectedPath != null) {
						// selectedPath.setStyle("-fx-stroke: black;");
						CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
					}
					if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
						CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
						if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
							CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
									"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
						}
						CurrentStatusIndicatorVariables.selectedStepNode = null;
					}
					if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
						CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
						CurrentStatusIndicatorVariables.selModuleIcon = null;
						CurrentStatusIndicatorVariables.selectedModuleId=null;
					}
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(connectPath);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(connectPath);
					Polygon polyG = StepHierarchyMapping.idAndPolygon.get(connectPath.getId());
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().remove(polyG);
					StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().add(polyG);
					// regularConnectPath.setStyle("-fx-stroke: #e59866;");
					connectPath.setEffect(new DropShadow());
					CurrentStatusIndicatorVariables.selectedPath = connectPath;
					CurrentStatusIndicatorVariables.selectedStepNode = null;
					if (mouseEvent.getClickCount() == 2) {
						createStageFunc(polygon);
					}
				}
			});
			StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setHvalue(scrollXpos);
			StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setVvalue(scrollYpos);
			StepHierarchyMapping.stepsConnectPath.put(pathName, connectPath);
			connectPath.setId(pathName);
			polygon.setId(pathName);
			StepHierarchyMapping.idAndPolygon.put(pathName, polygon);
			PathProperties curConnectorPath = new PathProperties();
			curConnectorPath.setTotalElmInPath(connectPath.getElements().size());
			curConnectorPath.setParentStep(CurrentStatusIndicatorVariables.selectedStepId);
			curConnectorPath.setPath(connectPath);
			curConnectorPath.setPathCondition(conditionStr);
			curConnectorPath.setPathName(pathName);
			StepHierarchyMapping.pathAndItsProperties.put(pathName, curConnectorPath);
			if (StepHierarchyMapping.sourceStepAndListOfPath.get(sourceStep) != null) {
				List<Path> listOfSourcePath = StepHierarchyMapping.sourceStepAndListOfPath.get(sourceStep);
				listOfSourcePath.add(connectPath);
				StepHierarchyMapping.sourceStepAndListOfPath.put(sourceStep, listOfSourcePath);
			} else {
				List<Path> listOfSourcePath = new ArrayList<Path>();
				listOfSourcePath.add(connectPath);
				StepHierarchyMapping.sourceStepAndListOfPath.put(sourceStep, listOfSourcePath);
				// System.out.println(sourceStep + " " +listOfSourcePath);
			}
			if (StepHierarchyMapping.destinationStepAndListOfPath.get(destinationStep) != null) {
				List<Path> listOfDestPath = StepHierarchyMapping.destinationStepAndListOfPath.get(destinationStep);
				listOfDestPath.add(connectPath);
				StepHierarchyMapping.destinationStepAndListOfPath.put(destinationStep, listOfDestPath);
			} else {
				List<Path> listOfDestPath = new ArrayList<Path>();
				listOfDestPath.add(connectPath);
				StepHierarchyMapping.destinationStepAndListOfPath.put(destinationStep, listOfDestPath);
			}
		}

		if (destinationHorizontalFactor >= destRowHeight) {

			rowHeightAdjustment(destinationStep, "destination");
		}

		return null;

	}

	public void adjustPreviousLineOfSource(String sourceStep, int sourceHorizontalFactorGap) {
		List<Path> tmpConnectionPath = StepHierarchyMapping.sourceStepAndListOfPath.get(sourceStep);
		
		for (Path tmpPath : tmpConnectionPath) {
			int pathSize = tmpPath.getElements().size();
			if (pathSize == 14) {
				// System.out.println(sourceHorizontalFactorGap + " " +
				// tmpPath.getId());
				String loopIndex = tmpPath.getId().split("-")[0];
				if(!loopIndex.equals("L"))
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
	}

	void rowHeightAdjustment(String StepId, String stepType) {
		String[] stepIDArray = StepId.split("-");
		String[] stepIDNumArray = stepIDArray[1].split("\\.");
		int rowNum = Integer.parseInt(stepIDNumArray[0]);
		if (stepType.equals("destination")) {
			rowNum = rowNum - 1;
		}
		List<String> siblingDone = new ArrayList<String>();
		siblingDone.add("dummy");
		RowProperties rowProp = new RowProperties();

		rowProp = StepHierarchyMapping.rowAndItsProperties.get(rowNum + 1);
		double rowStepsVerticalGap = rowProp.getRowHeight();
		// if(rowAndItsMembers.get(rowNum + 1) != null)
		if (rowProp.getRowMember() != null) {
			List<String> allElementsInRow = rowProp.getRowMember();
			for (String rowEle : allElementsInRow) {
				StepProperties Step = StepHierarchyMapping.stepIdAndItsProperties.get(rowEle);
				Step.setyPos(Step.getyPos() + 15);
				Pane parentPane = Step.getParentStep();
				String parentId = parentPane.getId();
				String[] parentIdArr = parentId.split("_");
				StepProperties parentStep = StepHierarchyMapping.stepIdAndItsProperties.get(parentIdArr[0]);
				if (!siblingDone.contains(parentIdArr[0])) {
					HBox SiblingBox = parentStep.getSiblingHBoxOfStep();
					// Bounds SiblingBoxBoundsInScene =
					// SiblingBox.sceneToLocal(SiblingBox.getBoundsInLocal());
					SiblingBox.setLayoutY(SiblingBox.getLayoutY() + 15);
					siblingDone.add(parentIdArr[0]);
				}
				String idString = parentIdArr[0] + "_" + rowEle;
				Path directPath = StepHierarchyMapping.stepsConnectPath.get(idString);
				int sizeOfPath;
				LineTo tempLineTo1;
				LineTo tempLineTo2;
				LineTo tempLineTo3;
				LineTo tempLineTo4;
				if (directPath != null) {
					sizeOfPath = directPath.getElements().size();
					tempLineTo1 = (LineTo) directPath.getElements().get(sizeOfPath - 4);
					tempLineTo2 = (LineTo) directPath.getElements().get(sizeOfPath - 3);
					tempLineTo3 = (LineTo) directPath.getElements().get(sizeOfPath - 2);
					tempLineTo4 = (LineTo) directPath.getElements().get(sizeOfPath - 1);
					tempLineTo1.setY(tempLineTo1.getY() + 15);
					tempLineTo2.setY(tempLineTo2.getY() + 15);
					tempLineTo3.setY(tempLineTo3.getY() + 15);
					tempLineTo4.setY(tempLineTo4.getY() + 15);
				}
					if (StepHierarchyMapping.destinationStepAndListOfPath.get(rowEle) != null) {
						List<Path> listOfDestinationStepPath = StepHierarchyMapping.destinationStepAndListOfPath
								.get(rowEle);
						for (Path curPath : listOfDestinationStepPath) {
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
				//}
			}

		}
		double gap = rowStepsVerticalGap + 15;
		rowProp.setRowHeight(gap);
		int i = 2;
		while (StepHierarchyMapping.rowAndItsProperties.get(rowNum + i) != null) {
			rowProp = StepHierarchyMapping.rowAndItsProperties.get(rowNum + i);
			// System.out.println(rowNum + i);
			List<String> allElementsInRow = rowProp.getRowMember();
			if (allElementsInRow != null) {
				for (String rowEle : allElementsInRow) {
					// System.out.println(rowEle);
					StepProperties Step = StepHierarchyMapping.stepIdAndItsProperties.get(rowEle);
					Step.setyPos(Step.getyPos() + 15);
					if (StepHierarchyMapping.destinationStepAndListOfPath.get(rowEle) != null) {
						List<Path> listOfDestinationStepPath = StepHierarchyMapping.destinationStepAndListOfPath
								.get(rowEle);
						for (Path curPath : listOfDestinationStepPath) {
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
			i = i + 1;
		}
		i = 1;
		while (StepHierarchyMapping.rowAndItsProperties.get(rowNum + i) != null) {
			rowProp = StepHierarchyMapping.rowAndItsProperties.get(rowNum + i);

			List<String> allElementsInRow = rowProp.getRowMember(); // rowAndItsMembers.get(rowNum
																	// + i);
			if (allElementsInRow != null) {
				for (String rowEle : allElementsInRow) {
					if (StepHierarchyMapping.sourceStepAndListOfPath.get(rowEle) != null) {
						List<Path> listOfSourceStepPath = StepHierarchyMapping.sourceStepAndListOfPath.get(rowEle);
						for (Path curPath : listOfSourceStepPath) {
							MoveTo tmpMoveTo0 = (MoveTo) curPath.getElements().get(0);
							tmpMoveTo0.setY(tmpMoveTo0.getY() + 15);

							if (curPath.getElements().size() != 10) {
								QuadCurveTo tempQuadTo4 = (QuadCurveTo) curPath.getElements().get(4);
								QuadCurveTo tempQuadTo2 = (QuadCurveTo) curPath.getElements().get(2);
								LineTo tempLineTo3 = (LineTo) curPath.getElements().get(3);

								LineTo tempLineTo1 = (LineTo) curPath.getElements().get(1);
								tempLineTo1.setY(tempLineTo1.getY() + 15);

								tempLineTo3.setY(tempLineTo3.getY() + 15);
								tempQuadTo2.setControlY(tempQuadTo2.getControlY() + 15);
								tempQuadTo4.setControlY(tempQuadTo4.getControlY() + 15);
								tempQuadTo2.setY(tempQuadTo2.getY() + 15);
								tempQuadTo4.setY(tempQuadTo4.getY() + 15);
							}
						}
					}
				}
			}
			i = i + 1;
		}
	}

	public void imidiateStepConnector(String sourceStep, String destStep, String conditionStr) {
		double startpointX = 0;
		double startpointY = 0;
		double midPoint1X = 0;
		double midPoint1Y = 0;
		double midPoint2X = 0;
		double midPoint2Y = 0;
		double EndpointX = 0;
		double EndpointY = 0;
		StepProperties selectedStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(sourceStep);
		Pane selectedStepContainer = selectedStepProp.getStepPane();
		double xCoordinateOfSelectedStep = selectedStepProp.getStepNameAndModuleVBox().getLayoutX();
		double selectedStepBoxWidth = selectedStepProp.getStepWidth();
		double selectedStepBoxHeight = selectedStepProp.getStepHeight();
		double yCoordinateOfSelectedStep = selectedStepProp.getStepNameAndModuleVBox().getLayoutY();

		int selectedStepChildrenCount = selectedStepProp.getStepChildrenCount();
		// int connectorAdjustmentFactor = 0;
		StepProperties newStepPro = StepHierarchyMapping.stepIdAndItsProperties.get(destStep);
		if (sourceStep.equals("START-0")) {
			xCoordinateOfSelectedStep = selectedStepProp.getStepNameAndModuleVBox().getLayoutX() - 20;
		}

		StepProperties destStepProp = StepHierarchyMapping.stepIdAndItsProperties.get(destStep);

		String[] stepIDArray = sourceStep.split("-");
		String[] stepIDNumArray = stepIDArray[1].split("\\.");
		int rowNum = Integer.parseInt(stepIDNumArray[0]);
		RowProperties rowProp = StepHierarchyMapping.rowAndItsProperties.get(rowNum + 1);
		double tmpStepsVerticalGap = 0;

		if (rowProp != null) {
			tmpStepsVerticalGap = rowProp.getRowHeight();
		} else {
			tmpStepsVerticalGap = DeclareSettingVariable.stepsVerticalGap;
		}

		if (selectedStepContainer.getId().equals("START-0")) {

			startpointX = xCoordinateOfSelectedStep + selectedStepBoxWidth / 2 + 20;
			startpointY = yCoordinateOfSelectedStep + selectedStepBoxHeight;
			midPoint1X = startpointX;
			midPoint1Y = startpointY + 25;
			// EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() +
			// 120 + connectorAdjustmentFactor;

			EndpointX = (destStepProp.getxPos() - selectedStepProp.getxPos()) + 120;// +
																					// connectorAdjustmentFactor;
			// selectedStepProp.setSiblingHBoxWidth(selectedStepProp.getSiblingHBoxWidth()
			// + 120 + connectorAdjustmentFactor);
			EndpointY = startpointY + tmpStepsVerticalGap;
			midPoint2X = EndpointX;
			midPoint2Y = midPoint1Y;
		} else {
			startpointX = xCoordinateOfSelectedStep + selectedStepBoxWidth / 2;
			startpointY = yCoordinateOfSelectedStep + selectedStepBoxHeight - 2;
			midPoint1X = startpointX;
			midPoint1Y = startpointY + (DeclareSettingVariable.stepsVerticalGap / 2);
			// EndpointX = selectedStepProp.getSiblingHBoxOfStep().getWidth() +
			// 60 + connectorAdjustmentFactor;
			EndpointX = (destStepProp.getxPos() - selectedStepProp.getxPos()) + 60;// +
																					// connectorAdjustmentFactor;
			EndpointY = startpointY + tmpStepsVerticalGap + 2;
			midPoint2X = EndpointX;
			midPoint2Y = midPoint1Y;
		}

		MoveTo moveTo = new MoveTo();
		moveTo.setX(startpointX);
		moveTo.setY(startpointY);

		LineTo lineTo1 = new LineTo();
		lineTo1.setX(midPoint1X);
		if (selectedStepChildrenCount == 1) {
			lineTo1.setY(midPoint1Y);
		} else {
			lineTo1.setY(midPoint1Y - DeclareSettingVariable.curveRadius);
		}

		QuadCurveTo quadTo1 = new QuadCurveTo();
		quadTo1.setControlX(midPoint1X);
		quadTo1.setControlY(midPoint1Y);
		quadTo1.setX(midPoint1X + DeclareSettingVariable.curveRadius);
		quadTo1.setY(midPoint1Y);

		LineTo lineTo2 = new LineTo();
		if (selectedStepChildrenCount == 1) {
			lineTo2.setX(midPoint2X);
		} else {
			lineTo2.setX(midPoint2X - DeclareSettingVariable.curveRadius);
		}
		lineTo2.setY(midPoint2Y);

		QuadCurveTo quadTo2 = new QuadCurveTo();
		quadTo2.setControlX(midPoint2X);
		quadTo2.setControlY(midPoint2Y);
		quadTo2.setX(midPoint2X);
		quadTo2.setY(midPoint2Y + DeclareSettingVariable.curveRadius);

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

		Path regularConnectPath = new Path();
		if ((destStepProp.getxPos() - selectedStepProp.getxPos()) == 0) {
			regularConnectPath.getElements().addAll(moveTo, lineTo3, lineTo4, lineTo5, lineTo6);
		} else {
			regularConnectPath.getElements().addAll(moveTo, lineTo1, quadTo1, lineTo2, quadTo2, lineTo3, lineTo4,
					lineTo5, lineTo6);
		}
		Polygon polygon = ConditionBlock.getConditionPolygon(EndpointX, EndpointY - DeclareSettingVariable.getStepsVerticalGap()/3, conditionStr);

		regularConnectPath.setStrokeWidth(1.5);
		selectedStepContainer.getChildren().addAll(regularConnectPath, polygon);

		String pathId = sourceStep + "_" + destStep;
		regularConnectPath.setId(pathId);
		polygon.setId(pathId);
		StepHierarchyMapping.idAndPolygon.put(pathId, polygon);
		PathProperties curConnectorPath = new PathProperties();
		curConnectorPath.setTotalElmInPath(regularConnectPath.getElements().size());
		curConnectorPath.setChildStep(destStep);
		curConnectorPath.setParentStep(CurrentStatusIndicatorVariables.selectedStepId);
		curConnectorPath.setPath(regularConnectPath);
		curConnectorPath.setPathCondition(conditionStr);
		curConnectorPath.setPathName(pathId);
		curConnectorPath.setPathContainer(selectedStepContainer);

		StepHierarchyMapping.pathAndItsProperties.put(pathId, curConnectorPath);
		StepHierarchyMapping.stepsConnectPath.put(pathId, regularConnectPath);

		List<Path> listOfPath = newStepPro.getStepIncomingPaths();
		if (listOfPath == null) {
			newStepPro.setStepIncomingPaths(listOfPath = new ArrayList<Path>());
		}
		listOfPath.add(regularConnectPath);
		newStepPro.setStepIncomingPaths(listOfPath);

		listOfPath = selectedStepProp.getStepOutgoingPaths();
		if (listOfPath == null) {
			selectedStepProp.setStepOutgoingPaths(listOfPath = new ArrayList<Path>());
		}
		listOfPath.add(regularConnectPath);
		selectedStepProp.setStepOutgoingPaths(listOfPath);

		regularConnectPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (CurrentStatusIndicatorVariables.selectedPath != null) {
					// selectedPath.setStyle("-fx-stroke: black;");
					CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
				}
				if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
					CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
					if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
						CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
								"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
					}
				}
				if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
					CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
					CurrentStatusIndicatorVariables.selModuleIcon = null;
					CurrentStatusIndicatorVariables.selectedModuleId = null;
				}
				Polygon polyG = StepHierarchyMapping.idAndPolygon.get(regularConnectPath.getId());
				selectedStepContainer.getChildren().remove(regularConnectPath);
				selectedStepContainer.getChildren().add(regularConnectPath);
				selectedStepContainer.getChildren().remove(polyG);
				selectedStepContainer.getChildren().add(polyG);
				regularConnectPath.setEffect(new DropShadow());
				CurrentStatusIndicatorVariables.selectedPath = regularConnectPath;
				CurrentStatusIndicatorVariables.selectedStepNode = null;
				if (mouseEvent.getClickCount() == 2) {
					createStageFunc(regularConnectPath);
				}
			}
		});
		
		polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (CurrentStatusIndicatorVariables.selectedPath != null) {
					// selectedPath.setStyle("-fx-stroke: black;");
					CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
				}
				if (CurrentStatusIndicatorVariables.selectedStepNode != null) {
					CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
					if (CurrentStatusIndicatorVariables.selectedStepNode != SetStartStep.startpane) {
						CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
								"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
					}
				}
				if (CurrentStatusIndicatorVariables.selModuleIcon != null) {
					CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
					CurrentStatusIndicatorVariables.selModuleIcon = null;
					CurrentStatusIndicatorVariables.selectedModuleId = null;
				}
				Polygon polyG = StepHierarchyMapping.idAndPolygon.get(regularConnectPath.getId());
				selectedStepContainer.getChildren().remove(regularConnectPath);
				selectedStepContainer.getChildren().add(regularConnectPath);
				selectedStepContainer.getChildren().remove(polyG);
				selectedStepContainer.getChildren().add(polyG);
				regularConnectPath.setEffect(new DropShadow());
				CurrentStatusIndicatorVariables.selectedPath = regularConnectPath;
				CurrentStatusIndicatorVariables.selectedStepNode = null;
				if (mouseEvent.getClickCount() == 2) {
					createStageFunc(polygon);
				}
			}
		});
	}

	public boolean checkDirectStep(String parentStep, String childStep) {
		String parntDotString = parentStep.split("-")[1];
		String childDotString = childStep.split("-")[1];
		String[] dotSepParnt = parntDotString.split("\\.");
		String[] dotSepChild = childDotString.split("\\.");
		if ((dotSepParnt.length + 1) == dotSepChild.length
				&& (Integer.parseInt(dotSepParnt[0]) + 1) == Integer.parseInt(dotSepChild[0])) {
			for (int i = 1; i < dotSepParnt.length; i++) {
				if (!dotSepParnt[i].equals(dotSepChild[i])) {
					return false;
				}
			}
			return true;
		} else if (dotSepParnt.length == 1 && dotSepChild.length == 1
				&& (Integer.parseInt(dotSepParnt[0]) + 1) == Integer.parseInt(dotSepChild[0])) {
			return true;
		}

		return false;
	}
	
	protected void createStageFunc(Polygon polygon)
	{
		String[] stepSplit = polygon.getId().split("_");
		if(stepSplit[0].split("-")[0].equals("L"))
		{
			PopUpWindowBeans stepLoopBean = StepLoopBean.getInstaceOfClass();
			PopUpWindow popUpObj = PopUpWindow.getInstance();
			try {
				popUpObj.prepareStage(stepLoopBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FXMLLoader loader = stepLoopBean.getLoader();
			StepLoopControl controller = loader.getController();
			
			controller.setFormStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[1]).getStepName());
			controller.setToStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[0].replaceFirst("L-", "")).getStepName());
			controller.setConditionTxt(StepHierarchyMapping.pathAndItsProperties.get(polygon.getId()).getPathCondition());
		} else
		{
			PopUpWindowBeans stepConnectorBean = StepConnectorBean.getInstaceOfClass();
			PopUpWindow popUpObj = PopUpWindow.getInstance();
			try {
				popUpObj.prepareStage(stepConnectorBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FXMLLoader loader = stepConnectorBean.getLoader();
			StepConnectorControl controller = loader.getController();
			controller.setFormStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[0]).getStepName());
			controller.setToStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[1]).getStepName());
			controller.setConditionTxt(StepHierarchyMapping.pathAndItsProperties.get(polygon.getId()).getPathCondition());
		}
	}
	
	protected void createStageFunc(Path path)
	{
		String[] stepSplit = path.getId().split("_");
		if(stepSplit[0].split("-")[0].equals("L"))
		{
			PopUpWindowBeans stepLoopBean = StepLoopBean.getInstaceOfClass();
			PopUpWindow popUpObj = PopUpWindow.getInstance();
			try {
				popUpObj.prepareStage(stepLoopBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FXMLLoader loader = stepLoopBean.getLoader();
			StepLoopControl controller = loader.getController();
			
			controller.setFormStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[1]).getStepName());
			controller.setToStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[0].replaceFirst("L-", "")).getStepName());
			controller.setConditionTxt(StepHierarchyMapping.pathAndItsProperties.get(path.getId()).getPathCondition());
		} else
		{
			PopUpWindowBeans stepConnectorBean = StepConnectorBean.getInstaceOfClass();
			PopUpWindow popUpObj = PopUpWindow.getInstance();
			try {
				popUpObj.prepareStage(stepConnectorBean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FXMLLoader loader = stepConnectorBean.getLoader();
			StepConnectorControl controller = loader.getController();
			controller.setFormStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[0]).getStepName());
			controller.setToStep(StepHierarchyMapping.stepIdAndItsProperties.get(stepSplit[1]).getStepName());
			controller.setConditionTxt(StepHierarchyMapping.pathAndItsProperties.get(path.getId()).getPathCondition());
		}
	}
}
