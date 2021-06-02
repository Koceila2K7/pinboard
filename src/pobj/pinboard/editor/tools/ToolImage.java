package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool {
	private ClipImage elemet;
	private File image;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		image = i.getSelectedFile();
		if (image == null)
			return;
		elemet = new ClipImage(e.getX(), e.getY(), i.getSelectedFile());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		if (image == null)
			return;
		elemet.setGeometry(e.getX(), e.getY(), 0, 0);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if (image == null)
			return;
		this.drag(i, e);
		Command cmd = new CommandAdd(i, elemet);
		i.getUndoStack().addCommand(cmd);
		cmd.execute();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		if (image == null)
			return;
		elemet.draw(gc);
	}

	@Override
	public String getName() {
		return "Image tool";
	}

}
