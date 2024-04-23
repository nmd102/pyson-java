import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PysonEntry pysonEntry = new PysonEntry("string", "str", "Testing Testing 123");
        PysonWriter.addPysonEntry("example.pyson", pysonEntry);
        ArrayList<PysonEntry> test = PysonReader.readPysonFile("example.pyson");
        for (PysonEntry entry : test) {
            System.out.print(entry.name + ": ");
            if(entry.type.equals("list")) {
                String[] tmp = (String[])entry.value;
                for (int i = 0; i < tmp.length; i++) {
                    System.out.print(tmp[i] + ((i == tmp.length-1) ? "" : ", "));
                }
                System.out.println();
            } else {
                System.out.println(entry.value);
            }
        }
    }

}