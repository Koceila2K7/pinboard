package pobj.pinboard.editor.tools;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool {
	private MouseEvent lastClique;
	private List<CommandMove> move;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.lastClique = e;
		if (e.isShiftDown()) {
			i.getSelection().toogleSelect(i.getBoard(), e.getX(), e.getY());
		} else {
			i.getSelection().select(i.getBoard(), e.getX(), e.getY());
		}

	}

	private double getX(MouseEvent e) {
		return e.getX() - lastClique.getX();
	}

	private double getY(MouseEvent e) {
		return e.getY() - lastClique.getY();
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		double x, y;

		for (Clip c : i.getSelection().getContents()) {
			CommandMove cm = new CommandMove(i, c, getX(e), getY(e));
			cm.execute();
			i.getUndoStack().addCommand(cm);
		}

		this.lastClique = e;
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {

	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName() {

		return "Selection Tool";
	}

}
