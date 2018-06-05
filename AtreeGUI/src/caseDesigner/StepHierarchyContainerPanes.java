package caseDesigner;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StepHierarchyContainerPanes {
    static ScrollPane scrollPaneToStepHirarchyContVBox;   
    static VBox completeStepHirarchyContVBox;
    static Pane paneInsideScrollPaneToStepHirarchy;
    
	public static ScrollPane getScrollPaneToStepHirarchyContVBox() {
		return scrollPaneToStepHirarchyContVBox;
	}
	public static void setScrollPaneToStepHirarchyContVBox(ScrollPane scrollPaneToStepHirarchyContVBox) {
		StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox = scrollPaneToStepHirarchyContVBox;
	}
	public static VBox getCompleteStepHirarchyContVBox() {
		return completeStepHirarchyContVBox;
	}
	public static void setCompleteStepHirarchyContVBox(VBox completeStepHirarchyContVBox) {
		StepHierarchyContainerPanes.completeStepHirarchyContVBox = completeStepHirarchyContVBox;
	}
	public static Pane getPaneInsideScrollPaneToStepHirarchy() {
		return paneInsideScrollPaneToStepHirarchy;
	}
	public static void setPaneInsideScrollPaneToStepHirarchy(Pane paneInsideScrollPaneToStepHirarchy) {
		StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy = paneInsideScrollPaneToStepHirarchy;
	}    
}
