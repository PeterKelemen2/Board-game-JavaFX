package boardgame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BoardGameController {

    @FXML
    private GridPane board;


    @FXML
    private void initialize() {
        board.getStyleClass().add("board");
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i,j);
                //board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare(int i, int j) {

        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
        piece.setFill(Color.TRANSPARENT);
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::showStep); // MOUSE CLICK HANDLER


        if(i == 0){
            piece.setFill(Color.RED);
        }

        if(i == 5){
            piece.setFill(Color.BLUE);
        }

        if((i == 2 && j == 4) || (i == 3 && j == 2)){
            piece.setFill(Color.TRANSPARENT);
        }

        return square;

    }


    private void showStep(MouseEvent event){
        // egyszer klikkelünk, megmutatja a valid lépést
        // aztán amikor oda léptünk akkor eltünteti azokat és
        // cserél színt (hogy tényleg lépés legyen)

        var sq = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(sq);
        var col = GridPane.getColumnIndex(sq);
        System.out.printf("Click on square (%d,%d)%n", row, col);
        var coin = (Circle) sq.getChildren().get(0);




        /*
        for(int i = col-1; i<col+2; i++){
            if(i>=0 && i<board.getColumnCount()){
                var piece = new Circle(50);
                piece.setFill(Color.ORANGE);
                board.add(piece,i,row+1);
            }
        }*/
    }

    Node getChildAtRowCol(int row, int col){
        for (Node child: board.getChildren()){
            if(GridPane.getColumnIndex(child) == col
            && GridPane.getRowIndex(child) == row){
                return child;
            }
        }
        return null;
    }

    Node getNodeByCoordinate(int row, int column) {
        for (Node node : board.getChildren()) {
            if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return node;
            }
        }
        return null;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)%n", row, col);
        var coin = (Circle) square.getChildren().get(0);
        coin.setFill(nextColor((Color) coin.getFill()));
    }

    private Color nextColor(Color color) {
        if (color == Color.TRANSPARENT) {
            return Color.RED;
        }
        if (color == Color.RED) {
            return Color.BLUE;
        }
        return Color.TRANSPARENT;
    }

}