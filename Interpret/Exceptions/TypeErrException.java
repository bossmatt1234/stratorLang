package lang.Interpret.Exceptions;


import lang.Interpret.Type;

public class TypeErrException extends CommonError {
    Type type;
    String val;
    public TypeErrException(int lineNum, int columnName, Type type, String val) {
        super(lineNum, columnName);
        this.type = type;
        this.val = val;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Type error: " + type + " incompatible with " + val;
    }
}
