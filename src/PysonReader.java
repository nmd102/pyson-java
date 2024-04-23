import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PysonReader {
    /**
     * Reads a file
     * @param filename the name of the file you would like to read
     * @return a list of strings, where each element is a line in the file,
     **/
    private static ArrayList<String> readFile(String filename) {
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                toReturn.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }

    /**
     * Reads a pyson file
     * @param filename the filename to be read
     * @return an ArrayList of PysonEntries, where each item is a line in the pyson file
     * */
    public static ArrayList<PysonEntry> readPysonFile(String filename) {
        ArrayList<String> file = readFile(filename);
        ArrayList<PysonEntry> toReturn = new ArrayList<>();
        for (String s : file) {
            if(s.isEmpty()) continue;
            String[] temp = s.split(":");
            try {
                for (int j = 3; j < temp.length; j++) {
                    temp[2] += ":" + temp[j];
                }
                temp = new String[]{temp[0], temp[1], temp[2]};
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new RuntimeException("Invalid pyson format "+s);
            }
            String name = temp[0];
            String type;
            Object value;
            type = switch (temp[1]) {
                case "int" -> {
                    try {
                        value = Integer.parseInt(temp[2]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(temp[2] + " is not an int");
                    }
                    yield "int";
                }
                case "float" -> {
                    try {
                        value = Float.parseFloat(temp[2]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(temp[2] + " is not a float");
                    }
                    yield "float";
                }
                case "str" -> {
                    value = temp[2];
                    yield "str";
                }
                case "list" -> {
                    value = temp[2].split(Pattern.quote("(*)"));
                    yield "list";
                }
                default -> throw new TypeNotPresentException("Unknown type " + temp[1], null);
            };
            PysonEntry entry = new PysonEntry(name, type, value);
            toReturn.add(entry);
        }
        return toReturn;
    }
}