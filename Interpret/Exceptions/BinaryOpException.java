package lang.Interpret.Exceptions;

import lang.Interpret.Operator;


public class BinaryOpException extends CommonError{
    String left,right;
    Operator op;
    public BinaryOpException(int lineNum, int columnName, String left, String right, Operator op) {
        super(lineNum, columnName);
        this.left = left;
        this.right = right;
        this.op = op;
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Binary Operation Error: Cannot perform operation " + op + " between " +left + " and " +right;
    }
}
