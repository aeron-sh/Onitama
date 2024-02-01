package main;

import java.util.ArrayList;
import java.util.Random;

public class PlayerRandom extends Player{

    /**
     * Create a new instance of main.PlayerRandom.
     */
    public PlayerRandom(char player) {
        super(player);
    }

    /**
     * Get the turn for this player.
     *
     * @return this player's next move
     */
    public Turn getTurn(Onitama onitama) {
        char[][] board = onitama.getBoard();
        Style[] styles = onitama.getStyles();
        int total_pieces = board.length;

        ArrayList<Style> player_styles = new ArrayList<Style>();
        for (Style style: styles) {
            if (style.getOwner() == player) {
                player_styles.add(style);
            }
        }

        char pawn = OnitamaBoard.M2;
        if (player == OnitamaBoard.G1) {
            pawn = OnitamaBoard.M1;
        }

        ArrayList<int[]> locations = new ArrayList<int[]>();
        for (int row = 0; row < total_pieces; row++) {
            for (int col = 0; col < total_pieces; col++) {
                if (board[row][col] == pawn || board[row][col] == player) {
                    int[] temp = {row, col};
                    locations.add(temp);
                }
            }
        }

        ArrayList<Turn> all_moves = getMoves(player_styles, locations, onitama);
        Random random = new Random();
        int random_number = random.nextInt(total_pieces);

        return all_moves.get(random_number);

    }

    /**
     * Returns the possible outcomes for this round.
     *
     * @return an ArrayList containing all possible turn's for this player
     */
    private ArrayList<Turn> getMoves(ArrayList<Style> player_styles, ArrayList<int[]> locations, Onitama onitama) {

        ArrayList<Turn> turns = new ArrayList<Turn>();
        for (Style style: player_styles) {
            Move[] moves = style.getMoves();
            for (int[] location: locations) {
                for (Move move: moves) {
                    int rowO = location[0];
                    int colO = location[1];
                    int rowD = rowO + move.getRow();
                    int colD = colO + move.getCol();
                    if (onitama.isLegalMove(rowO, colO, rowD, colD)) {
                        Turn turn = new Turn(rowO, colO, rowD, colD, style.getName(), player);
                        turns.add(turn);
                    }
                }
            }
        }
        return turns;
    }
}
