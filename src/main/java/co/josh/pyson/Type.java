package co.josh.pyson;

import java.util.Arrays;

public class Type {
    private final String type;

    public Type(String type) {
        if (!Arrays.asList(new String[]{"int", "float", "list", "str"}).contains(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
