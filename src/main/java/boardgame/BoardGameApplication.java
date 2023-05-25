package boardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGameApplication extends Application {

    private String iconPath = "icon2.png";
    private Image icon = new Image(iconPath);

    /**
     * Starts the JavaFX application by setting up the primary stage and loading the start screen.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException If an I/O exception occurs during loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Board Game");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.getIcons().add(icon);
        stage.show();
    }
}
