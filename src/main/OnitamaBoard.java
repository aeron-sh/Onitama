package main;

public class OnitamaBoard {
    public static final char EMPTY = ' ', M1 = 'x', G1 = 'X', M2 = 'o', G2 = 'O';
    private int size = 5;
    private char[][] board;
    private Style[] styles;

    /**
     * Constructs an empty main.Onitama board. Places four monks and one grandmaster on
     * opposite sides of the board. Creates five Styles and distributes them among
     * the players.
     *
     * @param size this board's width and height
     */
    public OnitamaBoard(int size) {

        this.board = new char[size][size];

        if (size % 2 == 1 && size >= 5) {
            this.size = size;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    setToken(i, j, EMPTY);
                }
            }

            for (int c = 0; c < size; c++) {
                setToken(0, c, M1);
                setToken(size - 1, c, M2);
            }

            int mid = (size - 1) / 2;
            setToken(0, mid, G1);
            setToken(size - 1, mid, G2);
        }

        constructStyles();

    }

    /**
     * Constructs a preset main.Onitama board from the provided input board.
     * Creates five styles and distributes it amongst the players.
     *
     * @param size this boards width and height
     * @param board the preset board to copy over PRE: board is a size x size array.
     */
    public OnitamaBoard(int size, char[][] board){
        this.board = board;

        this.size = size;

        for (int i = 0; i < size; i++) {
            setToken(0, i, M1);
            setToken(size - 1, i, M2);
        }

        int mid = size / 2;
        setToken(0, mid, G1);
        setToken(size - 1, mid, G2);

    }

    /**
     * Constructs the 5 movement styles of main.Onitama for this board.
     * Normally, there are 16 movement styles and they are distributed randomly, however for this assignment,
     * you are only required to use 5 of them (Dragon, Crab, Horse, Mantis, and Rooster).
     *
     * You can find the movement patterns for these styles under assets/{style}.png, where {style} is one of
     * the five styles mentioned above. Additionally, you can also find the images in README.md.
     *
     * IMPORTANT:
     * Additionally, we are going to distribute the styles at the start of the game in a static or consistent manner.
     *      Player 1 (G1) must get the Crab and Horse styles.
     *      Player 2 (G2) must get the Mantis and Rooster styles.
     *      Extra (EMPTY) must get the Dragon style.
     *
     * Please be sure to follow the distribution of styles as mentioned above as this is important for testing.
     * Failure to follow this distribution of styles will result in the LOSS OF A LOT OF MARKS.
     */
    public void constructStyles(){

        int[][] moveCrab = {{-1, 0}, {0, -2}, {0, 2}};
        int[][] moveHorse = {{-1, 0}, {1, 0}, {0, -1}};
        int[][] moveMantis = {{-1, 1}, {-1, -1}, {1, 0}};
        int[][] moveRooster = {{1, -1}, {-1, 1}, {0, -1}, {0, 1}};
        int[][] moveDragon = {{-1, -2}, {-1, 2}, {1, 1}, {1, -1}};

        Style Crab = new Style(moveCrab, "crab");
        Style Horse = new Style(moveHorse, "horse");
        Style Mantis = new Style(moveMantis, "mantis");
        Style Rooster = new Style(moveRooster, "rooster");
        Style Dragon = new Style(moveDragon, "dragon");

        Crab.setOwner(G1);
        Horse.setOwner(G1);
        Mantis.setOwner(G2);
        Rooster.setOwner(G2);
        Dragon.setOwner(EMPTY);

        this.styles = new Style[]{Crab, Horse, Mantis, Rooster, Dragon};

    }

    /**
     * Returns the dimensions of this board.
     *
     * @return this board's width/height
     */
    public int getDimension() {
        return this.size; // placeholder
    }

    /**
     * Returns the styles of this board.
     *
     * @return this board's styles
     */
    public Style[] getStyles() {
        return this.styles;
    }

    /**
     * Returns the player token that is in the given position, or the
     * empty character if no player token is there or if the position provided is
     * invalid.
     *
     * @param row integer representing a row on this board
     * @param col integer representing a column on this board
     * @return character representing M1,G1,M2,G2 or EMPTY
     */
    public char getToken(int row, int col) {
        if (this.board[row][col] == M1) {
            return M1;
        }
        else if (this.board[row][col] == G1) {
            return G1;
        }
        else if (this.board[row][col] == M2) {
            return M2;
        }
        else if (this.board[row][col] == G2) {
            return G2;
        }
        return EMPTY;
    }

    /**
     * Sets the given position on the board to be the given player (or throne/empty)
     * token.
     *
     * @param row   integer representing a row on this board
     * @param col   integer representing a column on this board
     * @param token character for M1, M2, G1, G2, or EMPTY
     */
    public void setToken(int row, int col, char token) {
        this.board[row][col] = token;

    }

    /**
     * Returns true iff the provided coordinates are valid (exists on the board).
     *
     * @param row integer representing a row on this board
     * @param col integer representing a column on this board
     * @return whether (row, col) is a position on the board. Example: (6,12) is not
     *         a valid position on an 8x8 board.
     */
    private boolean validCoordinate(int row, int col) {
        return row >= 0 && row < this.size && col >= 0 && col < this.size;
    }

    /**
     * Allows access to validCoordinate outside of this class.
     *
     * @param row integer representing a row on this board
     * @param col integer representing a column on this board
     * @return output for validCoordinate()
     */
    public boolean legal(int row, int col) {
        return validCoordinate(row, col);
    }

    /**
     * Exchange the given style with the empty style (the style whose owner is EMPTY).
     * Hint: Exchanging will involve swapping the owners of the style.
     *
     * @param style the movement style to be exchanged.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean exchangeStyle(Style style){
        boolean first = false;
        boolean second = false;
        char owner = style.getOwner();
        for (Style value : getStyles()) {
            if (value.getOwner() == EMPTY) {
                value.setOwner(owner);
                first = true;
            } else if (value == style) {
                value.setOwner(EMPTY);
                second = true;
            }
        }
        return first && second;
    }

    /*
        DO NOT CHANGE ANYTHING BELOW!!!
        Changing things below will mess up the Auto tests and result in a MAJOR LOSS OF MARKS!!!
     */

    /**
     * DO NOT MODIFY THIS!!!
     * Creates and returns a deep copy of this OnitamaBoard's current state.
     *
     * @return a deep copy of the current board.
     */
    public char[][] getBoard(){
        char [][] boardCopy = new char[this.size][this.size];
        for (int i = 0; i < this.size; i++){
            System.arraycopy(this.board[i], 0, boardCopy[i], 0, this.size);
        }
        return boardCopy;
    }

    /**
     * Returns a string representation of this game board.
     *
     * @return a string representation of this, just the play area, with no
     *         additional information.
     */
    // DO NOT MODIFY THIS!! DOING SO MAY CAUSE OUR AUTOTESTS TO FAIL
    // WHICH WOULD LEAD TO A SIGNIFICANT LOSS OF CORRECTNESS MARKS
    public String toString() {
        /**
         * See assignment web page for sample output.
         */
        String s = "";
        s += "  ";
        for (int col = 0; col < this.size; col++) {
            s += col + " ";
        }
        s += '\n';

        s += " +";
        for (int col = 0; col < this.size; col++) {
            s += "-+";
        }
        s += '\n';

        for (int row = 0; row < this.size; row++) {
            s += row + "|";
            for (int col = 0; col < this.size; col++) {
                s += this.board[row][col] + "|";
            }
            s += row + "\n";

            s += " +";
            for (int col = 0; col < this.size; col++) {
                s += "-+";
            }
            s += '\n';
        }
        s += "  ";
        for (int col = 0; col < this.size; col++) {
            s += col + " ";
        }
        s += '\n';
        return s;
    }

}
