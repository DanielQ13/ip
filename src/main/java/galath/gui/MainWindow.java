package galath.gui;

import galath.Galath;
import galath.command.Command;
import galath.exception.GalathException;
import galath.parser.Parser;
import galath.ui.Ui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Main GUI window for the Galath application.
 * Handles user interactions and displays chat messages.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Galath galath;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image galathImage = new Image(this.getClass().getResourceAsStream("/images/Galath.png"));

    /**
     * Initializes the MainWindow after FXML components are loaded.
     * Sets up auto-scrolling for the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Handle Enter key press
        userInput.setOnAction((event) -> handleUserInput());
    }

    /**
     * Sets the Galath instance for this window.
     *
     * @param galath The Galath chatbot instance
     */
    public void setGalath(Galath galath) {
        this.galath = galath;
    }

    /**
     * Shows the welcome message from Galath.
     */
    public void showWelcome() {
        String welcome = "Hello! I'm Galath\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getGalathDialog(welcome, galathImage)
        );
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * Creates dialog boxes for user input and Galath's response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                getGalathDialog(getResponse(input))
        );

        userInput.clear();

        if (input.equals("bye")) scheduleExit();
    }

    private DialogBox getGalathDialog(String response) {
        if (response.startsWith("OOPS!!!")) return DialogBox.getErrorDialog(response, galathImage);
        if (isSuccessResponse(response))   return DialogBox.getSuccessDialog(response, galathImage);
        return DialogBox.getGalathDialog(response, galathImage);
    }

    private boolean isSuccessResponse(String response) {
        return response.contains("Got it. I've added")
                || response.contains("Nice! I've marked")
                || response.contains("Noted. I've removed")
                || response.contains("OK, I've marked");
    }

    private void scheduleExit() {
        Platform.runLater(() -> {
            try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
            Platform.exit();
        });
    }

    /**
     * Gets a response from Galath for the given input.
     *
     * @param input The user's input command
     * @return Galath's response message
     */
    private String getResponse(String input) {
        try {
            Command command = Parser.parse(input);

            // Capture output using a custom Ui that returns strings
            StringBuilder output = new StringBuilder();
            Ui guiUi = new Ui() {
                @Override
                public void showMessage(String message) {
                    output.append(message);
                }

                @Override
                public void showError(String message) {
                    output.append("OOPS!!! ").append(message);
                }

                @Override
                public void showGoodbye() {
                    output.append("Bye. Hope to see you again soon!");
                }
            };

            command.execute(galath.getTasks(), guiUi, galath.getStorage());
            return output.toString();

        } catch (GalathException e) {
            return "OOPS!!! " + e.getMessage();
        }
    }
}