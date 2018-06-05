package caseDesigner;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;

public class CurrentStatusIndicatorVariables {


	/*    static String sourceStepName;
    static String destinationStepName;*/
    static int currentStepIndex;
    static String status = null;
    static String selectedStepId;
    static VBox previousStepNode;
    static String selectedModuleId;
    static VBox selectedStepNode = new VBox();
    static Path selectedPath = new Path();
    static Path selectedLoopPath = new Path();
    static ImageView selModuleIcon = new ImageView();
    
	public static VBox getPreviousStepNode() {
		return previousStepNode;
	}
	public static void setPreviousStepNode(VBox previousStepNode) {
		CurrentStatusIndicatorVariables.previousStepNode = previousStepNode;
	}
	public static VBox getSelectedStepNode() {
		return selectedStepNode;
	}
	
	
	public static ImageView getSelModuleIcon() {
		return selModuleIcon;
	}
	public static void setSelModuleIcon(ImageView selModuleIcon) {
		CurrentStatusIndicatorVariables.selModuleIcon = selModuleIcon;
	}
	public static void setSelectedStepNode(VBox selectedStepNode) {
		CurrentStatusIndicatorVariables.selectedStepNode = selectedStepNode;
	}
	public static Path getSelectedPath() {
		return selectedPath;
	}
	public static void setSelectedPath(Path selectedPath) {
		CurrentStatusIndicatorVariables.selectedPath = selectedPath;
	}
/*	public static String getSourceStepName() {
		return sourceStepName;
	}
	public static void setSourceStepName(String sourceStepName) {
		CurrentStatusIndicatorVariables.sourceStepName = sourceStepName;
	}
	public static String getDestinationStepName() {
		return destinationStepName;
	}
	public static void setDestinationStepName(String destinationStepName) {
		CurrentStatusIndicatorVariables.destinationStepName = destinationStepName;
	}*/
	public static int getCurrentStepIndex() {
		return currentStepIndex;
	}
	public static void setCurrentStepIndex(int currentStepIndex) {
		CurrentStatusIndicatorVariables.currentStepIndex = currentStepIndex;
	}
	public static String getStatus() {
		return status;
	}
	public static void setStatus(String status) {
		CurrentStatusIndicatorVariables.status = status;
	}
	public static String getSelectedStepId() {
		return selectedStepId;
	}
	public static void setSelectedStepId(String selectedStepId) {
		CurrentStatusIndicatorVariables.selectedStepId = selectedStepId;
	}
	
	public static String getSelectedModuleId() {
		return selectedModuleId;
	}
	
	public static void setSelectedModuleId(String selectedModuleId) {
		CurrentStatusIndicatorVariables.selectedModuleId = selectedModuleId;
	}
	
	public static void resetStepParameters()
	{
/*	    sourceStepName = null;
	    destinationStepName = null;*/
	    status = null;
	    selectedStepId = null;
	    previousStepNode = null;
	    selectedStepNode = null;
	    selectedPath = null;
	    selectedModuleId = null;
	}
}
