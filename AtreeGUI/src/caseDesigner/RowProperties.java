package caseDesigner;

import java.util.List;

public class RowProperties {
	private int rowNum;
	private int totalStepsForRow;
	private List<String> rowMember;
	private double rowHeight;
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public int getTotalStepsForRow() {
		return totalStepsForRow;
	}
	public void setTotalStepsForRow(int totalStepsForRow) {
		this.totalStepsForRow = totalStepsForRow;
	}
	
	public List<String> getRowMember() {
		return rowMember;
	}
	public void setRowMember(List<String> rowMember) {
		this.rowMember = rowMember;
	}
	public double getRowHeight() {
		return rowHeight;
	}
	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
	}	

}
