package co.josh.pyson;

/**
 * Represents a PYSON value, which can be either an integer, float, String, or list of Strings
 */
public class Value {
    protected Type type;
    protected Object value;

    /**
     * Value object constructor
     * @param value the value to be stored in the object
     * @throws InvalidPysonFormatException if the type of <code>value</code> is not an int, float, String, or String[]
     * @since 0.1.0
     */
    public Value(Object value) throws InvalidPysonFormatException {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        this.type = switch (value) {
            case String _ -> Type.Str;
            case Integer _ -> Type.Int;
            case Float _ -> Type.Float;
            case String[] _ -> Type.List;
            default -> throw new InvalidPysonFormatException("Unsupported value type: " + value.getClass());
        };
        this.value = value;
    }

    /**
     * Returns the string representation of a PYSON object
     * @return the string representation
     * @see #pysonStr()
     * @since 0.1.0
     */
    @Override
    public String toString() {
        return this.pysonStr();
    }

    /**
     * Getter for the <code>Type</code> object
     * @return the <code>Type</code> enum representing the type of the value
     * @since 0.1.0
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets a string representation of the <code>Type</code>  object
     * @return a string, which is either "str", "int", "float", or "list". The string will always be all lowercase
     * @since 0.1.0
     * @see Type#toString()
     */
    public String getTypeStr() {
        return this.type.toString();
    }

    /**
     * Getter for the value
     * <p>
     * The value is guaranteed to be either an int, float, String, or String[],
     * and can be safely cast to any of those
     * @return the value
     * @since 0.1.0
     */
    public Object getValue() {
        return value;
    }

    /**
     * Gets the string representation of the object
     * <p>
     * The string will be in the form of <code>type:value</code>
     * <p>
     * If the type is a list, the value will be a string with the list elements separated by <code>(*)</code>
     * @return the string representation
     * @see #toString()
     * @since 0.1.0
     */
    public String pysonStr() {
        String value = this.type == Type.List ? String.join("(*)", (String[]) this.value) : this.value.toString();
        return this.type + ":" + value;
    }

    /**
     * Checks if the type is an int
     * @return true if it is an int, otherwise false
     * @since 0.1.0
     */
    public boolean isInt() {
        return this.type == Type.Int;
    }

    /**
     * Checks if the type is a float
     * @return true if it is a float, otherwise false
     * @since 0.1.0
     */
    public boolean isFloat() {
        return this.type == Type.Float;
    }

    /**
     * Checks if the type is a str
     * @return true if it is a str, otherwise false
     * @since 0.1.0
     */
    public boolean isStr() {
        return this.type == Type.Str;
    }

    /**
     * Checks if the type is a list
     * @return true if it is a list, otherwise false
     * @since 0.1.0
     */
    public boolean isList() {
        return this.type == Type.List;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Value) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
