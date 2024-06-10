package co.josh.pyson;

public class NamedValue {
    private String name;
    private Value value;

    NamedValue(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String swapName(String name) {
        String oldName = this.name;
        this.name = name;
        return oldName;
    }

    public Type getType() {
        return this.value.getType();
    }

    public String getTypeStr() {
        return this.value.getTypeStr();
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value swapValue(Value value) {
        Value oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public String pysonStr() {
        return this.name + ":" + this.value.toString();
    }

    @Override
    public String toString() {
        return this.pysonStr();
    }
}
