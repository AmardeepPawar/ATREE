package caseDesigner;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;

public class PathProperties {
	private String pathName;
	private String parentStep;
	private String childStep;
	private String pathCondition;
	private Path path;
	private int totalElmInPath;
	private Pane pathContainer;
	
	public Pane getPathContainer() {
		return pathContainer;
	}

	public void setPathContainer(Pane pathContainer) {
		this.pathContainer = pathContainer;
	}

	public int getTotalElmInPath() {
		return totalElmInPath;
	}

	public void setTotalElmInPath(int totalElmInPath) {
		this.totalElmInPath = totalElmInPath;
	}

	public String getPathName() {
		return pathName;
	}
	
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	public String getParentStep() {
		return parentStep;
	}
	
	public void setParentStep(String parentStep) {
		this.parentStep = parentStep;
	}
	
	public String getChildStep() {
		return childStep;
	}
	
	public void setChildStep(String childStep) {
		this.childStep = childStep;
	}
	
	public String getPathCondition() {
		return pathCondition;
	}
	
	public void setPathCondition(String pathCondition) {
		this.pathCondition = pathCondition;
	}
	
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
}
