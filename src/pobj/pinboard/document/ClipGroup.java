package pobj.pinboard.document;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandColor;

public class ClipGroup extends AbstractClip implements Composite {

	private List<Clip> children;

	public ClipGroup() {
		super(0, 0, 0, 0, Color.TRANSPARENT);
		children = new ArrayList<>();
	}

	@Override
	public void draw(GraphicsContext ctx) {
		for (Clip c : children)
			c.draw(ctx);
	}

	@Override
	public Clip copy() {
		ClipGroup copie = new ClipGroup();
		for (Clip c : children)
			copie.addClip(c.copy());
		return copie;
	}

	@Override
	public List<Clip> getClips() {
		return this.children;
	}

	@Override
	public void addClip(Clip toAdd) {
		this.children.add(toAdd);
		updateCarreEnglobant(toAdd);

	}

	@Override
	public void removeClip(Clip toRemove) {
		this.children.remove(toRemove);
		updateCarreEnglobant();
	}

	private void updateCarreEnglobant(Clip c) {
		if (children.size() == 1) {
			setGeometry(c.getLeft(), c.getTop(), c.getRight(), c.getBottom());
			return;
		}
		double left = Math.min(getLeft(), c.getLeft());
		double top = Math.min(getTop(), c.getTop());
		double right = Math.max(getRight(), c.getRight());
		double bottom = Math.max(getBottom(), c.getBottom());

		setGeometry(left, top, right, bottom);
	}

	private void updateCarreEnglobant() {
		if (this.children.isEmpty()) {
			setGeometry(0, 0, 0, 0);
			return;
		} else {
			double left = children.get(0).getLeft();
			double top = children.get(0).getTop();
			double right = children.get(0).getRight();
			double bottom = children.get(0).getBottom();

			for (Clip c : children) {
				left = Math.min(left, c.getLeft());
				top = Math.min(top, c.getTop());
				right = Math.max(right, c.getRight());
				bottom = Math.max(bottom, c.getBottom());
			}

			setGeometry(left, top, right, bottom);
		}

	}

	@Override
	public void move(double x, double y) {
		super.move(x, y);
		for (Clip c : children)
			c.move(x, y);
	}

	
}
