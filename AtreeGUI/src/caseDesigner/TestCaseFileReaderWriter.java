package caseDesigner;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import application.PopUpWindow;
import application.SelectedCase;
import controllers.MessageBoxController;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import popUpWindowBeans.MessageBoxBean;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.*;

public class TestCaseFileReaderWriter {
	DocumentBuilderFactory factory;
	String testCaseName;
	String testCaseType;
	SetStartStep startStepSetup = new SetStartStep();
	AddStepClass addStepFromFileObj = new AddStepClass();
	ConnectTwoStepsClass connectStepObj = new ConnectTwoStepsClass();
	static Node extraConnections;
	static Document doc;
	static File inputFile;

	public void readTestCaseXMLFile(String testCaseFile) throws ParserConfigurationException {
		clearMappingVariable();
		inputFile = new File(testCaseFile);
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		AddStepClass.modNotFnd.clear();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			doc = builder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			NodeList testCaseParameters = root.getChildNodes();
			for (int i = 0; i < testCaseParameters.getLength(); i++) {
				Node childNode = testCaseParameters.item(i);
				// System.out.println(childNode.getLocalName());
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					if (childNode.getNodeName() == "atreeNS:testCaseName") {
						testCaseName = childNode.getTextContent();
					}
					if (childNode.getNodeName() == "atreeNS:testCaseType") {
						testCaseType = childNode.getTextContent();
					}
					if (childNode.getNodeName() == "atreeNS:stepHierarchy") {
						createStepHierarchy(childNode);
					}
				}
			}
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createStepHierarchy(Node stepHierarchyNode) {
		NodeList stepHierarchyChildList = stepHierarchyNode.getChildNodes();
		for (int i = 0; i < stepHierarchyChildList.getLength(); i++) {
			Node stepHierarchyChildNode = stepHierarchyChildList.item(i);
			if (stepHierarchyChildNode.getNodeName() == "atreeNS:start") {
				Node stepHierarchyStartNode = stepHierarchyChildList.item(i);

				startStepSetup.setUpTheStartButton();
				Attr stepId = (Attr) stepHierarchyStartNode.getAttributes().getNamedItem("id");
				for (int j = 0; j < stepHierarchyStartNode.getChildNodes().getLength(); j++) {
					if (stepHierarchyStartNode.getChildNodes().item(j).getNodeName().equals("atreeNS:siblingSteps")) {
						subStepReader(stepHierarchyStartNode.getChildNodes().item(j), stepId.getValue());
						loopLineConnector(stepHierarchyStartNode.getChildNodes().item(j), stepId.getValue());
						stepConnectorFunc(stepHierarchyStartNode.getChildNodes().item(j), stepId.getValue());
					}
				}
			}
		}

		if (!SelectedCase.selectedTestCase.equals(SelectedCase.prevSelectedTestCase)) {
			if (AddStepClass.modNotFnd.size() > 0) {
				PopUpWindow popUpWinObj = PopUpWindow.getInstance();
				MessageBoxBean bean = MessageBoxBean.getInstaceOfClass();
				try {
					popUpWinObj.prepareStage(bean);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String modName : AddStepClass.modNotFnd) {
					modString = modString + i + ". " + modName + "\n";
					i = i + 1;
				}
				MessageBoxController fxmlLdr = bean.getLoader().getController();
				fxmlLdr.clearElementsTextValues();
				fxmlLdr.setMessageToWindow("Following modules are not installed on system. \n\n" + modString);
			}
		}
		SelectedCase.prevSelectedTestCase = SelectedCase.selectedTestCase;
	}

	public void writeTestCaseXMLFile(String stepName, String stepId) throws TransformerException {
		String selectedStepId = CurrentStatusIndicatorVariables.selectedStepId;
		String xPathQuery = "//*[@id='" + selectedStepId + "']";
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate(xPathQuery, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList childrenOfSelectedStep = stepNode.getChildNodes();
		Element siblingElement = null;
		for (int i = 0; i < childrenOfSelectedStep.getLength(); i++) {
			Node childNode = childrenOfSelectedStep.item(i);
			if (childNode.getNodeName() == "atreeNS:siblingSteps") {
				siblingElement = (Element) childNode;
			}
		}
		if (siblingElement == null) {
			Element stepElement = (Element) stepNode;
			Element siblingEle = doc.createElement("atreeNS:siblingSteps");
			stepElement.appendChild(siblingEle);
			Element newStepEle = doc.createElement("atreeNS:step");
			siblingEle.appendChild(newStepEle);
			Attr attrType = doc.createAttribute("id");
			attrType.setValue(stepId);
			newStepEle.setAttributeNode(attrType);
			Element newStepNameEle = doc.createElement("atreeNS:stepName");
			Element stepMod = doc.createElement("atreeNS:stepModule");
			Element modId = doc.createElement("moduleId");
			stepMod.appendChild(modId);
			newStepNameEle.setTextContent(stepName);
			Element parentListEle = doc.createElement("atreeNS:parentStepsList");
			Element gotoListEle = doc.createElement("atreeNS:gotoSteps");
			Element parentEle = doc.createElement("atreeNS:parentStep");
			Attr nameAttr = doc.createAttribute("name");
			nameAttr.setValue(selectedStepId);
			parentEle.setAttributeNode(nameAttr);
			Element directCnFlag = doc.createElement("atreeNS:dflag");
			// sparentEle.setTextContent(selectedStepId);
			parentEle.appendChild(directCnFlag);
			directCnFlag.setTextContent("Y");
			Element parentCondEle = doc.createElement("atreeNS:condition");
			newStepEle.appendChild(newStepNameEle);
			newStepEle.appendChild(stepMod);
			newStepEle.appendChild(parentListEle);
			newStepEle.appendChild(gotoListEle);
			parentListEle.appendChild(parentEle);
			parentEle.appendChild(parentCondEle);
		} else {
			Element newStepEle = doc.createElement("atreeNS:step");
			siblingElement.appendChild(newStepEle);
			Attr attrType = doc.createAttribute("id");
			attrType.setValue(stepId);
			newStepEle.setAttributeNode(attrType);
			Element newStepNameEle = doc.createElement("atreeNS:stepName");
			Element stepMod = doc.createElement("atreeNS:stepModule");
			Element modId = doc.createElement("moduleId");
			stepMod.appendChild(modId);
			newStepNameEle.setTextContent(stepName);
			Element parentListEle = doc.createElement("atreeNS:parentStepsList");
			Element gotoListEle = doc.createElement("atreeNS:gotoSteps");
			Element parentEle = doc.createElement("atreeNS:parentStep");
			Attr nameAttr = doc.createAttribute("name");
			nameAttr.setValue(selectedStepId);
			parentEle.setAttributeNode(nameAttr);
			Element directCnFlag = doc.createElement("atreeNS:dflag");
			parentEle.appendChild(directCnFlag);
			directCnFlag.setTextContent("Y");
			// parentEle.setTextContent(selectedStepId);
			Element parentCondEle = doc.createElement("atreeNS:condition");
			// connectEle.setTextContent("y");
			// Element condintionEle = doc.createElement("atreeNS:condition");
			// connectEle.appendChild(condintionEle);
			newStepEle.appendChild(newStepNameEle);
			newStepEle.appendChild(stepMod);
			newStepEle.appendChild(parentListEle);
			newStepEle.appendChild(gotoListEle);
			parentListEle.appendChild(parentEle);
			parentEle.appendChild(parentCondEle);
		}
		try {
			saveDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addModuleToStep(String stepName, String modName) {
		String xPathQueryForParent = "//*[@id='" + stepName + "']";
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate(xPathQueryForParent, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList stepList = stepNode.getChildNodes();
		Node modIdNode = null;
		for (int i = 0; i < stepList.getLength(); i++) {
			Node childNode = stepList.item(i);
			if (childNode.getNodeName() == "atreeNS:stepModule") {
				modIdNode = childNode.getChildNodes().item(0);
			}
		}
		modIdNode.setTextContent(modName);
		try {
			saveDocument();
		} catch (TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteStepFromTestCaseXMLFile(String parentId, String deleteStepId)
			throws TransformerException, ParserConfigurationException {
		if (CurrentStatusIndicatorVariables.selectedModuleId == null) {
			String xPathQueryForParent = "//*[@id='" + parentId + "']";
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node parentStepNode = null;
			try {
				parentStepNode = (Node) xpath.evaluate(xPathQueryForParent, doc, XPathConstants.NODE);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NodeList parentStepList = parentStepNode.getChildNodes();
			Node siblingNode = null;
			for (int i = 0; i < parentStepList.getLength(); i++) {
				Node childNode = parentStepList.item(i);
				if (childNode.getNodeName() == "atreeNS:siblingSteps") {
					siblingNode = childNode;
				}
			}
			NodeList siblingNodeStepList = siblingNode.getChildNodes();
			for (int i = 0; i < siblingNodeStepList.getLength(); i++) {
				Node deleteStepNode = siblingNodeStepList.item(i);
				if (deleteStepNode.getAttributes().getNamedItem("id").getNodeValue().equals(deleteStepId)) {
					siblingNode.removeChild(deleteStepNode);
				}
			}
		} else {
			String xPathQueryForDelStep = "//*[@id='" + deleteStepId + "']";
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node delStepNode = null;
			try {
				delStepNode = (Node) xpath.evaluate(xPathQueryForDelStep, doc, XPathConstants.NODE);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NodeList delStepList = delStepNode.getChildNodes();
			for (int i = 0; i < delStepList.getLength(); i++) {
				Node childNode = delStepList.item(i);
				if (childNode.getNodeName() == "atreeNS:stepModule") {
					childNode.getFirstChild().setTextContent("");
				}
			}
		}
		saveDocument();
	}

	public void deletePathFromTestCaseXMLFile(String pathId) throws TransformerException, ParserConfigurationException {
		String[] pathArr;
		if (pathId.split("-").length == 3) {
			pathArr = pathId.split("_");
			String sourceStep = pathArr[0];
			String destStep = pathArr[1];
			String xPathQueryForPath = "//*[@id='" + destStep + "']";
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node pathNode = null;
			try {
				pathNode = (Node) xpath.evaluate(xPathQueryForPath, doc, XPathConstants.NODE);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// NodeList parentStepList = pathNode.getChildNodes();
			if (pathNode != null) {
				NodeList children = pathNode.getChildNodes();

				for (int i = 0; i < children.getLength(); i++) {
					if (children.item(i).getNodeName().equals("atreeNS:parentStepsList")) {
						NodeList parentStepsList = children.item(i).getChildNodes();
						for (int j = 0; j < parentStepsList.getLength(); j++) {
							if (parentStepsList.item(j).getNodeName().equals("atreeNS:parentStep")) {
								if (parentStepsList.item(j).getAttributes().getNamedItem("name").getNodeValue()
										.equals(sourceStep)) {
									children.item(i).removeChild(parentStepsList.item(j));
								}
							}

						}
					}
				}
			}
		} else {
			pathArr = pathId.substring(2).split("_");
			String destStep = pathArr[0];
			String sourceStep = pathArr[1];
			String xPathQueryForPath = "//*[@id='" + destStep + "']";
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node pathNode = null;
			try {
				pathNode = (Node) xpath.evaluate(xPathQueryForPath, doc, XPathConstants.NODE);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// NodeList parentStepList = pathNode.getChildNodes();
			if (pathNode != null) {
				NodeList children = pathNode.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					if (children.item(i).getNodeName().equals("atreeNS:gotoSteps")) {
						NodeList parentStepsList = children.item(i).getChildNodes();
						for (int j = 0; j < parentStepsList.getLength(); j++) {

							if (parentStepsList.item(j).getNodeName().equals("atreeNS:goto")) {

								if (parentStepsList.item(j).getAttributes().getNamedItem("stepName").getNodeValue()
										.equals(sourceStep)) {
									children.item(i).removeChild(parentStepsList.item(j));
								}
							}
						}
					}
				}
			}
		}
		saveDocument();
	}

	public void displayHierarchy() throws ParserConfigurationException {
		// clearMappingVariable();
		StepHierarchyContainerPanes.paneInsideScrollPaneToStepHirarchy.getChildren().clear();
		readTestCaseXMLFile(inputFile.getAbsolutePath());
		StepHierarchyCreator.setTheContentOfPane();
	}

	void clearMappingVariable() {
		StepHierarchyMapping.stepNameAndItsId.clear();
		StepHierarchyMapping.sourceStepAndListOfPath.clear();
		StepHierarchyMapping.destinationStepAndListOfPath.clear();
		StepHierarchyMapping.stepsConnectPath.clear();
		StepHierarchyMapping.columnNumberOccupied.clear();
		StepHierarchyMapping.tmpColumnNumberOccupied.clear();
		StepHierarchyMapping.rowNumberOccupied.clear();
		StepHierarchyMapping.pathIndexOfStepAsSource.clear();
		StepHierarchyMapping.pathIndexOfStepAsDestination.clear();
		StepHierarchyMapping.stepIdAndItsProperties.clear();
		StepHierarchyMapping.rowAndItsProperties.clear();
		StepHierarchyMapping.pathAndItsProperties.clear();
	}

	String modString = "";
	int i = 0;

	private void subStepReader(Node siblingStepsNode, String parentStepId) {
		VBox selectedStepNode;
		NodeList modDetail;
		String moduleId = null;
		selectedStepNode = new VBox();
		modString = "";
		i = 1;
		selectedStepNode = CurrentStatusIndicatorVariables.selectedStepNode;
		NodeList chinderNodes = siblingStepsNode.getChildNodes();
		for (int j = 0; j < chinderNodes.getLength(); j++) {
			Node ChildNode = chinderNodes.item(j);
			if (ChildNode.getNodeName() == "atreeNS:step") {
				NodeList stepDetailsNodes = ChildNode.getChildNodes();
				Attr stepId = (Attr) ChildNode.getAttributes().getNamedItem("id");
				String stepName = null;
				Node siblingNodeDetails = null;
				for (int k = 0; k < stepDetailsNodes.getLength(); k++) {
					Node stepDetailNode = stepDetailsNodes.item(k);
					if (stepDetailNode.getNodeName() == "atreeNS:stepName") {
						stepName = stepDetailNode.getTextContent();
					}
					if (stepDetailNode.getNodeName() == "atreeNS:stepModule") {
						modDetail = stepDetailNode.getChildNodes();
						moduleId = modDetail.item(0).getTextContent();
					}
					if (stepDetailNode.getNodeName() == "atreeNS:siblingSteps") {
						siblingNodeDetails = stepDetailNode;
					}
				}
				CurrentStatusIndicatorVariables.selectedStepNode = selectedStepNode;
				addStepFromFileObj.functionToAddNewStep(stepId.getValue() + "|" + stepName, "file");
				HBox moduleCont = (HBox) CurrentStatusIndicatorVariables.selectedStepNode.getChildren().get(1);
				if (moduleId != null) {
					addStepFromFileObj.addModulesToStep(moduleCont, moduleId);
				}
				moduleId = null;
				if (siblingNodeDetails != null) {
					subStepReader(siblingNodeDetails, stepId.getValue());
				}
			}
		}
	}

	static void saveDocument() throws TransformerException, ParserConfigurationException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(inputFile);
		transformer.transform(source, result);
	}

	void stepConnectorFunc(Node siblingNode, String srcStepName) {
		NodeList chinderNodes = siblingNode.getChildNodes();
		for (int j = 0; j < chinderNodes.getLength(); j++) {
			Node ChildNode = chinderNodes.item(j);
			if (ChildNode.getNodeName() == "atreeNS:step") {
				NodeList stepDetailsNodes = ChildNode.getChildNodes();
				Attr stepId = (Attr) ChildNode.getAttributes().getNamedItem("id");
				Node siblingNodeDetails = null;
				for (int k = 0; k < stepDetailsNodes.getLength(); k++) {
					Node stepDetailNode = stepDetailsNodes.item(k);
					if (stepDetailNode.getNodeName() == "atreeNS:siblingSteps") {
						siblingNodeDetails = stepDetailNode;
					}
					if (stepDetailNode.getNodeName() == "atreeNS:parentStepsList") {
						readParentListNode(stepDetailNode, stepId.getValue());
					}
				}
				if (siblingNodeDetails != null) {
					stepConnectorFunc(siblingNodeDetails, stepId.getValue());
				}
			}
		}
	}

	void loopLineConnector(Node siblingNode, String srcStepName) {
		// String destStepName = null;
		NodeList chinderNodes = siblingNode.getChildNodes();
		for (int j = 0; j < chinderNodes.getLength(); j++) {
			Node ChildNode = chinderNodes.item(j);
			if (ChildNode.getNodeName() == "atreeNS:step") {
				NodeList stepDetailsNodes = ChildNode.getChildNodes();
				Attr stepId = (Attr) ChildNode.getAttributes().getNamedItem("id");
				Node siblingNodeDetails = null;
				for (int k = 0; k < stepDetailsNodes.getLength(); k++) {
					Node stepDetailNode = stepDetailsNodes.item(k);
					if (stepDetailNode.getNodeName() == "atreeNS:siblingSteps") {
						siblingNodeDetails = stepDetailNode;
					}
					if (stepDetailNode.getNodeName() == "atreeNS:gotoSteps") {
						readGoToStepsNode(stepDetailNode, stepId.getValue());
					}
				}
				if (siblingNodeDetails != null) {
					loopLineConnector(siblingNodeDetails, stepId.getValue());
				}
			}
		}
	}

	public void writeConditionToLoop(String toStepName, String frmStepName, String condition)
			throws TransformerException, ParserConfigurationException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate("//*[@id='" + toStepName + "']", doc, XPathConstants.NODE);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element stepElement = (Element) stepNode;
		Element parentListEle = (Element) stepElement.getElementsByTagName("atreeNS:gotoSteps").item(0);
		NodeList children = parentListEle.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals("atreeNS:goto")) {
				Attr parentStepNm = (Attr) children.item(i).getAttributes().getNamedItem("stepName");
				if (parentStepNm.getTextContent().equals(frmStepName)) {
					Element parentEle = (Element) children.item(i);
					Node condEle = parentEle.getElementsByTagName("atreeNS:condition").item(0);
					condEle.setTextContent(condition);
				}
			}
		}
		saveDocument();
	}

	public void writeConditionToConnection(String toStepName, String frmStepName, String condition)
			throws TransformerException, ParserConfigurationException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate("//*[@id='" + toStepName + "']", doc, XPathConstants.NODE);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element stepElement = (Element) stepNode;
		Element parentListEle = (Element) stepElement.getElementsByTagName("atreeNS:parentStepsList").item(0);
		NodeList children = parentListEle.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals("atreeNS:parentStep")) {
				Attr parentStepNm = (Attr) children.item(i).getAttributes().getNamedItem("name");
				if (parentStepNm.getTextContent().equals(frmStepName)) {
					Element parentEle = (Element) children.item(i);
					Node condEle = parentEle.getElementsByTagName("atreeNS:condition").item(0);
					condEle.setTextContent(condition);
				}
			}
		}
		saveDocument();
	}

	void readGoToStepsNode(Node gotoStepNode, String curStepName) {
		NodeList children = gotoStepNode.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals("atreeNS:goto")) {
				Attr gotoStepNm = (Attr) children.item(i).getAttributes().getNamedItem("stepName");
				CreateLoop classInst = CreateLoop.getInstance();
				Element gotoEle = (Element) children.item(i);
				String conditionStr = "";
				if (gotoEle.getElementsByTagName("atreeNS:condition").item(0) != null) {
					conditionStr = gotoEle.getElementsByTagName("atreeNS:condition").item(0).getTextContent().trim();
				}
				classInst.createLoopConnection(gotoStepNm.getValue(), curStepName, conditionStr);
			}
		}
	}

	void readParentListNode(Node parentListNode, String curStepName) {
		NodeList parentList = parentListNode.getChildNodes();
		Node parntStepName = null;
		boolean dFlag;
		Node dflg = null;
		String conditionStr = "";
		for (int k = 0; k < parentList.getLength(); k++) {
			Node childNode = parentList.item(k);
			if (childNode.getNodeName() == "atreeNS:parentStep") {
				dFlag = false;
				conditionStr = "";
				Element childEle = (Element) childNode;
				dflg = childEle.getElementsByTagName("atreeNS:dflag").item(0);
				if (childEle.getElementsByTagName("atreeNS:condition").item(0) != null) {
					conditionStr = childEle.getElementsByTagName("atreeNS:condition").item(0).getTextContent().trim();
				}

				if (dflg != null) {
					dFlag = true;
				}
				parntStepName = childNode.getAttributes().getNamedItem("name");
				connectStepObj.twoStepConnector(parntStepName.getNodeValue(), curStepName, dFlag, conditionStr);
			}
		}
	}

	public void addStepConnectionInFile(String sourceStep, String destinationStep, boolean dirConnect) {
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate("//*[@id='" + destinationStep + "']", doc, XPathConstants.NODE);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NodeList stepNodeChildren = stepNode.getChildNodes();
		for (int i = 0; i < stepNodeChildren.getLength(); i++) {
			if (stepNodeChildren.item(i).getNodeName().equals("atreeNS:parentStepsList")) {
				Element parentStepEle = doc.createElement("atreeNS:parentStep");
				Attr attrType = doc.createAttribute("name");
				attrType.setValue(sourceStep);
				parentStepEle.setAttributeNode(attrType);
				Element condElement = doc.createElement("atreeNS:condition");
				parentStepEle.appendChild(condElement);
				stepNodeChildren.item(i).appendChild(parentStepEle);
				if (dirConnect) {
					Element directCnFlag = doc.createElement("atreeNS:dflag");
					parentStepEle.appendChild(directCnFlag);
					directCnFlag.setTextContent("Y");
				}
			}
		}
		try {
			saveDocument();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addStepLoopInFile(String frmStep, String toStep) {
		XPath xpath = XPathFactory.newInstance().newXPath();
		int toStpRowNum = Integer.parseInt(toStep.split("-")[1].split("\\.")[0]);
		Node stepNode = null;
		try {
			stepNode = (Node) xpath.evaluate("//*[@id='" + frmStep + "']", doc, XPathConstants.NODE);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		NodeList stepNodeChildren = stepNode.getChildNodes();
		for (int i = 0; i < stepNodeChildren.getLength(); i++) {
			if (stepNodeChildren.item(i).getNodeName().equals("atreeNS:gotoSteps")) {
				NodeList allGotoNd = stepNodeChildren.item(i).getChildNodes();
				Element gotoEle = doc.createElement("atreeNS:goto");
				Attr attrType = doc.createAttribute("stepName");
				attrType.setValue(toStep);
				gotoEle.setAttributeNode(attrType);
				Element condElement = doc.createElement("atreeNS:condition");
				gotoEle.appendChild(condElement);
				if (allGotoNd.getLength() > 0) {
					for (int j = 0; j < allGotoNd.getLength(); j++) {
						Node gotoNd = allGotoNd.item(j);
						String stpNmAttr = gotoNd.getAttributes().getNamedItem("stepName").getTextContent();
						int rowNum = Integer.parseInt(stpNmAttr.split("-")[1].split("\\.")[0]);
						if (rowNum < toStpRowNum) {
							stepNodeChildren.item(i).insertBefore(gotoEle, gotoNd);
						} else {
							stepNodeChildren.item(i).appendChild(gotoEle);
						}
					}
				} else {
					stepNodeChildren.item(i).appendChild(gotoEle);
				}
			}
		}
		try {
			saveDocument();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
