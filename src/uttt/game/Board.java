package uttt.game;

import uttt.utils.Symbol;
import uttt.UTTTFactory;

public class Board implements BoardInterface {
    MarkInterface[] m;

    public Board() {
        m = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            m[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
    }

    public MarkInterface[] getMarks() {
        return m;
    }

    public void setMarks(MarkInterface[] marks) {
        if (marks.length != 9)
            throw new IllegalArgumentException("wrong length");
        m = marks;
    }

    public boolean setMarkAt(Symbol symbol, int markIndex) {
        if (symbol == Symbol.EMPTY)
            throw new IllegalArgumentException("wrong symbol");
        if (isMovePossible(markIndex) == false)
            return false;
        m[markIndex].setSymbol(symbol);
        return true;
    }

    public boolean isClosed() {
        // lines
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[1].getSymbol()
                && m[1].getSymbol() == m[2].getSymbol())
            return true;
        if (m[3].getSymbol() != Symbol.EMPTY && m[3].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[5].getSymbol())
            return true;
        if (m[6].getSymbol() != Symbol.EMPTY && m[6].getSymbol() == m[7].getSymbol()
                && m[7].getSymbol() == m[8].getSymbol())
            return true;
        // columns
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[3].getSymbol()
                && m[3].getSymbol() == m[6].getSymbol())
            return true;
        if (m[1].getSymbol() != Symbol.EMPTY && m[1].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[7].getSymbol())
            return true;
        if (m[2].getSymbol() != Symbol.EMPTY && m[2].getSymbol() == m[5].getSymbol()
                && m[5].getSymbol() == m[8].getSymbol())
            return true;
        // diagonals
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[8].getSymbol())
            return true;
        if (m[2].getSymbol() != Symbol.EMPTY && m[2].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[6].getSymbol())
            return true;
        // full
        boolean validmove = false;
        int i;
        for (i = 0; i < 9; i++)
            if (m[i].getSymbol() == Symbol.EMPTY)
                validmove = true;
        if (validmove == true)
            return false;
        return true;
    }

    public boolean isMovePossible(int markIndex) {
        if (markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException("wrong index");
        if (isClosed() == true)
            return false;
        if (m[markIndex].getSymbol() != Symbol.EMPTY)
            return false;
        return true;
    }

    public Symbol getWinner() {
        if (isClosed() == false)
            return Symbol.EMPTY;
        // lines
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[1].getSymbol()
                && m[1].getSymbol() == m[2].getSymbol())
            return m[0].getSymbol();
        if (m[3].getSymbol() != Symbol.EMPTY && m[3].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[5].getSymbol())
            return m[3].getSymbol();
        if (m[6].getSymbol() != Symbol.EMPTY && m[6].getSymbol() == m[7].getSymbol()
                && m[7].getSymbol() == m[8].getSymbol())
            return m[6].getSymbol();
        // columns
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[3].getSymbol()
                && m[3].getSymbol() == m[6].getSymbol())
            return m[0].getSymbol();
        if (m[1].getSymbol() != Symbol.EMPTY && m[1].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[7].getSymbol())
            return m[1].getSymbol();
        if (m[2].getSymbol() != Symbol.EMPTY && m[2].getSymbol() == m[5].getSymbol()
                && m[5].getSymbol() == m[8].getSymbol())
            return m[2].getSymbol();
        // diagonals
        if (m[0].getSymbol() != Symbol.EMPTY && m[0].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[8].getSymbol())
            return m[0].getSymbol();
        if (m[2].getSymbol() != Symbol.EMPTY && m[2].getSymbol() == m[4].getSymbol()
                && m[4].getSymbol() == m[6].getSymbol())
            return m[2].getSymbol();
        return Symbol.EMPTY;
    }
}
