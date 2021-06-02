package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipRect;

public class Selection {
	private Set<Clip> selection;

	public Selection() {
		this.selection = new HashSet<>();
	}

	public void select(Board board, double x, double y) {
		this.clear();
		for (Clip forme : board.getContents()) {
			if (forme.isSelected(x, y)) {
				if (!selection.contains(forme))	selection.add(forme);
				return;
			}
		}
		
	}

	public void toogleSelect(Board board, double x, double y) {
		for (Clip forme : board.getContents()) {
			if (forme.isSelected(x, y)) {
				if (selection.contains(forme))
					selection.remove(forme);
				else
					selection.add(forme);
				return;
			}
		}
	}

	public void clear() {
		selection.clear();
	}

	public List<Clip> getContents() {
		return new ArrayList<Clip> (this.selection);
	}

	public void drawFeedback(GraphicsContext gc) {
		ClipRect carreEnglobant = new ClipRect(0, 0, 0, 0, Color.BLUE);
		for (Clip s : selection) {
			carreEnglobant.setGeometry(s.getLeft(), s.getTop(), s.getRight(), s.getBottom());
			carreEnglobant.draw(gc);
		}
	}
}
