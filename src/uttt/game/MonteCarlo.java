package uttt.game;

import java.util.Random;

import uttt.utils.Move;
import uttt.utils.Symbol;

public class MonteCarlo {
    double w, s;
    int expanded;
    Symbol symbol;
    MonteCarlo[][] nex;

    public static Random rnd = new Random();

    MonteCarlo(Symbol player) {
        w = s = 1.0;
        expanded = 0;
        symbol = player;
        nex = new MonteCarlo[9][9];
    }

    public static Symbol playout(MonteCarlo node, BoardInterface[] b, int nextboard, Symbol wins) {
        if (Minimax.isGameOver(b)) {
            node.s += 1.0;
            if (node.symbol == Minimax.getWinner(b))
                node.w += 1.0;
            else if (Minimax.getWinner(b) == Symbol.EMPTY && node.symbol == wins.flip())
                node.w += 1.0;
            return Minimax.getWinner(b);
        }
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
        b[i].getMarks()[j].setSymbol(node.symbol);
        int nexb = j;
        if (b[j].isClosed())
            nexb = -1;
        node.nex[i][j] = new MonteCarlo(node.symbol.flip());
        Symbol ans = playout(node.nex[i][j], b, nexb, wins);
        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
        node.s += 1.0;
        if (node.symbol == ans)
            node.w += 1.0;
        else if (ans == Symbol.EMPTY && node.symbol == wins.flip())
            node.w += 1.0;
        return ans;
    }

    // returns symbol of the player that won the playout
    public static Symbol findChildAndPlay(MonteCarlo node, BoardInterface[] b, int nextboard, Symbol wins) {
        // already expanded and has children
        if (node.expanded == 1) {
            double maxi = -2e9;
            int i, j, oki = -1, okj = -1;
            for (i = 0; i < 9; i++)
                for (j = 0; j < 9; j++)
                    if (node.nex[i][j] != null) {
                        double reward;
                        if (node.symbol == wins)
                            reward = 1 - node.nex[i][j].w / node.nex[i][j].s;
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
            else if (ans == Symbol.EMPTY && node.symbol == wins.flip())
                node.w += 1.0;
            return ans;
        }
        // determining all possible children and running playout on one of them

        // nothing to expand
        if (Minimax.isGameOver(b)) {
            node.s += 1.0;
            if (node.symbol == Minimax.getWinner(b))
                node.w += 1.0;
            else if (Minimax.getWinner(b) == Symbol.EMPTY && node.symbol == wins.flip())
                node.w += 1.0;
            return Minimax.getWinner(b);
        }

        // has available children
        int i, j;
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
        int selectedmove = rnd.nextInt(cnt);
        i = posb[selectedmove];
        j = posm[selectedmove];
        b[i].getMarks()[j].setSymbol(node.symbol);
        int nexb = j;
        if (b[j].isClosed())
            nexb = -1;
        // run playout on selected node
        Symbol ans = playout(node.nex[i][j], b, nexb, wins);
        b[i].getMarks()[j].setSymbol(Symbol.EMPTY);
        node.expanded = 1;
        node.s += 1.0;
        if (node.symbol == ans)
            node.w += 1.0;
        else if (ans == Symbol.EMPTY && node.symbol == wins.flip())
            node.w += 1.0;
        return ans;
    }

    public static Move iterate(MonteCarlo node, long curtime, BoardInterface[] b, int nextboard, Symbol wins) {
        while (true) {
            if (System.currentTimeMillis() - curtime > (long) 2000)
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
