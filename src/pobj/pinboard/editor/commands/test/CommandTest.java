package pobj.pinboard.editor.commands.test;

import java.io.File;

import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.Selection;

// Helper class for all Command tests

public class CommandTest {

	public class MockEditor implements EditorInterface {
		private Board board = new Board();

		public Board getBoard() {
			return board;
		}

		private Selection selection = new Selection();

		public Selection getSelection() {
			return selection;
		}

		private CommandStack command = new CommandStack();

		public CommandStack getUndoStack() {
			return command;
		}

		public Color getCurrentColor() {
			return Color.RED;
		}

		@Override
		public File getSelectedFile() {
			// TODO Auto-generated method stub
			return null;
		}
	};

}
