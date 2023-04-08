package lang.Interpret;



public abstract class  Val {
    protected Type type;
    protected Object val;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}

