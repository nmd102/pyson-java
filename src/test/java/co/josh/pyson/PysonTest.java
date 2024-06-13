package co.josh.pyson;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PysonTest {

    @Test
    void parsePysonEntry() throws InvalidPysonFormatException {
        NamedValue intTest = Pyson.parsePysonEntry("int_test:int:20");
        assertEquals("int_test", intTest.getName());
        assertEquals(20, intTest.getValue());
        assertEquals(Type.Int, intTest.getType());

        NamedValue floatTest = Pyson.parsePysonEntry("float_test:float:20.2");
        assertEquals("float_test", floatTest.getName());
        assertEquals(20.2f, floatTest.getValue());
        assertEquals(Type.Float, floatTest.getType());

        NamedValue strTest = Pyson.parsePysonEntry("str_test:str:this: has: colons in it");
        assertEquals("str_test", strTest.getName());
        assertEquals("this: has: colons in it", strTest.getValue());
        assertEquals(Type.Str, strTest.getType());

        NamedValue listTest = Pyson.parsePysonEntry("list_test:list:test(*)more(*)test");
        assertEquals("list_test", listTest.getName());
        assertArrayEquals(new String[] {"test", "more", "test"}, (String[])listTest.getValue());
        assertEquals(Type.List, listTest.getType());

        NamedValue emptyTest = Pyson.parsePysonEntry("empty_test:list:(*)empty(*)");
        assertArrayEquals(new String[] {"", "empty", ""}, (String[])emptyTest.getValue());

        assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePysonEntry("newline:\n:str:fun"));
        assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePysonEntry("insufficient_colons:str"));
        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_int:int:error"));
        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_float:float:error"));
    }

    @Test
    void parsePyson() throws InvalidPysonFormatException {
        HashMap<String, NamedValue> map = Pyson.parsePyson("int_test:int:20\nfloat_test:float:20.2\nstr_test:str:this: has: colons in it\nlist_test:list:test(*)more(*)test");
        assertEquals(new NamedValue("int_test", 20), map.get("int_test"));
        assertEquals(new NamedValue("float_test", 20.2f), map.get("float_test"));
        assertEquals(new NamedValue("str_test", "this: has: colons in it"), map.get("str_test"));
        assertEquals(new NamedValue("list_test", new String[] {"test", "more", "test"}), map.get("list_test"));
    }

    @Test
    void isValidPysonEntry() {
        assertTrue(Pyson.isValidPysonEntry("int_test:int:20"));
        assertTrue(Pyson.isValidPysonEntry("float_test:float:20.2"));
        assertTrue(Pyson.isValidPysonEntry("str_test:str:this: has: colons in it"));
        assertTrue(Pyson.isValidPysonEntry("list_test:list:test(*)more(*)test"));
        assertTrue(Pyson.isValidPysonEntry("empty_test:list:(*)empty(*)"));
        assertFalse(Pyson.isValidPysonEntry("no colons:str"));
        assertFalse(Pyson.isValidPysonEntry("newline:\n:str:fun"));
        assertFalse(Pyson.isValidPysonEntry("bad_int:int:error"));
        assertFalse(Pyson.isValidPysonEntry("bad_float:float:error"));
    }

    @Test
    void isValidPyson() {
        assertTrue(Pyson.isValidPyson("int_test:int:20\nfloat_test:float:20.2\nstr_test:str:this: has: colons in it\nlist_test:list:test(*)more(*)test"));
    }
}