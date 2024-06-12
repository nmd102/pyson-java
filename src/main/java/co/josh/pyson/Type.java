package co.josh.pyson;

/**
 * PYSON type object
 */
public class Type {
    private final String type;

    /**
     * Constructs the Type object
     *
     * @param type either "int", "float", "str", or "list"
     * @throws InvalidPysonFormatException if the provided type is not either "int", "float", "str", or "list"
     */
    public Type(String type) throws InvalidPysonFormatException {
        if (!Pyson.isInList(new String[]{"int", "float", "str", "list"}, type)) {
            throw new InvalidPysonFormatException("Invalid type: " + type);
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
