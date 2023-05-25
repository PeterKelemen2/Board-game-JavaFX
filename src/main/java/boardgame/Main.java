package boardgame;

import javafx.application.Application;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.tinylog.Logger;

public class Main {

    /**
     * The entry point of the Java application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(BoardGameApplication.class, args);
    }

}
