package uttt.game;

import uttt.utils.Move;
import uttt.utils.Symbol;

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
        if (ui == null)
            throw new IllegalArgumentException("null argument");
        return ui.getUserMove();
    }

    public int getPlayerMove(BoardInterface game, UserInterface ui) {
        if (ui == null)
            throw new IllegalArgumentException("null argument");
        return ui.getUserMove().getMarkIndex();
    }
}
