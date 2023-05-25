package boardgame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EndScreenController {

    private String path = "output.json";
    File jsonFile = new File(path);
    List <Game> gameList = new ArrayList<>();
    public Text scoreText;
    private String alataPath = "fonts/Alata-Regular.ttf";

    @FXML
    private void initialize(){
        //Font alataFont = Font.loadFont( Main.class.getClassLoader().getResourceAsStream( alataPath), 30);

        if(jsonFile.exists()){
            scoreText.setText("");
            jsonReaderGSON();
        } else{
            scoreText.setText("No games played yet");
        }

        //scoreText.setFont(alataFont);

    }

    public void jsonReaderGSON(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Game>>(){}.getType();

            gameList = gson.fromJson(reader, listType);

            int size = gameList.size();
            int startIndex = Math.max(size - 5, 0);
            int endIndex = size;

            for (int i = startIndex; i < endIndex; i++) {
                String toAdd =  gameList.get(i).getName() + " - won in " + gameList.get(i).getScore() + " turns";
                Logger.info(toAdd);
                scoreText.setText(scoreText.getText() + "\n" + toAdd);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backToStart(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Back to Start Screen");
    }

    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            Logger.error("Couldn't load the FXML file"+"\n"+ e);
            throw new RuntimeException(e);
        }
    }
}
