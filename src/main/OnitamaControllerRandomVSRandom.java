package main;

public class OnitamaControllerRandomVSRandom {

    protected Onitama onitama;
    PlayerRandom player1, player2;

    /**
     * Constructs a new game for Onitama to be played between two players.
     * Creates two new main.PlayerRandom instances and one main.Onitama game.
     */
    public OnitamaControllerRandomVSRandom() {
        this.player1 = new PlayerRandom(OnitamaBoard.G1);
        this.player2 = new PlayerRandom(OnitamaBoard.G2);
        this.onitama = new Onitama(player1, player2);
    }

    /**
     * Start's the game between the two players.
     */
    public void play() {
        while (onitama.getWinner() == OnitamaBoard.EMPTY) {
            this.report();

            Turn turn = player1.getTurn(onitama);
            Player whoseTurn = onitama.getWhoseTurn();
            if (whoseTurn == player2) {
                turn = player2.getTurn(onitama);
            }

            this.reportTurn(whoseTurn.getPlayer(), turn);
            onitama.move(turn.getRowO(), turn.getColO(), turn.getRowD(),
                    turn.getColD(), turn.getStyle());
        }
        this.reportFinal();
    }

    /**
     * Prints the last move made in this game.
     */
    private void reportTurn(char whoseTurn, Turn turn) {
        System.out.println(whoseTurn + " makes move " + turn + "\n");
    }

    /**
     * Print the report of the ongoing game.
     */
    private void report() {

        String s = onitama.getBoardString() + onitama.getStylesString(OnitamaBoard.G1) +
                onitama.getStylesString(OnitamaBoard.G2) +
                onitama.getStylesString(OnitamaBoard.EMPTY)
                + "  " + onitama.getWhoseTurn().getPlayer() + " moves next";
        System.out.println(s);
    }

    /**
     * Print the final score (who won).
     */
    private void reportFinal() {

        String s = onitama.getBoardString() + "  "
                + onitama.getWinner() + " won\n";
        System.out.println(s);
    }

    public static void main(String[] args) {

        OnitamaControllerRandomVSRandom oc = new OnitamaControllerRandomVSRandom();
        oc.play();
    }

}
