package co.josh.pyson;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class for all the PYSON static methods.
 */
public class Pyson {

    /**
     * Reads a text file to a string with the contents of the file
     *
     * @param filepath The path to the filepath
     * @return A string with the contents of the file
     * @throws FileNotFoundException If the pyson file cannot be found
     */
    private static String readFile(String filepath) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            throw new FileNotFoundException("Could not find pyson file at " + s + "/" + filepath);
        }
        return sb.toString();
    }

    /**
     * Parses a single PYSON entry
     *
     * @param data The entry to be parsed
     * @return a NamedValue object containing the parsed entry
     * @throws InvalidPysonFormatException If the PYSON formatting of the entry is invalid
     */
    public static NamedValue parsePysonEntry(String data) throws InvalidPysonFormatException {
        if (isInList(data.split(""), "\n")) {
            throw new InvalidPysonFormatException("Pyson entries cannot contain newlines");
        }
        String[] temp = data.split(":", 3);
        if (temp.length < 3) {
            throw new InvalidPysonFormatException("Pyson entry contains invalid format (not enough colons found)");
        }
        String name = temp[0];
        String type = temp[1];
        Object value = switch (type) {
            case "int" -> Integer.parseInt(temp[2]);
            case "float" -> Float.parseFloat(temp[2]);
            case "str" -> temp[2];
            case "list" -> {
                String[] list = temp[2].split(Pattern.quote("(*)"));
                if (temp[2].endsWith("(*)")) {
                    String[] newList = new String[list.length + 1];
                    System.arraycopy(list, 0, newList, 0, list.length);
                    newList[list.length] = "";
                    yield newList;
                } else {
                    yield list;
                }
            }
            default -> throw new InvalidPysonFormatException("Invalid pyson type " + type);
        };
        return new NamedValue(name, new Value(value));
    }

    /**
     * Parses multiple PYSON entries to an array of NamedValue objects
     *
     * @param data the PYSON entries to be parsed, seperated by newlines
     * @return An array of NamedValue objects with the parsed PYSON
     * @throws InvalidPysonFormatException If the PYSON data contains an invalid format
     */
    public static NamedValue[] pysonToArray(String data) throws InvalidPysonFormatException {
        return parsePyson(data).values().toArray(new NamedValue[0]);
    }

    /**
     * Method that drives the pysonToArray and pysonToMap methods
     *
     * @param data the PYSON data to be parsed
     * @return A HashMap with the names of the entries as the keys,and the corresponding NamedValue objects as the values
     * @throws InvalidPysonFormatException If the PYSON format is invalid
     */
    static HashMap<String, NamedValue> parsePyson(String data) throws InvalidPysonFormatException {
        String[] entries = data.split("\n");
        HashMap<String, NamedValue> map = new HashMap<>();
        for (String s : entries) {
            if (s.isEmpty()) {
                continue;
            }
            NamedValue entry = parsePysonEntry(s);
            int before = map.size();
            map.put(entry.getName(), entry);
            if (before == map.size()) {
                throw new InvalidPysonFormatException("Duplicates found in pyson data");
            }
        }
        return map;
    }

    /**
     * Checks if a String is in an array of Strings
     *
     * @param arr  the array of Strings to be checked
     * @param item the item to check for
     * @return true if the item is found, otherwise false
     */
    static boolean isInList(String[] arr, String item) {
        for (String s : arr) {
            if (s.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a PYSON file into an array of NamedValue objects
     *
     * @param filepath the filepath to the pyson file
     * @return an array of NamedValue objects containing the parsed PYSON
     * @throws FileNotFoundException       if the pyson file cannot be found
     * @throws InvalidPysonFormatException if the pyson file contains invalid pyson formatting
     */
    public static NamedValue[] pysonFileToArray(String filepath) throws FileNotFoundException, InvalidPysonFormatException {
        return pysonToArray(readFile(filepath));
    }

    /**
     * Parses PYSON data into a HashMap with the names as the keys and the corresponding Value object as the values
     *
     * @param data the PYSON data to be parsed
     * @return a HashMap with the format String name: Value value
     * @throws InvalidPysonFormatException if the Pyson data is invalid
     */
    public static HashMap<String, Value> pysonToMap(String data) throws InvalidPysonFormatException {
        HashMap<String, NamedValue> map = parsePyson(data);
        HashMap<String, Value> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, map.get(key).getValueObj());
        }
        return result;
    }

    /**
     * Parses a PYSON file to a HashMap with the name as the key, and the corresponding Value object as the value
     *
     * @param filepath the path to the PYSON file
     * @return a HashMap with the format String name: Value value
     * @throws FileNotFoundException       if the pyson file cannot be found
     * @throws InvalidPysonFormatException if the pyson file contains invalid pyson formatting
     */
    public static HashMap<String, Value> pysonFileToMap(String filepath) throws FileNotFoundException, InvalidPysonFormatException {
        return pysonToMap(readFile(filepath));
    }

    /**
     * Checks if a PYSON entry is valid or not
     *
     * @param entry is the entry to be checked
     * @return true if the PYSON is valid, otherwise false
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
     * Checks if PYSON is valid
     *
     * @param data is the PYSON data to check
     * @return true if the PYSON is valid, otherwise false
     */
    public static boolean isValidPyson(String data) {
        String[] split = data.split("\n");
        for (String s : split) {
            if (s.isEmpty()) {
                continue;
            }
            if (!isValidPysonEntry(s)) return false;
        }
        return true;
    }
}