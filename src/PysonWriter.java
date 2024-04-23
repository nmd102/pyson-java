import java.io.FileWriter;
import java.io.IOException;

public class PysonWriter {
    private static void appendFile(String filepath, String content) {
        try {
            FileWriter fw = new FileWriter(filepath, true);
            fw.write("\n" + content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Adds a pyson entry to a pyson file
     * @param filepath is the filepath to the pyson file you would like to add your entry to
     * @param content is the actual content you would like to add to the pyson file
     */
    public static void addPysonEntry(String filepath, PysonEntry content) {
        String toWrite = content.name + ":" + content.type + ":";
        String value = switch (content.type) {
            case "list" -> String.join("(*)", (String[])content.value);
            case "int", "float", "str" -> content.value.toString();
            default -> throw new RuntimeException("Unknown type: " + content.type);
        };
        toWrite += value;
        appendFile(filepath, toWrite);
    }
}