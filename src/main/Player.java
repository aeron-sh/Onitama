package main;

public class Player {

    protected final char player;

    /**
     * Create a new instance of main.Player.
     */
    public Player(char player) {
        this.player = player;
    }

    /**
     * Get the turn for this player.
     *
     * @return this player's next move
     */
    public Turn getTurn() {
        return null;
    }

    /**
     * Return this player's name as a char.
     *
     * @return player's name
     */
    public char getPlayer() {
        return player;
    }
}