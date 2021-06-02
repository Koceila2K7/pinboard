package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandRemove implements Command {
	private EditorInterface editor;
	private List<Clip> clips;

	public CommandRemove(EditorInterface editor, List<Clip> clips) {
		super();
		this.editor = editor;
		this.clips = clips;
	}

	@Override
	public void execute() {
		editor.getBoard().removeClip(clips);

	}

	@Override
	public void undo() {
		editor.getBoard().addClip(clips);
	}

}
