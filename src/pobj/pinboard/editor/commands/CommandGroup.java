package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {
	private EditorInterface editor;
	private List<Clip> rects;
	private Command undo;
	private ClipGroup group;

	public CommandGroup(EditorInterface editor, List<Clip> rects) {
		super();
		this.editor = editor;
		this.rects = rects;
	}

	@Override
	public void execute() {
		group = new ClipGroup();

		for (Clip c : rects)
			group.addClip(c);

		editor.getBoard().removeClip(rects);
		editor.getBoard().addClip(group);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(group);
		for (Clip c : group.getClips())
			editor.getBoard().addClip(c);

	}

}
