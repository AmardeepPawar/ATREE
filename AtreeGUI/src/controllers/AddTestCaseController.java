package controllers;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.ATreeWorkSpace;
import application.DisplayExceptions;
import application.PopUpWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
//import application.GUIController;
import popUpWindowBeans.AddTestCaseBean;

public class AddTestCaseController implements AtreeSingleStageGUIController {
	@FXML
	Button okbutton = new Button();
	@FXML
	Button cancelbutton = new Button();
	@FXML
	TextField testcasename = new TextField();
	@FXML
	Label addStepMsgBx = new Label();
	@FXML
	HBox labelHBox = new HBox();
	@FXML
	Label noteId = new Label();
	
	static String stestcasename;
	static File atryFile;
	public static Stage stage;
	PopUpWindow popUpWinObj = PopUpWindow.getInstance();
	DisplayExceptions popUpWinObj1 = DisplayExceptions.getInstance();
	
	public void initialize() {

		addStepMsgBx.setMaxWidth(300);
		addStepMsgBx.setMaxHeight(200);
		// GUIController mainController = new GUIController();
		okbutton.setOnAction(e -> {
			if (!testcasename.getText().equals("")) {
				String workSpace = ATreeWorkSpace.getAtreeWorkSpace();
				String testCaseName = workSpace + "\\" + testcasename.getText() + ".atry";
				stestcasename = testcasename.getText();
				boolean stringmatch = Pattern.matches("^[a-zA-Z][a-zA-Z0-9_-]*", stestcasename);
				atryFile = new File(testCaseName);
				stage = (Stage) cancelbutton.getScene().getWindow();
				if (!atryFile.exists() && stringmatch) {
					try {
						fileCreator();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						addStepMsgBx.setMaxWidth(350);
						labelHBox.setPrefHeight(55);						
						if((AddTestCaseBean.getInstaceOfClass().getHeight() + 50) == PopUpWindow.getPopUPStage().getHeight()){
						popUpWinObj.setStageHeight(PopUpWindow.getPopUPStage().getHeight() + 30);
						}
						if (stringmatch) {
							noteId.setText("Note: ");
							addStepMsgBx.setText("\"" + testCaseName + "\" test case already exist.");
						} else {
							noteId.setText("Note: ");
							addStepMsgBx.setText(
									"Test case name must start with alphabet and only \"_\" and \"-\" are allowed characters in test case name.");
						}
						addStepMsgBx.setWrapText(true);
						addStepMsgBx.setTextAlignment(TextAlignment.JUSTIFY);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		cancelbutton.setOnAction(e -> {
			stage = (Stage) cancelbutton.getScene().getWindow();
			stage.close();
		});
	}

	public void setAddMsgBox(String msg) {
		addStepMsgBx.setText(msg);
	}

	public void fileCreator() throws IOException {
		try {
			atryFile.createNewFile();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element rootElement = doc.createElement("atreeNS:aTreeTestCase");
			doc.appendChild(rootElement);
			Attr attrType = doc.createAttribute("xmlns:atreeNS");
			attrType.setValue("http://atreeSoftTest.com");
			rootElement.setAttributeNode(attrType);
			Element testCaseNameElement = doc.createElement("atreeNS:testCaseName");
			rootElement.appendChild(testCaseNameElement);
			testCaseNameElement.setTextContent(stestcasename);
			Element testCaseTypeElement = doc.createElement("atreeNS:testCaseType");
			rootElement.appendChild(testCaseTypeElement);
			testCaseTypeElement.setTextContent("simple");
			Element stepHierarchyElement = doc.createElement("atreeNS:stepHierarchy");
			rootElement.appendChild(stepHierarchyElement);
			Element startStepElement = doc.createElement("atreeNS:start");
			Attr startStepIdAttr = doc.createAttribute("id");
			startStepIdAttr.setValue("START-0");
			startStepElement.setAttributeNode(startStepIdAttr);
			stepHierarchyElement.appendChild(startStepElement);
			Element newStepNameEle = doc.createElement("atreeNS:stepName");
			newStepNameEle.setTextContent("START");
			Element connectEle = doc.createElement("atreeNS:siblingSteps");
			Element extraConnectionElement = doc.createElement("atreeNS:extraConnections");
			startStepElement.appendChild(newStepNameEle);
			startStepElement.appendChild(connectEle);
			rootElement.appendChild(extraConnectionElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(atryFile);
			transformer.transform(source, result);
			GUIController.addTestCase(stestcasename);
			stage.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();			
			popUpWinObj1.setException(e1.getMessage());			
		}
		
	}

	@Override
	public void clearElementsTextValues() {
		testcasename.clear();
		addStepMsgBx.setText("");
		noteId.setText("");
		labelHBox.setPrefHeight(5);
	}
	
	public void message()
	{
			
	}

	@Override
	public void setElementsTextValues() {
		// TODO Auto-generated method stub

	}
}
