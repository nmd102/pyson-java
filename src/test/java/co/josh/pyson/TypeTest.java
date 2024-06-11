package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testConstructor() {
        InvalidPysonFormatException badType = assertThrows(InvalidPysonFormatException.class, () -> new Type("not_a_type"));
        assertTrue(badType.getMessage().contains("Invalid type:"));
    }

    @Test
    void testToString() {
        try {
            assertEquals("str", new Type("str").toString());
            assertEquals("int", new Type("int").toString());
            assertEquals("float", new Type("float").toString());
            assertEquals("list", new Type("list").toString());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }
}