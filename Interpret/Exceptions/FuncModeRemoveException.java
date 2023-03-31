package lang.Interpret.Exceptions;

public class FuncModeRemoveException extends CommonError {
    String val;


    public FuncModeRemoveException(int lineNum, int columnName, String val) {
        super(lineNum, columnName);
        this.val = val;

    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Remove) Error: Removing from list mutates {" + val + "} variable." +
                "Instead, try initialising a new list variable and use the " + val + ".without[] expression";
    }
}
