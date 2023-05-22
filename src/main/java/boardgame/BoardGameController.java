package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.GamePhase;
import boardgame.model.Square;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.tinylog.Logger;

public class BoardGameController {

    public Text text;
    public Text winner;

    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
                model.setupBoard(i, j);
                model.getColorData();
            }
        }
        Logger.info("Board initialized");

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
    }


    public StackPane createSquare(int i, int j) {

        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);

/*
        piece.fillProperty().bind(Bindings.when(model.squareProperty(i, j).isEqualTo(Square.NONE))
                .then(Color.TRANSPARENT)
                .otherwise(Bindings.when(model.squareProperty(i, j).isEqualTo(Square.HEAD))
                        .then(Color.RED)
                        .otherwise(Color.BLUE))
        );
*/
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

        //square.setOnMouseClicked(this::handleMouseClick);
        //square.setOnMouseClicked(this::handleLegalMoveClick);

        if((i == 2 && j== 4) || (i == 3 && j == 2)){
            square.getStyleClass().add("forbiddenSquare");
        }

        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);

        //System.out.printf("Click on square (%d,%d)%n", row, col);

        String color = model.whatColor(row,col);

        if(model.currentPhase.get() == GamePhase.RED){
            if(color.equals("red")){
                model.manageBoardAfterStepShow();
                model.showLegalMoves(row,col, "red");
                model.clickedOnRed(row,col);

            }

            if(color.equals("yellow")){
                model.makeMove();
                model.checkForGameOver();
                model.currentPhase.set(GamePhase.BLUE);
            }


        } else {
            if(color.equals("blue")){
                model.manageBoardAfterStepShow();
                model.showLegalMoves(row, col, "blue");
                model.clickedOnBlue(row,col);
            }

            if(color.equals("yellow")){
                model.makeMove();
                model.checkForGameOver();
                model.currentPhase.set(GamePhase.RED);
            }
        }


        if(color.equals("no color")){
            Logger.info("Clicked on empty square at (" + row + " " + col + ")");
            //System.out.println("No Color");
        }
    }
}
