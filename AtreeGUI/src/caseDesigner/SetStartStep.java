package caseDesigner;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SetStartStep {

	static VBox startpane;
	CurrentStatusIndicatorVariables curStatus = new CurrentStatusIndicatorVariables();
	StepHierarchyContainerPanes contPanes = new StepHierarchyContainerPanes();
	StepHierarchyMapping Mapping = new StepHierarchyMapping();
	StepProperties startStepProp = new StepProperties();

	// public void setEffectsToStartButton()
	public void setUpTheStartButton() {
		startpane = new VBox();
		// startpane.setEffect(new DropShadow());
		startpane.setStyle(
				"-fx-background-color: linear-gradient( #00FF00 , #32CD32);-fx-background-radius: 10.0;-fx-border-width:1; -fx-border-color:#6B8E23;-fx-border-radius:6 6 6 6;-fx-width:80;-fx-height:10;");
		startpane.setLayoutX(80);
		startpane.setLayoutY(30);
		startpane.setId("START-0");

		Label start = new Label("START");
		start.setAlignment(Pos.CENTER);
		start.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
		start.setPrefHeight(25);
		start.setPrefWidth(80);
		startpane.getChildren().add(start);
		HBox siblingContainer = new HBox();

		startStepProp.setStepChildrenCount(0);
		startStepProp.setStepId("START-0");
		startStepProp.setStepName("START");
		startStepProp.setStepChildMaxIndex(0);
		startStepProp.setStepHeight(27);
		startStepProp.setStepWidth(80);
		startStepProp.setSiblingHBoxWidth(0);
		startStepProp.setxPos(60);
		startStepProp.setyPos(30);

		startStepProp.setSiblingHBoxOfStep(siblingContainer);
		startStepProp.setStepPane(StepHierarchyContainerPanes.getPaneInsideScrollPaneToStepHirarchy());
		startStepProp.setStepNameAndModuleVBox(startpane);
		Map<String, StepProperties> startStepPropMap = StepHierarchyMapping.getStepIdAndItsProperties();
		startStepPropMap.put("START-0", startStepProp);
		StepHierarchyMapping.setStepIdAndItsProperties(startStepPropMap);
		StepHierarchyMapping.stepNameAndItsId.put("START", "START-0");

		CurrentStatusIndicatorVariables.setSelectedStepNode(startpane);

		startpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {

				if (CurrentStatusIndicatorVariables.selectedModuleId != null) {
					CurrentStatusIndicatorVariables.selModuleIcon.setEffect(null);
					CurrentStatusIndicatorVariables.selModuleIcon = null;
					CurrentStatusIndicatorVariables.selectedModuleId = null;
				}

				if (CurrentStatusIndicatorVariables.selectedPath != null) {
					CurrentStatusIndicatorVariables.selectedPath.setEffect(null);
				}
				startpane.setEffect(new DropShadow());
				startpane.setStyle(
						"-fx-background-color: linear-gradient( #00FF00 , #32CD32);-fx-background-radius: 10.0;-fx-border-width:1; -fx-border-color:#6B8E23;-fx-border-radius:6 6 6 6; ");

				if (CurrentStatusIndicatorVariables.selectedStepNode != null
						&& CurrentStatusIndicatorVariables.selectedStepNode != startpane) {
					CurrentStatusIndicatorVariables.selectedStepNode.setEffect(null);
					if (CurrentStatusIndicatorVariables.selectedStepNode != startpane) {
						CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(0).setStyle(
								"-fx-border-width:2; -fx-border-color:#202020;-fx-border-radius:6 6 0 0;-fx-padding: 3 0 3 20;-fx-background-color: linear-gradient(#CD5C5C,#202020 );    -fx-background-radius: 5 5 0 0;    -fx-background-insets: 0, 1;");
					}
				}
				CurrentStatusIndicatorVariables.selectedStepNode = startpane;
			}
		});
	}
}
