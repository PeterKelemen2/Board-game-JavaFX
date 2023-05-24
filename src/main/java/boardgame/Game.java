package boardgame;

public class Game {
    private String name;
    private int score;


    public Game(String name, int score) {
        this.name = name;
        this.score = score;

    }

    public Game() {
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


    @Override
    public String toString() {
        return name + ":" + score;
    }
}


