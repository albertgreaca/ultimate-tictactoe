package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimulatorTest {

    SimulatorInterface s = UTTTFactory.createSimulator();

    public BoardInterface make1null() {
        BoardInterface b = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            if (i != 5)
                aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        b.setMarks(aux);
        return b;
    }

    public BoardInterface makexwin() {
        BoardInterface b = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        aux[0].setSymbol(Symbol.CROSS);
        aux[1].setSymbol(Symbol.CROSS);
        aux[2].setSymbol(Symbol.CROSS);
        b.setMarks(aux);
        return b;
    }

    public BoardInterface makeowin() {
        BoardInterface b = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        aux[0].setSymbol(Symbol.CIRCLE);
        aux[1].setSymbol(Symbol.CIRCLE);
        aux[2].setSymbol(Symbol.CIRCLE);
        b.setMarks(aux);
        return b;
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
        assertEquals(s.isMovePossible(8 - x), false);
        assertEquals(s.isMovePossible(8 - y), false);
        assertEquals(s.isMovePossible(8 - z), false);
        assertEquals(s.isMovePossible(8 - x, 0), false);
        assertEquals(s.isMovePossible(8 - y, 0), false);
        assertEquals(s.isMovePossible(8 - z, 0), false);
        s.setCurrentPlayerSymbol(Symbol.CROSS);
        assertEquals(s.setMarkAt(Symbol.CROSS, 8 - x, 0), false);
        assertEquals(s.setMarkAt(Symbol.CROSS, 8 - y, 0), false);
        assertEquals(s.setMarkAt(Symbol.CROSS, 8 - z, 0), false);
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
        assertEquals(s.isMovePossible(8 - x), false);
        assertEquals(s.isMovePossible(8 - y), false);
        assertEquals(s.isMovePossible(8 - z), false);
        assertEquals(s.isMovePossible(8 - x, 0), false);
        assertEquals(s.isMovePossible(8 - y, 0), false);
        assertEquals(s.isMovePossible(8 - z, 0), false);
        s.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 8 - x, 0), false);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 8 - y, 0), false);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 8 - z, 0), false);
        assertEquals(s.isGameOver(), true);
        assertEquals(s.getWinner(), Symbol.CIRCLE);
    }

    @Test
    public void Test() {
        assertNotNull(s);
        // null
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(null);
        });
        // too long array
        BoardInterface[] test1 = new BoardInterface[10];
        int i;
        for (i = 0; i < 10; i++)
            test1[i] = makexwin();
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(test1);
        });

        // too short array
        BoardInterface[] test2 = new BoardInterface[8];
        for (i = 0; i < 8; i++)
            test2[i] = makexwin();
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(test2);
        });

        // null array
        BoardInterface[] test3 = new BoardInterface[9];
        assertThrows(IllegalArgumentException.class, () -> {
            s.setBoards(test3);
        });

        // check getBoards and setBoards
        BoardInterface[] aux = new BoardInterface[9];
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createBoard();
        aux[5] = makexwin();
        aux[8] = makeowin();
        aux[0] = makexwin();
        s.setBoards(aux);
        assertArrayEquals(s.getBoards(), aux);

        // check getCurrentPlayerSymbol and setCurrentPlayerSymbol
        s.setBoards(makenew()); // reset board
        assertThrows(IllegalArgumentException.class, () -> {
            s.setCurrentPlayerSymbol(null);
        });
        s.setCurrentPlayerSymbol(Symbol.CROSS);
        assertEquals(s.getCurrentPlayerSymbol(), Symbol.CROSS);
        s.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertEquals(s.getCurrentPlayerSymbol(), Symbol.CIRCLE);
        s.setCurrentPlayerSymbol(Symbol.EMPTY);
        assertEquals(s.getCurrentPlayerSymbol(), Symbol.EMPTY);

        // check setMarkAt, setIndexNextBoard, getIndexNextBoard, isMovePossible and
        // isMovePossible
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(null, 1, 0);
        });
        s.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertThrows(IllegalArgumentException.class, () -> {
            s.setMarkAt(Symbol.CROSS, 1, 0);
        });
        s.setCurrentPlayerSymbol(Symbol.EMPTY);
        assertEquals(s.setMarkAt(Symbol.EMPTY, 1, 0), true);
        s.setCurrentPlayerSymbol(Symbol.CIRCLE);
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
                    assertEquals(s.isMovePossible(j), false);
                    assertEquals(s.isMovePossible(j, 0), false);
                    assertEquals(s.setMarkAt(Symbol.CIRCLE, j, 0), false);
                } else {
                    assertEquals(s.isMovePossible(j), true);
                    assertEquals(s.isMovePossible(j, 0), true);
                    assertEquals(s.setMarkAt(Symbol.CIRCLE, j, 0), true);
                }
        }
        // if nextboard is -1 check that it can write everywhere
        s.setBoards(makenew());
        for (i = 0; i < 9; i++) {
            s.setIndexNextBoard(-1);
            assertEquals(s.getIndexNextBoard(), -1);
            assertEquals(s.isMovePossible(i), true);
            assertEquals(s.isMovePossible(i, 0), true);
            assertEquals(s.setMarkAt(Symbol.CIRCLE, i, 0), true);
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
        s.setIndexNextBoard(3);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 3, 5), true);
        assertEquals(s.getIndexNextBoard(), -1);

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
        s.setIndexNextBoard(3);
        assertEquals(s.setMarkAt(Symbol.CIRCLE, 3, 5), true);
        assertEquals(s.getIndexNextBoard(), -1);

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
