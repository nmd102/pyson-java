package co.josh.pyson;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PysonTest {

    @Test
    void parsePysonEntry() {
        NamedValue strTest;
        try {
            strTest = Pyson.parsePysonEntry("str_test:str:more: test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("str_test", strTest.getName());
        assertEquals("str", strTest.getType().toString());
        assertEquals("more: test", strTest.getValue());

        NamedValue intTest;
        try {
            intTest = Pyson.parsePysonEntry("int_test:int:69");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("int_test", intTest.getName());
        assertEquals("int", intTest.getType().toString());
        assertEquals(69, intTest.getValue());

        NamedValue floatTest;
        try {
            floatTest = Pyson.parsePysonEntry("float_test:float:3.1415");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("float_test", floatTest.getName());
        assertEquals("float", floatTest.getType().toString());
        assertEquals(3.1415f, floatTest.getValue());

        NamedValue listTest;
        try {
            listTest = Pyson.parsePysonEntry("list_test:list:list(*)more: (*)elements");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals("list_test", listTest.getName());
        assertEquals("list", listTest.getType().toString());
        assertArrayEquals(new String[]{"list", "more: ", "elements"}, (String[]) listTest.getValue());

        NamedValue emptyTest;
        try {
            emptyTest = Pyson.parsePysonEntry("empty_test:list:(*)crazy(*)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertArrayEquals(new String[]{"", "crazy", ""}, (String[]) emptyTest.getValue());

        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_int:int:error"));
        assertThrows(NumberFormatException.class, () -> Pyson.parsePysonEntry("bad_float:float:error"));
        InvalidPysonFormatException newline = assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePysonEntry("newline:str:\nnewline"));
        assertTrue(newline.getMessage().contains("Pyson entries cannot contain newlines"));
        InvalidPysonFormatException bad_format = assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePysonEntry("insufficient_colons:str"));
        assertTrue(bad_format.getMessage().contains("Pyson entry contains invalid format"));
        InvalidPysonFormatException bad_type = assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePysonEntry("bad_type:bool:test"));
        assertTrue(bad_type.getMessage().contains("Invalid pyson type"));
    }

    @Test
    void pysonToArray() {
        NamedValue[] arrayTest;
        try {
            arrayTest = Pyson.pysonToArray("str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        NamedValue[] correct;
        try {
            correct = new NamedValue[]{new NamedValue("str_test", new Value("more: test")), new NamedValue("float_test", new Value(3.1415f)), new NamedValue("list_test", new Value(new String[]{"list", "more: ", "elements"})), new NamedValue("empty_test", new Value(new String[]{"", "crazy", ""})), new NamedValue("int_test", new Value(69))};
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < arrayTest.length; i++) {
            assertEquals(correct[i].toString(), arrayTest[i].toString());
        }
        InvalidPysonFormatException duplicates = assertThrows(InvalidPysonFormatException.class, () -> Pyson.pysonToArray("duplicate:str:duplicate\nduplicate:list:duplicate"));
        assertTrue(duplicates.getMessage().contains("Duplicates found in pyson data"));
    }

    @Test
    void parsePyson() {
        HashMap<String, NamedValue> tested;
        try {
            tested = Pyson.parsePyson("str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HashMap<String, NamedValue> correct = new HashMap<>();
        try {
            correct.put("str_test", new NamedValue("str_test", new Value("more: test")));
            correct.put("int_test", new NamedValue("int_test", new Value(69)));
            correct.put("float_test", new NamedValue("float_test", new Value(3.1415f)));
            correct.put("list_test", new NamedValue("list_test", new Value(new String[]{"list", "more: ", "elements"})));
            correct.put("empty_test", new NamedValue("empty_test", new Value(new String[]{"", "crazy", ""})));
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
        assertEquals(correct.toString(), tested.toString());
        InvalidPysonFormatException duplicate = assertThrows(InvalidPysonFormatException.class, () -> Pyson.parsePyson("duplicate:str:duplicate\nduplicate:int:123"));
        assertTrue(duplicate.getMessage().contains("Duplicates found in pyson data"));
    }

    @Test
    void pysonToMap() {
        HashMap<String, Value> tested;
        try {
            tested = Pyson.pysonToMap("str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Value> correct = new HashMap<>();
        try {
            correct.put("str_test", new Value("more: test"));
            correct.put("int_test", new Value(69));
            correct.put("float_test", new Value(3.1415f));
            correct.put("list_test", new Value(new String[]{"list", "more: ", "elements"}));
            correct.put("empty_test", new Value(new String[]{"", "crazy", ""}));
        } catch (InvalidPysonFormatException e) {
            throw new RuntimeException(e);
        }
        assertEquals(correct.toString(), tested.toString());
        InvalidPysonFormatException duplicate = assertThrows(InvalidPysonFormatException.class, () -> Pyson.pysonToMap("duplicate:str:duplicate\nduplicate:int:123"));
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
        assertTrue(Pyson.isValidPyson("str_test:str:more: test\nint_test:int:69\nfloat_test:float:3.1415\nlist_test:list:list(*)more: (*)elements\nempty_test:list:(*)crazy(*)"));
        assertTrue(Pyson.isValidPyson("newlines:str:test\n\n\nmore_test:str:more test"));
        assertFalse(Pyson.isValidPysonEntry("duplicate:str:duplicate\nduplicate:int:69"));
        assertFalse(Pyson.isValidPysonEntry("insufficient:str:colors are coming up\nnot_enough:int"));
    }
}