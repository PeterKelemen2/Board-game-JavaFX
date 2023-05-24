package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.GamePhase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import org.json.simple.JSONObject;
import org.tinylog.Logger;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

    private Color customBlue = Color.rgb(47,56,174);
    private Color customRed = Color.rgb(217,25,38);
    private Color customYellow = Color.rgb(251,202,4);


    public Text text;
    public Text winner;
    public Text turns;

    private String redNameString;
    private String blueNameString;
    private int turnsTaken = 1;

    @FXML
    public TextField redName;
    @FXML
    public TextField blueName;
    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();

    private String path = "output.json";

    private List<Game> gameList = new ArrayList<>();

    public void jsonWriterGSON(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(gameList);

        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(gameList, writer);
            Logger.info("JSON file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jsonReaderGSON(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Game>>(){}.getType();

            gameList = gson.fromJson(reader, listType);

            for (Game game : gameList) {
                Logger.info("Name: " + game.getName() + ", Score:" + game.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backTostart(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) board.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Back to the Start Screen");
    }

    @FXML
    public void restartGame(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/boardGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) board.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Restarted game");
    }

    @FXML
    public void loadEndScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) board.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Restarted game");
    }

    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            Logger.error("Couldn't load the FXML file"+"\n"+ e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {

        jsonReaderGSON();

        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
                model.setupBoard(i, j);
                model.getColorData();
            }
        }

        text.setFill(customRed);
        Logger.info("Board initialized");


        redName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                redNameString = redName.getText();
                model.setRedName(redNameString);
                Logger.info("Red player name set: " + redNameString);
            }
        });

        blueName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                blueNameString = blueName.getText();
                model.setBlueName(blueNameString);
                Logger.info("Blue player name set: " + blueNameString);
            }
        });

        text.textProperty().bind(Bindings.concat("", Bindings
                .when(model.currentPhase.isEqualTo(GamePhase.RED))
                .then("Round of: Red")
                .otherwise(
                        Bindings.when(model.currentPhase.isEqualTo(GamePhase.BLUE))
                                .then("Round of: Blue")
                                .otherwise(Bindings.when(model.currentPhase.isEqualTo(GamePhase.OVER))
                                        .then("")
                                        .otherwise(""))
                )));

        winner.textProperty().bind(Bindings.concat("", Bindings
                .when(model.getBlueWon())
                .then("Winner: Blue")
                .otherwise(
                        Bindings.when(model.getRedWon())
                                .then("Winner: Red")
                                .otherwise(""))));

        turns.setText("Turns taken: " + turnsTaken);
    }



    public StackPane createSquare(int i, int j) {

        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);

        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i, j).get()) {
                            case NONE -> Color.TRANSPARENT;
                            case RED -> customRed;
                            case BLUE -> customBlue;
                            case YELLOW -> customYellow;
                            case BLACK -> Color.BLACK;
                            case ORANGERED -> Color.ORANGERED;

                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);

        if((i == 2 && j== 4) || (i == 3 && j == 2)){
            square.getStyleClass().add("forbiddenSquare");
        }

        return square;
    }

    private void turnsTakenText(){
        turnsTaken++;
        model.setTurnsTaken(turnsTaken);
        turns.setText("Turns taken: " + turnsTaken);
    }

    @FXML
    private void handleMouseClick(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);

        String color = model.whatColor(row,col);

        if(model.currentPhase.get() == GamePhase.RED){
            if(color.equals("red")){
                model.manageBoardAfterStepShow();
                model.showLegalMoves(row,col, "red");
                model.clickedOnRed(row,col);
            }

            if(color.equals("yellow")){
                model.makeMove();
                if(model.checkForGameOver()){
                    if(redNameString == null){
                        redNameString = "Unknown";
                    }
                    Game game = new Game(redNameString, turnsTaken);
                    gameList.add(game);
                    Logger.info(game);
                    jsonWriterGSON();
                }
                model.currentPhase.set(GamePhase.BLUE);
                turnsTakenText();
                text.setFill(customBlue);
            }

        } else {
            if(color.equals("blue")){
                model.manageBoardAfterStepShow();
                model.showLegalMoves(row, col, "blue");
                model.clickedOnBlue(row,col);
            }

            if(color.equals("yellow")){
                model.makeMove();
                if(model.checkForGameOver()){
                    if(blueNameString == null){
                        blueNameString = "Unknown";
                    }
                    Game game = new Game(blueNameString, turnsTaken);
                    gameList.add(game);
                    Logger.info(game);
                    jsonWriterGSON();
                }
                model.currentPhase.set(GamePhase.RED);
                turnsTakenText();
                text.setFill(customRed);
            }
        }


        if(color.equals("no color")){
            Logger.info("Clicked on empty square at (" + row + " " + col + ")");
        }
    }
}
