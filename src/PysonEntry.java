/**
 * Pyson data object
 */
public class PysonEntry {
    public String name;
    public String type;
    public Object value;
    /**
     * PysonEntry constructor
     * @param name the name of the entry
     * @param type the type, either str, int, float, or list
     * @param value the actual value of the entry
     * */
    public PysonEntry(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
    /**
     * PysonEntry constructor, but with type inference.
     * @param name the name of the entry
     * @param value the actual value
     */
    public PysonEntry(String name, Object value) {
        this.name = name;
        this.value = value;
        if(value instanceof String) {
            this.type = "str";
        } else if(value instanceof Integer) {
            this.type = "int";
        } else if(value instanceof Double || value instanceof Float) {
            this.type = "float";
        } else if (value instanceof String[]) {
            this.type = "list";
        } else {
            throw new RuntimeException("Unsupported type: " + value.getClass().getName());
        }
    }
    /**
     * Converts the pyson object to a string (in name:type:value format)
     * @return a string representation of the pyson object
     */
    public String toString() {
        String toReturn = this.name + ":" + this.type + ":";
        String value = switch (this.type) {
            case "list" -> String.join("(*)", (String[])this.value);
            case "int", "float", "str" -> this.value.toString();
            default -> throw new RuntimeException("Unknown type: " + this.type);
        };
        toReturn += value;
        return toReturn;
    }
}