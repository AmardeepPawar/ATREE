package caseDesigner;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;

public class StepProperties {
	private int stepChildrenCount;
	private int stepChildMaxIndex;
	private int stepIndex;

	double xPos;
	double yPos;
	double stepHeight;	
	double stepWidth;
	private Pane stepPane;
	private HBox siblingHBoxOfStep;
	double siblingHBoxWidth;
	private VBox stepNameAndModuleVBox;
	//private HBox stepNameHBox;
	//private HBox stepModuleHBox;
	private Pane parentStep;
	private String stepId;
	private String stepName;
	List<Pane> stepChildrenList = new ArrayList<Pane>();
	List<Path> stepIncomingPaths = new ArrayList<Path>();
	List<Path> stepOutgoingPaths = new ArrayList<Path>();
	
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepDisplayName) {
		this.stepName = stepDisplayName;
	}

	
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepName) {
		this.stepId = stepName;
	}
	
	public int getStepChildrenCount() {
		return stepChildrenCount;
	}
	public void setStepChildrenCount(int stepChildrenCount) {
		this.stepChildrenCount = stepChildrenCount;
	}
	public int getStepChildMaxIndex() {
		return stepChildMaxIndex;
	}
	public void setStepChildMaxIndex(int stepChildMaxIndex) {
		this.stepChildMaxIndex = stepChildMaxIndex;
	}
	public int getStepIndex() {
		return stepIndex;
	}
	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}
	public Pane getStepPane() {
		return stepPane;
	}
	public void setStepPane(Pane stepPane) {
		this.stepPane = stepPane;
	}
	public HBox getSiblingHBoxOfStep() {
		return siblingHBoxOfStep;
	}
	public void setSiblingHBoxOfStep(HBox siblingHBoxOfStep) {
		this.siblingHBoxOfStep = siblingHBoxOfStep;
	}
	public VBox getStepNameAndModuleVBox() {
		return stepNameAndModuleVBox;
	}
	public void setStepNameAndModuleVBox(VBox stepNameAndModuleVBox) {
		this.stepNameAndModuleVBox = stepNameAndModuleVBox;
	}
/*	public HBox getStepNameHBox() {
		return stepNameHBox;
	}
	public void setStepNameHBox(HBox stepNameHBox) {
		this.stepNameHBox = stepNameHBox;
	}
	public HBox getStepModuleHBox() {
		return stepModuleHBox;
	}
	public void setStepModuleHBox(HBox stepModuleHBox) {
		this.stepModuleHBox = stepModuleHBox;
	}
*/
	public Pane getParentStep() {
		return parentStep;
	}
	public void setParentStep(Pane parentStep) {
		this.parentStep = parentStep;
	}
	public List<Pane> getStepChildrenList() {
		return stepChildrenList;
	}
	public void setStepChildrenList(List<Pane> stepChildrenList) {
		this.stepChildrenList = stepChildrenList;
	}
	public List<Path> getStepIncomingPaths() {
		return stepIncomingPaths;
	}
	public void setStepIncomingPaths(List<Path> stepIncomingPaths) {
		this.stepIncomingPaths = stepIncomingPaths;
	}
	public List<Path> getStepOutgoingPaths() {
		return stepOutgoingPaths;
	}
	public void setStepOutgoingPaths(List<Path> stepOutgoingPaths) {
		this.stepOutgoingPaths = stepOutgoingPaths;
	}
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	public double getStepHeight() {
		return stepHeight;
	}
	public void setStepHeight(double stepHeight) {
		this.stepHeight = stepHeight;
	}
	public double getStepWidth() {
		return stepWidth;
	}
	public void setStepWidth(double stepWidth) {
		this.stepWidth = stepWidth;
	}
	public double getSiblingHBoxWidth() {
		return siblingHBoxWidth;
	}
	public void setSiblingHBoxWidth(double siblingHBoxWidth) {
		this.siblingHBoxWidth = siblingHBoxWidth;
	}
}
