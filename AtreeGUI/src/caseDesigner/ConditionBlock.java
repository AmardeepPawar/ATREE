package caseDesigner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConditionBlock {


	public static Polygon getConditionPolygon(double startPointX, double startPointY, String conditionStr) {
		Polygon polygon = new Polygon();
		if (!conditionStr.equals("")) {
			polygon.getPoints().addAll(new Double[] { startPointX, startPointY - 6, startPointX + 6, startPointY,
					startPointX, startPointY + 6, startPointX - 6, startPointY, });
			polygon.setFill(Color.WHITE);
			polygon.setStroke(Color.BLACK);
			polygon.setStrokeWidth(1.5);
		} else {
			polygon.getPoints().addAll(new Double[] { startPointX, startPointY - 4, startPointX + 4, startPointY,
					startPointX, startPointY + 4, startPointX - 4, startPointY, });

		}
		return polygon;
	}
}