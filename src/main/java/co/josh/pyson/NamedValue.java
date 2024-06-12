package co.josh.pyson;

/**
 * Class for storing a value with a name, aka a PYSON entry
 */
public class NamedValue {
    private String name;
    private Value value;

    /**
     * NamedValue object constructor
     *
     * @param name  the name of the PYSON entry
     * @param value the value to be associated with the name, as a Value object
     */
    NamedValue(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    /**
     * NamedValue object constructor
     *
     * @param name  the name of the PYSON entry
     * @param value the value to be associated with the name, either an int, float, String, or String[]
     * @throws InvalidPysonFormatException if the type of value is not int, float, String, or String[]
     */
    NamedValue(String name, Object value) throws InvalidPysonFormatException {
        this.name = name;
        this.value = new Value(value);
    }

    /**
     * Getter for the name
     *
     * @return the name of the entry
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     *
     * @param name the new name for the entry
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Swaps the name with a new name
     *
     * @param name the new name for the entry
     * @return the previous name
     */
    public String swapName(String name) {
        String oldName = this.name;
        this.name = name;
        return oldName;
    }

    /**
     * Getter for the type of the PYSON entry
     *
     * @return the type object for the PYSON entry
     */
    public Type getType() {
        return this.value.getType();
    }

    /**
     * Gets the string representation for the type of the PYSON entry
     *
     * @return the string representation of the type
     */
    public String getTypeStr() {
        return this.value.getTypeStr();
    }

    /**
     * Getter for the value object
     *
     * @return the value as a Value object
     */
    public Value getValueObj() {
        return value;
    }

    /**
     * Gets the value of the PYSON object
     *
     * @return either an int, float, String, or String[], so you can safely cast the result to any of those
     */
    public Object getValue() {
        return this.value.getValue();
    }

    /**
     * Setter for the value object
     *
     * @param value the new value
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Swaps the value with a new value
     *
     * @param value the new value for the entry
     * @return the previous value
     */
    public Value swapValue(Value value) {
        Value oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    /**
     * Gets the string representation of the PYSON entry
     *
     * @return the String representation in the format of name:type:value
     */
    public String pysonStr() {
        return this.name + ":" + this.value.toString();
    }

    @Override
    public String toString() {
        return this.pysonStr();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedValue) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
