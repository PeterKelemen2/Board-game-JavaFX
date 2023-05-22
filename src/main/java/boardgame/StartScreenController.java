package boardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;


public class StartScreenController {

    @FXML
    public void startGame(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Started");
    }

    public void showEndScreen(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("End screen shown");
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
