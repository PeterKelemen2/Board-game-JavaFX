package boardgame;

import javafx.application.Application;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.tinylog.Logger;

public class Main {

    public static void main(String[] args) {
        Player p1 = new Player("Peti", 12, LocalTime.now());
        System.out.println(p1);

        Application.launch(BoardGameApplication.class, args);


    }

}
