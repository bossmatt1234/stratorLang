package lang.Interpret;


public class VInteger extends Val{
    int val;
    Type type;
    public VInteger(Integer x){
        this.val = x;
        setType(Type.TInt);
    }



    @Override
    public String toString() {
        return String.valueOf(val);
    }


    public void setType(Type type) {
        this.type = type;
    }

    public int getVal() {
        return val;
    }

    public Type getType() {
        return type;
    }
}
