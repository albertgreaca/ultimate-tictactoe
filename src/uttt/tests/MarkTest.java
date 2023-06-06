package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class MarkTest {

    MarkInterface m;

    @Test
    public void Test() {
        // empty start
        m = UTTTFactory.createMark(Symbol.EMPTY, 3);
        assertNotNull(m);

        assertEquals(m.getSymbol(), Symbol.EMPTY);
        assertEquals(m.getPosition(), 3);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(Symbol.EMPTY);
        });

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);

        // circle start
        m = UTTTFactory.createMark(Symbol.CIRCLE, 5);
        assertNotNull(m);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);
        assertEquals(m.getPosition(), 5);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(Symbol.EMPTY);
        });

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);

        // cross start
        m = UTTTFactory.createMark(Symbol.CROSS, 10);
        assertNotNull(m);
        assertEquals(m.getSymbol(), Symbol.CROSS);
        assertEquals(m.getPosition(), 10);

        assertThrows(IllegalArgumentException.class, () -> {
            m.setSymbol(Symbol.EMPTY);
        });

        m.setSymbol(Symbol.CIRCLE);
        assertEquals(m.getSymbol(), Symbol.CIRCLE);

        m.setSymbol(Symbol.CROSS);
        assertEquals(m.getSymbol(), Symbol.CROSS);
    }

}
