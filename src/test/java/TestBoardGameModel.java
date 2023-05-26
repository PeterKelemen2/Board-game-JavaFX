import boardgame.BoardGameController;
import boardgame.model.BoardGameModel;
import boardgame.model.Square;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.tinylog.Logger;

public class TestBoardGameModel {

    @Test
    void testSetupBoard(){
        BoardGameModel model = new BoardGameModel();

        model.setupBoard(0, 2);
        Square square1 = model.getSquareAtPosition(0, 2);
        Logger.info("Square at (0, 2): " + square1);  // Output: Square at (0, 2): RED

        model.setupBoard(5, 4);
        Square square2 = model.getSquareAtPosition(5, 4);
        Logger.info("Square at (5, 4): " + square2);  // Output: Square at (5, 4): BLUE

        model.setupBoard(2, 3);
        Square square3 = model.getSquareAtPosition(2, 3);
        Logger.info("Square at (2, 3): " + square3);  // Output: Square at (2, 3): NONE
    }

    @Test
    void testGetColorData() {

        BoardGameModel model = new BoardGameModel();

        for(int i=0; i<6; i++){
            for(int j = 0; j<7; j++){
                model.setupBoard(i,j);
            }
        }
        model.getColorData();

        Assertions.assertEquals(1, model.colorData[0][0]);
        Assertions.assertEquals(0, model.colorData[1][1]);
        Assertions.assertEquals(3, model.colorData[3][2]);
        Assertions.assertEquals(2, model.colorData[5][2]);
    }

    @Test
    void testWhatColor(){
        BoardGameModel model = new BoardGameModel();
        for(int i=0; i<6; i++){
            for(int j = 0; j<7; j++){
                model.setupBoard(i,j);
            }
        }

        Assertions.assertEquals("red", model.whatColor(0,0));
        Assertions.assertEquals("no color", model.whatColor(1,1));
        Assertions.assertEquals("black", model.whatColor(3,2));
        Assertions.assertEquals("blue", model.whatColor(5,2));
    }

}
