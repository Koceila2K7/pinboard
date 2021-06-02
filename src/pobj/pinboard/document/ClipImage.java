package pobj.pinboard.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip {
	private Image image;
	private File filename;

	public ClipImage(double left, double top, File filename) {
		super(left, 0, 0, 0, Color.WHITE);
		this.filename = filename;
		try {
			image = new Image(new FileInputStream(filename.getAbsoluteFile()));
			this.setGeometry(left, top, left + image.getWidth(), top + image.getHeight());
		} catch (FileNotFoundException e) {
			this.messageErreur(e.getMessage());
		}
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(image, getLeft(), getTop());
	}

	@Override
	public Clip copy() {
		return new ClipImage(getLeft(), getTop(), filename);
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		super.setGeometry(left, top, left + image.getWidth(), top + image.getHeight());
	}

	private void messageErreur(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		return;
	}
}
