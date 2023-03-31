package lang.Interpret;

public class VBool extends Val{
    boolean val;
    Type type = Type.TBoolean;
    public VBool(Boolean x){
        this.val = x;
    }


    @Override
    public String toString() {
        return "" + val;
    }

    public boolean isVal() {
        return val;
    }
}
