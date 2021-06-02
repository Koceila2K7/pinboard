package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command {
	private EditorInterface editor;
	private Clip toAdd;

	public CommandAdd(EditorInterface editor, Clip toAdd) {
		super();
		this.editor = editor;
		this.toAdd = toAdd;

	}

	@Override
	public void execute() {
		editor.getBoard().addClip(toAdd);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(toAdd);
	}

}
