package co.josh.pyson;

public class Value {
    protected Type type;
    protected Object value;
    public Value(Object value) throws InvalidPysonFormatException {
        this.type = switch (value) {
            case String _ -> Type.Str;
            case Integer _ -> Type.Int;
            case Float _ -> Type.Float;
            case String[] _ -> Type.List;
            default -> throw new InvalidPysonFormatException("Unsupported value type: " + value.getClass());
        };
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
        String value = this.type == Type.List ? String.join("(*)", (String[]) this.value) : this.value.toString();
        return this.type + ":" + value;
    }

    public boolean isInt() {
        return this.type == Type.Int;
    }

    public boolean isFloat() {
        return this.type == Type.Float;
    }

    public boolean isStr() {
        return this.type == Type.Str;
    }

    public boolean isList() {
        return this.type == Type.List;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Value) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
