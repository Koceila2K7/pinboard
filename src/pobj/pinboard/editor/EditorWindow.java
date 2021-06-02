package pobj.pinboard.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.Composite;
import pobj.pinboard.document.IBoardListener;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandColor;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandRemove;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener, ICommandStackListener, IBoardListener {
	private Board board;
	private Tool currentTool;
	private Label statut;
	private VBox root;
	private Scene scene;
	private Stage stage;
	private MenuBar barreMenu;
	private ToolBar barreDeBoutouns;
	private List<Tool> tools;
	private Canvas zoneDessin;
	private File selectedFile;
	private Selection selection;
	private Clipboard clipboard;
	private MenuItem copy;
	private MenuItem paste;
	private MenuItem delete;
	private ColorPicker picker;
	private CommandStack stack;
	private MenuItem undo;
	private MenuItem redo;

	public EditorWindow(Stage stage) {
		this.stage = stage;
		this.board = new Board();
		board.addListener(this);
		this.selection = new Selection();
		this.clipboard = Clipboard.getInstance();
		this.clipboard.addListener(this);

		stage.onCloseRequestProperty().addListener((e) -> {
			clipboard.removeListener(this);
			System.out.println("azul");
		});
		tools = new ArrayList<Tool>();
		tools.add(new ToolRect());
		tools.add(new ToolEllipse());
		tools.add(new ToolImage());
		tools.add(new ToolSelection());

		currentTool = tools.get(0);
		stage.setTitle("PinBoard");
		root = new VBox();
		scene = new Scene(root);
		stage.setScene(scene);

		this.createMenuBarre();
		this.createToolBouton();
		stack = new CommandStack();
		stack.addListener(this);
		zoneDessin = new Canvas(500, 500);
		this.configureMouseCanvas();
		root.getChildren().add(zoneDessin);
		root.getChildren().add(new Separator());
		statut = new Label("Filled rectangle tool");
		root.getChildren().add(statut);
		stage.show();
	}

	private void createMenuBarre() {
		// Création de la barre de menu
		barreMenu = new MenuBar();
		// Définition des options de la barre
		// Option File
		Menu file = new Menu("File");
		barreMenu.getMenus().add(file);
		MenuItem newItem = new MenuItem("New");
		MenuItem close = new MenuItem("Close");
		file.getItems().add(newItem);
		file.getItems().add(close);
		newItem.setOnAction((e) -> new EditorWindow(new Stage()));

		close.setOnAction((e) -> {
			stage.close();
			clipboard.removeListener(this);
		});
		// Option Edit
		Menu edit = new Menu("Edit");
		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		paste.setDisable(clipboard.isEmpty());
		delete = new MenuItem("Delete");
		MenuItem group = new MenuItem("Group");
		MenuItem ungroup = new MenuItem("Ungroup");
		undo = new MenuItem("Undo");
		redo = new MenuItem("Redo");

		copy.setOnAction((e) -> {
			if (!selection.getContents().isEmpty()) {
				clipboard.copyToClipboard(selection.getContents());
			}
		});

		paste.setOnAction((e) -> paste());

		delete.setOnAction((e) -> delete());

		undo.setOnAction((e) -> stack.undo());
		redo.setOnAction((e) -> stack.redo());

		group.setOnAction((e) -> group());
		ungroup.setOnAction((e) -> ungroup());

		edit.getItems().add(copy);
		edit.getItems().add(paste);
		edit.getItems().add(delete);
		edit.getItems().add(group);
		edit.getItems().add(ungroup);
		edit.getItems().add(undo);
		edit.getItems().add(redo);

		barreMenu.getMenus().add(edit);

		Menu tools = new Menu("Tools");
		barreMenu.getMenus().add(tools);
		root.getChildren().add(barreMenu);
	}

	private void paste() {
		if (clipboard.isEmpty())
			return;
		for (Clip c : clipboard.copyFromClipboard()) {
			Command cmd = new CommandAdd(this, c);
			cmd.execute();
			this.stack.addCommand(cmd);
		}

	}

	private void delete() {
		if (selection.getContents().isEmpty())
			return;
		Command cr = new CommandRemove(this, selection.getContents());
		cr.execute();
		this.stack.addCommand(cr);
		selection.clear();

	}

	private void group() {
		if (selection.getContents().isEmpty())
			return;

		Command cg = new CommandGroup(this, selection.getContents());
		cg.execute();
		stack.addCommand(cg);
		selection.clear();
	}

	private void ungroup() {
		if (selection.getContents().isEmpty())
			return;
		for (Clip c : selection.getContents()) {
			if (!(c instanceof Composite))
				continue;
			Composite cg = (Composite) c;
			Command cu = new CommandUngroup(this, cg);
			cu.execute();
			stack.addCommand(cu);
			selection.getContents().remove(c);
		}

	}

	private void setStatuLabel(String text) {
		statut.setText(text);
	}

	private void createToolBouton() {
		barreDeBoutouns = new ToolBar();
		Button box = new Button("Box");
		barreDeBoutouns.getItems().add(box);
		Button ellipse = new Button("Ellipse");
		box.setOnAction((e) -> setCurrentTool(tools.get(0)));
		ellipse.setOnAction((e) -> setCurrentTool(tools.get(1)));

		barreDeBoutouns.getItems().add(ellipse);
		Button image = new Button("Image");

		image.setOnAction((e) -> {
			setCurrentTool(tools.get(2));
			openFileChooser();
		});

		picker = new ColorPicker(Color.BLACK);
		picker.setOnAction((e) -> setCurrentColor(picker.getValue()));
		Button selection = new Button("Selection");
		selection.setOnAction((e) -> setCurrentTool(tools.get(3)));
		barreDeBoutouns.getItems().add(image);
		barreDeBoutouns.getItems().add(selection);
		barreDeBoutouns.getItems().add(picker);

		root.getChildren().add(barreDeBoutouns);
	}

	public void setCurrentTool(Tool currentTool) {
		selection.clear();
		board.notifyListener();
		this.currentTool = currentTool;
		setStatuLabel(currentTool.getName());
	}

	private void configureMouseCanvas() {
		zoneDessin.setOnMousePressed((arg0) -> {
			currentTool.press(EditorWindow.this, arg0);
			currentTool.drawFeedback(EditorWindow.this, zoneDessin.getGraphicsContext2D());
		});
		zoneDessin.setOnMouseDragged((arg0) -> {
			currentTool.drag(EditorWindow.this, arg0);
			draw();
		});
		zoneDessin.setOnMouseReleased((arg0) -> {
			currentTool.release(this, arg0);
			draw();
		});
	}

	@Override
	public Board getBoard() {
		return this.board;
	}

	private void draw() {
		board.draw(zoneDessin.getGraphicsContext2D());
		currentTool.drawFeedback(EditorWindow.this, zoneDessin.getGraphicsContext2D());
	}

	@Override
	public Selection getSelection() {
		return selection;
	}

	@Override
	public CommandStack getUndoStack() {
		return stack;
	}

	private void openFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png"),
				new FileChooser.ExtensionFilter("Image", "*.JPEG"), new FileChooser.ExtensionFilter("Image", "*.JPG"));
		this.selectedFile = chooser.showOpenDialog(stage);
	}

	@Override
	public File getSelectedFile() {
		return selectedFile;
	}

	@Override
	public void clipboardChanged() {
		this.paste.setDisable(clipboard.isEmpty());
	}

	private void setCurrentColor(Color currentColor) {
		if (selection.getContents().isEmpty())
			return;
		for (Clip c : selection.getContents()) {
			CommandColor cc = new CommandColor(this, c, currentColor);
			stack.addCommand(cc);
			cc.execute();
		}
	}

	@Override
	public Color getCurrentColor() {
		return picker.getValue();
	}

	@Override
	public void commandStackChanged() {
		undo.setDisable(stack.isUndoEmpty());
		redo.setDisable(stack.isRedoEmpty());
	}

	@Override
	public void boardChanged() {
		board.draw(zoneDessin.getGraphicsContext2D());
	}

}
