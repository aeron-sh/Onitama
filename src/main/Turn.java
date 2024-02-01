package main;

/**
 * A class to represent a potential turn (piece selection and movement).
 */

public class Turn {
    private int rowO, colO, rowD, colD;
    private String styleName;
    private char player;


    /**
     * Constructs a move (piece placement) that knows its row, col
     * movement from origin to destination on the grid
     *
     * @param row_o	integer representing the origin row of the piece to move
     * @param col_o	integer representing the origin column of the piece to move
     * @param row_d	integer representing the destination row of the piece to move
     * @param col_d	integer representing the destination column of the piece to move
     * @param styleName string representing the style being used to move
     * @param player the character representing the player making this Turn
     */
    public Turn(int row_o, int col_o, int row_d, int col_d, String styleName, char player) {
        this.rowO = row_o;
        this.colO = col_o;
        this.rowD = row_d;
        this.colD = col_d;
        this.styleName = styleName;
        this.player = player;
    }

    /**
     * Returns the start row of this turn.
     *
     * @return this turn's rowO
     */
    public int getRowO() {
        return this.rowO;
    }

    /**
     * Returns the start column of this turn.
     *
     * @return this turn's colO
     */
    public int getColO() {
        return this.colO;
    }

    /**
     * Returns the end row of this turn.
     *
     * @return this turn's rowD
     */
    public int getRowD() {
        return this.rowD;
    }

    /**
     * Returns the end column of this turn.
     *
     * @return this turn's colD
     */
    public int getColD() {
        return this.colD;
    }

    /**
     * Returns the style used this turn.
     *
     * @return this turn's styleName
     */
    public String getStyle() {
        return this.styleName;
    }

    /**
     * Returns a string representation of this turn.
     *
     * @return a string representation of this turn.
     */
    public String toString() {
        String s = "";
        s += this.player + ": (" + this.rowO + "," + this.colO + "," + this.styleName + ") -> (";
        s += this.rowD + "," + this.colD + ")";
        return s;
    }

    public static void main(String[] args){
        // Create Turns
        Turn t1 = new Turn(0, 0, 1, 2, "dragon", 'X');
        Turn t2 = new Turn(3, 2, 2, 2, "crab", 'O');
        Turn t3 = new Turn(2, 2, 3, 1, "rooster", 'X');

        // Print Turns
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
    }
}
