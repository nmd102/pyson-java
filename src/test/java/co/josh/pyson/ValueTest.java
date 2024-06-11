package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    @Test
    void testConstructor() {
        InvalidPysonFormatException badType = assertThrows(InvalidPysonFormatException.class, () -> new Value(new int[]{69}));
        assertTrue(badType.getMessage().contains("Unsupported value type"));
    }

    @Test
    void testToString() {
        try {
            Value int_test = new Value(69);
            assertEquals("int:69", int_test.toString());
            Value float_test = new Value(69.5f);
            assertEquals("float:69.5", float_test.toString());
            Value str_test = new Value("string");
            assertEquals("str:string", str_test.toString());
            Value list_test = new Value(new String[]{"a", "b", "c"});
            assertEquals("list:a(*)b(*)c", list_test.toString());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getType() {
        try {
            Value int_test = new Value(69);
            assertEquals(new Type("int"), int_test.getType());
            Value str_test = new Value("string");
            assertEquals(new Type("str"), str_test.getType());
            Value float_test = new Value(69.2f);
            assertEquals(new Type("float"), float_test.getType());
            Value list_test = new Value(new String[]{"list", "item"});
            assertEquals(new Type("list"), list_test.getType());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getValue() {
        try {
            Value int_test = new Value(69);
            assertEquals(69, int_test.getValue());
            Value str_test = new Value("string");
            assertEquals("string", str_test.getValue());
            Value float_test = new Value(69.2f);
            assertEquals(69.2f, float_test.getValue());
            Value list_test = new Value(new String[]{"list", "item"});
            assertArrayEquals(new String[]{"list", "item"}, (String[]) list_test.getValue());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isInt() {
        try {
            assertTrue(new Value(69).isInt());
            assertFalse(new Value("69").isInt());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isFloat() {
        try {
            assertFalse(new Value(69).isFloat());
            assertTrue(new Value(69.2f).isFloat());
            assertFalse(new Value("69.2f").isFloat());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isStr() {
        try {
            assertTrue(new Value("69").isStr());
            assertTrue(new Value("69.2f").isStr());
            assertTrue(new Value("string").isStr());
            assertFalse(new Value(12).isStr());
            assertFalse(new Value(new String[]{"string"}).isStr());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isList() {
        try {
            assertTrue(new Value(new String[]{"list"}).isList());
            assertTrue(new Value(new String[]{"more", "elements"}).isList());
            assertFalse(new Value("string").isList());
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
    }
}