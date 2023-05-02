package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Square;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BoardGameController {

    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
                model.setupBoard(i,j);
            }
        }
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
        square.setOnMouseClicked(this::dummyClickMethod);
        //square.setOnMouseClicked(this::handleMouseClick);
        //square.setOnMouseClicked(this::handleLegalMoveClick);

        if((i == 2 && j== 4) || (i == 3 && j == 2)){
            square.getStyleClass().add("forbiddenSquare");
        }

        return square;
    }

    @FXML
    private void dummyClickMethod(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)%n", row, col);



        String color = model.whatColor(row,col);
        //model.showMoveAdvanced(row,col);

        if(color == "red"){
            model.showMoveAdvanced(row,col); // TODO: Maybe not at the best spot
            model.clickedOnRed(row,col);
        }

        if(color == "yellow"){
            model.moveToYellow(row,col);
        }

        if(color == "blue"){
            model.moveToBlue(row,col); // TODO: Fixing blue click
        }

    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)%n", row, col);
        //model.move(row, col);
        //model.showMoveAdvanced(row,col);
        System.out.println(model.whatColor(row,col));
    }

    @FXML void handleLegalMoveClick(MouseEvent event){
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Moved to square (%d,%d)%n", row, col);
        //model.makeStep(row,col);
    }


}
