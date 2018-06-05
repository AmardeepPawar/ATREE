package application;

import java.util.Map;

import application.beans.ModulePropertyBean;
import application.beans.ModulesGroupPropertyBean;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;

public class GetAndDisplayModules {
	ClipboardContent content;
	Dragboard db;

	public void getAndDisplayModuleFunction(TilePane listOfModules) {
		listOfModules.setPadding(new Insets(10, 7, 7, 15));
		listOfModules.setVgap(12);
		listOfModules.setHgap(10);
		listOfModules.getChildren().clear();
		SetModuleProperty setModProp = new SetModuleProperty();
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String, ModulePropertyBean> modMap = keyValMap.getModulePropertyBean();
		Map<String, ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		// modMap.clear();
		for (Map.Entry<String, Boolean> pair : ConfigVariables.selModIdList.entrySet()) {
			if (!pair.getValue()) {
				setModProp.setModuleProperty(pair.getKey());
			}
			ConfigVariables.selModIdList.put(pair.getKey(), true);
		}

		if (modMap.size() != 0) {
			for (Map.Entry<String, ModulePropertyBean> pair : modMap.entrySet()) {
				Image ModuleImg = new Image("file:" + ConfigVariables.modulesFldr + "//"
						+ modGrpMap.get(modMap.get(pair.getKey()).getModGrpId()).getModuleGrpFldr() + "//ModuleIcons//"
						+ modMap.get(pair.getKey()).getIcon());
				ImageView folderIcon = new ImageView(ModuleImg);
				folderIcon.setFitWidth(50);
				folderIcon.setFitHeight(50);
				Node ModuleIcon = folderIcon;
				ModuleIcon.setId(pair.getKey());
				listOfModules.getChildren().add(ModuleIcon);
				Tooltip tooltip = new Tooltip();
				tooltip.setText(
						"Module Type: " + modGrpMap.get(modMap.get(pair.getKey()).getModGrpId()).getModuleGrpName()
								+ "\n" + "Module: " + modMap.get(pair.getKey()).getModName());
				Tooltip.install(ModuleIcon, tooltip);
				KeyValueMapping.getInstance().keyAndTipMap.put(ModuleIcon.getId(), tooltip);

				ModuleIcon.setOnDragDetected(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						db = ModuleIcon.startDragAndDrop(TransferMode.COPY);
						content = new ClipboardContent();
						String[] modIds = ModuleIcon.getId().split("::");
						Image ModuleImg = new Image("file:" + ConfigVariables.modulesFldr + "//"
								+ modGrpMap.get(modIds[0]).getModuleGrpFldr() + "//ModuleIcons//"
								+ modMap.get(pair.getKey()).getIcon(), 40, 40, false, false);
						content.putString(ModuleIcon.getId());
						db.setDragView(ModuleImg, 25, 25);
						db.setContent(content);
						event.consume();
					}
				});

				ModuleIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						ModuleIcon.setEffect(new DropShadow());
					}
				});

				ModuleIcon.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						ModuleIcon.setEffect(null);
					}
				});
			}
		}

	}
}
