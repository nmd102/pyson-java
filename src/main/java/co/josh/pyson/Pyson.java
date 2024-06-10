package co.josh.pyson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Pyson {
    private static String readFile(String filepath)  {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static NamedValue parsePysonEntry(String data) {
        if(Arrays.asList(data.split("")).contains("\n")) {
            throw new RuntimeException("Pyson entries cannot contain newlines");
        }
        String[] temp = data.split(":", 3);
        if (temp.length < 3) {
            throw new RuntimeException("Pyson entry contains invalid format");
        }
        String name = temp[0];
        String type = temp[1];
        Object value = switch (type) {
            case "int" -> Integer.parseInt(temp[2]);
            case "float" -> Float.parseFloat(temp[2]);
            case "str" -> temp[2];
            case "list" -> {
                String[] list = temp[2].split(Pattern.quote("(*)"));
                if(temp[2].endsWith("(*)")) {
                    String[] newList = new String[list.length + 1];
                    System.arraycopy(list, 0, newList, 0, list.length);
                    newList[list.length] = "";
                    yield newList;
                } else {
                    yield list;
                }
            }
            default -> throw new RuntimeException("Invalid pyson type " + type);
        };
        return new NamedValue(name, new Value(value));
    }
    public static NamedValue[] pysonToArray(String data) {
        return parsePyson(data).values().toArray(new NamedValue[0]);
    }
    static HashMap<String, NamedValue> parsePyson(String data) {
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
                throw new RuntimeException("Duplicates found in pyson data");
            }
        }
        return map;
    }
    public static NamedValue[] pysonFileToArray(String filepath) {
        return pysonToArray(readFile(filepath));
    }
    public static HashMap<String, Value> pysonToMap(String data) {
        HashMap<String, NamedValue> map = parsePyson(data);
        HashMap<String, Value> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, map.get(key).getValue());
        }
        return result;
    }
    public static HashMap<String, Value> pysonFileToMap(String filepath) {
        return pysonToMap(readFile(filepath));
    }
    public static boolean isValidPysonEntry(String entry) {
        try {
            parsePysonEntry(entry);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
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