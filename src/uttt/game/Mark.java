package uttt.game;

import uttt.utils.Symbol;

public class Mark implements MarkInterface {
    Symbol s;
    int p;

    public Mark() {
        s = Symbol.EMPTY;
        p = 0;
    }

    public Mark(Symbol s, int p) {
        this.s = s;
        this.p = p;
    }

    public Symbol getSymbol() {
        return s;
    }

    public int getPosition() {
        return p;
    }

    public void setSymbol(Symbol symbol) {
        if (symbol == Symbol.EMPTY)
            throw new IllegalArgumentException("wrong symbol");
        s = symbol;
    }
}
