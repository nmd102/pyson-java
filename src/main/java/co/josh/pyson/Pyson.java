package co.josh.pyson;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class Pyson {
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

    private static boolean isInList(String[] arr, String item) {
        for (String s : arr) {
            if (s.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static NamedValue parsePysonEntry(String entry) throws InvalidPysonFormatException, NumberFormatException {
        if (entry == null) {
            throw new NullPointerException("Entry cannot be null");
        }
        if (isInList(entry.split(""), "\n")) {
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

    public static NamedValue[] pysonToArray(String data) throws InvalidPysonFormatException, NumberFormatException {
        return parsePyson(data).values().toArray(new NamedValue[0]);
    }

    public static NamedValue[] pysonFileToArray(String filepath) throws InvalidPysonFormatException, NumberFormatException, FileNotFoundException {
        return pysonToArray(readFile(filepath));
    }

    public static HashMap<String, Value> pysonToMap(String data) throws InvalidPysonFormatException, NumberFormatException {
        HashMap<String, NamedValue> map = parsePyson(data);
        HashMap<String, Value> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, new Value(map.get(key).getValue()));
        }
        return result;
    }

    public static HashMap<String, Value> pysonFileToMap(String filepath) throws InvalidPysonFormatException, NumberFormatException, FileNotFoundException {
        return pysonToMap(readFile(filepath));
    }

    public static boolean isValidPysonEntry(String entry) {
        try {
            parsePysonEntry(entry);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidPyson(String data) {
        for(String s: data.split("\n")) {
            if(s.isEmpty()) continue;
            if(!isValidPysonEntry(s)) return false;
        }
        return true;
    }
}