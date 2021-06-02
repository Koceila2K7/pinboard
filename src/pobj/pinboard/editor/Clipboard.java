package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pobj.pinboard.document.Clip;

public class Clipboard {
	private static Clipboard instance = null;
	private List<Clip> pressPapier;
	private Set<ClipboardListener> clipboardListeners;

	private Clipboard() {
		pressPapier = new ArrayList<>();
		clipboardListeners = new HashSet<>();
	}

	public void copyToClipboard(List<Clip> clips) {
		clear();
		for (Clip c : clips)
			pressPapier.add(c.copy());
		this.notifyListener();
	}

	public List<Clip> copyFromClipboard() {
		List<Clip> rst = new ArrayList<>();
		for (Clip c : pressPapier)
			rst.add(c.copy());
		return rst;

	}

	public void clear() {
		this.pressPapier.clear();
		this.notifyListener();
	}

	public boolean isEmpty() {
		return this.pressPapier.isEmpty();
	}

	public static Clipboard getInstance() {
		if (instance == null) {
			instance = new Clipboard();
		}
		return instance;
	}

	private void notifyListener() {
		for (ClipboardListener listener : this.clipboardListeners)
			listener.clipboardChanged();
	}

	public void addListener(ClipboardListener listener) {
		this.clipboardListeners.add(listener);
	}

	public void removeListener(ClipboardListener listener) {
		this.clipboardListeners.remove(listener);
	}
}
