package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class BoardTest {

    BoardInterface b = UTTTFactory.createBoard();

    public BoardInterface makenew() {
        BoardInterface b2 = UTTTFactory.createBoard();
        MarkInterface[] aux = new MarkInterface[9];
        int i;
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        b2.setMarks(aux);
        return b2;
    }

    public void checkindexes(int x, int y, int z) {
        b = makenew();
        assertEquals(b.isClosed(), false);
        assertEquals(b.getWinner(), Symbol.EMPTY);
        b.setMarkAt(Symbol.CROSS, x);
        b.setMarkAt(Symbol.CROSS, y);
        b.setMarkAt(Symbol.CROSS, z);
        assertEquals(b.isMovePossible(8 - x), false);
        assertEquals(b.setMarkAt(Symbol.CIRCLE, 8 - x), false);
        assertEquals(b.isMovePossible(8 - y), false);
        assertEquals(b.setMarkAt(Symbol.CIRCLE, 8 - y), false);
        assertEquals(b.isMovePossible(8 - z), false);
        assertEquals(b.setMarkAt(Symbol.CIRCLE, 8 - z), false);
        assertEquals(b.isClosed(), true);
        assertEquals(b.getWinner(), Symbol.CROSS);
        b = makenew();
        b.setMarkAt(Symbol.CIRCLE, x);
        b.setMarkAt(Symbol.CIRCLE, y);
        b.setMarkAt(Symbol.CIRCLE, z);
        assertEquals(b.isMovePossible(8 - x), false);
        assertEquals(b.setMarkAt(Symbol.CROSS, 8 - x), false);
        assertEquals(b.isMovePossible(8 - y), false);
        assertEquals(b.setMarkAt(Symbol.CROSS, 8 - y), false);
        assertEquals(b.isMovePossible(8 - z), false);
        assertEquals(b.setMarkAt(Symbol.CROSS, 8 - z), false);
        assertEquals(b.isClosed(), true);
        assertEquals(b.getWinner(), Symbol.CIRCLE);
    }

    @Test
    public void Test() {
        assertNotNull(b);
        // null
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarks(null);
        });
        // too long array
        MarkInterface[] test1 = new MarkInterface[10];
        int i;
        for (i = 0; i < 9; i++)
            test1[i] = UTTTFactory.createMark(Symbol.CIRCLE, i);
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarks(test1);
        });
        // too short array
        MarkInterface[] test2 = new MarkInterface[8];
        for (i = 0; i < 8; i++)
            test2[i] = UTTTFactory.createMark(Symbol.CROSS, i);
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarks(test2);
        });
        // null array
        MarkInterface[] test3 = new MarkInterface[9];
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarks(test3);
        });
        // check getMarks and setMarks
        MarkInterface[] aux = new MarkInterface[9];
        for (i = 0; i < 9; i++)
            aux[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        aux[5].setSymbol(Symbol.CROSS);
        aux[8].setSymbol(Symbol.CIRCLE);
        aux[0].setSymbol(Symbol.CIRCLE);
        b.setMarks(aux);
        assertArrayEquals(b.getMarks(), aux);

        // check setMarkAt and isMovePossible
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarkAt(null, 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarkAt(Symbol.CIRCLE, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            b.setMarkAt(Symbol.CIRCLE, 9);
        });
        assertEquals(b.setMarkAt(Symbol.EMPTY, 4), true);
        assertEquals(b.setMarkAt(Symbol.EMPTY, 4), true);
        assertEquals(b.setMarkAt(Symbol.EMPTY, 5), false);
        assertEquals(b.isMovePossible(2), true);
        b.setMarkAt(Symbol.CIRCLE, 2);
        assertEquals(b.getMarks()[2].getSymbol(), Symbol.CIRCLE);
        assertEquals(b.isMovePossible(2), false);
        assertEquals(b.setMarkAt(Symbol.CROSS, 2), false);

        assertEquals(b.isMovePossible(3), true);
        b.setMarkAt(Symbol.CROSS, 3);
        assertEquals(b.getMarks()[3].getSymbol(), Symbol.CROSS);
        assertEquals(b.isMovePossible(3), false);
        assertEquals(b.setMarkAt(Symbol.CROSS, 3), false);

        // check isClosed and getWinner
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
        // check tie
        b = makenew();
        b.setMarkAt(Symbol.CROSS, 1);
        b.setMarkAt(Symbol.CROSS, 5);
        b.setMarkAt(Symbol.CROSS, 6);
        b.setMarkAt(Symbol.CROSS, 8);
        b.setMarkAt(Symbol.CIRCLE, 0);
        b.setMarkAt(Symbol.CIRCLE, 2);
        b.setMarkAt(Symbol.CIRCLE, 3);
        b.setMarkAt(Symbol.CIRCLE, 4);
        b.setMarkAt(Symbol.CIRCLE, 7);
        for (i = 0; i < 9; i++)
            assertEquals(b.isMovePossible(i), false);
        assertEquals(b.isClosed(), true);
        assertEquals(b.getWinner(), Symbol.EMPTY);
    }

}
