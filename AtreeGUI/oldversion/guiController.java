package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class guiController {
	private final static Node folderIcon = new ImageView(new Image("icons//foldericon.png"));
    private final Node fileIcon = new ImageView(new Image("icons//file.png"));
    private final Node actFileIcon = new ImageView(new Image("icons//activefile.png"));
    static String folderName;
    static DropShadow shadow = new DropShadow();
    @FXML
    Button addstepbutton = new Button();
	@FXML 
	public TreeView<String> filefoldertree = new TreeView<String>();
	
	@FXML
	TilePane listOfModules = new TilePane();
	//static TreeView<String> filefoldertree1 = new TreeView<String>();
	@FXML 
	Button addtestcase = new Button();
	@FXML 
	Button deletetestcase = new Button();
	@FXML 
	Button changeWStestcase = new Button();
	@FXML
	HBox stepdesignarea = new HBox();

	static TreeItem<String> root = new TreeItem<String>();
	static TreeItem<String> Files = new TreeItem<String>();
	static TreeItem<String> workFolder = new TreeItem<String>();
	static TreeItem<String> selectedTreeItem, activeTreeItem;
	static TreeItem<String> preActiveTreeItem = null;
	static String fileregex = ".+\\.atry$";
	static String selectedItem,activeItem = null;
	static TreeItem<String> item = null;
	public void initialize() throws IOException {
		final Tooltip tooltip = new Tooltip();
		tooltip.setFont(new Font(12));
		tooltip.setText(
		    "Add new test case"
		);
		addtestcase.setTooltip(tooltip);
		popUpWindow popUpObj = new popUpWindow();
		root = new TreeItem<String>();
		addtestcase.setOnAction(e -> {try {
			popUpObj.addPopUpWindow("addTestCase.fxml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		deletetestcase.setOnAction(e -> {try {
			popUpObj.deletePopUpWindow("deleteTestCase.fxml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		changeWStestcase.setOnAction(e -> {try {
			popUpObj.selectWSPopUpWindow("selectWorkSpace.fxml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		filefoldertree.setRoot(root);
		filefoldertree.setEditable(true);
		filefoldertree.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	                return new TextFieldTreeCellImpl();
	            }
	        });
		filefoldertree.setShowRoot(false);
		getAndDisplayModules displayModuleObj = new getAndDisplayModules();
		displayModuleObj.getAndDisplayModuleFunction(listOfModules);
		
		stepdesignarea.setOnDragDropped(new EventHandler<DragEvent>() {
			    public void handle(DragEvent event) {
			        /* data dropped */
			        /* if there is a string data on dragboard, read it and use it */
			        Dragboard db = event.getDragboard();
			          String moduleId = db.getString();
			          System.out.println("amar");
			        boolean success = false;
			        if (db.hasString()) {

			  		  Node folderIcon = new ImageView(new Image("file:///C:/Users/ACER/workspace/AtreeGUI/bin/moduleIcons/" + moduleId));
			          stepdesignarea.getChildren().add(folderIcon);
			          success = true;
			        }
			        /* let the source know whether the string was successfully 
			         * transferred and used */
			        event.setDropCompleted(success);			        
			        event.consume();
			     }
			});
	  }  

	public static TreeItem<String> makeBranch(String title, TreeItem<String> parent)
	{
		
		if (activeItem != null && activeItem.equals(title))
			{
			item = new TreeItem<String>(title,new ImageView(new Image("icons//activefile.png")));
			workFolder.getChildren().add(item);			
			}
		  else
		    {
			item = new TreeItem<String>(title,new ImageView(new Image("icons//file.png")));
			workFolder.getChildren().add(item);
			}
		return item;
	}
	
	public static void setWorkSpace(String workSpace)
	{
		folderName = workSpace.trim();		
	}
	
	public static String getWorkSpace()
	{
		return folderName;		
	}
	
	public static TreeItem<String> getPreviousSelectedItem()
	{
		return preActiveTreeItem;		
	}
	
	public static void displayTreeContent()
	{
		ReadFolder fileLob = new ReadFolder();
		ArrayList<String> fileList = fileLob.readFileList(folderName);
        root.getChildren().clear();
		workFolder = new TreeItem<String> (folderName, folderIcon);
		root.getChildren().add(workFolder);
		root.setExpanded(true);
		workFolder.setExpanded(true);
		Iterator<String> itr=fileList.iterator();
		while(itr.hasNext()){  
		String pattern = itr.next();
		if (pattern.matches(fileregex) )
		{
		Files = makeBranch(pattern,workFolder);
    	if (activeItem != null && activeItem.equals(pattern))
		 {
    		selectedTreeItem = Files;
    		selectedItem = selectedTreeItem.getValue();
    		preActiveTreeItem = selectedTreeItem;
		 }
		}
		}
	}
	
	public static void addTestCase(String pattern)
	{
		if(pattern != null)
		{
		String patternwithext= pattern + ".atry";
		if(patternwithext.equals(activeItem))
		{
			activeItem = null;
		}
		Files = makeBranch(patternwithext,workFolder);		
		}
	}
	
	public static void deleteTestCase()
	{
		workFolder.getChildren().remove(selectedTreeItem);
		selectedTreeItem = null;		
	}
	
	public void deleteTestCase(String testcasename)
	{
		workFolder.getChildren().remove(selectedTreeItem);
		selectedTreeItem = null;		
	}
	
	public void mouseClick(MouseEvent mouseEvt)
	{		
		if (mouseEvt.getClickCount() == 2)
		{
			activeTreeItem = filefoldertree.getSelectionModel().getSelectedItem();
			if(!activeTreeItem.getValue().equals(folderName))
			{
			activeItem = activeTreeItem.getValue();
			}
			if(!folderName.equals(activeTreeItem.getValue()))
			{
			activeTreeItem.setGraphic(new ImageView(new Image("icons//activefile.png")));
			if (preActiveTreeItem != null && activeTreeItem != preActiveTreeItem)
			{
				preActiveTreeItem.setGraphic(new ImageView(new Image("icons//file.png")));
			}
			preActiveTreeItem = activeTreeItem;
		  }
		}
		else
		{
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
	
	public void keyboardpress(KeyEvent keyEvt)
	{
		fileregex = ".*" + searchtext.getText() + ".*\\.atry$";
		displayTreeContent();
	}
	

	public static TreeItem<String> getSelectedTreeItem()
	{
		return selectedTreeItem;
	}
	
	public static String getSelectedItem()
	{
		return selectedItem;
	}
	
	public static void setSelectedItem(String fileName)
	{
		selectedItem = fileName;
	}
	
/*	public void setfocus()
	{
		//filefoldertree.getSelectionModel().select(selectedTreeItem);		
		int index = filefoldertree.focusModelProperty().get().getFocusedIndex();
		filefoldertree.requestFocus();
		filefoldertree.getSelectionModel().select(index);
		filefoldertree.getFocusModel().focus(index);
	}
	*/
	
	private final class TextFieldTreeCellImpl extends TreeCell<String> {
		 
        public TextFieldTreeCellImpl() {
        }
/*        @Override
        public void startEdit() {
            super.startEdit(); 
            this.setText(getTreeItem().getValue());
            this.setGraphic(new ImageView(new Image("icons//activefile.png")));
            System.out.println("start");
        }
       
     @Override
       public void cancelEdit() {
           super.cancelEdit();
           setText((String) getItem());
           if(this.getItem().equals("anjali.atry"))
           {
               setGraphic(new ImageView(new Image("icons//file.png")));  
           }

           System.out.println("cancel");
       }
       
*/
        
       

       @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
            	if(!getTreeItem().getValue().equals(folderName))
            	{
            	    this.setText(getTreeItem().getValue());
                    this.setGraphic(getTreeItem().getGraphic());
                    this.setStyle("-fx-font-weight: regular");
                    if(this.getItem().equals(activeItem))
                    {
                     this.setStyle("-fx-font-weight: bold");
                    }
            	} 
            	else
            	{
            		this.setText(getTreeItem().getValue());
                    this.setGraphic(getTreeItem().getGraphic());
                    this.setStyle("-fx-font-weight: regular");
            	}
            }
        }
	}
}
