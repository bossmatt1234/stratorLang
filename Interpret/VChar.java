package lang.Interpret;

public class VChar extends Val{
    char val;
    Type type = Type.TChar;
    public VChar(Character x){
        this.val = x;
    }

    public void setDynamic(boolean x){
        if(x){
            this.type = Type.TAuto;
        }else{
            this.type = Type.TChar;
        }
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
