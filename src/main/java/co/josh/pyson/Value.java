package co.josh.pyson;

public class Value {
    private final Object value;
    private final Type type;

    Value(Object value) {
        if (value instanceof String) {
            this.type = new Type("str");
        } else if (value instanceof Integer) {
            this.type = new Type("int");
        } else if (value instanceof Float || value instanceof Double) {
            this.type = new Type("float");
        } else if (value instanceof String[]) {
            this.type = new Type("list");
        } else {
            throw new RuntimeException("Unsupported value type: " + value.getClass());
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return this.pysonStr();
    }

    public Type getType() {
        return this.type;
    }

    public String getTypeStr() {
        return this.type.toString();
    }

    public Object getValue() {
        return value;
    }

    public String pysonStr() {
        String value = this.getTypeStr().equals("list") ?
                String.join("(*)", (String[]) this.value) : this.value.toString();
        return this.type + ":" + value;
    }

    public boolean isInt() {
        return this.type.equals(new Type("int"));
    }

    public boolean isFloat() {
        return this.type.equals(new Type("float"));
    }

    public boolean isStr() {
        return this.type.equals(new Type("str"));
    }

    public boolean isList() {
        return this.type.equals(new Type("list"));
    }
}
