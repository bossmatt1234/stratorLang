package lang.Interpret;

public class VFunc extends Val{
    Type type;
    Type funcType;
    Function val;

    public VFunc(Function func, Type funcType){
        this.type = Type.TFunc;
        this.funcType = funcType;
        this.val = func;

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
