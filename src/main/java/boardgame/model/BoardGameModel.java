package boardgame.model;

import boardgame.Game;
import javafx.beans.property.*;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

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
    private int boardEnd;
    private String color = null;
    private int playerColor = 0;
    private int turnsTaken;
    public Game p;

    private String redName;
    private String blueName;


    public int[][] colorData = new int [6][7];
    private int redCount;
    private int blueCount;
    private int nrOfLegalRedMoves = 0;
    private int nrOfLegalBlueMoves = 0;
    private SimpleBooleanProperty redWon = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty blueWon = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isTie = new SimpleBooleanProperty(false);

    public ObjectProperty<GamePhase> currentPhase = new SimpleObjectProperty<>(GamePhase.RED);
    private boolean isGameOver = false;

    /**
     * Retrieves the color of a specific cell on the game board.
     *
     * @param i The row index of the cell.
     * @param j The column index of the cell.
     * @return The color of the specified cell as a string.
     */

    public String whatColor(int i, int j){
        switch (board[i][j].get()){
            case RED:
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

    /**
     * Checks if the red player won
     * @return {@code true} if the red player won, {@code false} otherwise.
     */
    public BooleanProperty getRedWon(){
        return redWon;
    }

    /**
     * Checks if the blue player won
     * @return {@code true} if the blue player won, {@code false} otherwise.
     */
    public BooleanProperty getBlueWon(){
        return blueWon;
    }

    /**
     * Checks if the game ended on a tie
     * @return {@code true} if the game ended on a tie, {@code false} otherwise.
     */
    public BooleanProperty getIsTie(){
        return isTie;
    }

    /**
     * Sets the number for turns taken in the game
     * @param turnsTaken The number of turns so far.
     */
    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }

    /**
     * Sets the name for the red player
     * @param redName The name of the red player
     */
    public void setRedName(String redName) {
        this.redName = redName;
    }

    /**
     * Sets the name for the blue player
     * @param blueName The name of the blue player
     */
    public void setBlueName(String blueName) {
        this.blueName = blueName;
    }

    /**
     * Checks if the game is over by evaluating the current state of the game.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean checkForGameOver(){
        // Calculate the number of legal moves for red and blue players
        calculateRedMoveCount();
        calculateRedCount();
        calculateBlueMoveCount();
        calculateBlueCount();

        // Check if red player has lost
        if(redCount == 0 || nrOfLegalRedMoves == 0){
            blueWon.set(true);
            currentPhase.set(GamePhase.OVER);
            p = new Game(blueName, turnsTaken);
            Logger.info(" == Blue won == ");
            return true;
        }
        // Check if blue player has lost
        if(blueCount == 0 || nrOfLegalBlueMoves == 0){
            redWon.set(true);
            currentPhase.set(GamePhase.OVER);
            p = new Game(redName, turnsTaken);
            Logger.info(" == Red won == ");
            return true;
        }
        // Check if the game is tied
        if(blueCount == redCount && (nrOfLegalBlueMoves == 0 && nrOfLegalRedMoves == 0)){
            isTie.set(true);
            currentPhase.set(GamePhase.OVER);
            p = new Game("Tie", turnsTaken);
            Logger.info(" == Tie == ");
            return true;
        }
        // The game is not over
        return false;
    }

    private void calculateRedMoveCount(){
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

    private void calculateBlueMoveCount(){
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

    private void calculateRedCount(){
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 1){
                    redCount++;
                }
            }
        }
    }

    private void calculateBlueCount(){
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                if(colorData[i][j] == 2){
                    blueCount++;
                }
            }
        }
    }

    /**
     * Retrieves the color data for each cell on the game board.
     * Updates the colorData array based on the color of each cell.
     */
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

    /**
     * Updates the game board after a step is shown.
     * Updates each cell on the board based on the color data.
     */
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

    /**
     * Makes a move on the game board based on the current game phase and selected cell positions.
     * Updates the board, color data, and manages the board after the step is shown.
     */
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
        Logger.info("Moved from (" + fromX + " " + fromY + ") to (" + toX + " " + toY + ")");

        if(currentPhase.get() == GamePhase.RED){
            board[toX][toY].set(Square.RED);
        } else if (currentPhase.get() == GamePhase.BLUE){
            board[toX][toY].set(Square.BLUE);
        }

        board[fromX][fromY].set(Square.NONE);
        colorData[toX][toY] = colorData[fromX][fromY];
        colorData[fromX][fromY] = 0;

        manageBoardAfterStepShow();
    }

    private int boardIndexWidth = 6;
    private int boardIndexHeight = 5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * Constructs a new instance of the BoardGameModel.
     * Initializes the game board with default values and sets specific cells to Square.BLACK.
     */
    public BoardGameModel() {
        for (var i = 0; i <= boardIndexHeight; i++) {
            for (var j = 0; j <= boardIndexWidth; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
            }
        }
        // Set specific cells on the board to Square.BLACK
        board[2][4].set(Square.BLACK);
        board[3][2].set(Square.BLACK);
    }

    /**
     * Retrieves the read-only object property for a specific square on the game board.
     *
     * @param i The row index of the square.
     * @param j The column index of the square.
     * @return The read-only object property for the specified square.
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Sets up the initial state of a specific cell on the game board.
     * Based on the row index and column index, sets the cell to Square.RED if it is in the top row (i = 0),
     * or sets the cell to Square.BLUE if it is in the bottom row (i = 5).
     *
     * @param i The row index of the cell.
     * @param j The column index of the cell.
     */
    public void setupBoard(int i, int j){
        if(i == 0){
            board[i][j].set(Square.RED);
        } else if(i == 5){
            board[i][j].set(Square.BLUE);
        }
    }

    public void setupBoardTwo(){
        for(int i = 0; i<6; i++){
            for(int j = 0; j<7; j++){
                if(i == 0){
                    board[i][j].set(Square.RED);
                } else if(i == 5){
                    board[i][j].set(Square.BLUE);
                }
            }
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

    /**
     * Shows the legal moves for a specific cell on the game board based on the player's color.
     * Updates the board to display the legal move cells as Square.YELLOW.
     *
     * @param i      The row index of the cell.
     * @param j      The column index of the cell.
     * @param player The color of the player. Possible values are "red" or "blue".
     */
    public void showLegalMoves(int i, int j, String player){
        getColorData();

        switch(player){
            case "red" -> {
                onRowToShow = i+1;
                playerColor = 1;
                boardEnd = 5;
            }
            case "blue" -> {
                onRowToShow = i-1;
                playerColor = 2;
                boardEnd = 0;
            }
        }

        if(j-1 < 0 && i != boardEnd){
            if(colorData[onRowToShow][j] != playerColor){
                board[onRowToShow][j].set(Square.YELLOW);
            }
            if(colorData[onRowToShow][j+1] != playerColor){
                board[onRowToShow][j+1].set(Square.YELLOW);
            }
        }
        if(j+1 > 6 && i != boardEnd){
            if(colorData[onRowToShow][j] != playerColor){
                board[onRowToShow][j].set(Square.YELLOW);
            }
            if(colorData[onRowToShow][j-1] != playerColor){
                board[onRowToShow][j-1].set(Square.YELLOW);
            }
        }
        if(j > 0 && j < 6 && i != boardEnd){
            showIfNotForbidden(onRowToShow,j, player);
        }
    }

    /**
     * Updates the variables to store the coordinates of the cell that was clicked on with red color.
     * Logs the information about the click event.
     *
     * @param i The row index of the clicked cell.
     * @param j The column index of the clicked cell.
     */
    public void clickedOnRed(int i, int j){
        wasRedX = i;
        wasRedY = j;
        Logger.info("Clicked on red at (" + wasRedX + " " + wasRedY + ")");
    }

    /**
     * Updates the variables to store the coordinates of the cell that was clicked on with blue color.
     * Logs the information about the click event.
     *
     * @param i The row index of the clicked cell.
     * @param j The column index of the clicked cell.
     */
    public void clickedOnBlue(int i, int j){
        wasBlueX = i;
        wasBlueY = j;
        Logger.info("Clicked on red at (" + wasRedX + " " + wasRedY + ")");
    }

    /**
     * Returns a string representation of the game board.
     * The string contains the ordinal values of the squares on the board, separated by spaces and newlines.
     *
     * @return A string representation of the game board.
     */
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

    /**
     * The entry point of the program.
     * Creates an instance of the BoardGameModel class and initializes the game model.
     *
     * @param args The command line arguments passed to the program (not used in this implementation).
     */
    public static void main(String[] args) {
        var model = new BoardGameModel();

    }

    /**
     * Retrieves the Square value at the specified position on the board.
     *
     * @param i the row index of the position
     * @param j the column index of the position
     * @return the Square value at the specified position
     */
    public Square getSquareAtPosition(int i, int j) {
        return board[i][j].get();
    }
}
