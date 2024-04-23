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
}