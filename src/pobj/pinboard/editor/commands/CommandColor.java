package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.Composite;
import pobj.pinboard.editor.EditorInterface;

public class CommandColor implements Command {
	private Color newColor;
	private Clip c;
	private EditorInterface editor;
	private List<Command> listCommand;

	public CommandColor(EditorInterface editor, Clip c, Color newColor) {
		this.c = c;
		c.getColor();
		this.newColor = newColor;
		this.editor = editor;
		listCommand = new ArrayList<>();
		if (c instanceof Composite)
			decompose((Composite) c);
		else
			listCommand.add(new CommandSimpleColor(c, newColor));

	}

	private void decompose(Composite c) {
		List<Clip> children = c.getClips();
		for (Clip clip : children) {
			if (clip instanceof Composite) {
				this.decompose((Composite) clip);
			} else {
				this.listCommand.add(new CommandSimpleColor(clip, newColor));
			}
		}
	}

	@Override
	public void execute() {
		for (Command cmd : listCommand)
			cmd.execute();
		editor.getBoard().notifyListener();
	}

	@Override
	public void undo() {
		for (Command cmd : listCommand)
			cmd.undo();
		editor.getBoard().notifyListener();

	}

	private class CommandSimpleColor implements Command {
		private Clip c;
		private Color oldColor;
		private Color newColor;

		public CommandSimpleColor(Clip c, Color newColor) {
			this.c = c;
			this.newColor = newColor;
			this.oldColor = c.getColor();
		}

		@Override
		public void execute() {
			this.c.setColor(newColor);
		}

		@Override
		public void undo() {
			this.c.setColor(oldColor);
		}

	}

}
