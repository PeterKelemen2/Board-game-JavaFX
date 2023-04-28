package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.*;

public class BoardGameModel {

    /*
    Board:
    00 01 02 03 04 05 06
    10 11 12 13 14 15 16
    20 21 22 23 24 25 26
    30 31 32 33 34 35 36
    40 41 42 43 44 45 46
    50 51 52 53 54 55 56
    */

    public static int BOARD_SIZE = 7;
    public int x;
    public int y;

    int boardIndexWidth = 6;
    int boardIndexHeight = 5;


    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    public BoardGameModel() {
        for (var i = 0; i <= boardIndexHeight; i++) {
            for (var j = 0; j <= boardIndexWidth; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }


    public void printMatrix(){
        for(int i=0; i<=boardIndexHeight;i++){
            System.out.println();
            for (int j=0; j<=boardIndexWidth;j++){
                System.out.print(i + "" + j + " ");
            }
        }
    }

    public void purgeShown(){
        for(int i=0; i<= boardIndexHeight; i++){
            for (int j=0; j<=boardIndexWidth; j++){
                if(board[i][j].get() == Square.YELLOW){
                    board[i][j].set(Square.NONE);
                }
            }
        }
    }
    public void showMove(int i, int j){
        /*
        0 0 0   00 01 02   01 lépései: [i+1][j-1], [i+1][j], [i+1][j+1]
        0 0 0   10 11 12   01 törlése, csak i[0]-t tudom: board[i+1][i] board[i+1][i+1] board[i+1][i+2]
        0 0 0   20 21 22
        */

        purgeShown();

            if (j > 0 && j + 1 < 7 && i < 5) {
                for (int k = j - 1; k <= j + 1; k++) {
                    board[i + 1][k].set(Square.YELLOW);
                }
            } else if (j == 0 && i < 5) {
                board[i + 1][j].set(Square.YELLOW);
                board[i + 1][j + 1].set(Square.YELLOW);
            } else if (j == 6 && i < 5) {
                board[i + 1][j - 1].set(Square.YELLOW);
                board[i + 1][j].set(Square.YELLOW);
            }


    }
    public void move(int i, int j) {
        // két változó kellene, az egyik a current hely, a másik az ahova lépni akarok
        // a current hely Color.NONE-t kap, a másik pedig a saját színét

        showMove(i,j);
        /*
        board[i][j].set(
                switch (board[i][j].get()) {
                    case NONE -> Square.RED;
                    case RED -> Square.BLUE;
                    case BLUE -> Square.NONE;
                }
        );
         */
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }

}
