package execution;
import java.util.List;

import application.AtreeFolder;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
 
public class ExecDashBoardTableUpdateClass { 
	static ExecDashBoardTableUpdateClass classObj = new ExecDashBoardTableUpdateClass();
	private ExecDashBoardTableUpdateClass(){
		
	}
	
	public static ExecDashBoardTableUpdateClass getClassInstance()
	{
		if(classObj == null)
		{
			classObj = new ExecDashBoardTableUpdateClass();
		}
		return classObj;
	}

/*
    private final ImageView depIcon = new ImageView (
            new Image(getClass().getResourceAsStream("department.png"))
    );*/
    static int folderCntr=0;
	TreeItem<CaseProperty> root = 
            new TreeItem<>(new CaseProperty(new Label("0"), new Label(),  new VBox(), new VBox(), new VBox(), new VBox(),new VBox(),new VBox()));

   
    @SuppressWarnings("unchecked")
	public void updateTable(String folderName, List<String> fileList, TreeTableView<CaseProperty> treeTableView) {
        folderCntr = folderCntr + 1;
        Label SrNo = getSrNoLabel(folderCntr+"","workSpace");
        Label caseName = getCaseNameLabel(folderName,"workSpace");  
        VBox wrkSpcSelVbox = getSelectionVBox(folderName, "workSpace");
        VBox runVBox = getRunVBox(folderName, "workSpace");
        VBox stopVBox = getStopVBox(folderName, "workSpace");
        final TreeItem<CaseProperty> folderItem = new TreeItem<>(new CaseProperty(SrNo, caseName, wrkSpcSelVbox , runVBox, stopVBox, new VBox(),new VBox(),new VBox()));
        if(treeTableView.getRoot() == null)
        	{
        	  treeTableView.setRoot(root);
        	}
        TreeItem<CaseProperty> root = treeTableView.getRoot();
        int fileCntr=1;
        for(String fileName : fileList)
        {
        	SrNo = getSrNoLabel(fileCntr+"","case");
            caseName = getCaseNameLabel("    " + fileName,"case");  
            wrkSpcSelVbox = getSelectionVBox(folderName, "case");
            runVBox = getRunVBox(folderName, "case");
            stopVBox = getStopVBox(folderName, "case");
        	TreeItem<CaseProperty> fileItem = new TreeItem<>(new CaseProperty(SrNo, caseName, wrkSpcSelVbox, runVBox, stopVBox, new VBox(),new VBox(),new VBox()));
        	folderItem.getChildren().add(fileItem);	
        	fileCntr = fileCntr + 1;
        }
        root.getChildren().addAll(folderItem);
        treeTableView.setShowRoot(false);
        TreeTableColumn<CaseProperty, Label> srNoCol = (TreeTableColumn<CaseProperty, Label>) treeTableView.getColumns().get(0);
        TreeTableColumn<CaseProperty, Label> caseNameCol = (TreeTableColumn<CaseProperty, Label>) treeTableView.getColumns().get(1);
        TreeTableColumn<CaseProperty, VBox> selVBoxCol = (TreeTableColumn<CaseProperty, VBox>) treeTableView.getColumns().get(2);
        TreeTableColumn<CaseProperty, VBox> runVBoxCol = (TreeTableColumn<CaseProperty, VBox>) treeTableView.getColumns().get(3);
        TreeTableColumn<CaseProperty, VBox> stopVBoxCol = (TreeTableColumn<CaseProperty, VBox>) treeTableView.getColumns().get(4);
            // srNo.setPrefWidth(190);
        srNoCol.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<CaseProperty, Label> param) -> 
            new ReadOnlyObjectWrapper<>(param.getValue().getValue().getSrNo())
        );   
        caseNameCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CaseProperty, Label> param) -> 
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getCaseName())
            );  
        selVBoxCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CaseProperty, VBox> param) -> 
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getSelectionBox())
            ); 
        runVBoxCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CaseProperty, VBox> param) -> 
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getRunBtn())
            ); 
        stopVBoxCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<CaseProperty, VBox> param) -> 
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getStopBtn())
            ); 
        //treeTableView.getColumns().setAll(srNo);
    } 
    
    VBox getSelectionVBox(String caseName, String type)
    {
        VBox wrkSpcSelVbox = new VBox();
        if(!type.equals("case"))
        {
        		ComboBox<String> workSpcSel = new ComboBox<String>();
        		workSpcSel.getItems().addAll(
                        "All",
                        "Selected",
                        "Passed",
                        "Failed",
                        "Not Run"
                    );  
                wrkSpcSelVbox.getChildren().add(workSpcSel);
                wrkSpcSelVbox.setAlignment(Pos.CENTER);
        }
        else
        {
        	    CheckBox workSpcSel = new CheckBox();
                wrkSpcSelVbox.getChildren().add(workSpcSel);
                wrkSpcSelVbox.setAlignment(Pos.CENTER);
        }
        return  wrkSpcSelVbox;
    }
    
    Label getSrNoLabel(String label,String type)
    {
        Label SrNo = new Label(label);
        if(!type.equals("case"))
        {
          SrNo.setTextFill(Color.web("#1E52C4"));
          SrNo.setStyle("-fx-font-weight: bold");
        }
        else
        {
          SrNo.setStyle("-fx-font-weight: bold;");
          SrNo.setTextFill(Color.web("#C15842"));
        }
        return SrNo;
    }
    
    Label getCaseNameLabel(String label,String type)
    {
        Label caseName = new Label(label);
        if(!type.equals("case"))
        {
        	caseName.setTextFill(Color.web("#1E52C4"));
        	caseName.setStyle("-fx-font-weight: bold");
        }
        else
        {
        	caseName.setStyle("-fx-font-weight: bold;");
        	caseName.setTextFill(Color.web("#C15842"));
        }
        return caseName;
    }
    
    VBox getRunVBox(String caseName, String type)
    {
        VBox runVBox = new VBox();
        Button runButton = new Button();  
        if(type.equals("case"))
        {
        	runButton.setGraphic(new ImageView(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//start-icon-small.png")));
        }
        else
        {
        	runButton.setGraphic(new ImageView(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//start-icon.png")));
        }
        runButton.setStyle("-fx-padding: 0; -fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0; -fx-background-color: transparent;");
        runButton.setMaxHeight(15);
        runVBox.getChildren().add(runButton);
        runVBox.setAlignment(Pos.CENTER);
        return  runVBox;    
    }
    
    VBox getStopVBox(String caseName, String type)
    {
        VBox stopVBox = new VBox();
        Button stopButton = new Button();  
        if(type.equals("case"))
        {
        	stopButton.setGraphic(new ImageView(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//stop-icon-small.png")));
        }
        else
        {
        	stopButton.setGraphic(new ImageView(new Image("file:///" + AtreeFolder.getInstance().getATreeFolder() + "//icons//stop-icon.png")));
        }
        stopButton.setStyle("-fx-padding: 0; -fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0; -fx-background-color: transparent;");
        stopButton.setMaxHeight(15);
        stopVBox.getChildren().add(stopButton);
        stopVBox.setAlignment(Pos.CENTER);
        return  stopVBox;    
    }
}