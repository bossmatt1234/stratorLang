package lang.Interpret;

public class VString extends Val{
    String val;
    Type type = Type.TString;
    public VString(String x){
        this.val = x;
    }

    public void setDynamic(boolean x){
        if(x){
            this.type = Type.TAuto;
        }else{
            this.type = Type.TString;
        }
    }

    @Override
    public String toString() {
        return val;
    }

    public String getVal() {
        return val;
    }
}
