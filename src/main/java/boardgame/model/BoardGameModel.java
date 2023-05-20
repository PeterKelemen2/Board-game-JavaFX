package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.*;

public class BoardGameModel {


    public static int BOARD_SIZE = 7;
    public int x = 0;
    public int y = 0;
    int xx;
    int yy;
    int lx;
    int ly;
    int rx = 0;
    int colorCode;
    int onRowToShow;

    int clickedYellowX = 0;
    int clickedYellowY = 0;
    int wasRedX = 0;
    int wasRedY = 0;
    int wasBlueX = 0;
    int wasBlueY = 0;
    int fromX;
    int fromY;
    private String color = null;

    int clickedRedX = 0;
    int clickedRedY = 0;

    boolean blueInitialized = false;

    public int[][] wasBlueMatrix = new int[3][2];
    public int[][] wasRedMatrix = new int[3][2];
    public int[][] legalMove = new int[3][2];

    public int sumOfWasBlueMatrix = 0;
    public int sumOfWasRedMatrix = 0;
    public int sumOfMatrix = 0;

    public int[][] colorData = new int [6][7];

    //public int[][] wasYellow = new int[1][2];
    public int[][] wasBlue = new int[1][2];

    public ObjectProperty<GamePhase> currentPhase = new SimpleObjectProperty<>(GamePhase.RED);

    public String whatColor(int i, int j){
        switch (board[i][j].get()){
            case RED:
                //wasRedX = i;
                //wasRedY = j;
                //System.out.println("Clicked on red at " + wasRedX + " " + wasRedY );
                return "red";
            case BLUE:
                wasBlueX = i;
                wasBlueY = j;
                return "blue";
            case YELLOW:
                clickedYellowX = i;
                clickedYellowY = j;
                return "yellow";
            case BLACK:
                return "black";
            case NONE:
                return "no color";
        }
        return "Could not get color.";
    }

    public void getColorData(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                color = whatColor(i,j);

                switch(color){
                    case "red" -> colorData[i][j] = 1;
                    case "blue" -> colorData[i][j] = 2;
                    case "black" -> colorData[i][j] = 3;
                    default -> colorData[i][j] = 0;
                }
            }
        }
    }

    public void manageBoardAfterStepShow(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                colorCode = colorData[i][j];
                switch (colorCode){
                    case 0 -> board[i][j].set(Square.NONE);
                    case 1 -> board[i][j].set(Square.RED);
                    case 2 -> board[i][j].set(Square.BLUE);
                }
            }
        }
    }

    public void printColorData(){
        //getColorData();
        for (int i = 0; i < 6; i++){
            System.out.println();
            for(int j = 0; j < 7; j++){
                System.out.print(colorData[i][j] + " ");
            }
        }
        System.out.println();
    }

    public void makeMove(){
        if(currentPhase.get() == GamePhase.RED){
            fromX = wasRedX;
            fromY = wasRedY;
        }
        else if(currentPhase.get() == GamePhase.BLUE){
            fromX = wasBlueX;
            fromY = wasBlueY;
        }

        int toX = clickedYellowX;
        int toY = clickedYellowY;
        System.out.println("Moved from " + fromX + " " + fromY + " to " + toX + " " + toY);

        if(currentPhase.get() == GamePhase.RED){
            board[toX][toY].set(Square.RED);
        } else if (currentPhase.get() == GamePhase.BLUE){
            board[toX][toY].set(Square.BLUE);
        }

        board[fromX][fromY].set(Square.NONE);
        colorData[toX][toY] = colorData[fromX][fromY];
        colorData[fromX][fromY] = 0;

        manageBoardAfterStepShow();
        //purgeShown();
        printColorData();
    }

    int boardIndexWidth = 6;
    int boardIndexHeight = 5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    public BoardGameModel() {
        for (var i = 0; i <= boardIndexHeight; i++) {
            for (var j = 0; j <= boardIndexWidth; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }

        board[2][4].set(Square.BLACK);
        board[3][2].set(Square.BLACK);
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public void setupBoard(int i, int j){
        if(i == 0){
            board[i][j].set(Square.RED);
        } else if(i == 5){
            board[i][j].set(Square.BLUE);
        }
    }

// purgeShown
//    public void purgeShown(){
//        nullifyLegalMoveMatrix();
//
//        for(int i=0; i<= boardIndexHeight; i++){
//            for (int j=0; j<=boardIndexWidth; j++){
//                if(board[i][j].get() == Square.YELLOW){
//                    board[i][j].set(Square.NONE);
//                }
//            }
//        }
//
//        for(int i = 0; i<3; i++){
//            for (int j = 0; j<2; j++){
//                sumOfWasBlueMatrix += wasBlueMatrix[i][j];
//                sumOfWasRedMatrix += wasRedMatrix[i][j];
//            }
//        }
//        if(sumOfWasBlueMatrix != 0){
//            //printMatrix(wasBlueMatrix);
//            for(int i=0; i<3; i++){
//                xx = wasBlueMatrix[i][0];
//                yy = wasBlueMatrix[i][1];
//                board[xx][yy].set(Square.BLUE);
//            }
//        }
//
//        if(sumOfWasRedMatrix != 0){
//
//            for(int i=0; i<3; i++){
//                xx = wasRedMatrix[i][0];
//                yy = wasRedMatrix[i][1];
//                board[xx][yy].set(Square.RED);
//            }
//        }
//
//        x = 0;
//        lx = 0;
//        sumOfWasBlueMatrix = 0;
//        sumOfWasRedMatrix = 0;
//        sumOfMatrix = 0;
//
//    }

    private void showIfNotForbidden(int i, int k){
         for(int j = k-1 ; j <= k+1; j++){
            if((i == 2 && j == 4) || (i == 3 && j == 2)) {
                board[i][j].set(Square.BLACK);
            } else{
                board[i][j].set(Square.YELLOW);
            }
        }
    }

    public void showLegalMoves(int i, int j, String player){
        //purgeShown();
        //clickedOnRed(i,j);
        getColorData();
        printColorData();

        if(player.equals("red")) {
            onRowToShow = i + 1;
            clickedOnRed(i,j);
        } else if (player.equals("blue")) {
            onRowToShow = i - 1;
        }

        // if not on first or last column
        if(j-1 < 0 ){

            board[onRowToShow][j].set(Square.YELLOW);
            board[onRowToShow][j+1].set(Square.YELLOW);

        } else if(j+1 > 6){
            board[onRowToShow][j-1].set(Square.YELLOW);
            board[onRowToShow][j].set(Square.YELLOW);
        } else {
            /*
                board[onRowToShow][j-1].set(Square.YELLOW);
                board[onRowToShow][j].set(Square.YELLOW);
                board[onRowToShow][j+1].set(Square.YELLOW);
            */
            showIfNotForbidden(onRowToShow,j);
        }
    }

    public void clickedOnRed(int i, int j){
        wasRedX = i;
        wasRedY = j;
        //clickedRedX = i;
        //clickedRedY = j;
        //showMove(i,j);
        System.out.println("Clicked on red at " + wasRedX + " " + wasRedY);
    }

    public void clickedOnBlue(int i, int j){
        wasBlueX = i;
        wasBlueY = j;
        System.out.println("Clicked on blue at " + wasBlueX + " " + wasBlueY);
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
