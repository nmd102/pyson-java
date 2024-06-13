package co.josh.pyson;

public class NamedValue extends Value {
    private String name;
    NamedValue(String name, Object value) throws InvalidPysonFormatException {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String swapName(String name) {
        String oldName = this.name;
        this.setName(name);
        return oldName;
    }
    public void setValue(Object value) throws InvalidPysonFormatException {
        Value newValue = new Value(value);
        this.type = newValue.getType();
        this.value = newValue.getValue();
    }

    public Object swapValue(Object value) throws InvalidPysonFormatException {
        Object oldValue = this.value;
        this.setValue(value);
        return oldValue;
    }

    @Override
    public String pysonStr() {
        return this.name + ":" + super.pysonStr();
    }

    @Override
    public String toString() {
        return this.pysonStr();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NamedValue) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }
}
