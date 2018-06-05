package caseDesigner;



import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StepHierarchyCreator {

    static Button createLoop;

    SetStartStep startStepSetup = new SetStartStep();
    AddStepClass addStepObj = new AddStepClass();
    DeleteStepClass deleteStepObj = new DeleteStepClass();
    ConnectTwoStepsClass connectStepObj = new ConnectTwoStepsClass();
    CreateLoop CreateLoopObj = CreateLoop.getInstance();
    
    public void addStepToHierarchy()
    {
    	addStepObj.addChildStepMethod();
    }
    
    public void deleteStepToHierarchy()
    {
    	deleteStepObj.deleteChildStepMethod();
    }
    
    public void connectStepsInHierarchy()
    {
    	connectStepObj.connectTwoStepMethod();
    }
    
    public void crtLoopInHierarchy()
    {
    	CreateLoopObj.loopBtwTwoStepMethod();
    }
    
    public void hierarchyCreator()
    {
		createLoop = new Button("Create Loop");

	/*	stepHierarchyContainerPanes.completeStepHirarchyContVBox = new VBox();
		stepHierarchyContainerPanes.completeStepHirarchyContVBox.setAlignment(Pos.CENTER);        
	    
		stepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox = new ScrollPane();        
        VBox.setVgrow(stepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox, Priority.ALWAYS);
        stepHierarchyContainerPanes.completeStepHirarchyContVBox.getChildren().add(stepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox);
        
        stepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy = new Pane();      
               
        startStepSetup.setEffectsToStartButton(); */
        createLoop.setLayoutX(280);
        setTheContentOfPane();
    }  
    
    public static void setTheContentOfPane()
    {
        VBox paneContaier = new VBox();
        StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().addAll(SetStartStep.startpane);
        paneContaier.getChildren().add(StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy);
        VBox.setMargin(StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy, new Insets(0, 10, 10, 20));

       // StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setContent(StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy);
        StepHierarchyContainerPanes.scrollPaneToStepHirarchyContVBox.setContent(paneContaier);
        if(!CurrentStatusIndicatorVariables.selectedStepNode.getId().equals("START-0"))
        {
		 CurrentStatusIndicatorVariables.selectedStepNode = null;
        }
    }
  
	public VBox getBox()
	{
		return StepHierarchyContainerPanes.completeStepHirarchyContVBox;
	}
}