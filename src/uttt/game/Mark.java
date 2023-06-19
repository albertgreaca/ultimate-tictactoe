package uttt.game;

import uttt.utils.Symbol;

public class Mark implements MarkInterface {
    Symbol s;
    int p;

    public Mark(Symbol s, int p) {
        if (s == null)
            throw new IllegalArgumentException("null argument");
        if (p < 0 || p > 8)
            throw new IllegalArgumentException("wrong index");
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
        if (symbol == null)
            throw new IllegalArgumentException("null argument");
        s = symbol;
    }
}
