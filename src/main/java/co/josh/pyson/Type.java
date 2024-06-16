package co.josh.pyson;

/**
 * Represents a PYSON type, which can be either an int, float, str (String), or list (String[]).
 * <p>
 * Note that lists can only have strings as elements.
 */
public enum Type {
    Int,
    Float,
    Str,
    List;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    /**
     * Gets the enum value from a string.
     * <p>
     * The input output is as follows:
     * <ul>
     *     <li>"str" Will return <code>Type.Str</code></li>
     *     <li>"float" Will return <code>Type.Float</code></li>
     *     <li>"int" Will return <code>Type.Int</code></li>
     *     <li>"list" Will return <code>Type.List</code></li>
     * </ul>
     * note that this method is case-sensitive, so calling <code>Type.getEnum("Str")</code> will fail
     * @param value the sting to be converted into the enum
     * @return the enum value from the string
     * @throws InvalidPysonFormatException if the value is not a valid PYSON type
     * @since 0.1.0
     */
    public static Type getEnum(String value) throws InvalidPysonFormatException {
        for (Type type : Type.values()) {
            if(type.toString().equals(value)) {
                return type;
            }
        }
        throw new InvalidPysonFormatException("Unknown type " + value);
    }
}
