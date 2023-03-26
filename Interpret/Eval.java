package lang.Interpret;

public interface Eval {

    public Val checkInstance(Val leftVal, Val rightVal, Operator op, String leftRight);
    Val eval(VInteger x, VInteger y,Operator op);
    Val eval(VDouble x, VDouble y, Operator op);
    Val eval(VString x, VString y, Operator op);
    Val eval(VChar x, VChar y, Operator op);
    Val eval(VBool x, VBool y, Operator op);

    Val eval(VString x, VChar y, Operator op);
    Val eval(VChar x, VString y, Operator op);

    Val eval(VString x, VInteger y, Operator op);
    Val eval(VInteger x, VString y, Operator op);

    Val eval(VString x, VDouble  y, Operator op);
    Val eval(VDouble  x, VString y, Operator op);

    Val eval(VString x, VBool  y, Operator op);
    Val eval(VBool  x, VString y, Operator op);
}
