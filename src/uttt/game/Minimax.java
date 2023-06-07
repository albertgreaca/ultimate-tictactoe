package uttt.game;

import uttt.game.MarkInterface;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.utils.Symbol;

public class Minimax {
    public static boolean isGameOver(BoardInterface[] b) {
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

    public static Symbol getWinner(BoardInterface[] b) {
        if (isGameOver(b) == false)
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

    public static int[] minimizer(BoardInterface[] b, int depth, Symbol s, int nextboard, int alpha, int beta) {
        if (getWinner(b) == s) {
            int[] ans = { depth, -1, -1 };
            return ans;
        }
        if (isGameOver(b) == true) {
            int[] ans = { 4, -1, -1 };
            return ans;
        }
        if (depth == 3) {
            int[] ans = { 4, -1, -1 };
            return ans;
        }
        int mini = 4, okb = -1, okm = -1, i, j;
        for (i = 0; i < 9; i++) {
            if (nextboard == -1 || nextboard == i)
                for (j = 0; j < 9; j++) {
                    if (b[i].isMovePossible(j)) {
                        b[i].getMarks()[j].setSymbol(s);
                        int nexb = j;
                        if (b[j].isClosed())
                            nexb = -1;
                        int aux = maximizer(b, depth + 1, s.flip(), nexb, alpha, beta);
                        if (aux < mini) {
                            mini = aux;
                            okb = i;
                            okm = j;
                        }
                        beta = Math.min(beta, aux);
                        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
                    }
                    if (beta <= alpha)
                        break;
                }
            if (beta <= alpha)
                break;
        }
        int[] ans = { mini, okb, okm };
        return ans;
    }

    public static int maximizer(BoardInterface[] b, int depth, Symbol s, int nextboard, int alpha, int beta) {
        if (getWinner(b) == s.flip())
            return depth;
        if (isGameOver(b) == true)
            return 4;
        if (depth == 3)
            return 4;
        int maxi = -1, i, j;
        for (i = 0; i < 9; i++) {
            if (nextboard == -1 || nextboard == i)
                for (j = 0; j < 9; j++) {
                    if (b[i].isMovePossible(j)) {
                        b[i].getMarks()[j].setSymbol(s);
                        int nexb = j;
                        if (b[j].isClosed())
                            nexb = -1;
                        int[] aux = minimizer(b, depth, s.flip(), nexb, alpha, beta);
                        maxi = Math.max(maxi, aux[0]);
                        alpha = Math.max(alpha, aux[0]);
                        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
                    }
                    if (beta <= alpha)
                        break;
                }
            if (beta <= alpha)
                break;
        }
        return maxi;
    }
}
