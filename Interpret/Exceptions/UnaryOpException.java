package lang.Interpret.Exceptions;

import lang.Interpret.Operator;
import lang.Interpret.Val;

public class UnaryOpException extends CommonError{
    String val;
    Operator op;
    public UnaryOpException(int lineNum, int columnName, String val, Operator op) {
        super(lineNum, columnName);
        this.val = val;
        this.op = op;
    }

    @Override
    public String getMessage() {
        return trace + "Unary Operation Error: Operation " + op + " cannot be applied to " + val;
    }
}
