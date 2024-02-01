package main;

public class OnitamaControllerHumanVSHuman {

    protected Onitama onitama;
    PlayerHuman player1, player2;

    /**
     * Constructs a new game for Onitama to be played between two players.
     * Creates two new main.PlayerHuman instances and one main.Onitama game.
     */
    public OnitamaControllerHumanVSHuman() {
        this.player1 = new PlayerHuman(OnitamaBoard.G1);
        this.player2 = new PlayerHuman(OnitamaBoard.G2);
        this.onitama = new Onitama(player1, player2);
    }

    /**
     * Start's the game between the two players.
     */
    public void play() {
        while (onitama.getWinner() == OnitamaBoard.EMPTY) {
            this.report();

            Turn turn = null;
            Player whosTurn = onitama.getWhoseTurn();
            turn = whosTurn.getTurn();

            this.reportTurn(whosTurn.getPlayer(), turn);
            onitama.move(turn.getRowO(), turn.getColO(), turn.getRowD(),
                    turn.getColD(), turn.getStyle());
        }
        this.reportFinal();
    }

    /**
     * Prints the last move made in this game.
     */
    private void reportTurn(char whosTurn, Turn turn) {
        System.out.println(whosTurn + " makes move " + turn + "\n");
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

        OnitamaControllerHumanVSHuman oc = new OnitamaControllerHumanVSHuman();
        oc.play();
    }

}