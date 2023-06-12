package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class MarkTest {

    MarkInterface m;

    @Test
    public void Test() {
        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(null, 3);
        });
        // empty start
        m = UTTTFactory.createMark(Symbol.EMPTY, 3);
        assertNotNull(m);

        assertEquals(m.getSymbol(), Symbol.EMPTY);
        assertEquals(m.getPosition(), 3);

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);

        m.setSymbol(Symbol.EMPTY);
        assertEquals(m.getSymbol(), Symbol.EMPTY);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(null);
        });

        // circle start
        m = UTTTFactory.createMark(Symbol.CIRCLE, 5);
        assertNotNull(m);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);
        assertEquals(m.getPosition(), 5);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);

        m.setSymbol(Symbol.EMPTY);
        assertEquals(m.getSymbol(), Symbol.EMPTY);

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(null);
        });

        // cross start
        m = UTTTFactory.createMark(Symbol.CROSS, 7);
        assertNotNull(m);
        assertEquals(m.getSymbol(), Symbol.CROSS);
        assertEquals(m.getPosition(), 7);

        m.setSymbol(Symbol.EMPTY);
        assertEquals(m.getSymbol(), Symbol.EMPTY);

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CROSS, -5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CIRCLE, -5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.EMPTY, -5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CROSS, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CIRCLE, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.EMPTY, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CROSS, 9);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CIRCLE, 9);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.EMPTY, 9);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CROSS, 13);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.CIRCLE, 13);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            m = UTTTFactory.createMark(Symbol.EMPTY, 13);
        });
    }

}
