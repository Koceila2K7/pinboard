package pobj.pinboard.editor;

import java.io.File;

import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;

public interface EditorInterface {
	public Board getBoard();

	public File getSelectedFile();

	public Selection getSelection();

	public CommandStack getUndoStack();
	
	public Color getCurrentColor();
}
