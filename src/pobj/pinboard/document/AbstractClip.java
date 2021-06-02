package pobj.pinboard.document;

import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip {

	private double left;
	private double top;
	private double right;
	private double bottom;
	private Color color;

	public AbstractClip(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getLeft() {
		return left;
	}

	public double getTop() {
		return top;
	}

	public double getRight() {
		return right;
	}

	public double getBottom() {
		return bottom;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	@Override
	public void move(double x, double y) {
		this.setGeometry(left + x, top + y, right + x, bottom + y);
	}

	@Override
	public boolean isSelected(double x, double y) {
		return (x >= left && x <= right) ? ((y >= top && y <= bottom) ? true : false) : false;
	}

}
