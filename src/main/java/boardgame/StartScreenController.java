package boardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;


public class StartScreenController {

    private String alataPath = "fonts/Alata-Regular.ttf";

    @FXML
    private void initialize(){

        Logger.info("Start Screen loaded");

        try {
            Font alataFont = Font.loadFont( Main.class.getClassLoader().getResourceAsStream( alataPath), 30);
            Logger.info("Font loaded successfully");
        } catch (Exception e){
            Logger.info("Something went wrong!");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void startGame(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Started");
    }

    @FXML
    public void loadEndScreen(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("End Scene loaded");
    }

    @FXML
    public void loadHelpScreen(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/helpScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Help Scene loaded");
    }

    private Parent fxmlLoading(FXMLLoader loader){
        try{
            return loader.load();
        } catch (IOException e){
            Logger.error("Couldn't load the FXML file \n" + e);
            throw new RuntimeException(e);
        }
    }
}
