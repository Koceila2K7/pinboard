package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.document.CountourRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool {
	private CountourRect element;
	private MouseEvent firstClick;
	private ClipRect result;
	private boolean lastClick;
	private CommandAdd command;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.firstClick = e;
		this.element = new CountourRect(e.getX(), e.getSceneY(), e.getSceneX(), e.getSceneY(), i.getCurrentColor());
		lastClick = false;
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
		this.result = new ClipRect(element.getLeft(), element.getTop(), element.getRight(), element.getBottom(),
				i.getCurrentColor());
		command = new CommandAdd(i, result);
		i.getUndoStack().addCommand(command);
		command.execute();
		lastClick = true;

	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		if (!lastClick) {
			this.element.draw(gc);
		}

	}

	@Override
	public String getName() {
		return "Filled rectangle tool";
	}

}
