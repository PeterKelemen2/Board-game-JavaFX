package boardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class HelpScreenController {

    public Text helpText;

    @FXML
    private void initialize(){
        helpText.setText(
                "• Each player can move straight or\n" +
                "diagonally to the other end of the board.\n" +
                "• A player can lose by losing all their\n" +
                "tiles or having no more steps available.\n" +
                "• There are two forbidden squares, where\n" +
                "the player is not allowed to step.");
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

    private Parent fxmlLoading(FXMLLoader loader){
        try{
            return loader.load();
        } catch (IOException e){
            Logger.error("Couldn't load the FXML file \n" + e);
            throw new RuntimeException(e);
        }
    }

}
