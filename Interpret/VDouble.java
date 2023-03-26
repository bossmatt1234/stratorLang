package lang.Interpret;

public class VDouble extends Val{
    double val;
    Type type = Type.TDouble;
    public VDouble (Double x){
        this.val = x;
    }

    public void setDynamic(boolean x){
        if(x){
            this.type = Type.TAuto;
        }else{
            this.type = Type.TDouble;
        }
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
