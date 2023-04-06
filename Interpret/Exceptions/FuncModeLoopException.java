package lang.Interpret.Exceptions;

public class FuncModeLoopException extends CommonError {

    public FuncModeLoopException(int lineNum, int columnName) {
        super(lineNum, columnName);

    }

    @Override
    public String getMessage() {
        return trace + "Functional Mode (Loop) Error: Loops alter states, and break immutability." +
                " Instead, try creating a (tail) recursive function for loop behaviour.";
    }
}
