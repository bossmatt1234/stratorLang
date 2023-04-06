package lang.Interpret;

public interface Checker {

    boolean check(Type t,Val val, int lineNum, int columnNum);

    boolean isType(Type t, VInteger val );
    boolean isType(Type type, VDouble val);
    boolean isType(Type type, VBool val);
    boolean isType(Type type, VString val);
    boolean isType(Type type, VChar val);
    boolean isType(Type type, VList val);
    Val returnValOfType(Type t, int lineNum, int columnNum);
    Type returnType(Val val,int lineNum, int columnNum);

}
