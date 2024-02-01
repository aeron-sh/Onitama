package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerHuman extends Player{

    private static final String INVALID_MOVE_INPUT_MESSAGE = "Invalid number, please enter 0-4";
    private static final String INVALID_STYLE_INPUT_MESSAGE = "Invalid style, please enter one of this player's styles " +
            "(Dragon, Crab, Horse, Mantis, Rooster)";
    private static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Create a new instance of main.PlayerHuman.
     */
    public PlayerHuman(char player) {
        super(player);
    }

    @Override
    /**
     * Get the turn for this player.
     *
     * @return this player's next move
     */
    public Turn getTurn() {
        String style_name = "";
        try {
            System.out.print("Choose your style:");
            style_name = PlayerHuman.stdin.readLine();
        } catch (IOException e) {
            System.out.println(INVALID_STYLE_INPUT_MESSAGE);
        }
        int row_o = getMove("row origin: ");
        int col_o = getMove("col origin: ");
        int row_d = getMove("row destination: ");
        int col_d = getMove("col destination: ");
        return new Turn(row_o, col_o, row_d, col_d, style_name, this.player);
    }

    /**
     * Get the move for this player.
     *
     * @return move
     */
    private int getMove(String message) {
        int move, lower = 0, upper = 4;
        while (true) {
            try {
                System.out.print(message);
                String line = PlayerHuman.stdin.readLine();
                move = Integer.parseInt(line);
                if (lower <= move && move <= upper) {
                    return move;
                } else {
                    System.out.println(INVALID_MOVE_INPUT_MESSAGE);
                }
            } catch (IOException e) {
                System.out.println(INVALID_MOVE_INPUT_MESSAGE);
                break;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_MOVE_INPUT_MESSAGE);
            }
        }
        return -1;
    }
}