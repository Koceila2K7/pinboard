package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
	private List<Clip> contents;
	private Set<IBoardListener> listeners;

	public Board() {
		contents = new ArrayList<>();
		listeners = new HashSet<>();
	}

	public List<Clip> getContents() {
		return contents;
	}

	public void addClip(Clip clip) {
		contents.add(clip);
		notifyListener();
	}

	public void addClip(List<Clip> clip) {
		contents.addAll(clip);
		notifyListener();
	}

	public void removeClip(Clip clip) {
		this.contents.remove(clip);
		notifyListener();
	}

	public void removeClip(List<Clip> clip) {
		contents.removeAll(clip);
		notifyListener();
	}

	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		for (Clip c : contents)
			c.draw(gc);
	}

	public void addListener(IBoardListener listener) {
		listeners.add(listener);
		// notifyListener();
	}

	public void notifyListener() {
		for (IBoardListener listener : listeners)
			listener.boardChanged();

	}

	public void removeListener(IBoardListener listener) {
		listeners.remove(listener);
	}
}
