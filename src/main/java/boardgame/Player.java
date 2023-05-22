package boardgame;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Player {
    private String name;
    private int score;
    private DateTimeFormatter formatter;
    private LocalTime date;



    public Player(String name, int score, LocalTime date) {
        this.name = name;
        this.score = score;

        formatter = DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm:ss");
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Player won in " + score + " turns (" + LocalDateTime.now().format(formatter) + ")";
    }
}


