import pyson.PysonEntry;
import pyson.PysonReader;
import pyson.PysonWriter;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // EXAMPLE USAGE
        // reading a pyson file
        ArrayList<PysonEntry> test = PysonReader.readPysonFile("example.pyson");
        for (PysonEntry entry : test) {
            System.out.print(entry.name + ": ");
            if(entry.type.equals("list")) {
                System.out.println(String.join(", ", (String[])entry.value));
            } else {
                System.out.println(entry.value);
            }
        }
        //and now append the contents of example.pyson to example2.pyson
        PysonWriter.addPysonEntry("example2.pyson", test);
    }

}