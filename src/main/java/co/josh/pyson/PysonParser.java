package co.josh.pyson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Class for all the methods related to parsing PYSON
 */
public final class PysonParser {
    /**
     * Reads a file to a string
     * @param filepath the path to the file
     * @return a string containing the contents of the file
     * @throws IOException if the file cannot be found or an IO error occurs
     */
    public static String readFile(String filepath) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            sb.append(line).append("\n");
            while (line != null) {
                line = reader.readLine();
                sb.append(line).append("\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            throw new IOException("Could not find pyson file at " + s + "/" + filepath);
        }
        return sb.toString();
    }

    /**
     * Checks if a char is in a String
     * @param s the String to check if it contains the item
     * @param item the item to check for
     * @return true if the item is found in the string, otherwise false
     */
    private static boolean isInString(String s, char item) {
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == item) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a pyson entry into a NamedValue object
     * <p>
     * the entry should be in the format <code>name:type:value</code>
     * @param entry the PYSON entry to be parsed
     * @return a NamedValue object representing the parsed PYSON entry
     * @throws InvalidPysonFormatException if the PYSON entry contains invalid formatting
     * @throws NumberFormatException if the value for an int or float is not a number
     * @since 0.1.0
     */
    public static NamedValue parsePysonEntry(String entry) throws InvalidPysonFormatException, NumberFormatException {
        if (entry == null) {
            throw new NullPointerException("Entry cannot be null");
        }
        if (isInString(entry, '\n')) {
            throw new InvalidPysonFormatException("A PYSON entry cannot contain a newline");
        }
        String[] data = entry.split(":", 3);
        if (data.length != 3) {
            throw new InvalidPysonFormatException("Not enough colons found it pyson entry (expected 3, found" + (data.length-1) +" colons)");
        }
        String name = data[0];
        Type type = Type.getEnum(data[1]);
        Object value = switch (type) {
            case Int -> Integer.parseInt(data[2]);
            case Float -> Float.parseFloat(data[2]);
            case Str -> data[2];
            case List -> {
                String[] split = data[2].split(Pattern.quote("(*)"));
                if(data[2].endsWith("(*)")) {
                    String[] newSplit = new String[split.length + 1];
                    System.arraycopy(split, 0, newSplit, 0, split.length);
                    newSplit[newSplit.length - 1] = "";
                    yield newSplit;
                } else {
                    yield split;
                }
            }
        };
        return new NamedValue(name, value);
    }

    /**
     * Parses PYSON to a HashMap with the names as the keys, and a NamedValue object as the values
     * @param data the PYSON to parse
     * @return a HashMap with names as the keys, and NamedValues as the values
     * @throws InvalidPysonFormatException if the PYSON format is invalid
     * @throws NumberFormatException if the value for an int or float is not a number
     */
    static HashMap<String, NamedValue> parsePyson(String data) throws InvalidPysonFormatException, NumberFormatException {
        if (data == null) {
            throw new NullPointerException("Data cannot be null");
        }
        String[] split = data.split("\n");
        HashMap<String, NamedValue> map = new HashMap<>();
        for (String s: split) {
            if(s.isEmpty()) continue;
            NamedValue entry = parsePysonEntry(s);
            int before = map.size();
            map.put(entry.getName(), entry);
            if (before == map.size()) throw new InvalidPysonFormatException("Duplicates found in PYSON data");
        }
        return map;
    }
    /**
     * Parses PYSON to an array of <code>NamedValue</code> objects
     * <p>
     * Note that the order of the NamedValue object may not be the order that they were in the data
     * @param data the PYSON to be parsed
     * @return the array of NamedValue objects
     * @throws InvalidPysonFormatException if the PYSON data contains invalid formatting
     * @throws NumberFormatException if the value for an int or float is not a number
     * @since 0.1.0
     * @see #parsePysonEntry(String)
     */
    public static NamedValue[] pysonToArray(String data) throws InvalidPysonFormatException, NumberFormatException {
        return parsePyson(data).values().toArray(new NamedValue[0]);
    }

    /**
     * Reads a PYSON file to an array of NamedValue objects
     * <p>
     * Note that the order of the NamedValue object may not be the order that they were in the data
     * @param filepath the filepath to the PYSON file
     * @return the array of NamedValue objects
     * @throws InvalidPysonFormatException if the PYSON file contains invalid formatting
     * @throws NumberFormatException if the value for an int or float is not a number
     * @throws IOException if the file cannot be found or an IO error occurs
     * @since 0.1.0
     * @see #pysonToArray(String) 
     */
    public static NamedValue[] pysonFileToArray(String filepath) throws InvalidPysonFormatException, NumberFormatException, IOException {
        return pysonToArray(readFile(filepath));
    }

    /**
     * Parses PYSON to a map with the keys as the names and the values as the corresponding <code>Value</code> objects
     * @param data the PYSON to be parsed
     * @return a map with names as the keys and Value objects as the values
     * @throws InvalidPysonFormatException if the PYSON data contains invalid formatting
     * @throws NumberFormatException if the value for an int or float is not a number
     * @since 0.1.0
     */
    public static HashMap<String, Value> pysonToMap(String data) throws InvalidPysonFormatException, NumberFormatException {
        HashMap<String, NamedValue> map = parsePyson(data);
        HashMap<String, Value> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, new Value(map.get(key).getValue()));
        }
        return result;
    }

    /**
     * Parses a PYSON file to a map with the keys as the names and the values as the corresponding <code>Value</code> objects
     * @param filepath the path to the PYSON file
     * @return a map with names as the keys and Value objects as the values
     * @throws InvalidPysonFormatException if the PYSON file contains invalid formatting
     * @throws NumberFormatException if the value for an int or float is not a number
     * @throws IOException if the PYSON file cannot be found or an IO error occurs
     * @since 0.1.0
     * @see #pysonToMap(String)
     */
    public static HashMap<String, Value> pysonFileToMap(String filepath) throws InvalidPysonFormatException, NumberFormatException, IOException {
        return pysonToMap(readFile(filepath));
    }

    /**
     * Checks if a PYSON entry is valid PYSON or not
     * @param entry the entry to check
     * @return true if the entry is valid PYSON, otherwise false
     * @since 0.1.0
     */
    public static boolean isValidPysonEntry(String entry) {
        try {
            parsePysonEntry(entry);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if PYSON data is valid PYSON or not
     * @param data the data to check
     * @return true if the data is valid PYSON, otherwise false
     * @since 0.1.0
     * @see #isValidPysonEntry(String)
     */
    public static boolean isValidPyson(String data) {
        for(String s: data.split("\n")) {
            if(s.isEmpty()) continue;
            if(!isValidPysonEntry(s)) return false;
        }
        return true;
    }
}