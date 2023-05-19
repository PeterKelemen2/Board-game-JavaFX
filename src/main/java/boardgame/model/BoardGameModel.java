package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

    int clickedYellowX = 0;
    int clickedYellowY = 0;
    int wasRedX = 0;
    int wasRedY = 0;
    int wasBlueX = 0;
    int wasBlueY = 0;

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

    public String whatColor(int i, int j){
        switch (board[i][j].get()){
            case RED:
                wasRedX = i;
                wasRedY = j;
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
        String color = null;

        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                color = whatColor(i,j);

                switch(color){
                    case "red" -> {
                        colorData[i][j] = 1;
                        // TODO find a way to get last clicked red
                        //wasRedX = i;
                        //wasRedY = j;
                        //System.out.println("Clicked on red at " + wasRedX + " " + wasRedY );
                    }
                    case "blue" -> colorData[i][j] = 2;
                    case "black" -> colorData[i][j] = 3;
                    default -> colorData[i][j] = 0;
                }
            }
        }
    }

    public void resetBoardAfterStepShow(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                colorCode = colorData[i][j];
                switch (colorCode){
                    case 1 -> board[i][j].set(Square.RED);
                    case 2 -> board[i][j].set(Square.BLUE);
                }
            }
        }
    }

    public void printColorData(){
        getColorData();
        for (int i = 0; i < 6; i++){
            System.out.println();
            for(int j = 0; j < 7; j++){
                System.out.print(colorData[i][j] + " ");
            }
        }
        System.out.println();
    }

    public void makeMove(){
        int fromX = wasRedX;
        int fromY = wasRedY;
        int toX = clickedYellowX;
        int toY = clickedYellowY;
        System.out.println("Moved from " + fromX + " " + fromY + " to " + toX + " " + toY);
        colorData[toX][toY] = colorData[fromX][fromY];
        colorData[fromX][fromY] = 0;
        getColorData(); // TODO not sure if here appropriate
    }

    public void printMatrix(int[][] matrix){

        if(sumOfMatrix(matrix) !=0) {
            System.out.println("Legal moves on:");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {

                        System.out.print(matrix[i][j] + " ");

                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public int sumOfMatrix(int[][] matrix){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<2 ; j++){
                sumOfMatrix += matrix[i][j];
            }
        }
        return sumOfMatrix;
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

    public void nullifyLegalMoveMatrix(){
        for(int i=0; i<3; i++){
            legalMove[i][0] = 0;
            legalMove[i][1] = 0;
        }
    }

    public void initializeCache(){ // TODO: Needs sorting out
        for(int i=0; i<3; i++){
            for (int j=0; j<2; j++){
                legalMove[i][j] = 9;
                wasBlueMatrix[i][j] = 9;
                wasRedMatrix[i][j] = 9;
            }
        }
    }

    public void purgeShown(){
        nullifyLegalMoveMatrix();

        for(int i=0; i<= boardIndexHeight; i++){
            for (int j=0; j<=boardIndexWidth; j++){
                if(board[i][j].get() == Square.YELLOW){
                    board[i][j].set(Square.NONE);
                }
            }
        }

        for(int i = 0; i<3; i++){
            for (int j = 0; j<2; j++){
                sumOfWasBlueMatrix += wasBlueMatrix[i][j];
                sumOfWasRedMatrix += wasRedMatrix[i][j];
            }
        }
        if(sumOfWasBlueMatrix != 0){
            //printMatrix(wasBlueMatrix);
            for(int i=0; i<3; i++){
                xx = wasBlueMatrix[i][0];
                yy = wasBlueMatrix[i][1];
                board[xx][yy].set(Square.BLUE);
            }
        }

        if(sumOfWasRedMatrix != 0){

            for(int i=0; i<3; i++){
                xx = wasRedMatrix[i][0];
                yy = wasRedMatrix[i][1];
                board[xx][yy].set(Square.RED);
            }
        }

        x = 0;
        lx = 0;
        sumOfWasBlueMatrix = 0;
        sumOfWasRedMatrix = 0;
        sumOfMatrix = 0;

    }

    public void purgeShownAdvanced(){ // TODO: Needs fixing
        //nullifyLegalMoveMatrix();

        // Core problem lies within being able to discard false reports

        for(int auxX = 0; auxX < 3; auxX++){
            if(legalMove[auxX][0] == wasBlueMatrix[auxX][0] &&
               legalMove[auxX][1] == wasBlueMatrix[auxX][1]){
                board[legalMove[auxX][0]][legalMove[auxX][1]].set(Square.BLUE);
            } else {
                board[legalMove[auxX][0]][legalMove[auxX][1]].set(Square.NONE);
            }

        }
        nullifyLegalMoveMatrix();
        initializeCache();
    }

    public boolean checkIfMiddle(int i, int j){
        if(
                (i >= 0 && i < 5) &&
                (j > 0 && j < 6)
                //(i == 2 && j == 4) &&
                //(i == 3 && j == 2)
        ){
            return true;
        }
        return false;
    }

    public void showLegalMoves(int i, int j){
        purgeShown();
        clickedOnRed(i,j);
        getColorData();
        printColorData();

        int onRowToShow = i+1;

        // if not on first or last column
        if(j-1 < 0){
            board[onRowToShow][j].set(Square.YELLOW);
            board[onRowToShow][j+1].set(Square.YELLOW);

        } else if(j+1 > 6){
            board[onRowToShow][j-1].set(Square.YELLOW);
            board[onRowToShow][j].set(Square.YELLOW);
        } else {
            board[onRowToShow][j-1].set(Square.YELLOW);
            board[onRowToShow][j].set(Square.YELLOW);
            board[onRowToShow][j+1].set(Square.YELLOW);
        }




    }

    //public void

    public void showMove(int i, int j){
        purgeShown();

            if(checkIfMiddle(i,j)){
                for(int k = j-1; k<= j+1; k++){


                    /*
                    if(board[i + 1][k].get() != Square.BLACK && board[i][j].get() == Square.RED){
                        if(board[i+1][k].get() == Square.BLUE){
                            wasBlueMatrix[x][0] = i+1;
                            wasBlueMatrix[x][1] = k;
                            //printMatrix(wasBlueMatrix);
                            x++;
                            blueInitialized = true;

                        }
                        if(board[i+1][k].get() == Square.RED){
                            wasRedMatrix[rx][0] = i+1;
                            wasRedMatrix[rx][1] = k;
                            //printMatrix(wasBlueMatrix);
                            rx++;
                            //blueInitialized = true;

                        }

                        board[i + 1][k].set(Square.YELLOW);
                        legalMove[lx][0] = i+1;
                        legalMove[lx][1] = k;
                        lx++;
                        sumOfMatrix(legalMove);
                    }
                    */
                }
            }


        if((j == 0) && board[i][j].get() == Square.RED){
            board[i+1][j].set(Square.YELLOW);
            board[i+1][j+1].set(Square.YELLOW);

            wasRedMatrix[rx][0] = i+1;
            wasRedMatrix[rx][1] = j;
            wasRedMatrix[rx++][0] = i+1;
            wasRedMatrix[rx++][1] = j+1;

            legalMove[0][0] = i+1;
            legalMove[0][1] = j;
            legalMove[1][0] = i+1;
            legalMove[1][1] = j+1;

            //lx++;
            sumOfMatrix(legalMove);
        }else if(j == 6 && board[i][j].get() == Square.RED){

            board[i+1][j-1].set(Square.YELLOW);
            board[i+1][j].set(Square.YELLOW);

            wasRedMatrix[0][0] = i+1;
            wasRedMatrix[0][1] = j-1;
            wasRedMatrix[1][0] = i+1;
            wasRedMatrix[1][1] = j;

            legalMove[0][0] = i+1;
            legalMove[0][1] = j-1;
            legalMove[1][0] = i+1;
            legalMove[1][1] = j;
            sumOfMatrix(legalMove);

        }
        //System.out.println("Legal moves on:");
        printMatrix(legalMove);
        x = 0;
        lx = 0;
        rx = 0;
    }

    public void showMoveSzar(int i, int j){

        purgeShown();

        if((i >= 0 && i < 5) && (j > 0 && j < 6)){
            if((i == 2 && j== 4) || (i == 3 && j == 2)){
                System.out.println("Forbibben square");
            } else {
                for(int k  = j-1; k<= j+1; k++){
                    if(board[i+1][k].get() != Square.BLACK){
                        board[i + 1][k].set(Square.YELLOW);
                    }
                }
            }
        }
        if((j == 0)){
            board[i+1][j].set(Square.YELLOW);
            board[i+1][j+1].set(Square.YELLOW);
        }else if(j == 6){

            board[i+1][j-1].set(Square.YELLOW);
            board[i+1][j].set(Square.YELLOW);
        }



    }

    public void clickedOnRed(int i, int j){
        clickedRedX = i;
        clickedRedY = j;
        //showMove(i,j);
        System.out.println("Clicked on red");
    }


    public void moveToYellow(int i, int j){
        board[clickedYellowX][clickedYellowY].set(Square.RED);
        board[clickedRedX][clickedRedY].set(Square.NONE);

        System.out.println("Moved to Yellow");
        purgeShown();
    }

    public void moveToBlue(int i, int j){

        for(int k=0; k<3; k++){
            if(wasBlueMatrix[k][0] == wasBlueX && wasBlueMatrix[k][1] == wasBlueY){
                board[i][j].set(Square.RED);
                board[wasBlueX][wasBlueY].set(Square.NONE);
            }
        }

        System.out.println("Moved to Blue");
        purgeShownAdvanced();
    }



    public void move(int i, int j) {

        showMove(i, j);
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
