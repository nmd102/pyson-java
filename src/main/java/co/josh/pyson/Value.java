package co.josh.pyson;

public class Value {
    private final Object value;
    private final Type type;

    Value(Object value) throws InvalidPysonFormatException {
        if (value instanceof String) {
            this.type = new Type("str");
        } else if (value instanceof Integer) {
            this.type = new Type("int");
        } else if (value instanceof Float || value instanceof Double) {
            this.type = new Type("float");
        } else if (value instanceof String[]) {
            this.type = new Type("list");
        } else {
            throw new InvalidPysonFormatException("Unsupported value type: " + value.getClass());
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
        try {
            return this.type.equals(new Type("int"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    public boolean isFloat() {
        try {
            return this.type.equals(new Type("float"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    public boolean isStr() {
        try {
            return this.type.equals(new Type("str"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    public boolean isList() {
        try {
            return this.type.equals(new Type("list"));
        } catch (InvalidPysonFormatException e) {
            // This can't fail, so we are fine
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
