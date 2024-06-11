package co.josh.pyson;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

class PysonTest {

    @Test
    void parsePysonEntry() {
        NamedValue strTest = Pyson.parsePysonEntry("str_test:str:more: test");
        assertEquals("str_test", strTest.getName());
        assertEquals("str", strTest.getType().toString());
        assertEquals("more: test", strTest.getValue());
        NamedValue intTest = Pyson.parsePysonEntry("int_test:int:69");
        assertEquals("int_test", intTest.getName());
        assertEquals("int", intTest.getType().toString());
        assertEquals(69, intTest.getValue());
        NamedValue floatTest = Pyson.parsePysonEntry("float_test:float:3.1415");
        assertEquals("float_test", floatTest.getName());
        assertEquals("float", floatTest.getType().toString());
        assertEquals(3.1415f, floatTest.getValue());
        NamedValue listTest = Pyson.parsePysonEntry("list_test:list:list(*)more: (*)elements");
        assertEquals("list_test", listTest.getName());
        assertEquals("list", listTest.getType().toString());
        assertArrayEquals(new String[] {"list", "more: ", "elements"}, (String[])listTest.getValue());
        NamedValue emptyTest = Pyson.parsePysonEntry("empty_test:list:(*)crazy(*)");
        assertArrayEquals(new String[] {"", "crazy", ""}, (String[])emptyTest.getValue());
        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_int:int:error"));
        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_float:float:error"));
        RuntimeException newline = assertThrows(RuntimeException.class, () -> Pyson.parsePysonEntry("newline:str:\nnewline"));
        assertTrue(newline.getMessage().contains("Pyson entries cannot contain newlines"));
        RuntimeException bad_format = assertThrows(RuntimeException.class, () -> Pyson.parsePysonEntry("insufficient_colons:str"));
        assertTrue(bad_format.getMessage().contains("Pyson entry contains invalid format"));
        RuntimeException bad_type = assertThrows(RuntimeException.class, () -> Pyson.parsePysonEntry("bad_type:bool:test"));
        assertTrue(bad_type.getMessage().contains("Invalid pyson type"));
    }

    @Test
    void pysonToArray() {
        NamedValue[] arrayTest = Pyson.pysonToArray(
                "str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)"
        );
        NamedValue[] correct = new NamedValue[] {
                new NamedValue("str_test", new Value("more: test")),
                new NamedValue("float_test", new Value(3.1415f)),
                new NamedValue("list_test", new Value(new String[] {"list", "more: ", "elements"})),
                new NamedValue("empty_test", new Value(new String[] {"", "crazy", ""})),
                new NamedValue("int_test", new Value(69))
        };
        for (int i = 0; i < arrayTest.length; i++) {
            assertEquals(correct[i].toString(), arrayTest[i].toString());
        }
        RuntimeException duplicates = assertThrows(RuntimeException.class, () -> Pyson.pysonToArray("duplicate:str:duplicate\nduplicate:list:duplicate"));
        assertTrue(duplicates.getMessage().contains("Duplicates found in pyson data"));
    }

    @Test
    void parsePyson() {
        HashMap<String, NamedValue> tested = Pyson.parsePyson(
                "str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)"
        );
        HashMap<String, NamedValue> correct = new HashMap<>();
        correct.put("str_test", new NamedValue("str_test", new Value("more: test")));
        correct.put("int_test", new NamedValue("int_test", new Value(69)));
        correct.put("float_test", new NamedValue("float_test", new Value(3.1415f)));
        correct.put("list_test", new NamedValue("list_test", new Value(new String[] {"list", "more: ", "elements"})));
        correct.put("empty_test", new NamedValue("empty_test", new Value(new String[] {"", "crazy", ""})));
        assertEquals(correct.toString(), tested.toString());
        RuntimeException duplicate = assertThrows(RuntimeException.class, () -> Pyson.parsePyson("duplicate:str:duplicate\nduplicate:int:123"));
        assertTrue(duplicate.getMessage().contains("Duplicates found in pyson data"));
    }

    @Test
    void pysonToMap() {
        HashMap<String, Value> tested = Pyson.pysonToMap(
                "str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)"
        );
        HashMap<String, Value> correct = new HashMap<>();
        correct.put("str_test", new Value("more: test"));
        correct.put("int_test", new Value(69));
        correct.put("float_test", new Value(3.1415f));
        correct.put("list_test", new Value(new String[] {"list", "more: ", "elements"}));
        correct.put("empty_test", new Value(new String[] {"", "crazy", ""}));
        assertEquals(correct.toString(), tested.toString());
        RuntimeException duplicate = assertThrows(RuntimeException.class, () -> Pyson.pysonToMap("duplicate:str:duplicate\nduplicate:int:123"));
        assertTrue(duplicate.getMessage().contains("Duplicates found in pyson data"));
    }

    @Test
    void isValidPysonEntry() {
        assertTrue(Pyson.isValidPysonEntry("str_test:str:more: test"));
        assertTrue(Pyson.isValidPysonEntry("int_test:int:69"));
        assertTrue(Pyson.isValidPysonEntry("float_test:float:3.1415"));
        assertTrue(Pyson.isValidPysonEntry("list_test:list:list(*)more: (*)elements"));
        assertTrue(Pyson.isValidPysonEntry("empty_test:list:(*)crazy(*)"));
        assertFalse(Pyson.isValidPysonEntry("error:str"));
        assertFalse(Pyson.isValidPysonEntry("error:int:error"));
        assertFalse(Pyson.isValidPysonEntry("error:float:error"));
        assertFalse(Pyson.isValidPysonEntry("error:float:\nerror"));
        assertFalse(Pyson.isValidPysonEntry("error:what:\nerror"));
        assertFalse(Pyson.isValidPysonEntry("error::what"));
    }

    @Test
    void isValidPyson() {
        assertTrue(Pyson.isValidPyson(
                "str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)")
        );
        assertTrue(Pyson.isValidPyson("newlines:str:test\n\n\nmore_test:str:more test"));
        assertFalse(Pyson.isValidPysonEntry("duplicate:str:duplicate\nduplicate:int:69"));
        assertFalse(Pyson.isValidPysonEntry("insufficient:str:colors are coming up\nnot_enough:int"));
    }
}