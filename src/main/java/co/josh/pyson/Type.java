package co.josh.pyson;

public class Type {
    private final String type;

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
