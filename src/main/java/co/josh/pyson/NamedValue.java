package co.josh.pyson;

/**
 * Object representing a PYSON entry
 */
public class NamedValue extends Value {
    private String name;
    /**
     * NamedValue object constructor
     * @param name the name for the pyson entry
     * @param value the value, which can be either an int, float, String, or String[]
     * @throws InvalidPysonFormatException if the value is not an int, float, String, or String[]
     * @since 0.1.0
     * @see Value#Value(Object)
     */
    NamedValue(String name, Object value) throws InvalidPysonFormatException {
        super(value);
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Getter for the name
     * @return the name of the PYSON value
     * @since 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param name the new name for the value
     * @since 0.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Swaps the current name with a new name
     * @param name the new name for the PYSON value
     * @return the previous name before it was changed to <code>name</code>
     * @since 0.1.0
     */
    public String swapName(String name) {
        String oldName = this.name;
        this.setName(name);
        return oldName;
    }

    /**
     * Setter for the value
     * @param value the new value for the Value, which should be either an int, float, String, or String[]
     * @since 0.1.0
     * @throws InvalidPysonFormatException if the value is not either an int, float, String, or String[]
     */
    public void setValue(Object value) throws InvalidPysonFormatException {
        Value newValue = new Value(value);
        this.type = newValue.getType();
        this.value = newValue.getValue();
    }

    /**
     * Swaps the current value with a new value
     * @param value the new value for the PYSON value, which should be either an int, float, String, or String[]
     * @return the previous value before it was changed to <code>value</code>
     * @since 0.1.0
     * @throws InvalidPysonFormatException if the value is not an int, float, String, or String[]
     */
    public Object swapValue(Object value) throws InvalidPysonFormatException {
        Object oldValue = this.value;
        this.setValue(value);
        return oldValue;
    }

    /**
     * Gets the string representation of the PYSON entry
     * <p>
     * The string will be in the form of <code>name:type:value</code>
     * <p>
     * If the type is a list, the value will be a string with the list elements separated by <code>(*)</code>
     * @return the string representation
     * @since 0.1.0
     * @see Value#pysonStr()
     * @see #toString()
     */
    @Override
    public String pysonStr() {
        return this.name + ":" + super.pysonStr();
    }

    /**
     * Returns the string representation of the PYSON entry
     * @return the string representation
     * @since 0.1.0
     * @see #pysonStr()
     */
    @Override
    public String toString() {
        return this.pysonStr();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NamedValue) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
