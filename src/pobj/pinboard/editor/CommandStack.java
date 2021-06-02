package pobj.pinboard.editor;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	private Stack<Command> redo;
	private Stack<Command> undo;
	private Set<ICommandStackListener> listeners;

	public CommandStack() {
		redo = new Stack<Command>();
		undo = new Stack<Command>();
		listeners = new HashSet<>();
	}

	public void addCommand(Command cmd) {
		undo.add(cmd);
		redo.clear();
		notifyListener();
	}

	public void undo() {
		Command last = undo.lastElement();
		undo.remove(undo.size() - 1);
		last.undo();
		redo.add(last);
		notifyListener();
	}

	public void redo() {
		Command last = redo.lastElement();
		redo.remove(redo.size() - 1);
		last.execute();
		undo.add(last);
		notifyListener();
	}

	public boolean isUndoEmpty() {
		return undo.isEmpty();
	}

	public boolean isRedoEmpty() {
		return redo.isEmpty();
	}

	private void notifyListener() {
		for (ICommandStackListener listener : listeners)
			listener.commandStackChanged();
	}

	public void addListener(ICommandStackListener listener) {
		listeners.add(listener);
		notifyListener();
	}

	public void removeListener(ICommandStackListener listener) {
		listeners.remove(listener);
	}

}
