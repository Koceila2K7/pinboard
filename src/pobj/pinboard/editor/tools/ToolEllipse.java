package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.CountourEllipse;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool {
	private CountourEllipse element;
	private MouseEvent firstClick;
	private ClipEllipse result;
	private boolean lastClick;
	private CommandAdd command;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.firstClick = e;
		this.element = new CountourEllipse(e.getX(), e.getSceneY(), e.getSceneX(), e.getSceneY(), i.getCurrentColor());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		double top = Math.min(firstClick.getY(), e.getY());
		double bottom = Math.max(firstClick.getY(), e.getY());

		double left = Math.min(firstClick.getX(), e.getX());
		double right = Math.max(firstClick.getX(), e.getX());

		this.element.setGeometry(left, top, right, bottom);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		this.drag(i, e);
		this.result = new ClipEllipse(element.getLeft(), element.getTop(), element.getRight(), element.getBottom(),
				element.getColor());
		this.command = new CommandAdd(i, result);
		i.getUndoStack().addCommand(command);
		command.execute();

	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		if (lastClick) {
			this.result.draw(gc);
			lastClick = false;
			return;
		}
		this.element.draw(gc);
	}

	@Override
	public String getName() {
		return "Filled Ellipse";
	}

}
