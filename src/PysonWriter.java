import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PysonWriter {
    /**
     * Appends a string to a file
     * @param filepath the file you would like to append content to
     * @param content the content you would like to append to the file
     */
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
     * Adds a pyson entry to a pyson file. Note that this will append to the file, not overwrite it
     * @param filepath the filepath to the pyson file you would like to add your entry to
     * @param content the actual content you would like to add to the pyson file, as a single pyson.PysonEntry object
     */
    public static void addPysonEntry(String filepath, PysonEntry content) {
        appendFile(filepath, content.toString());
    }
    /**
     * Adds a pyson entry to a pyson file. Note that this will append to the file, not overwrite it
     * @param  filepath the filepath to the pyson file you would like to add your entry to
     * @param content the actual content you would like to add to the pyson file, as an array of pyson.PysonEntry objects
     */
    public static void addPysonEntry(String filepath, PysonEntry[] content) {
        StringBuilder toWrite = new StringBuilder();
        for(PysonEntry entry : content) {
            toWrite.append(entry.toString()).append("\n");
        }
        appendFile(filepath, toWrite.toString());
    }
    /**
     * Adds a pyson entry to a pyson file. Note that this will append to the file, not overwrite it
     * @param  filepath the filepath to the pyson file you would like to add your entry to
     * @param content the actual content you would like to add to the pyson file as an ArrayList of pyson.PysonEntry objects
     */
    public static void addPysonEntry(String filepath, ArrayList<PysonEntry> content) {
        addPysonEntry(filepath, content.toArray(new PysonEntry[0]));
    }
}