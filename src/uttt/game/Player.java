package uttt.game;

import uttt.utils.Move;
import uttt.utils.Symbol;

public class Player implements PlayerInterface {
    Symbol s;

    public Player(Symbol s) {
        if (s == null || s == Symbol.EMPTY)
            throw new IllegalArgumentException("wrong argument");
        this.s = s;
    }

    public Symbol getSymbol() {
        return s;
    }

    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) {
        if (ui == null)
            throw new IllegalArgumentException("null argument");
        return ui.getUserMove();
    }

    public int getPlayerMove(BoardInterface game, UserInterface ui) {
        return ui.getUserMove().getMarkIndex();
    }
}
