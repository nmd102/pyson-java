package co.josh.pyson;

/**
 * PYSON value object
 */
public class Value {
    private final Object value;
    private final Type type;

    /**
     * Value object constructor
     *
     * @param value is the value that the object should hold.
     *              the value should be either an int, float, String or String[]
     * @throws InvalidPysonFormatException if the value is not either int, float, String, or String[]
     */
    Value(Object value) throws InvalidPysonFormatException {
        if (value instanceof String) {
            this.type = new Type("str");
        } else if (value instanceof Integer) {
            this.type = new Type("int");
        } else if (value instanceof Float || value instanceof Double) {
            this.type = new Type("float");
        } else if (value instanceof String[]) {
            this.type = new Type("list");
        } else {
            throw new InvalidPysonFormatException("Unsupported value type: " + value.getClass());
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return this.pysonStr();
    }

    /**
     * Getter for the type object
     *
     * @return the type object for the Value
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the string representation for the type object
     *
     * @return the string representation of the type object
     */
    public String getTypeStr() {
        return this.type.toString();
    }

    /**
     * Getter for the value object
     *
     * @return the value, which is guaranteed to be either int, float, String, or String[], so you can safely cast it to those types
     */
    public Object getValue() {
        return value;
    }

    /**
     * Gets the string representation of the Value object
     *
     * @return a string in the format type:value
     */
    public String pysonStr() {
        String value = this.getTypeStr().equals("list") ?
                String.join("(*)", (String[]) this.value) : this.value.toString();
        return this.type + ":" + value;
    }

    /**
     * Checks if the pyson value is an int or not
     *
     * @return true if it is an int, otherwise false
     */
    public boolean isInt() {
        try {
            return this.type.equals(new Type("int"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    /**
     * Checks if the pyson value is a float or not
     *
     * @return true if it is a float, otherwise false
     */
    public boolean isFloat() {
        try {
            return this.type.equals(new Type("float"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    /**
     * Checks if the pyson value is a string or not
     *
     * @return true if it is a string, otherwise false
     */
    public boolean isStr() {
        try {
            return this.type.equals(new Type("str"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    /**
     * Checks if the pyson value is a list or not
     *
     * @return true if it is a list, otherwise false
     */
    public boolean isList() {
        try {
            return this.type.equals(new Type("list"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
