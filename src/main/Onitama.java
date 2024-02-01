package main;

/**
 * An main.Onitama game class consisting of a game board, and keeping track of
 * which player's turn it currently is and some statistics about the game (e.g.
 * how many tokens each player has). It knows who the winner of the game is, and
 * when the game is over.
 */
public class Onitama {
    public static final int DIMENSION = 5; // This is a 5x5 game
    private OnitamaBoard board = new OnitamaBoard(Onitama.DIMENSION); // The main game board

    private final Player player1;
    private final Player player2;
    private Player whoseTurn; // player1 moves first!

    /**
     * Constructs a game of Onitama by creating 2 new players. Sets whoseTurn to
     * player1
     */
    public Onitama() {
        this.player1 = new Player(OnitamaBoard.G1);
        this.player2 = new Player(OnitamaBoard.G2);
        this.whoseTurn = this.player1;
    }

    /**
     * Constructs a game of Onitama with 2 players passed in as parameters Sets
     * whoseTurn to player1
     *
     * @param player1 the first player, G1
     * @param player2 the second player, G2
     */
    public Onitama(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.whoseTurn = this.player1;
    }

    /**
     * Constructs a game of Onitama by creating 2 new players. Sets whoseTurn to
     * player1 Sets the dimension of Onitama to the passed in dimension if valid.
     * The dimension must be odd and greater than or equal to 5.
     *
     * @param dimension the dimension of this Onitama
     */
    public Onitama(int dimension) {

        this.player1 = new Player(OnitamaBoard.G1);
        this.player2 = new Player(OnitamaBoard.G2);
        this.whoseTurn = this.player1;
        if (dimension >= 5 && dimension % 2 == 1) {
            this.board = new OnitamaBoard(dimension);
        }
    }

    /**
     * Constructs a game of Onitama with 2 players passed in as parameters Sets
     * whoseTurn to player1 Sets the dimension of Onitama to the passed in dimension
     * if valid. The dimension must be odd and greater than or equal to 5.
     *
     * @param player1   the first player, G1
     * @param player2   the second player, G2
     * @param dimension the dimension of this Onitama
     */
    public Onitama(Player player1, Player player2, int dimension) {
        this.player1 = player1;
        this.player2 = player2;
        this.whoseTurn = this.player1;
        if (dimension >= 5 && dimension % 2 == 1) {
            this.board = new OnitamaBoard(dimension);
        }
    }

    /**
     * Returns the Player for player1, player2 depending on who moves next.
     *
     * @return the Player for player1, player2
     */
    public Player getWhoseTurn() {
        return this.whoseTurn;
    }

    /**
     * Given one player, returns the other player. If the given player is invalid,
     * returns null.
     *
     * @param player Player object for a player - should be either player1 or
     *               player2
     * @return player1 or player2, the opposite of the given player, or null if the
     *         given player object was invalid
     */
    public Player otherPlayer(Player player) {
        if (player == player1) {
            return player2;
        } else if (player == player2) {
            return player1;
        } else {
            return null;
        }
    }

    /**
     * Checks if a move with the given parameters would be legal based on the origin
     * and destination coordinates. This method should specifically check for the
     * following 3 conditions: 1) The movement is in the bounds of this game's
     * board. 2) The correct piece is being moved based on the current player's
     * turn. 3) The destination is valid. A player CANNOT move on top of their own
     * piece.
     *
     * @param rowO integer representing the row origin
     * @param colO integer representing the column origin
     * @param rowD integer representing the row destination
     * @param colD integer representing the column destination
     * @return true if this is a legal move, false otherwise
     */
    public boolean isLegalMove(int rowO, int colO, int rowD, int colD) {

        if (this.board.legal(rowO, colO)) {
            char piece = this.board.getToken(rowO, colO);
            if (this.board.legal(rowD, colD)) {
                char piece2 = this.board.getToken(rowD, colD);
                if (piece == OnitamaBoard.G1 || piece == OnitamaBoard.M1) {
                    return piece2 == OnitamaBoard.G2 || piece2 == OnitamaBoard.M2 || piece2 == OnitamaBoard.EMPTY;
                } else if (piece == OnitamaBoard.G2 || piece == OnitamaBoard.M2) {
                    return piece2 == OnitamaBoard.G1 || piece2 == OnitamaBoard.M1 || piece2 == OnitamaBoard.EMPTY;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to make a move for player1 or player2 (depending on whose turn it
     * is) from position rowO, colO to position rowD, colD. Returns true if the move
     * was successfully made.
     *
     * @param rowO      integer representing the row origin
     * @param colO      integer representing the column origin
     * @param rowD      integer representing the row destination
     * @param colD      integer representing the column destination
     * @param styleName string representing the name of the movement style
     * @return true if the move was successfully made, false otherwise
     */
    public boolean move(int rowO, int colO, int rowD, int colD, String styleName) {
        // Hint: Use existing methods (from main.Onitama and main.OnitamaBoard) as
        // helpers here
        // This method should also modify whoseTurn it is to be the next player
        if (isLegalMove(rowO, colO, rowD, colD)) {
            char start = this.board.getToken(rowO, colO);
            char owner = 'a';
            char name = this.whoseTurn.getPlayer();
            Style[] styles = this.board.getStyles();
            int[][] temp = {{1, 2}};
            Style selected = new Style(temp, "temp");

            for (Style style : styles) {
                if (style.getName().equals(styleName)) {
                    owner = style.getOwner();
                    selected = style;
                }
            }

            int row_change = rowD - rowO;
            int col_change = colD - colO;
            if (name == OnitamaBoard.G1){
                row_change = rowO - rowD;
                col_change = colO - colD;
            }

            boolean legal = false;
            for (Move move: selected.getMoves()) {
                if (move.getRow() == row_change && move.getCol() == col_change) {
                    legal = true;
                    break;
                }
            }
            if (!legal) {
                return false;
            }

            if (owner == name) {
                this.board.exchangeStyle(selected);

                this.board.setToken(rowD, colD, start);
                this.board.setToken(rowO, colO, OnitamaBoard.EMPTY);

                this.whoseTurn = otherPlayer(this.whoseTurn);

                return true;
            }
        }
        return false;
    }

    /**
     * Returns the winner of the game if the game is over, or the board token for
     * EMPTY if the game is not yet finished. As per main.Onitama's rules, the
     * winner of the game is the player whose Grandmaster reaches the middle column
     * on the opposite row from the start position, OR the player who captures the
     * other player's Grandmaster.
     *
     * @return the character of the winning player's Grandmaster (G1 or G2) or the
     *         token for EMPTY if the game is not finished.
     */
    public char getWinner() {
        // Hint: Use existing methods as helpers here
        // Use main.OnitamaBoard.G1, main.OnitamaBoard.G2, etc. to access the board
        // tokens

        int mid = this.board.getDimension() / 2;
        if (this.board.getToken(0, mid) == OnitamaBoard.G2) {
            return OnitamaBoard.G2;
        } else if (this.board.getToken(this.board.getDimension() - 1, mid) == OnitamaBoard.G1) {
            return OnitamaBoard.G1;
        }

        boolean check1 = false;
        boolean check2 = false;

        for (int i = 0; i < this.board.getDimension(); i++) {
            for (int j = 0; j < this.board.getDimension(); j++) {
                if (this.board.getToken(i, j) == OnitamaBoard.G1) {
                    check1 = true;
                } else if (this.board.getToken(i, j) == OnitamaBoard.G2) {
                    check2 = true;
                }
            }
        }

        if (check1 && !check2) {
            return OnitamaBoard.G1;
        } else if (!check1 && check2) {
            return OnitamaBoard.G2;
        }
        return OnitamaBoard.EMPTY;

    }

    /*
     * DO NOT CHANGE ANYTHING BELOW!!! Changing things below will mess up the Auto
     * tests and result in a MAJOR LOSS OF MARKS!!!
     */

    /**
     * DO NOT MODIFY THIS!!! Returns string representation of this board.
     *
     * @return a string representation of this board
     */
    // NOTE: This method is already done for you. DO NOT MODIFY THIS!!
    public String getBoardString() {
        return this.board.toString() + "\n";
    }

    /**
     * DO NOT MODIFY THIS!!! Returns string representation of a player's available
     * styles.
     *
     * @param player the grandmaster of the player whose styles are printed, or
     *               EMPTY for the fifth style
     * @return a string representation of the style
     */
    // NOTE: This method is already done for you. DO NOT MODIFY THIS!!
    public String getStylesString(char player) {
        String s = "";
        if (player == OnitamaBoard.EMPTY) {
            s += "Fifth style: \n";
        } else {
            s += "Player " + player + " styles: \n";
        }
        for (int i = 0; i < 5; i++) {
            if (this.board.getStyles()[i].getOwner() == player) {
                s += this.board.getStyles()[i].toString() + "\n";
            }
        }
        return s;
    }

    /**
     * DO NOT MODIFY THIS!!! Get the different styles of movement in main.Onitama
     *
     * @return an array of all styles of movement.
     */
    public Style[] getStyles() {
        return this.board.getStyles();
    }

    /**
     * DO NOT MODIFY THIS!!! Gets a copy of this OnitamaBoard from
     * OnitamaBoard.getBoard()
     *
     * @return a copy of the current game board.
     */
    public char[][] getBoard() {
        return this.board.getBoard();
    }

    /**
     * DO NOT MODIFY THIS!!! Construct a new OnitamaBoard with the given size and
     * preset board.
     * 
     * @param size  the dimension of the OnitamaBoard
     * @param board the preset board state of the OnitamaBoard
     */
    public void setBoard(int size, char[][] board) {
        this.board = new OnitamaBoard(size, board);
    }

    /**
     * Main function which creates and runs a random main.Onitama game.
     */
    // DO NOT MODIFY THIS!!
    // Run this to test the current class. We speedrun a game of Onitama in 4 moves.
    // The output should match EXACTLY to onitamaMainOutput.txt
    public static void main(String[] args) {
        // Speed run a game of Onitama where player 'O' wins
        Onitama o = new Onitama();
        int[][] moves = { { 0, 2, 1, 2 }, { 4, 2, 3, 3 }, { 1, 2, 2, 4 }, { 3, 3, 2, 4 } };
        String[] styleNames = { "crab", "rooster", "dragon", "mantis" };
        int rowO, colO, rowD, colD;
        String styleName;

        for (int i = 0; i < moves.length; i++) {
            System.out.println(o.getBoardString() + o.getWhoseTurn().getPlayer() + " moves next");
            rowO = moves[i][0];
            colO = moves[i][1];
            rowD = moves[i][2];
            colD = moves[i][3];
            styleName = styleNames[i];
            o.move(rowO, colO, rowD, colD, styleName);
            // Print the move made, board, who's turn it is, and player styles
            System.out.println("makes move " + styleName + ": (" + rowO + ", " + colO + ", " + rowD + ", " + colD + ")");
            System.out.println(o.getBoardString() + o.getWhoseTurn().getPlayer() + " moves next");
            System.out.println(o.getStylesString(OnitamaBoard.EMPTY));
            System.out.println(o.getStylesString(OnitamaBoard.G1));
            System.out.println(o.getStylesString(OnitamaBoard.G2));
        }

        // Print final board state and who won
        System.out.println(o.getBoardString());
        System.out.println("==============================\nGame Finished: " + o.getWinner() + " won the game!");
    }
}