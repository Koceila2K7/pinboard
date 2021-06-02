package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command {
	private EditorInterface editor;
	private Clip rect;
	private double x;
	private double y;

	public CommandMove(EditorInterface editor, Clip c, double x, double y) {
		super();
		this.editor = editor;
		this.rect = c;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		rect.move(x, y);
		editor.getBoard().notifyListener();
	}

	@Override
	public void undo() {
		rect.move(-x, -y);
		editor.getBoard().notifyListener();

	}

}
