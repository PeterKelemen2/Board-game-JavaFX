package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.GamePhase;
import boardgame.model.Square;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
//import org.json.simple.JSONObject;
import org.tinylog.Logger;


import java.io.*;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

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

    private String path = "/result.json";

    public void jsonWriterGSON(){
        Player testPlayer = new Player("RedName", turnsTaken);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(testPlayer);

        try (FileWriter writer = new FileWriter("output.json")) {
            gson.toJson(testPlayer, writer);
            Logger.info("JSON file created successfully!");
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

        jsonWriterGSON();

        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
                model.setupBoard(i, j);
                model.getColorData();
            }
        }
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
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                            case YELLOW -> Color.YELLOWGREEN;
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
                    Player p = new Player(color, turnsTaken);
                    //writeResult();
                    Logger.info(p);
                }
                //model.checkForGameOver();
                model.currentPhase.set(GamePhase.BLUE);
                turnsTakenText();
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
                    Player p = new Player(color, turnsTaken);
                    //writeResult();
                    Logger.info(p);
                }
                //model.checkForGameOver();
                model.currentPhase.set(GamePhase.RED);
                turnsTakenText();
            }
        }


        if(color.equals("no color")){
            Logger.info("Clicked on empty square at (" + row + " " + col + ")");
        }
    }
}
