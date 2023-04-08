package lang.Interpret;

public class VString extends Val{
    String val;
    Type type = Type.TString;
    public VString(String x){
        this.val = x;
    }


    @Override
    public String toString() {
        return val;
    }

    public String getVal() {
        return val;
    }
}
