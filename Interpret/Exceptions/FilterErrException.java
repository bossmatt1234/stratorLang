package lang.Interpret.Exceptions;

public class FilterErrException extends CommonError{
    public FilterErrException(int lineNum, int columnName) {
        super(lineNum, columnName);
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Filter function error: Only functions with 1 parameter are allowed";
    }
}
