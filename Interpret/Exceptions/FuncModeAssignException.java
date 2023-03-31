package lang.Interpret.Exceptions;

public class FuncModeAssignException extends CommonError {
    String var;
    public FuncModeAssignException(int lineNum, int columnName, String var) {
        super(lineNum, columnName);

        this.var = var;
    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Assign) Error: Cannot re-assign variable {" + var + "} in Functional mode. " +
                "Instead, try creating a new variable and assign the value to {" + var + "}.";
    }
}
