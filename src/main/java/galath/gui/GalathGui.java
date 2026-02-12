package galath.gui;

import java.io.IOException;

import galath.Galath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * GUI for Galath using FXML
 */
public class GalathGui extends Application {
    private Galath galath;

    @Override
    public void start(Stage stage) {
        try {
            // Initialize Galath
            galath = new Galath("./data/galath.txt");

            // Load FXML
            FXMLLoader fxmlLoader = new FXMLLoader(GalathGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Set up stage
            stage.setScene(scene);
            stage.setTitle("Galath - Personal Task Assistant");
            stage.setMinHeight(600);
            stage.setMinWidth(400);

            // Set up controller
            MainWindow controller = fxmlLoader.getController();
            controller.setGalath(galath);

            // Show welcome message
            controller.showWelcome();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
