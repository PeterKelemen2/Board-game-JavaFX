package boardgame;

public class Game {
    private String name;
    private int score;

    /**
     * Constructs a new Game object with the specified name and score.
     *
     * @param name  The name of the game.
     * @param score The score of the game.
     */
    public Game(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Constructs a new Game object.
     */
    public Game() {
    }

    /**
     * Returns the name associated with this Game.
     *
     * @return The name of the Game.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with this Game.
     *
     * @param name The new name for the Game.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the score associated with this Game.
     *
     * @return The score of the Game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score associated with this Game.
     *
     * @param score The new score for the Game.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns a string representation of the Game object.
     *
     * @return A string containing the name and score of the Game.
     */
    @Override
    public String toString() {
        return name + ":" + score;
    }
}


