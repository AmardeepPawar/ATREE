package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import application.ReadFolder;
import application.SelectedCase;
import caseDesigner.*;
import execution.CaseProperty;
import execution.ExecDashBoardTableUpdateClass;
import application.ATreeWorkSpace;
import application.AtreeFolder;
import application.GetAndDisplayModules;
import application.PopUpWindow;
//import application.guiController.TextFieldTreeCellImpl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import popUpWindowBeans.AddTestCaseBean;
import popUpWindowBeans.DeleteTestCaseBean;
import popUpWindowBeans.MessageBoxBean;
import popUpWindowBeans.PopUpWindowBeans;
import popUpWindowBeans.SelectWorkSpaceBean;

public class GUIController {
	Stage stage;
	
	//#################################################################################
	//# Design Tab 
	//#################################################################################

	@FXML
	public ToolBar stepButtonContainer = new ToolBar();	
	@FXML
	TabPane stepDesignTapPane = new TabPane();
	static Tab stepDesignBlockView = new Tab();
	@FXML
	BorderPane designPageBorderPane = new BorderPane();
	@FXML
	Button addstepbutton = new Button();
	@FXML
	Button connectflowbutton = new Button();
	@FXML
	Button cntLoopLinebutton = new Button();
	@FXML
	Button deletestepbutton = new Button();
	
	
	@FXML
	ScrollPane listOfModulesScr = new ScrollPane();
	@FXML
	public ToolBar ModuleButtonContainer = new ToolBar();
	@FXML
	TilePane listOfModules = new TilePane();
	
	
	@FXML
	public TreeView<String> filefoldertree = new TreeView<String>();
	static TreeItem<String> root = new TreeItem<String>();
	static TreeItem<String> Files = new TreeItem<String>();
	static TreeItem<String> workFolder = new TreeItem<String>();
	static TreeItem<String> selectedTreeItem;
	static TreeItem<String> activeTreeItem;
	static TreeItem<String> preActiveTreeItem = null;
	static TreeItem<String> item = null;
	@FXML
	public ToolBar treecontol = new ToolBar();	
	@FXML
	Button addtestcase = new Button();
	@FXML
	Button deletetestcase = new Button();
	@FXML
	Button changeWStestcase = new Button();
	
	static DropShadow shadow = new DropShadow();
	private static Node folderIcon;
	StepHierarchyCreator hierarchyHanlerObj = new StepHierarchyCreator();

	static String folderName;
	static String fileRegEx = ".+\\.atry$";
	static String selectedItem;
	static String activeItem = null;

	static double clickAndPaneXDifference;
	static double dragStartPos;
	static double startWidthOfTreecontol;
	static double startWidthOfStepButtonContainer;
	static double startWidthOfModuleButtonContainer;
	static double moduleListLayoutX;
	static double moduleListLayoutX1;
	static double widthOfDesignTab = 0;
	static double heightOfDesignTab = 0;
	
	//#################################################################################
	//# Execution Tab
	//#################################################################################
	
	@FXML
	Button browseWorkSpaceBtn = new Button();
	@FXML
	Button addWorkSpaceBtn = new Button();
	@FXML
	TextField workSpaceBox = new TextField();
	@FXML
	TreeTableView<CaseProperty> caseDashboardTable = new TreeTableView<CaseProperty>();

	ReadFolder readfoldObj;
	static int workSpaceCounter = 0;
	static String workSpaceFrExe;
	static ArrayList<String> atreeCseList;

	public void initialize() throws IOException {
		
		folderIcon = new ImageView(
				new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//foldericon.png"));
			
		listOfModulesScr.setHbarPolicy(ScrollBarPolicy.NEVER);
			
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		widthOfDesignTab = primaryScreenBounds.getWidth() - treecontol.getPrefWidth()
				- ModuleButtonContainer.getPrefWidth();
		heightOfDesignTab = primaryScreenBounds.getHeight();
		
		stepDesignBlockView.setGraphic(new Label("Atree Application"));
		stepDesignTapPane.getTabs().add(stepDesignBlockView);
		stepDesignTapPane.setStyle("-fx-border-color: brown;-fx-border-width: 0.2px;");
		
		// #######set Atree Icon to design page#################
		setIconOnDisplay();

		filefoldertree.setRoot(root);
		filefoldertree.setEditable(true);
		filefoldertree.setShowRoot(false);
		filefoldertree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> p) {
				return new TextFieldTreeCellImpl();
			}
		});

		PopUpWindow popUpObj = PopUpWindow.getInstance();
		
		final Tooltip addTooltip = new Tooltip();
		addTooltip.setFont(new Font(12));
		addTooltip.setText("Add new test case");
		addtestcase.setTooltip(addTooltip);
		
		addtestcase.setOnAction(e -> {
			try {
				if(ATreeWorkSpace.getAtreeWorkSpace() != null)
				{
				PopUpWindowBeans addTestCaseBean = AddTestCaseBean.getInstaceOfClass();
				popUpObj.prepareStage(addTestCaseBean);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		final Tooltip delTooltip = new Tooltip();
		delTooltip.setFont(new Font(12));
		delTooltip.setText("Delete test case");
		deletetestcase.setTooltip(delTooltip);	
		
		deletetestcase.setOnAction(e -> {
			try {
				if (GUIController.getSelectedItem() != null) {
					String fileName = GUIController.getSelectedTreeItem().getValue();
					String fileregex = ".+\\.atry$";
					if (fileName != null) {
						if (fileName.matches(fileregex)) {
							PopUpWindowBeans deleteTestCaseBean = DeleteTestCaseBean.getInstaceOfClass();
							popUpObj.prepareStage(deleteTestCaseBean);
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		final Tooltip chgTooltip = new Tooltip();
		chgTooltip.setFont(new Font(12));
		chgTooltip.setText("Change work space");
		changeWStestcase.setTooltip(chgTooltip);
	
		changeWStestcase.setOnAction(e -> {
			try {
				PopUpWindowBeans selectWorkSpaceBean = SelectWorkSpaceBean.getInstaceOfClass();
				popUpObj.prepareStage(selectWorkSpaceBean);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
		addstepbutton.setOnAction(e -> {
			try {
				hierarchyHanlerObj.addStepToHierarchy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
		deletestepbutton.setOnAction(e -> {
			try {
				hierarchyHanlerObj.deleteStepToHierarchy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		connectflowbutton.setOnAction(e -> {
			try {
				hierarchyHanlerObj.connectStepsInHierarchy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		cntLoopLinebutton.setOnAction(e -> {
			try {
				hierarchyHanlerObj.crtLoopInHierarchy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		designPageBorderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				clickAndPaneXDifference = 0;
				moduleListLayoutX = ModuleButtonContainer.getLayoutX();
				moduleListLayoutX1 = moduleListLayoutX;
				if ((stepButtonContainer.getLayoutX() - event.getX()) < 10
						&& (stepButtonContainer.getLayoutX() - event.getX()) > 0) {
					dragStartPos = event.getX();
					clickAndPaneXDifference = 1;
					startWidthOfTreecontol = treecontol.getPrefWidth();
				} else if (event.getX() <= moduleListLayoutX) {
					dragStartPos = event.getX();
					clickAndPaneXDifference = 2;
					startWidthOfModuleButtonContainer = ModuleButtonContainer.getPrefWidth();
				}
			}
		});

		designPageBorderPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (clickAndPaneXDifference == 1) {
					double xPosition = event.getX() - dragStartPos;
					if ((startWidthOfTreecontol + xPosition) > 75) {
						filefoldertree.setPrefWidth(startWidthOfTreecontol + xPosition);
						treecontol.setPrefWidth(startWidthOfTreecontol + xPosition);
					}
				} else if (clickAndPaneXDifference == 2) {
					double xPosition = event.getX() - dragStartPos;
					if ((startWidthOfModuleButtonContainer - xPosition) > 75) {
						listOfModules.setPrefWidth(startWidthOfModuleButtonContainer - xPosition);
						listOfModulesScr.setPrefWidth(startWidthOfModuleButtonContainer - xPosition);
						ModuleButtonContainer.setPrefWidth(startWidthOfModuleButtonContainer - xPosition);
					}
				}
			}
		});

		browseWorkSpaceBtn.setOnAction(e -> {
			javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
			stage = (Stage) browseWorkSpaceBtn.getScene().getWindow();
			File selectedDirectory = directoryChooser.showDialog(stage);
			if (selectedDirectory != null) {
				workSpaceBox.setText(selectedDirectory.getAbsolutePath());
			}
		});

		addWorkSpaceBtn.setOnAction(e -> {
			readfoldObj = ReadFolder.getInstance();
			workSpaceFrExe = workSpaceBox.getText();
			File dir = new File(workSpaceFrExe);
			if(dir.exists())
			{
			atreeCseList = readfoldObj.readFileList(workSpaceFrExe);
			root.setExpanded(true);
			ExecDashBoardTableUpdateClass.getClassInstance().updateTable(workSpaceFrExe, atreeCseList,
					caseDashboardTable);
			}
			else
			{
				PopUpWindowBeans msgBoxBean = MessageBoxBean.getInstaceOfClass();
				try {
					popUpObj.prepareStage(msgBoxBean);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FXMLLoader msgBxLdr = msgBoxBean.getLoader();
				MessageBoxController aa = msgBxLdr.getController();
				aa.setMessageToWindow("Folder does not exist");
			}
		});
	}

	public static TreeItem<String> makeBranch(String title, TreeItem<String> parent) {

		if (activeItem != null && activeItem.equals(title)) {
			item = new TreeItem<String>(title, new ImageView(
					new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//activefile.png")));
			workFolder.getChildren().add(item);
		} else {
			item = new TreeItem<String>(title, new ImageView(
					new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//file.png")));
			workFolder.getChildren().add(item);
		}
		return item;
	}

/*	public static void setWorkSpace(String workSpace) {
		folderName = workSpace.trim();
	}

	public static String getWorkSpace() {
		return folderName;
	}*/

	public static TreeItem<String> getPreviousSelectedItem() {
		return preActiveTreeItem;
	}

	public static void displayTreeContent() {
		String folderName = ATreeWorkSpace.getAtreeWorkSpace();
		ReadFolder fileLob = ReadFolder.getInstance();
		ArrayList<String> fileList = fileLob.readFileList(folderName);
		root.getChildren().clear();
		workFolder = new TreeItem<String>(folderName, folderIcon);
		root.getChildren().add(workFolder);
		root.setExpanded(true);
		workFolder.setExpanded(true);
		Iterator<String> itr = fileList.iterator();
		while (itr.hasNext()) {
			String pattern = itr.next();
			if (pattern.matches(fileRegEx)) {
				Files = makeBranch(pattern, workFolder);
				if (activeItem != null && activeItem.equals(pattern)) {
					selectedTreeItem = Files;
					selectedItem = selectedTreeItem.getValue();
					preActiveTreeItem = selectedTreeItem;
				}
			}
		}
	}

	public static void addTestCase(String pattern) {
		if (pattern != null) {
			String patternwithext = pattern + ".atry";
			if (patternwithext.equals(activeItem)) {
				activeItem = null;
			}
			Files = makeBranch(patternwithext, workFolder);
		}
	}

	public static void deleteTestCase() {
		workFolder.getChildren().remove(selectedTreeItem);
		selectedTreeItem = null;
	}

	public void displayModuleIcon() {
		GetAndDisplayModules displayModuleObj = new GetAndDisplayModules();
		displayModuleObj.getAndDisplayModuleFunction(listOfModules);
	}

	public void deleteTestCase(String testcasename) {
		workFolder.getChildren().remove(selectedTreeItem);
		selectedTreeItem = null;
	}

	public void mouseClick(MouseEvent mouseEvt) {
		if (mouseEvt.getClickCount() == 2) {
			folderName = ATreeWorkSpace.getAtreeWorkSpace();
			activeTreeItem = filefoldertree.getSelectionModel().getSelectedItem();
			SelectedCase.selectedTestCase = activeTreeItem.getValue();
			String fileName = folderName + "\\" + activeTreeItem.getValue();
			if (!activeTreeItem.getValue().equals(folderName)) {
				activeItem = activeTreeItem.getValue();
			}
			if (!folderName.equals(activeTreeItem.getValue())) {
				activeTreeItem.setGraphic(new ImageView(new Image(
						"file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//activefile.png")));
				if (preActiveTreeItem != null && activeTreeItem != preActiveTreeItem) {
					preActiveTreeItem.setGraphic(new ImageView(
							new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//file.png")));
				}
				preActiveTreeItem = activeTreeItem;
				try {
					stepDesignBlockView.setContent(null);
					Label testcasenamelabel = new Label(activeTreeItem.getValue() + " ");
					testcasenamelabel.setGraphic(new ImageView(new Image(
							"file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//closeTabIcon.png")));
					testcasenamelabel.setStyle("-fx-font-weight: bold");
					stepDesignBlockView.setGraphic(testcasenamelabel);
					testcasenamelabel.setContentDisplay(ContentDisplay.RIGHT);
					provoker.provokeGeneration(fileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			selectedTreeItem = filefoldertree.getSelectionModel().getSelectedItem();
			selectedItem = selectedTreeItem.getValue();
		}
		int index = filefoldertree.focusModelProperty().get().getFocusedIndex();
		root.setExpanded(false);
		root.setExpanded(true);
		filefoldertree.getSelectionModel().select(index);
	}

	@FXML
	TextField searchtext = new TextField();

	public void keyboardpress(KeyEvent keyEvt) {
		fileRegEx = ".*" + searchtext.getText() + ".*\\.atry$";
		displayTreeContent();

	}

	public static void setIconOnDisplay() {
		VBox IconPane = new VBox();
		ScrollPane sPane = new ScrollPane();
		IconPane.setAlignment(Pos.CENTER);
		VBox.setVgrow(sPane, Priority.ALWAYS);
		IconPane.getChildren().add(sPane);
		Image img = new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//AtreeIcon.png");
		ImageView atreeIcon = new ImageView(
				new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//AtreeIcon.png"));
		Pane contentPane = new Pane();
		sPane.setContent(contentPane);
		stepDesignBlockView.setGraphic(new Label("Atree Application"));
		contentPane.getChildren().add(atreeIcon);
		stepDesignBlockView.setContent(IconPane);
		atreeIcon.setLayoutX((widthOfDesignTab - img.getWidth()) / 2);
		atreeIcon.setLayoutY((heightOfDesignTab - img.getWidth() - 200) / 2);
	}

	public static TreeItem<String> getSelectedTreeItem() {
		return selectedTreeItem;
	}

	public static String getSelectedItem() {
		return selectedItem;
	}

	public void setContentStepDesignTapPane(VBox content) {

		stepDesignBlockView.setContent(content);
	}

	public static void setSelectedItem(String fileName) {
		selectedItem = fileName;
	}

	private final class TextFieldTreeCellImpl extends TreeCell<String> {

		public TextFieldTreeCellImpl() {
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			folderName=ATreeWorkSpace.getAtreeWorkSpace();
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (!getTreeItem().getValue().equals(folderName)) {
					this.setText(getTreeItem().getValue());
					this.setGraphic(getTreeItem().getGraphic());
					this.setStyle("-fx-font-weight: regular");
					if (this.getItem().equals(activeItem)) {
						this.setStyle("-fx-font-weight: bold");
					}
				} else {
					this.setText(getTreeItem().getValue());
					this.setGraphic(getTreeItem().getGraphic());
					this.setStyle("-fx-font-weight: regular");
				}
			}
		}
	}

	public void setHeight() {
		// listOfModules.setMinHeight(listOfModulesScr.getHeight());
		listOfModulesScr.setStyle("-fx-background: #FFFFFF;");
		listOfModules.setStyle("-fx-border-width: 0.0px;");
	}

	public void displayTreeTableContent() {

	}

}
