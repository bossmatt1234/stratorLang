package lang.Interpret.Exceptions;

public class FuncModeAppendException extends CommonError {
    String val;


    public FuncModeAppendException(int lineNum, int columnName,String val) {
        super(lineNum, columnName);
        this.val = val;

    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Append) Error: Appending to list mutates {" + val + "} variable." +
                "Instead, try initialising a new list variable and use the " + val + ".with() expression";
    }
}
