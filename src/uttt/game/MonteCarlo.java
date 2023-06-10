package uttt.game;

import java.util.Random;

import uttt.utils.Move;
import uttt.utils.Symbol;

public class MonteCarlo {
    double w, s;
    Symbol symbol;
    MonteCarlo[][] nex;

    public static Random rnd = new Random();

    MonteCarlo(Symbol player) {
        w = s = 1.0;
        symbol = player;
        nex = new MonteCarlo[9][9];
    }

    public static Symbol playout(BoardInterface[] b, int nextboard, Symbol curs) {
        if (Minimax.isGameOver(b))
            return Minimax.getWinner(b);
        int i, j;
        int[] posb = new int[81];
        int[] posm = new int[81];
        int cnt = 0;
        for (i = 0; i < 9; i++)
            if (i == nextboard || nextboard == -1)
                for (j = 0; j < 9; j++)
                    if (b[i].isMovePossible(j)) {
                        posb[cnt] = i;
                        posm[cnt] = j;
                        cnt++;
                    }
        int selectedmove = rnd.nextInt(cnt);
        i = posb[selectedmove];
        j = posm[selectedmove];
        b[i].getMarks()[j].setSymbol(curs);
        int nexb = j;
        if (b[j].isClosed())
            nexb = -1;
        Symbol ans = playout(b, nexb, curs.flip());
        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
        return ans;
    }

    // returns symbol of the player that won the playout
    public static Symbol findChildAndPlay(MonteCarlo node, BoardInterface[] b, int nextboard, Symbol wins) {
        // find best child
        double maxi = -2e9;
        int i, j, oki = -1, okj = -1;
        for (i = 0; i < 9; i++)
            for (j = 0; j < 9; j++)
                if (node.nex[i][j] != null) {
                    double reward;
                    if (node.symbol == wins)
                        reward = 1.0 - node.nex[i][j].w / node.nex[i][j].s;
                    else
                        reward = node.nex[i][j].w / node.nex[i][j].s;
                    if (reward + Math.sqrt((double) 2)
                            * Math.sqrt(Math.log(node.s) / node.nex[i][j].s) > maxi) {
                        maxi = reward
                                + Math.sqrt((double) 2) * Math.sqrt(Math.log(node.s) / node.nex[i][j].s);
                        oki = i;
                        okj = j;
                    }
                }
        if (oki != -1) { // if it has a child
            i = oki;
            j = okj;
            b[i].getMarks()[j].setSymbol(node.symbol);
            int nexb = j;
            if (b[j].isClosed())
                nexb = -1;
            Symbol ans = findChildAndPlay(node.nex[i][j], b, nexb, wins);
            b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
            node.s += 1.0;
            if (node.symbol == ans)
                node.w += 1.0;
            if (node.symbol == wins.flip() && ans == Symbol.EMPTY)
                node.w += 1.0;
            return ans;
        }
        // otherwise find all legal moves and play on one of them
        int[] posb = new int[81];
        int[] posm = new int[81];
        int cnt = 0;
        for (i = 0; i < 9; i++)
            if (nextboard == -1 || nextboard == i)
                for (j = 0; j < 9; j++)
                    if (b[i].isMovePossible(j)) {
                        node.nex[i][j] = new MonteCarlo(node.symbol.flip());
                        posb[cnt] = i;
                        posm[cnt] = j;
                        cnt++;
                    }
        if (cnt == 0) { // no legal moves
            node.s += 1.0;
            if (node.symbol == Minimax.getWinner(b))
                node.w += 1.0;
            if (node.symbol == wins.flip() && Minimax.getWinner(b) == Symbol.EMPTY)
                node.w += 1.0;
            return Minimax.getWinner(b);
        }
        int selectedmove = rnd.nextInt(cnt);
        i = posb[selectedmove];
        j = posm[selectedmove];
        b[i].getMarks()[j].setSymbol(node.symbol);
        int nexb = j;
        if (b[j].isClosed())
            nexb = -1;
        // run playout on selected node
        Symbol ans = playout(b, nexb, node.symbol.flip());
        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
        node.s += 1.0;
        if (node.symbol == ans)
            node.w += 1.0;
        if (node.symbol == wins.flip() && ans == Symbol.EMPTY)
            node.w += 1.0;
        return ans;

    }

    public static Move iterate(MonteCarlo node, long curtime, BoardInterface[] b, int nextboard, Symbol wins) {
        while (true) {
            if (System.currentTimeMillis() - curtime > (long) 2600)
                break;
            findChildAndPlay(node, b, nextboard, wins);
        }
        int i, j, soli = -1, solj = -1;
        double mini = 2e9;
        for (i = 0; i < 9; i++)
            for (j = 0; j < 9; j++)
                if (node.nex[i][j] != null) {
                    if (node.nex[i][j].w / node.nex[i][j].s < mini) {
                        mini = node.nex[i][j].w / node.nex[i][j].s;
                        soli = i;
                        solj = j;
                    }
                }
        return new Move(soli, solj);
    }
}
