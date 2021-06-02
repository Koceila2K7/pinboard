package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CountourEllipse extends ClipEllipse {

	public CountourEllipse(double left, double top, double right, double bottom, Color color) {
		super(left, top, right, bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.strokeOval(getLeft(), getTop(), getRight() - getLeft(), getBottom() - getTop());
	}
}
