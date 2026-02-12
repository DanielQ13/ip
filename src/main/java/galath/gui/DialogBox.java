package galath.gui;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox dialogBubble;

    /**
     * Creates a new DialogBox with the specified text and image.
     *
     * @param text The message text to display
     * @param img The avatar image for the speaker
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            // Make dialog bubble responsive
            dialogBubble.maxWidthProperty().bind(this.widthProperty().multiply(0.75));
            dialog.maxWidthProperty().bind(dialogBubble.maxWidthProperty().subtract(20));
            dialog.setWrapText(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Make avatar circular
        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a user dialog with user styling.
     *
     * @param text The user's message
     * @param img The user's avatar image
     * @return A DialogBox styled for user messages
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialogBubble.getStyleClass().add("user-bubble");
        db.dialog.getStyleClass().add("user-label");
        return db;
    }

    /**
     * Creates a Galath dialog with Galath styling.
     *
     * @param text Galath's message
     * @param img Galath's avatar image
     * @return A DialogBox styled for Galath messages
     */
    public static DialogBox getGalathDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialogBubble.getStyleClass().add("galath-bubble");
        db.dialog.getStyleClass().add("galath-label");
        db.flip();
        return db;
    }

    /**
     * Creates an error dialog with error styling.
     *
     * @param text The error message
     * @param img Galath's avatar image
     * @return A DialogBox styled for error messages
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialogBubble.getStyleClass().add("error-bubble");
        db.dialog.getStyleClass().add("error-label");
        db.flip();
        return db;
    }

    /**
     * Creates a success dialog with success styling.
     *
     * @param text The success message
     * @param img Galath's avatar image
     * @return A DialogBox styled for success messages
     */
    public static DialogBox getSuccessDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialogBubble.getStyleClass().add("success-bubble");
        db.dialog.getStyleClass().add("success-label");
        db.flip();
        return db;
    }
}