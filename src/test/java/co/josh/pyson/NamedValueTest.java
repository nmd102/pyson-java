package co.josh.pyson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamedValueTest {

    @Test
    void getName() {
        assertEquals("test", new NamedValue("test", new Value("string")).getName());
    }

    @Test
    void setName() {
        NamedValue namedValue = new NamedValue("test", new Value("string"));
        assertEquals("test", namedValue.getName());
        namedValue.setName("new_name");
        assertEquals("new_name", namedValue.getName());
    }

    @Test
    void swapName() {
        NamedValue namedValue = new NamedValue("test", new Value("string"));
        String old = namedValue.swapName("new_name");
        assertEquals("test", old);
        assertEquals("new_name", namedValue.getName());
    }

    @Test
    void getType() {
        NamedValue int_test = new NamedValue("test", new Value(69));
        assertEquals(new Type("int"), int_test.getType());
        NamedValue str_test = new NamedValue("test", new Value("string"));
        assertEquals(new Type("str"), str_test.getType());
        NamedValue float_test = new NamedValue("test", new Value(69.2f));
        assertEquals(new Type("float"), float_test.getType());
        NamedValue list_test = new NamedValue("test", new Value(new String[] {"list", "item"}));
        assertEquals(new Type("list"), list_test.getType());
    }

    @Test
    void getValue() {
        NamedValue int_test = new NamedValue("test", new Value(69));
        assertEquals(new Value(69), int_test.getValue());
        NamedValue str_test = new NamedValue("test", new Value("string"));
        assertEquals(new Value("string"), str_test.getValue());
        NamedValue float_test = new NamedValue("test", new Value(69.2f));
        assertEquals(new Value(69.2), float_test.getValue());
        NamedValue list_test = new NamedValue("test", new Value(new String[] {"list", "item"}));
        assertEquals(new Value(new String[] {"list", "item"}), list_test.getValue());

    }

    @Test
    void setValue() {
        NamedValue namedValue = new NamedValue("test", new Value("string"));
        assertEquals(new Value("string"), namedValue.getValue());
        namedValue.setValue(new Value(69));
        assertEquals(new Value(69), namedValue.getValue());
    }

    @Test
    void swapValue() {
        NamedValue namedValue = new NamedValue("test", new Value("string"));
        Value old_value = namedValue.swapValue(new Value("new_value"));
        assertEquals(new Value("string"), old_value);
        assertEquals(new Value("new_value"), namedValue.getValue());
    }

    @Test
    void testToString() {
        NamedValue str_value = new NamedValue("test", new Value("string"));
        assertEquals("test:str:string", str_value.toString());
        NamedValue int_value = new NamedValue("test", new Value(69));
        assertEquals("test:int:69", int_value.toString());
        NamedValue float_value = new NamedValue("test", new Value(3.1415f));
        assertEquals("test:float:3.1415", float_value.toString());
        NamedValue list_value = new NamedValue("test", new Value(new String[] {"list", "item"}));
        assertEquals("test:list:list(*)item", list_value.toString());
    }
}