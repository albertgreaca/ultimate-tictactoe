package uttt.game;

import uttt.utils.Move;
import uttt.utils.Symbol;
import uttt.game.Precalculated;

public class Player implements PlayerInterface {
    Symbol s;

    public Player() {
        s = Symbol.EMPTY;
    }

    public Player(Symbol s) {
        this.s = s;
    }

    public Symbol getSymbol() {
        return s;
    }

    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) {
        if (ui != null)
            return ui.getUserMove();
        int[] aux = Minimax.minimizer(game.getBoards(), 0, getSymbol(), game.getIndexNextBoard(), -10, 20);
        return new Move(aux[1], aux[2]);
    }

    public int getPlayerMove(BoardInterface game, UserInterface ui) {
        if (game == null)
            throw new IllegalArgumentException("null argument");
        if (Precalculated.ok == 0)
            Precalculated.setandrun();
        MarkInterface[] aux = game.getMarks();
        int i, nr = 0, p3 = 1;
        for (i = 0; i < 9; i++) {
            if (aux[i].getSymbol() == Symbol.CROSS)
                nr = nr + p3;
            if (aux[i].getSymbol() == Symbol.CIRCLE)
                nr = nr + 2 * p3;
            p3 = 3 * p3;
        }
        if (getSymbol() == Symbol.CROSS)
            return Precalculated.nexminx[nr];
        else
            return Precalculated.nexmino[nr];
    }
}
