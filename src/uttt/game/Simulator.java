package uttt.game;

import uttt.utils.Symbol;
import uttt.utils.Move;
import uttt.UTTTFactory;

public class Simulator implements SimulatorInterface {
    BoardInterface[] b;
    Symbol cps;
    int nextboard;

    public Simulator() {
        b = new BoardInterface[9];
        cps = Symbol.EMPTY;
        nextboard = -1;
        int i;
        for (i = 0; i < 9; i++)
            b[i] = UTTTFactory.createBoard();
    }

    public BoardInterface[] getBoards() {
        return b;
    }

    public void setBoards(BoardInterface[] boards) {
        if (boards.length != 9)
            throw new IllegalArgumentException("wrong length");
        b = boards;
    }

    public Symbol getCurrentPlayerSymbol() {
        return cps;
    }

    public void setCurrentPlayerSymbol(Symbol symbol) {
        if (symbol == Symbol.EMPTY)
            throw new IllegalArgumentException("wrong symbol");
        cps = symbol;
    }

    public boolean setMarkAt(Symbol symbol, int boardIndex, int markIndex) {
        if (symbol != cps || symbol == Symbol.EMPTY || boardIndex < 0 || boardIndex > 8 || markIndex < 0
                || markIndex > 8)
            throw new IllegalArgumentException("wrong argument");
        if ((boardIndex != nextboard && nextboard != -1))
            return false;
        boolean ok = b[boardIndex].setMarkAt(symbol, markIndex);
        if (ok == false)
            return false;
        if (b[markIndex].isClosed() == true)
            setIndexNextBoard(-1);
        else
            setIndexNextBoard(markIndex);
        return true;
    }

    public int getIndexNextBoard() {
        return nextboard;
    }

    public void setIndexNextBoard(int index) {
        if (index < -1 || index > 8)
            throw new IllegalArgumentException("wrong index");
        nextboard = index;
    }

    public boolean isGameOver() {
        Symbol[] aux = new Symbol[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = b[i].getWinner();
        // lines
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[1] && aux[1] == aux[2])
            return true;
        if (aux[3] != Symbol.EMPTY && aux[3] == aux[4] && aux[4] == aux[5])
            return true;
        if (aux[6] != Symbol.EMPTY && aux[6] == aux[7] && aux[7] == aux[8])
            return true;
        // columns
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[3] && aux[3] == aux[6])
            return true;
        if (aux[1] != Symbol.EMPTY && aux[1] == aux[4] && aux[4] == aux[7])
            return true;
        if (aux[2] != Symbol.EMPTY && aux[2] == aux[5] && aux[5] == aux[8])
            return true;
        // diagonals
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[4] && aux[4] == aux[8])
            return true;
        if (aux[2] != Symbol.EMPTY && aux[2] == aux[4] && aux[4] == aux[6])
            return true;
        // full
        boolean validboard = false;
        for (i = 0; i < 9; i++)
            if (b[i].isClosed() == false)
                validboard = true;
        if (validboard == true)
            return false;
        return true;
    }

    public boolean isMovePossible(int boardIndex) {
        if (boardIndex < 0 || boardIndex > 8)
            throw new IllegalArgumentException("wrong board");
        if (nextboard != -1 && nextboard != boardIndex)
            return false;
        if (b[boardIndex].isClosed() == true)
            return false;
        return true;
    }

    public boolean isMovePossible(int boardIndex, int markIndex) {
        if (isMovePossible(boardIndex) == false)
            return false;
        return b[boardIndex].isMovePossible(markIndex);
    }

    public Symbol getWinner() {
        if (isGameOver() == false)
            return Symbol.EMPTY;
        Symbol[] aux = new Symbol[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = b[i].getWinner();
        // lines
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[1] && aux[1] == aux[2])
            return aux[0];
        if (aux[3] != Symbol.EMPTY && aux[3] == aux[4] && aux[4] == aux[5])
            return aux[3];
        if (aux[6] != Symbol.EMPTY && aux[6] == aux[7] && aux[7] == aux[8])
            return aux[6];
        // columns
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[3] && aux[3] == aux[6])
            return aux[0];
        if (aux[1] != Symbol.EMPTY && aux[1] == aux[4] && aux[4] == aux[7])
            return aux[1];
        if (aux[2] != Symbol.EMPTY && aux[2] == aux[5] && aux[5] == aux[8])
            return aux[2];
        // diagonals
        if (aux[0] != Symbol.EMPTY && aux[0] == aux[4] && aux[4] == aux[8])
            return aux[0];
        if (aux[2] != Symbol.EMPTY && aux[2] == aux[4] && aux[4] == aux[6])
            return aux[2];
        return Symbol.EMPTY;
    }

    public void run(PlayerInterface playerOne, PlayerInterface playerTwo, UserInterface ui) {
        int currentguy = 1;
        if (playerOne.getSymbol() == Symbol.EMPTY)
            throw new IllegalArgumentException("needs symbol");
        if (playerTwo.getSymbol() == Symbol.EMPTY)
            throw new IllegalArgumentException("needs symbol");
        if (playerOne.getSymbol() == playerTwo.getSymbol())
            throw new IllegalArgumentException("need different symbols");
        this.setCurrentPlayerSymbol(playerOne.getSymbol());
        while (true) {
            Move m;
            PlayerInterface player, otherplayer;
            if (currentguy == 1) {
                player = playerOne;
                otherplayer = playerTwo;
            } else {
                player = playerTwo;
                otherplayer = playerOne;
            }
            m = player.getPlayerMove(this, ui);
            if (isMovePossible(m.getBoardIndex(), m.getMarkIndex()) == true) {
                setMarkAt(player.getSymbol(), m.getBoardIndex(), m.getMarkIndex());
                currentguy = 3 - currentguy;
                setCurrentPlayerSymbol(otherplayer.getSymbol());
            }
            ui.updateScreen(this);
            if (isGameOver() == true)
                ui.showGameOverScreen(getWinner());
        }
    }
}
