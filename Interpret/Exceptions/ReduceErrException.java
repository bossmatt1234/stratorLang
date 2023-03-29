package lang.Interpret.Exceptions;

public class ReduceErrException extends CommonError{
    public ReduceErrException(int lineNum, int columnName) {
        super(lineNum, columnName);
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Reduce function error: Only functions with 2 parameters are allowed";
    }
}
