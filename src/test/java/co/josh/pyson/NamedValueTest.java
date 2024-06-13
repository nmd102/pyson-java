package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamedValueTest {

    @Test
    void swapName() throws InvalidPysonFormatException {
        NamedValue value = new NamedValue("test", "string");
        String oldName = value.swapName("new_name");
        assertEquals("test", oldName);
        assertEquals("new_name", value.getName());
    }

    @Test
    void swapValue() throws InvalidPysonFormatException {
        NamedValue value = new NamedValue("test", "string");
        Object oldValue = value.swapValue(12);
        assertEquals("string", oldValue);
        assertEquals(12, value.getValue());

    }

    @Test
    void testToString() throws InvalidPysonFormatException {
        NamedValue intTest = new NamedValue("int_test", 12);
        assertEquals("int_test:int:12", intTest.toString());
        NamedValue floatTest = new NamedValue("float_test", 12.2f);
        assertEquals("float_test:float:12.2", floatTest.toString());
        NamedValue strTest = new NamedValue("str_test", "string");
        assertEquals("str_test:str:string", strTest.toString());
        NamedValue listTest = new NamedValue("list_test", new String[]{"list", "elements"});
        assertEquals("list_test:list:list(*)elements", listTest.toString());
        NamedValue emptyTest = new NamedValue("list_test", new String[]{"", "crazy", ""});
        assertEquals("list_test:list:(*)crazy(*)", emptyTest.toString());
    }
}