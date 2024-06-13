package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testToString() {
        assertEquals("int", Type.Int.toString());
        assertEquals("float", Type.Float.toString());
        assertEquals("str", Type.Str.toString());
        assertEquals("list", Type.List.toString());
    }

    @Test
    void getEnum() throws InvalidPysonFormatException {
        assertEquals(Type.Int, Type.getEnum("int"));
        assertEquals(Type.Float, Type.getEnum("float"));
        assertEquals(Type.Str, Type.getEnum("str"));
        assertEquals(Type.List, Type.getEnum("list"));
        assertThrows(InvalidPysonFormatException.class, () -> Type.getEnum("invalid"));
        assertThrows(InvalidPysonFormatException.class, () -> Type.getEnum("Int"));
    }
}