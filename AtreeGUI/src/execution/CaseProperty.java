package execution;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CaseProperty {
	Label srNo;
	Label caseName;
	VBox selectionBox;
	VBox runBtn;
	VBox stopBtn;
	VBox startTm;
	VBox endTm;
	VBox result;
	
	public CaseProperty()
	{
	}
	
	public Label getSrNo() {
		return srNo;
	}

	public void setSrNo(Label srNo) {
		this.srNo = srNo;
	}

	public Label getCaseName() {
		return caseName;
	}

	public void setCaseName(Label caseName) {
		this.caseName = caseName;
	}

	public VBox getSelectionBox() {
		return selectionBox;
	}

	public void setSelectionBox(VBox selectionBox) {
		this.selectionBox = selectionBox;
	}

	public VBox getRunBtn() {
		return runBtn;
	}

	public void setRunBtn(VBox runBtn) {
		this.runBtn = runBtn;
	}

	public VBox getStopBtn() {
		return stopBtn;
	}

	public void setStopBtn(VBox stopBtn) {
		this.stopBtn = stopBtn;
	}

	public VBox getStartTm() {
		return startTm;
	}

	public void setStartTm(VBox startTm) {
		this.startTm = startTm;
	}

	public VBox getEndTm() {
		return endTm;
	}

	public void setEndTm(VBox endTm) {
		this.endTm = endTm;
	}

	public VBox getResult() {
		return result;
	}

	public void setResult(VBox result) {
		this.result = result;
	}

	public CaseProperty(Label srNo,Label caseName, VBox selectionBox, VBox runBtn, VBox stopBtn,VBox startTm,VBox endTm,VBox result)
	{
		this.srNo = srNo;
		this.caseName = caseName;
		this.selectionBox = selectionBox;
		this.runBtn = runBtn;
		this.stopBtn = stopBtn;
		this.startTm = startTm;
		this.endTm = endTm;
		this.result = result;
	}
	
}
