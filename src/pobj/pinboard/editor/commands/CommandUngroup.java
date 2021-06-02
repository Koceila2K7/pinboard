package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.Composite;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {
	private EditorInterface editor;
	private Composite group;

	public CommandUngroup(EditorInterface editor, Composite group) {
		super();
		this.editor = editor;
		this.group = group;
	}

	@Override
	public void execute() {
		for (Clip c : group.getClips())
			editor.getBoard().addClip(c);
		editor.getBoard().removeClip(group);
	}

	@Override
	public void undo() {
		for (Clip c : group.getClips())
			editor.getBoard().removeClip(c);
		editor.getBoard().addClip(group);
	}

}
