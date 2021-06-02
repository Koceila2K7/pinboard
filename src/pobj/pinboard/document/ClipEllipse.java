package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip {

	public ClipEllipse(double left, double top, double right, double bottom, Color color) {
		super(left, top, right, bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.fillOval(getLeft(), getTop(), getRight() - getLeft(), getBottom() - getTop());
	}

	@Override
	public Clip copy() {
		return new ClipEllipse(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

	@Override
	public boolean isSelected(double x, double y) {
		if (!super.isSelected(x, y))
			return false;
		double cx, cy, rx, ry, a, b;
		cx = (getLeft() + getRight()) / 2;
		cy = (getTop() + getBottom()) / 2;
		rx = (getRight() - getLeft()) / 2;
		ry = (getBottom() - getTop()) / 2;

		a = (x - cx) / rx;
		a = a * a;
		b = (y - cy) / ry;
		b = b * b;
		return ((b + a) <= 1);
	}
}
