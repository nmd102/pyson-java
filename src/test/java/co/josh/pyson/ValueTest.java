package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    @Test
    void testConstructor() {
        assertThrows(InvalidPysonFormatException.class, () -> new Value(new int[] {1, 2, 3}));
    }

    @Test
    void testToString() throws InvalidPysonFormatException {
        assertEquals("str:test", new Value("test").toString());
        assertEquals("int:120", new Value(120).toString());
        assertEquals("float:3.1415", new Value(3.1415f).toString());
        assertEquals("list:this(*)is a(*)list", new Value(new String[]{"this", "is a", "list"}).toString());
    }

    @Test
    void isInt() throws InvalidPysonFormatException {
        assertTrue(new Value(69).isInt());
        assertFalse(new Value(69.2f).isInt());
        assertFalse(new Value("69").isInt());
    }

    @Test
    void isFloat() throws InvalidPysonFormatException {
        assertTrue(new Value(12.2f).isFloat());
        assertFalse(new Value(12).isFloat());
        assertFalse(new Value("12.2").isFloat());
    }

    @Test
    void isStr() throws InvalidPysonFormatException {
        assertTrue(new Value("test").isStr());
        assertFalse(new Value(12).isStr());
    }

    @Test
    void isList() throws InvalidPysonFormatException {
        assertTrue(new Value(new String[]{"list"}).isList());
        assertFalse(new Value("string").isList());
    }
}