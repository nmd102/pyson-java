package co.josh.pyson;

public enum Type {
    Int,
    Float,
    Str,
    List;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
    public static Type getEnum(String value) throws InvalidPysonFormatException {
        for (Type type : values()) {
            if(type.toString().equals(value)) {
                return type;
            }
        }
        throw new InvalidPysonFormatException("Unknown type " + value);
    }
}
