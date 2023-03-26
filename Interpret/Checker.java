package lang.Interpret;

public interface Checker {
    public boolean check(Type t,Val val);

    public boolean isType(Type t, VInteger val );
    public boolean isType(Type type, VDouble val);
    public boolean isType(Type type, VBool val);
    public boolean isType(Type type, VString val);
    public boolean isType(Type type, VChar val);
    public boolean isType(Type type, VList val);
    public Val returnValOfType(Type t);
    public Type returnType(Val val);

}
