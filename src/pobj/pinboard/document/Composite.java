package pobj.pinboard.document;

import java.util.List;

import javafx.scene.paint.Color;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.Command;

public interface Composite extends Clip {
	public List<Clip> getClips();

	public void addClip(Clip toAdd);

	public void removeClip(Clip toRemove);


}
