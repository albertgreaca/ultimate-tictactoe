package uttt.tests;

import static org.junit.Assert.*;

import javax.swing.border.EmptyBorder;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimulatorTest {

    SimulatorInterface s = UTTTFactory.createSimulator();

    public BoardInterface makexwin() {
        BoardInterface b2 = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        aux[0].setSymbol(Symbol.CROSS);
        aux[1].setSymbol(Symbol.CROSS);
        aux[2].setSymbol(Symbol.CROSS);
        b2.setMarks(aux);
        return b2;
    }

    public BoardInterface makeowin() {
        BoardInterface b2 = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        aux[0].setSymbol(Symbol.CIRCLE);
        aux[1].setSymbol(Symbol.CIRCLE);
        aux[2].setSymbol(Symbol.CIRCLE);
        b2.setMarks(aux);
        return b2;
    }

    BoardInterface[] makenew() {
        BoardInterface[] aux = new BoardInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createBoard();
        return aux;
    }

    public void checkindexes(int x, int y, int z) {
        BoardInterface[] aux = makenew();
        s.setBoards(aux);
        assertEquals(s.isGameOver(), false);
        assertEquals(s.getWinner(), Symbol.EMPTY);
        aux[x] = makexwin();
        aux[y] = makexwin();
        aux[z] = makexwin();
        s.setBoards(aux);
        assertEquals(s.isGameOver(), true);
        assertEquals(s.getWinner(), Symbol.CROSS);

        aux = makenew();
        s.setBoards(aux);
        assertEquals(s.isGameOver(), false);
        assertEquals(s.getWinner(), Symbol.EMPTY);
        aux[x] = makeowin();
        aux[y] = makeowin();
        aux[z] = makeowin();
        s.setBoards(aux);
        assertEquals(s.isGameOver(), true);
        assertEquals(s.getWinner(), Symbol.CIRCLE);
    }

    @Test
    public void Test() {
        // too long array
        BoardInterface[] test1 = new BoardInterface[10];
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(test1);
        });

        // too short array
        BoardInterface[] test2 = new BoardInterface[8];
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(test2);
        });

        // check getBoards and setBoards
        BoardInterface[] aux = new BoardInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createBoard();
        aux[5] = makexwin();
        aux[8] = makeowin();
        aux[0] = makexwin();
        s.setBoards(aux);
        assertArrayEquals(s.getBoards(), aux);

        // check getCurrentPlayerSymbol and setCurrentPlayerSymbol
        assertThrows(IllegalArgumentException.class, () -> {
            s.setCurrentPlayerSymbol(Symbol.EMPTY);
        });
        s.setCurrentPlayerSymbol(Symbol.CROSS);
        assertEquals(s.getCurrentPlayerSymbol(), Symbol.CROSS);
        s.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertEquals(s.getCurrentPlayerSymbol(), Symbol.CIRCLE);

        // check setMarkAt, setIndexNextBoard, getIndexNextBoard, isMovePossible and
        // isMovePossible
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CROSS, 1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.EMPTY, 1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CIRCLE, -1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CIRCLE, 9, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CIRCLE, 0, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CIRCLE, 0, 9);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setIndexNextBoard(-2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.setIndexNextBoard(9);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(9);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(-1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(9, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(0, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            s.isMovePossible(0, 9);
        });
        // checks that it can only write in nextboard
        for (i = 0; i < 9; i++) {
            s.setIndexNextBoard(i);
            assertEquals(s.getIndexNextBoard(), i);
            int j;
            for (j = 0; j < 9; j++)
                if (i != j) {
                    int k = j;
                    assertEquals(s.isMovePossible(k), false);
                    assertEquals(s.isMovePossible(k, 0), false);
                    assertEquals(s.setMarkAt(Symbol.CIRCLE, k, 0), false);
                }
        }
        // if nextboard is -1 check that it can write everywhere
        s.setBoards(makenew());
        for (i = 0; i < 9; i++) {
            s.setIndexNextBoard(-1);
            assertEquals(s.getIndexNextBoard(), -1);
            int k = i;
            assertEquals(s.isMovePossible(k), true);
            assertEquals(s.isMovePossible(k, 0), true);
            assertEquals(s.setMarkAt(Symbol.CIRCLE, k, 0), true);
        }
        // check if cell is already written
        s.setBoards(makenew());
        s.setIndexNextBoard(4);
        s.setMarkAt(Symbol.CIRCLE, 4, 2);
        assertEquals(s.getIndexNextBoard(), 2);
        assertEquals(s.getBoards()[4].getMarks()[2].getSymbol(), Symbol.CIRCLE);
        s.setIndexNextBoard(4);
        assertEquals(s.isMovePossible(4), true);
        assertEquals(s.isMovePossible(4, 2), false);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 4, 2), false);

        // check if board is won
        aux = makenew();
        aux[5] = makexwin();
        s.setBoards(aux);
        s.setIndexNextBoard(5);
        assertEquals(s.isMovePossible(5), false);
        assertEquals(s.isMovePossible(5, 4), false);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 5, 4), false);

        // check if board is tied
        BoardInterface b = UTTTFactory.createBoard();
        MarkInterface[] aux2 = new MarkInterface[9];
        for (i = 0; i < 9; i++)
            aux2[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        b.setMarks(aux2);
        b.setMarkAt(Symbol.CROSS, 1);
        b.setMarkAt(Symbol.CROSS, 5);
        b.setMarkAt(Symbol.CROSS, 6);
        b.setMarkAt(Symbol.CROSS, 8);
        b.setMarkAt(Symbol.CIRCLE, 0);
        b.setMarkAt(Symbol.CIRCLE, 2);
        b.setMarkAt(Symbol.CIRCLE, 3);
        b.setMarkAt(Symbol.CIRCLE, 4);
        b.setMarkAt(Symbol.CIRCLE, 7);
        aux = makenew();
        aux[5] = b;
        s.setBoards(aux);
        s.setIndexNextBoard(5);
        assertEquals(s.isMovePossible(5), false);
        assertEquals(s.isMovePossible(5, 4), false);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 5, 4), false);

        // check getWinner and isGameOver
        ////// lines
        // 0 1 2
        checkindexes(0, 1, 2);
        // 3 4 5
        checkindexes(3, 4, 5);
        // 6 7 8
        checkindexes(6, 7, 8);
        ////// columns
        // 0 3 6
        checkindexes(0, 3, 6);
        // 1 4 7
        checkindexes(1, 4, 7);
        // 2 5 8
        checkindexes(2, 5, 8);
        ////// diagonals
        // 0 4 8
        checkindexes(0, 4, 8);
        // 2 4 6
        checkindexes(2, 4, 6);
        aux = makenew();
        for (i = 0; i < 9; i++)
            aux[i] = b;
        s.setBoards(aux);
        assertEquals(s.isGameOver(), true);
        assertEquals(s.getWinner(), Symbol.EMPTY);
    }

}
