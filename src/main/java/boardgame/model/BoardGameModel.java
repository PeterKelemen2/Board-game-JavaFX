package boardgame.model;

import javafx.beans.property.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.*;

public class BoardGameModel {

    public static int BOARD_SIZE = 7;

    private int colorCode;
    private int onRowToShow;

    private int clickedYellowX = 0;
    private int clickedYellowY = 0;
    private int wasRedX = 0;
    private int wasRedY = 0;
    private int wasBlueX = 0;
    private int wasBlueY = 0;
    private int fromX;
    private int fromY;
    private String color = null;
    private int playerColor = 0;

    private int[][] colorData = new int [6][7];
    int redCount;
    int blueCount;
    int nrOfLegalRedMoves = 0;
    int nrOfLegalBlueMoves = 0;
    private SimpleBooleanProperty redWon = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty blueWon = new SimpleBooleanProperty(false);

    public ObjectProperty<GamePhase> currentPhase = new SimpleObjectProperty<>(GamePhase.RED);
    private boolean isGameOver = false;

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


    public BooleanProperty getRedWon(){
        return redWon;
    }

    public BooleanProperty getBlueWon(){
        return blueWon;
    }

    public void checkForGameOver(){
        getRedMoveCount();
        getRedCount();
        getBlueMoveCount();
        getBlueCount();

        if(redCount == 0 || nrOfLegalRedMoves == 0){
            blueWon.set(true);
            currentPhase.set(GamePhase.OVER);
            System.out.println(" == Blue won == ");
        }
        if(blueCount == 0 || nrOfLegalBlueMoves == 0){
            redWon.set(true);
            currentPhase.set(GamePhase.OVER);
            System.out.println(" == Red won == ");
        }

    }

    private void getRedMoveCount(){
        nrOfLegalRedMoves = 0;
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 1 && i != 5){
                    if(j == 0){
                        if(colorData[i+1][0] == 0 ||
                                colorData[i+1][1] == 0){
                            nrOfLegalRedMoves++;
                        }
                    }
                    if(j == 6){
                        if(colorData[i+1][5] == 0 ||
                                colorData[i+1][6] == 0){
                            nrOfLegalRedMoves++;
                        }
                    }
                    if(j > 0 && j < 6){
                        if(colorData[i+1][j-1] == 0 ||
                                colorData[i+1][j] == 0 ||
                                colorData[i+1][j+1] == 0){
                            nrOfLegalRedMoves++;
                        }
                    }
                }
            }
        }
    }

    private void getBlueMoveCount(){
        nrOfLegalBlueMoves = 0;
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 2 && i != 0){
                    if(j == 0){
                        if(colorData[i-1][0] == 0 ||
                                colorData[i-1][1] == 0){
                            nrOfLegalBlueMoves++;
                        }
                    }
                    if(j == 6){
                        if(colorData[i-1][5] == 0 ||
                                colorData[i-1][6] == 0){
                            nrOfLegalBlueMoves++;
                        }
                    }
                    if(j > 0 && j < 6){
                        if(colorData[i-1][j-1] == 0 ||
                                colorData[i-1][j] == 0 ||
                                colorData[i-1][j+1] == 0){
                            nrOfLegalBlueMoves++;
                        }
                    }
                }
            }
        }
    }

    private void getRedCount(){
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 1){
                    redCount++;
                }
            }
        }
    }

    private void getBlueCount(){
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 2){
                    blueCount++;
                }
            }
        }
    }

    private void checkPartOfMap(int i, int j){

        if(currentPhase.get() == GamePhase.RED){
            onRowToShow = i+1;

            if(colorData[i][j] == 1 && i != 5 && (j > 0 && j < 6) &&
                    (colorData[onRowToShow][j-1]== 0 ||
                    colorData[onRowToShow][j] == 0 ||
                    colorData[onRowToShow][j+1] == 0)){
                nrOfLegalRedMoves++;
            }
            if(colorData[i][j] == 1 && i != 5 && j == 0 &&
                    (colorData[onRowToShow][j] == 0 ||
                     colorData[onRowToShow][j+1] == 0)){
                nrOfLegalRedMoves++;
            }
            if(colorData[i][j] == 1 && i != 5 && j == 6 &&
                    (colorData[onRowToShow][j-1] == 0 ||
                     colorData[onRowToShow][j] == 0)){
                nrOfLegalRedMoves++;
            }

        } else if (currentPhase.get() == GamePhase.BLUE){
            onRowToShow = i-1;

            if(colorData[i][j] == 2 && i != 0 && (j > 0 && j < 6) &&
                    (colorData[onRowToShow][j-1]== 0 ||
                     colorData[onRowToShow][j] == 0 ||
                     colorData[onRowToShow][j+1] == 0)){
                nrOfLegalBlueMoves++;
            }
            if(colorData[i][j] == 2 && i != 0 && j == 0 &&
                    (colorData[onRowToShow][j] == 0 ||
                     colorData[onRowToShow][j+1] == 0)){
                nrOfLegalBlueMoves++;
            }
            if(colorData[i][j] == 2 && i != 0 && j == 6 &&
                    (colorData[onRowToShow][j-1] == 0 ||
                     colorData[onRowToShow][j] == 0)){
                nrOfLegalBlueMoves++;
            }
        }
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

    private void printColorData(){
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

    private int boardIndexWidth = 6;
    private int boardIndexHeight = 5;

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

    private void showIfNotForbidden(int i, int k, String player){
        switch(player){
            case "red" -> {
                onRowToShow = i+1;
                playerColor = 1;
            }
            case "blue" -> {
                onRowToShow = i-1;
                playerColor = 2;
            }
        }

        for(int j = k-1 ; j <= k+1; j++){
            if((i == 2 && j == 4) || (i == 3 && j == 2)) {
                board[i][j].set(Square.BLACK);
            } else{
                if(colorData[i][j] != playerColor){
                    board[i][j].set(Square.YELLOW);
                }
            }
        }
    }

    public void showLegalMoves(int i, int j, String player){
        //purgeShown();
        //clickedOnRed(i,j);
        //printColorData();
        getColorData();

        switch(player){
            case "red" -> {
                onRowToShow = i+1;
                playerColor = 1;
            }
            case "blue" -> {
                onRowToShow = i-1;
                playerColor = 2;
            }
        }

        if(j-1 < 0){
            if(colorData[onRowToShow][j] != playerColor){
                board[onRowToShow][j].set(Square.YELLOW);
            }
            if(colorData[onRowToShow][j+1] != playerColor){
                board[onRowToShow][j+1].set(Square.YELLOW);
            }
        }
        if(j+1 > 6){
            if(colorData[onRowToShow][j] != playerColor){
                board[onRowToShow][j].set(Square.YELLOW);
            }
            if(colorData[onRowToShow][j-1] != playerColor){
                board[onRowToShow][j-1].set(Square.YELLOW);
            }
        }
        if(j > 0 && j < 6){
            showIfNotForbidden(onRowToShow,j, player);
        }
    }

    public void clickedOnRed(int i, int j){
        wasRedX = i;
        wasRedY = j;
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
