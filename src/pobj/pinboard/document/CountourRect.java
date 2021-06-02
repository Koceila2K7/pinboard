package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CountourRect extends ClipRect {

	public CountourRect(double left, double top, double right, double bottom, Color color) {
		super(left, top, right, bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.strokeRect(getLeft(), getTop(), getRight() - getLeft(), getBottom() - getTop());
	}

}
