package boardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGameApplication extends Application {

    String iconPath = "icon2.png";
    Image icon = new Image(iconPath);
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
