package co.josh.pyson;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PysonParserTest {

    @Test
    void parsePysonEntry() throws InvalidPysonFormatException {
        NamedValue intTest = PysonParser.parsePysonEntry("int_test:int:20");
        assertEquals("int_test", intTest.getName());
        assertEquals(20, intTest.getValue());
        assertEquals(Type.Int, intTest.getType());

        NamedValue floatTest = PysonParser.parsePysonEntry("float_test:float:20.2");
        assertEquals("float_test", floatTest.getName());
        assertEquals(20.2f, floatTest.getValue());
        assertEquals(Type.Float, floatTest.getType());

        NamedValue strTest = PysonParser.parsePysonEntry("str_test:str:this: has: colons in it");
        assertEquals("str_test", strTest.getName());
        assertEquals("this: has: colons in it", strTest.getValue());
        assertEquals(Type.Str, strTest.getType());

        NamedValue listTest = PysonParser.parsePysonEntry("list_test:list:test(*)more(*)test");
        assertEquals("list_test", listTest.getName());
        assertArrayEquals(new String[] {"test", "more", "test"}, (String[])listTest.getValue());
        assertEquals(Type.List, listTest.getType());

        NamedValue emptyTest = PysonParser.parsePysonEntry("empty_test:list:(*)empty(*)");
        assertArrayEquals(new String[] {"", "empty", ""}, (String[])emptyTest.getValue());

        assertThrows(InvalidPysonFormatException.class, () -> PysonParser.parsePysonEntry("newline:\n:str:fun"));
        assertThrows(InvalidPysonFormatException.class, () -> PysonParser.parsePysonEntry("insufficient_colons:str"));
        assertThrows(NumberFormatException.class, () -> PysonParser.parsePysonEntry("bad_int:int:error"));
        assertThrows(NumberFormatException.class, () -> PysonParser.parsePysonEntry("bad_float:float:error"));
    }

    @Test
    void parsePyson() throws InvalidPysonFormatException {
        HashMap<String, NamedValue> map = PysonParser.parsePyson("int_test:int:20\nfloat_test:float:20.2\nstr_test:str:this: has: colons in it\nlist_test:list:test(*)more(*)test");
        assertEquals(new NamedValue("int_test", 20), map.get("int_test"));
        assertEquals(new NamedValue("float_test", 20.2f), map.get("float_test"));
        assertEquals(new NamedValue("str_test", "this: has: colons in it"), map.get("str_test"));
        assertEquals(new NamedValue("list_test", new String[] {"test", "more", "test"}), map.get("list_test"));
    }

    @Test
    void isValidPysonEntry() {
        assertTrue(PysonParser.isValidPysonEntry("int_test:int:20"));
        assertTrue(PysonParser.isValidPysonEntry("float_test:float:20.2"));
        assertTrue(PysonParser.isValidPysonEntry("str_test:str:this: has: colons in it"));
        assertTrue(PysonParser.isValidPysonEntry("list_test:list:test(*)more(*)test"));
        assertTrue(PysonParser.isValidPysonEntry("empty_test:list:(*)empty(*)"));
        assertFalse(PysonParser.isValidPysonEntry("no colons:str"));
        assertFalse(PysonParser.isValidPysonEntry("newline:\n:str:fun"));
        assertFalse(PysonParser.isValidPysonEntry("bad_int:int:error"));
        assertFalse(PysonParser.isValidPysonEntry("bad_float:float:error"));
    }

    @Test
    void isValidPyson() {
        assertTrue(PysonParser.isValidPyson("int_test:int:20\nfloat_test:float:20.2\nstr_test:str:this: has: colons in it\nlist_test:list:test(*)more(*)test"));
    }
}