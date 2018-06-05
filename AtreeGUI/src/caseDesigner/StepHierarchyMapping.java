package caseDesigner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

public class StepHierarchyMapping {
    static Map<String,String> stepNameAndItsId = new HashMap<String,String>();
    static Map<String,List<Path>> sourceStepAndListOfPath = new HashMap<String,List<Path>>();
    static Map<String,List<Path>> destinationStepAndListOfPath = new HashMap<String,List<Path>>();
    static Map<String,Path> stepsConnectPath = new HashMap<String,Path>();
    static Map<Integer,String> columnNumberOccupied = new HashMap<Integer,String>();
    static Map<Integer,String> tmpColumnNumberOccupied = new HashMap<Integer,String>();
    static Map<String,String> rowNumberOccupied = new HashMap<String,String>();
    static Map<String,String> pathIndexOfStepAsSource = new HashMap<String,String>();
    static Map<String,String> pathIndexOfStepAsDestination = new HashMap<String,String>();  
    static Map<String,StepProperties> stepIdAndItsProperties = new HashMap<String,StepProperties>();  
    static Map<Integer,RowProperties> rowAndItsProperties = new HashMap<Integer,RowProperties>();
    static Map<String,PathProperties> pathAndItsProperties = new HashMap<String,PathProperties>();
    static Map<String,Polygon> idAndPolygon = new HashMap<String,Polygon>();
    static List<String> connectorList;
    
	public static Map<String, Polygon> getIdAndPolygon() {
		return idAndPolygon;
	}
	public static void setIdAndPolygon(Map<String, Polygon> idAndPolygon) {
		StepHierarchyMapping.idAndPolygon = idAndPolygon;
	}
	public static Map<String, String> getStepNameAndItsId() {
		return stepNameAndItsId;
	}
	public static void setStepNameAndItsId(Map<String, String> stepNameAndItsId) {
		StepHierarchyMapping.stepNameAndItsId = stepNameAndItsId;
	}
	public static Map<String, List<Path>> getSourceStepAndListOfPath() {
		return sourceStepAndListOfPath;
	}
	public static void setSourceStepAndListOfPath(Map<String, List<Path>> sourceStepAndListOfPath) {
		StepHierarchyMapping.sourceStepAndListOfPath = sourceStepAndListOfPath;
	}
	public static Map<String, List<Path>> getDestinationStepAndListOfPath() {
		return destinationStepAndListOfPath;
	}
	public static void setDestinationStepAndListOfPath(Map<String, List<Path>> destinationStepAndListOfPath) {
		StepHierarchyMapping.destinationStepAndListOfPath = destinationStepAndListOfPath;
	}
	public static Map<String, Path> getStepsConnectPath() {
		return stepsConnectPath;
	}
	public static void setStepsConnectPath(Map<String, Path> stepsConnectPath) {
		StepHierarchyMapping.stepsConnectPath = stepsConnectPath;
	}
	public static Map<Integer, String> getColumnNumberOccupied() {
		return columnNumberOccupied;
	}
	public static void setColumnNumberOccupied(Map<Integer, String> columnNumberOccupied) {
		StepHierarchyMapping.columnNumberOccupied = columnNumberOccupied;
	}
	public static Map<Integer, String> getTmpColumnNumberOccupied() {
		return tmpColumnNumberOccupied;
	}
	public static void setTmpColumnNumberOccupied(Map<Integer, String> tmpColumnNumberOccupied) {
		StepHierarchyMapping.tmpColumnNumberOccupied = tmpColumnNumberOccupied;
	}
	public static Map<String, String> getRowNumberOccupied() {
		return rowNumberOccupied;
	}
	public static void setRowNumberOccupied(Map<String, String> rowNumberOccupied) {
		StepHierarchyMapping.rowNumberOccupied = rowNumberOccupied;
	}
	public static Map<String, String> getPathIndexOfStepAsSource() {
		return pathIndexOfStepAsSource;
	}
	public static void setPathIndexOfStepAsSource(Map<String, String> pathIndexOfStepAsSource) {
		StepHierarchyMapping.pathIndexOfStepAsSource = pathIndexOfStepAsSource;
	}
	public static Map<String, String> getPathIndexOfStepAsDestination() {
		return pathIndexOfStepAsDestination;
	}
	public static void setPathIndexOfStepAsDestination(Map<String, String> pathIndexOfStepAsDestination) {
		StepHierarchyMapping.pathIndexOfStepAsDestination = pathIndexOfStepAsDestination;
	}
	public static Map<String, StepProperties> getStepIdAndItsProperties() {
		return stepIdAndItsProperties;
	}
	public static void setStepIdAndItsProperties(Map<String, StepProperties> stepIdAndItsProperties) {
		StepHierarchyMapping.stepIdAndItsProperties = stepIdAndItsProperties;
	}
	public static Map<Integer, RowProperties> getRowAndItsProperties() {
		return rowAndItsProperties;
	}
	public static void setRowAndItsProperties(Map<Integer, RowProperties> rowAndItsProperties) {
		StepHierarchyMapping.rowAndItsProperties = rowAndItsProperties;
	}
	public static Map<String, PathProperties> getPathAndItsProperties() {
		return pathAndItsProperties;
	}
	public static void setPathAndItsProperties(Map<String, PathProperties> pathAndItsProperties) {
		StepHierarchyMapping.pathAndItsProperties = pathAndItsProperties;
	}
	public static List<String> getConnectorList() {
		return connectorList;
	}
	public static void setConnectorList(List<String> connectorList) {
		StepHierarchyMapping.connectorList = connectorList;
	}  

}
