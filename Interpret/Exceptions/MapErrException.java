package lang.Interpret.Exceptions;

public class MapErrException extends CommonError{

    public MapErrException(int lineNum, int columnName) {
        super(lineNum, columnName);
        getMessage();
    }

    @Override
    public String getMessage() {
        return trace + "Map function error: Only functions with 1 parameter are allowed";
    }
}
