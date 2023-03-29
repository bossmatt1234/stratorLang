package lang.Interpret.Exceptions;

import lang.Interpret.ErrorType;
import lang.Interpret.Type;
import lang.Interpret.Val;

public class TypeArgException extends TypeErrException{


    public TypeArgException(int lineNum, int columnName, Type type, String val) {
        super(lineNum, columnName, type, val);
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Type argument error: " + type + " incompatible with " + val;
    }
}
